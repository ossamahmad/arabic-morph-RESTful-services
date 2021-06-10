 /* ALKHALIL MORPHO SYS -- An open source programm.
 *
 * Copyright (C) 2010.
 *
 * This program is free software, distributed under the terms of
 * the GNU General Public License Version 3. For more informations see web site at :
 * http://www.gnu.org/licenses/gpl.txt
 */
package AlKhalil.analyse;

import java.util.*;
import AlKhalil.segment.*;

/**
 * <p>This class provides implementations for segmenting an arabic word in (prefixes, stems, suffixes ).
 * </p>
 *
 */
public class Segmentor {
    // ------------------------------------------------------  Class Variables

    /** Contains the list of  prefixes */
    private List Prefixes;
    /** Contains the list of  suffixes */
    private List Suffixes;

    // --------------------------------------------------------- Public Methods
    /**
    <p>Given an arabic wors this method returns a list of possible sgementations
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
        boolean valid = true; // the segmentation process will be stopped if this boolean became false
        while (valid && (i < unvoweledWord.length())) {


            String preftemp = unvoweledWord.substring(0, i);  //a candidate prefixe of length i

            String remaining = unvoweledWord.substring(i);

            List possiblePrefixes = PossiblePrefixes(preftemp);// returns a list of possible prefixes having the string preftemp as unvoweled form
            if (!possiblePrefixes.isEmpty()) {
                for (int j = 0; j < remaining.length(); j++) {

                    String suftemp = remaining.substring(remaining.length() - j);// a candidate suffix of length j

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
                                //when for example it ends with ? an additional segmentation replacing this ?
                                // by ? should be considered.

                                Alternatives.add(stem);
                                if (stem.endsWith("?")) {
                                    char[] s1 = stem.toCharArray();
                                    s1[s1.length - 1] = '?';
                                    String s2 = "";
                                    for (int k = 0; k < s1.length; k++) {
                                        s2 = s2 + s1[k];
                                    }
                                    Alternatives.add(s2);
                                }
                                if (stem.endsWith("?")) {

                                    String s2 = stem + "?";

                                    Alternatives.add(s2);
                                }

                                for (int k = 0; k < Alternatives.size(); k++) {

                                    String sch = (String) Alternatives.get(k);
                                    Stem st = new Stem();

                                    st.setUnvoweledform(sch);

                                    Segment segment = new Segment(p, st, s);

                                    if (isValidSegment(segment)) {// Segment validation: compatibility between the elements, stem length etc..

                                        result.add(segment);


                                    }


                                }



                            }

                        }
                    }


                }


            } else {

                if ((unvoweledWord.charAt(i - 1) == '?') && (unvoweledWord.charAt(i) == '?')) {
                } else {
                    valid = false;//will stop the segmentation loop

                }

            }


            i++;
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
        if (((prefix_classe.indexOf("N") != -1) && (suffixe_classe.indexOf("V") != -1)) || ((prefix_classe.indexOf("V") != -1) && (suffixe_classe.indexOf("N") != -1)) || (st.getStemLength() < 2) || (st.getStemLength() > 9) || ((prefix_classe.equals("N1") || prefix_classe.equals("N2") || prefix_classe.equals("N3") || prefix_classe.equals("N5")) && (!segment.getSuffixe().getUnvoweledform().equals("")))) {
            return false;
        } else {
            return true;
        }
    }
}