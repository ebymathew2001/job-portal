package com.jobportal.Job_Portal.job_seeker;

import com.jobportal.Job_Portal.exception.ResourceNotFoundException;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileRequestDto;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileResponseDto;
import com.jobportal.Job_Portal.user.User;
import com.jobportal.Job_Portal.user.UserRepository;
import com.jobportal.Job_Portal.user.UserResponseDto;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class JobSeekerProfileService {

    private JobSeekerProfileRepository jobSeekerProfileRepository;

    private UserRepository userRepository;

    public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepository,UserRepository userRepository){
        this.jobSeekerProfileRepository=jobSeekerProfileRepository;
        this.userRepository=userRepository;
    }

    public JobSeekerProfileResponseDto createProfile(JobSeekerProfileRequestDto requestDto, Principal principal){
        String email=principal.getName();
        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","email",email));

        if(jobSeekerProfileRepository.existsByUser(user)) {
            throw new IllegalStateException("Job seeker profile already exists");
        }

        JobSeekerProfile jobSeekerProfile=new JobSeekerProfile();
        jobSeekerProfile.setFullName(requestDto.getFullName());
        jobSeekerProfile.setBio(requestDto.getBio());
        jobSeekerProfile.setSkills(requestDto.getSkills());
        jobSeekerProfile.setExperience(requestDto.getExperience());
        jobSeekerProfile.setEducation(requestDto.getEducation());
        jobSeekerProfile.setResumeUrl(requestDto.getResumeUrl());
        jobSeekerProfile.setLocation(requestDto.getLocation());
        jobSeekerProfile.setUser(user);

        jobSeekerProfileRepository.save(jobSeekerProfile);

        UserResponseDto userResponseDto=new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setRole(user.getRole().name());
        userResponseDto.setEmail(user.getEmail());

        JobSeekerProfileResponseDto jobSeekerProfileResponseDto =new JobSeekerProfileResponseDto();
        jobSeekerProfileResponseDto.setId(jobSeekerProfile.getId());
        jobSeekerProfileResponseDto.setFullName(jobSeekerProfile.getFullName());
        jobSeekerProfileResponseDto.setBio(jobSeekerProfile.getBio());
        jobSeekerProfileResponseDto.setSkills(jobSeekerProfile.getSkills());
        jobSeekerProfileResponseDto.setExperience(jobSeekerProfile.getExperience());
        jobSeekerProfileResponseDto.setEducation(jobSeekerProfile.getEducation());
        jobSeekerProfileResponseDto.setLocation(jobSeekerProfile.getLocation());
        jobSeekerProfileResponseDto.setUpdatedAt(jobSeekerProfile.getUpdatedAt());
        jobSeekerProfileResponseDto.setResumeUrl(jobSeekerProfile.getResumeUrl());
        jobSeekerProfileResponseDto.setUser(userResponseDto);
        
        return jobSeekerProfileResponseDto;

    }

}
