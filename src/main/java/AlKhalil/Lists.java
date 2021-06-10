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
package AlKhalil;

import java.util.*;
import AlKhalil.underived.*;
import AlKhalil.segment.*;
import AlKhalil.root.*;
import AlKhalil.pattern.*;

/**
 * <p>This class provides implementations for different lists used by Alkhalil.
 *
 * </p>
 */
public class Lists {
    // ------------------------------------------------------  Class Variables

    /** the tool words list */
    private List toolwords = new LinkedList();
    /** the proper nouns hashmap, keys are the unvoweled forms of the proper nouns */
    private HashMap propernouns = new HashMap();
    /** the exceptional words hashmap, keys are the unvoweled forms of the exceptional words */
    private HashMap exceptionalwords = new HashMap();
    /** the prefixes list */
    private List prefixes = new LinkedList();
    /** the suffixes list */
    private List suffixes = new LinkedList();
    /** the roots list */
    private List roots = new LinkedList();
    /** the unvoweled patterns list */
    private List UnvoweledPatterns = new LinkedList();

    /** Creates a new instance of Lists */
    public Lists() {
    }
    // --------------------------------------------------------- Public Methods

    /** return the tool words list
     */
    public List getToolwords() {
        return toolwords;
    }

    /** return the proper nouns list
     */
    public HashMap getPropernouns() {
        return propernouns;
    }

    /** return the exceptional words list
     */
    public HashMap getExceptionalwords() {
        return exceptionalwords;
    }

    /** return the prefixes list
     */
    public List getPrefixes() {
        return prefixes;
    }

    /** return the suffixes list
     */
    public List getSuffixes() {
        return suffixes;
    }

    /** return the roots list
     */
    public List getRoots() {
        return roots;
    }

    /** return the unvoweled patterns list
     */
    public List getPatterns() {
        return UnvoweledPatterns;
    }

    /** add a new tool word the tool words list
     * @param tw a tool word
     */
    public void addToolword(ToolWord tw) {
        toolwords.add(tw);
    }

    /** add a new proper noun the proper nouns hashmap
     * @param pn a proper noun
     */
    public void addPropernoun(ProperNoun pn) {
        propernouns.put(pn.getUnvoweledform(), pn);
    }

    /** add a new exceptional word the exceptional words hashmap
     * @param ew an exceptional word
     */
    public void addExceptionalword(ExceptionalWord ew) {
        exceptionalwords.put(ew.getUnvoweledform(), ew);
    }

    /** add a new prefix the prefixes list
     * @param p a prefix
     */
    public void addPrefixe(Prefixe p) {
        prefixes.add(p);
    }

    /** add a new suffix the suffixes list
     * @param s a suffix
     */
    public void addSuffixe(Suffixe s) {
        suffixes.add(s);
    }

    /** add a new root the roots list
     * @param nr a root
     */
    public void addRoot(Root nr) {
        roots.add(nr);
    }

    /** add a new pattern the unvoweled pattern list
     * @param unp an unvoweled pattern
     */
    public void addPattern(UnvoweledPattern unp) {
        UnvoweledPatterns.add(unp);
    }
}
