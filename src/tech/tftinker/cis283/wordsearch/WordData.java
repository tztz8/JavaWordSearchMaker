package tech.tftinker.cis283.wordsearch;

import com.sun.source.tree.WhileLoopTree;
import tech.tftinker.cis283.wordsearch.debug.WordsDebugData;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class WordData {
    public String angle, word;
    public int x,y;
    public char[][] charPlace;
    public boolean failed = false;
    public WordsDebugData wordsDebugData;
    public static final String[] angles = {
        "Horizontal", "Vertical", "Diagonal", "Diagonal-Left",
        "Horizontal-Flip", "Vertical-Flip", "Diagonal-Flip", "Diagonal-Left-Flip"};
    public static Point[] notUsedPoints = new Point[0];

    public WordData(String word, int puzzleBoardSize){

        this.wordsDebugData = new WordsDebugData(word);
        wordsDebugData.print(ConsoleColors.BLUE);
        wordsDebugData.print("Make WordData for " + word);
        wordsDebugData.println(ConsoleColors.RESET);

        this.word = word;

        int rnd = new Random().nextInt(angles.length);
        angle = angles[rnd];

        charPlace = angleOut();

        boolean works = false;
        while (!works){
            int rndPoint = new Random().nextInt(this.notUsedPoints.length-1);

            if((Main.puzzleSize-word.length()) > this.notUsedPoints[rndPoint].x){
                if((Main.puzzleSize-word.length()) > this.notUsedPoints[rndPoint].y){
                    this.x = this.notUsedPoints[rndPoint].x;
                    this.y = this.notUsedPoints[rndPoint].y;
                    works = true;
                }else {
                    works = false;
                }
            }else {
                works = false;
            }

            if (works){
                this.notUsedPoints = Main.removeTheElement(this.notUsedPoints, rndPoint);
            }
        }


        for (int x = 0; x < charPlace.length; x++) {
            wordsDebugData.print(ConsoleColors.BLUE + ConsoleColors.GREEN_BACKGROUND);
            for (int y = 0; y < charPlace[x].length; y++) {
                wordsDebugData.print(charPlace[x][y] + " ");
            }
            wordsDebugData.println(ConsoleColors.RESET);
        }
    }

    public WordData(String word, int puzzleBoardSize, WordsDebugData wordsDebugData){
        this(word, puzzleBoardSize);
        this.wordsDebugData = wordsDebugData;
    }

    public static boolean contains(final int[] array, final int v) {

        boolean result = false;

        for(int i : array){
            if(i == v){
                result = true;
                break;
            }
        }

        return result;
    }

    public char[][] angleOut(){
        return angleOut(this.word, angle);
    }

    public char[][] angleOut(String word, String angle){
        char[][] charPlaceOut = new char[word.length()][word.length()];
        for (int xPrime = 0; xPrime < word.length(); xPrime++) {
            for (int yPrime = 0; yPrime < word.length(); yPrime++) {
                charPlaceOut[xPrime][yPrime] = Main.charBlank;
            }
        }

        if (angle == "Horizontal"){
            for (int i = 0; i < word.length(); i++) {
                charPlaceOut[i][0] = word.charAt(i);
            }
        }else if (angle == "Vertical") {
            for (int i = 0; i < word.length(); i++) {
                charPlaceOut[0][i] = word.charAt(i);
            }
        }else if (angle == "Diagonal") {
            for (int i = 0; i < word.length(); i++) {
                charPlaceOut[i][i] = word.charAt(i);
            }
        }else if (angle == "Diagonal-Left"){
            for (int i = 0; i < word.length(); i++) {
                charPlaceOut[i][word.length()-(i+1)] = word.charAt(i);
            }
        }else if (angle == "Horizontal-Flip"){
            charPlaceOut = angleOut(new StringBuilder(word).reverse().toString(), "Horizontal");
        }else if (angle == "Vertical-Flip"){
            charPlaceOut = angleOut(new StringBuilder(word).reverse().toString(), "Vertical");
        }else if (angle == "Diagonal-Flip"){
            charPlaceOut = angleOut(new StringBuilder(word).reverse().toString(), "Diagonal");
        }else if (angle == "Diagonal-Left-Flip"){
            charPlaceOut = angleOut(new StringBuilder(word).reverse().toString(), "Diagonal-Left");
        }else {
            System.out.println("Error");
        }

        return charPlaceOut;
    }
}
//public static final String[] angles = {
//        "Horizontal", "Vertical", "Diagonal", "Diagonal-Left",
//        "Horizontal-Flip", "Vertical-Flip", "Diagonal-Flip", "Diagonal-Left-Flip"};