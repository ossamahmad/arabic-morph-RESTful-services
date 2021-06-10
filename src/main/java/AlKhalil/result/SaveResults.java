/*
 * SaveResults.java
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
package AlKhalil.result;

import AlKhalil.result.tables.*;
import AlKhalil.TextIndex;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * <p>This class provides implementations for SaveResults. It saves the search results in a CSV file.
 *
 * </p>
 *
 */
public class SaveResults {

    public static void saveResults(LinkedList<String> strFind) {

        JFileChooser jfc = new JFileChooser("saveAsFileChooser");
        String[] csv = new String[]{"csv"};
        jfc.setDialogTitle("حفظ النتائج");
        jfc.setApproveButtonText("Save");
        jfc.setApproveButtonToolTipText("Here");
        jfc.setMultiSelectionEnabled(false);


        jfc.addChoosableFileFilter(new SaveFilter(csv, "Text CSV (*.csv)"));

        int resultat = jfc.showSaveDialog(null);


        if (resultat == JFileChooser.APPROVE_OPTION) {

            String fn = jfc.getSelectedFile().getName();
            String ext = jfc.getFileFilter().getDescription();

            if (ext.endsWith("csv)")) {
                if (fn.endsWith(".csv")) // fileWrite(jfc.getSelectedFile(),htmCode1);
                {
                    try {
                        fileWriteCsv(jfc.getSelectedFile(), strFind);
                    } catch (Exception ex) {
                    }
                } else {
                    try {
                        fileWriteCsv(new File(jfc.getSelectedFile().getAbsolutePath() + ".csv"), strFind);
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }

    /** Save the results in CSV File */
    private static void fileWriteCsv(File fichier, LinkedList<String> strFind) {

        HashMap<String, LinkedList> wordInfo = TextIndex.getwordInfo();
        StringBuffer csvOutput = new StringBuffer();
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fichier), "UTF-8");
            String csvHead = "\"الكلمة\"" + ";" + "\"موقع الكلمة\"" + ";" + "\"السياق\"" + "\n";
            csvOutput.append(csvHead);

            for (int i = 0; i < strFind.size(); i++) {
                String append = "";
                String wordFind = strFind.get(i).toString();
                LinkedList wordPos = new LinkedList();
                wordPos = wordInfo.get(wordFind);
                for (int j = 0; j < wordPos.size(); j += 3) {
                    append += "\"" + strFind.get(i) + "\";";             //The word
                    append += "\"" + wordPos.get(j).toString() + "\";";   //The position of the word in the text
                    append += "\"" + wordPos.get(j + 1).toString() + " " + strFind.get(i) + " " + wordPos.get(j + 2).toString() + "\"\n";            //Context of the word
                }
                csvOutput.append(append);
            }
            out.write(csvOutput.toString());
            out.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }
}
