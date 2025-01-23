package com.santoshkumawat.journalApp.scheduler;

import com.santoshkumawat.journalApp.cache.AppCache;
import com.santoshkumawat.journalApp.entity.JournalEntry;
import com.santoshkumawat.journalApp.entity.User;
import com.santoshkumawat.journalApp.repository.UserRepositoryImpl;
import com.santoshkumawat.journalApp.service.EmailService;
import com.santoshkumawat.journalApp.service.SentimentAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class Scheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void fetchUsersAndSendSaMail(){
        log.info("============================ Sending sentiment mail every minute ==================================");
        List<User> usersForSA = userRepository.getUsersForSA();
        for(User user: usersForSA){
            List<String> stringList = user.getJournalEntries().stream()
                    .filter(x -> x.getCreated().isAfter(LocalDateTime.now().minusDays(7)))
                    .map(JournalEntry::getContent)
                    .toList();

            String entry = String.join(" ", stringList);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
//            emailService.sendMail("santoshkumawat.sde@gmail.com", "Sentiment for last 7 days", sentiment);
        }
    }

    @Scheduled(cron = "0 0/5 * 1/1 * ?")
    public void clearAppCache(){
        log.info("================ Clearing app cache in every 5 minutes ==================");
        appCache.init();
    }

}
