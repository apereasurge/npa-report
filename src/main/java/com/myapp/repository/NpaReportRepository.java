package com.myapp.repository;

import com.myapp.domain.NpaReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NpaReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NpaReportRepository extends JpaRepository<NpaReport, Long> {}
