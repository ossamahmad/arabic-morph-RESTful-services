/*
 * This class was modified from the book
 * /*      Java Swing, Second Edition
 *      Tips and Tools for Killer GUIs
 *    By Marc Loy, Robert Eckstein, Dave Wood, James Elliott, Brian Cole
 *      Second Edition November 2002
 *      http://www.oreilly.com/catalog/jswing2/
 */
package AlKhalil.result.tables;

/**
 * <p>This class provides implementations to allow  the user to choose the extension of result files to be saved.
 * .</p>
 *
 */
import javax.swing.filechooser.*;
import java.io.File;

public class SaveFilter extends FileFilter {

    String[] extensions;
    String description;

    public SaveFilter(String ext) {
        this(new String[]{ext}, null);
    }

    public SaveFilter(String[] exts, String descr) {

        extensions = new String[exts.length];
        for (int i = exts.length - 1; i >= 0; i--) {

            extensions[i] = exts[i].toLowerCase();
        }

        description = (descr == null ? exts[0] + " files" : descr);
    }

    public boolean accept(File f) {

        if (f.isDirectory()) {
            return true;
        }

        String name = f.getName().toLowerCase();
        for (int i = extensions.length - 1; i >= 0; i--) {
            if (name.endsWith(extensions[i])) {
                return true;
            }
        }
        return false;
    }

    public String getDescription() {
        return description;
    }
}
