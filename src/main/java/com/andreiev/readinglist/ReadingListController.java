package com.andreiev.readinglist;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@ConfigurationProperties(prefix="amazon")  // inject with properties
public class ReadingListController {

    @Getter
    @Setter
    private String associateId;
    private ReadingListRepository readingListRepository;

    @Autowired
    public ReadingListController( ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }

    @GetMapping("/{reader}")
    public String readersBooks(@PathVariable("reader") String reader, Model model) {
        var readingList = readingListRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
            model.addAttribute("reader", reader);
            model.addAttribute("amazonID", associateId);
        }
        return "readingList";
    }

    @PostMapping("/{reader}")
    public String addToReadingList(@PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/{reader}";
    }
}
