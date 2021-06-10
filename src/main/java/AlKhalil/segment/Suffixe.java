/*
 * Suffixe.java
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
package AlKhalil.segment;

/**
 * <p>This class provides implementations for arabic suffixes.
 * each suffix is characterized by its  unvoweled form,  voweled form, description and its class for compatibility validation.
 *
 * </p>
 *
 */
public class Suffixe {
    // ------------------------------------------------------  Class Variables

    /** the  unvoweled form of the suffix  */
    private String unvoweledform;
    /** the  voweled form of the suffix  */
    private String voweledform;
    /** the  description of the suffix  */
    private String desc;
    /** the  class of the suffix  */
    private String classe;

    /** Creates a new instance of Suffixe */
    public Suffixe() {
    }
    // --------------------------------------------------------- Public Methods

    /** returns the unvoweled form of the suffix    */
    public String getUnvoweledform() {
        return unvoweledform;
    }

    /** returns the voweled form of the suffix    */
    public String getVoweledform() {
        return voweledform;
    }

    /** returns the class of the suffix    */
    public String getClasse() {
        return classe;
    }

    /** returns the ddescription of the suffix    */
    public String getDesc() {
        return desc;
    }

    /** sets  the unvoweled form of the suffix
    @param unvoweledform the unvoweled form of the suffix
     */
    public void setUnvoweledform(String unvoweledform) {
        this.unvoweledform = unvoweledform;
    }

    /** sets  the voweled form of the suffix
    @param voweledform the voweled form of the suffix
     */
    public void setVoweledform(String voweledform) {
        this.voweledform = voweledform;
    }

    /** sets  the class of the suffix
    @param classe the class of the suffix
     */
    public void setClasse(String classe) {
        this.classe = classe;
    }

    /** sets  the description of the suffix
    @param desc the description of the suffix
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
