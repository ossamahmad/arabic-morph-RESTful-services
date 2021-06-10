 /* ALKHALIL MORPHO SYS -- An open source programm.
 *
 * Copyright (C) 2010.
 *
 * This program is free software, distributed under the terms of
 * the GNU General Public License Version 3. For more informations see web site at :
 * http://www.gnu.org/licenses/gpl.txt
 */
package AlKhalil.analyse;

import AlKhalil.DbLoader;
import AlKhalil.pattern.UnvoweledPattern;
import AlKhalil.pattern.VoweledNominalPattern;
import AlKhalil.pattern.VoweledVerbalPattern;
import AlKhalil.result.Result;
import AlKhalil.root.Root;
import AlKhalil.segment.Prefixe;
import AlKhalil.segment.Segment;
import AlKhalil.segment.Stem;
import AlKhalil.segment.Suffixe;
import AlKhalil.underived.ExceptionalWord;
import AlKhalil.underived.ProperNoun;
import AlKhalil.underived.ToolWord;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 * <p>This class provides implementations for the morphological analysis process
 * an instance of this class is used by the Gui.java class to analyze tokens.
 * .</p>
 *
 *
 */
public class Analyzer {

    // ------------------------------------------------------  Class Variables
    /** Used to Load the xml databases */
    public DbLoader db;
    /** Contains the list of  prefixes loaded from prefixes.xml*/
    private List Prefixes;
    /** Contains the list of  suffixes loaded from suffixes.xml */
    private List Suffixes;
    /** Contains the list of  toolwords loaded from toolwords.xml */
    private List ToolWords;
    /** a Hash Map containing all analysis results  */
    public static HashMap allResults = new HashMap();
    /** another Hash Map containing all analysis results */
    public static HashMap allResultsBis = new HashMap();
    /** Contains the verbal roots loaded from AllRoots1.txt or from AllRoots2.txt depending on the settings*/
    public HashMap VRoots = new HashMap();
    /**  Contains the  nominal roots loaded from AllRoots1.txt or from AllRoots2.txt depending on the settings */
    public HashMap NRoots = new HashMap();
    /** Contains all verbal solutions of a given token */
    HashMap VSolutions = new HashMap();
    /** Contains all nominal solutions of a given token*/
    public static HashMap NSolutions = new HashMap();
    /** a Hash Map containing all verbal patterns found during the anlysis of a text */
    public static HashMap VPatterns = new HashMap();
    /**a Hash Map containing all nominal patterns found during the anlysis of a text */
    public static HashMap NPatterns = new HashMap();
    /** Contains all tool words found during the anlysis of a text */
    HashMap TW = new HashMap();
    /** Contains all proper nouns loaded from propernouns.xml */
    HashMap PN = new HashMap();
    /** Contains all exceptional words loaded from exceptionalwords.xml */
    HashMap EW = new HashMap();
    /** Contains all unvoweled verbal patterns loaded from
     * UnvoweledVerbalPatterns2.xml to UnvoweledVerbalPatterns9.xml */
    HashMap VUPatterns = new HashMap();
    /** Contains all unvoweled nominal patterns loaded from
     * UnvoweledNominalPatterns2.xml to UnvoweledNominalPatterns9.xml */
    HashMap NUPatterns = new HashMap();
    /** Contains all voweled verbal patterns loaded from
     * VoweledVerbalPatterns2.xml to VoweledVerbalPatterns9.xml */
    HashMap VVPatterns = new HashMap();
    /** Contains all voweled nominal patterns loaded from
     * VoweledNominalPatterns2.xml to VoweledNominalPatterns9.xml */
    HashMap NVPatterns = new HashMap();

    // --------------------------------------------------------- Constuctor
    /**
     * <p>Constructs an instance the analyzer
     * it use a Dbloader instance to load:
     * Prefixes, Suffixes,Tool Words,Proper nouns and the differente verbal and nominal databases
     * .</p>
     */
    public Analyzer() {
        db = new DbLoader();
        try {
            VRoots = db.LoadRoots("db/AllRoots1.txt");
            NRoots = db.LoadRoots("db/AllRoots1.txt");

            Prefixes = db.LoadPrefixes().getPrefixes();
            Suffixes = db.LoadSuffixes().getSuffixes();
            ToolWords = db.LoadToolWords().getToolwords();

            PN = db.LoadProperNouns().getPropernouns();
            EW = db.LoadExceptionalWords().getExceptionalwords();

            for (int i = 2; i <= 9; i++) {
                NUPatterns.put(i, db.LoadUnvoweledNominalPatterns(i).getPatterns());
                VUPatterns.put(i, db.LoadUnvoweledVerbalPatterns(i).getPatterns());
                NVPatterns.put(i, db.LoadVoweledNominalPatterns(i).getPatterns());
                VVPatterns.put(i, db.LoadVoweledVerbalPatterns(i).getPatterns());
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // --------------------------------------------------------- Public Methods
    /**
     * <p>Given an arabic word this method returns a list of possible sgementations
     * in three elements (prefix+stem+suffixe). The returned list consists of objects
     * of Segment.java class
     * .</p>
     *
     * @param unvoweledWord the unvoweled form of a tokenized arabic word
     *
     */
    public List Segment(String unvoweledWord) throws Exception {


        List result = new LinkedList();// the list to be returned

        int i = 0;
        boolean valid = true;// the segmentation process will be stopped if this boolean became false
        while (valid && (i < unvoweledWord.length())) {


            String preftemp = unvoweledWord.substring(0, i);// a candidate prefixe of length i

            String remaining = unvoweledWord.substring(i);

            List possiblePrefixes = PossiblePrefixes(preftemp);// returns a list of possible prefixes having the string preftemp as unvoweled form
            if (!possiblePrefixes.isEmpty()) {
                for (int j = 0; j < remaining.length(); j++) {

                    String suftemp = remaining.substring(remaining.length() - j); // a candidate suffix of length j

                    String stem = remaining.substring(0, remaining.length() - j);

                    List possibleSuffixes = PossibleSuffixes(suftemp);// returns a list of possible suffixes having the string suftemp as unvoweled form

                    if (!possibleSuffixes.isEmpty()) {

                        Iterator itp = possiblePrefixes.iterator();
                        while (itp.hasNext()) {

                            Prefixe p = (Prefixe) itp.next();

                            Iterator its = possibleSuffixes.iterator();

                            while (its.hasNext()) {

                                Suffixe s = (Suffixe) its.next();

                                List Alternatives = new LinkedList();//in some cases the stem resulting from segmentation may have another forms
                                //when for example it ends with ت an additional segmentation replacing this ت
                                // by ة should be considered.

                                Alternatives.add(stem);
                                if (stem.endsWith("ت")) {
                                    char[] s1 = stem.toCharArray();
                                    s1[s1.length - 1] = 'ة';
                                    String s2 = "";
                                    for (int k = 0; k < s1.length; k++) {
                                        s2 = s2 + s1[k];
                                    }
                                    Alternatives.add(s2);
                                }
                                if (stem.endsWith("و")) {

                                    String s2 = stem + "ا";

                                    Alternatives.add(s2);
                                }

                                for (int k = 0; k < Alternatives.size(); k++) {

                                    String sch = (String) Alternatives.get(k);
                                    Stem st = new Stem();

                                    st.setUnvoweledform(sch);

                                    Segment segment = new Segment(p, st, s);

                                    if (isValidSegment(segment)) { // Segment validation: compatibility between the elements, stem length etc..

                                        result.add(segment);


                                    }


                                }



                            }

                        }
                    }


                }


            } else {

                if ((unvoweledWord.charAt(i - 1) == 'ا') && (unvoweledWord.charAt(i) == 'ل')) {
                } else {
                    valid = false; //will stop the segmentation loop

                }

            }


            i++;
        }

        return result;
    }

    /**
     * <p>This method returns the analysis results list of a tokenized arabic word.
     * These results come from several levels of treatment. firsrt of all of the segementation,
     * then the segments are compared to the list of exceptional words,
     * proper nouns, tool words and finally the possible nominal and verbal solutions are considered.</p>
     *
     * @param normalizedWord the normalized form of the token to be analyzed
     * @param unvoweledWord the unvoweled form of the token to be analyzed
     *
     *
     */
    public List Analyze(String normalizedWord, String unvoweledWord) throws Exception {

        List resulL = new LinkedList(); // list to be returned
        if (NSolutions.containsKey(normalizedWord)) { // if the word was already analyzed
            resulL = (List) NSolutions.get(normalizedWord);
        } else {
            List Segments = Segment(unvoweledWord); // segmentation
            Iterator it = Segments.iterator();


            if (EW.containsKey(unvoweledWord)) { // test whether the word is exceptional

                ExceptionalWord ew = (ExceptionalWord) EW.get(unvoweledWord);

                Result r = new Result(ew.getVoweledform(), ew.getPrefix(), ew.getStem(), ew.getType(), "#", "#", "#", ew.getSuffix(), "1");

                resulL.add(r); // add the exceptional word found to the result list to be returned

            } else {

                while (it.hasNext()) {

                    Segment segment = (Segment) it.next();
                    Prefixe p = segment.getPrefixe();
                    Suffixe s = segment.getSuffixe();

                    //////////////////////////////////////ToolWords
                    List toolwordlist = new LinkedList();


                    toolwordlist = PossibleToolWords(segment); // get all possible tool words given the segment

                    Iterator itw = toolwordlist.iterator();

                    while (itw.hasNext()) { //for each tool word

                        ToolWord tw = (ToolWord) itw.next();

                        // befor creating the voweled form of the entire word some suffixes cases should be discussed
                        if (s.getUnvoweledform().equals("ه")) {
                            if (tw.getVoweledform().endsWith("ي") || tw.getVoweledform().endsWith("يْ") || tw.getVoweledform().endsWith("ِ")) {
                                s.setVoweledform("هِ");
                            } else {
                                s.setVoweledform("هُ");
                            }
                        }

                        if (s.getUnvoweledform().equals("هما")) {
                            if (tw.getVoweledform().endsWith("ي") || tw.getVoweledform().endsWith("يْ") || tw.getVoweledform().endsWith("ِ")) {
                                s.setVoweledform("هِمَا");
                            } else {
                                s.setVoweledform("هُمَا");
                            }
                        }
                        if (s.getUnvoweledform().equals("هم")) {
                            if (tw.getVoweledform().endsWith("ي") || tw.getVoweledform().endsWith("يْ") || tw.getVoweledform().endsWith("ِ")) {
                                s.setVoweledform("هِمْ");
                            } else {
                                s.setVoweledform("هُمْ");
                            }
                        }
                        if (s.getUnvoweledform().equals("هن")) {
                            if (tw.getVoweledform().endsWith("ي") || tw.getVoweledform().endsWith("يْ") || tw.getVoweledform().endsWith("ِ")) {
                                s.setVoweledform("هِنَّ");
                            } else {
                                s.setVoweledform("هُنَّ");
                            }
                        }

                        // create the voweled form the entire tool word
                        String vowledWord = p.getVoweledform() + tw.getVoweledform() + s.getVoweledform();
                        String pf = p.getVoweledform();
                        String sf = s.getVoweledform();
                        if (p.getVoweledform().equals("")) {
                            pf = "#"; // replace the empty prefix by # for display purpose
                        }
                        if (s.getVoweledform().equals("")) {
                            sf = "#"; //replace the empty suffix by #
                        }

                        if (!notCompatible(normalizedWord, vowledWord)) { // check the compatibility  between
                            // the word voweled form produced by the analyzer and the short vowels possibly
                            //existing  in the normlized form of the input word


                            Result r = new Result(vowledWord, pf + " " + p.getDesc(), segment.getStem().getUnvoweledform(), tw.getType(), "#", "#", "#", sf + " " + s.getDesc(), tw.getPriority());

                            resulL.add(r); // add the tool word found to the result list to be returned
                        }


                    }
                    //////////////////////////////////////////////////////////////////Proper Nouns
                    if (PN.containsKey(segment.getStem().getUnvoweledform()) && (p.getClasse().indexOf("C1") != -1) && (s.getClasse().indexOf("C1") != -1)) {

                        // a segment may be interpreted as a proper noun if the stem belongs to the proper nouns PN hashmap and the prefix and suffix classes are C1
                        ProperNoun pn = (ProperNoun) PN.get(segment.getStem().getUnvoweledform());

                        //create the voweled form of the proper noun
                        String vowledWord = segment.getPrefixe().getVoweledform() + pn.getVoweledform() + segment.getSuffixe().getVoweledform();
                        String pf = segment.getPrefixe().getVoweledform();
                        String sf = s.getVoweledform();
                        if (segment.getPrefixe().getVoweledform().equals("")) {
                            pf = "#";// replace the empty prefix by # for display purpose
                        }
                        if (segment.getSuffixe().getVoweledform().equals("")) {
                            sf = "#";// replace the empty suffix by #
                        }
                       // System.out.println(normalizedWord);
                       // System.out.println(vowledWord);

                        if (!notCompatible(normalizedWord, vowledWord)) {// check the compatibility  between
                            // the word voweled form produced by the analyzer and the short vowels possibly
                            //existing  in the normlized form of the input word

                            Result r = new Result(vowledWord, pf + " " + p.getDesc(), segment.getStem().getUnvoweledform(), pn.getType(), "#", "#", "#", sf + " " + s.getDesc(), "102");
                            resulL.add(r);//add the  proper noun to the result list to be returned



                        }
                    }


                    //////////////////////////////////////////////////////////////////Nouns
                    List nounSolutionList = new LinkedList();

                    if ((segment.getPrefixe().getClasse().indexOf("V") == -1) && (segment.getSuffixe().getClasse().indexOf("V") == -1)) {
                        // a word can have possible nominal solutions if the prefix and suffix classes are not verbal (V)
                        nounSolutionList = PossibleNominalSolutions(segment, normalizedWord); //get all possible nominal solutions of the inptu word
                        resulL.addAll(nounSolutionList);
                    }




                    ////////////////////////////////////////////////////////////////Verbs
                    List verbSolutionList = new LinkedList();

                    if ((segment.getPrefixe().getClasse().indexOf("N") == -1) && (segment.getSuffixe().getClasse().indexOf("N") == -1)) {
                        // a word can have possible verbal solutions if the prefix and suffix classes are not nominal (N)
                        verbSolutionList = PossibleVerbalSolutions(segment, normalizedWord);  //get all possible verbal solutions of the inptu word
                        resulL.addAll(verbSolutionList);
                    }
                }



            }



            resulL = Sort(resulL); //  The entire results list is sorted depending on the prioririty defined in the Result objects
            NSolutions.put(normalizedWord, resulL);
        }
        //---------------------------------------------------------------
        return resulL;
    }

    /**
     * <p>This method returns a sorted list of the results depending on the prioririty defined in the Result objects .</p>
     *
     * @param resul analysis results of a given word
     */
    public List Sort(List resul) {
        List result = new LinkedList();
        result = resul;
        if (!result.isEmpty()) {
            for (int i = 0; i < result.size() - 1; i++) {
                Result ri = (Result) result.get(i);
                int ind = i;
                Result temp = ri;
                for (int j = i; j < result.size(); j++) {
                    Result rj = (Result) result.get(j);
                    if (rj.getPriority().compareTo(temp.getPriority()) < 0) {
                        ind = j;
                        temp = rj;
                    }


                }
                result.add(i, temp);
                result.remove(i + 1);
                result.add(ind, ri);
                result.remove(ind + 1);
            }

        }

        return result;
    }

    /**
     *
     * <p>This method returns a list of Prefixes objects that have the unvoweled form equal to the input string pref</p>
     *
     * @param pref unvoweled word substring to be compared with the unvoweled forms of the Prefixes list
     *
     *
     */
    public List PossiblePrefixes(String pref) throws Exception {

        List result = new LinkedList();

        Iterator it = Prefixes.iterator();
        while (it.hasNext()) {

            Prefixe p = (Prefixe) it.next();

            if (pref.equals(p.getUnvoweledform())) {

                result.add(p);
            }

        }
        return result;


    }

    /**
     *
     * <p>This method returns a list of suffixes objects that have the unvoweled form equal to the input string suf</p>
     *
     * @param suf unvoweled word substring to be compared with the unvoweled forms of the Suffixes list
     *
     *
     */
    public List PossibleSuffixes(String suf) throws Exception {

        List result = new LinkedList();

        Iterator it = Suffixes.iterator();
        while (it.hasNext()) {

            Suffixe s = (Suffixe) it.next();

            if (s.getUnvoweledform().equals(suf)) {
                result.add(s);
            }

        }
        return result;


    }

    /**
     *
     * <p>This method tests whether the given sgment is valid according to the prefix and suffix classes and the stem length</p>
     *
     * @param segment possible segmentation of the word to be validated
     *
     *
     */
    public boolean isValidSegment(Segment segment) {
        Prefixe p = segment.getPrefixe();
        Stem st = segment.getStem();
        Suffixe s = segment.getSuffixe();
        String prefix_classe = p.getClasse();
        String suffixe_classe = s.getClasse();
        int stem_len = st.getStemLength();

        //Validation criteria
        if (((prefix_classe.indexOf("N") != -1) && (suffixe_classe.indexOf("V") != -1)) || ((prefix_classe.indexOf("V") != -1) && (suffixe_classe.indexOf("N") != -1)) || (st.getStemLength() < 2)/*||(st.getStemLength()>9)*/ || ((prefix_classe.equals("N1") || prefix_classe.equals("N2") || prefix_classe.equals("N3") || prefix_classe.equals("N5")) && (!segment.getSuffixe().getUnvoweledform().equals("")))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * <p>This method returns a list of possible tool words for a  given segment</p>
     *
     * @param segment possible segmentation of the word to be analyzed
     *

     */
    public List PossibleToolWords(Segment seg) throws Exception {

        List result = new LinkedList();
        List resultemp = new LinkedList();
        Iterator it = ToolWords.iterator();
        if (TW.containsKey(seg.getStem().getUnvoweledform())) {
            resultemp = (List) TW.get(seg.getStem().getUnvoweledform());

        } else {


            while (it.hasNext()) {

                ToolWord tw = (ToolWord) it.next();

                if (seg.getStem().getUnvoweledform().equals(tw.getUnvoweledform())) {

                    resultemp.add(tw);

                }

            }

            TW.put(seg.getStem().getUnvoweledform(), result);

        }
        Iterator itr = resultemp.iterator();
        while (itr.hasNext()) {

            ToolWord tw = (ToolWord) itr.next();


            if ((tw.getPrefixeclass().indexOf(seg.getPrefixe().getClasse()) != -1) && (tw.getSuffixeclass().indexOf(seg.getSuffixe().getClasse()) != -1)) {
                result.add(tw);//add the toolword if the segment prefix/suffix class appears in the tool word prefix/suffix classes
            }


        }
        return result;


    }

    /**
     * <p>This method returns the possible nominal patterns of a given stem.
     * Firstly by comparing the string parameter st to the unvoweled nominal patterns with the same length than it.
     * this comaprison may generate some possible roots. Finaly it returns the intersection between the voweled patterns of theses roots
     * and the possible voweled patterns of the stem</p>
     *
     * @param st the unvoweled form of a stem
     *
     *
     *
     */
    public HashMap PossibleNominalPatterns(String st) throws Exception {

        HashMap result = new HashMap(); // Hash map to be returned
        String unpValue;

        List UnvowledNominalPatterns = (List) NUPatterns.get(st.length()); //unvoweled nominal patterns with the same length than st
        if (UnvowledNominalPatterns != null) {
            Iterator it = UnvowledNominalPatterns.iterator();


            List vowPatterns = new LinkedList();
            while (it.hasNext()) {

                UnvoweledPattern unp = (UnvoweledPattern) it.next();
                unpValue = unp.getValue();

                int test = 0; // test remains == 0 if unp shares the same infixes than st
                String t[] = st.split("");
                String tt[] = unpValue.split("");
                for (int i = 0; i < tt.length; i++) { //check the compatibility between st and unp

                    if ((tt[i].indexOf("ف") == -1) && (tt[i].indexOf("ع") == -1) && (tt[i].indexOf("ل") == -1)) {

                        if (!tt[i].equals(t[i])) {
                            test++; // in this case  unp is not compatible with st
                            break;
                        }
                    }

                }
                if (test == 0) {




                    HashMap ROOT = new HashMap();

                    String[] roots = getRoot(st, unp.getRules()).split(" ");

                    for (int rr = 0; rr < roots.length; rr++) {
                        String root = roots[rr];
                        if (NRoots.get(root) != null) {
                            Root vr = new Root();


                            int index = Integer.parseInt(NRoots.get(root).toString());
                            vr = getNominalRoot(root, index);

                            if (vr != null) {
                                List intersection = new LinkedList();
                                intersection = intersection(vr.getVect().split(" "), unp.getIds().split(" "));

                                //---------------------------------------------
                                if (!intersection.isEmpty()) {

                                    List vnpl = (List) NVPatterns.get(unp.getValue().length());



                                    vowPatterns = intersection(vnpl, intersection, false);

                                    ROOT.put(root, vowPatterns);


                                }



                            }

                        }



                    }
                    result.put(unp, ROOT);

                }


            }
            NPatterns.put(st, result);
        }
        return result;


    }

    /**
     * <p>This method returns the possible verbal patterns of a given stem.
     * Firstly by comparing the string parameter st to the unvoweled verbal patterns with the same length than it.
     * this comaprison may generate some possible roots. Finaly it returns the intersection between the voweled patterns of theses roots
     * and the possible voweled patterns of the stem</p>
     *
     * @param st the unvoweled form of a stem
     *
     */
    public HashMap PossibleVerbalPatterns(String st) throws Exception {

        HashMap result = new HashMap();// hash map to be returned
        String unpValue;

        List UnvowledVerbalPatterns = (List) VUPatterns.get(st.length());//unvoweled verbal patterns with the same length than st
        if (UnvowledVerbalPatterns != null) {
            Iterator it = UnvowledVerbalPatterns.iterator();

            List vowPatterns = new LinkedList();
            while (it.hasNext()) {

                UnvoweledPattern unp = (UnvoweledPattern) it.next();
                unpValue = unp.getValue();

                int test = 0; // test remains == 0 if unp shares the same infixes than st
                String t[] = st.split("");
                String tt[] = unpValue.split("");
                for (int i = 0; i < tt.length; i++) {//check the compatibility between st and unp

                    if ((tt[i].indexOf("ف") == -1) && (tt[i].indexOf("ع") == -1) && (tt[i].indexOf("ل") == -1)) {

                        if (!tt[i].equals(t[i])) {
                            test++; // in this case  unp is not compatible with st
                            break;
                        }
                    }

                }
                if (test == 0) {



                    HashMap ROOT = new HashMap();

                    String[] roots = getRoot(st, unp.getRules()).split(" ");

                    for (int rr = 0; rr < roots.length; rr++) {
                        String root = roots[rr];
                        if (VRoots.get(root) != null) {

                            Root vr = new Root();


                            int index = Integer.parseInt(VRoots.get(root).toString());
                            vr = getVerbalRoot(root, index);

                            if (vr != null) {
                                List intersection = new LinkedList();
                                intersection = intersection(vr.getVect().split(" "), unp.getIds().split(" "));


                                if (!intersection.isEmpty()) {

                                    List vnpl = (List) VVPatterns.get(unp.getValue().length());
                                    vowPatterns = intersection(vnpl, intersection, true);

                                    ROOT.put(root, vowPatterns);


                                }



                            }

                        }



                    }
                    result.put(unp, ROOT);

                }


            }
            VPatterns.put(st, result);
        }
        return result;


    }

    /**
     *
     * <p>This method return a string containig all possible roots of a stem giving someroot extraction rules as defined in the unvowled pattern xml files.
     * the roots returned are seperated by white space. for example the rule 123 means that the first, second and third characters of the stem constitute the characters of a possible root.
     * </p>
     *
     * @param st unvoweled form of a stem
     * @param rules root extraction rules seperated by white space
     *
     *
     */
    public String getRoot(String st, String rules) throws Exception {

        String root = ""; // string to be returned

        String[] t = rules.split(" ");

        for (int j = 0; j < t.length; j++) {
            String rule = t[j];
            for (int i = 0; i < rule.length(); i++) {
                if (isNumeric(rule.charAt(i))) {
                    root += st.charAt(Integer.parseInt(rule.charAt(i) + "") - 1);
                } else {
                    root += rule.charAt(i);
                }
            }
            root += " ";

        }


        return root.replaceAll("[إأئؤ]", "ء");


    }

    /**
     *
     * <p>This method returns the list of possible nominal solutions of given segment.This is done in 4 steps.
     * First we look for the possible voweled nominal patterns of segment. Then for each voweled  pattern, we deduce the voweled form of the word.
     * Then comes the validation of solutions. This validation is based on the compatibility between all the informations
     * contained in the prefixes, stems, patterns, suffixes and vowelizations.
     * Finally the coded informations in the valid solutions are interpreted and added to the list to be returned. </p>
     *
     * @param segment a segmentation of the token
     * @param normalizedWord the normalized form of the token i.e. with the short vowels existing in the input word.
     *
     *
     */
    public List PossibleNominalSolutions(Segment segment, String normalizedWord) throws Exception {



        List result = new LinkedList(); // list to be returned
        List vowPatterns = new LinkedList();


        HashMap possiblePatterns = new HashMap();
        if (NPatterns.containsKey(segment.getStem().getUnvoweledform())) {
            possiblePatterns = (HashMap) NPatterns.get(segment.getStem().getUnvoweledform());
        } else {
            possiblePatterns = PossibleNominalPatterns(segment.getStem().getUnvoweledform());// look for the possible voweled nominal patterns
        }


        Iterator itvp = possiblePatterns.values().iterator();

        while (itvp.hasNext()) {
            HashMap ROOT = (HashMap) itvp.next();
            Iterator itr = ROOT.keySet().iterator();

            while (itr.hasNext()) {
                String vr = (String) itr.next();
                vowPatterns = (List) ROOT.get(vr);

                Iterator itvvp = vowPatterns.iterator();
                while (itvvp.hasNext()) {
                    VoweledNominalPattern vnp = (VoweledNominalPattern) itvvp.next();

                    String[] vowledWords = Vowelize(segment, vnp.getDiac()); //deducing the voweled form of the word
                    String vowledWord = vowledWords[0];

                    if (isValidNominalSolution(segment, vnp, vowledWords, normalizedWord)) {//validation of the  nominal solutions


                        String[] sol = Interpret(segment, vnp, vr); //interpretation of the coded informations

                        Result r = new Result(vowledWord, sol[0], segment.getStem().getUnvoweledform(), sol[1], vnp.getCanonic(), vr, sol[2], sol[3], sol[4]);

                        result.add(r);
                    }
                }

            }


        }




        return result;
    }

    /**
     *
     *
     * <p>This method returns a string array containing the interpretations
     * of the coded informations in the segment, voweled nominal pattern and the root.</p>
     *
     * @param seg a segmentation of the word
     * @param vnp possible voweled nominal pattern of the word
     * @param root possible root of the word
     *
     *
     */
    public String[] Interpret(Segment seg, VoweledNominalPattern vnp, String root) {

        String result[] = new String[5];
        StringBuffer prefix = new StringBuffer();
        StringBuffer suffix = new StringBuffer();
        StringBuffer priority = new StringBuffer();
        prefix.append("");
        suffix.append("");
        priority.append("1");
        if (seg.getPrefixe().getVoweledform().equals("")) {
            result[0] = "#";
        } else {
            result[0] = seg.getPrefixe().getVoweledform() + ": " + seg.getPrefixe().getDesc();
        }

        if (seg.getSuffixe().getVoweledform().equals("")) {
            result[3] = "#";
        } else {
            result[3] = seg.getSuffixe().getVoweledform() + ": " + seg.getSuffixe().getDesc();
        }

        if (vnp.getType().equals("فا")) {
            result[1] = "اسم فاعل";
            priority.append("10");
        } else {
            if (vnp.getType().equals("مف")) {
                result[1] = "اسم مفعول";
                priority.append("11");
            } else {


                if (vnp.getType().equals("مفا")) {
                    result[1] = "مبالغة اسم الفاعل";
                    priority.append("10");
                } else {
                    if (vnp.getType().equals("آ")) {
                        result[1] = "اسم آلة";
                        priority.append("15");
                    } else {
                        if (vnp.getType().equals("زمك")) {
                            result[1] = "اسم زمان أو مكان";
                            priority.append("14");
                        } else {
                            if (vnp.getType().equals("فض")) {
                                result[1] = "اسم تفضيل";
                                priority.append("13");
                            } else {
                                if (vnp.getType().equals("وش")) {
                                    result[1] = "صفة مشبهة";
                                    priority.append("12");
                                } else {
                                    if (vnp.getType().equals("صأ")) {
                                        result[1] = "مصدر أصلي";
                                        priority.append("07");
                                    } else {
                                        if (vnp.getType().equals("صم")) {
                                            result[1] = "مصدر ميمي";
                                            priority.append("08");
                                        } else {
                                            if (vnp.getType().equals("صه")) {
                                                result[1] = "مصدر هيئة";
                                                priority.append("17");
                                            } else {
                                                if (vnp.getType().equals("صر")) {
                                                    result[1] = "مصدر مرة";
                                                    priority.append("16");
                                                }

                                                if (vnp.getType().equals("جا")) {
                                                    result[1] = "اسم جامد";
                                                    priority.append("01");
                                                }
                                                if (vnp.getType().equals("صن")) {
                                                    result[1] = "مصدر صناعي";
                                                    priority.append("16");
                                                }
                                                if (vnp.getType().equals("نس")) {
                                                    result[1] = "نسبة";
                                                    priority.append("16");
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (vnp.getNcg().equals("1")) {

            if (vnp.getCas().equals("إض")) {
                if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                    result[2] = "مفرد مذكر مرفوع في حالة التعريف";
                    priority.append("1111");
                } else {
                    result[2] = "مفرد مذكر مرفوع في حالة الاضافة";
                    priority.append("1112");
                }
            } else {
                result[2] = "مفرد مذكر مرفوع نكرة";
                priority.append("1113");
            }
        } else {
            if (vnp.getNcg().equals("2")) {

                if (vnp.getCas().equals("إض")) {
                    if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                        result[2] = "مفرد مؤنث مرفوع في حالة التعريف";
                        priority.append("1211");
                    } else {
                        result[2] = "مفرد مؤنث مرفوع في حالة الاضافة";
                        priority.append("1212");
                    }
                } else {
                    result[2] = "مفرد مؤنث مرفوع نكرة";
                    priority.append("1213");
                }
            } else {
                if (vnp.getNcg().equals("3")) {

                    if (vnp.getCas().equals("إض")) {
                        if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                            result[2] = "مثنى مذكر مرفوع في حالة التعريف";
                            priority.append("2111");
                        } else {
                            result[2] = "مثنى مذكر مرفوع في حالة الاضافة";
                            priority.append("2112");
                        }
                    } else {
                        result[2] = "مثنى مذكر مرفوع نكرة";
                        priority.append("2113");
                    }
                } else {
                    if (vnp.getNcg().equals("4")) {

                        if (vnp.getCas().equals("إض")) {
                            if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                result[2] = "مثنى مؤنث مرفوع في حالة التعريف";
                                priority.append("2211");
                            } else {
                                result[2] = "مثنى مؤنث مرفوع في حالة الاضافة";
                                priority.append("2212");
                            }
                        } else {
                            result[2] = "مثنى مؤنث مرفوع نكرة";
                            priority.append("2213");
                        }
                    } else {
                        if (vnp.getNcg().equals("5")) {

                            if (vnp.getCas().equals("إض")) {
                                if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                    result[2] = "جمع مذكر مرفوع في حالة التعريف";
                                    priority.append("3111");
                                } else {
                                    result[2] = "جمع مذكر مرفوع في حالة الاضافة";
                                    priority.append("3112");
                                }
                            } else {
                                result[2] = "جمع مذكر مرفوع نكرة";
                                priority.append("3113");
                            }
                        } else {
                            if (vnp.getNcg().equals("6")) {

                                if (vnp.getCas().equals("إض")) {
                                    if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                        result[2] = "جمع مؤنث في حالة التعريف";
                                        priority.append("3211");
                                    } else {
                                        result[2] = "جمع مؤنث مرفوع في حالة الاضافة";
                                        priority.append("3212");
                                    }
                                } else {
                                    result[2] = "جمع مؤنث مرفوع نكرة";
                                    priority.append("3213");
                                }
                            } else {
                                if (vnp.getNcg().equals("7")) {

                                    if (vnp.getCas().equals("إض")) {
                                        if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                            result[2] = "مفرد مذكر منصوب في حالة التعريف";
                                            priority.append("1121");
                                        } else {
                                            result[2] = "مفرد مذكر منصوب في حالة الاضافة";
                                            priority.append("1122");
                                        }
                                    } else {
                                        result[2] = "مفرد مذكر منصوب نكرة";
                                        priority.append("1123");
                                    }
                                } else {
                                    if (vnp.getNcg().equals("8")) {

                                        if (vnp.getCas().equals("إض")) {
                                            if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                                result[2] = "مفرد مؤنث منصوب في حالة التعريف";
                                                priority.append("1221");
                                            } else {
                                                result[2] = "مفرد مؤنث منصوب في حالة الاضافة";
                                                priority.append("1222");
                                            }
                                        } else {
                                            result[2] = "مفرد مؤنث منصوب نكرة";
                                            priority.append("1223");
                                        }
                                    } else {
                                        if (vnp.getNcg().equals("9")) {

                                            if (vnp.getCas().equals("إض")) {
                                                if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                                    result[2] = "مثنى مذكر منصوب في حالة التعريف";
                                                    priority.append("2121");
                                                } else {
                                                    result[2] = "مثنى مذكر منصوب في حالة الاضافة";
                                                    priority.append("2122");
                                                }
                                            } else {
                                                result[2] = "مثنى مذكر منصوب نكرة";
                                                priority.append("2123");
                                            }
                                        } else {
                                            if (vnp.getNcg().equals("10")) {

                                                if (vnp.getCas().equals("إض")) {
                                                    if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                                        result[2] = "مثنى مؤنث منصوب في حالة التعريف";
                                                        priority.append("2221");
                                                    } else {
                                                        result[2] = "مثنى مؤنث منصوب في حالة الاضافة";
                                                        priority.append("2222");
                                                    }
                                                } else {
                                                    result[2] = "مثنى مؤنث منصوب نكرة";
                                                    priority.append("2223");
                                                }
                                            } else {
                                                if (vnp.getNcg().equals("11")) {

                                                    if (vnp.getCas().equals("إض")) {
                                                        if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                                            result[2] = "جمع مذكر منصوب في حالة التعريف";
                                                            priority.append("3121");
                                                        } else {
                                                            result[2] = "جمع مذكر منصوب في حالة الاضافة";
                                                            priority.append("3122");
                                                        }
                                                    } else {
                                                        result[2] = "جمع مذكر منصوب نكرة";
                                                        priority.append("3123");
                                                    }
                                                } else {
                                                    if (vnp.getNcg().equals("12")) {

                                                        if (vnp.getCas().equals("إض")) {
                                                            if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                                                result[2] = "جمع مؤنث منصوب في حالة التعريف";
                                                                priority.append("3221");
                                                            } else {
                                                                result[2] = "جمع مؤنث منصوب في حالة الاضافة";
                                                                priority.append("3222");
                                                            }
                                                        } else {
                                                            result[2] = "جمع مؤنث منصوب نكرة";
                                                            priority.append("3223");
                                                        }
                                                    } else {
                                                        if (vnp.getNcg().equals("13")) {

                                                            if (vnp.getCas().equals("إض")) {
                                                                if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                                                    result[2] = "مفرد مذكر مجرور في حالة التعريف";
                                                                    priority.append("1131");
                                                                } else {
                                                                    result[2] = "مفرد مذكر مجرور في حالة الاضافة";
                                                                    priority.append("1132");
                                                                }
                                                            } else {
                                                                result[2] = "مفرد مذكر مجرور نكرة";
                                                                priority.append("1133");
                                                            }
                                                        } else {
                                                            if (vnp.getNcg().equals("14")) {

                                                                if (vnp.getCas().equals("إض")) {
                                                                    if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                                                        result[2] = "مفرد مؤنث مجرور في حالة التعريف";
                                                                        priority.append("1231");
                                                                    } else {
                                                                        result[2] = "مفرد مؤنث مجرور في حالة الاضافة";
                                                                        priority.append("1232");
                                                                    }
                                                                } else {
                                                                    result[2] = "مفرد مؤنث مجرور نكرة";
                                                                    priority.append("1233");
                                                                }
                                                            } else {
                                                                if (vnp.getNcg().equals("15")) {

                                                                    if (vnp.getCas().equals("إض")) {
                                                                        if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                                                            result[2] = "مثنى مذكر مجرور في حالة التعريف";
                                                                            priority.append("2131");
                                                                        } else {
                                                                            result[2] = "مثنى مذكر مجرور في حالة الاضافة";
                                                                            priority.append("2132");
                                                                        }
                                                                    } else {
                                                                        result[2] = "مثنى مذكر مجرور نكرة";
                                                                        priority.append("2133");
                                                                    }
                                                                } else {
                                                                    if (vnp.getNcg().equals("16")) {

                                                                        if (vnp.getCas().equals("إض")) {
                                                                            if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                                                                result[2] = "مثنى مؤنث مجرور في حالة التعريف";
                                                                                priority.append("2231");
                                                                            } else {
                                                                                result[2] = "مثنى مؤنث مجرور في حالة الاضافة";
                                                                                priority.append("2232");
                                                                            }
                                                                        } else {
                                                                            result[2] = "مثنى مؤنث مجرور نكرة";
                                                                            priority.append("2233");
                                                                        }
                                                                    } else {
                                                                        if (vnp.getNcg().equals("17")) {

                                                                            if (vnp.getCas().equals("إض")) {
                                                                                if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                                                                    result[2] = "جمع مذكر مجرور في حالة التعريف";
                                                                                    priority.append("3131");
                                                                                } else {
                                                                                    result[2] = "جمع مذكر مجرور في حالة الاضافة";
                                                                                    priority.append("3132");
                                                                                }
                                                                            } else {
                                                                                result[2] = "جمع مذكر مجرور نكرة";
                                                                                priority.append("3133");
                                                                            }
                                                                        } else {
                                                                            if (vnp.getNcg().equals("18")) {

                                                                                if (vnp.getCas().equals("إض")) {
                                                                                    if (seg.getPrefixe().getClasse().equals("N1") || seg.getPrefixe().getClasse().equals("N2") || seg.getPrefixe().getClasse().equals("N3") || seg.getPrefixe().getClasse().equals("N5")) {
                                                                                        result[2] = "جمع مؤنث في حالة التعريف";
                                                                                        priority.append("3231");
                                                                                    } else {
                                                                                        result[2] = "جمع مؤنث مجرور في حالة الاضافة";
                                                                                        priority.append("3232");
                                                                                    }
                                                                                } else {
                                                                                    result[2] = "جمع مؤنث مجرور نكرة";
                                                                                    priority.append("3233");
                                                                                }
                                                                            } else {
                                                                                if (vnp.getNcg().equals("0")) {
                                                                                    result[2] = "مفرد مذكر";
                                                                                    priority.append("11");
                                                                                } else {
                                                                                    if (vnp.getNcg().equals("-1")) {
                                                                                        result[2] = "مفرد مؤنث";
                                                                                        priority.append("12");
                                                                                    } else {
                                                                                        if (vnp.getNcg().equals("-2")) {
                                                                                            result[2] = "جمع مذكر";
                                                                                            priority.append("31");
                                                                                        } else {
                                                                                            if (vnp.getNcg().equals("-3")) {
                                                                                                result[2] = "جمع مؤنث";
                                                                                                priority.append("32");
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if ((vnp.getNcg().equals("2") || vnp.getNcg().equals("8") || vnp.getNcg().equals("14")) && (seg.getStem().getUnvoweledform().endsWith("ة"))) {
            suffix.append("ة: تاء التأنيث");

        }

        if (vnp.getNcg().equals("4") || vnp.getNcg().equals("10") || vnp.getNcg().equals("16")) {
            if (seg.getStem().getUnvoweledform().endsWith("تان")) {
                suffix.append("ة:تاء التأنيث+ا علامة الإعراب");
            }
            if (seg.getStem().getUnvoweledform().endsWith("تين")) {
                suffix.append("ة:تاء التأنيث+ي علامة الإعراب");
            }
            if (seg.getStem().getUnvoweledform().endsWith("تا")) {
                suffix.append("ة:تاء التأنيث+ا علامة الإعراب");
            }



        }
        if (vnp.getNcg().equals("6") || vnp.getNcg().equals("12") || vnp.getNcg().equals("18")) {
            if (seg.getStem().getUnvoweledform().endsWith("ات")) {
                suffix.append("ات:تاء التأنيث");
            }
        }

        if (vnp.getNcg().equals("3") || vnp.getNcg().equals("9") || vnp.getNcg().equals("15")) {
            if (seg.getStem().getUnvoweledform().endsWith("ان")) {
                suffix.append("ان: علامة الإعراب");
            }
            if (seg.getStem().getUnvoweledform().endsWith("ين")) {
                suffix.append("ين:علامة الإعراب");
            }
            if (seg.getStem().getUnvoweledform().endsWith("ا")) {
                suffix.append("ا:علامة الإعراب");
            }
            if (seg.getStem().getUnvoweledform().endsWith("ي")) {
                suffix.append("ي:علامة الإعراب");
            }


        }
        if (vnp.getNcg().equals("5") || vnp.getNcg().equals("11") || vnp.getNcg().equals("17")) {
            if (seg.getStem().getUnvoweledform().endsWith("ون")) {
                suffix.append("ون:علامة الإعراب");
            }
            if (seg.getStem().getUnvoweledform().endsWith("ين")) {
                suffix.append("ين:علامة الإعراب");
            }
            if (seg.getStem().getUnvoweledform().endsWith("وا")) {
                suffix.append("وا: علامة الإعراب");
            }
            if (seg.getStem().getUnvoweledform().endsWith("ي")) {
                suffix.append("ي: علامة الإعراب");
            }


        }

        result[4] = priority.toString();
        if (result[0].equals("#") && (!prefix.toString().equals(""))) {
            result[0] = prefix.toString();
        } else {
            if ((!result[0].equals("#")) && (!prefix.toString().equals(""))) {
                result[0] = result[0] + "+ " + prefix.toString();
            }

        }
        if (result[3].equals("#") && (!suffix.toString().equals(""))) {
            result[3] = suffix.toString();
        } else {
            if ((!result[3].equals("#")) && (!suffix.toString().equals(""))) {
                result[3] = suffix.toString() + "+ " + result[3];
            }

        }
        return result;

    }

    /**
     * <p>This method returns a string array containing the interpretations
     * of the coded informations in the segment, voweled verbal pattern and the root.</p>
     *
     * @param seg a segmentation of the word
     * @param vnp possible voweled verbal pattern of the word
     * @param root possible root of the word
     *
     *
     */
    public String[] Interpret(Segment seg, VoweledVerbalPattern vnp, String root) {
        String result[] = new String[5];

        StringBuffer priority = new StringBuffer();
        StringBuffer prefix = new StringBuffer();
        StringBuffer suffix = new StringBuffer();
        priority.append("2");
        prefix.append("");
        suffix.append("");
        if (seg.getPrefixe().getVoweledform().equals("")) {
            result[0] = "#";
        } else {
            result[0] = seg.getPrefixe().getVoweledform() + ": " + seg.getPrefixe().getDesc();
        }

        if (seg.getSuffixe().getVoweledform().equals("")) {
            result[3] = "#";
        } else {
            result[3] = seg.getSuffixe().getVoweledform() + ": " + seg.getSuffixe().getDesc();
        }

        if (vnp.getType().equals("مم")) {
            result[1] = "فعل ماض مبني للمعلوم";
            priority.append("11");
        } else {
            if (vnp.getType().equals("مج")) {
                result[1] = "فعل ماض مبني للمجهول";
                priority.append("12");
            } else {
                if (vnp.getType().equals("ضم")) {
                    result[1] = "فعل مضارع مبني للمعلوم";
                    priority.append("21");
                } else {
                    if (vnp.getType().equals("ضءم")) {
                        result[1] = "فعل مضارع مؤكد مبني للمعلوم";
                        priority.append("21");
                    } else {
                        if (vnp.getType().equals("ضج")) {
                            result[1] = "فعل مضارع مبني للمجهول";
                            priority.append("22");
                        } else {
                            if (vnp.getType().equals("ضءج")) {
                                result[1] = "فعل مضارع مؤكد مبني للمجهول";
                                priority.append("22");
                            } else {
                                if (vnp.getType().equals("أ")) {
                                    result[1] = "فعل أمر";
                                    priority.append("3");
                                } else {
                                    if (vnp.getType().equals("أء")) {
                                        result[1] = "فعل أمر مؤكد";
                                        priority.append("3");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String pos = "";
        if (root.length() == 3) {
            pos = pos + "ثلاثي";
        } else {
            pos = pos + "رباعي";
        }
        if (vnp.getAug().equals("جر")) {
            pos = pos + " " + "مجرد";
        } else {
            pos = pos + " " + "مزيد";
        }
        if (vnp.getCas().equals("ج")) {
            pos = pos + " " + "مجزوم";
            priority.append("3");
        } else {
            if (vnp.getCas().equals("ر")) {
                pos = pos + " " + "مرفوع";
                priority.append("1");
            } else if (vnp.getCas().equals("ن")) {
                pos = pos + " " + "منصوب";
                priority.append("2");
            }
        }


        if (vnp.getNcg().equals("1")) {
            pos = pos + " " + "مسند إلى المتكلم أنا";
        } else {
            if (vnp.getNcg().equals("2")) {
                pos = pos + " " + "مسند إلى المتكلمين(نحن)";
            } else {

                if (vnp.getNcg().equals("3")) {
                    pos = pos + " " + "مسند إلى المخاطَب أنت";
                } else {
                    if (vnp.getNcg().equals("4")) {
                        pos = pos + " " + "مسند إلى المخاطبة(أنتِ)";
                    } else {
                        if (vnp.getNcg().equals("5")) {
                            pos = pos + " " + "مسند إلى المخاطَبَين(أنتما)";
                        } else {
                            if (vnp.getNcg().equals("6")) {
                                pos = pos + " " + "مسند إلى المخاطبين(أنتم)";
                            } else {
                                if (vnp.getNcg().equals("7")) {
                                    pos = pos + " " + "مسند إلى المخاطَبات(أنتن)";
                                } else {
                                    if (vnp.getNcg().equals("8")) {
                                        pos = pos + " " + "مسند إلى الغائب(هو)";
                                    } else {
                                        if (vnp.getNcg().equals("9")) {
                                            pos = pos + " " + "مسند إلى الغائبة(هي)";
                                        } else {
                                            if (vnp.getNcg().equals("10")) {
                                                pos = pos + " " + "مسند إلى الغائبَين(هما)";
                                            } else {
                                                if (vnp.getNcg().equals("11")) {
                                                    pos = pos + " " + "مسند إلى الغائبتين(هما)";
                                                } else {
                                                    if (vnp.getNcg().equals("12")) {
                                                        pos = pos + " " + "مسند إلى الغائبين(هم)";
                                                    } else {
                                                        if (vnp.getNcg().equals("13")) {
                                                            pos = pos + " " + "مسند إلى الغائبات(هن)";
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (vnp.getTrans().equals("ل")) {
            pos = pos + " " + "لازم";
            priority.append("1");
        } else {
            if (vnp.getTrans().equals("م")) {
                pos = pos + " " + "متعد";
                priority.append("2");
            } else {
                pos = pos + " " + "متعد ولازم";
            }
            priority.append("3");
        }
        ////////////////////////////////////////////
        if (vnp.getType().equals("أ")) {

            if (vnp.getNcg().equals("3")) {
                suffix.append("");
            }
            if (vnp.getNcg().equals("4")) {
                suffix.append("ي: ياء المخاطبة");
            }
            if (vnp.getNcg().equals("5")) {
                suffix.append("ا: أللف المثنى");
            }
            if (vnp.getNcg().equals("6")) {
                suffix.append("وا: واو الجماعة");
            }
            if (vnp.getNcg().equals("7")) {
                suffix.append("ن: نون النسوة");
            }


        }

        if (vnp.getType().equals("أء")) {

            if (vnp.getNcg().equals("3")) {
                suffix.append("ن: نون التوكيد");
            }
            if (vnp.getNcg().equals("4")) {
                suffix.append("ن: نون التوكيد");
            }
            if (vnp.getNcg().equals("5")) {
                suffix.append("ن: نون التوكيد");
            }
            if (vnp.getNcg().equals("6")) {
                suffix.append("ن: نون التوكيد");
            }
            if (vnp.getNcg().equals("7")) {
                suffix.append("ن: نون التوكيد");
            }


        }

        if (vnp.getType().equals("مم") || (vnp.getType().equals("مج"))) {

            if (vnp.getNcg().equals("1")) {
                suffix.append("ت: تاء المتكلم");
            }
            if (vnp.getNcg().equals("2")) {
                suffix.append("نا: نون المتكلمين");
            }
            if (vnp.getNcg().equals("3")) {
                suffix.append("ت: تاء المخاطب");
            }
            if (vnp.getNcg().equals("4")) {
                suffix.append("ت: تاء المخاطبة");
            }
            if (vnp.getNcg().equals("5")) {
                suffix.append("تما: تاء المخاطبين");
            }
            if (vnp.getNcg().equals("6")) {
                suffix.append("تم: تاء المخاطبين");
            }
            if (vnp.getNcg().equals("7")) {
                suffix.append("تن: تاء المخاطبات");
            }
            if (vnp.getNcg().equals("8")) {
                suffix.append("");
            }
            if (vnp.getNcg().equals("9")) {
                suffix.append("ت: تاء التأنيث الساكنة");
            }
            if (vnp.getNcg().equals("10")) {
                suffix.append("ا:ألف الاثنين");
            }
            if (vnp.getNcg().equals("11")) {
                suffix.append("تا: ألف الاثنين");
            }
            if (vnp.getNcg().equals("12")) {
                suffix.append("وا: واو الجماعة");
            }
            if (vnp.getNcg().equals("13")) {
                suffix.append("ن: نون النسوة");
            }





        }
// المضارع
        if (vnp.getType().equals("ضم") || (vnp.getType().equals("ضج"))) {

            if (vnp.getNcg().equals("1")) {
                prefix.append("أ:حرف المضارعة");

            }

            if (vnp.getNcg().equals("2")) {

                prefix.append("ن: حرف المضارعة");

            }

            if (vnp.getNcg().equals("3")) {
                prefix.append("ت: حرف المضارعة");
            }
            if (vnp.getNcg().equals("4")) {
                prefix.append("ت: حرف المضارعة");
                if (vnp.getCas().equals("ن") || (vnp.getCas().equals("ج"))) {
                    suffix.append("ي: ياء المخاطبة");
                } else {
                    suffix.append("ين: ياء المخاطبة+ن علامة الرفع");
                }

            }
            if (vnp.getNcg().equals("5")) {

                prefix.append("ت:حرف المضارعة");
                if (vnp.getCas().equals("ن") || (vnp.getCas().equals("ج"))) {
                    suffix.append("ا: ألف المثنى");
                } else {
                    suffix.append("ان: ألف المثنى+ن علامة الرفع");
                }

            }
            if (vnp.getNcg().equals("6")) {

                prefix.append("ت:حرف المضارعة");
                if (vnp.getCas().equals("ن") || (vnp.getCas().equals("ج"))) {
                    suffix.append("وا: واو الجماعة");
                } else {
                    suffix.append("ون: واو الجماعة+ن علامة الرفع");
                }

            }

            if (vnp.getNcg().equals("7")) {

                prefix.append("ت:حرف المضارعة");

                suffix.append("ن: نون النسوة");

            }

            if (vnp.getNcg().equals("8")) {

                prefix.append("ي: حرف المضارعة");



            }
            if (vnp.getNcg().equals("9")) {

                prefix.append("ت: تاء الغائبة");

            }

            if (vnp.getNcg().equals("10")) {

                prefix.append("ي:حرف المضارعة");
                if (vnp.getCas().equals("ن") || (vnp.getCas().equals("ج"))) {
                    suffix.append("ا: ألف المثنى");
                } else {
                    suffix.append("ان: ألف المثنى+ن علامة الرفع");
                }

            }

            if (vnp.getNcg().equals("11")) {

                prefix.append("ت:حرف المضارعة");
                if (vnp.getCas().equals("ن") || (vnp.getCas().equals("ج"))) {
                    suffix.append("ا: ألف المثنى");
                } else {
                    suffix.append("ان: ألف المثنى+ن علامة الرفع");
                }

            }

            if (vnp.getNcg().equals("12")) {

                prefix.append("ي:حرف المضارعة");
                if (vnp.getCas().equals("ن") || (vnp.getCas().equals("ج"))) {
                    suffix.append("وا: واو الجماعة");
                } else {
                    suffix.append("ون: واو الجماعة+ن علامة الرفع");
                }

            }

            if (vnp.getNcg().equals("13")) {

                prefix.append("ي: حرف المضارعة");

                suffix.append("ن: نون النسوة");


            }

        }
// المضارع المؤكد
        if (vnp.getType().equals("ضءم") || (vnp.getType().equals("ضءج"))) {

            if (vnp.getNcg().equals("1")) {
                prefix.append("أ: حرف المضارعة");
                suffix.append("ن: نون التوكيد");

            }

            if (vnp.getNcg().equals("2")) {

                prefix.append("ن: حرف المضارعة");
                suffix.append("ن: نون التوكيد");
            }

            if (vnp.getNcg().equals("3")) {
                prefix.append("ت:حرف المضارعة");
                suffix.append("ن: نون التوكيد");
            }
            if (vnp.getNcg().equals("4")) {
                prefix.append("ت:حرف المضارعة");
                suffix.append("ن: نون التوكيد");

            }
            if (vnp.getNcg().equals("5")) {

                prefix.append("ت:حرف المضارعة");
                suffix.append("ن: نون التوكيد");

            }
            if (vnp.getNcg().equals("6")) {

                prefix.append("ت:حرف المضارعة");
                suffix.append("ن: نون التوكيد");
            }

            if (vnp.getNcg().equals("7")) {

                prefix.append("ت: حرف المضارعة");

                suffix.append("ن: نون التوكيد");

            }

            if (vnp.getNcg().equals("8")) {

                prefix.append("ي:حرف المضارعة");

                suffix.append("ن: نون التوكيد");

            }
            if (vnp.getNcg().equals("9")) {

                prefix.append("ت:حرف المضارعة");
                suffix.append("ن: نون التوكيد");

            }

            if (vnp.getNcg().equals("10")) {

                prefix.append("ي: حرف المضارعة");
                suffix.append("ن: نون التوكيد");
            }

            if (vnp.getNcg().equals("11")) {

                prefix.append("ت:حرف المضارعة");
                suffix.append("ن: نون التوكيد");

            }

            if (vnp.getNcg().equals("12")) {

                prefix.append("ي: حرف المضارعة");
                suffix.append("ن: نون التوكيد");
            }

            if (vnp.getNcg().equals("13")) {

                prefix.append("ي:حرف المضارعة");

                suffix.append("ن: نون التوكيد");


            }





        }


////////////////////////////////////////////////////////////
        result[2] = pos;
        result[4] = priority.toString();

        if (result[0].equals("#") && (!prefix.toString().equals(""))) {
            result[0] = prefix.toString();
        } else {
            if ((!result[0].equals("#")) && (!prefix.toString().equals(""))) {
                result[0] = result[0] + "+ " + prefix.toString();
            }

        }
        if (result[3].equals("#") && (!suffix.toString().equals(""))) {
            result[3] = suffix.toString();
        } else {
            if ((!result[3].equals("#")) && (!suffix.toString().equals(""))) {
                result[3] = suffix.toString() + "+ " + result[3];
            }

        }


        return result;
    }

    /**
     *
     * <p>This method returns the list of possible verbal solutions of given segment.This is done in 4 steps.
     * First we look for the possible voweled verbal patterns of segment. Then for each voweled  pattern, we deduce the voweled form of the word.
     * Then comes the validation of solutions. This validation is based on the compatibility between all the informations
     * contained in the prefixes, stems, patterns, suffixes and vowelizations.
     * Finally the coded informations in the valid solutions are interpreted and added to the list to be returned. </p>
     *
     * @param segment a segmentation of the token
     * @param normalizedWord the normalized form of the token i.e. with the short vowels existing in the input word.
     *
     *
     */
    public List PossibleVerbalSolutions(Segment segment, String normalizedWord) throws Exception {



        List result = new LinkedList(); // list to be returned
        List vowPatterns = new LinkedList();

        HashMap possiblePatterns = new HashMap();
        if (VPatterns.containsKey(segment.getStem().getUnvoweledform())) {
            possiblePatterns = (HashMap) VPatterns.get(segment.getStem().getUnvoweledform());

        } else {
            possiblePatterns = PossibleVerbalPatterns(segment.getStem().getUnvoweledform()); // look for the possible voweled verbal patterns
        }


        Iterator itvp = possiblePatterns.values().iterator();

        while (itvp.hasNext()) {
            HashMap ROOT = (HashMap) itvp.next();
            Iterator itr = ROOT.keySet().iterator();

            while (itr.hasNext()) {
                String vr = (String) itr.next();
                vowPatterns = (List) ROOT.get(vr);

                Iterator itvvp = vowPatterns.iterator();
                while (itvvp.hasNext()) {
                    VoweledVerbalPattern vnp = (VoweledVerbalPattern) itvvp.next();

                    String[] vowledWords = Vowelize(segment, vnp.getDiac()); //deducing the voweled form of the word
                    String vowledWord = vowledWords[0];

                    if (isValidVerbalSolution(segment, vnp, vowledWords, normalizedWord)) { //validation of the verbal solutions


                        String[] sol = Interpret(segment, vnp, vr); //interpretation of the coded informations


                        Result r = new Result(vowledWord, sol[0], segment.getStem().getUnvoweledform(), sol[1], vnp.getCanonic(), vr, sol[2], sol[3], sol[4]);



                        result.add(r);
                    }
                }

            }


        }




        return result;
    }

    /**

     * <p>This method retuns a verbal root  object containing the the voweled verbal patterns Ids of the string root.
     *
     * </p>
     *
     * @param root a sequence of charachters representing an arabic root
     * @param ind the index of the root in the xml file
     *
     *
     */
    public Root getVerbalRoot(String root, int ind) throws Exception {

        List nRL = db.LoadVerbalRootsByFirstChar(root.charAt(0) + "").getRoots();


        Root r = new Root();
        r = (Root) nRL.get(ind);

        return r;
    }

    /**
     *  <p>This method retuns a nominal root  object containing the the voweled verbal patterns Ids of the string root.
     *
     * </p>
     *
     * @param root a sequence of charachters representing an arabic root
     * @param ind the index of the root in the xml file
     *
     *
     */
    public Root getNominalRoot(String root, int ind) throws Exception {

        List nRL = db.LoadNominalRootsByFirstChar(root.charAt(0) + "").getRoots();

        Root r = new Root();
        r = (Root) nRL.get(ind);

        return r;
    }

    /**
     *
     * <p>This method returns the intersection  of two lists of voweled paaterns.</p>
     *
     * @param L1 a list of voweled patterns
     * @param L2 a list of voweled patterns
     * @param v boolean true if the voweled patterns are verbal and false if they are nominal
     *
     */
    public List intersection(List L1, List L2, boolean v) throws Exception {

        List intersection = new LinkedList();



        try {
            int j = L2.size() - 1;
            int i = L1.size() - 1;
            int imax = i;

            if (v == true) {
                while (((String) L2.get(j)).compareTo(((VoweledVerbalPattern) L1.get(i)).getId()) < 0 && (i > 0)) {
                    i--;
                }

                if (((String) L2.get(j)).equals(((VoweledVerbalPattern) L1.get(i)).getId())) {
                    intersection.add((VoweledVerbalPattern) L1.get(i));

                    imax = i;
                } else {
                    imax = java.lang.Math.min(i + 1, L1.size() - 1);
                }


                if (imax > 0) {

                    j = 0;
                    int imin = 0;
                    while (j <= L2.size() - 2) {
                        i = imin;
                        //
                        while (((String) L2.get(j)).compareTo(((VoweledVerbalPattern) L1.get(i)).getId()) > 0 && (i <= imax)) {

                            i++;

                        }
                        if (((String) L2.get(j)).equals(((VoweledVerbalPattern) L1.get(i)).getId())) {
                            intersection.add((VoweledVerbalPattern) L1.get(i));

                            imin = i;
                        } else {
                            imin = java.lang.Math.max(i - 1, 0);
                        }
                        j++;

                    }


                }

                if (!intersection.isEmpty()) {
                    if (((String) ((VoweledVerbalPattern) intersection.get(0)).getId()).equals(((String) ((VoweledVerbalPattern) L2.get(L2.size() - 1)).getId()))) {
                        VoweledVerbalPattern t = (VoweledVerbalPattern) intersection.get(0);
                        intersection.remove(0);
                        intersection.add(t);
                    }
                }
            } else {


                while (((String) L2.get(j)).compareTo(((VoweledNominalPattern) L1.get(i)).getId()) < 0 && (i > 0)) {
                    i--;
                }


                if (((String) L2.get(j)).equals(((VoweledNominalPattern) L1.get(i)).getId())) {
                    intersection.add((VoweledNominalPattern) L1.get(i));

                    imax = i;
                } else {
                    imax = java.lang.Math.min(i + 1, L1.size() - 1);
                }


                if (imax > 0) {

                    j = 0;
                    int imin = 0;
                    while (j <= L2.size() - 2) {
                        i = imin;
                        //
                        while (((String) L2.get(j)).compareTo(((VoweledNominalPattern) L1.get(i)).getId()) > 0 && (i <= imax)) {

                            i++;

                        }
                        if (((String) L2.get(j)).equals(((VoweledNominalPattern) L1.get(i)).getId())) {
                            intersection.add((VoweledNominalPattern) L1.get(i));

                            imin = i;
                        } else {
                            imin = java.lang.Math.max(i - 1, 0);
                        }
                        j++;

                    }


                }

                if (!intersection.isEmpty()) {
                    if (((String) ((VoweledNominalPattern) intersection.get(0)).getId()).equals(((String) ((VoweledNominalPattern) L2.get(L2.size() - 1)).getId()))) {
                        VoweledNominalPattern t = (VoweledNominalPattern) intersection.get(0);
                        intersection.remove(0);
                        intersection.add(t);
                    }
                }

            }

        } catch (Exception ex) {
        }


        return intersection;
    }

    /**
     * <p>This method returns the intersection  of two string arrays.</p>
     *
     * @param tab1 a string array
     * @param tab2 a string array
     *
     *
     */
    public List intersection(String[] tab1, String[] tab2) throws Exception {

        List intersection = new LinkedList();
        String[] temp;

        if (tab1.length < tab2.length) {
            temp = tab1;
            tab1 = tab2;
            tab2 = temp;
        }
        try {
            int j = tab2.length - 1;
            int i = tab1.length - 1;
            int imax = i;

            while ((tab2[j].compareTo(tab1[i]) < 0) && (i > 0)) {
                i--;
            }


            if (tab2[j].equals(tab1[i])) {
                intersection.add(tab2[j]);

                imax = i;
            } else {
                imax = java.lang.Math.min(i + 1, tab1.length - 1);
            }


            if (imax > 0) {

                j = 0;
                int imin = 0;
                while (j <= tab2.length - 2) {
                    i = imin;
                    //
                    while ((tab2[j].compareTo(tab1[i]) > 0) && (i <= imax)) {

                        i++;

                    }


                    if (tab2[j].equals(tab1[i])) {
                        intersection.add(tab2[j]);

                        imin = i;
                    } else {
                        imin = java.lang.Math.max(i - 1, 0);
                    }
                    j++;

                }


            }

        } catch (Exception ex) {
        }


        if (!intersection.isEmpty()) {
            if (((String) intersection.get(0)).equals(tab2[tab2.length - 1])) {
                String t = (String) intersection.get(0);
                intersection.remove(0);
                intersection.add(t);
            }
        }

        return intersection;
    }

    /**
     * <p>This method returns the voweleization of a word according to a voweled pattern.
     * the returned string array contains two elements. the first is the vowelization of the entire word while the second is only the vowelization of the stem
     * </p>
     *
     * @param segment a possible segmentation of the word
     * @param voweledpattern a possible voweled pattern of the word
     *
     *
     */
    public String[] Vowelize(Segment segment, String voweledpattern) {

        String[] resultat = {"", ""};
        String stem = segment.getStem().getUnvoweledform();
        String result = "";
        String soukoun = "";
        int j = 0;
        // if the prefix contains the definit article ال we have to discuss whether the stem begins with a solar character or not
        if (segment.getPrefixe().getClasse().equals("N1") || segment.getPrefixe().getClasse().equals("N2") || segment.getPrefixe().getClasse().equals("N3") || segment.getPrefixe().getClasse().equals("N5")) {

            if (isSolar(stem.charAt(0))) {

                for (int i = 0; i < voweledpattern.length(); i++) {

                    if (isDiacritic(voweledpattern.charAt(i))) {
                        result = result + voweledpattern.charAt(i);
                    } else {
                        result = result + stem.charAt(j);
                        j++;
                    }
                }
                // insert the shadda
                result = result.substring(0, 1) + "ّ" + result.substring(1);

            } else {

                for (int i = 0; i < voweledpattern.length(); i++) {

                    if (isDiacritic(voweledpattern.charAt(i))) {
                        result = result + voweledpattern.charAt(i);
                    } else {
                        result = result + stem.charAt(j);
                        j++;
                    }
                }
                // insert the soukoun

                soukoun = "ْ";
            }
        } else {
            for (int i = 0; i < voweledpattern.length(); i++) {

                if (isDiacritic(voweledpattern.charAt(i))) {
                    result = result + voweledpattern.charAt(i);
                } else {
                    result = result + stem.charAt(j);
                    j++;
                }
            }

        }

        // replace ة by ت in the stem if the suffix is not empty
        if ((result.indexOf("ة") != -1) && (!segment.getSuffixe().getVoweledform().equals(""))) {
            result = result.replace('ة', 'ت');
        }
        // delete ا in some verbs if the suffix is not empty
        if (result.endsWith("ُوا") && (!segment.getSuffixe().getUnvoweledform().equals(""))) {
            char[] car = result.toCharArray();
            result = "";
            for (int ct = 0; ct < car.length - 1; ct++) {
                result += car[ct];
            }

        }

        // the vowelization of some suffix should be discussed according to the last character of the stem
        if (segment.getSuffixe().getUnvoweledform().equals("ه")) {
            if (result.endsWith("ي") || result.endsWith("يْ") || result.endsWith("ِ")) {
                segment.getSuffixe().setVoweledform("هِ");
            } else {
                segment.getSuffixe().setVoweledform("هُ");
            }
        }

        if (segment.getSuffixe().getUnvoweledform().equals("هما")) {
            if (result.endsWith("ي") || result.endsWith("يْ") || result.endsWith("ِ")) {
                segment.getSuffixe().setVoweledform("هِمَا");
            } else {
                segment.getSuffixe().setVoweledform("هُمَا");
            }
        }
        if (segment.getSuffixe().getUnvoweledform().equals("هم")) {
            if (result.endsWith("ي") || result.endsWith("يْ") || result.endsWith("ِ")) {
                segment.getSuffixe().setVoweledform("هِمْ");
            } else {
                segment.getSuffixe().setVoweledform("هُمْ");
            }
        }
        if (segment.getSuffixe().getUnvoweledform().equals("هن")) {
            if (result.endsWith("ي") || result.endsWith("يْ") || result.endsWith("ِ")) {
                segment.getSuffixe().setVoweledform("هِنَّ");
            } else {
                segment.getSuffixe().setVoweledform("هُنَّ");
            }
        }
        if ((result.endsWith("ُ") || result.endsWith("َ")) && (segment.getSuffixe().getUnvoweledform().equals("ي"))) {
            result = result.substring(0, result.length() - 1) + "ِ";
        }

        // the vowelization of the entire word
        resultat[0] = segment.getPrefixe().getVoweledform() + soukoun + result + segment.getSuffixe().getVoweledform();
        // the vowelization of the stem only
        resultat[1] = result;
        return resultat;




    }

    /**
     * <p>This method tests whether an arabic chacter is "solar" or not.
     * solar characters should be geminated when preceded by definit article  </p>
     *
     * @param c arabic character
     *
     *
     *
     */
    public boolean isSolar(char c) {

        switch (c) {
            case 'ت':
                return true;

            case 'ث':
                return true;

            case 'د':
                return true;

            case 'ذ':
                return true;

            case 'ر':
                return true;

            case 'ز':
                return true;

            case 'س':
                return true;

            case 'ش':
                return true;

            case 'ص':
                return true;

            case 'ض':
                return true;

            case 'ط':
                return true;

            case 'ظ':
                return true;

            case 'ل':
                return true;

            case 'ن':
                return true;

            default:
                return false;



        }

    }

    /**
     * <p>This method tests whether a character is numeric or not ie '0'..'9'.</p>
     *
     * @param c a character
     *
     *
     *
     */
    public boolean isNumeric(char c) {

        switch (c) {
            case '0':
                return true;

            case '1':
                return true;

            case '2':
                return true;

            case '3':
                return true;

            case '4':
                return true;

            case '5':
                return true;

            case '6':
                return true;

            case '7':
                return true;

            case '8':
                return true;

            case '9':
                return true;

            default:
                return false;



        }

    }

    /**
     * <p>This method tests whether a charcter is short vowel or not</p>
     *
     * @param c a character
     *
     *
     *
     */
    public boolean isDiacritic(char c) {

        switch (c) {
            case 'َ':
                return true;

            case 'ً':
                return true;

            case 'ُ':
                return true;

            case 'ٌ':
                return true;

            case 'ِ':
                return true;

            case 'ٍ':
                return true;

            case 'ْ':
                return true;

            case 'ّ':
                return true;

            default:
                return false;



        }

    }

    /**
     *
     * <p>This method tests whether a nominal solution is valid or not.
     * It is based on the compatibility between all the informations
     * contained in the prefixes, stems, patterns, suffixes and vowelizations</p>
     *
     * @param segment a possible segmentation of the word
     * @param vnp a possible voweled nominal pattern of the word
     * @param vowledWords the vowelization array of the word according to pattern vnp
     * @param normalizedWord the nomalized form of the token. it contains the short vowels possibly existing in the input word
     */
    public boolean isValidNominalSolution(Segment segment, VoweledNominalPattern vnp, String[] vowledWords, String normalizedWord) {

        boolean valid = true;
        String prefcalss = segment.getPrefixe().getClasse();
        String suffcalss = segment.getSuffixe().getClasse();
        String ncg = vnp.getNcg();
        String vowledWord = vowledWords[0];

        // Nunnation (tanwin) is not compatible with suffixes except the empty suffix
        // it is not compatible also with the definit article prefixes (ie N1 N2 N3 N5)
        if (((vowledWord.indexOf("ً") != -1) || (vowledWord.indexOf("ٍ") != -1) || (vowledWord.indexOf("ٌ") != -1))
                && ((!segment.getSuffixe().getUnvoweledform().equals("")) || (prefcalss.equals("N1")) || (prefcalss.equals("N2")) || (prefcalss.equals("N3")) || (prefcalss.equals("N5")))) {
            valid = false;
            return valid;
        }

        // Some prefixes (C2 C3 N2) are not compatible with the genitive case (majrour)
        if ((prefcalss.equals("N2") || prefcalss.equals("C2") || prefcalss.equals("C3")) && (ncg.equals("13") || ncg.equals("14") || ncg.equals("15") || ncg.equals("16") || ncg.equals("17") || ncg.equals("18"))) {
            valid = false;
            return valid;
        }

        // Some prefixes (N4 N5) are not compatible with the  non genitive cases
        if ((prefcalss.equals("N4") || prefcalss.equals("N5")) && (!(ncg.equals("13") || ncg.equals("14") || ncg.equals("15") || ncg.equals("16") || ncg.equals("17") || ncg.equals("18")))) {

            valid = false;
            return valid;

        }

        // Rules for writing Hamza

        for (int h = 1; h < vowledWords[1].length() - 2; h++) {

            if ((vowledWords[1].charAt(h) == 'ؤ') || (vowledWords[1].charAt(h) == 'ئ') || (vowledWords[1].charAt(h) == 'أ')) {
                if ((vowledWords[1].charAt(h - 1) == 'ِ') || (vowledWords[1].charAt(h + 1) == 'ِ')) {

                    if (vowledWords[1].charAt(h) != 'ئ') {
                        valid = false;

                        return valid;

                    }
                } else {
                    if ((vowledWords[1].charAt(h - 1) == 'ُ') || (vowledWords[1].charAt(h + 1) == 'ُ')) {
                        valid = valid && (vowledWords[1].charAt(h) == 'ؤ');
                    } else {
                        if ((vowledWords[1].charAt(h - 1) == 'َ') || (vowledWords[1].charAt(h + 1) == 'َ')) {
                            valid = valid && (vowledWords[1].charAt(h) == 'أ');
                        }

                    }
                }


            } else {
                if (vowledWords[1].charAt(h) == 'ء') {
                    if (!((vowledWords[1].charAt(h - 1) == 'ا') && ((vowledWords[1].charAt(h + 1) == 'َ') || (vowledWords[1].charAt(h + 1) == 'ْ')))) {
                        valid = false;
                        return valid;
                    }
                }
            }

        }


        int h = vowledWords[1].length() - 2;
        if (h > 0) {
            if (vowledWords[1].charAt(h) == 'ئ') {
                if ((vowledWords[1].charAt(h - 1) != 'ِ')) {
                    valid = false;
                    return valid;
                }
            } else {
                if (vowledWords[1].charAt(h) == 'ؤ') {
                    if ((vowledWords[1].charAt(h - 1) != 'ُ')) {

                        valid = false;
                        return valid;
                    }
                } else {
                    if (vowledWords[1].charAt(h) == 'أ') {

                        if ((vowledWords[1].charAt(h - 1) != 'َ')) {
                            valid = false;
                            return valid;
                        }
                    } else {
                        if (vowledWords[1].charAt(h) == 'ء') {
                            if ((vowledWords[1].charAt(h - 1) == 'َ') || (vowledWords[1].charAt(h - 1) == 'ِ') || (vowledWords[1].charAt(h - 1) == 'ُ')) {
                                valid = false;
                                return valid;
                            }
                        }
                    }
                }
            }

        }


        if ((vowledWord.indexOf("إَ") != -1) || (vowledWord.indexOf("أِ") != -1) || (vowledWord.indexOf("إُ") != -1) || (vowledWord.indexOf("ؤِ") != -1)
                || (vowledWord.indexOf("ٌأْ") != -1) || (vowledWord.indexOf("ُأَ") != -1)) {
            valid = false;
            return valid;
        }

        // Compatibility of the voweled word with the normalized word according
        //to the possibly existing short vowels in the normalized word
        if (notCompatible(normalizedWord, vowledWord)) {

            valid = false;
            return valid;
        }
        return valid;

    }

    /**
     * <p>This method tests the compatibility of the voweled word with the normalized word according
     * to the possibly existing short vowels in the normalized for of the word</p>
     *
     * @param vowledWord the vowelization  of the word according to a voweled pattern pattern
     * @param normalizedWord the nomalized form of the token. it contains the short vowels possibly existing in the input word

     *
     */
    public boolean notCompatible(String normalizedWord, String voweledWord) {



        int test = 0;

        String vpnr = "";
        List vpnrmList = new LinkedList();
        char[] vpn = normalizedWord.toCharArray();
        for (int ct = 0; ct < vpn.length; ct++) {
            if (isDiacritic(vpn[ct])) {
                vpnr += vpn[ct];
            } else {
                vpnrmList.add(vpnr);
                vpnr = "";
            }
        }
        vpnrmList.add(vpnr);

        String vpvwl = "";
        List vpvwList = new LinkedList();

        char[] vpvw = voweledWord.toCharArray();
        for (int ct = 0; ct < vpvw.length; ct++) {
            if (isDiacritic(vpvw[ct])) {
                vpvwl += vpvw[ct];
            } else {
                vpvwList.add(vpvwl);
                vpvwl = "";
            }

        }
        vpvwList.add(vpvwl);
        for (int ct = 0; ct < vpnrmList.size(); ct++) {
            if (!vpnrmList.get(ct).equals("")) {
                if (vpnrmList.get(ct).equals("ّ")) {
                    if (vpvwList.get(ct).equals("ّ") || vpvwList.get(ct).equals("َّ") || vpvwList.get(ct).equals("ًّ") || vpvwList.get(ct).equals("ُّ") || vpvwList.get(ct).equals("ٌّ") || vpvwList.get(ct).equals("ِّ") || vpvwList.get(ct).equals("ٍّ")) {
                    } else {
                        test++;
                    }

                } else {
                    if (!vpnrmList.get(ct).equals(vpvwList.get(ct))) {
                        test++;

                    }
                }

            }
        }

        String undiac1 = normalizedWord.replaceAll("[ًٌٍَُِّْ]", "");
        String undiac2 = voweledWord.replaceAll("[ًٌٍَُِّْ]", "");
        if (!undiac1.equals(undiac2)) {
            test++;
        }


        return test != 0;
    }

    /**
     * <p>This method tests whether a verbal solution is valid or not.
     * It is based on the compatibility between all the informations
     * contained in the prefixes, stems, patterns, suffixes and vowelizations</p>
     *
     * @param segment a possible segmentation of the word
     * @param vnp a possible voweled verbal pattern of the word
     * @param vowledWords the vowelization array of the word according to pattern vnp
     * @param normalizedWord the nomalized form of the token. it contains the short vowels possibly existing in the input word

     *
     */
    public boolean isValidVerbalSolution(Segment segment, VoweledVerbalPattern vnp, String[] vowledWords, String normalizedWord) {


        boolean valid = true;
        String prefcalss = segment.getPrefixe().getClasse();
        String suffcalss = segment.getSuffixe().getClasse();
        String ncg = vnp.getNcg();

        String vowledWord = vowledWords[0];
        // intransitive verbs are not compatible with non empty suffixes
        if (vnp.getTrans().equals("ل") && (!segment.getSuffixe().getUnvoweledform().equals(""))) {
            valid = false;
            return valid;
        }

        //some prefixes (V1 class) are not compatible with the  non imperfect indicative verbs ( mouDarie ghayr marfoue)
        if (prefcalss.equals("V1") && vnp.getCas().indexOf("ر") == -1) {
            valid = false;
            return valid;
        }
        //some prefixes (V2 class )are not compatible with the non imperfect subjunctive verbs ( mouDarie ghayr mansoub)
        if (prefcalss.equals("V2") && vnp.getCas().indexOf("ن") == -1) {
            valid = false;
            return valid;
        }

        //some prefixes (V3 class )are not compatible with the non imperfect jussive verbs ( mouDarie ghayr majzoum)

        if (prefcalss.equals("V3") && vnp.getCas().indexOf("ج") == -1) {
            valid = false;
            return valid;
        }

        //some prefixes (C2 class )are not compatible with the imperative verbs ( amr)

        if (prefcalss.equals("C2") && vnp.getType().indexOf("أ") != -1) {
            valid = false;
            return valid;
        }

        //some suffixes (except C1 suffixes )are not compatible with the  imperfect jussive verbs ( mouDarie majzoum)

        if (!suffcalss.equals("C1") && vnp.getType().indexOf("ج") != -1) {
            valid = false;
            return valid;
        }

        //some suffixes (V2 and V3 class )are not compatible with the imperative verbs ( amr)
        if (suffcalss.equals("V2") && vnp.getType().indexOf("أ") != -1) {
            valid = false;
            return valid;
        }
        if (suffcalss.equals("V3") && vnp.getType().indexOf("أ") != -1) {
            valid = false;
            return valid;
        }


        if (suffcalss.equals("V4") && vnp.getNcg().indexOf("6") == -1) {
            valid = false;
            return valid;
        }

        // misspelled hamza writings
        if ((vowledWord.indexOf("إَ") != -1) || (vowledWord.indexOf("أِ") != -1) || (vowledWord.indexOf("ِؤ") != -1) || (vowledWord.indexOf("إُ") != -1) || (vowledWord.indexOf("ؤِ") != -1) || (vowledWord.indexOf("ئَ") != -1) || (vowledWord.indexOf("ئُ") != -1)) {
            valid = false;
            return valid;
        }

        // Compatibility of the voweled word with the normalized word according
        //to the possibly existing short vowels in the normalized word
        if (notCompatible(normalizedWord, vowledWord)) {

            valid = false;
            return valid;
        }

        //  hamza writing rules
        for (int h = 1; h < vowledWords[1].length() - 2; h++) {

            if ((vowledWords[1].charAt(h) == 'ؤ') || (vowledWords[1].charAt(h) == 'ئ') || (vowledWords[1].charAt(h) == 'أ')) {
                if ((vowledWords[1].charAt(h - 1) == 'ِ') || (vowledWords[1].charAt(h + 1) == 'ِ')) {

                    if (vowledWords[1].charAt(h) != 'ئ') {
                        valid = false;

                        return valid;

                    }
                } else {
                    if ((vowledWords[1].charAt(h - 1) == 'ُ') || (vowledWords[1].charAt(h + 1) == 'ُ')) {
                        valid = valid && (vowledWords[1].charAt(h) == 'ؤ');
                    } else {
                        if ((vowledWords[1].charAt(h - 1) == 'َ') || (vowledWords[1].charAt(h + 1) == 'َ')) {
                            valid = valid && (vowledWords[1].charAt(h) == 'أ');
                        }

                    }
                }


            } else {
                if (vowledWords[1].charAt(h) == 'ء') {
                    if (!((vowledWords[1].charAt(h - 1) == 'ا') && ((vowledWords[1].charAt(h + 1) == 'َ') || (vowledWords[1].charAt(h + 1) == 'ْ')))) {
                        valid = false;
                        return valid;
                    }
                }
            }

        }


        int h = vowledWords[1].length() - 2;
        if (vowledWords[1].charAt(h) == 'ئ') {
            if ((vowledWords[1].charAt(h - 1) != 'ِ')) {
                valid = false;
                return valid;
            }
        } else {
            if (vowledWords[1].charAt(h) == 'ؤ') {
                if ((vowledWords[1].charAt(h - 1) != 'ُ')) {
                    valid = false;
                    return valid;
                }
            } else {
                if (vowledWords[1].charAt(h) == 'أ') {
                    if ((vowledWords[1].charAt(h - 1) != 'َ')) {
                        valid = false;
                        return valid;
                    }
                } else {
                    if (vowledWords[1].charAt(h) == 'ء') {
                        if ((vowledWords[1].charAt(h - 1) == 'َ') || (vowledWords[1].charAt(h - 1) == 'ِ') || (vowledWords[1].charAt(h - 1) == 'ُ')) {
                            valid = false;
                            return valid;
                        }
                    }
                }
            }
        }





        if ((vowledWord.indexOf("إَ") != -1) || (vowledWord.indexOf("أِ") != -1) || (vowledWord.indexOf("ِؤ") != -1) || (vowledWord.indexOf("إُ") != -1) || (vowledWord.indexOf("ؤِ") != -1) || (vowledWord.indexOf("ئَ") != -1) || (vowledWord.indexOf("ئُ") != -1)) {
            valid = false;
            return valid;
        }

        return valid;

    }
}
