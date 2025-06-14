package com.jobportal.Job_Portal.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(Principal principal){

        List<UserResponseDto> userResponseDto=userService.getAllUsers(principal);

        return ResponseEntity.ok(userResponseDto);
    }

}
