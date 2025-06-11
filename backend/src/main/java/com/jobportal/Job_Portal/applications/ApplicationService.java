package com.jobportal.Job_Portal.applications;

import com.jobportal.Job_Portal.applications.dto.ApplicationRequestDto;
import com.jobportal.Job_Portal.applications.dto.ApplicationResponseDto;
import com.jobportal.Job_Portal.exception.ResourceNotFoundException;
import com.jobportal.Job_Portal.job_seeker.JobSeekerProfile;
import com.jobportal.Job_Portal.job_seeker.JobSeekerProfileRepository;
import com.jobportal.Job_Portal.jobs.Job;
import com.jobportal.Job_Portal.jobs.JobRepository;
import com.jobportal.Job_Portal.user.User;
import com.jobportal.Job_Portal.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;



@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final UserRepository userRepository;

    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    private final JobRepository jobRepository;

    private final ApplicationRepository applicationRepository;

    private final ModelMapper modelMapper;


    public ApplicationResponseDto createApplication(ApplicationRequestDto applicationRequestDto, Principal principal){
        String email=principal.getName();

        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","email",email));

        Job job=jobRepository.findById(applicationRequestDto.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job","id",applicationRequestDto.getJobId()));

        JobSeekerProfile jobSeekerProfile=jobSeekerProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("JobSeekerProfile","user",user));


        if(applicationRepository.ExistByJobSeeker(job,jobSeekerProfile)){
            throw new RuntimeException("You have already applied for this job");
        }

        Application application=new Application();
        application.setJob(job);
        application.setJobSeeker(jobSeekerProfile);
        application.setStatus(ApplicationStatus.APPLIED);
        applicationRepository.save(application);

        ApplicationResponseDto applicationResponseDto=modelMapper.map(application,ApplicationResponseDto.class);
        applicationResponseDto.setMessage("Job application submitted successfully.");

        return applicationResponseDto;

    }







}
