package se.experis.academy.diary_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.diary_app.model.Entry;
import se.experis.academy.diary_app.repository.EntryRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EntryController {
    @Autowired
    EntryRepository entryRepository;


    @GetMapping("/entries")
    public List<Entry> getEntries() {
        return entryRepository.findByActiveTrue();
    }

    @DeleteMapping("/entry/delete/{id}")
    public void deleteEntry(@PathVariable("id") int id) {
        if (entryRepository.existsById(id)) {
            Entry entry = entryRepository.findById(id);
            entry.setInactive();
            entryRepository.save(entry);
        }
    }

    @PostMapping("/entry/create")
    public Entry addEntry(@RequestBody Entry entry) {
        return entryRepository.save(entry);
    }

    @PostMapping("/entry/update")
    public Entry updateEntry(@RequestBody Entry entry) {
        if (entryRepository.existsById((int) entry.getId())) return entryRepository.save(entry);
        return new Entry();
    }

    @PatchMapping("/entries")
    public Entry editEntry(@RequestBody Entry entry){
        if(entry.getText() != null){
            getEntries().text = entry.text;
        }
        if(entry.getDate() != null){
            getEntries().date = entry.date;
        }
        if(entry.getImg() != null){
            getEntries().img = entry.img;
        }
        return entryRepository.save(entry);

    }
}
