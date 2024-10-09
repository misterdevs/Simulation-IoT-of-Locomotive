package com.mrdevs.sil_service.service.telegram;

import java.util.Optional;

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
            Optional<Subscriber> isSubscribed = subscriberRepository.findByChatIdAndIsDeletedFalse(chatId);

            Subscriber subscriber = new Subscriber();

            String message = "Subscribe locomotive summary every " + interval + " successful!";

            if (isSubscribed.isPresent()) {
                // just update the interval
                if (isSubscribed.get().getTimeframe().equalsIgnoreCase(interval)) {
                    throw new Exception("You're already subscribed");
                }
                message = "Update subscribe interval from " + isSubscribed.get().getTimeframe() + " to " + interval;
                subscriber = isSubscribed.get();
                subscriber.setTimeframe(interval);
            } else {
                // new subscriber
                subscriber = Subscriber.builder().chatId(chatId).timeframe(interval).build();
            }

            subscriberRepository.save(subscriber);

            return message;
        } catch (Exception e) {
            return "Subscribe locomotive summary failed! : " + e.getMessage();
        }

    }

}
