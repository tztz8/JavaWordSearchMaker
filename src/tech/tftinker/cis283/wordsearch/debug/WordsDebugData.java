package tech.tftinker.cis283.wordsearch.debug;

import tech.tftinker.cis283.wordsearch.ConsoleColors;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class WordsDebugData {
    public String word;
    public String[] debuglines = new String[0];
    public String debugline = "";
    public boolean startOfDebugLine = false;

    public WordsDebugData(String word){
        this.word = word;
    }

    private void startLine(){
        debugline = ConsoleColors.PURPLE;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        debugline = debugline + time;
        debugline = debugline + ConsoleColors.RESET + " : ";
        startOfDebugLine = true;
    }

    public void print(String message){
        if(!startOfDebugLine){
            startLine();
        }

        debugline = debugline + message;
    }

    public void println(String message){
        print(message);

        this.debuglines = Arrays.copyOf(this.debuglines, this.debuglines.length + 1);
        this.debuglines[this.debuglines.length - 1] = this.debugline;
        startOfDebugLine = false;
        this.debugline = "";
    }

    public void printData(){
        for (int i = 0; i < debuglines.length; i++) {
            System.out.println(debuglines[i]);
        }
//        try {
//            File f1 = new File("./assets/debugInfo/words_debuglog.txt");
//
//            if (!f1.exists()) {
//                f1.createNewFile();
//            }
//
//            FileWriter fileWriter = new FileWriter(f1.getName(), true);
//            BufferedWriter bw = new BufferedWriter(fileWriter);
//
//            for (int i = 0; i < debuglines.length; i++) {
//                bw.write(debuglines[i] + "/n");
//                System.out.println(debuglines[i]);
//            }
//
//            bw.close();
//            //Desktop.getDesktop().open(f1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
