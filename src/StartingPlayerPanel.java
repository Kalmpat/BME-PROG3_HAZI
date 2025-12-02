import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class StartingPlayerPanel extends JPanel {

    private JButton teKezd, botKezd;

    public StartingPlayerPanel() {
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        // szöveg (címke) beállítása
        this.add(Box.createRigidArea(new Dimension(10, 0)));
        JLabel kezdLabel = new JLabel("Ki kezdjen?");
        kezdLabel.setFont(new Font("Arial", Font.BOLD, 20));
        kezdLabel.setForeground(Color.WHITE);
        this.add(kezdLabel);

        // Gombok beállítása
        teKezd = new JButton("Én");
        botKezd = new JButton("Bot");

        Dimension kezdMeret = new Dimension(120, 50);
        teKezd.setPreferredSize(kezdMeret);
        botKezd.setPreferredSize(kezdMeret);

        teKezd.setBackground(new Color(57, 56, 55));
        teKezd.setForeground(Color.WHITE);
        teKezd.setBorder(new LineBorder(new Color(30, 31, 34), 2));
        teKezd.setFocusable(false);

        botKezd.setBackground(new Color(57, 56, 55));
        botKezd.setForeground(Color.WHITE);
        botKezd.setBorder(new LineBorder(new Color(30, 31, 34), 2));
        botKezd.setFocusable(false);

        // Actionok
        teKezd.addActionListener(new TeKezd_Click());
        botKezd.addActionListener(new BotKezd_Click());

        this.add(teKezd);
        this.add(botKezd);
    }
    /**
     * Gombok vizuális állapotának frissítése
     * - engedélyezett/nem engedélyezett (pl. ha 1 játékos van, csak akkor lehet választani)
     * - kiválasztott gomb narancssárga kiemelést kap
     */

    public void updateGombok() {
        boolean engedelyezett = SettingsManager.egyJatekosValasztva;

        // Kattinthatósák beállítása
        teKezd.setEnabled(engedelyezett);
        botKezd.setEnabled(engedelyezett);

        // Gombok beállítása
        if(engedelyezett) {
            teKezd.setForeground(SettingsManager.teKezdValasztva ? Color.ORANGE : Color.WHITE);
            botKezd.setForeground(!SettingsManager.teKezdValasztva ? Color.ORANGE : Color.WHITE);

            teKezd.setBorder(new LineBorder(SettingsManager.teKezdValasztva ? Color.ORANGE : new Color(30, 31, 34), 2));
            botKezd.setBorder(new LineBorder(!SettingsManager.teKezdValasztva ? Color.ORANGE : new Color(30, 31, 34), 2));
        } else {
            teKezd.setBorder(new LineBorder(new Color(50, 50, 50), 2));
            botKezd.setBorder(new LineBorder(new Color(50, 50, 50), 2));
        }
    }

    // Eseménykezelő az „tekezd” gombhoz
    class TeKezd_Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SettingsManager.teKezdValasztva = true;
            updateGombok();
            SettingsManager.ment();
        }
    }
    // Eseménykezelő az „botkezd” gombhoz
    class BotKezd_Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SettingsManager.teKezdValasztva = false;
            updateGombok();
            SettingsManager.ment();
        }
    }
}