package Vinnsla;

import javafx.beans.property.*;

public class Leikur {
    private final Leikmadur[] leikmenn = {new Leikmadur("Leikmaður 1"), new Leikmadur("Leikmaður 2")};
    private final Teningur teningur = new Teningur();
    private final SlongurStigar slongurStigar = new SlongurStigar();
    private final IntegerProperty nuverandiLeikmadur = new SimpleIntegerProperty(0);
    private final BooleanProperty leikLokid = new SimpleBooleanProperty(false);
    private final StringProperty sigurvegari = new SimpleStringProperty("");
    private final int lokareitur;

    /**
     * Smiður sem tekur inn fjölda raða og dálka á borðinu.
     */
    public Leikur(int radir, int dalkar) {
        this.lokareitur = radir * dalkar;
    }

    /**
     * Kastar teningi, færir leikmann og skiptir um leikmann.
     * @return `true` ef leik er lokið, annars `false`.
     */
    public boolean leikaLeik() {
        if (leikLokid.get()) return false; // Leikur er þegar búinn

        teningur.kasta(); // Kasta tening
        int kasta = teningur.getTala();
        Leikmadur leikmadur = leikmenn[nuverandiLeikmadur.get()];
        int nyReitur = leikmadur.getReitur() + kasta;
        nyReitur = slongurStigar.faLendingarReit(nyReitur); // Athuga slöngur/stiga

        leikmadur.faera(nyReitur, lokareitur);

        // Athuga sigurvegara
        if (nyReitur >= lokareitur) {
            leikLokid.set(true);
            sigurvegari.set(leikmadur.getNafn() + " hefur unnið!");
            return true;
        }

        // Skipta um leikmann
        nuverandiLeikmadur.set((nuverandiLeikmadur.get() + 1) % 2);
        return false;
    }

    /**
     * Endursetur leikinn.
     */
    public void nyrLeikur() {
        leikmenn[0].faera(1, lokareitur);
        leikmenn[1].faera(1, lokareitur);
        leikLokid.set(false);
        sigurvegari.set("");
        nuverandiLeikmadur.set(0);
    }

    public Leikmadur getLeikmadur(int i) {
        return leikmenn[i];
    }

    public Teningur getTeningur() {
        return teningur;
    }

    public StringProperty sigurvegariProperty() {
        return sigurvegari;
    }

    public BooleanProperty leikLokidProperty() {
        return leikLokid;
    }

    public IntegerProperty nuverandiLeikmadurProperty() {
        return nuverandiLeikmadur;
    }

    public SlongurStigar getSlongurStigar() {
        return slongurStigar;
    }


    public static void main(String[] args) {
        Leikur leikur = new Leikur(4, 6);
        leikur.nyrLeikur();

        for (int i = 0; i < 10; i++) {
            if (leikur.leikaLeik()) {
                System.out.println(leikur.sigurvegariProperty().get());
                break;
            }
        }
    }
}
