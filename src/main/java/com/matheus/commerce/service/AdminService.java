package com.matheus.commerce.service;

import com.matheus.commerce.domain.User;
import com.matheus.commerce.dto.AdminResponse.AdminResponse;
import com.matheus.commerce.enums.Role;
import com.matheus.commerce.infra.security.JwtService;
import com.matheus.commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    public AdminResponse getUserByToken(@RequestBody String accessToken){
        AdminResponse adminResponse=new AdminResponse();
        String email=jwtService.extractUsername(accessToken);
        Optional<User> optionalUser=userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) return null;
        adminResponse.setUserEmail(email);
        adminResponse.setAuthorized(optionalUser.get().getRole() == Role.ADMIN);
        return adminResponse;
    }

}
