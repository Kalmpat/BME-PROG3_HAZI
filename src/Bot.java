import java.util.*;

public class Bot {

    private int intelligencia; // 1 = kezdő, 2 = közepes, 3 = nehéz
    private Random random = new Random();
    private Amoba jatek;
    private String botJel;
    private String ellenfelJel;

    public Bot(Amoba jatek) {
        this.jatek = jatek;

        // Nehézségi szint beállítása
        if (SettingsManager.kezdoValasztva) {
            intelligencia = 1;
        } else if (SettingsManager.kozepesValasztva) {
            intelligencia = 2;
        } else if (SettingsManager.nehezValasztva) {
            intelligencia = 3;
        }

        // Bot és ellenfél jele
        if (SettingsManager.felhasznaloJel.equals("X")) {
            botJel = "O";
            ellenfelJel = "X";
        } else {
            botJel = "X";
            ellenfelJel = "O";
        }
    }
    // a bot aktuális lépését végrehajtja intelligencia alapján
    public void lep() {
        int index = -1;

        if (intelligencia == 1) {
            index = randomLepes();
        } else if (intelligencia == 2) {
            index = kozepesLepes();
        } else if (intelligencia == 3) {
            index = nehezLepes();
        }

        // Ha van érvényes lépés
        if (index != -1) {
            if (botJel.equals("X")) {
                jatek.gombok[index].setForeground(new java.awt.Color(255, 0, 0));
            } else {
                jatek.gombok[index].setForeground(new java.awt.Color(0, 0, 255));
            }
            jatek.gombok[index].setText(botJel);
            jatek.jatekos_1 = !jatek.jatekos_1;
            jatek.szoveg.setText(ellenfelJel + " jön");
            jatek.ellenorzes();
        }
    }

    // Randomlépés (véletlenszerű lépés) beállítása
    public int randomLepes() {
        List<Integer> uresMezok = new ArrayList<>();
        for (int i = 0; i < jatek.gombok.length; i++) {
            if (jatek.gombok[i].getText().equals("")) {
                uresMezok.add(i);
            }
        }
        if (!uresMezok.isEmpty()) {
            return uresMezok.get(random.nextInt(uresMezok.size()));
        }
        return -1;
    }

    // Közepes szintű lépés
    public int kozepesLepes() {
        /*// 1. Megpróbálunk nyerni
        int nyeroLepes = keresNyeroLepes(botJel);
        if (nyeroLepes != -1) return nyeroLepes;

        // 2. Blokkoljuk az ellenfelet
        int blokkLepes = keresNyeroLepes(ellenfelJel);
        if (blokkLepes != -1) return blokkLepes;

        // 3. Középső mező előnyben
        int kozepso = 112;
        if (jatek.gombok[kozepso].getText().equals("")) {
            return kozepso;
        }

        // 4. Random lépés
        return randomLepes();*/

        // Nyerő lépés
        int nyero4 = keresNSorozat(botJel, 4);
        if (nyero4 != -1) return nyero4;

        // Ellenfél blokkolása
        int blokk4 = keresNSorozat(ellenfelJel, 4);
        if (blokk4 != -1) return blokk4;

        // 3. Saját 3-as folytatása
        int nyero3 = keresNSorozat(botJel, 3);
        if (nyero3 != -1) return nyero3;

        // 4. Ellenfél 3-as blokkolása
        int blokk3 = keresNSorozat(ellenfelJel, 3);
        if (blokk3 != -1) return blokk3;

        // Középső mező
        int kozepso = 112;
        if (jatek.gombok[kozepso].getText().equals("")) return kozepso;

        // Random lépés
        return randomLepes();


    }

    // Nehéz szintű lépés
    public int nehezLepes() {
        // 1. Nyerő lépés keresése (4-es sorok)
        int nyero4 = keresNSorozat(botJel, 4);
        if (nyero4 != -1) return nyero4;

        // 2. Ellenfél 4-es blokkolása
        int blokk4 = keresNSorozat(ellenfelJel, 4);
        if (blokk4 != -1) return blokk4;

        // 3. Saját 3-as folytatása
        int nyero3 = keresNSorozat(botJel, 3);
        if (nyero3 != -1) return nyero3;

        // 4. Ellenfél 3-as blokkolása
        int blokk3 = keresNSorozat(ellenfelJel, 3);
        if (blokk3 != -1) return blokk3;

        // 5. Saját 2-es folytatása
        int nyero2 = keresNSorozat(botJel, 2);
        if (nyero2 != -1) return nyero2;

        // 6. Ellenfél 2-es blokkolása
        int blokk2 = keresNSorozat(ellenfelJel, 2);
        if (blokk2 != -1) return blokk2;

        // 7. Középső mező
        int kozepso = 112;
        if (jatek.gombok[kozepso].getText().equals("")) {
            return kozepso;
        }
        // 8. Stratégiai pozíciók (közép körüli mezők)
        int[] strategiai = {97, 98, 99, 111, 113, 126, 127, 128};
        for (int idx : strategiai) {
            if (jatek.gombok[idx].getText().equals("")) {
                return idx;
            }
        }
        // 9. Random lépés
        return randomLepes();
    }

    // Keres egy lépést, ami azonnali győzelmet eredményez a megadott jel számára.
    public int keresNyeroLepes(String jel) {
        // Keresünk egy lépést, ami 5-öt eredményez
        for (int i = 0; i < jatek.gombok.length; i++) {
            if (jatek.gombok[i].getText().equals("")) {
                // Szimuláljuk a lépést
                jatek.gombok[i].setText(jel);
                boolean nyer = ellenorizNyeres(i, jel);
                jatek.gombok[i].setText(""); // visszaállítás

                if (nyer) return i;
            }
        }
        return -1;
    }

    // Ellenőrzi, hogy az adott mezőn a jel 5-öt eredményez-e.
    public boolean ellenorizNyeres(int index, String jel) {
        int sor = index / 15;
        int oszlop = index % 15;

        // Vízszintes ellenőrzés
        int db = 1;
        for (int i = oszlop - 1; i >= 0 && jatek.gombok[sor * 15 + i].getText().equals(jel); i--) db++;
        for (int i = oszlop + 1; i < 15 && jatek.gombok[sor * 15 + i].getText().equals(jel); i++) db++;
        if (db >= 5) return true;

        // Függőleges
        db = 1;
        for (int i = sor - 1; i >= 0 && jatek.gombok[i * 15 + oszlop].getText().equals(jel); i--) db++;
        for (int i = sor + 1; i < 15 && jatek.gombok[i * 15 + oszlop].getText().equals(jel); i++) db++;
        if (db >= 5) return true;

        // Átló \
        db = 1;
        for (int i = 1; sor - i >= 0 && oszlop - i >= 0 && jatek.gombok[(sor - i) * 15 + (oszlop - i)].getText().equals(jel); i++) db++;
        for (int i = 1; sor + i < 15 && oszlop + i < 15 && jatek.gombok[(sor + i) * 15 + (oszlop + i)].getText().equals(jel); i++) db++;
        if (db >= 5) return true;

        // Átló /
        db = 1;
        for (int i = 1; sor - i >= 0 && oszlop + i < 15 && jatek.gombok[(sor - i) * 15 + (oszlop + i)].getText().equals(jel); i++) db++;
        for (int i = 1; sor + i < 15 && oszlop - i >= 0 && jatek.gombok[(sor + i) * 15 + (oszlop - i)].getText().equals(jel); i++) db++;
        if (db >= 5) return true;

        return false;
    }

    // Keres n hosszú sorozatot, amit folytatni lehet.
    public int keresNSorozat(String jel, int n) {
        // Keresünk n hosszú sorozatot, amit folytatni lehet
        for (int i = 0; i < jatek.gombok.length; i++) {
            if (jatek.gombok[i].getText().equals("")) {
                int sor = i / 15;
                int oszlop = i % 15;

                // Vízszintes
                int db = szamolSzomszedok(i, jel, 0, 1) + szamolSzomszedok(i, jel, 0, -1);
                if (db >= n) return i;

                // Függőleges
                db = szamolSzomszedok(i, jel, 1, 0) + szamolSzomszedok(i, jel, -1, 0);
                if (db >= n) return i;

                // Átló \
                db = szamolSzomszedok(i, jel, 1, 1) + szamolSzomszedok(i, jel, -1, -1);
                if (db >= n) return i;

                // Átló /
                db = szamolSzomszedok(i, jel, 1, -1) + szamolSzomszedok(i, jel, -1, 1);
                if (db >= n) return i;
            }
        }
        return -1;
    }

    // Megszámolja az adott jel szomszédos előfordulásait egy irányban maximum 4 mezőre.
    public int szamolSzomszedok(int index, String jel, int sorIrany, int oszlopIrany) {
        int sor = index / 15;
        int oszlop = index % 15;
        int db = 0;

        for (int i = 1; i < 5; i++) {
            int ujSor = sor + (sorIrany * i);
            int ujOszlop = oszlop + (oszlopIrany * i);

            if (ujSor < 0 || ujSor >= 15 || ujOszlop < 0 || ujOszlop >= 15) break;

            int idx = ujSor * 15 + ujOszlop;
            if (jatek.gombok[idx].getText().equals(jel)) {
                db++;
            } else {
                break;
            }
        }
        return db;
    }
}