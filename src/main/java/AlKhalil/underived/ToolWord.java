/*
 * ToolWord.java
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
package AlKhalil.underived;

/**
 * <p>This class provides implementations for arabic tool words.
 * each tool word is characterized by its  unvoweled form,  
 * voweled form, type, prefixes and suffixes calsses and an attriubute called priority to sort the results.
 *
 * </p>
 */
public class ToolWord {
    // ------------------------------------------------------  Class Variables

    /** the voweled form of the tool word  */
    private String voweledform;
    /** the unvoweled form of the tool word  */
    private String unvoweledform;
    /** the type of the tool word  */
    private String type;
    /** the prefix classes of the tool word for compatibility validation  */
    private String prefixeclass;
    /** the suffix classes of the tool word for compatibility validation  */
    private String suffixeclass;
    /** the priority of the tool word in displaying */
    private String priority;

    /** Creates a new instance of ToolWord */
    public ToolWord() {
    }

    // --------------------------------------------------------- Public Methods
    /** returns the unvoweled form of the tool word    */
    public String getUnvoweledform() {
        return unvoweledform;
    }

    /** returns the voweled form of the tool word    */
    public String getVoweledform() {
        return voweledform;
    }

    /** returns the type of the tool word    */
    public String getType() {
        return type;
    }

    /** returns the prefix compatibility classes of the tool word    */
    public String getPrefixeclass() {
        return prefixeclass;
    }

    /** returns the suffix compatibility classes of the tool word    */
    public String getSuffixeclass() {
        return suffixeclass;
    }

    /** returns the priority of the tool word    */
    public String getPriority() {
        return priority;
    }

    /** sets  the unvoweled form of the stem
    @param unvoweledform the unvoweled form of the word
     */
    public void setUnvoweledform(String unvoweledform) {
        this.unvoweledform = unvoweledform;
    }

    /** sets  the voweled form of the tool word
    @param voweledform the voweled form of the tool word
     */
    public void setVoweledform(String voweledform) {
        this.voweledform = voweledform;
    }

    /** sets  the type of the word
    @param type the type of the word
     */
    public void setType(String type) {
        this.type = type;
    }

    /** sets  the prefix classes of the tool word
    @param prefixeclass the prefix classes of the tool word
     */
    public void setPrefixeclass(String prefixeclass) {
        this.prefixeclass = prefixeclass;
    }

    /** sets  the suffix classes of the tool word
    @param suffixeclass the prefix classes of the tool word
     */
    public void setSuffixeclass(String suffixeclass) {
        this.suffixeclass = suffixeclass;
    }

    /** sets  the priority of the tool word
    @param priority he priority of the tool word in displaying
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }
}
