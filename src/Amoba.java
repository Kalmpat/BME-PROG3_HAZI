

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Amoba implements ActionListener {

    Random random = new Random();
    JFrame keret = new JFrame();
    JPanel szovegmezo = new JPanel();
    JPanel gombmezo = new JPanel();
    JButton[] gombok = new JButton[225];
    JLabel szoveg = new JLabel();
    boolean jatekos_1 = true; // true = X lép, false = O lép
    boolean jatekVege = false; // játék vége jelző
    JLabel balJatekos = new JLabel();
    JLabel jobbJatekos = new JLabel();

    Bot bot = null;

    Amoba(){

        //keret beállítása

        keret.setTitle("Amőba játék");
        keret.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //keret.setSize(1000,1000);
        keret.setExtendedState(JFrame.MAXIMIZED_BOTH);
        keret.setBackground(new Color(50,50,50));
        keret.setLayout(new BorderLayout());
        keret.setVisible(true);

        //szöveg beállítása
        szoveg.setBackground(new Color(0, 0, 0));
        szoveg.setForeground(new Color(255,255,255));
        szoveg.setFont(new Font("Roboto",Font.BOLD,75));
        szoveg.setHorizontalAlignment(JLabel.CENTER);
        szoveg.setLayout(new BorderLayout());
        szoveg.setText("Amőba");
        szoveg.setOpaque(true);

        szovegmezo.setLayout(new GridLayout(1, 3));
        szovegmezo.setBackground(new Color(0, 0, 0));

        gombmezo.setLayout(new GridLayout(15,15));
        gombmezo.setBackground(new Color(0, 0, 0));

        // Bal oldali játékos label
        balJatekos.setBackground(new Color(0, 0, 0));
        balJatekos.setForeground(new Color(255,255,255));
        balJatekos.setFont(new Font("Roboto",Font.BOLD,40));
        balJatekos.setHorizontalAlignment(JLabel.CENTER);
        balJatekos.setOpaque(true);

        // Jobb oldali játékos label
        jobbJatekos.setBackground(new Color(0, 0, 0));
        jobbJatekos.setForeground(new Color(255,255,255));
        jobbJatekos.setFont(new Font("Roboto",Font.BOLD,40));
        jobbJatekos.setHorizontalAlignment(JLabel.CENTER);
        jobbJatekos.setOpaque(true);


        if (SettingsManager.egyJatekosValasztva) {
            bot = new Bot(this); // csak 1 játékosnál
        }

        // Gombok elhelyezése
        for(int i=0; i<225; i++) {
            gombok[i] = new JButton();
            gombmezo.add(gombok[i]);
            gombok[i].setBackground(new Color(238, 225, 218));
            gombok[i].setFont(new Font("Roboto",Font.BOLD, 40));
            gombok[i].setFocusable(false);
            gombok[i].addActionListener(this);



        }


        szovegmezo.add(balJatekos);
        szovegmezo.add(szoveg);
        szovegmezo.add(jobbJatekos);

        keret.add(szovegmezo, BorderLayout.NORTH);
        keret.add(gombmezo);

        Jatekosok();
        korok();

    }

    // Játékosok mutatása
    public void Jatekosok() {
        if (SettingsManager.egyJatekosValasztva) {
            // Egyjátékos mód
            if (SettingsManager.felhasznaloJel.equals("X")) {
                balJatekos.setText("Te (X)");
                jobbJatekos.setText("Bot (O)");
            } else {
                balJatekos.setText("Te (O)");
                jobbJatekos.setText("Bot (X)");
            }
        } else if (SettingsManager.ketJatekosValasztva) {
            // Kétjátékos mód
            balJatekos.setText("Játékos 1 (X)");
            jobbJatekos.setText("Játékos 2 (O)");
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (jatekVege) return;
        for(int i=0; i<225; i++) {
            if(e.getSource()==gombok[i]) {
                // Üres mező ellenőrzése
                if(gombok[i].getText().equals("")) {

                    // Egyjátékos mód
                    if(SettingsManager.egyJatekosValasztva) {
                        // Csak akkor léphet a játékos, ha ő van soron
                        if((jatekos_1 && SettingsManager.felhasznaloJel.equals("X")) ||
                                (!jatekos_1 && SettingsManager.felhasznaloJel.equals("O"))) {

                            // Felhasználó lépése
                            if(SettingsManager.felhasznaloJel.equals("X")) {
                                gombok[i].setForeground(new Color(255,0,0));
                                gombok[i].setText("X");
                                jatekos_1 = false;
                                if (!jatekVege) szoveg.setText("O jön");
                            } else {
                                gombok[i].setForeground(new Color(0,0,255));
                                gombok[i].setText("O");
                                jatekos_1 = true;
                                if (!jatekVege) szoveg.setText("X jön");
                            }

                            ellenorzes();

                            // Bot lépése késleltetéssel
                            Timer t = new Timer(300, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!jatekVege) bot.lep();
                                }
                            });
                            t.setRepeats(false);
                            t.start();
                        }
                    }
                    // Kétjátékos mód
                    else if(SettingsManager.ketJatekosValasztva) {
                        if(jatekos_1) {
                            gombok[i].setForeground(new Color(255,0,0));
                            gombok[i].setText("X");
                            jatekos_1 = false;
                            if (!jatekVege) szoveg.setText("O jön");
                            ellenorzes();
                        } else {
                            gombok[i].setForeground(new Color(0,0,255));
                            gombok[i].setText("O");
                            jatekos_1 = true;
                            if (!jatekVege) szoveg.setText("X jön");
                            ellenorzes();
                        }
                    }
                }
            }
        }
    }

    // A játék körönkénti lépéseinek beállítása
    public void korok() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Kétjátékos mód: véletlenszerűen kezd X vagy O
        if (SettingsManager.ketJatekosValasztva) {
            if (random.nextInt(2) == 0) {
                jatekos_1 = true;
                szoveg.setText("X jön");
            } else {
                jatekos_1 = false;
                szoveg.setText("O jön");
            }
        }
        // Egyjátékos mód: beállítjuk, ki kezd, felhasználó vagy bot
        else if (SettingsManager.egyJatekosValasztva) {
            // Ha a felhasználó kezd
            if (SettingsManager.teKezdValasztva) {
                // Felhasználó jele alapján állítjuk be
                if (SettingsManager.felhasznaloJel.equals("X")) {
                    jatekos_1 = true;  // Felhasználó (X) kezd
                    szoveg.setText("X jön");
                } else {
                    jatekos_1 = false;  // Felhasználó (O) kezd
                    szoveg.setText("O jön");
                }
            }
            // Ha a bot kezd
            else {
                if (SettingsManager.felhasznaloJel.equals("X")) {
                    jatekos_1 = false;  // Bot (O) kezd
                    szoveg.setText("O jön");
                    bot.lep();  // Bot lép
                } else {
                    jatekos_1 = true;  // Bot (X) kezd
                    szoveg.setText("X jön");
                    bot.lep();  // Bot lép
                }
            }
        }
    }

    /**
     * Ellenőrzi a táblát, hogy van-e nyertes.
     * Ha X nyert, meghívja az X_gyozelem metódust,
     * ha O nyert, meghívja az O_gyozelem metódust,
     * ha nincs üres mező, döntetlent jelez.
     */

    // Ellenőrzi a győzelmi feltételeket minden irányban
    public void ellenorzes() {
        int sor_max = 15;
        int oszlop_max = 15;

        // soronkénti ellenőrzés
        // Ellenőrizzük a sorokat 5 azonos jel után
        for(int sor=0; sor < sor_max; sor++) {
            for(int oszlop=0; oszlop <= oszlop_max - 5; oszlop++) {
                int idx = sor * 15 + oszlop;
                String jel = gombok[idx].getText();
                if (!jel.equals("") &&
                        jel.equals(gombok[idx + 1].getText()) &&
                        jel.equals(gombok[idx + 2].getText()) &&
                        jel.equals(gombok[idx + 3].getText()) &&
                        jel.equals(gombok[idx + 4].getText())) {

                    if (jel.equals("X")) {
                        X_gyozelem(idx, idx + 1, idx + 2, idx + 3, idx + 4);
                    } else if (jel.equals("O")) {
                        O_gyozelem(idx, idx + 1, idx + 2, idx + 3, idx + 4);
                    }
                    return;
                }
            }
        }




        // oszloponkénti ellenőrzés
        // Ellenőrizzük az oszlopokat 5 azonos jel után
        for(int oszlop = 0; oszlop < oszlop_max; oszlop++) {
            for(int sor = 0; sor <= sor_max - 5; sor++) {
                int idx = sor * 15 + oszlop;
                String jel = gombok[idx].getText();
                if (!jel.equals("") &&
                        jel.equals(gombok[idx + 15].getText()) &&
                        jel.equals(gombok[idx + 30].getText()) &&
                        jel.equals(gombok[idx + 45].getText()) &&
                        jel.equals(gombok[idx + 60].getText())) {

                    if (jel.equals("X")) {
                        X_gyozelem(idx, idx + 15, idx + 30, idx + 45, idx + 60);
                    } else if (jel.equals("O")) {
                        O_gyozelem(idx, idx + 15, idx + 30, idx + 45, idx + 60);
                    }
                    return;
                }
            }
        }

        // átlós ellenőrzés (\ irány)
        // Ellenőrizzük a diagonális (\) irányt
        for(int sor = 0; sor <= sor_max - 5; sor++) {
            for(int oszlop = 0; oszlop <= oszlop_max - 5; oszlop++) {
                int idx = sor * 15 + oszlop;
                String jel = gombok[idx].getText();
                if (!jel.equals("") &&
                        jel.equals(gombok[idx + 16].getText()) &&  // 15 + 1
                        jel.equals(gombok[idx + 32].getText()) &&  // 15*2 + 2
                        jel.equals(gombok[idx + 48].getText()) &&  // 15*3 + 3
                        jel.equals(gombok[idx + 64].getText())) {  // 15*4 + 4

                    if (jel.equals("X")) {
                        X_gyozelem(idx, idx + 16, idx + 32, idx + 48, idx + 64);
                    } else if (jel.equals("O")) {
                        O_gyozelem(idx, idx + 16, idx + 32, idx + 48, idx + 64);
                    }
                    return;
                }
            }
        }


        // átlós ellenőrzés (/ irány)
        // Ellenőrizzük a diagonális (/) irányt
        for(int sor = 0; sor <= sor_max - 5; sor++) {
            for(int oszlop = 4; oszlop < oszlop_max; oszlop++) {  // oszlop >= 4, hogy ne menjünk negatívba
                int idx = sor * 15 + oszlop;
                String jel = gombok[idx].getText();
                if (!jel.equals("") &&
                        jel.equals(gombok[idx + 14].getText()) &&  // 15 - 1
                        jel.equals(gombok[idx + 28].getText()) &&  // 15*2 - 2
                        jel.equals(gombok[idx + 42].getText()) &&  // 15*3 - 3
                        jel.equals(gombok[idx + 56].getText())) {  // 15*4 - 4

                    if (jel.equals("X")) {
                        X_gyozelem(idx, idx + 14, idx + 28, idx + 42, idx + 56);
                    } else if (jel.equals("O")) {
                        O_gyozelem(idx, idx + 14, idx + 28, idx + 42, idx + 56);
                    }
                    return;
                }
            }
        }

        // Döntetlen esetén
        boolean vanUresMezo = false;
        for(int i = 0; i < 225; i++) {
            if(gombok[i].getText().equals("")) {
                vanUresMezo = true;
                break;
            }
        }

        // Ha nincs üres mező, döntetlen
        if(!vanUresMezo) {
            dontetlen();
        }

    }


    // Döntetlen vizsgálata
    public void dontetlen() {
        if (jatekVege) return;
        jatekVege = true;

        // Minden gombot sárgára színezünk
        for(int i = 0; i < 225; i++) {
            gombok[i].setBackground(Color.YELLOW);
            gombok[i].setEnabled(false);
        }

        szoveg.setText("Döntetlen!");

        // 5 másodperc múlva visszatérés a menübe
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keret.dispose();
                new Menu();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }


    // X jel győzelének beállítása
    public void X_gyozelem(int a, int b, int c, int d, int e) {
        if (jatekVege) return;
        jatekVege = true;


        gombok[a].setBackground(Color.GREEN);
        gombok[b].setBackground(Color.GREEN);
        gombok[c].setBackground(Color.GREEN);
        gombok[d].setBackground(Color.GREEN);
        gombok[e].setBackground(Color.GREEN);
        for(int i=0; i<225; i++) {
            gombok[i].setEnabled(false);
        }
        szoveg.setText("X nyert!");


     Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keret.dispose(); // bezárjuk az Amőba ablakot
                new Menu();       // visszatérés a menübe
            }
        });
        timer.setRepeats(false); // egyszeri futtatás
        timer.start();


    }

    // O jel győzelének beállítása
    public void O_gyozelem(int a, int b, int c, int d, int e) {
        if (jatekVege) return;
        jatekVege = true;

        gombok[a].setBackground(Color.GREEN);
        gombok[b].setBackground(Color.GREEN);
        gombok[c].setBackground(Color.GREEN);
        gombok[d].setBackground(Color.GREEN);
        gombok[e].setBackground(Color.GREEN);

        for(int i=0; i<225; i++) {
            gombok[i].setEnabled(false);
        }
        szoveg.setText("O nyert!");



        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keret.dispose(); // bezárjuk az Amőba ablakot
                new Menu();       // visszatérés a menübe
            }
        });
        timer.setRepeats(false); // egyszeri futtatás
        timer.start();
    }



}

