/*
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
package AlKhalil.underived;

/**
 * <p>This class provides implementations for exceptional words.
 * each exceptional word is accompanied by its maorphological analysis results.
 *
 * </p>
 *
 */
public class ExceptionalWord {
// ------------------------------------------------------  Class Variables

    /** the voweled form of the word  */
    private String voweledform;
    /** the unvoweled form of the word  */
    private String unvoweledform;
    /** the prefix of the word  */
    private String prefix;
    /**the stem of the word  */
    private String stem;
    /** the type of the word  */
    private String type;
    /** the suffix of the word  */
    private String suffix;

    /** Creates a new instance of Stem */
    public ExceptionalWord() {
    }
// --------------------------------------------------------- Public Methods

    /** returns the unvoweled form of the word    */
    public String getUnvoweledform() {
        return unvoweledform;
    }

    /** returns the voweled form of the word    */
    public String getVoweledform() {
        return voweledform;
    }

    /** returns the stem of the word    */
    public String getStem() {
        return stem;
    }

    /** returns the type of the word    */
    public String getType() {
        return type;
    }

    /** returns the prefix of the word    */
    public String getPrefix() {
        return prefix;
    }

    /** returns the suffix of the word    */
    public String getSuffix() {
        return suffix;
    }

    /** sets  the unvoweled form of the stem
    @param unvoweledform the unvoweled form of the word
     */
    public void setUnvoweledform(String unvoweledform) {
        this.unvoweledform = unvoweledform;
    }

    /** sets  the voweled form of the stem
    @param voweledform the voweled form of the word
     */
    public void setVoweledform(String voweledform) {
        this.voweledform = voweledform;
    }

    /** sets  the stem of the word
    @param stem the unvoweled form of the word
     */
    public void setStem(String stem) {
        this.stem = stem;
    }

    /** sets  the type of the word
    @param type the type of the word
     */
    public void setType(String type) {
        this.type = type;
    }

    /** sets  the prefix of the word
    @param prefix the prefix of the word
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /** sets  the suffix of the stem
    @param suffix the suffix of the word
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
