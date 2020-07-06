package tech.tftinker.cis283.wordsearch;

import java.util.Arrays;
import java.util.Random;

public class WordData {
    public String angle, word;
    public int x,y;
    public char[][] charPlace;
    public boolean failed = false;
//    public static final String[] angles = {
//            "Horizontal", "Vertical", "Diagonal",
//            "Diagonal-Left", "Horizontal-Flip", "Vertical-Flip",
//            "Diagonal-Flip", "Diagonal-Left-Flip"};
    public static final String[] angles = {
        "Horizontal", "Vertical", "Diagonal",
        "Diagonal-Left"};
    public static int[] xS = new int[0];
    public static int[] yS = new int[0];

    public WordData(String word, int puzzleBoardSize){
        if (Main.debugShowEverythingFlag){
            System.out.println("Make WordData for " + word);
        }
        this.word = word;

        int rnd = new Random().nextInt(angles.length);
        angle = angles[rnd];

        charPlace = angleOut();

        boolean works = true;
        int trys = 0;
        do {
            this.x = new Random().nextInt((puzzleBoardSize-word.length())-1);
            this.y = new Random().nextInt((puzzleBoardSize-word.length())-1);

            trys++;

            if (contains(this.xS, this.x)){
                if (contains(this.yS, this.y)) {
                    works = false;
                }else {
                    works = true;
                }
            }else {
                works = true;
            }

            if (trys > 25){
                works = true;
                System.out.println("Error word: \"" + word + "\" did failed to make word data " + trys + " trys");
                failed = true;
                trys = 0;
            }
        }while (!works);

        if (!false) {
            this.xS = Arrays.copyOf(this.xS, this.xS.length + 1);
            this.xS[this.xS.length - 1] = this.x;

            this.yS = Arrays.copyOf(this.yS, this.yS.length + 1);
            this.yS[this.yS.length - 1] = this.y;
        }
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
        }

        return charPlaceOut;
    }
}
