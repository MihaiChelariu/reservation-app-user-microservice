package org.chelariulicenta.user.views;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.PasswordAuthentication;

import static org.junit.jupiter.api.Assertions.*;

class VUserTest {

    @Test
    void test() {
        VUser user = new VUser(1, "name", "mail", "password", true);
        System.out.println(user.toString());
    }

}