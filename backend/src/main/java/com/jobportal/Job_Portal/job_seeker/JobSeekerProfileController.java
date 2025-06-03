package com.jobportal.Job_Portal.job_seeker;

import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileRequestDto;
import com.jobportal.Job_Portal.job_seeker.dto.JobSeekerProfileResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job-seeker")
public class JobSeekerProfileController {

    private final JobSeekerProfileService jobSeekerProfileService;



    @PostMapping(value = "/jobseeker-profiles",consumes = {"multipart/form-data"})
    public ResponseEntity<JobSeekerProfileResponseDto> createProfile(@RequestPart("profile") @Valid JobSeekerProfileRequestDto requestDto, @RequestPart("resume") MultipartFile resumeFile ,Principal principal){

        JobSeekerProfileResponseDto responseDto=jobSeekerProfileService.createProfile(requestDto,resumeFile,principal);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }


}

