package org.chelariulicenta.user.services;

import com.github.dozermapper.core.Mapper;
import org.chelariulicenta.user.model.User;
import org.chelariulicenta.user.repositories.UserRepository;
import org.chelariulicenta.user.views.VUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public VUser getUserById(Integer id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id " + id + " not found!"));
        VUser view = mapper.map(user, VUser.class);
        return view;
    }

    @Transactional(readOnly = true)
    public VUser getUserByEmail(String email){
        User user = userRepository.getUserByUserEmail(email);
        return mapper.map(user, VUser.class);
    }

    public VUser loginUser(String email, String password){
        User user = userRepository.getUserByUserEmail(email);
        if(user != null && encoder.matches(password, user.getUserPassword()))
        {
            return mapper.map(user, VUser.class);
        }
        else if (user == null){
            throw new RuntimeException("User not found with email: " + email);
        } else{
            throw new RuntimeException("Incorrect username or password");
        }
    }


    public VUser saveUser(VUser vUser) {
        // Verifică dacă email-ul există deja în baza de date
        User existingUser = userRepository.getUserByUserEmail(vUser.getUserEmail());
        if (existingUser != null) {
            throw new RuntimeException("Email already in use: " + vUser.getUserEmail());
        }

        User user = mapper.map(vUser, User.class);
        String encryptedPassword = encoder.encode(vUser.getUserPassword());
        user.setUserPassword(encryptedPassword);
        userRepository.saveAndFlush(user);
        return vUser;
    }

    @Transactional(readOnly = true)
    public List<VUser> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<VUser> vUserList = userList
                .stream()
                .map(user -> mapper.map(user, VUser.class))
                .toList();
        return vUserList;
    }


    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }


    public void deleteUserByEmail(String email) {
        userRepository.deleteByUserEmail(email);
    }
}
