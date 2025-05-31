package com.jobportal.Job_Portal.employer.dto;

import com.jobportal.Job_Portal.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyProfileResponseDto {

    private Long id;

    private String companyName;

    private String bio;

    private String location;

    private LocalDateTime updatedAt;

    private UserResponseDto user;

}
