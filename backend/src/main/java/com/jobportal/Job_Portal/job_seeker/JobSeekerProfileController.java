package com.jobportal.Job_Portal.job_seeker;

import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileRequestDto;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job-seeker")
public class JobSeekerProfileController {

    private final JobSeekerProfileService jobSeekerProfileService;



    @PostMapping("jobseeker-profiles")
    public ResponseEntity<JobSeekerProfileResponseDto> createProfile(@Valid @RequestBody JobSeekerProfileRequestDto requestDto, Principal principal){

        JobSeekerProfileResponseDto responseDto=jobSeekerProfileService.createProfile(requestDto,principal);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    }


}

