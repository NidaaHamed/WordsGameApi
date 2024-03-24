package com.example.WordsGameApi.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/wordgrid")

public class WordsGridController {
    @PostMapping
    public String createWordGrid(int gridSize, List<String> words){
//         int gridSize;

        return "";
    }
}
