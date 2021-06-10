/*
 * DbLoader.java
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
package AlKhalil;

import AlKhalil.pattern.*;
import AlKhalil.root.Root;
import AlKhalil.segment.Prefixe;
import AlKhalil.segment.Suffixe;
import AlKhalil.ui.Settings;
import AlKhalil.underived.ExceptionalWord;
import AlKhalil.underived.ProperNoun;
import AlKhalil.underived.ToolWord;
import org.apache.commons.digester.Digester;

import java.io.*;
import java.util.HashMap;

/**
 * <p>This class provides implementations to load different xml databases used by Alkhalil.
 *
 * </p>
 */
public class DbLoader {

    /** Creates a new instance of DbLoader */
    public DbLoader() {
    }

    /** returns the prefixes list loaded from  prefixes.xml */
    public Lists LoadPrefixes() throws Exception {
        Digester digester = new Digester();
        File pref = new File("db/prefixes.xml");
        digester.addObjectCreate("prefixes", Lists.class);
        digester.addObjectCreate("prefixes/prefixe", Prefixe.class);
        digester.addSetProperties("prefixes/prefixe", "unvoweledform", "unvoweledform");
        digester.addSetProperties("prefixes/prefixe", "voweledform", "voweledform");
        digester.addSetProperties("prefixes/prefixe", "desc", "desc");
        digester.addSetProperties("prefixes/prefixe", "classe", "classe");
        digester.addSetNext("prefixes/prefixe", "addPrefixe");

        return (Lists) digester.parse(pref);
    }

    /** returns the suffixes list loaded from  suffixes.xml */
    public Lists LoadSuffixes() throws Exception {
        Digester digester = new Digester();
        File suf = new File("db/suffixes.xml");
        digester.addObjectCreate("suffixes", Lists.class);
        digester.addObjectCreate("suffixes/suffixe", Suffixe.class);
        digester.addSetProperties("suffixes/suffixe", "unvoweledform", "unvoweledform");
        digester.addSetProperties("suffixes/suffixe", "voweledform", "voweledform");
        digester.addSetProperties("suffixes/suffixe", "desc", "desc");
        digester.addSetProperties("suffixes/suffixe", "classe", "classe");
        digester.addSetNext("suffixes/suffixe", "addSuffixe");



        return (Lists) digester.parse(suf);
    }

    /** returns the unvoweled nominal patterns list  according to their length
     * @param len the length of the unvovoweled pattern
     */
    public Lists LoadUnvoweledNominalPatterns(int len) throws Exception {
        Digester digester = new Digester();
        if (len >= 2 && len <= 9) {
            File np = new File("db/nouns/patterns/Unvoweled/UnvoweledNominalPatterns" + len + ".xml");
            digester.addObjectCreate("patterns", Lists.class);
            digester.addObjectCreate("patterns/pattern", UnvoweledPattern.class);
            digester.addSetProperties("patterns/pattern", "value", "value");

            digester.addSetProperties("patterns/pattern", "rules", "rules");

            digester.addSetProperties("patterns/pattern", "ids", "ids");
            digester.addSetNext("patterns/pattern", "addPattern");




            return (Lists) digester.parse(np);
        } else {
            return new Lists();
        }
    }

    /** returns the unvoweled verbal patterns list  according to their length
     * @param len the length of the unvovoweled pattern
     */
    public Lists LoadUnvoweledVerbalPatterns(int len) throws Exception {
        Digester digester = new Digester();
        if (len >= 2 && len <= 9) {
            File vp = new File("db/verbs/patterns/Unvoweled/UnvoweledVerbalPatterns" + len + ".xml");
            digester.addObjectCreate("patterns", Lists.class);
            digester.addObjectCreate("patterns/pattern", UnvoweledPattern.class);
            digester.addSetProperties("patterns/pattern", "value", "value");

            digester.addSetProperties("patterns/pattern", "rules", "rules");

            digester.addSetProperties("patterns/pattern", "ids", "ids");




            digester.addSetNext("patterns/pattern", "addPattern");




            return (Lists) digester.parse(vp);
        } else {
            return new Lists();
        }
    }

    /** returns the voweled nominal patterns list  according to their length
     * @param len the length of the vovoweled pattern
     */
    public VoweledNominalPatternsList LoadVoweledNominalPatterns(int len) throws Exception {
        Digester digester = new Digester();
        if (len >= 2 && len <= 9) {
            File np = new File("db/nouns/patterns/Voweled/VoweledNominalPatterns" + len + ".xml");
            digester.addObjectCreate("patterns", VoweledNominalPatternsList.class);
            digester.addObjectCreate("patterns/pattern", VoweledNominalPattern.class);
            digester.addSetProperties("patterns/pattern", "id", "id");

            digester.addSetProperties("patterns/pattern", "diac", "diac");
            digester.addSetProperties("patterns/pattern", "canonic", "canonic");
            digester.addSetProperties("patterns/pattern", "type", "type");
            digester.addSetProperties("patterns/pattern", "cas", "cas");
            digester.addSetProperties("patterns/pattern", "ncg", "ncg");




            digester.addSetNext("patterns/pattern", "addPattern");




            return (VoweledNominalPatternsList) digester.parse(np);
        } else {
            return new VoweledNominalPatternsList();
        }
    }

    /** returns the voweled verbal patterns list  according to their length
     * @param len the length of the vovoweled pattern
     */
    public VoweledVerbalPatternsList LoadVoweledVerbalPatterns(int len) throws Exception {
        Digester digester = new Digester();
        if (len >= 2 && len <= 9) {
            File np = new File("db/verbs/patterns/Voweled/VoweledVerbalPatterns" + len + ".xml");
            digester.addObjectCreate("patterns", VoweledVerbalPatternsList.class);
            digester.addObjectCreate("patterns/pattern", VoweledVerbalPattern.class);

            digester.addSetProperties("patterns/pattern", "id", "id");

            digester.addSetProperties("patterns/pattern", "diac", "diac");
            digester.addSetProperties("patterns/pattern", "canonic", "canonic");
            digester.addSetProperties("patterns/pattern", "type", "type");
            digester.addSetProperties("patterns/pattern", "aug", "aug");
            digester.addSetProperties("patterns/pattern", "cas", "cas");
            digester.addSetProperties("patterns/pattern", "ncg", "ncg");
            digester.addSetProperties("patterns/pattern", "trans", "trans");

//     digester.addSetNext("patterns/pattern/pos","addVoweledPattern");


            digester.addSetNext("patterns/pattern", "addPattern");




            return (VoweledVerbalPatternsList) digester.parse(np);
        } else {
            return new VoweledVerbalPatternsList();
        }
    }

    /** returns a hashmap of arabic roots associated to their indexes
     * @param f the all root file name.
     */
    public HashMap LoadRoots(String f) throws Exception {
        HashMap HashRoots = new HashMap();

        File file = new File(f);

        String line;
        StringBuffer buf = new StringBuffer();

        try {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    new FileInputStream(file), "UTF-8"));


            while ((line = in.readLine()) != null) {

                String t[] = line.split("\t");

                HashRoots.put(t[0], t[1]);
            }

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }




        return HashRoots;
    }

    /** returns the nominal roots list  according to their first character
     * @param fc the first character of the root
     */
    public Lists LoadNominalRootsByFirstChar(String fc) throws Exception {

        Digester digester = new Digester();
        File nr;
        if (Settings.dbchoice) {
            nr = new File("db/nouns/roots2/" + fc + ".xml");
        } else {
            nr = new File("db/nouns/roots1/" + fc + ".xml");
        }

        digester.addObjectCreate("roots", Lists.class);
        digester.addObjectCreate("roots/root", Root.class);
        digester.addSetProperties("roots/root", "val", "val");
        digester.addSetProperties("roots/root", "vect", "vect");


        digester.addSetNext("roots/root", "addRoot");




        return (Lists) digester.parse(nr);
    }

    /** returns the verbal roots list  according to their first character
     * @param fc the first character of the root
     */
    public Lists LoadVerbalRootsByFirstChar(String fc) throws Exception {

        Digester digester = new Digester();
        File nr;
        if (Settings.dbchoice) {
            nr = new File("db/verbs/roots2/" + fc + ".xml");
        } else {
            nr = new File("db/verbs/roots1/" + fc + ".xml");
        }

        digester.addObjectCreate("roots", Lists.class);
        digester.addObjectCreate("roots/root", Root.class);
        digester.addSetProperties("roots/root", "val", "val");
        digester.addSetProperties("roots/root", "vect", "vect");

        digester.addSetNext("roots/root", "addRoot");




        return (Lists) digester.parse(nr);
    }

    /** returns the tool words list
     *
     */
    public Lists LoadToolWords() throws Exception {
        Digester digester = new Digester();
        File tw = new File("db/specialwords/toolwords.xml");
        //digester.addObjectCreate("toolwords",ToolWordsList.class);
        digester.addObjectCreate("toolwords", Lists.class);
        digester.addObjectCreate("toolwords/toolword", ToolWord.class);
        digester.addSetProperties("toolwords/toolword", "unvoweledform", "unvoweledform");
        digester.addSetProperties("toolwords/toolword", "voweledform", "voweledform");
        digester.addSetProperties("toolwords/toolword", "type", "type");
        digester.addSetProperties("toolwords/toolword", "prefixeclass", "prefixeclass");
        digester.addSetProperties("toolwords/toolword", "suffixeclass", "suffixeclass");
        digester.addSetProperties("toolwords/toolword", "priority", "priority");
        digester.addSetNext("toolwords/toolword", "addToolword");

        return (Lists) digester.parse(tw);
    }

    /** returns the proper nouns list
     *
     */
    public Lists LoadProperNouns() throws Exception {
        Digester digester = new Digester();
        File pn = new File("db/specialwords/propernouns.xml");
        digester.addObjectCreate("propernouns", Lists.class);
        digester.addObjectCreate("propernouns/propernoun", ProperNoun.class);
        digester.addSetProperties("propernouns/propernoun", "unvoweledform", "unvoweledform");
        digester.addSetProperties("propernouns/propernoun", "voweledform", "voweledform");
        digester.addSetProperties("propernouns/propernoun", "type", "type");

        digester.addSetNext("propernouns/propernoun", "addPropernoun");

        return (Lists) digester.parse(pn);
    }

    /** returns the exceptional words list
     *
     */
    public Lists LoadExceptionalWords() throws Exception {
        Digester digester = new Digester();
        File pn = new File("db/specialwords/exceptionalwords.xml");
        digester.addObjectCreate("exceptionalwords", Lists.class);
        digester.addObjectCreate("exceptionalwords/exceptionalword", ExceptionalWord.class);
        digester.addSetProperties("exceptionalwords/exceptionalword", "unvoweledform", "unvoweledform");
        digester.addSetProperties("exceptionalwords/exceptionalword", "voweledform", "voweledform");
        digester.addSetProperties("exceptionalwords/exceptionalword", "prefix", "prefix");
        digester.addSetProperties("exceptionalwords/exceptionalword", "stem", "stem");
        digester.addSetProperties("exceptionalwords/exceptionalword", "type", "type");
        digester.addSetProperties("exceptionalwords/exceptionalword", "suffix", "suffix");

        digester.addSetNext("exceptionalwords/exceptionalword", "addExceptionalword");

        return (Lists) digester.parse(pn);
    }
}