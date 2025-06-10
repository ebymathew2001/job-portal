package com.jobportal.Job_Portal.jobs;

import com.jobportal.Job_Portal.employer.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

   List<Job> findByJobStatus(JobStatus jobStatus);

   List<Job> findByCompanyProfile(CompanyProfile companyProfile);
}
