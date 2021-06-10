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
public class ResultPanel2 extends JFrame {

    private JMenuBar menu;
    private JButton save;
    private JButton print;
    final String htmC;
    private static String htmCode1, csvCode;
    private JEditorPane page = new JEditorPane();
    private JEditorPane pagePrint = new JEditorPane();

    /** Creates a new instance of ResultPanel2 */
    public ResultPanel2(String htmCode) {
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


        try {
            pagePrint.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //String destination;

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




        jfc.addChoosableFileFilter(new SaveFilter(csv, "Text CSV (*.csv)"));
        jfc.addChoosableFileFilter(new SaveFilter(html, "Web page (*.html, *.htm)"));
        int resultat = jfc.showSaveDialog(null);


        if (resultat == JFileChooser.APPROVE_OPTION) {

            String fn = jfc.getSelectedFile().getName();
            String ext = jfc.getFileFilter().getDescription();
            if (ext.endsWith("htm)")) {
                if (fn.endsWith(".html") || fn.endsWith(".htm")) {
                    try {
                        fileWrite2(jfc.getSelectedFile());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {



                    try {
                        fileWrite2(new File(jfc.getSelectedFile().getAbsolutePath() + ".htm"));
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

    public static void fileWrite2(File fichier) {

        StringBuffer str = new StringBuffer();
        str.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
        try {

            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fichier), "utf-8");

            str.append("<HTML>\n");
            str.append("<BODY DIR=\"LTR\" BGCOLOR=\"#FEFDE5\">\n");
            str.append("<center><font size=7><b><br>äÊÇÆÌ ÇáÊÍáíá<br>Analysis Results</b></font></center><br>\n");
            str.append("<TABLE WIDTH=100% BORDER=1>\n");

            str.append("<TR>\n");
            str.append("<TD COLSPAN=8 BGCOLOR=\"#DBDBFF\"  VALIGN=TOP>\n");
            str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=7>ÇáÎÑÌ</font><br><font size=5>OUTPUT</font></P>\n");
            str.append("</TD>\n");
            str.append("<TD  ROWSPAN=2 BGCOLOR=\"#E2C9A6\">\n");
            str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=7>ÇáÏÎá</font><br><font size=5>INPUT</font></P>\n");
            str.append("</TD>\n");
            str.append("</TR>\n");
            str.append("<TR >\n");
            str.append("<TD  BGCOLOR=\"#DBDB70\" VALIGN=TOP>\n");
            str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>ÇááÇÍÞ</font><br><font size=5>Suffix</font></P>\n");
            str.append("</TD>\n");

            str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
            str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>ÇáÍÇáÉ ÇáÅÚÑÇÈíÉ</font><br><font size=5>POS Tags</font>\n");
            str.append("</P>\n");
            str.append("</TD>\n");
            str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
            str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>ÇáÌÐÑ</font><br><font size=5>Root</font>\n");
            str.append("</P>\n");
            str.append("</TD>\n");
            str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
            str.append("<P DIR=\"RTL\" WIDTH=15% ALIGN=CENTER><font size=5>ÇáæÒä</font><br><font size=5>Pattern</font>\n");
            str.append("</P>\n");
            str.append("</TD>\n");
            str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
            str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>äæÚ ÇáßáãÉ</font><br><font size=5>Type</font>\n");
            str.append("</P>\n");
            str.append("</TD>\n");
            str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
            str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>ÇáÌÐÚ</font><br><font size=5>Stem</font></P>\n");
            str.append("</TD>\n");
            str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
            str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>ÇáÓÇÈÞ</font><br><font size=5>Prefix</font></P>\n");
            str.append("</TD>\n");
            str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
            str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>ÇáßáãÉ ÇáãÔßæáÉ</font><br><font size=5>Voweled Word</font></P>\n");
            str.append("</TD>\n");
            str.append("</TR>\n");

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

                    String bgc = i % 2 == 0 ? "\"#DBDBFF\"" : "\"#E2C9A6\"";

                    if (result.isEmpty()) {

                        str.append("<TR>\n");

                        str.append("<TD COLSPAN=8 BGCOLOR=" + bgc + ">\n");
                        str.append("<P ALIGN=CENTER><font size=5><b>" + "áÇ ÊæÌÏ äÊÇÆÌ áÊÍáíá åÐå ÇáßáãÉ" + "</b></font></P>\n");
                        str.append("</TD>\n");
                        str.append("<TD ROWSPAN=1 BGCOLOR=" + bgc + ">\n");
                        str.append("<P ALIGN=CENTER><font size=5><b>" + normalizedWord + "</b></font></P>\n");
                        str.append("</TD>\n");
                        str.append("</TR>\n");



                    } else {
                        Result resul = (Result) result.get(0);
                        int nbsol = result.size();
                        str.append("<TR>\n");
                        str.append("<TD  VALIGN=TOP>\n");
                        str.append("<P ALIGN=CENTER><font size=5>" + resul.getSuffix() + "</font></P>\n");
                        str.append("</TD>\n");
                        str.append("<TD  VALIGN=TOP>\n");
                        str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resul.getPos());
                        str.append("</font></P>\n");
                        str.append("</TD>\n");
                        str.append("<TD  VALIGN=TOP>\n");
                        str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resul.getWordroot());
                        str.append("</font></P>\n");
                        str.append("</TD>\n");
                        str.append("<TD  VALIGN=TOP>\n");
                        str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resul.getWordpattern());
                        str.append("</font></P>\n");
                        str.append("</TD>\n");
                        str.append("<TD  VALIGN=TOP>\n");
                        str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resul.getWordtype());
                        str.append("</font></P>\n");
                        str.append("</TD>\n");
                        str.append("<TD  VALIGN=TOP>\n");
                        str.append("<P ALIGN=CENTER><font size=5>" + resul.getStem() + "</font></P>\n");
                        str.append("</TD>\n");
                        str.append("<TD  VALIGN=TOP>\n");
                        str.append("<P ALIGN=CENTER><font size=5>" + resul.getPrefix() + "</font></P>\n");
                        str.append("</TD>\n");
                        str.append("<TD  VALIGN=TOP>\n");
                        str.append("<P ALIGN=CENTER><font size=5>" + resul.getVoweledword() + "</font></P>\n");
                        str.append("</TD>\n");
                        str.append("<TD ROWSPAN=" + nbsol + " BGCOLOR=" + bgc + ">\n");
                        str.append("<P ALIGN=CENTER><font size=5><b>" + normalizedWord + "</b></font></P>\n");
                        str.append("</TD>\n");
                        str.append("</TR>\n");
                        for (int n = 1; n < result.size(); n++) {
                            Result resulta = (Result) result.get(n);

                            str.append("<TR>");
                            str.append("<TD  VALIGN=TOP>");
                            str.append("<P ALIGN=CENTER><font size=5>" + resulta.getSuffix() + "</font></P>\n");
                            str.append("</TD>");
                            str.append("<TD  VALIGN=TOP>");
                            str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resulta.getPos());
                            str.append("</font></P>");
                            str.append("</TD>\n");
                            str.append("<TD  VALIGN=TOP>");
                            str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resulta.getWordroot());
                            str.append("</font></P>");
                            str.append("</TD>\n");
                            str.append("<TD  VALIGN=TOP>");
                            str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resulta.getWordpattern());
                            str.append("</font></P>");
                            str.append("</TD>\n");
                            str.append("<TD  VALIGN=TOP>");
                            str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resulta.getWordtype());
                            str.append("</font></P>");
                            str.append("</TD>\n");
                            str.append("<TD  VALIGN=TOP>");
                            str.append("<P ALIGN=CENTER><font size=5>" + resulta.getStem() + "</font></P>");
                            str.append("</TD>\n");
                            str.append("<TD  VALIGN=TOP>");
                            str.append("<P ALIGN=CENTER><font size=5>" + resulta.getPrefix() + "</font></P>");
                            str.append("</TD>\n");
                            str.append("<TD  VALIGN=TOP>");
                            str.append("<P ALIGN=CENTER><font size=5>" + resulta.getVoweledword() + "</font></P>");
                            str.append("</TD>\n");
                            str.append("</TR>\n");
                        }

                    }



                }
                out.write(str.toString());
            }

            str = new StringBuffer();
            str.append("</TABLE> <BR><HR>\n");
            str.append("</BODY>\n");
            str.append("</HTML>");
            out.write(str.toString());
            out.close();
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

        StringBuffer str = new StringBuffer();
        str.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
        try {

            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new File("Alkhalil_Results.html")), "utf-8");
            // BufferedWriter out = new BufferedWriter(new FileWriter("Alkhalil_Results.html"));







            str.append("<HTML>\n");
            str.append("<BODY DIR=\"LTR\" BGCOLOR=\"#FEFDE5\">\n");
            str.append("<center><font size=7><b><br>äÊÇÆÌ ÇáÊÍáíá<br>Analysis Results</b></font></center><br>\n");
            str.append("<TABLE WIDTH=100% BORDER=1>\n");

            str.append("<TR>\n");
            str.append("<TD COLSPAN=" + Settings.ncoloumns + " BGCOLOR=\"#DBDBFF\"  VALIGN=TOP>\n");
            str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=7>ÇáÎÑÌ</font><br><font size=5>OUTPUT</font></P>\n");
            str.append("</TD>\n");
            str.append("<TD  ROWSPAN=2 BGCOLOR=\"#E2C9A6\">\n");
            str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=7>ÇáÏÎá</font><br><font size=5>INPUT</font></P>\n");
            str.append("</TD>\n");
            str.append("</TR>\n");
            str.append("<TR >\n");
            if (Settings.suffixchoice) {
                str.append("<TD  BGCOLOR=\"#DBDB70\" VALIGN=TOP>\n");
                str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>ÇááÇÍÞ</font><br><font size=5>Suffix</font></P>\n");
                str.append("</TD>\n");
            }
            if (Settings.poschoice) {
                str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
                str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>ÇáÍÇáÉ ÇáÅÚÑÇÈíÉ</font><br><font size=5>POS Tags</font>\n");
                str.append("</P>\n");
                str.append("</TD>\n");
            }
            if (Settings.rootchoice) {
                str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
                str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>ÇáÌÐÑ</font><br><font size=5>Root</font>\n");
                str.append("</P>\n");
                str.append("</TD>\n");
            }
            if (Settings.patternchoice) {
                str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
                str.append("<P DIR=\"RTL\" WIDTH=15% ALIGN=CENTER><font size=5>ÇáæÒä</font><br><font size=5>Pattern</font>\n");
                str.append("</P>\n");
                str.append("</TD>\n");
            }
            if (Settings.typechoice) {
                str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
                str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>äæÚ ÇáßáãÉ</font><br><font size=5>Type</font>\n");
                str.append("</P>\n");
                str.append("</TD>\n");
            }
            if (Settings.stemchoice) {
                str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
                str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>ÇáÌÐÚ</font><br><font size=5>Stem</font></P>\n");
                str.append("</TD>\n");
            }
            if (Settings.prefchoice) {
                str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
                str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>ÇáÓÇÈÞ</font><br><font size=5>Prefix</font></P>\n");
                str.append("</TD>\n");
            }
            if (Settings.vowchoice) {
                str.append("<TD  VALIGN=TOP BGCOLOR=\"#DBDB70\">\n");
                str.append("<P DIR=\"RTL\" ALIGN=CENTER><font size=5>ÇáßáãÉ ÇáãÔßæáÉ</font><br><font size=5>Voweled Word</font></P>\n");
                str.append("</TD>\n");
            }
            str.append("</TR>\n");

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

                    String bgc = i % 2 == 0 ? "\"#DBDBFF\"" : "\"#E2C9A6\"";

                    if (result.isEmpty()) {

                        str.append("<TR>\n");

                        str.append("<TD COLSPAN=8 BGCOLOR=" + bgc + ">\n");
                        str.append("<P ALIGN=CENTER><font size=5><b>" + "áÇ ÊæÌÏ äÊÇÆÌ áÊÍáíá åÐå ÇáßáãÉ" + "</b></font></P>\n");
                        str.append("</TD>\n");
                        str.append("<TD ROWSPAN=1 BGCOLOR=" + bgc + ">\n");
                        str.append("<P ALIGN=CENTER><font size=5><b>" + normalizedWord + "</b></font></P>\n");
                        str.append("</TD>\n");
                        str.append("</TR>\n");



                    } else {
                        Result resul = (Result) result.get(0);
                        int nbsol = result.size();
                        str.append("<TR>\n");
                        if (Settings.suffixchoice) {
                            str.append("<TD  VALIGN=TOP>\n");
                            str.append("<P ALIGN=CENTER><font size=5>" + resul.getSuffix() + "</font></P>\n");
                            str.append("</TD>\n");
                        }
                        if (Settings.poschoice) {
                            str.append("<TD  VALIGN=TOP>\n");
                            str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resul.getPos());
                            str.append("</font></P>\n");
                            str.append("</TD>\n");
                        }
                        if (Settings.rootchoice) {
                            str.append("<TD  VALIGN=TOP>\n");
                            str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resul.getWordroot());
                            str.append("</font></P>\n");
                            str.append("</TD>\n");
                        }
                        if (Settings.patternchoice) {
                            str.append("<TD  VALIGN=TOP>\n");
                            str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resul.getWordpattern());
                            str.append("</font></P>\n");
                            str.append("</TD>\n");
                        }
                        if (Settings.typechoice) {
                            str.append("<TD  VALIGN=TOP>\n");
                            str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resul.getWordtype());
                            str.append("</font></P>\n");
                            str.append("</TD>\n");
                        }
                        if (Settings.stemchoice) {
                            str.append("<TD  VALIGN=TOP>\n");
                            str.append("<P ALIGN=CENTER><font size=5>" + resul.getStem() + "</font></P>\n");
                            str.append("</TD>\n");
                        }
                        if (Settings.prefchoice) {
                            str.append("<TD  VALIGN=TOP>\n");
                            str.append("<P ALIGN=CENTER><font size=5>" + resul.getPrefix() + "</font></P>\n");
                            str.append("</TD>\n");
                        }
                        if (Settings.vowchoice) {
                            str.append("<TD  VALIGN=TOP>\n");
                            str.append("<P ALIGN=CENTER><font size=5>" + resul.getVoweledword() + "</font></P>\n");
                            str.append("</TD>\n");
                        }
                        str.append("<TD ROWSPAN=" + nbsol + " BGCOLOR=" + bgc + ">\n");
                        str.append("<P ALIGN=CENTER><font size=5><b>" + normalizedWord + "</b></font></P>\n");
                        str.append("</TD>\n");
                        str.append("</TR>\n");
                        for (int n = 1; n < result.size(); n++) {
                            Result resulta = (Result) result.get(n);

                            str.append("<TR>");
                            if (Settings.suffixchoice) {
                                str.append("<TD  VALIGN=TOP>");
                                str.append("<P ALIGN=CENTER><font size=5>" + resulta.getSuffix() + "</font></P>\n");
                                str.append("</TD>");
                            }
                            if (Settings.poschoice) {
                                str.append("<TD  VALIGN=TOP>");
                                str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resulta.getPos());
                                str.append("</font></P>");
                                str.append("</TD>\n");
                            }
                            if (Settings.rootchoice) {
                                str.append("<TD  VALIGN=TOP>");
                                str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resulta.getWordroot());
                                str.append("</font></P>");
                                str.append("</TD>\n");
                            }
                            if (Settings.patternchoice) {
                                str.append("<TD  VALIGN=TOP>");
                                str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resulta.getWordpattern());
                                str.append("</font></P>");
                                str.append("</TD>\n");
                            }
                            if (Settings.typechoice) {
                                str.append("<TD  VALIGN=TOP>");
                                str.append("<P DIR=\"RTL\" ALIGN=RIGHT><font size=5>" + resulta.getWordtype());
                                str.append("</font></P>");
                                str.append("</TD>\n");
                            }
                            if (Settings.stemchoice) {
                                str.append("<TD  VALIGN=TOP>");
                                str.append("<P ALIGN=CENTER><font size=5>" + resulta.getStem() + "</font></P>");
                                str.append("</TD>\n");
                            }
                            if (Settings.prefchoice) {
                                str.append("<TD  VALIGN=TOP>");
                                str.append("<P ALIGN=CENTER><font size=5>" + resulta.getPrefix() + "</font></P>");
                                str.append("</TD>\n");
                            }
                            if (Settings.vowchoice) {
                                str.append("<TD  VALIGN=TOP>");
                                str.append("<P ALIGN=CENTER><font size=5>" + resulta.getVoweledword() + "</font></P>");
                                str.append("</TD>\n");
                            }
                            str.append("</TR>\n");
                        }

                    }



                }
                out.write(str.toString());
            }

            str = new StringBuffer();
            str.append("</TABLE> <BR><HR>\n");
            str.append("</BODY>\n");
            str.append("</HTML>");
            out.write(str.toString());
            out.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        try {
            Desktop.getDesktop().open(new File("Alkhalil_Results.html"));
        } catch (Exception el) {
            el.printStackTrace();
        }

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
