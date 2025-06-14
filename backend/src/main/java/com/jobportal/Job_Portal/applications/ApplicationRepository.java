package com.jobportal.Job_Portal.applications;


import com.jobportal.Job_Portal.job_seeker.JobSeekerProfile;
import com.jobportal.Job_Portal.jobs.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {

    boolean existsByJobAndJobSeeker(Job job, JobSeekerProfile jobSeekerProfile);

    List<Application> findByJobSeeker(JobSeekerProfile jobSeekerProfile);

    List<Application> findByJob(Job job);
}
