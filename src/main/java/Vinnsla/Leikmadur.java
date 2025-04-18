package Vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Leikmadur {
    private final StringProperty nafn = new SimpleStringProperty();
    private final IntegerProperty reitur = new SimpleIntegerProperty(1); // Byrjar á reit 1

    /**
     * Smiður sem tekur inn nafn leikmanns.
     */
    public Leikmadur(String nafn) {
        this.nafn.set(nafn);
    }

    /**
     * Færir leikmann á nýjan reit.
     * @param nyReitur Reiturinn sem leikmaður færist á.
     * @param max Lokareitur á borðinu.
     */
    public void faera(int nyReitur, int max) {
        reitur.set(Math.min(nyReitur, max)); // Ef leikmaður fer fram yfir lokareit, settur á síðasta reit
    }

    /**
     * Skilar nafni leikmanns sem Property.
     */
    public StringProperty nafnProperty() {
        return nafn;
    }

    /**
     * Skilar reit leikmanns sem Property.
     */
    public IntegerProperty reiturProperty() {
        return reitur;
    }

    /**
     * Skilar nafni leikmanns.
     */
    public String getNafn() {
        return nafn.get();
    }

    /**
     * Skilar núverandi reit leikmanns.
     */
    public int getReitur() {
        return reitur.get();
    }

    public static void main(String[] args) {
        Leikmadur l = new Leikmadur("Bergur");
        System.out.println(l.getNafn() + " er á reit: " + l.getReitur());

        l.faera(5, 24);
        System.out.println(l.getNafn() + " færður á reit: " + l.getReitur());

        l.faera(25, 24); // Prófum að fara yfir max
        System.out.println(l.getNafn() + " er nú á lokareit: " + l.getReitur());
    }
}
