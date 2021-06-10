/*
 * ResultPanel2.java
 *
 *
 *
 *
 */
package AlKhalil.result.tables;

import AlKhalil.ui.Gui;
import AlKhalil.ui.Settings;
import AlKhalil.result.*;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.JFrame;

/**
 * <p>This class provides implementations to display the analysis results in JFrame.
 * .</p>
 *
 */
public class ResultPanel2bis extends JFrame {

    private JMenuBar menu;
    private JButton save;
    private JButton print;
    final String htmC;
    private static String htmCode1, csvCode;
    private JEditorPane page = new JEditorPane();
    private JEditorPane pagePrint = new JEditorPane();

    /** Creates a new instance of ResultPanel2 */
    public ResultPanel2bis(String htmCode) {
        // super("äÊÇÆÌ ÇáÊÍáíá");
        htmC = htmCode;
        addWindowListener(new WindowCloser());
        htmCode1 = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" + htmCode;

        menu = new JMenuBar();
        setJMenuBar(menu);//add the menu bar to the frame

        menu.applyComponentOrientation(ComponentOrientation.getOrientation(new Locale("ar")));

//creat the menu file and his options open, save, save as and close
        save = new JButton("ÍÝÙ ÇáäÊÇÆÌ");
        save.applyComponentOrientation(ComponentOrientation.getOrientation(new Locale("ar")));
        save.setFont(new java.awt.Font("Traditional Arabic", 1, 18));
        menu.add(save);
        save.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        //Print Button
        print = new JButton("ØÈÇÚÉ");
        print.applyComponentOrientation(ComponentOrientation.getOrientation(new Locale("ar")));
        print.setFont(new java.awt.Font("Traditional Arabic", 1, 18));
        print.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        menu.add(print);
        // JLabel page=new JLabel();

        JScrollPane scrollpane = new JScrollPane(page);
        HTMLEditorKit htmKit = new HTMLEditorKit();
        page.setEditorKit(htmKit);
        page.setText(htmCode);
        page.setEditable(false);
        getContentPane().add(scrollpane);
        htmCode = htmCode.replaceAll("size=5", "");
        htmCode = htmCode.replaceAll("size=7", "size=3");
        pagePrint.setEditorKit(htmKit);
        pagePrint.setText(htmCode);


    }

    private void printActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //String destination;

        try {
            pagePrint.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // String destination;

        traitement();


    }

    public static void traitement() {

        JFileChooser jfc = new JFileChooser("saveAsFileChooser");
        String[] html = new String[]{"html", "htm"};
        String[] csv = new String[]{"csv"};
        jfc.setDialogTitle("ÍÝÙ ÇáäÊÇÆÌ");
        jfc.setApproveButtonText("Save");
        jfc.setApproveButtonToolTipText("Here");
        jfc.setMultiSelectionEnabled(false);

        //
        //   jfc.addChoosableFileFilter(new SaveFilter(html, "Web page (*.html, *.htm)"));


        jfc.addChoosableFileFilter(new SaveFilter(csv, "Text CSV (*.csv)"));
        jfc.addChoosableFileFilter(new SaveFilter(html, "Web page (*.html, *.htm)"));
        int resultat = jfc.showSaveDialog(null);


        if (resultat == JFileChooser.APPROVE_OPTION) {
            // System.out.println(jfc.getFileFilter().getDescription());
            String fn = jfc.getSelectedFile().getName();
            String ext = jfc.getFileFilter().getDescription();

            if (ext.endsWith("csv)")) {
                if (fn.endsWith(".csv")) {
                    try {
                        fileWriteCsv(jfc.getSelectedFile());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {



                    try {
                        fileWriteCsv(new File(jfc.getSelectedFile().getAbsolutePath() + ".csv"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                if (fn.endsWith(".html") || fn.endsWith(".htm")) {
                    try {
                        fileWriteCsv(jfc.getSelectedFile());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {



                    try {
                        fileWriteCsv(new File(jfc.getSelectedFile().getAbsolutePath() + ".htm"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }


            }
        }

    }

    public static void fileWrite(File fichier, String str) {
        try {


            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fichier), "utf-8");

            out.write(str);

            out.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public static void fileWriteCsv(File fichier) {

        StringBuffer str = new StringBuffer();

        try {

            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fichier));
            // BufferedWriter out = new BufferedWriter(fichier);

//str.append(";;;äÊÇÆÌ ÇáÊÍáíá;;\n");
//str.append("ÇáÏÎá;ÇáßáãÉ ÇáãÔßæáÉ;äæÚ ÇáßáãÉ;ÇáÓÇÈÞ;ÇáÌÐÚ;ÇáæÒä;ÇáÌÐÑ;ÇáÍÇáÉ ÇáÅÚÑÇÈíÉ;ÇááÇÍÞ;\n");


            out.write(str.toString());
            Collection results = Gui.analyzer.allResults.values();
            Iterator it = results.iterator();
            int i = 1;
            while (it.hasNext()) {
                i++;
                str = new StringBuffer();
                HashMap sol = (HashMap) it.next();
                Collection normalizedWords = sol.keySet();
                Iterator itn = normalizedWords.iterator();
                while (itn.hasNext()) {
                    String normalizedWord = (String) itn.next();
                    java.util.List result = (java.util.List) sol.get(normalizedWord);



                    if (result.isEmpty()) {
                        str.append(normalizedWord + ";");
                        str.append("áÇ ÊæÌÏ äÊÇÆÌ áÊÍáíá åÐå ÇáßáãÉ;;;;;;;;#;#\n");


                    } else {
                        for (int n = 0; n < result.size(); n++) {
                            Result resul = (Result) result.get(n);



                            str.append(normalizedWord + ";");
                            if (Settings.vowchoice) {
                                str.append(resul.getVoweledword() + ";");
                            }
                            if (Settings.prefchoice) {
                                str.append(resul.getPrefix() + ";");
                            }
                            if (Settings.stemchoice) {
                                str.append(resul.getStem() + ";");
                            }
                            if (Settings.typechoice) {
                                str.append(resul.getWordtype() + ";");
                            }

                            if (Settings.patternchoice) {
                                str.append(resul.getWordpattern() + ";");
                            }
                            if (Settings.rootchoice) {
                                str.append(resul.getWordroot() + ";");
                            }
                            if (Settings.poschoice) {
                                str.append(resul.getPos() + ";");
                            }
                            if (Settings.suffixchoice) {
                                str.append(resul.getSuffix() + "\n");
                            }
                            str.append("\n");







                        }

                    }



                }
                out.write(str.toString());
            }



            out.close();
            Gui.select.setEnabled(true);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

    }

    public Dimension getPreferredSize() {
        return new Dimension(900, 600);
    }

    public static void showResults(String htmCode) {
        // JFrame frame = new JFrame("äÊÇÆÌ ÇáÊÍáíá");
        ResultPanel2 panel;

        panel = new ResultPanel2(htmCode);

        panel.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        panel.setForeground(Color.black);
        panel.setBackground(Color.lightGray);
        //panel.getContentPane().setLayout(new GridLayout(1,1));
        // panel.getContentPane().add(panel,"Center");

        panel.setSize(panel.getPreferredSize());
        panel.setVisible(true);
        // panel.addWindowListener(new WindowCloser());
    }

    public static void showResults() {
        File csvFile = new File("AlKhalil_Results.csv");
        fileWriteCsv(csvFile);
        SelectResults.showResults(csvFile);

    }

    class WindowCloser extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            Window win = e.getWindow();
            win.setVisible(false);
            dispose();
            //System.exit(0);
        }
    }
}
