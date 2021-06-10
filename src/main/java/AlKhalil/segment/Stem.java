/*
 * Stem.java
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
 * <p>This class provides implementations for arabic stems.
 * each stem is characterized by its  unvoweled form,  voweled forms, possible patterns and possible roots.
 *
 * </p>
 *
 */
import java.util.*;

public class Stem {
    // ------------------------------------------------------  Class Variables

    /** the  unvoweled form of the stem  */
    private String unvoweledform;
    /** the  voweled form of the stem  */
    private List voweledforms;
    /** the  possible patterns of the stem  */
    private List possiblepatterns;
    /** the  possible roots of the stem  */
    private List possibleroots;

    /** Creates a new instance of Stem */
    public Stem() {
    }

    // --------------------------------------------------------- Public Methods
    /** returns the unvoweled form of the stem    */
    public String getUnvoweledform() {
        return unvoweledform;
    }

    /** returns the voweled forms of the stem    */
    public List getVoweledforms() {
        return voweledforms;
    }

    /** returns the possible patterns of the stem    */
    public List getPossiblepatterns() {
        return possiblepatterns;
    }

    /** returns the possible roots of the stem    */
    public List getPossibleroots() {
        return possibleroots;
    }

    /** sets  the unvoweled form of the stem
    @param unvoweledform the unvoweled form of the stem
     */
    public void setUnvoweledform(String unvoweledform) {
        this.unvoweledform = unvoweledform;
    }

    /** add  a new voweled form of the stem
    @param voweledform a voweled form of the stem
     */
    public void addVoweledform(String voweledform) {
        voweledforms.add(voweledform);
    }

    /** add  a new pattern of the stem
    @param possiblepattern a pattern of the stem
     */
    public void addPossiblepattern(String possiblepattern) {
        possiblepatterns.add(possiblepattern);
    }

    /** add  a new root of the stem
    @param possibleroot a root of the stem
     */
    public void addPossibleroots(String possibleroot) {
        possibleroots.add(possibleroot);
    }

    /** returns the length of the stem    */
    public int getStemLength() {
        return unvoweledform.length();
    }
}
