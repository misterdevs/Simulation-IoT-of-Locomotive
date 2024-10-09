package com.mrdevs.sil_service.service.telegram;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mrdevs.sil_service.dto.SendSummary;
import com.mrdevs.sil_service.dto.SendSummaryStatusTotal;
import com.mrdevs.sil_service.model.postgresql.Subscriber;
import com.mrdevs.sil_service.repository.postgresql.SubscriberRepository;
import com.mrdevs.sil_service.service.locomotive.SummaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelegramService {
    private static final Logger logger = LoggerFactory.getLogger(TelegramService.class);

    private final SummaryService summaryService;
    private final SubscriberRepository subscriberRepository;

    public String createLatestLocomotiveSummaryMessage() {
        String message;
        // retrieve latest summary
        SendSummary summary = summaryService.getLatestSummary();

        // mapping to markdown
        String statusTotalMessage = "";
        for (SendSummaryStatusTotal status : summary.getStatus()) {
            statusTotalMessage += status.getStatusTitle() + " : " + status.getTotal() + "\n";
        }

        // create response
        message = "Locomotive Status\n\n"
                + //
                statusTotalMessage
                + //
                "Total Locomotive : " + summary.getTotalLocomotive() + "\n\n"
                + //
                "Last Updated at : " + summary.getCreatedAt();

        return message;
    }

    public String subscribeLocomotiveSummary(String chatId, String interval) {

        try {
            // retrieve subscriber
            Optional<Subscriber> isSubscribed = subscriberRepository.findByChatId(chatId);

            Subscriber subscriber = new Subscriber();

            // initial message
            String message = "Subscribe locomotive status every " + interval + " successful!";

            // check that user was subscribed or not
            if (isSubscribed.isPresent()) {
                if (isSubscribed.get().getIsDeleted() == false) {
                    // just update the interval
                    if (isSubscribed.get().getTimeframe().equalsIgnoreCase(interval)) {
                        throw new Exception("You're already subscribed");
                    }
                    message = "Update subscribe interval from " + isSubscribed.get().getTimeframe() + " to " + interval;
                    subscriber = isSubscribed.get();
                    subscriber.setTimeframe(interval);
                } else {
                    // handle user ever subscribed
                    subscriber = isSubscribed.get();
                    subscriber.setTimeframe(interval);
                    subscriber.setIsDeleted(false);
                }
            } else {
                // new subscriber
                subscriber = Subscriber.builder().chatId(chatId).timeframe(interval).build();
            }

            subscriberRepository.save(subscriber);

            logger.info(message);
            return message;
        } catch (Exception e) {
            logger.error("Subscribe locomotive status failed! : " + e.getMessage());
            return "Subscribe locomotive status failed! : " + e.getMessage();
        }

    }

    public String unsubscribeLocomotiveSummary(String chatId) {

        // initial message
        String message = "Unsubscribe locomotive status successful!";

        try {
            // retrieve subscriber
            Optional<Subscriber> isSubscribed = subscriberRepository.findByChatId(chatId);

            Subscriber subscriber = new Subscriber();

            // check that user ever subscribed or not
            if (isSubscribed.isPresent()) {

                // handle user already unsubscribe
                if (isSubscribed.get().getIsDeleted() == true) {
                    throw new Exception("You're already unsubscribed");

                } else {
                    // unsubscribe user
                    subscriber = isSubscribed.get();
                    subscriber.setIsDeleted(true);
                }
                // subscriber.setTimeframe(interval);
            } else {
                throw new Exception("You're not subscribed yet");
            }

            subscriberRepository.save(subscriber);

            logger.info(message);
            return message;
        } catch (Exception e) {
            logger.error("Unsubscribe locomotive status failed! : " + e.getMessage());
            return "Unsubscribe locomotive status failed! : " + e.getMessage();
        }

    }

    public List<Subscriber> getSubscribers(String interval) {
        List<Subscriber> subscribers = new ArrayList<Subscriber>();
        if (interval != null) {
            subscribers = subscriberRepository.findByTimeframeAndIsDeletedFalse(interval);
        }
        return subscribers;
    }

}
