package com.jobportal.Job_Portal.job_seeker;

import com.jobportal.Job_Portal.user.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobSeekerProfileRepository  extends JpaRepository<JobSeekerProfile,Long> {

    Optional<JobSeekerProfile> findByUser(User user);
    boolean existsByUser(User user);
    Optional<JobSeekerProfile> findByUserEmail(Email email);


}
