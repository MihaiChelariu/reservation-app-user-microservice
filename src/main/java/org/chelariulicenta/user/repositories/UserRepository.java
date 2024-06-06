package org.chelariulicenta.user.repositories;

import org.chelariulicenta.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    void deleteByUserEmail(String email);
    User getUserByUserEmail(String email);
}
