package test;

import org.junit.Test;
import org.junit.Assert;

import core.Cell;
import core.Chronicler;

public class ChroniclerTest extends Assert {

    @Test
    public void chroniclerCreate() {

        for (int i = 0; i < 5; i += 4) {

            Chronicler chronicler = new Chronicler(i);
            assertFalse(chronicler.addWalk(null));
            assertTrue(chronicler.addWalk(new Cell(1, 1, 'X')));
            assertFalse(chronicler.addWalk(new Cell(1, 1, 'X')));

            assertTrue(chronicler.addWalk(new Cell(1, 2, 'X')));
            assertTrue(chronicler.addWalk(new Cell(2, 1, 'X')));
            assertTrue(chronicler.addWalk(new Cell(2, 2, 'X')));

            assertFalse(chronicler.addWalk(new Cell(3, 3, 'X')));
        }

        Chronicler chronicler = new Chronicler(128 * 128);

        for (int j = 1; j <= 64; j++) {

            for (int i = 1; i <= 64; i++) {

                assertTrue(chronicler.addWalk(new Cell(i, j, 'X')));
            }
        }
        assertFalse(chronicler.addWalk(new Cell(0, 0, 'X')));
        assertFalse(chronicler.addWalk(new Cell(65, 65, 'X')));
    }

    @Test
    public void chroniclerUndo() {

        Chronicler chronicler = new Chronicler(0);
        assertEquals(null, chronicler.undoLastWalk());
        Cell inputCell = new Cell(1, 2, ' ');
        inputCell.makeMove('X');
        assertTrue(chronicler.addWalk(inputCell));
        Cell cell = chronicler.undoLastWalk();
        assertEquals(null, chronicler.undoLastWalk());
        assertEquals(inputCell, cell);
        assertEquals(1, cell.getX());
        assertEquals(2, cell.getY());
        assertEquals('X', cell.getFigure());
    }

    @Test
    public void chroniclerShowWalks() {

        Chronicler chronicler = new Chronicler(0);
        chronicler.addWalk(new Cell(1, 2, Cell.getDefFigureValue()));

        Cell cell = new Cell(2, 1, Cell.getDefFigureValue());
        cell.makeMove('X');
        chronicler.addWalk(cell);

        assertEquals("", chronicler.toString());
        assertNotEquals(null, chronicler.undoLastWalk());
        assertNotEquals(null, chronicler.undoLastWalk());
        assertEquals(null, chronicler.undoLastWalk());

        cell = new Cell(1, 1, Cell.getDefFigureValue());
        cell.makeMove('X');
        chronicler.addWalk(cell);
        cell = new Cell(1, 2, Cell.getDefFigureValue());
        cell.makeMove('O');
        chronicler.addWalk(cell);
        cell = new Cell(2, 1, Cell.getDefFigureValue());
        cell.makeMove('X');
        chronicler.addWalk(cell);
        cell = new Cell(2, 2, Cell.getDefFigureValue());
        cell.makeMove('O');
        chronicler.addWalk(cell);

        String checkString = "Step - 1. Figure X on x = 1 y = 1\n" +
                "Step - 2. Figure O on x = 1 y = 2\n" +
                "Step - 3. Figure X on x = 2 y = 1\n" +
                "Step - 4. Figure O on x = 2 y = 2\n";
        assertEquals(checkString, chronicler.toString());
    }
}
