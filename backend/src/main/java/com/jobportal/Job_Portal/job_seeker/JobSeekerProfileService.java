package com.jobportal.Job_Portal.job_seeker;

import com.jobportal.Job_Portal.exception.ProfileAlreadyExistsException;
import com.jobportal.Job_Portal.exception.ResourceNotFoundException;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileRequestDto;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileResponseDto;
import com.jobportal.Job_Portal.job_seeker.dto.ResumeDownloadResponseDto;
import com.jobportal.Job_Portal.user.User;
import com.jobportal.Job_Portal.user.UserRepository;
import com.jobportal.Job_Portal.user.UserResponseDto;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
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
            throw new ProfileAlreadyExistsException("Job seeker profile already exists");
        }

        //save resume file to local storage
        String uploadDir = "D:/spring projects/Job-Portal/backend/uploads/resumes/";
        String originalFilename= resumeFile.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalFilename;

        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            boolean created = uploadPath.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create upload directory: " + uploadPath.getAbsolutePath());
            }
        }

        try {
            // Save the resume file
            File destinationFile = new File(uploadPath, fileName);
            resumeFile.transferTo(destinationFile);
            System.out.println("Resume saved at: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload resume: " + e.getMessage(), e);
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

    public JobSeekerProfileResponseDto viewProfile(Principal principal){
        String email=principal.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","email",email));

        JobSeekerProfile jobSeekerProfile= jobSeekerProfileRepository.findByUser(user)
                .orElseThrow(()-> new ResourceNotFoundException("JobSeekerProfile","user",user));

        UserResponseDto userResponseDto=modelMapper.map(user,UserResponseDto.class);
        userResponseDto.setRole(user.getRole().name());

        JobSeekerProfileResponseDto jobSeekerProfileResponseDto=modelMapper.map(jobSeekerProfile,JobSeekerProfileResponseDto.class);
        jobSeekerProfileResponseDto.setUser(userResponseDto);

        return jobSeekerProfileResponseDto;

    }
    public ResumeDownloadResponseDto getResumeForDownload(Principal principal, HttpServletRequest request) {
        String email = principal.getName();
        JobSeekerProfile profile = jobSeekerProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("JobSeekerProfile", "userEmail", email));

        String resumeFileName = profile.getResumeUrl().substring(profile.getResumeUrl().lastIndexOf("/") + 1);
        File file = new File("D:/spring projects/Job-Portal/backend/uploads/resumes/", resumeFileName);

        if (!file.exists()) {
            throw new ResourceNotFoundException("Resume", "fileName", resumeFileName);
        }

        Resource resource = new FileSystemResource(file);
        String contentType = request.getServletContext().getMimeType(file.getAbsolutePath());
        contentType = (contentType != null) ? contentType : "application/octet-stream";

        return new ResumeDownloadResponseDto(resource, file.getName(), contentType);
    }





    //for downloading resume







}
