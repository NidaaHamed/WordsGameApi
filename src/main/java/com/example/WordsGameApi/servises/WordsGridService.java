package com.example.WordsGameApi.servises;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordsGridService {

    private enum Direction{
        VERTICAL,
        HORIZONTAL,
        DIAGONAL,
        VERTICAL_INVERSE,
        HORIZONTAL_INVERSE,
        DIAGONAL_INVERSE
    }
    private class Coordinate {
        int x;
        int y;
        Coordinate(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public char[][] generateGrid(int gridSize, List<String> words){
        List<Coordinate> coordinates = new ArrayList<>();
        char[][] content =  new char[gridSize][gridSize];
        for(int i=0 ; i<gridSize ; i++){
            for(int j=0 ; j<gridSize ; j++){
                coordinates.add(new Coordinate(i,j));
                content[i][j] = '_';
            }
        }
        for(String word : words) {
            Collections.shuffle(coordinates);
            for(Coordinate coordinate:coordinates){
                int x = coordinate.x;
                int y = coordinate.y;
                Direction selectedDirection = getDirectionforFit(content, word,coordinate);
                if(selectedDirection != null) {
                    switch (selectedDirection){
                        case DIAGONAL -> {
                            for(char c : word.toCharArray()){
                                content[x++][y++] = c;
                            }
                        }
                        case VERTICAL ->{
                            for(char c : word.toCharArray()){
                                content[x++][y] = c;
                            }
                        }
                        case HORIZONTAL ->{
                            for(char c : word.toCharArray()){
                                content[x][y++] = c;
                            }
                        }
                        case DIAGONAL_INVERSE -> {
                            for(char c : word.toCharArray()){
                                content[x--][y--] = c;
                            }
                        }
                        case VERTICAL_INVERSE ->{
                            for(char c : word.toCharArray()){
                                content[x--][y] = c;
                            }
                        }
                        case HORIZONTAL_INVERSE ->{
                            for(char c : word.toCharArray()){
                                content[x][y--] = c;
                            }
                        }
                    }
                    break;
                }
            }
        }
        randomFillGrid(content);
        return content;
    }

    private void randomFillGrid(char[][] content){
        int gridSize = content[0].length;
        String allCapLetter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(int i=0 ; i<gridSize ; i++){
            for(int j=0 ; j<gridSize ; j++){
                if(content[i][j]=='_'){
                    int randomIndex = ThreadLocalRandom.current().nextInt(0,allCapLetter.length());
                    content[i][j]=allCapLetter.charAt(randomIndex);
                }
            }
        }
    }
    private Direction getDirectionforFit(char[][] content, String word, Coordinate coordinate){
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for(Direction direction:directions){
            if(doesFit(content,word,coordinate,direction)){
                return direction;
            }
        }
        return null;
    }
    private boolean doesFit(char[][] content,String word, Coordinate coordinate, Direction direction){
        int gridSize = content[0].length;
        int wordLength = word.length();
        switch (direction){
            case HORIZONTAL ->{
                if (coordinate.y+wordLength>gridSize)return false;
                for(int i = 0 ; i < wordLength; i++){
                    char letter = content[coordinate.x][coordinate.y+i];
                    if(letter != '_' && letter != word.charAt(i)) return  false;
                }
            }
            case VERTICAL ->{
                if (coordinate.x+wordLength>gridSize)return false;
                for(int i = 0 ; i < wordLength; i++){
                    char letter = content[coordinate.x+i][coordinate.y];
                    if(letter != '_' && letter != word.charAt(i)) return  false;
                }
            }
            case DIAGONAL -> {
                if (coordinate.y+wordLength>gridSize || coordinate.x+word.length()>gridSize)return false;
                for(int i = 0 ; i < wordLength; i++){
                    char letter = content[coordinate.x+i][coordinate.y+i];
                    if(letter != '_' && letter != word.charAt(i)) return  false;
                }
            }
            case HORIZONTAL_INVERSE ->{
                if (coordinate.y < wordLength)return false;
                for(int i = 0 ; i < wordLength; i++){
                    char letter = content[coordinate.x][coordinate.y-i];
                    if(letter != '_' && letter != word.charAt(i)) return  false;
                }
            }
            case VERTICAL_INVERSE ->{
                if (coordinate.x < wordLength)return false;
                for(int i = 0 ; i < wordLength; i++){
                    char letter = content[coordinate.x-i][coordinate.y];
                    if(letter != '_' && letter != word.charAt(i)) return  false;
                }
            }
            case DIAGONAL_INVERSE -> {
                if (coordinate.y < wordLength || coordinate.x < word.length())return false;
                for(int i = 0 ; i < wordLength; i++){
                    char letter = content[coordinate.x-i][coordinate.y-i];
                    if(letter != '_' && letter != word.charAt(i)) return  false;
                }
            }
        }
        return true;
    }

}
