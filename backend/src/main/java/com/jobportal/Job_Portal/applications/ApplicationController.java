package com.jobportal.Job_Portal.applications;

import com.jobportal.Job_Portal.applications.dto.ApplicationRequestDto;
import com.jobportal.Job_Portal.applications.dto.ApplicationResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/job-Seeker/Applications")
    public ResponseEntity<ApplicationResponseDto> createApplication(@Valid @RequestBody ApplicationRequestDto applicationRequestDto, Principal principal){

        ApplicationResponseDto applicationResponseDto=applicationService.createApplication(applicationRequestDto,principal);

        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponseDto);

    }
}
