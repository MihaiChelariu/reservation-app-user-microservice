package org.chelariulicenta.user.controllers;

import org.chelariulicenta.user.services.UserService;
import org.chelariulicenta.user.views.VUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;


@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail(){
            return email;
        }

        public String getPassword(){
            return password;
        }
    }
    @Autowired
    private UserService userService;

    //ADMIN USER
    @GetMapping("/getUserById/{id}")
    private VUser getUserById(@PathVariable("id") Integer id) {
        VUser vUser = userService.getUserById(id);
        LOGGER.info("GET " + vUser);
        return vUser;
    }

    @GetMapping("/getUserByEmail/{email}")
    private VUser getUserByEmail(@PathVariable("email") String email){
        VUser vUser = userService.getUserByEmail(email);
        LOGGER.info("GET" + vUser);
        return vUser;
    }

    @PostMapping("/login")
    private ResponseEntity<?> getUserByEmail(@RequestBody LoginRequest request){
        try {
            String email = request.getEmail();
            String password = request.getPassword();
            VUser vUser = userService.loginUser(email, password);
            return ResponseEntity.ok(vUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/getAllUsers")
    private List<VUser> getUsers() {
        List<VUser> allUsers = userService.getAllUsers();
        LOGGER.info("GET all users");
        return allUsers;
    }

    @DeleteMapping("/deleteUserById/{id}")
    private void deleteUserById(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
    }

    @DeleteMapping("/deleteUserByEmail/{email}")
    private void deleteUserByEmail(@PathVariable("email") String email) {
        userService.deleteUserByEmail(email);
    }

    //USERI NORMALI
    @PostMapping("/saveUser")
    private VUser saveUser(@RequestBody VUser user) {
        LOGGER.info("POST " + user);
        VUser savedUser = userService.saveUser(user);
        return savedUser;
    }
}
