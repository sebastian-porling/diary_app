package se.experis.academy.diary_app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.diary_app.model.Entry;
import se.experis.academy.diary_app.repository.EntryRepository;

import java.util.List;

@Controller()
public class WebController {

    @Autowired
    EntryRepository entryRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Entry> entries = entryRepository.findByActiveTrue();
        model.addAttribute("entries", entries);
        return "index";
    }

}

