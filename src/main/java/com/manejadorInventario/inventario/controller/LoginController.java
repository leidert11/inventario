package com.manejadorInventario.inventario.controller;


import com.manejadorInventario.inventario.domain.repository.AdminRepository;
import com.manejadorInventario.inventario.domain.security.JWTAuthtenticationConfig;
import com.manejadorInventario.inventario.persistence.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private JWTAuthtenticationConfig jwtAuthtenticationConfig;

    @Autowired
    private AdminRepository adminRepository;

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        Admin admin = adminRepository.findByUsername(username);

        if (admin != null && admin.getPassword().equals(password)) {
            String token = jwtAuthtenticationConfig.getJWTToken(username);
            adminRepository.save(admin);
            return token;
        } else {
            throw new RuntimeException("Invalid Information");
        }
    }
}