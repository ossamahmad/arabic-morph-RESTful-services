/*
 * ResultModel.java
 *
 * 
 */
package AlKhalil.result.tables;

/**
 * <p>This class provides implementations to display the analysis results in JTbales.
 * .</p>
 *
 */
import javax.swing.table.*;
import java.io.*;
import java.util.*;
import AlKhalil.ui.Settings;
import AlKhalil.result.*;

public class ResultModel extends AbstractTableModel {

    private Vector data;
    protected Vector columnNames;
    protected List results;

    public ResultModel(List resultList) {
        results = resultList;
        initVectors();
    }

    public ResultModel(File csvFile) {

        initVectors(csvFile);
    }

    public void initVectors() {

        data = new Vector();
        columnNames = new Vector();
        columnNames.addElement("اختيار");
        columnNames.addElement("الدخل");

        columnNames.addElement("الكلمة المشكولة");
        columnNames.addElement("السابق");
        columnNames.addElement("الجذع");
        columnNames.addElement("نوع الكلمة");
        columnNames.addElement("وزن الكلمة");
        columnNames.addElement("جذر الكلمة");
        columnNames.addElement("الحالة الإعرابية");
        columnNames.addElement("اللاحق");

        Iterator it = results.iterator();

        while (it.hasNext()) {
            Result r = (Result) it.next();
            data.addElement(new Boolean(false));
            data.addElement(r);
            data.addElement(r.getVoweledword());
            data.addElement(r.getPrefix());
            data.addElement(r.getWordtype());
            data.addElement(r.getWordpattern());

            data.addElement(r.getWordroot());
            data.addElement(r.getPos());
            data.addElement(r.getSuffix());


        }
    }

    public void initVectors(File csvFile) {

        data = new Vector();
        columnNames = new Vector();
        columnNames.addElement("اختيار");
        columnNames.addElement("الدخل");
        if (Settings.vowchoice) {
            columnNames.addElement("الكلمة المشكولة");
        }
        if (Settings.prefchoice) {
            columnNames.addElement("السابق");
        }
        if (Settings.stemchoice) {
            columnNames.addElement("الجذع");
        }
        if (Settings.typechoice) {
            columnNames.addElement("نوع الكلمة");
        }
        if (Settings.patternchoice) {

            columnNames.addElement("وزن الكلمة");
        }
        if (Settings.rootchoice) {
            columnNames.addElement("جذر الكلمة");
        }
        if (Settings.poschoice) {
            columnNames.addElement("الحالة الإعرابية");
        }
        if (Settings.suffixchoice) {
            columnNames.addElement("اللاحق");
        }


        String line;
        try {


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    new FileInputStream(csvFile), "UTF-8"));


            while ((line = in.readLine()) != null) {
                String ligne[] = line.split(";");
//if(ligne.length==9){
                int i = 0;
                data.addElement(new Boolean(false));

                data.addElement(ligne[0]);
                i++;
                if (Settings.vowchoice) {

                    data.addElement(ligne[i]);
                    i++;
                }
                if (Settings.prefchoice) {
                    data.addElement(ligne[i]);
                    i++;
                }
                if (Settings.stemchoice) {
                    data.addElement(ligne[i]);
                    i++;
                }
                if (Settings.typechoice) {
                    data.addElement(ligne[i]);
                    i++;
                }

                if (Settings.patternchoice) {
                    data.addElement(ligne[i]);
                    i++;
                }
                if (Settings.rootchoice) {
                    data.addElement(ligne[i]);
                    i++;
                }
                if (Settings.poschoice) {
                    data.addElement(ligne[i]);
                    i++;
                }
                if (Settings.suffixchoice) {
                    data.addElement(ligne[i]);
                    i++;
                }



            }
            //


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readFile(File fichier) {
        String ligne;

        StringBuffer buf = new StringBuffer();
        try {


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    new FileInputStream(fichier), "UTF-8"));


            while ((ligne = in.readLine()) != null) {
                buf.append(ligne + (char) '\n');
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRowCount() {
        return data.size() / getColumnCount();
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public String getColumnName(int columnIndex) {
        String colName = "";

        if (columnIndex <= getColumnCount()) {
            colName = (String) columnNames.elementAt(columnIndex);
        }

        return colName;
    }

    public Class getColumnClass(int columnIndex) {
        //return String.class;
        return getValueAt(0, columnIndex).getClass();


    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return columnIndex == 0;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
//    return (String)data.elementAt
//        ( (rowIndex * getColumnCount()) + columnIndex);
        return data.elementAt((rowIndex * getColumnCount()) + columnIndex);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        int ind = rowIndex * getColumnCount() + columnIndex;
        data.add(ind, aValue);
        data.remove(ind + 1);
        fireTableCellUpdated(rowIndex, columnIndex);


    }
}
