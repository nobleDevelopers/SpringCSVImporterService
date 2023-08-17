package com.noble.developers.controller;

import com.noble.developers.dto.CSVDataDto;
import com.noble.developers.service.CSVService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/csv")

public class CSVController {

    private final CSVService fileService;

    public CSVController(CSVService fileService) {
        this.fileService = fileService;
    }

    /*
        Upload file, Parse, Map to Model and Save into Database
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        fileService.save(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Uploaded the file successfully: " + file.getOriginalFilename());
    }

    /*
    Fetch all data
     */
    @GetMapping("")
    public ResponseEntity<List<CSVDataDto>> getAllData() {
        List<CSVDataDto> dataModelList = fileService.getAllModels();
        return new ResponseEntity<>(dataModelList, HttpStatus.OK);
    }
    /*
    Fetch by code
    */
    @GetMapping("/{code}")
    public ResponseEntity<CSVDataDto> fetchByCode(@PathVariable("code") String code) {
        return new ResponseEntity<>(fileService.fetchByCode(code), HttpStatus.OK);
    }

    /*
    Delete all data
    */
    @DeleteMapping("")
    public ResponseEntity<String> deleteAll() {
        fileService.deleteAll();
        return new ResponseEntity<>("All records are deleted", HttpStatus.OK);
    }

}
