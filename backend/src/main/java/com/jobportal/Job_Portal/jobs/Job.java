package com.jobportal.Job_Portal.jobs;

import com.jobportal.Job_Portal.employer.CompanyProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String description;

    @Column(name ="job_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(name = "work_mode",nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkMode workMode;

    @Column(nullable = true)
    private String salary;

    @Column(name="created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="job_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;


    @ManyToOne
    @JoinColumn(name = "company_profile_id",nullable = false)
    private CompanyProfile companyProfile;




}
