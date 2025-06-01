package com.jobportal.Job_Portal.authentication;

import com.jobportal.Job_Portal.authentication.dto.AuthenticationRequest;
import com.jobportal.Job_Portal.authentication.dto.AuthenticationResponse;
import com.jobportal.Job_Portal.security.jwt.JwtService;
import com.jobportal.Job_Portal.user.User;
import com.jobportal.Job_Portal.user.UserRequestDto;
import com.jobportal.Job_Portal.user.UserResponseDto;
import com.jobportal.Job_Portal.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    
    private final JwtService jwtService;

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
