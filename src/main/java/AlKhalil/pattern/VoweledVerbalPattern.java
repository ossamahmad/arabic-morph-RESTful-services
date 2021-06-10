/*
 * VoweledVerbalPattern.java
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
package AlKhalil.pattern;

/**
 * <p>This class provides implementations for voweled verbal patterns. each pattern is characterized by its id, voweled form, canonical form of the pattern,
 * the type of the pattern, the augmentation of the pattern, the case of the word,
 * an attribute indicating the tense, number, gender and the mood of the pattern,and the transitivity of the pattern.
 *
 * .</p>
 *
 */
public class VoweledVerbalPattern {
    // ------------------------------------------------------  Class Variables

    /** the identifiant of the pattern */
    private String id;
    /** the voweled form of the pattern */
    private String diac;
    /** the canonical form of the pattern */
    private String canonic;
    /** the type form of the pattern */
    private String type;
    /** indicates the augmentation of the pattern */
    private String aug;
    /** the case of the pattern */
    private String cas;
    /** indicates the tense, number, gender and the mood of the pattern */
    private String ncg;
    /** indicates the transitivity of the pattern */
    private String trans;

    /** Creates a new instance of VoweledVerbalPattern */
    public VoweledVerbalPattern() {
    }

    // --------------------------------------------------------- Public Methods
    /** returns the identifiant of the pattern */
    public String getId() {
        return id;
    }

    /** returns the voweled form of the pattern */
    public String getDiac() {
        return diac;
    }

    /** returns the canonical of the pattern */
    public String getCanonic() {
        return canonic;
    }

    /** returns the type of the pattern */
    public String getType() {
        return type;
    }

    /** returns the augmentation of the pattern */
    public String getAug() {
        return aug;
    }

    /** returns the case of the pattern */
    public String getCas() {
        return cas;
    }

    /** returns the tense, number, gender and the mood of the pattern */
    public String getNcg() {
        return ncg;
    }

    public String getTrans() {
        return trans;
    }

    /** sets the the identifiant of the pattern
    @param id identifiant of the pattern
     */
    public void setId(String id) {
        this.id = id;
    }

    /** sets the the voweled form of the pattern
    @param diac voweled form of an arabic pattern
     */
    public void setDiac(String diac) {
        this.diac = diac;
    }

    /** sets the the canonical form of the pattern
    @param canonic caonical form of an arabic pattern
     */
    public void setCanonic(String canonic) {
        this.canonic = canonic;
    }

    /** sets the the type of the pattern
    @param value type of the pattern
     */
    public void setType(String type) {
        this.type = type;
    }

    /** sets the the augmentation of the pattern
    @param aug the augmentation of the pattern
     */
    public void setAug(String aug) {
        this.aug = aug;
    }

    /** sets the the case of the pattern
    @param value case of the pattern
     */
    public void setCas(String cas) {
        this.cas = cas;
    }

    /** sets the tense, number, gender and the mood of the pattern
    @param ncg represents the tense, number, gender and the mood of the pattern
     */
    public void setNcg(String ncg) {
        this.ncg = ncg;
    }

    /** sets  the transitivity of the pattern
    @param trans indicates the transitivity of the pattern
     */
    public void setTrans(String trans) {
        this.trans = trans;
    }
}
