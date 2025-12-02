
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class Tesztek {

    Bot bot;
    Amoba game;

    @BeforeEach
    void setup() {
        // Settings alaphelyzet
        SettingsManager.kezdoValasztva = true;
        SettingsManager.kozepesValasztva = false;
        SettingsManager.nehezValasztva = false;

        SettingsManager.egyJatekosValasztva = true;
        SettingsManager.ketJatekosValasztva = false;
        SettingsManager.teKezdValasztva = true;
        SettingsManager.felhasznaloJel = "X";


        game = new Amoba();
        bot = new Bot(game);
    }

    // SettingsManager tesztek

    @Test
    void testDefaultDifficulty() {
        // Ellenőrizzük, hogy a kezdő nehézség alapértelmezett-e
        assertTrue(SettingsManager.kezdoValasztva);
        assertFalse(SettingsManager.kozepesValasztva);
        assertFalse(SettingsManager.nehezValasztva);
    }

    @Test
    void testSetMediumDifficulty() {
        // Beállítjuk a közepes nehézséget és ellenőrizzük
        SettingsManager.kezdoValasztva = false;
        SettingsManager.kozepesValasztva = true;
        assertTrue(SettingsManager.kozepesValasztva);
        assertFalse(SettingsManager.kezdoValasztva);
        assertFalse(SettingsManager.nehezValasztva);
    }

    @Test
    void testSymbolChange() {
        // Felhasználói jel változtatásának tesztelése
        SettingsManager.felhasznaloJel = "O";
        assertEquals("O", SettingsManager.felhasznaloJel);
    }

    @Test
    void testStartingPlayerSwitch() {
        // Ellenőrizzük, hogy bot kezd-e vagyis nem a felhasználó
        SettingsManager.teKezdValasztva = false;
        assertFalse(SettingsManager.teKezdValasztva);
    }


    // Bot tesztek
    @Test
    void testRandomMove() {
        SettingsManager.kezdoValasztva = true;
        SettingsManager.kozepesValasztva = false;
        SettingsManager.nehezValasztva = false;
        bot = new Bot(game);
        // Véletlenszerű lépés generálása valid indexre
        int idx = bot.randomLepes();
        assertTrue(idx >= 0 && idx < 225);
    }

    @Test
    void testMediumMove() {
        SettingsManager.kezdoValasztva = false;
        SettingsManager.kozepesValasztva = true;
        SettingsManager.nehezValasztva = false;
        bot = new Bot(game);

        // Közepes nehézségű lépés valid indexe
        int idx = bot.kozepesLepes();
        assertTrue(idx >= 0 && idx < 225);
    }

    @Test
    void testHardMove() {
        // Nehéz szint beállítása
        SettingsManager.kezdoValasztva = false;
        SettingsManager.kozepesValasztva = false;
        SettingsManager.nehezValasztva = true;
        bot = new Bot(game);

        // Nehéz szintű lépés valid indexe
        int idx = bot.nehezLepes();
        assertTrue(idx >= 0 && idx < 225);
    }

    @Test
    void testWinningMoveDetection() {
        // Tegyünk 4 X-et vízszintesen sorba
        game.gombok[0].setText("X");
        game.gombok[1].setText("X");
        game.gombok[2].setText("X");
        game.gombok[3].setText("X");

        int nyero = bot.keresNyeroLepes("X");
        assertEquals(4, nyero); // 4. indexet kell tölteni
    }

    // Amoba tesztek

    @Test
    void testDefaultStartingPlayer() {
        // Alapértelmezett, felhasználó kezd X-szel
        game.korok();
        // Ellenőrizzük a korok metódust
        if (SettingsManager.felhasznaloJel.equals("X") && SettingsManager.teKezdValasztva) {
            assertTrue(game.jatekos_1);
        } else if (SettingsManager.felhasznaloJel.equals("O") && SettingsManager.teKezdValasztva) {
            assertFalse(game.jatekos_1);
        }
    }

    @Test
    void testXWinDetection() {
        // Tegyük 5 X-et vízszintesen az első sorba
        game.gombok[0].setText("X");
        game.gombok[1].setText("X");
        game.gombok[2].setText("X");
        game.gombok[3].setText("X");
        game.gombok[4].setText("X");

        // Ellenőrizzük, hogy a játék véget ért
        game.ellenorzes();
        assertTrue(game.jatekVege); // X győzelem miatt vége kell legyen
    }


}
