package vinnsla;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.HashMap;
import java.util.Map;

public class SlongurStigar {
    private final Map<Integer, Integer> slongurStigar = new HashMap<>();
    private final StringProperty skilabod = new SimpleStringProperty("");

    /**
     * Smiður sem býr til slöngur og stiga.
     */
    public SlongurStigar() {
        // Skilgreinum slöngur (færa niður)
        slongurStigar.put(16, 6);
        slongurStigar.put(19, 8);
        slongurStigar.put(21, 11);

        // Skilgreinum stiga (færa upp)
        slongurStigar.put(3, 12);
        slongurStigar.put(7, 14);
        slongurStigar.put(9, 18);
    }

    /**
     * Athugar hvort reiturinn inniheldur slöngu eða stiga og skilar lendingarreit.
     * @param reitur Reitur sem er skoðaður.
     * @return Lendingarreitur ef reiturinn er slanga/stigi, annars sami reitur.
     */
    public boolean erSlanga(int reitur) {
        return slongurStigar.containsKey(reitur) && slongurStigar.get(reitur) < reitur;
    }

    public boolean erStigi(int reitur) {
        return slongurStigar.containsKey(reitur) && slongurStigar.get(reitur) > reitur;
    }

    public int faLendingarReit(int reitur) {
        if (slongurStigar.containsKey(reitur)) {
            int nyReitur = slongurStigar.get(reitur);
            skilabod.set("Þú féllst á " + reitur + " og fórst á " + nyReitur);
            return nyReitur;
        }
        skilabod.set(""); // Engin slanga/stigi
        return reitur;
    }

    /**
     * Skilar skilaboðum um slöngur/stiga.
     */
    public StringProperty skilabodProperty() {
        return skilabod;
    }

    public static void main(String[] args) {
        SlongurStigar ss = new SlongurStigar();
        int[] testReitir = {3, 16, 7, 21, 9};

        for (int reitur : testReitir) {
            int lendingarReitur = ss.faLendingarReit(reitur);
            System.out.println("Lenti á " + reitur + ", fer á " + lendingarReitur);
        }
    }
}
