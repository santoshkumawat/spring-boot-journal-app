package com.santoshkumawat.journalApp.controller;

import com.santoshkumawat.journalApp.entity.JournalEntry;
import com.santoshkumawat.journalApp.entity.User;
import com.santoshkumawat.journalApp.service.JournalEntryService;
import com.santoshkumawat.journalApp.service.UserService;
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
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalsForUser(@PathVariable String userName) {
        Optional<User> byUserName = userService.findByUserName(userName);
        if(byUserName.isPresent()){
            List<JournalEntry> all = byUserName.get().getJournalEntries();
            if (all != null && !all.isEmpty()) {
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
            return new ResponseEntity<>("Journal entries not found for the user: " + userName, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("userId/{userId}")
    private ResponseEntity<?> findByUserId(@PathVariable ObjectId userId) {
        Optional<User> byId = userService.findById(userId);
        if(byId.isPresent()){
            List<JournalEntry> all = byId.get().getJournalEntries();
            if (all != null && !all.isEmpty()) {
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
            return new ResponseEntity<>("Journal entries not found for the user: " + byId.get().getUserName(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    private ResponseEntity<JournalEntry> createJournal(@PathVariable String userName, @RequestBody JournalEntry journalEntry) {
        try {
            journalEntryService.saveJournal(journalEntry, userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("id/{userName}/{id}")
    private ResponseEntity<?> updateJournal(@PathVariable ObjectId id, @PathVariable String userName, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if (old != null) {
            old.setModified(LocalDateTime.now());
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveJournal(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{userName}/{journalId}")
    private ResponseEntity<?> deleteJournal(@PathVariable String userName, @PathVariable ObjectId journalId) {
        journalEntryService.deleteJournalById(journalId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
