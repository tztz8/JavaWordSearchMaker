package tech.tftinker.cis283.wordsearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author      Timbre Freeman
 * @assignment  Word Search - Remade in Java for Java (Original Ruby)
 * @date        06/13/2020 (Original made in 03/03/2020 for Ruby)
 * @classroom   CIS 283
 * @description Make a program that make a word search in a pdf for a list of words
 *
 */

public class Main {
    // Debugging Flag
    public final static boolean debugShowEverythingFlag = true;
    public final static boolean debugPhaseFlag = true;

    // Words File Path
    public final static String wordsFilePath = "./assets/words.txt";

    // chars
    public final static char debugPhaseBorder = '*';
    public final static char charBlank = '.';

    // Puzzle size
    public final static int puzzleSize = 45;

    public static void main(String[] args) {
        if (debugPhaseFlag){
            phasePrint("Reading the words file");
        }
        // read the words file
        String[] words = readWordsFile(wordsFilePath);
        if (words == null){
            System.exit(5);
        }

        if (debugPhaseFlag){
            phasePrint("Sorting words");
        }
        // Sorting words
        Arrays.sort(words, (a,b)->b.length() - a.length());
        if (debugShowEverythingFlag){
            System.out.println("\nWords sorting");
        }

        if (debugPhaseFlag){
            phasePrint("getting all chars from words");
        }
        // getting all chars from words
        char[] chars = new char[0];
        for (int i = 0; i < words.length; i++) {
            if (debugShowEverythingFlag){
                System.out.println(words[i]);
            }
            for (int j = 0; j < words[i].length(); j++) {
                chars = Arrays.copyOf(chars, chars.length +1);
                chars[chars.length - 1] = words[i].charAt(j);
            }
        }

        if (debugPhaseFlag){
            phasePrint("Starting the puzzle maker");
        }
        // Making the puzzle
        Puzzle puzzle = new Puzzle(words, chars, puzzleSize);
        if (debugPhaseFlag){
            phasePrint("Making the puzzle board");
        }
        long startTime = System.nanoTime();
        puzzle.start();
        while (puzzle.isAlive()){
            if (((double) (System.nanoTime() - startTime) > (double) (1 * 1_000_000_000)) && (!puzzle.stopFlag)){
                System.out.println("Fail to run at 10 sec");
                puzzle.stopFlag = true;
            }
        }
        if (debugShowEverythingFlag){
            System.out.println("Time to run : " + (System.nanoTime() - startTime) + " nano seconds");
            System.out.println(puzzle.puzzleBoardAsString());
        }
    }

    public static void phasePrint(String phaseName){
        for (int i = 0; i < (phaseName.length()+4); i++) {
            System.out.print(debugPhaseBorder);
        }
        System.out.println("\n" + debugPhaseBorder + " " + phaseName + " " + debugPhaseBorder);
        for (int i = 0; i < (phaseName.length()+4); i++) {
            System.out.print(debugPhaseBorder);
        }
        System.out.println("");
    }

    public static String[] readWordsFile(String path){
        String[] words;
        BufferedReader reader;
        try {
            // get how many words there are
            BufferedReader readerCount = new BufferedReader(new FileReader(path));
            int linesCount = 0;
            while (readerCount.readLine() != null) linesCount++;
            readerCount.close();
            if (debugShowEverythingFlag){
                System.out.println("\nThere are " + linesCount + " words");
            }
            words = new String[linesCount];

            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            if(debugShowEverythingFlag){
                System.out.println("\nWords in word list");
            }
            int i = 0;
            while (line != null){
                if(debugShowEverythingFlag){
                    System.out.println(line);
                }
                words[i] = line;
                i++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            words = null;
        }
        return words;
    }
}
