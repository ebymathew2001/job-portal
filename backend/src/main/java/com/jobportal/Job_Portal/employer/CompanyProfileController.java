package com.jobportal.Job_Portal.employer;

import com.jobportal.Job_Portal.employer.dto.CompanyProfileRequestDto;
import com.jobportal.Job_Portal.employer.dto.CompanyProfileResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/employer")
public class CompanyProfileController {

    private CompanyProfileService companyProfileService;

    public CompanyProfileController(CompanyProfileService companyProfileService){

        this.companyProfileService=companyProfileService;
    }


    @PostMapping("profile/create")
    public ResponseEntity<CompanyProfileResponseDto> createProfile(@Valid @RequestBody CompanyProfileRequestDto requestDto ,Principal principal) {

        String email=principal.getName();
        CompanyProfileResponseDto responseDto = companyProfileService.createProfile(requestDto,email);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }







}
