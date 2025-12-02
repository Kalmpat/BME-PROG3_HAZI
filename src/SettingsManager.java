import java.io.*;

public class SettingsManager {

    // Nehézségi szint
    public static boolean kezdoValasztva = true; // kezdő szint alapértelmezett
    public static boolean kozepesValasztva = false;
    public static boolean nehezValasztva = false;

    // Játékosok száma
    public static boolean egyJatekosValasztva = true; // 1 játékos alapértelmezett
    public static boolean ketJatekosValasztva = false;

    public static boolean teKezdValasztva = true; // felhasználó kezd
    public static String felhasznaloJel = "X"; // felhasználó jele alapértelmezett X




    // Mentés fájlba
    public static void ment() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("settings.txt"))) {
            writer.write(egyJatekosValasztva + "\n");
            writer.write(ketJatekosValasztva + "\n");
            writer.write(teKezdValasztva + "\n");
            writer.write(kezdoValasztva + "\n");
            writer.write(kozepesValasztva + "\n");
            writer.write(nehezValasztva + "\n");
            writer.write(felhasznaloJel + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Beolvasás fájlból
    public static void betolt() {
        try (BufferedReader reader = new BufferedReader(new FileReader("settings.txt"))) {
            egyJatekosValasztva = Boolean.parseBoolean(reader.readLine());
            ketJatekosValasztva = Boolean.parseBoolean(reader.readLine());
            teKezdValasztva = Boolean.parseBoolean(reader.readLine());
            kezdoValasztva = Boolean.parseBoolean(reader.readLine());
            kozepesValasztva = Boolean.parseBoolean(reader.readLine());
            nehezValasztva = Boolean.parseBoolean(reader.readLine());
            felhasznaloJel = reader.readLine();
        } catch (IOException e) {
            System.out.println("Settings.txt nem található");
        }
    }



}
