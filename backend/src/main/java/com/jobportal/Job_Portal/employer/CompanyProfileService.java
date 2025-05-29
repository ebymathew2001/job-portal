package com.jobportal.Job_Portal.employer;

import com.jobportal.Job_Portal.employer.dto.CompanyProfileRequestDto;
import com.jobportal.Job_Portal.employer.dto.CompanyProfileResponseDto;
import com.jobportal.Job_Portal.user.User;
import com.jobportal.Job_Portal.user.UserRepository;
import com.jobportal.Job_Portal.user.UserResponseDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class CompanyProfileService {

    private CompanyProfileRepository companyProfileRepository;
    private UserRepository userRepository;

    public CompanyProfileService(CompanyProfileRepository companyProfileRepository,UserRepository userRepository){
        this.companyProfileRepository=companyProfileRepository;
        this.userRepository=userRepository;
    }


    public CompanyProfileResponseDto createProfile(CompanyProfileRequestDto requestDto, Principal principal){
        String email=principal.getName();
        User user= userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : "+email));

        if(companyProfileRepository.existsByUser(user)){
           throw new IllegalStateException("User already has a company profile");
        }

        CompanyProfile companyProfile=new CompanyProfile();
        companyProfile.setCompanyName(requestDto.getCompanyName());
        companyProfile.setBio(requestDto.getBio());
        companyProfile.setLocation(requestDto.getLocation());
        companyProfile.setUser(user);

        companyProfileRepository.save(companyProfile);

        UserResponseDTO userDto = new UserResponseDTO();
        userDto.setId(companyProfile.getUser().getId());
        userDto.setEmail(companyProfile.getUser().getEmail());
        userDto.setRole(companyProfile.getUser().getRole().name());

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
