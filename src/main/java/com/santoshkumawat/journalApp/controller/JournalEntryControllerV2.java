package com.santoshkumawat.journalApp.controller;

import com.santoshkumawat.journalApp.entity.JournalEntry;
import com.santoshkumawat.journalApp.service.JournalEntryServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journals")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryServices journalEntryServices;

    @GetMapping
    public List<JournalEntry> getAllJournals() {
        return journalEntryServices.getAll();
    }

    @GetMapping("id/{id}")
    private JournalEntry findById(@PathVariable ObjectId id) {
        return journalEntryServices.findById(id).orElse(null);
    }

    @PostMapping
    private JournalEntry createJournal(@RequestBody JournalEntry journalEntry) {
        journalEntry.setCreated(LocalDateTime.now());
        journalEntry.setModified(LocalDateTime.now());
        return journalEntryServices.createJournal(journalEntry);
    }

    @PutMapping("id/{id}")
    private JournalEntry updateJournal(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryServices.findById(id).orElse(null);
        if (old != null) {
            old.setModified(LocalDateTime.now());
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
        }
        journalEntryServices.updateJournal(old);
        return old;
    }

    @DeleteMapping("id/{id}")
    private Boolean deleteJournal(@PathVariable ObjectId id) {
        journalEntryServices.deleteJournalById(id);
        return true;
    }

}
