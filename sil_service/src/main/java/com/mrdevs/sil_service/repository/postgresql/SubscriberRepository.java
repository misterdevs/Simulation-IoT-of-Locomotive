package com.mrdevs.sil_service.repository.postgresql;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrdevs.sil_service.model.postgresql.Subscriber;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {

    Optional<Subscriber> findByChatIdAndIsDeletedFalse(String chatId);

    Optional<Subscriber> findByChatId(String chatId);

    List<Subscriber> findAllByIsDeletedFalse();

    List<Subscriber> findByTimeframeAndIsDeletedFalse(String timeframe);

}
