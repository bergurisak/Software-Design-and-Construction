package Vinnsla;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SlongurStigarTest {

    @Test
    void testFaLendingarReit() {
        SlongurStigar ss = new SlongurStigar();

        assertEquals(12, ss.faLendingarReit(3));   // stigi
        assertEquals(6, ss.faLendingarReit(16));   // slanga
        assertEquals(14, ss.faLendingarReit(7));   // stigi
        assertEquals(10, ss.faLendingarReit(10));  // engin breyting
    }

    @Test
    void testErSlangaOgStigi() {
        SlongurStigar ss = new SlongurStigar();

        assertTrue(ss.erSlanga(16));
        assertFalse(ss.erSlanga(3));

        assertTrue(ss.erStigi(3));
        assertFalse(ss.erStigi(19));
    }
}
