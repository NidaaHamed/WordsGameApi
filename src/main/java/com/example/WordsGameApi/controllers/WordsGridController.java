package com.example.WordsGameApi.controllers;

import com.example.WordsGameApi.servises.WordsGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController("/")

public class WordsGridController {
    @Autowired
    WordsGridService wordsGridService;
    @GetMapping("/wordgrid")
    public String createWordGrid(@RequestParam int gridSize,@RequestParam String wordsList){
        List<String> words = Arrays.asList(wordsList.split("-"));
        char[][] grid = wordsGridService.generateGrid(gridSize,words);
        String gridToString = "";
        for(int i=0 ; i<gridSize ; i++){
            for(int j=0 ; j<gridSize ; j++){
                gridToString += grid[i][j] + " ";
            }
            gridToString += "\r\n";
        }
        return gridToString;
    }
}
