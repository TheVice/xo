package test;

import org.junit.Test;
import org.junit.Assert;

import core.Cell;

public class CellTest extends Assert {

    @Test
    public void cellCreate() {

        Cell cell = new Cell(1, 2, ' ');
        assertEquals(1, cell.getX());
        assertEquals(2, cell.getY());
        assertTrue(cell.makeMove('X'));
        assertTrue(cell.makeMove(Cell.getDefFigureValue()));
        assertTrue(cell.makeMove('O'));
        assertFalse(cell.makeMove('X'));
        assertTrue(cell.makeMove(Cell.getDefFigureValue()));

        cell = new Cell(4, 6, '*');
        assertEquals(4, cell.getX());
        assertEquals(6, cell.getY());
        assertEquals("" + Cell.getDefFigureValue(), cell.toString());
        assertTrue(cell.makeMove('O'));
        assertEquals("O", cell.toString());
        assertTrue(cell.makeMove(Cell.getDefFigureValue()));
        assertTrue(cell.makeMove('X'));
        assertEquals("X", cell.toString());
    }

}
