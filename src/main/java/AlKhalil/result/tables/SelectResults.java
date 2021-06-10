/*
 * SelectResults.java
 *
 * 
 */
package AlKhalil.result.tables;

/**
 * <p>This class provides implementations to display the analysis results in JTable in order to select some results to be saved.
 * .</p>
 *
 */
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.table.*;

public class SelectResults extends JPanel {

    public static JTable table;

    /** Creates a new instance of SelectResults */
    public SelectResults(java.util.List resultList) {




        ResultModel model;

        Font f;

        f = new Font("SanSerif", Font.PLAIN, 24);
        setFont(f);
        setLayout(new BorderLayout());

        model = new ResultModel(resultList);

        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.RIGHT);


        table = new JTable();
        table.setModel(model);
        table.getTableHeader().setFont(new Font("SanSerif", Font.PLAIN, 16));
        table.createDefaultColumnsFromModel();
        table.setRowHeight(50);
        table.applyComponentOrientation(ComponentOrientation.getOrientation(new Locale("ar")));
        table.setFont(new Font("Simplified Arabic", 1, 18));

        //table.getColumnModel().getColumn(0).setCellRenderer(r);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setCellRenderer(r);
        table.getColumnModel().getColumn(1).setPreferredWidth(20);
        table.getColumnModel().getColumn(2).setCellRenderer(r);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setCellRenderer(r);
        table.getColumnModel().getColumn(3).setPreferredWidth(20);
        table.getColumnModel().getColumn(4).setCellRenderer(r);
        table.getColumnModel().getColumn(4).setPreferredWidth(20);
        table.getColumnModel().getColumn(5).setCellRenderer(r);
        table.getColumnModel().getColumn(5).setPreferredWidth(300);
        table.getColumnModel().getColumn(6).setCellRenderer(r);
        table.getColumnModel().getColumn(6).setPreferredWidth(20);
        JScrollPane scrollpane = new JScrollPane(table);
        add(scrollpane);



    }

    public SelectResults(File csvFile) {



        ResultModel model;

        Font f;

        f = new Font("SanSerif", Font.PLAIN, 24);
        setFont(f);
        setLayout(new BorderLayout());

        model = new ResultModel(csvFile);

        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.RIGHT);


        table = new JTable();
        table.setModel(model);
        table.getTableHeader().setFont(new Font("SanSerif", Font.PLAIN, 16));
        table.createDefaultColumnsFromModel();
        table.setRowHeight(50);
        table.applyComponentOrientation(ComponentOrientation.getOrientation(new Locale("ar")));
        table.setFont(new Font("Simplified Arabic", 1, 18));

        // table.getColumnModel().getColumn(0).setCellRenderer(r);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);

        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(r);
        }
        //table.getColumnModel().getColumn(1).setPreferredWidth(20);


        JScrollPane scrollpane = new JScrollPane(table);
        add(scrollpane);



    }

    public Dimension getPreferredSize() {
        return new Dimension(900, 600);
    }

    public static void showResults(java.util.List resultList) {
        JFrame frame = new JFrame("نتائج التحليل");
        SelectResults panel;

        panel = new SelectResults(resultList);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setForeground(Color.black);
        frame.setBackground(Color.lightGray);
        frame.getContentPane().add(panel, "Center");

        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
        frame.addWindowListener(new WindowCloser());
    }

    public static void showResults(File csvFile) {
        JFrame frame = new JFrame("نتائج التحليل");
        JMenuBar menu = new JMenuBar();
        JButton save;
        // setJMenuBar(menu);
        menu.applyComponentOrientation(ComponentOrientation.getOrientation(new Locale("ar")));
        save = new JButton("حفظ");
        menu.add(save);
        save.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        saveAction(e);

                    }
                });
        SelectResults panel;

        panel = new SelectResults(csvFile);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setForeground(Color.black);
        frame.setBackground(Color.lightGray);
        frame.getContentPane().add(panel, "Center");
        frame.setJMenuBar(menu);
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
        frame.addWindowListener(new WindowCloser());
    }

    public static void saveAction(ActionEvent e) {

        traitement();


    }

    public static void traitement() {

        JFileChooser jfc = new JFileChooser("saveAsFileChooser");
        //String[] html = new String[] {"html", "htm"};
        String[] csv = new String[]{"csv"};
        jfc.setDialogTitle("حفظ النتائج");
        jfc.setApproveButtonText("Save");
        jfc.setApproveButtonToolTipText("Here");
        jfc.setMultiSelectionEnabled(false);

        //
        //   jfc.addChoosableFileFilter(new SaveFilter(html, "Web page (*.html, *.htm)"));


        jfc.addChoosableFileFilter(new SaveFilter(csv, "Text CSV (*.csv)"));
        // jfc.addChoosableFileFilter(new SaveFilter(html, "Web page (*.html, *.htm)"));
        int resultat = jfc.showSaveDialog(null);


        if (resultat == JFileChooser.APPROVE_OPTION) {

            String fn = jfc.getSelectedFile().getName();



            if (fn.endsWith(".csv")) // fileWrite(jfc.getSelectedFile(),htmCode1);
            {
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
        }


    }

    public static void fileWriteCsv(File fichier) {



        try {

            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fichier));

            for (int i = 0; i < table.getRowCount(); i++) {
                StringBuffer str = new StringBuffer();
                int j;
                if ((Boolean) table.getValueAt(i, 0)) {

                    for (j = 1; j < table.getColumnCount() - 1; j++) {
                        str.append(table.getValueAt(i, j).toString() + ";");
                    }
                    str.append(table.getValueAt(i, j).toString() + "\n");
                    out.write(str.toString());
                }
            }



            out.close();

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

    }
}

class WindowCloser extends WindowAdapter {

    public void windowClosing(WindowEvent e) {
        Window win = e.getWindow();
        win.setVisible(false);
        // System.exit(0);
    }
}
