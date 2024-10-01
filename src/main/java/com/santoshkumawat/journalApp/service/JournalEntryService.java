package com.santoshkumawat.journalApp.service;

import com.santoshkumawat.journalApp.entity.JournalEntry;
import com.santoshkumawat.journalApp.entity.User;
import com.santoshkumawat.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void saveJournal(JournalEntry journalEntry, String userName){
        Optional<User> byUserName = userService.findByUserName(userName);
        if (byUserName.isPresent()){
            journalEntry.setCreated(LocalDateTime.now());
            journalEntry.setModified(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            byUserName.get().getJournalEntries().add(saved);
            userService.saveUser(byUserName.get());
        }
    }

    public void saveJournal(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public void deleteJournalById(ObjectId journalId, String userName){
        Optional<User> byUserName = userService.findByUserName(userName);
        if(byUserName.isPresent()){
            byUserName.get().getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(journalId));
            userService.saveUser(byUserName.get());
            journalEntryRepository.deleteById(journalId);
        }
    }
}
