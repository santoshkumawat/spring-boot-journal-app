package com.santoshkumawat.journalApp.controller;

import com.santoshkumawat.journalApp.entity.JournalEntry;
import com.santoshkumawat.journalApp.service.JournalEntryServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAllJournals() {
        List<JournalEntry> all = journalEntryServices.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{id}")
    private ResponseEntity<JournalEntry> findById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryServices.findById(id);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    private ResponseEntity<JournalEntry> createJournal(@RequestBody JournalEntry journalEntry) {
        try {
            journalEntry.setCreated(LocalDateTime.now());
            journalEntry.setModified(LocalDateTime.now());
            journalEntryServices.createJournal(journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("id/{id}")
    private ResponseEntity<?> updateJournal(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryServices.findById(id).orElse(null);
        if (old != null) {
            old.setModified(LocalDateTime.now());
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryServices.updateJournal(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{id}")
    private ResponseEntity<?> deleteJournal(@PathVariable ObjectId id) {
        journalEntryServices.deleteJournalById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
