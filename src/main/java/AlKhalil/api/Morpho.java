package AlKhalil.api;

import AlKhalil.analyse.Analyzer;
import AlKhalil.result.Result;
import AlKhalil.token.Tokens;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Morpho {
    static Analyzer analyzer = new Analyzer();

    static public HashMap Analyse (String text) {
        Tokens tokens = new Tokens(text);

        List unvoweledTokens = tokens.getUnvoweledTokens();

        List normalizedTokens = tokens.getNormalizedTokens();


        analyzer.allResults.clear();
        for (int i = 0; i < unvoweledTokens.size(); i++) {// process all tokens

            String unvoweledWord = (String) unvoweledTokens.get(i);

            String normalizedWord = (String) normalizedTokens.get(i);

            List result = new LinkedList();
            try {
                result = analyzer.Analyze(normalizedWord, unvoweledWord);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            HashMap sol = new HashMap();
            sol.put(normalizedWord, result);
            analyzer.allResults.put(i, sol);
        }

        return analyzer.allResults;
    }
    static public Map Roots (String text) {
        HashMap<Integer,HashMap> analysis = Analyse(text);

        HashMap<String,List> allRoots = new HashMap();

        for (HashMap<String,List> sol : analysis.values()) {
            List roots = new LinkedList();
            for (Map.Entry<String,List> ent : sol.entrySet()) {
                List<Result> results = ent.getValue();
                if (results.size()==0) continue;
                for (Result res : results) {
                    String wt = res.getWordtype();
                    if (wt.equals("اسم علم"))
                        continue;
                    if (!roots.contains(res.getWordroot()))
                        roots.add(res.getWordroot());
                }
                allRoots.put(ent.getKey(), roots);
            }
        }
        return allRoots;
    }
    static public HashMap Stems (String text) {
        Tokens tokens = new Tokens(text);

        List unvoweledTokens = tokens.getUnvoweledTokens();

        List normalizedTokens = tokens.getNormalizedTokens();

        HashMap stemsMap = new HashMap();

        for (int i = 0; i < unvoweledTokens.size(); i++) {// process all tokens

            String unvoweledWord = (String) unvoweledTokens.get(i);

            String normalizedWord = (String) normalizedTokens.get(i);

            List result = new LinkedList();
            try {
                result = analyzer.Segment(unvoweledWord);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            stemsMap.put(normalizedWord, result);
        }

        return stemsMap;
    }
}
