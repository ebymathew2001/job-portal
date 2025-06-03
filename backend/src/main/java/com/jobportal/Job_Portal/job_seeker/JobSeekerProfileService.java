package com.jobportal.Job_Portal.job_seeker;

import com.jobportal.Job_Portal.exception.ResourceNotFoundException;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileRequestDto;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileResponseDto;
import com.jobportal.Job_Portal.user.User;
import com.jobportal.Job_Portal.user.UserRepository;
import com.jobportal.Job_Portal.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobSeekerProfileService {


    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    private final UserRepository userRepository;
    
    private final ModelMapper modelMapper;



    public JobSeekerProfileResponseDto createProfile(JobSeekerProfileRequestDto requestDto, MultipartFile resumeFile,Principal principal){
        String email=principal.getName();
        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","email",email));

        if(jobSeekerProfileRepository.existsByUser(user)) {
            throw new IllegalStateException("Job seeker profile already exists");
        }

        //save resume file to local storage
        String uploadDir = "uploads/resumes/";
        String originalFilename= resumeFile.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalFilename;

        File uploadPath = new File(uploadDir);
        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }

        try{
            resumeFile.transferTo(new File(uploadDir + fileName));
        } catch (IOException e){
            throw new RuntimeException("Failed to upload resume", e);
        }

        JobSeekerProfile jobSeekerProfile= modelMapper.map(requestDto,JobSeekerProfile.class);
        jobSeekerProfile.setUser(user);
        jobSeekerProfile.setResumeUrl("/files/resumes/" + fileName);

        jobSeekerProfileRepository.save(jobSeekerProfile);

        UserResponseDto userResponseDto=modelMapper.map(user,UserResponseDto.class);
        userResponseDto.setRole(user.getRole().name());


        JobSeekerProfileResponseDto jobSeekerProfileResponseDto =modelMapper.map(jobSeekerProfile,JobSeekerProfileResponseDto.class);
        jobSeekerProfileResponseDto.setUser(userResponseDto);
        
        return jobSeekerProfileResponseDto;

    }

}
