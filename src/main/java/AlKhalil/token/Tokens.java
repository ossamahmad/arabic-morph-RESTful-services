/*
 * Tokens.java
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
package AlKhalil.token;

import java.util.*;

/**
 * <p>This class provides implementations to tokenize an input text.
 * a tokenized text is characterized by two lists: the unvoweled tokens and the normalized tokens
 * i.e. tokens with possibly existing short vowels in the input text.
 *
 * </p>
 *
 */
public class Tokens {
    // ------------------------------------------------------  Class Variables

    /** the  the normalized tokens list  */
    private LinkedList<String> normalizedTokens = new LinkedList();
    /** the unvoweled tokens list  */
    private LinkedList<String> unvoweledTokens = new LinkedList();
    /** the all input text words  */
    private String words[];
    /** total number of words in the input text */
    private int nbrWords;

    // --------------------------------------------------------- Constuctor
    /**
     * <p>Constructs an instance of Tokens
     * @param textToBeAnalysed the input text
     *
     * </p>
     */
    public Tokens(String textToBeAnalysed) {
        String inputText = textToBeAnalysed.trim();
        inputText = inputText.replaceAll("ـ", "");
        inputText = inputText.replaceAll("([^\\sَءةًٌُِإٍّْئؤآأابتثجحخدذرزسشصضطظعغفقكلمىنهوي])", " ");
        // inputText = inputText.replaceAll("\\s+", " ");

        words = inputText.split("\\s+");

        // String originalWord;

        nbrWords = words.length;

        for (int i = 0; i < nbrWords; i++) {
            String normalizedWord = words[i];
            if (!normalizedTokens.contains(normalizedWord)) {
                normalizedTokens.add(normalizedWord);
                unvoweledTokens.add(normalizedWord.replaceAll("[ًٌٍَُِّْ]", ""));
            }
        }
    }

    /** returns the normalized tokens list   */
    public LinkedList<String> getNormalizedTokens() {
        return normalizedTokens;
    }

    /** returns the unvoweled tokens list   */
    public LinkedList<String> getUnvoweledTokens() {
        return unvoweledTokens;
    }

    /** returns all the input text words   */
    public String[] getWords() {
        return words;
    }
}
