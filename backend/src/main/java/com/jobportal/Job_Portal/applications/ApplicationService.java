package com.jobportal.Job_Portal.applications;

import com.jobportal.Job_Portal.applications.dto.*;
import com.jobportal.Job_Portal.employer.CompanyProfile;
import com.jobportal.Job_Portal.employer.CompanyProfileRepository;
import com.jobportal.Job_Portal.exception.ResourceNotFoundException;
import com.jobportal.Job_Portal.job_seeker.JobSeekerProfile;
import com.jobportal.Job_Portal.job_seeker.JobSeekerProfileRepository;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileResponseDto;
import com.jobportal.Job_Portal.job_seeker.dto.SimpleJobSeekerDto;
import com.jobportal.Job_Portal.jobs.Job;
import com.jobportal.Job_Portal.jobs.JobRepository;
import com.jobportal.Job_Portal.jobs.dto.JobResponseDto;
import com.jobportal.Job_Portal.jobs.dto.SimpleJobDto;
import com.jobportal.Job_Portal.user.User;
import com.jobportal.Job_Portal.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final UserRepository userRepository;

    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    private final JobRepository jobRepository;

    private final ApplicationRepository applicationRepository;

    private final ModelMapper modelMapper;
    
    private final CompanyProfileRepository companyProfileRepository;


    public ApplicationResponseDto createApplication(ApplicationCreateRequestDto applicationRequestDto, Principal principal){
        String email=principal.getName();

        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","email",email));

        Job job=jobRepository.findById(applicationRequestDto.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job","id",applicationRequestDto.getJobId()));

        JobSeekerProfile jobSeekerProfile=jobSeekerProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("JobSeekerProfile","user",user));


        if(applicationRepository.existsByJobAndJobSeeker(job,jobSeekerProfile)){
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


    public List<ApplicationSummaryDto> getMyApplications(Principal principal){
        String email=principal.getName();

        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","email",email));

        JobSeekerProfile jobSeekerProfile=jobSeekerProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("JobSeekerProfile","user",user));

        List<Application> applicationsList=applicationRepository.findByJobSeeker(jobSeekerProfile);

        List<ApplicationSummaryDto> applicationSummaryDtoList=new ArrayList<>();

        for(Application application:applicationsList){
            ApplicationSummaryDto applicationSummaryDto=modelMapper.map(application,ApplicationSummaryDto.class);

            SimpleJobDto simpleJobDto =modelMapper.map(application.getJob(),SimpleJobDto.class);
            simpleJobDto.setCompanyName(application.getJob().getCompanyProfile().getCompanyName());

            applicationSummaryDto.setSimpleJobDto(simpleJobDto);

            applicationSummaryDtoList.add(applicationSummaryDto);

        }

        return applicationSummaryDtoList;

    }


    public List<EmployerApplicationSummaryDto> getApplicationsForJob(Long jobId, Principal principal) {
        String email = principal.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        CompanyProfile companyProfile = companyProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("EmployerProfile", "user", user));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));


        if (!job.getCompanyProfile().getId().equals(companyProfile.getId())) {
            throw new AccessDeniedException("You do not have access to view applications for this job");
        }

        List<Application> applications = applicationRepository.findByJob(job);

        List<EmployerApplicationSummaryDto> dtoList = new ArrayList<>();

        for (Application application : applications) {
            EmployerApplicationSummaryDto dto = modelMapper.map(application, EmployerApplicationSummaryDto.class);

            SimpleJobSeekerDto jobSeekerDto = modelMapper.map(application.getJobSeeker(), SimpleJobSeekerDto.class);
            dto.setJobSeekerDto(jobSeekerDto);

            dtoList.add(dto);
        }

        return dtoList;
    }


    public JobSeekerApplicationDetailsDto getApplicationDetailsById(Long id,Principal principal){
        String email=principal.getName();

        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","email",email));

        JobSeekerProfile jobSeekerProfile=jobSeekerProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("JobSeekerProfile","User",user));

        Application application=applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application","id",id));

        if(!application.getJobSeeker().getId().equals(jobSeekerProfile.getId())){
            throw new AccessDeniedException("you do not have access to this application");
        }

        JobResponseDto jobResponseDto =modelMapper.map(application.getJob(),JobResponseDto.class);

        JobSeekerApplicationDetailsDto jobSeekerApplicationDetailsDto=modelMapper.map(application,JobSeekerApplicationDetailsDto.class);
        jobSeekerApplicationDetailsDto.setJobResponseDto(jobResponseDto);

        return jobSeekerApplicationDetailsDto;

    }

    public EmployerApplicationDetailsDto getApplicationDetailsForEmployer(Long jobId, Long applicationId, Principal principal) {
        String email = principal.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        CompanyProfile companyProfile = companyProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("EmployerProfile", "User", user));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));

        if (!job.getCompanyProfile().getId().equals(companyProfile.getId())) {
            throw new AccessDeniedException("You do not have access to this job’s applications");
        }

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", applicationId));

        if (!application.getJob().getId().equals(jobId)) {
            throw new IllegalArgumentException("This application does not belong to the specified job");
        }

        JobSeekerProfileResponseDto jobSeekerProfileResponseDto = modelMapper.map(application.getJobSeeker(), JobSeekerProfileResponseDto.class);

        EmployerApplicationDetailsDto employerApplicationDetailsDto = modelMapper.map(application, EmployerApplicationDetailsDto.class);

        employerApplicationDetailsDto.setJobSeekerProfileResponseDto(jobSeekerProfileResponseDto);

        return employerApplicationDetailsDto;


    }

    public StatusUpdateResponseDto updateStatus(Long jobId,Long applicationId,Principal principal,ApplicationStatusUpdateRequestDto applicationStatusUpdateRequestDto){

        String email= principal.getName();

        User user=userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User","email",email));

        CompanyProfile companyProfile=companyProfileRepository.findByUser(user)
                .orElseThrow(()-> new ResourceNotFoundException("CompanyProfile","user",user));

        Job job=jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job","jobId",jobId));

        Application application=applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application","applicationId",applicationId));


        if(!job.getCompanyProfile().getId().equals(companyProfile.getId())){
            throw  new IllegalArgumentException("This job is not belongs to you");
        }

        String oldStatus= application.getStatus().name();

        application.setStatus(applicationStatusUpdateRequestDto.getStatus());

        applicationRepository.save(application);

        StatusUpdateResponseDto statusUpdateResponseDto=new StatusUpdateResponseDto();
        statusUpdateResponseDto.setApplicationId(applicationId);
        statusUpdateResponseDto.setOldStatus(oldStatus);
        statusUpdateResponseDto.setNewStatus(application.getStatus().name());
        statusUpdateResponseDto.setMessage("Status Successfully updated");

        return statusUpdateResponseDto;

    }





}
