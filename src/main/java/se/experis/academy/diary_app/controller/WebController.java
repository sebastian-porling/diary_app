package se.experis.academy.diary_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Class that handles all the views
 */
@Controller
public class WebController {


    /**
     * Gets contacts and returns them to index.html
     * @param model
     * @return to index.html with list of contacts
     */
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
}
