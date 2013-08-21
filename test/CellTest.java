import org.junit.Test;
import org.junit.Assert;

import core.Cell;

public class CellTest extends Assert {

    @Test
    public void test() {

        Cell cell = new Cell(1, 2, ' ');
        assertEquals(1, cell.getX());
        assertEquals(2, cell.getY());
        assertEquals(' ', Cell.getDefFigureValue());
        assertTrue(cell.makeMove('X'));
        assertTrue(cell.makeMove(Cell.getDefFigureValue()));
        assertTrue(cell.makeMove('O'));
        assertFalse(cell.makeMove('X'));
        assertTrue(cell.makeMove(Cell.getDefFigureValue()));

        cell = new Cell(4, 6, '*');
        assertEquals(' ', Cell.getDefFigureValue());
        assertEquals(4, cell.getX());
        assertEquals(6, cell.getY());
    }

}