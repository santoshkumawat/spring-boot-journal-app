package com.santoshkumawat.journalApp.service;

import com.santoshkumawat.journalApp.entity.JournalEntry;
import com.santoshkumawat.journalApp.entity.User;
import com.santoshkumawat.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void saveJournal(JournalEntry journalEntry, String userName){
        try {
            Optional<User> byUserName = userService.findByUserName(userName);
            if (byUserName.isPresent()){
                journalEntry.setCreated(LocalDateTime.now());
                journalEntry.setModified(LocalDateTime.now());
                JournalEntry saved = journalEntryRepository.save(journalEntry);
                byUserName.get().getJournalEntries().add(saved);
                userService.saveUser(byUserName.get());
            }
        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the entry: ", e);
        }
    }

    public void saveJournal(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    @Transactional
    public boolean deleteJournalById(ObjectId journalId, String userName){
        boolean removed = false;
        try {
            Optional<User> byUserName = userService.findByUserName(userName);
            if (byUserName.isPresent()) {
                removed = byUserName.get().getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(journalId));
                if (removed) {
                    userService.saveUser(byUserName.get());
                    journalEntryRepository.deleteById(journalId);
                }
            }
        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entry: ", e);
        }
        return removed;
    }
}
