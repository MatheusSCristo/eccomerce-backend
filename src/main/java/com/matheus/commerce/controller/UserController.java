package com.matheus.commerce.controller;

import com.matheus.commerce.dto.user.UserResponseDto;
import com.matheus.commerce.dto.user.UserUpdateDto;
import com.matheus.commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> userList = userService.findAll();
        return ResponseEntity.ok().body(userList);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable String id) {
        UserResponseDto user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponseDto> update(@RequestBody @Valid UserUpdateDto userUpdateDto, @PathVariable String id) {
        UserResponseDto user = userService.update(id, userUpdateDto);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserResponseDto> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }


}
