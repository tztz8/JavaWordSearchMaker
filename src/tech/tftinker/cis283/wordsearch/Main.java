package tech.tftinker.cis283.wordsearch;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

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
    public static boolean debugShowEverythingFlag = false;
    public final static boolean debugPhaseFlag = true;
    public static boolean debugAutoOpen = false;
    public static boolean debugPDFPhaseFlag = true;

    // File Paths
    public static String wordsFilePath = "./assets/words.txt";
    public static String pdfFilePath = "./assets/pdf/puzzle.pdf";
    //public final static String acrobatWindowsPath = "\"C:\\Program Files (x86)\\Adobe\\Acrobat Reader DC\\Reader\\\\AcroRd32.exe\" ";

    // chars
    public final static char debugPhaseBorder = '*';
    public final static char charBlank = '.';

    // Puzzle size
    public final static int puzzleSize = 45;

    public static void main(String[] args) {
        if (args.length != 0){
            MainWithArgs.run(args);
        }

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
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].toLowerCase();
        }
        if (debugShowEverythingFlag){
            System.out.println(ConsoleColors.BLUE + "\nWords sorting"  + ConsoleColors.RESET);
        }


        if (debugPhaseFlag){
            phasePrint("getting all chars from words");
        }
        // getting all chars from words
        char[] chars = new char[0];
        for (int i = 0; i < words.length; i++) {
            if (debugShowEverythingFlag){
                System.out.println(ConsoleColors.BLUE + words[i] + ConsoleColors.RESET);
            }
            for (int j = 0; j < words[i].length(); j++) {
                if (words[i].charAt(j) != ' ') {
                    chars = Arrays.copyOf(chars, chars.length + 1);
                    chars[chars.length - 1] = words[i].charAt(j);
                }else {
                    words[i] = words[i].replace(' ', charBlank);
                }
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
        puzzle.makepuzzle();
        if (debugShowEverythingFlag){
            System.out.print(ConsoleColors.BLUE);
            System.out.println("Time to run : " + (System.nanoTime() - startTime) + " nano seconds");
            System.out.println("Puzzle Board");
            System.out.println(puzzle.puzzleBoardAsString());
            System.out.println("Puzzle Key Board");
            System.out.println(puzzle.keyPuzzleBoardAsString());
            System.out.print(ConsoleColors.RESET);
        }

        if (!puzzle.stopFlag) {
            if (debugPhaseFlag) {
                phasePrint("Starting PDF Maker");
            }
            PDF pdfMaker = new PDF(pdfFilePath, puzzle, debugPDFPhaseFlag);
            if (debugPhaseFlag) {
                phasePrint("Making PDF");
            }
            pdfMaker.makePDF();
            if (debugAutoOpen){
                if (debugPhaseFlag) {
                    phasePrint("Open the PDF");
                }
                try {
                    File f = new File(pdfFilePath);
                    if (f.exists()){
                        if (Desktop.isDesktopSupported()){
                            Desktop.getDesktop().open(f);
                        }
                    }else {
                        System.out.println(ConsoleColors.RED_BOLD +
                                "ERROR: Can't find PDF" +
                                ConsoleColors.RESET);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
                System.out.print(ConsoleColors.BLUE);
                System.out.println("\nThere are " + linesCount + " words");
                System.out.print(ConsoleColors.RESET);
            }
            words = new String[linesCount];

            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            if(debugShowEverythingFlag){
                System.out.print(ConsoleColors.BLUE);
                System.out.println("\nWords in word list");
                System.out.print(ConsoleColors.RESET);
            }
            int i = 0;
            while (line != null){
                if(debugShowEverythingFlag){
                    System.out.print(ConsoleColors.BLUE);
                    System.out.println(line);
                    System.out.print(ConsoleColors.RESET);
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

    // Function to remove the element
    public static Point[] removeTheElement(Point[] arr, int index){

        if (arr == null || index < 0 || index >= arr.length) {
            return arr;
        }

        Point[] anotherArray = new Point[arr.length - 1];

        for (int i = 0, k = 0; i < arr.length; i++) {

            if (i == index) {
                continue;
            }

            anotherArray[k++] = arr[i];
        }

        // return the resultant array
        return anotherArray;
    }
}
