package com.jobportal.Job_Portal.applications;


import com.jobportal.Job_Portal.job_seeker.JobSeekerProfile;
import com.jobportal.Job_Portal.jobs.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {

    boolean ExistByJobSeeker(Job job, JobSeekerProfile jobSeekerProfile);


}
