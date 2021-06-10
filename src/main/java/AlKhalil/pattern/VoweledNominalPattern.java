/*
 * VoweledNominalPattern.java
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
 * <p>This class provides implementations for voweled nominal patterns. each pattern is characterized by its id, voweled form, canonical form of the pattern,
 * the type of the pattern, the case of the word, and an attribute indicating the number, gender and the definitness of the pattern.
 *
 * .</p>
 *
 */
public class VoweledNominalPattern {
    // ------------------------------------------------------  Class Variables

    /** the identifiant of the pattern */
    private String id;
    /** the voweled form of the pattern */
    private String diac;
    /** the canonical form of the pattern */
    private String canonic;
    /** the type form of the pattern */
    private String type;
    /** the case of the pattern */
    private String cas;
    /** indicates the number, gender and the definitness of the pattern */
    private String ncg;

    /** Creates a new instance of VoweledNominalPattern */
    public VoweledNominalPattern() {
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

    /** returns the case of the pattern */
    public String getCas() {
        return cas;
    }

    /** returns the number,gender and definitness of the pattern */
    public String getNcg() {
        return ncg;
    }

    /** sets the identifiant of the pattern
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

    /** sets the the case of the pattern
    @param value case of the pattern
     */
    public void setCas(String cas) {
        this.cas = cas;
    }

    /** sets the the number, gender and definitness of the pattern
    @param ncg represents the number, gender and definitness of the pattern
     */
    public void setNcg(String ncg) {
        this.ncg = ncg;
    }
}
