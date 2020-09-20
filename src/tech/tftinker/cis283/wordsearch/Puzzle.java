package tech.tftinker.cis283.wordsearch;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Puzzle{
//public class Puzzle {
    public String[] words;
    public WordData[] wordsData;
    public char[] chars;
    public int puzzleBoardSize;
    public char[][] puzzleBoard;
    public char[][] puzzleKeyBoard;
    public static int MAXTRYS = 300;

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

        int j = 0;
        for (int x = 0; x < puzzleBoardSize; x++) {
            for (int y = 0; y < puzzleBoardSize; y++) {
                WordData.notUsedPoints = Arrays.copyOf(WordData.notUsedPoints, WordData.notUsedPoints.length + 1);
                WordData.notUsedPoints[j] = new Point(x,y);
                j++;
            }
        }

        this.wordsData = new WordData[words.length];
        for (int i = 0; i < words.length; i++) {
            wordsData[i] = new WordData(words[i],puzzleBoardSize);
        }
        if (Main.debugShowEverythingFlag){
            System.out.print(ConsoleColors.BLUE);
            System.out.println("WordData x's and y's");
            System.out.println("PuzzleBoard ID: " + puzzleBoard.toString());
            System.out.println("Key PuzzleBoard ID: " + puzzleKeyBoard.toString());
            System.out.print(ConsoleColors.RESET);
        }
    }

    public void makepuzzle(){
        if (Main.debugShowEverythingFlag){
            System.out.print(ConsoleColors.BLUE);
            System.out.println("PuzzleBoard ID: " + puzzleBoard.toString());
            System.out.println("Key PuzzleBoard ID: " + puzzleKeyBoard.toString());
            System.out.print(ConsoleColors.RESET);
        }

        for (int i = 0; i < wordsData.length; i++) {
            int j;
            for (j = 0; j < MAXTRYS; j++) {
                if(checkWordPlace(wordsData[i], puzzleBoard)){
                    puzzleBoard = placeWord(wordsData[i], puzzleBoard);
                    puzzleKeyBoard = placeWord(wordsData[i], puzzleKeyBoard);

                    if (Main.debugShowEverythingFlag){
                        String message = ConsoleColors.CYAN + "Used " + (j+1) + " trys to fit \"" + wordsData[i].word + "\"" + ConsoleColors.RESET;
                        System.out.println(message);
                        wordsData[i].wordsDebugData.println(message);
                    }

                    break;

                }else{

                    wordsData[i] = new WordData(wordsData[i].word, puzzleBoardSize, wordsData[i].wordsDebugData);

                    if (Main.debugShowEverythingFlag) {
                        String message = ConsoleColors.CYAN + "Reset " + wordsData[i].word + " data" + ConsoleColors.RESET;
                        System.out.println(message);
                        wordsData[i].wordsDebugData.println(message);
                    }

                }
            }
            if (j == MAXTRYS || wordsData[i].failed){
                String message = ConsoleColors.RED_BOLD + "Error word: \"" + wordsData[i].word + "\" did failed to be added to board with " + j + " trys" + ConsoleColors.RESET;
                System.out.println(message);
                wordsData[i].wordsDebugData.println(message);
            }
        }



        if (Main.debugShowEverythingFlag && (!stopFlag)){
            System.out.print(ConsoleColors.BLUE);
            System.out.println("done inserting words");
            System.out.print(ConsoleColors.RESET);
        }

        if (!stopFlag){
            puzzleBoard = insertRandChar(puzzleBoard);
            puzzleKeyBoard = insertSpaceChar(puzzleKeyBoard);
            if (Main.debugShowEverythingFlag){
                System.out.print(ConsoleColors.BLUE);
                System.out.println("done inserting random chars");
                System.out.println("PuzzleBoard ID: " + puzzleBoard.toString());
                System.out.println("Key PuzzleBoard ID: " + puzzleKeyBoard.toString());
                System.out.print(ConsoleColors.RESET);
            }
        }

        if (Main.debugShowEverythingFlag){
            for (int i = 0; i < this.wordsData.length; i++) {
                System.out.println("");
                this.wordsData[i].wordsDebugData.printData();
            }
            System.out.println("");
        }
    }

    public String puzzleBoardAsString(){
        return puzzleBoardAsString(this.puzzleBoard);
    }
    public String keyPuzzleBoardAsString() {
        return puzzleBoardAsString(this.puzzleKeyBoard);
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

    public String[] puzzleBoardAsStringArray(){
        return puzzleBoardAsStringArray(this.puzzleBoard);
    }
    public String[] keyPuzzleBoardAsStringArray() {
        return puzzleBoardAsStringArray(this.puzzleKeyBoard);
    }

    public String[] puzzleBoardAsStringArray(char[][] puzzleBoard){
        String[] returnString = new String[puzzleBoardSize];
        for (int x = 0; x < puzzleBoardSize; x++) {
            for (int y = 0; y < puzzleBoardSize; y++) {
                returnString[x] = returnString[x] + puzzleBoard[x][y];
            }
            //returnString = returnString + "\n";
        }
        return returnString;
    }

    public String line1puzzleBoardAsString(){
        return line1puzzleBoardAsString(puzzleBoard);
    }

    public String line1puzzleBoardAsString(char[][] puzzleBoard){
        String returnString = "";
        int x = 0;
        for (int y = 0; y < puzzleBoardSize; y++) {
            returnString = returnString + puzzleBoard[x][y] + " ";
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

    public char[][] insertRandChar(char[][] puzzleBoard) {
        for (int x = 0; x < puzzleBoard.length; x++) {
            for (int y = 0; y < puzzleBoard[x].length; y++) {
                if (puzzleBoard[x][y] == Main.charBlank) {
                    int rnd = new Random().nextInt(chars.length);
                    puzzleBoard[x][y] = chars[rnd];
                }
            }
        }
        return puzzleBoard;
    }

    public char[][] insertSpaceChar(char[][] puzzleBoard) {
        for (int x = 0; x < puzzleBoard.length; x++) {
            for (int y = 0; y < puzzleBoard[x].length; y++) {
                if (puzzleBoard[x][y] == Main.charBlank) {
                    puzzleBoard[x][y] = ' ';
                }
            }
        }
        return puzzleBoard;
    }

    public boolean checkWordPlace(WordData wordData, char[][] puzzleBoard){
        boolean works = true;
        for (int xPrime = 0; xPrime < wordData.word.length(); xPrime++) {
            for (int yPrime = 0; yPrime < wordData.word.length(); yPrime++) {
                int x = wordData.x+xPrime;
                int y = wordData.y+yPrime;
                if (puzzleBoard[x][y] != wordData.charPlace[xPrime][yPrime]){
                    if (puzzleBoard[x][y] != Main.charBlank){
                        works = false;
                        wordData.wordsDebugData.println(ConsoleColors.GREEN + ConsoleColors.RED_BACKGROUND + "Failed to place " + wordData.word + ConsoleColors.RESET);
                        wordData.wordsDebugData.println(ConsoleColors.GREEN + "[\'" +
                                puzzleBoard[wordData.x+xPrime][wordData.y+yPrime] + "\',\'" +
                                wordData.charPlace[xPrime][yPrime] + "\'] at [" +
                                xPrime + "," + yPrime + "] in word and at [" +
                                x + "," + y + "] in puzzle"+ ConsoleColors.RESET);
                        break;
                    }else {
                        wordData.wordsDebugData.println(ConsoleColors.GREEN + "It was a Blank char" + ConsoleColors.RESET);
                    }
                }else {
                    wordData.wordsDebugData.println(ConsoleColors.GREEN + "It was the same char" + ConsoleColors.RESET);
                }
                wordData.wordsDebugData.println(ConsoleColors.GREEN + "[\'" +
                        puzzleBoard[wordData.x+xPrime][wordData.y+yPrime] + "\',\'" +
                        wordData.charPlace[xPrime][yPrime] + "\']" + ConsoleColors.RESET);
            }
            if (!works){
                break;
            }
        }
        return works;
    }
}
