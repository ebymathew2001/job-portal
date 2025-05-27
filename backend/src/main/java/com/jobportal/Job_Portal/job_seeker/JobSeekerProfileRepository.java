package com.jobportal.Job_Portal.job_seeker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSeekerProfileRepository  extends JpaRepository<JobSeekerProfile,Long> {
}
