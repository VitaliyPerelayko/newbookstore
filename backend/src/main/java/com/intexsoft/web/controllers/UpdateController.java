package com.intexsoft.web.controllers;

import com.intexsoft.dao.importdata.ImportData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/update")
public class UpdateController {

    private final ImportData importData;

    public UpdateController(@Qualifier("importDataJAXBImpl") ImportData importData) {
        this.importData = importData;
    }

    @GetMapping("/all")
    public HttpStatus updateDatabase() {
        importData.saveData();
        return HttpStatus.OK;
    }

    @GetMapping("/books")
    public HttpStatus updateDatabaseBooks() {
        importData.saveBooks();
        return HttpStatus.OK;
    }

    @GetMapping("/authors")
    public HttpStatus updateDatabaseAuthors() {
        importData.saveAuthors();
        return HttpStatus.OK;
    }

    @GetMapping("/publishers")
    public HttpStatus updateDatabasePublishers() {
        importData.savePublishers();
        return HttpStatus.OK;
    }
}
