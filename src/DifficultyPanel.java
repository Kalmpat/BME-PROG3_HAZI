import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class DifficultyPanel extends JPanel {

    private JButton kezdo, kozepes, nehez;

    public DifficultyPanel() {
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        // szöveg (címke) beállítása
        JLabel nehLabel = new JLabel("Nehézségi szint:");
        nehLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nehLabel.setForeground(Color.WHITE);

        this.add(Box.createRigidArea(new Dimension(10, 0)));
        this.add(nehLabel);


        // Gombok beállítása
        kezdo = new JButton("Könnyű");
        kozepes = new JButton("Közepes");
        nehez = new JButton("Nehéz");

        Dimension gombMeret = new Dimension(80, 50);
        kezdo.setPreferredSize(gombMeret);
        kozepes.setPreferredSize(gombMeret);
        nehez.setPreferredSize(gombMeret);

        // Gombok stílusa
        kezdo.setBackground(new Color(57, 56, 55));
        kezdo.setForeground(Color.WHITE);
        kezdo.setBorder(new LineBorder(new Color(30, 31, 34), 2));
        kezdo.setFocusable(false);

        kozepes.setBackground(new Color(57, 56, 55));
        kozepes.setForeground(Color.WHITE);
        kozepes.setBorder(new LineBorder(new Color(30, 31, 34), 2));
        kozepes.setFocusable(false);

        nehez.setBackground(new Color(57, 56, 55));
        nehez.setForeground(Color.WHITE);
        nehez.setBorder(new LineBorder(new Color(30, 31, 34), 2));
        nehez.setFocusable(false);

        // Actionok
        kezdo.addActionListener(new Konnyu_Click());
        kozepes.addActionListener(new Kozepes_Click());
        nehez.addActionListener(new Nehez_Click());

        this.add(kezdo);
        this.add(kozepes);
        this.add(nehez);
    }

    /**
     * Gombok vizuális állapotának frissítése
     * - engedélyezett/nem engedélyezett (pl. 2 játékos esetén a nehézség letiltva)
     * - kiválasztott gomb narancssárga kiemelést kap
     */

    public void updateGombok() {
        boolean engedelyezett = !SettingsManager.ketJatekosValasztva;

        // Kattinthatósák beállítása
        kezdo.setEnabled(engedelyezett);
        kozepes.setEnabled(engedelyezett);
        nehez.setEnabled(engedelyezett);

        // Gombok beállítása
        if(engedelyezett) {
            kezdo.setForeground(SettingsManager.kezdoValasztva ? Color.ORANGE : Color.WHITE);
            kozepes.setForeground(SettingsManager.kozepesValasztva ? Color.ORANGE : Color.WHITE);
            nehez.setForeground(SettingsManager.nehezValasztva ? Color.ORANGE : Color.WHITE);

            kezdo.setBorder(new LineBorder(SettingsManager.kezdoValasztva ? Color.ORANGE : new Color(30, 31, 34), 2));
            kozepes.setBorder(new LineBorder(SettingsManager.kozepesValasztva ? Color.ORANGE : new Color(30, 31, 34), 2));
            nehez.setBorder(new LineBorder(SettingsManager.nehezValasztva ? Color.ORANGE : new Color(30, 31, 34), 2));
        } else {
            kezdo.setBorder(new LineBorder(new Color(50, 50, 50), 2));
            kozepes.setBorder(new LineBorder(new Color(50, 50, 50), 2));
            nehez.setBorder(new LineBorder(new Color(50, 50, 50), 2));
        }
    }

    // Eseménykezelő az "könnyű" gombhoz
    class Konnyu_Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SettingsManager.kezdoValasztva = true;
            SettingsManager.kozepesValasztva = false;
            SettingsManager.nehezValasztva = false;
            updateGombok();
            SettingsManager.ment();
        }
    }
    // Eseménykezelő az "közepes" gombhoz
    class Kozepes_Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SettingsManager.kezdoValasztva = false;
            SettingsManager.kozepesValasztva = true;
            SettingsManager.nehezValasztva = false;
            updateGombok();
            SettingsManager.ment();
        }
    }

    // Eseménykezelő az "nehéz" gombhoz
    class Nehez_Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SettingsManager.kezdoValasztva = false;
            SettingsManager.kozepesValasztva = false;
            SettingsManager.nehezValasztva = true;
            updateGombok();
            SettingsManager.ment();
        }
    }
}