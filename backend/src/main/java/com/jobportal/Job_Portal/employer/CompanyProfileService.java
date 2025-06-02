package com.jobportal.Job_Portal.employer;

import com.jobportal.Job_Portal.employer.dto.CompanyProfileRequestDto;
import com.jobportal.Job_Portal.employer.dto.CompanyProfileResponseDto;
import com.jobportal.Job_Portal.exception.ResourceNotFoundException;
import com.jobportal.Job_Portal.user.User;
import com.jobportal.Job_Portal.user.UserRepository;
import com.jobportal.Job_Portal.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CompanyProfileService {

    private final CompanyProfileRepository companyProfileRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;





    public CompanyProfileResponseDto createProfile(CompanyProfileRequestDto requestDto, Principal principal){
        String email=principal.getName();
        User user= userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : "+email));

        if(companyProfileRepository.existsByUser(user)){
           throw new IllegalStateException("User already has a company profile");
        }

        CompanyProfile companyProfile= modelMapper.map(requestDto,CompanyProfile.class);
        companyProfile.setUser(user);
        companyProfileRepository.save(companyProfile);

        UserResponseDto userResponseDto= modelMapper.map(user,UserResponseDto.class);
        userResponseDto.setRole(user.getRole().name());

        CompanyProfileResponseDto responseDto=modelMapper.map(companyProfile,CompanyProfileResponseDto.class);
        responseDto.setUser(userResponseDto);
        return responseDto;

    }

    public CompanyProfileResponseDto viewProfile(Principal principal){
        String email=principal.getName();
        User user=userRepository.findByEmail(email)
                .orElseThrow(() ->new ResourceNotFoundException("User","email", email));
        CompanyProfile companyProfile=companyProfileRepository.findByUser(user).
                orElseThrow(()-> new ResourceNotFoundException("CompanyProfile","user",user));

        UserResponseDto userResponseDto= modelMapper.map(user,UserResponseDto.class);
        userResponseDto.setRole(user.getRole().name());


        CompanyProfileResponseDto companyProfileResponseDto=modelMapper.map(companyProfile,CompanyProfileResponseDto.class);
        companyProfileResponseDto.setUser(userResponseDto);

        return companyProfileResponseDto;

    }

    public CompanyProfileResponseDto updateProfile(CompanyProfileRequestDto requestDto,Principal principal){
        String email=principal.getName();

        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","email",email));

        CompanyProfile companyProfile=companyProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("CompanyProfile","user",user));

        companyProfile.setCompanyName(requestDto.getCompanyName());
        companyProfile.setBio(requestDto.getBio());
        companyProfile.setLocation(requestDto.getLocation());

        UserResponseDto userDto=new UserResponseDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());

        CompanyProfileResponseDto responseDto=new CompanyProfileResponseDto();

        responseDto.setId(companyProfile.getId());
        responseDto.setCompanyName(companyProfile.getCompanyName());
        responseDto.setBio(companyProfile.getBio());
        responseDto.setLocation(companyProfile.getLocation());
        responseDto.setUpdatedAt(companyProfile.getUpdatedAt());
        responseDto.setUser(userDto);

        return responseDto;

    }


}
