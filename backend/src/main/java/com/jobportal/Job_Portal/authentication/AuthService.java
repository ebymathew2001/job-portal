package com.jobportal.Job_Portal.authentication;

import com.jobportal.Job_Portal.authentication.dto.AuthenticationRequest;
import com.jobportal.Job_Portal.authentication.dto.AuthenticationResponse;
import com.jobportal.Job_Portal.security.jwt.JwtService;
import com.jobportal.Job_Portal.user.User;
import com.jobportal.Job_Portal.user.UserRequestDto;
import com.jobportal.Job_Portal.user.UserResponseDto;
import com.jobportal.Job_Portal.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public UserResponseDto registerUser(UserRequestDto requestDTO){
        if(userRepository.findByEmail(requestDTO.getEmail()).isPresent()){
            throw new RuntimeException("Email already registered");
        }

        User user= new User();
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setRole(requestDTO.getRole());

        userRepository.save(user);

        UserResponseDto responseDTO= new UserResponseDto();
        responseDTO.setRole(user.getRole().name());
        responseDTO.setId(user.getId());
        responseDTO.setEmail(user.getEmail());

        return responseDTO;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {

        //Authenticate user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token =jwtService.generateToken(user.getEmail(),user.getRole().name());

        AuthenticationResponse response=new AuthenticationResponse();
        response.setToken(token);
        response.setRole(user.getRole().name());
        return response;


    }




}
