package com.jobportal.Job_Portal.jobs;

import com.jobportal.Job_Portal.employer.CompanyProfile;
import com.jobportal.Job_Portal.employer.CompanyProfileRepository;
import com.jobportal.Job_Portal.employer.dto.CompanySummaryDto;
import com.jobportal.Job_Portal.exception.ResourceNotFoundException;
import com.jobportal.Job_Portal.jobs.dto.JobRequestDto;
import com.jobportal.Job_Portal.jobs.dto.JobResponseDto;
import com.jobportal.Job_Portal.jobs.dto.JobStatusUpdateDTO;
import com.jobportal.Job_Portal.user.User;
import com.jobportal.Job_Portal.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


import java.security.Principal;

@Service
@RequiredArgsConstructor
public class JobService {
    
    private JobRepository jobRepository;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private CompanyProfileRepository companyProfileRepository;


    public JobResponseDto createJob(JobRequestDto jobRequestDto, Principal principal){
        String email=principal.getName();
        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","email",email));
        CompanyProfile companyProfile=companyProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("CompanyProfile","user",user));

        Job job=modelMapper.map(jobRequestDto,Job.class);
        job.setJobStatus(JobStatus.ACTIVE);
        job.setCompanyProfile(companyProfile);
        jobRepository.save(job);

        CompanySummaryDto companySummaryDto=modelMapper.map(companyProfile,CompanySummaryDto.class);

        JobResponseDto jobResponseDto=modelMapper.map(job,JobResponseDto.class);
        jobResponseDto.setCompany(companySummaryDto);

        return jobResponseDto;

    }

    public JobResponseDto updateJob(Long id,JobRequestDto jobRequestDto,Principal principal){
        String email=principal.getName();

        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","email",email));

        CompanyProfile companyProfile=companyProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("CompanyProfile","user",user));

        Job job= jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job","id",id));

        Long jobOwnerId= job.getCompanyProfile().getId();
        Long currentLoggedUserCompanyId=companyProfile.getId();

        if(!jobOwnerId.equals(currentLoggedUserCompanyId)){
            throw new AccessDeniedException("You are not authorized to update this job");
        }
        modelMapper.map(jobRequestDto,job);
        job.setCompanyProfile(companyProfile);
        jobRepository.save(job);

        CompanySummaryDto companySummaryDto=modelMapper.map(companyProfile,CompanySummaryDto.class);

        JobResponseDto jobResponseDto=modelMapper.map(job,JobResponseDto.class);
        jobResponseDto.setCompany(companySummaryDto);

        return jobResponseDto;
    }

    public JobResponseDto updateStatus(Long id, JobStatusUpdateDTO jobStatusUpdateDTO,Principal principal){
        String email=principal.getName();

        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","email",email);

        CompanyProfile companyProfile=companyProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("CompanyProfile","user",user));

        Job job=jobRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Job","id",id) );

        Long jobOwnerId=job.getCompanyProfile().getId();

        Long currentLoggedUserCompany=companyProfile.getId();

        if(!jobOwnerId.equals(currentLoggedUserCompany)){
            throw new AccessDeniedException("You are not authorized to update the job");
        }

        job.setJobStatus(jobStatusUpdateDTO.getJobStatus());
        jobRepository.save(job);

        CompanySummaryDto companySummaryDto=modelMapper.map(job,CompanySummaryDto.class);

        JobResponseDto jobResponseDto=modelMapper.map(job,JobResponseDto.class);
        jobResponseDto.setCompany(companySummaryDto);

        return jobResponseDto;
    }
















}
