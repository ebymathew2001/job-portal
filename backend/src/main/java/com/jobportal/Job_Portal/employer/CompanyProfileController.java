package com.jobportal.Job_Portal.employer;

import com.jobportal.Job_Portal.employer.dto.CompanyProfileRequestDto;
import com.jobportal.Job_Portal.employer.dto.CompanyProfileResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employer")
public class CompanyProfileController {

    private final CompanyProfileService companyProfileService;



    @PostMapping("company-profiles")
    public ResponseEntity<CompanyProfileResponseDto> createProfile(@Valid @RequestBody CompanyProfileRequestDto requestDto ,Principal principal) {


        CompanyProfileResponseDto responseDto = companyProfileService.createProfile(requestDto,principal);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("company-profile/me")
    public ResponseEntity<CompanyProfileResponseDto> viewProfile(Principal principal){
        CompanyProfileResponseDto responseDto= companyProfileService.viewProfile(principal);
        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("company-profile/me")
    public ResponseEntity<CompanyProfileResponseDto> updateProfile(@Valid @RequestBody CompanyProfileRequestDto requestDto,Principal principal){
        CompanyProfileResponseDto responseDto=companyProfileService.updateProfile(requestDto,principal);
        return ResponseEntity.ok(responseDto);
    }











}
