/*
 * VoweledNominalPatternsList.java
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
 * <p>This class provides implementations for the voweled nominal pattern lists.
 * .</p>
 *
 */
import java.util.*;

public class VoweledNominalPatternsList {
    // ------------------------------------------------------  Class Variables

    /** the voweled nominal patterns list */
    private List nominalPatterns = new LinkedList();

    /** Creates a new instance of VoweledNominalPatternsList */
    public VoweledNominalPatternsList() {
    }
    // --------------------------------------------------------- Public Methods

    /** add a new  voweled nominal patterns to the list
    @param vnp a voweled nominal pattern
     */
    public void addPattern(VoweledNominalPattern vnp) {
        nominalPatterns.add(vnp);
    }

    /** returns the voweled nominal patterns list  */
    public List getPatterns() {
        return nominalPatterns;
    }
}
