package se.experis.academy.diary_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.diary_app.model.Entry;
import se.experis.academy.diary_app.model.Response;
import se.experis.academy.diary_app.repository.EntryRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EntryController {

    @Autowired
    EntryRepository entryRepository;

    @GetMapping("/entries")
    public ResponseEntity<Response> getEntries() {
        Response response;
        HttpStatus status;
        List<Entry> entries = entryRepository.findByActiveTrue();
        if (entries != null && !entries.isEmpty()){
            response = new Response(entries, "SUCCESS");
            status = HttpStatus.OK;
        } else {
            response = new Response(null, "NO ENTRIES");
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(response, status);
    }

    @DeleteMapping("/entry/delete/{id}")
    public ResponseEntity<Response> deleteEntry(@PathVariable("id") long id) {
        Response response;
        HttpStatus status;
        if (entryRepository.existsById(id)) {
            Entry entry = entryRepository.findById(id);
            entry.setInactive();
            entryRepository.save(entry);
            response = new Response(null, "DELETED");
            status = HttpStatus.OK;
        } else {
            response = new Response(null, "COULDN'T DELETE");
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/entry/create")
    public ResponseEntity<Response> addEntry(@RequestBody Entry entry) {
        Entry createdEntry = entryRepository.save(entry);
        Response response = new Response(createdEntry, "CREATED");
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(response, status);
    }

    @PatchMapping("/entry/update")
    public ResponseEntity<Response> updateEntry(@RequestBody Entry entry) {
        Response response;
        HttpStatus httpStatus;
        if (entryRepository.existsById(entry.getId())) {
            Entry modifiedEntry = entryRepository.save(entry);
            response = new Response(modifiedEntry, "MODIFIED");
            httpStatus = HttpStatus.OK;
        } else {
            response = new Response(null, "ENTRY DOESN'T EXIST");
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(response, httpStatus);
    }
}
