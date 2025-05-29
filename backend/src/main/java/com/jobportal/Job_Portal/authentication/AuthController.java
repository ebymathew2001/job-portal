package com.jobportal.Job_Portal.authentication;

import com.jobportal.Job_Portal.authentication.dto.AuthenticationRequest;
import com.jobportal.Job_Portal.authentication.dto.AuthenticationResponse;
import com.jobportal.Job_Portal.user.UserRequestDTO;
import com.jobportal.Job_Portal.user.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO requestDTO){
        try{
            UserResponseDTO userResponseDTO = authService.registerUser(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        AuthenticationResponse response=authService.login(request);
        return ResponseEntity.ok(response);
    }


    









    






}
