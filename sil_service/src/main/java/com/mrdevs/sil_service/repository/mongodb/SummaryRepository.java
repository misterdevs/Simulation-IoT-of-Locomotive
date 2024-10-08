package com.mrdevs.sil_service.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mrdevs.sil_service.model.mongodb.Summary;

@Repository
public interface SummaryRepository extends MongoRepository<Summary, String> {

}
