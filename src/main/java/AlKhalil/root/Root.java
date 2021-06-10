/*
 * Root.java
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
package AlKhalil.root;

/**
 * <p>This class provides implementations for arabic roots. 
 * each root is characterized by its value and a set of voweled pattern ids separated by white space.
 *
 * </p>
 *
 */
public class Root {
    // ------------------------------------------------------  Class Variables

    /** the value of the root  */
    private String val;
    /** voweled patterns ids separated by white space*/
    private String vect;

    /** Creates a new instance of Root */
    public Root() {
    }

    // --------------------------------------------------------- Public Methods
    /** returns the value of the root   */
    public String getVal() {
        return val;
    }

    /** returns the voweled patterns ids of the root */
    public String getVect() {
        return vect;
    }

    /** sets  the value of the root
    @param val the value of the root
     */
    public void setVal(String val) {
        this.val = val;
    }

    /** sets  the voweled patterns ids of the root
    @param vect the voweled patterns ids of the root separated by white space
     */
    public void setVect(String vect) {
        this.vect = vect;
    }
}
