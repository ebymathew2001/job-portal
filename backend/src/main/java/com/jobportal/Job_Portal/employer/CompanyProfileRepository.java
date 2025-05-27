package com.jobportal.Job_Portal.employer;

import com.jobportal.Job_Portal.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile,Long> {

    boolean existsByUser(User user);
}
