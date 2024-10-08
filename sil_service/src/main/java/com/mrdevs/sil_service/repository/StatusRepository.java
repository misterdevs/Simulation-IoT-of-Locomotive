package com.mrdevs.sil_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrdevs.sil_service.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

}
