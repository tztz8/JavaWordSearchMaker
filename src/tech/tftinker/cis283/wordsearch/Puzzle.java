package tech.tftinker.cis283.wordsearch;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Puzzle extends Thread {
//public class Puzzle {
    public String[] words;
    public WordData[] wordsData;
    public char[] chars;
    public int puzzleBoardSize;
    public char[][] puzzleBoard;
    public char[][] puzzleKeyBoard;

    public boolean stopFlag = false;

    public Puzzle(String[] words, char[] chars, int puzzleBoardSize){
        this.words = words;
        this.chars = chars;
        this.puzzleBoardSize = puzzleBoardSize;
        this.puzzleBoard = new char[puzzleBoardSize][puzzleBoardSize];
        for (int x = 0; x < puzzleBoardSize; x++) {
            for (int y = 0; y < puzzleBoardSize; y++) {
                this.puzzleBoard[x][y] = Main.charBlank;
            }
        }
        this.puzzleKeyBoard = new char[puzzleBoardSize][puzzleBoardSize];
        for (int x = 0; x < puzzleBoardSize; x++) {
            for (int y = 0; y < puzzleBoardSize; y++) {
                this.puzzleKeyBoard[x][y] = Main.charBlank;
            }
        }
        this.wordsData = new WordData[words.length];
        for (int i = 0; i < words.length; i++) {
            wordsData[i] = new WordData(words[i],puzzleBoardSize);
        }
        if (Main.debugShowEverythingFlag){
            System.out.println("WordData x's and y's");
            System.out.println("xS ("+WordData.xS.length+"): " + Arrays.toString(WordData.xS));
            System.out.println("yS ("+WordData.yS.length+"): " + Arrays.toString(WordData.yS));
        }
    }

    @Override
    public void run(){
        for (int i = 0; (i < wordsData.length) && (!stopFlag); i++) {
            boolean works = true;
            int trys = 0;
            do {
                if (!works){
                    wordsData[i] = new WordData(wordsData[i].word, puzzleBoardSize);
                }
                trys++;
                if (checkWordPlace(wordsData[i], puzzleBoard) || (!wordsData[i].failed)){
                    puzzleBoard = placeWord(wordsData[i], puzzleBoard);
                    puzzleKeyBoard = placeWord(wordsData[i], puzzleBoard);
                    works = true;
                    trys = 0;
                } else{
                    works = false;
                }

                if (trys > 15 || wordsData[i].failed){
                    works = true;
                    System.out.println("Error word: \"" + wordsData[i].word + "\" did failed to be added to board with " + trys + " trys");
                    trys = 0;
                }
            }while (!works && (!stopFlag));

        }
        if (Main.debugShowEverythingFlag && (!stopFlag)){
            System.out.println("done");
        }

        if (stopFlag){
            System.out.println("Puzzle maker id: " + currentThread().getId() + " , stoped before being done");
        }
    }

    public String puzzleBoardAsString(){
        return puzzleBoardAsString(this.puzzleBoard);
    }

    public String puzzleBoardAsString(char[][] puzzleBoard){
        String returnString = "";
        for (int x = 0; x < puzzleBoardSize; x++) {
            for (int y = 0; y < puzzleBoardSize; y++) {
                returnString = returnString + puzzleBoard[x][y];
            }
            returnString = returnString + "\n";
        }
        return returnString;
    }

    public char[][] placeWord(WordData wordData, char[][] puzzleBoard) {
        for (int xPrime = 0; xPrime < wordData.word.length(); xPrime++) {
            for (int yPrime = 0; yPrime < wordData.word.length(); yPrime++) {
                if (puzzleBoard[wordData.x+xPrime][wordData.y+yPrime] == Main.charBlank) {
                    puzzleBoard[wordData.x + xPrime][wordData.y + yPrime] = wordData.charPlace[xPrime][yPrime];
                }
            }
        }
        return puzzleBoard;
    }

    public boolean checkWordPlace(WordData wordData, char[][] puzzleBoard){
        boolean works = true;
        for (int xPrime = 0; xPrime < wordData.word.length(); xPrime++) {
            for (int yPrime = 0; yPrime < wordData.word.length(); yPrime++) {
                if (puzzleBoard[wordData.x+xPrime][wordData.y+yPrime] != wordData.charPlace[xPrime][yPrime]){
                    if (puzzleBoard[wordData.x+xPrime][wordData.y+yPrime] != Main.charBlank){
                        works = false;
                        break;
                    }
                }
            }
        }
        return works;
    }
}
