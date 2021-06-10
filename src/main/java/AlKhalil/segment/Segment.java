/*
 * Segment.java
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
 * <p>This class provides implementations for segments.
 * each segment contains a prefix, stem and suffix.
 *
 * </p>
 *
 */
public class Segment {
    // ------------------------------------------------------  Class Variables

    /** the  prefix  */
    private Prefixe prefixe;
    /** the  stem  */
    private Stem stem;
    /** the  suffix  */
    private Suffixe suffixe;

    // --------------------------------------------------------- Constuctor
    /**
     * <p>Constructs an instance of the segment
     *
     * @param p the prefix of the word
     * @param st the stem of the word
     * @param s the suffix of the word
     *
     * </p>
     */
    public Segment(Prefixe p, Stem st, Suffixe s) {
        this.prefixe = p;
        this.stem = st;
        this.suffixe = s;
    }
    // --------------------------------------------------------- Public Methods

    /** returns the prefix of the word */
    public Prefixe getPrefixe() {
        return prefixe;
    }

    /** returns the suffix of the word */
    public Suffixe getSuffixe() {
        return suffixe;
    }

    /** returns the stem of the word */
    public Stem getStem() {
        return stem;
    }
}
