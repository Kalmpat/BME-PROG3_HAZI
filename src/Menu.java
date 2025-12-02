import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    JLabel felirat;
    JButton play;
    JButton settings;
    JButton quit;
    JPanel gombPanel;

    Menu() {

        // Ablak beállításai
        this.setTitle("Menu");
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); //középre igazítás
        this.setResizable(false); //fix méret megtartása


        // Háttérképes JLabel betöltése az Image mappából
        ImageIcon hatterIcon = new ImageIcon(getClass().getResource("/image/menu.png"));
        JLabel hatterLabel = new JLabel(hatterIcon);
        hatterLabel.setLayout(new BorderLayout());
        this.setContentPane(hatterLabel);

        // Felirat beállítása
        felirat = new JLabel("Amőba játék", SwingConstants.CENTER);
        felirat.setFont(new Font("Arial", Font.BOLD, 28));
        felirat.setOpaque(true);
        felirat.setBackground(new Color(10, 10, 10, 10));
        felirat.setForeground(Color.orange);
        hatterLabel.add(felirat, BorderLayout.NORTH);

        // Gombok beállírása
        gombPanel = new JPanel();
        gombPanel.setLayout(new BoxLayout(gombPanel, BoxLayout.Y_AXIS));
        gombPanel.setOpaque(false); // átlátszó, hogy a háttér látszódjon

        play = new JButton("Új játék");
        settings = new JButton("Beállítások");
        quit = new JButton("Kilépés");

        play.setFocusable(false);
        settings.setFocusable(false);
        quit.setFocusable(false);

        // Gombok stílusa
        play.setFont(new Font("Arial", Font.BOLD, 20));
        settings.setFont(new Font("Arial", Font.BOLD, 20));
        quit.setFont(new Font("Arial", Font.BOLD, 20));

        play.setBackground(new Color(57, 56, 55));
        play.setForeground(Color.WHITE);
        play.setBorder(new LineBorder(new Color(30, 31, 34), 3));
        settings.setBackground(new Color(57, 56, 55));
        settings.setForeground(Color.WHITE);
        settings.setBorder(new LineBorder(new Color(30, 31, 34), 3));
        quit.setBackground(new Color(57, 56, 55));
        quit.setForeground(Color.WHITE);
        quit.setBorder(new LineBorder(new Color(30, 31, 34), 3));

        play.setAlignmentX(Component.CENTER_ALIGNMENT);
        settings.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Gombok mérete
        Dimension gombMeret = new Dimension(200, 50);
        play.setMaximumSize(gombMeret);
        settings.setMaximumSize(gombMeret);
        quit.setMaximumSize(gombMeret);


        //Action
        quit.addActionListener(new Quit_Click());
        settings.addActionListener(new Settings_Click());
        play.addActionListener(new Play_Click());

        // Gombok hozzáadása panelhez
        gombPanel.add(Box.createRigidArea(new Dimension(0, 20))); //térköz
        gombPanel.add(settings);
        gombPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gombPanel.add(play);
        gombPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gombPanel.add(quit);

        // Panel hozzáadása a háttérhez
        hatterLabel.add(gombPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }


    // Eseménykezelők (actionok)
    class Quit_Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class Settings_Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Settings();
        }
    }

    class Play_Click implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Menu.this.dispose();
            new Amoba();
        }
    }

}
