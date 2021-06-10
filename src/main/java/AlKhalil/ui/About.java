/*
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
package AlKhalil.ui;

import javax.swing.*;
import java.awt.*;

/**
 * <p>JDialog showing the work team.
 *
 * </p>
 *
 */
public class About extends JDialog {

    About() {
        setTitle("حول البرنامج");
        setSize(670, 500);
        setResizable(false);
        pan = new Panneau();
        getContentPane().add(pan);


    }
    private JPanel pan;
    private Image photo = null;

    class Panneau extends JPanel {

        public Panneau() {
            photo = getToolkit().getImage("doc/about.jpg");
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int x = 10, y = 10;
            if (photo != null) {
                g.drawImage(photo, x, y, this);
            }

        }
    }

    public static void main(String args[]) {
        About fen = new About();
        fen.setVisible(true);
    }
}
