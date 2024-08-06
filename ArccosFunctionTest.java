import org.junit.Test;
import static org.junit.Assert.*;

public class ArccosFunctionTest {
    @Test
    public void testArccos() {
        ArccosFunction calc = new ArccosFunction();
        assertEquals(60.0000000, calc.arccos(0.5), 1e-7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInput() {
        ArccosFunction calc = new Arccos();
        calc.arccos(2);
    }
}
