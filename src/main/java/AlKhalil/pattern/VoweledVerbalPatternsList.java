/*
 * VoweledVerbalPatternsList.java
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
 * <p>This class provides implementations for the voweled verbal pattern lists.
 * .</p>
 *
 */
import java.util.*;

public class VoweledVerbalPatternsList {
    // ------------------------------------------------------  Class Variables

    /** the voweled verbal patterns list */
    private List verbalPatterns = new LinkedList();

    /** Creates a new instance of VoweledVerbalPatternsList */
    public VoweledVerbalPatternsList() {
    }

    // --------------------------------------------------------- Public Methods
    /** add a new  voweled verbal patterns to the list
    @param vvp a voweled verbal pattern
     */
    public void addPattern(VoweledVerbalPattern vvp) {
        verbalPatterns.add(vvp);
    }

    /** returns the voweled verbal patterns list  */
    public List getPatterns() {
        return verbalPatterns;
    }
}
