package Vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.Random;

public class Teningur {
    private static final int MAX = 7; // Hámarks gildi
    private final IntegerProperty talaProperty = new SimpleIntegerProperty(1);
    private final Random random = new Random();

    /**
     * Kastar tening og uppfærir töluna.
     */
    public void kasta() {
        talaProperty.set(random.nextInt(1,MAX));
    }

    /**
     * Nær í töluna á teningnum sem Property.
     */
    public IntegerProperty getTalaProperty() {
        return talaProperty;
    }

    /**
     * Skilar núverandi tölu á teningnum.
     */
    public int getTala() {
        return talaProperty.get();
    }

    public static void main(String[] args) {
        Teningur teningur = new Teningur();
        for (int i = 0; i < 15; i++) {
            teningur.kasta();
            System.out.println("Teningur sýnir: " + teningur.getTala());
        }
    }
}