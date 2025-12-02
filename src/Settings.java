import javax.swing.*;
import java.awt.*;

public class Settings extends JFrame {

    JLabel felirat;
    DifficultyPanel difficultyPanel;
    PlayerNumberPanel playerNumberPanel;
    StartingPlayerPanel startingPlayerPanel;
    SymbolSelectionPanel symbolSelectionPanel;

    public Settings() {

        // Ablak beállításai
        this.setTitle("Beállítások");
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // Háttérképes JLabel betöltése az Image mappából
        ImageIcon hatterIcon = new ImageIcon(getClass().getResource("/image/menu.png"));
        JLabel hatterLabel = new JLabel(hatterIcon);
        hatterLabel.setLayout(new BorderLayout());
        this.setContentPane(hatterLabel);

        // Felirat panel
        JPanel feliratPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        feliratPanel.setOpaque(false);
        felirat = new JLabel("Beállítások");
        felirat.setFont(new Font("Arial", Font.BOLD, 28));
        felirat.setForeground(Color.ORANGE);
        feliratPanel.add(felirat);
        hatterLabel.add(feliratPanel, BorderLayout.NORTH);

        // Fő panel
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridLayout(4, 1, 10, 10));

        // Panelok létrehozása
        difficultyPanel = new DifficultyPanel();
        playerNumberPanel = new PlayerNumberPanel();
        startingPlayerPanel = new StartingPlayerPanel();
        symbolSelectionPanel = new SymbolSelectionPanel();

        // Referenciák beállítása a PlayerNumberPanel-ben
        playerNumberPanel.setReferencePanels(difficultyPanel, startingPlayerPanel, symbolSelectionPanel);

        // Panelok hozzáadása
        mainPanel.add(difficultyPanel);
        mainPanel.add(playerNumberPanel);
        mainPanel.add(startingPlayerPanel);
        mainPanel.add(symbolSelectionPanel);

        hatterLabel.add(mainPanel, BorderLayout.CENTER);

        // Kezdeti állapot frissítése
        difficultyPanel.updateGombok();
        playerNumberPanel.updateGombok();
        startingPlayerPanel.updateGombok();
        symbolSelectionPanel.updateGombok();

        this.setVisible(true);
    }
}