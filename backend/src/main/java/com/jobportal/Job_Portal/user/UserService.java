package com.jobportal.Job_Portal.user;

import com.jobportal.Job_Portal.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public List<UserResponseDto> getAllUsers(Principal principal){
        String email=principal.getName();

        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","email",email));


        List<User> userList=userRepository.findAll();

        List<UserResponseDto> userResponseDtoList=new ArrayList<>();

        for(User users: userList){

            UserResponseDto userResponseDto=modelMapper.map(users,UserResponseDto.class);
            userResponseDto.setRole(users.getRole().name());

            userResponseDtoList.add(userResponseDto);

        }
        return userResponseDtoList;


    }
}
