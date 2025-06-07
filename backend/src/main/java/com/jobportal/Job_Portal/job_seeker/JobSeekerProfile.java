package com.jobportal.Job_Portal.job_seeker;


import com.jobportal.Job_Portal.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="job_seeker_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobSeekerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id" ,nullable = false ,unique = true)
    private User user;

    @Column(name="full_name",nullable = false)
    private String fullName;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(columnDefinition = "TEXT" )
    private String skills;

    @Column(columnDefinition = "TEXT")
    private String experience;

    @Column(nullable = true)
    private String education;

    @Column(name ="resume_url")
    private String resumeUrl;

    @Column(nullable = false)
    private String location;

    @Column(name="created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}


