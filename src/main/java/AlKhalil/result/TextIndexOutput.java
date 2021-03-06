/*
 * TextIndexOutput.java
 *
 */

package AlKhalil.result;

import AlKhalil.result.tables.*;
import AlKhalil.TextIndex;
import java.awt.ComponentOrientation;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * <p>This class provides implementations to idex the input text. foreach tokenized word, it gives it frequency and contexts
 *
 * </p>
 *
 */

public class TextIndexOutput extends javax.swing.JFrame {
public static String htmlIndex;

    /** Creates new form TextIndexOutput */
    public TextIndexOutput() {
        super("Indexing  الفَهرَسَة");
        initComponents();
        EditorPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        EditorPane = new javax.swing.JEditorPane();
        jButton2 = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jButton1.setLabel("غلق");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        EditorPane.setContentType("text/html");
        EditorPane.setEditable(false);
        jScrollPane1.setViewportView(EditorPane);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
        );

        jButton2.setText("حفظ النتائج");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(318, 318, 318)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addComponent(jButton2)
                .addContainerGap(217, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        EditorPane.setText(htmlIndex);
    }//GEN-LAST:event_formComponentShown

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        saveResults();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void saveResults() {

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
                        fileWriteCsv(jfc.getSelectedFile());
                    } catch (Exception ex) {
                    }
                } else {
                    try {
                        fileWriteCsv(new File(jfc.getSelectedFile().getAbsolutePath() + ".csv"));
                    } catch (Exception ex) {
                    }
                }
            }
        }

    }


    private  void fileWriteCsv(File fichier) {
        LinkedList<String> wordDupli = TextIndex.getnormalizedWords();
        HashMap<String, LinkedList> wordInfo = TextIndex.getwordInfo();
        StringBuffer csvOutput = new StringBuffer();
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fichier), "UTF-8");
            String csvHead = "\"الكلمة\"" +";"+  "\"موقع الكلمة\"" + ";" + "\"السياق\"" + "\n";
            csvOutput.append(csvHead);
            
            for (int i = 0; i < wordDupli.size(); i++) {
                String append = "";
                String wordFind = wordDupli.get(i).toString();
                LinkedList wordPos = new LinkedList();
                wordPos = wordInfo.get(wordFind);
                for (int j = 0; j < wordPos.size(); j += 3) {
                    append += "\"" + wordDupli.get(i) + "\";";             //The word                    
                    append += "\"" + wordPos.get(j).toString() + "\";";   //The position of the word in the text
                    append += "\"" + wordPos.get(j + 1).toString() + " " + wordDupli.get(i) + " " + wordPos.get(j + 2).toString() + "\"\n";            //Context of the word
                }
                csvOutput.append(append);
            }
            out.write(csvOutput.toString());
            out.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }


    public  void showIndexoutput(){
    new TextIndexOutput().setVisible(true);
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane EditorPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
