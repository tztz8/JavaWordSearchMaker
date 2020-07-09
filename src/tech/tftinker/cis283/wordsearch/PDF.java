package tech.tftinker.cis283.wordsearch;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;

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
            // PDF Document and font
            PDDocument document = new PDDocument();
            PDFont font = PDTrueTypeFont.loadTTF(document, new File("./assets/Fonts/Anonymous Pro.ttf"));

            // WordSearchPage
            PDPage wordSearchPage = new PDPage();
            PDPageContentStream contentStreamForWordSearchPage = new PDPageContentStream(document, wordSearchPage);

            //----------
            //  Title
            //----------
            int titleFontSize = 20;
            placeText(contentStreamForWordSearchPage, font,
                    wordSearchPage.getMediaBox().getWidth()/2,
                    wordSearchPage.getMediaBox().getHeight() - 50,
                    titleFontSize,
                    titleText,
                    true);

            //----------
            //  Puzzle
            //----------
            int puzzleFontSize = 9;
            String[] puzzleTextArray = puzzle.puzzleBoardAsStringArray();
            float height = (font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * puzzleFontSize);
            for (int i = 0; i < puzzleTextArray.length; i++) {
                placeText(contentStreamForWordSearchPage, font,
                        wordSearchPage.getMediaBox().getWidth()/2,
                        wordSearchPage.getMediaBox().getHeight() - (75 + i*height),
                        puzzleFontSize,
                        puzzleTextArray[i].substring(4,puzzle.puzzleBoardSize+4),
                        true,
                        5f);
            }

            //---------------
            //  Words Title
            //---------------
            float heightPuzzle = (font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * puzzleFontSize) * puzzleTextArray.length;
            String wordsTitle = "Find the following " + puzzle.words.length + " words:";
            int wordsTitleFontSize = 14;
            placeText(contentStreamForWordSearchPage, font,
                    wordSearchPage.getMediaBox().getWidth()/2 - 10,
                    wordSearchPage.getMediaBox().getHeight() - (75 + heightPuzzle + 20),
                    wordsTitleFontSize,
                    wordsTitle,
                    true);

            //-------------
            // Words list
            //-------------
            int wordsFontSize = 11;
            String[] words =  Main.readWordsFile(Main.wordsFilePath);
            float outLineSize = 40f;
            float thirdWidth = (wordSearchPage.getMediaBox().getWidth() - (outLineSize*2))/3;
            float heightOfWords = (font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * wordsFontSize);
            for (int i = 0, y = 0; i < words.length; y++) {
                for (int j = 0; j < 3; j++) {
                    if (i < words.length){
                        placeText(contentStreamForWordSearchPage, font,
                                outLineSize + (j*thirdWidth) + (thirdWidth/2),
                                wordSearchPage.getMediaBox().getHeight() - (75 + heightPuzzle + 20 + 20 + (y*heightOfWords)),
                                wordsFontSize,
                                words[i],
                                true);
                    }
                    i++;
                }
            }

            // -----------
            // adding page
            // -----------

            placeText(contentStreamForWordSearchPage, font,
                    wordSearchPage.getMediaBox().getWidth() - 125, 8,
                    6, "TFTinker-WordSearchMaker");

            //Closing the content stream
            contentStreamForWordSearchPage.close();

            document.addPage(wordSearchPage);

            // WordSearchPage
            PDPage keyWordSearchPage = new PDPage();
            PDPageContentStream contentStreamForKeyWordSearchPage = new PDPageContentStream(document, keyWordSearchPage);

            //----------
            //  Title
            //----------
            placeText(contentStreamForKeyWordSearchPage, font,
                    keyWordSearchPage.getMediaBox().getWidth()/2,
                    keyWordSearchPage.getMediaBox().getHeight() - 50,
                    titleFontSize,
                    titleText + " - KEY",
                    true);

            //----------
            //  Puzzle
            //----------
            String[] keyPuzzleTextArray = puzzle.keyPuzzleBoardAsStringArray();
            for (int i = 0; i < puzzleTextArray.length; i++) {
                placeText(contentStreamForKeyWordSearchPage, font,
                        keyWordSearchPage.getMediaBox().getWidth()/2,
                        keyWordSearchPage.getMediaBox().getHeight() - (75 + i*height),
                        puzzleFontSize,
                        keyPuzzleTextArray[i].substring(4,puzzle.puzzleBoardSize+4),
                        true,
                        5f);
            }

            //---------------
            //  Words Title
            //---------------
            placeText(contentStreamForKeyWordSearchPage, font,
                    keyWordSearchPage.getMediaBox().getWidth()/2 - 10,
                    keyWordSearchPage.getMediaBox().getHeight() - (75 + heightPuzzle + 20),
                    wordsTitleFontSize,
                    "The Words are:",
                    true);

            //-------------
            // Words list
            //-------------
            for (int i = 0, y = 0; i < words.length; y++) {
                for (int j = 0; j < 3; j++) {
                    if (i < words.length){
                        placeText(contentStreamForKeyWordSearchPage, font,
                                outLineSize + (j*thirdWidth) + (thirdWidth/2),
                                keyWordSearchPage.getMediaBox().getHeight() - (75 + heightPuzzle + 20 + 20 + (y*heightOfWords)),
                                wordsFontSize,
                                words[i],
                                true);
                    }
                    i++;
                }
            }

            // -----------
            // adding page
            // -----------

            placeText(contentStreamForKeyWordSearchPage, font,
                    keyWordSearchPage.getMediaBox().getWidth() - 125, 8,
                    6, "TFTinker-WordSearchMaker");

            //Closing the content stream
            contentStreamForKeyWordSearchPage.close();

            document.addPage(keyWordSearchPage);

            // ------------------------------
            // Writing final info to document
            // ------------------------------

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
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void placeText(PDPageContentStream contentStream, PDFont font, float x, float y, int textSize, String text) throws IOException {
        placeText(contentStream, font, x, y, textSize, text, false, 1f);
    }

    private void placeText(PDPageContentStream contentStream, PDFont font, float x, float y, int textSize, String text, boolean center) throws IOException {
        placeText(contentStream, font, x, y, textSize, text, center, 1f);
    }

    private void placeText(PDPageContentStream contentStream, PDFont font, float x, float y, int textSize, String text, boolean center, float characterSpacing) throws IOException {
        //Begin the Content stream
        contentStream.beginText();

        //Setting the font to the Content stream
        contentStream.setFont(font, textSize);

        //Setting the leading
        contentStream.setLeading(textSize+0.1f);

        //Setting the character spacing
        contentStream.setCharacterSpacing(characterSpacing);

        if (center) {
            float width;
            if (characterSpacing != 1f) {
                width = (font.getStringWidth(text) / 1000 * textSize) * (characterSpacing / 2.5f);
            } else {
                width = (font.getStringWidth(text) / 1000 * textSize);
            }
            contentStream.newLineAtOffset( x - (width/2), y);
        }else {
            //Setting the position for the line
            contentStream.newLineAtOffset(x, y);
        }

        //Adding text in the form of string
        contentStream.showText(text);

        //Ending the content stream
        contentStream.endText();
    }
}
