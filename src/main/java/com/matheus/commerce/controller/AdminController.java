package com.matheus.commerce.controller;

import com.matheus.commerce.dto.AdminResponse.AdminResponse;
import com.matheus.commerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("{accessToken}")
    public ResponseEntity<AdminResponse> getUserByToken(@PathVariable String accessToken){
    AdminResponse adminResponse=adminService.getUserByToken(accessToken);
    return ResponseEntity.ok().body(adminResponse);
    }
}
