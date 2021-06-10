/*
 * TextIndex.java
 *
 *
 */
/* ALKHALIL MORPHO SYS -- An open source programm.
 *
 * Copyright (C) 2010.
 *
 * This program is free software, distributed under the terms of
 * the GNU General Public License Version 3. For more informations see web site at :
 * http://www.gnu.org/licenses/gpl.txt
 */
package AlKhalil;

import java.util.*;
import java.util.LinkedList;
import AlKhalil.token.*;

/**
 *<p>This class provides implementations for indexing an input text. the input text is the parameter of the constructer
 *
 * </p>
 */
public class TextIndex {
    //Each, non duplicate word,  contain the line position and the position in the given line
    //The LinkedList is composed of an integer (the position of the word in the text), a string (left context) and a string (right context)

    private static HashMap<String, LinkedList> wordInfo = new HashMap<String, LinkedList>();
    private static LinkedList<String> normalizedTokens = new LinkedList();
    private static String printFind = "";

    public void TextIndex(String str) {
        Tokens tokens = new Tokens(str);

        normalizedTokens = tokens.getNormalizedTokens();



        String words[] = tokens.getWords();
        int nbrWords = words.length;


        List<String> wordsList = Arrays.asList(words);


        //for each non duplicated word, find the position, the left and the right context
        for (int i = 0; i < normalizedTokens.size(); i++) {
            LinkedList wordPos = new LinkedList();
            String normalizedWord = (String) normalizedTokens.get(i);
            for (int j = 0; j < nbrWords; j++) {
                if (wordsList.get(j).equals(normalizedWord)) {
                    wordPos.add(j + 1); //word position
                    String leftContext = "";
                    String rightContext = "";
                    //Left context
                    for (int k = -4; k < 0; k++) {
                        if ((j + k >= 0) && (j + k < nbrWords)) {
                            leftContext += " " + wordsList.get(j + k);
                        }
                    }
                    wordPos.add(leftContext);

                    //Left context
                    for (int k = 1; k <= 4; k++) {
                        if ((j + k >= 0) && (j + k < nbrWords)) {
                            rightContext += " " + wordsList.get(j + k);
                        }
                    }
                    wordPos.add(rightContext);

                }
                wordInfo.put(normalizedWord, wordPos);
            }
        }


        String htmlHead = "<html> <body LANG=\"ar-SA\" DIR=\"LTR\"> <center><font size=3> <b>الفَهرَسَة <br> Indexing</b></font></center><br>\n";
        htmlHead += "";


        htmlHead += "<P align=\"right\"> عدد الكلمات:";
        htmlHead += " " + nbrWords + "</P><BR>";


        htmlHead += "<P align=\"right\"> عدد الكلمات غير المكررة:";
        htmlHead += " " + normalizedTokens.size() + "</P><BR>";


        htmlHead += "<DIV ALIGN=RIGHT>";
        htmlHead += "<TABLE DIR=\"RTL\" BORDER=1 CELLSPACING=0>";

        String cellBeg = "<TD align=\"right\">";

        String cellEnd = "</TD>";

        htmlHead += "<TR VALIGN=TOP> ";

        htmlHead += cellBeg + "<B>السياقات</B>" + cellEnd + cellBeg + "<B> مواقع الكلمات </B>" + cellEnd + cellBeg + "<B>التكرار</B>" + cellEnd + cellBeg + "<B>الكلمة</B>" + cellEnd + "</TR>";


        StringBuffer htmlOutput = new StringBuffer();



        for (int i = 0; i < normalizedTokens.size(); i++) {
            String wordFind = normalizedTokens.get(i).toString();
            LinkedList wordPos = new LinkedList();
            wordPos = wordInfo.get(wordFind);
            htmlOutput.append("<TR VALIGN=TOP> ");

            htmlOutput.append(cellBeg);
            for (int j = 1; j < wordPos.size(); j += 3) {
                String context = wordPos.get(j).toString() + " <Font color=red>" + wordFind + "</Font> " + wordPos.get(j + 1).toString();
                htmlOutput.append(context + "<br>");
            }
            htmlOutput.append(cellEnd);

            htmlOutput.append(cellBeg);
            for (int j = 0; j < wordPos.size(); j += 3) {
                String posLi = wordPos.get(j).toString();
                htmlOutput.append(posLi + " <BR>");
            }
            htmlOutput.append(cellEnd);

            htmlOutput.append(cellBeg + wordPos.size() / 3 + cellEnd);

            htmlOutput.append(cellBeg + normalizedTokens.get(i) + cellEnd);

            htmlOutput.append("</TR>");

        }

        String htmlBottom = "</TABLE> </DIV>" + "</body></html>";

        printFind = htmlHead + htmlOutput.toString() + htmlBottom;


       // System.out.println("Text Indexed!!!");
    }

    public static HashMap<String, LinkedList> getwordInfo() {
        return wordInfo;
    }

    public static LinkedList<String> getnormalizedWords() {
        return normalizedTokens;
    }

    public static String getHTML() {
        return printFind;
    }
}
