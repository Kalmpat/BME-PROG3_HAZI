import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class PlayerNumberPanel extends JPanel {

    private JButton egyJatekos, ketJatekos;
    private DifficultyPanel difficultyPanel;
    private StartingPlayerPanel startingPlayerPanel;
    private SymbolSelectionPanel symbolSelectionPanel;

    public PlayerNumberPanel() {
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        // szöveg (címke) beállítása
        this.add(Box.createRigidArea(new Dimension(10, 0)));
        JLabel jatekosLabel = new JLabel("Játékosok száma:");
        jatekosLabel.setFont(new Font("Arial", Font.BOLD, 20));
        jatekosLabel.setForeground(Color.WHITE);
        this.add(jatekosLabel);


        // Gombok beállítása
        egyJatekos = new JButton("1");
        ketJatekos = new JButton("2");

        egyJatekos.setBackground(new Color(57, 56, 55));
        egyJatekos.setForeground(Color.WHITE);
        egyJatekos.setBorder(new LineBorder(new Color(30, 31, 34), 2));
        egyJatekos.setFocusable(false);

        ketJatekos.setBackground(new Color(57, 56, 55));
        ketJatekos.setForeground(Color.WHITE);
        ketJatekos.setBorder(new LineBorder(new Color(30, 31, 34), 2));
        ketJatekos.setFocusable(false);

        Dimension gombMeret = new Dimension(100, 40);
        egyJatekos.setPreferredSize(gombMeret);
        ketJatekos.setPreferredSize(gombMeret);

        // Actionok
        egyJatekos.addActionListener(new Egy_Click());
        ketJatekos.addActionListener(new Ketto_Click());

        this.add(egyJatekos);
        this.add(ketJatekos);
    }

    /**
     * Referencia beállítása a többi panelhez, hogy azok is frissüljenek
     * amikor a játékosok számát választjuk.
     */
    public void setReferencePanels(DifficultyPanel diffPanel, StartingPlayerPanel startPanel, SymbolSelectionPanel symbolPanel) {
        this.difficultyPanel = diffPanel;
        this.startingPlayerPanel = startPanel;
        this.symbolSelectionPanel = symbolPanel;
    }

    /**
     * Gombok állapotának frissítése a SettingsManager értékei alapján.
     * Kiemelés narancssárgára, ha éppen az adott opció van kiválasztva.
     */

    public void updateGombok() {
        egyJatekos.setForeground(SettingsManager.egyJatekosValasztva ? Color.ORANGE : Color.WHITE);
        ketJatekos.setForeground(SettingsManager.ketJatekosValasztva ? Color.ORANGE : Color.WHITE);

        egyJatekos.setBorder(new LineBorder(SettingsManager.egyJatekosValasztva ? Color.ORANGE : new Color(30, 31, 34), 2));
        ketJatekos.setBorder(new LineBorder(SettingsManager.ketJatekosValasztva ? Color.ORANGE : new Color(30, 31, 34), 2));
    }

    // Eseménykezelő az „1 játékos” gombhoz
    class Egy_Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SettingsManager.egyJatekosValasztva = true;
            SettingsManager.ketJatekosValasztva = false;
            updateGombok();
            SettingsManager.ment();
            if(difficultyPanel != null) difficultyPanel.updateGombok();
            if(startingPlayerPanel != null) startingPlayerPanel.updateGombok();
            if(symbolSelectionPanel != null) symbolSelectionPanel.updateGombok();
        }
    }

    // Eseménykezelő a „2 játékos” gombhoz
    class Ketto_Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SettingsManager.egyJatekosValasztva = false;
            SettingsManager.ketJatekosValasztva = true;
            updateGombok();
            SettingsManager.ment();
            if(difficultyPanel != null) difficultyPanel.updateGombok();
            if(startingPlayerPanel != null) startingPlayerPanel.updateGombok();
            if(symbolSelectionPanel != null) symbolSelectionPanel.updateGombok();
        }
    }
}