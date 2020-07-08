package tech.tftinker.cis283.wordsearch;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.text.TextPosition;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PDF {

    String fileNameAndPath;
    Puzzle puzzle;
    String titleText = "Word Search";

    public PDF(String fileOutPathAndName, Puzzle puzzle){
        this.fileNameAndPath = fileOutPathAndName;
        this.puzzle = puzzle;
    }

    public void makePDF(){
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            PDFont font = PDTrueTypeFont.loadTTF(document, new File("./assets/Fonts/Anonymous Pro.ttf"));

            //----------
            //  Title
            //----------

            //Begin the Content stream
            contentStream.beginText();

            int titleFontSize = 20;

            //Setting the font to the Content stream
            contentStream.setFont(font, titleFontSize);

            float widthTitle = font.getStringWidth(titleText) / 1000 * titleFontSize;

            //Setting the position for the line
            contentStream.newLineAtOffset(
                    page.getMediaBox().getWidth()/2 - (widthTitle/2),
                    page.getMediaBox().getHeight() - 50);

            //Adding text in the form of string
            contentStream.showText(titleText);

            //Ending the content stream
            contentStream.endText();

            //----------
            //  Puzzle
            //----------

            //Begin the Content stream
            contentStream.beginText();

            //puzzle fontsize
            int puzzleFontSize = 9;

            String[] puzzleTextArray = puzzle.puzzleBoardAsStringArray();

            //Setting the font to the Content stream
            contentStream.setFont(font, puzzleFontSize);

            //Setting the leading
            contentStream.setLeading(puzzleFontSize+0.1f);

            //Setting the character spacing
            contentStream.setCharacterSpacing(5f);

            float widthPuzzle = font.getStringWidth(puzzle.line1puzzleBoardAsString()) / 1000 * puzzleFontSize;

            //Setting the position for the line
            contentStream.newLineAtOffset(
                    page.getMediaBox().getWidth()/2 - (widthPuzzle/2),
                    page.getMediaBox().getHeight() - 75);

            for (int x = 0; x < puzzleTextArray.length; x++) {
                contentStream.newLine();
                //Adding text in the form of string
                contentStream.showText(puzzleTextArray[x].substring(4,puzzle.puzzleBoardSize+4));
            }

            //Ending the content stream
            contentStream.endText();

            //---------------
            //  Words Title
            //---------------

            contentStream.beginText();

            float heightPuzzle = (font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * puzzleFontSize) * puzzleTextArray.length;

            String wordsTitle = "Find the following " + puzzle.words.length + " words:";

            int wordsTitleFontSize = 12;

            //Setting the font to the Content stream
            contentStream.setFont(font, puzzleFontSize);

            //Setting the leading
            contentStream.setLeading(wordsTitleFontSize+0.1f);

            //Setting the character spacing
            contentStream.setCharacterSpacing(-5f);

            //Setting the leading
            contentStream.setLeading(wordsTitleFontSize+0.1f);

            //Setting the character spacing
            contentStream.setCharacterSpacing(5f);

            float widthWordsTitle = font.getStringWidth(wordsTitle) / 1000 * wordsTitleFontSize;

            //Setting the position for the line
            contentStream.newLineAtOffset(
                    page.getMediaBox().getWidth()/2 - ((widthWordsTitle/2)+50),
                    page.getMediaBox().getHeight() - (70 + heightPuzzle));

            contentStream.showText(wordsTitle);

            contentStream.endText();

            //Closing the content stream
            contentStream.close();

            document.addPage(page);

            //Creating the PDDocumentInformation object
            PDDocumentInformation pdd = document.getDocumentInformation();

            //Setting the author of the document
            pdd.setAuthor(System.getProperty("user.name"));

            // Setting the title of the document
            pdd.setTitle("Simple Word Search");

            //Setting the creator of the document
            pdd.setCreator("TFTinker");

            //Setting the subject of the document
            pdd.setSubject("Word Search");

            //Setting the created date of the document
            Calendar date = new GregorianCalendar();
            pdd.setCreationDate(date);

            //Setting keywords for the document
            pdd.setKeywords("CIS-283 Project, Word Search, tftinker");

            //Saving the document
            document.save(this.fileNameAndPath);

            //Closing the document
            document.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
