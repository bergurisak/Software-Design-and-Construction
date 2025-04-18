package vinnsla;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LeikmadurTest {

    @Test
    void testFaera() {
        Leikmadur l = new Leikmadur("Bergur");
        l.faera(5, 24);
        assertEquals(5, l.getReitur());

        l.faera(30, 24); // fer yfir max
        assertEquals(24, l.getReitur());
    }

    @Test
    void testNafn() {
        Leikmadur l = new Leikmadur("Helga");
        assertEquals("Helga", l.getNafn());
    }
}
