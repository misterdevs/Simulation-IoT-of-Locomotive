package com.mrdevs.sil_service.repository.postgresql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrdevs.sil_service.model.postgresql.Locomotive;

@Repository
public interface LocomotiveRepository extends JpaRepository<Locomotive, String> {

    long countByStatusId(Long statusId);

}
