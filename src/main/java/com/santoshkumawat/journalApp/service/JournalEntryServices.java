package com.santoshkumawat.journalApp.service;

import com.santoshkumawat.journalApp.entity.JournalEntry;
import com.santoshkumawat.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServices {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public JournalEntry createJournal(JournalEntry journalEntry){
        return journalEntryRepository.save(journalEntry);
    }

    public void updateJournal(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public void deleteJournalById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }
}
