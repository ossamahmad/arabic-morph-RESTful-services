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
 * <p>This class provides implementations for unvoweled arabic patterns. each pattern is characterized by its value, root extraction rules rules
 * and the ids of all voweled forms of the pattern as defined in the xml databases.
 *
 * .</p>
 *
 */
public class UnvoweledPattern {
    // ------------------------------------------------------  Class Variables

    /** the value of the pattern */
    private String value;
    /** root extraction rules according to the pattern */
    private String rules;
    /** ids of all voweled forms of the pattern */
    private String ids;

    /**
     * Creates a new instance of UnvoweledPattern
     */
    public UnvoweledPattern() {
    }

    // --------------------------------------------------------- Public Methods
    /** returns the value of the pattern */
    public String getValue() {
        return value;
    }

    /** returns the rules of the pattern */
    public String getRules() {
        return rules;
    }

    /** returns the ids of the pattern */
    public String getIds() {
        return ids;
    }

    /** sets the the value of the pattern
    @param value unvoweled form of an arabic pattern
     */
    public void setValue(String value) {
        this.value = value;
    }

    /** sets the the rules of the pattern
    @param rules root extraction rules separated by white space
     */
    public void setRules(String rules) {
        this.rules = rules;
    }

    /** sets the the ids of the pattern
    @param ids identifiants of voweled patterns separated by white space
     */
    public void setIds(String ids) {
        this.ids = ids;
    }
}
