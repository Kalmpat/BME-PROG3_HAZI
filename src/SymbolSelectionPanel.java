import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class SymbolSelectionPanel extends JPanel {

    private JButton xJel, oJel;

    public SymbolSelectionPanel() {
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        // szöveg (címke) beállítása
        this.add(Box.createRigidArea(new Dimension(10, 0)));
        JLabel jelLabel = new JLabel("Felhasználó jele:");
        jelLabel.setFont(new Font("Arial", Font.BOLD, 20));
        jelLabel.setForeground(Color.WHITE);
        this.add(jelLabel);

        // Gombok beállítása
        xJel = new JButton("X");
        oJel = new JButton("O");

        Dimension jelMeret = new Dimension(80, 50);
        xJel.setPreferredSize(jelMeret);
        oJel.setPreferredSize(jelMeret);

        xJel.setBackground(new Color(57, 56, 55));
        xJel.setForeground(Color.WHITE);
        xJel.setBorder(new LineBorder(new Color(30, 31, 34), 2));
        xJel.setFocusable(false);

        oJel.setBackground(new Color(57, 56, 55));
        oJel.setForeground(Color.WHITE);
        oJel.setBorder(new LineBorder(new Color(30, 31, 34), 2));
        oJel.setFocusable(false);

        // Actionok
        xJel.addActionListener(new X_Click());
        oJel.addActionListener(new O_Click());

        this.add(xJel);
        this.add(oJel);
    }

    /**
     * Gombok vizuális állapotának frissítése
     * - engedélyezett/nem engedélyezett (pl. ha 1 játékos van, csak akkor lehet választani)
     * - kiválasztott gomb narancssárga kiemelést kap
     */

    public void updateGombok() {
        boolean engedelyezett = !SettingsManager.ketJatekosValasztva;

        xJel.setEnabled(engedelyezett);
        oJel.setEnabled(engedelyezett);

        if(engedelyezett) {
            xJel.setForeground(SettingsManager.felhasznaloJel.equals("X") ? Color.ORANGE : Color.WHITE);
            oJel.setForeground(SettingsManager.felhasznaloJel.equals("O") ? Color.ORANGE : Color.WHITE);

            xJel.setBorder(new LineBorder(SettingsManager.felhasznaloJel.equals("X") ? Color.ORANGE : new Color(30, 31, 34), 2));
            oJel.setBorder(new LineBorder(SettingsManager.felhasznaloJel.equals("O") ? Color.ORANGE : new Color(30, 31, 34), 2));
        } else {
            xJel.setBorder(new LineBorder(new Color(50, 50, 50), 2));
            oJel.setBorder(new LineBorder(new Color(50, 50, 50), 2));
        }
    }

    // Eseménykezelő az „X” gombhoz
    class X_Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SettingsManager.felhasznaloJel = "X";
            updateGombok();
            SettingsManager.ment();
        }
    }
    // Eseménykezelő az „O” gombhoz
    class O_Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SettingsManager.felhasznaloJel = "O";
            updateGombok();
            SettingsManager.ment();
        }
    }
}