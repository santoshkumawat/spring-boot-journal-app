package com.santoshkumawat.journalApp.controller;

import com.santoshkumawat.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journals")
public class JournalEntryController {

//    private final Map<Long, JournalEntry> journalEntries = new HashMap<>();
//
//    @GetMapping
//    public List<JournalEntry> getAllJournals(){
//        return new ArrayList<>(journalEntries.values());
//    }
//
//    @GetMapping("id/{id}")
//    private JournalEntry findById(@PathVariable long id){
//        return journalEntries.get(id);
//    }
//
//    @PostMapping
//    private Boolean createJournal(@RequestBody JournalEntry journalEntry){
//        journalEntries.put(journalEntry.getId(), journalEntry);
//        return true;
//    }
//
//    @PutMapping("id/{id}")
//    private Boolean updateJournal(@PathVariable long id, @RequestBody JournalEntry journalEntry){
//        journalEntries.put(id, journalEntry);
//        return true;
//    }
//
//    @DeleteMapping("id/{id}")
//    private Boolean deleteJournal(@PathVariable long id){
//        journalEntries.remove(id);
//        return true;
//    }

}
