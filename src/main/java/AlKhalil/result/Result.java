/*
 * Result.java
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
package AlKhalil.result;

/**
 * <p>This class provides implementations for results. each result contains the voweled form, the prefix,
 * the type of the word , the pattern , the root, the part of speech, the suffix
 * and an attribute indicating the priority of the result for display purpose.
 *
 * </p>
 *
 */
public class Result {
    // ------------------------------------------------------  Class Variables

    /** the voweled form of the word  */
    private String voweledWord;
    /** the prefix of the word  */
    private String prefix;
    /**the stem of the word  */
    private String stem;
    /** the type of the word  */
    private String wordType;
    /** the pattern of the word */
    private String wordPattern;
    /** the root of the word */
    private String wordRoot;
    /** the part of speech of the word  */
    private String pos;
    /** the suffix of the word  */
    private String suffix;
    /** the priority of the result in displaying  */
    private String priority;

    // --------------------------------------------------------- Constuctor
    /**
     * <p>Constructs an instance of the result
     * @param vw the voweled form of the word
     * @param p the prefix of the word
     * @param st the stem of the word
     * @param t the type of the word
     * @param wp the pattern of the word
     * @param wr the root of the word
     * @param ps the part of speech of the word
     * @param s the suffix of the word
     * @param pr the priority of the resultin displaying
     *
     * </p>
     */
    public Result(String vw, String p, String st, String t, String wp, String wr, String ps, String s, String pr) {
        this.voweledWord = vw;
        this.prefix = p;
        this.stem = st;
        this.wordType = t;
        this.wordPattern = wp;
        this.wordRoot = wr;
        this.pos = ps;
        this.suffix = s;
        this.priority = pr;
    }

    // --------------------------------------------------------- Public Methods
    /** returns the voweled form of the word */
    public String getVoweledword() {
        return voweledWord;
    }

    /** returns the prefix of the word */
    public String getPrefix() {
        return prefix;
    }

    /** returns the stem of the word */
    public String getStem() {
        return stem;
    }

    /** returns the type of the word */
    public String getWordtype() {
        return wordType;
    }

    /** returns the pattern of the word */
    public String getWordpattern() {
        return wordPattern;
    }

    /** returns the root of the word  */
    public String getWordroot() {
        return wordRoot;
    }

    /** returns the  part of speech of the word */
    public String getPos() {
        return pos;
    }

    /** returns the suffix of the word */
    public String getSuffix() {
        return suffix;
    }

    /** returns the priority of the result */
    public String getPriority() {
        return priority;
    }
}
