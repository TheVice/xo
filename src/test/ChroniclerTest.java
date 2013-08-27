package test;

import org.junit.Test;
import org.junit.Assert;

import core.Cell;
import core.Field;
import core.Chronicler;

public class ChroniclerTest extends Assert {

    @Test
    public void chroniclerCreate() {

        for (int i = 0; i < 5; i += 4) {

            Chronicler chronicler = new Chronicler(i);

            assertFalse(chronicler.addWalk(null));

            Cell cell = new Cell(1, 1, ' ');
            cell.makeMove('X');
            assertTrue(chronicler.addWalk(cell));

            cell = new Cell(1, 1, ' ');
            cell.makeMove('X');
            assertFalse(chronicler.addWalk(cell));

            cell = new Cell(1, 2, ' ');
            cell.makeMove('X');
            assertTrue(chronicler.addWalk(cell));

            cell = new Cell(2, 1, ' ');
            cell.makeMove('X');
            assertTrue(chronicler.addWalk(cell));

            cell = new Cell(2, 2, ' ');
            cell.makeMove('X');
            assertTrue(chronicler.addWalk(cell));

            cell = new Cell(3, 3, ' ');
            cell.makeMove('X');
            assertFalse(chronicler.addWalk(cell));
        }

        Chronicler chronicler = new Chronicler(128 * 128);

        for (int j = 1; j <= 64; j++) {

            for (int i = 1; i <= 64; i++) {

                Cell cell = new Cell(i, j, ' ');
                cell.makeMove('X');
                assertTrue(chronicler.addWalk(cell));
            }
        }

        Cell cell = new Cell(0, 0, ' ');
        cell.makeMove('X');
        assertFalse(chronicler.addWalk(cell));

        cell = new Cell(65, 65, ' ');
        cell.makeMove('X');
        assertFalse(chronicler.addWalk(cell));
    }

    @Test
    public void chroniclerUndo() {

        Chronicler chronicler = new Chronicler(0);

        Cell cell = new Cell(1, 2, ' ');
        cell.makeMove('X');
        assertTrue(chronicler.addWalk(cell));

        assertNotEquals("", chronicler.toString());
        chronicler.undoLastWalk(null);
        assertEquals("", chronicler.toString());

        for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

            chronicler = new Chronicler(0);
            Field field = new Field(2, 2, ' ', scs);

            cell = new Cell(1, 1, ' ');
            cell.makeMove('X');

            assertTrue(field.setCell(cell.getX(), cell.getY(), cell.getFigure()));
            assertTrue(chronicler.addWalk(cell));

            cell = new Cell(1, 2, ' ');
            cell.makeMove('O');

            assertTrue(field.setCell(cell.getX(), cell.getY(), cell.getFigure()));
            assertTrue(chronicler.addWalk(cell));
            assertFalse(field.setCell(cell.getX(), cell.getY(), cell.getFigure()));
            assertFalse(chronicler.addWalk(cell));

            chronicler.undoLastWalk(field);
            assertEquals("Step - 1. Figure X on x = 1 y = 1\n", chronicler.toString());
            assertTrue(field.setCell(cell.getX(), cell.getY(), cell.getFigure()));
        }
    }

    @Test
    public void chroniclerRevertTo() {

        Chronicler chronicler = new Chronicler(0);
        assertEquals(0, chronicler.getStepCount());

        Cell cell = new Cell(1, 1, ' ');
        cell.makeMove('X');
        chronicler.addWalk(cell);

        cell = new Cell(1, 2, ' ');
        cell.makeMove('O');
        chronicler.addWalk(cell);

        cell = new Cell(2, 2, ' ');
        cell.makeMove('X');
        chronicler.addWalk(cell);

        cell = new Cell(2, 1, ' ');
        cell.makeMove('O');
        chronicler.addWalk(cell);

        assertEquals(4, chronicler.getStepCount());
        assertEquals('O', chronicler.revertTo(4, null));
        assertEquals('O', chronicler.revertTo(5, null));
        assertEquals('O', chronicler.revertTo(-1, null));

        assertEquals('X', chronicler.revertTo(3, null));
        assertEquals('X', chronicler.revertTo(3, null));
        assertEquals('O', chronicler.revertTo(2, null));
        assertEquals('O', chronicler.revertTo(2, null));

        assertEquals('X', chronicler.revertTo(1, null));

        assertEquals(' ', chronicler.revertTo(0, null));
        assertEquals(' ', chronicler.revertTo(0, null));
    }

    @Test
    public void chroniclerShowWalks() {

        Chronicler chronicler = new Chronicler(0);
        chronicler.addWalk(new Cell(1, 2, ' '));

        assertEquals("", chronicler.toString());
        assertEquals(0, chronicler.getStepCount());

        Cell cell = new Cell(1, 1, ' ');
        cell.makeMove('X');
        chronicler.addWalk(cell);

        cell = new Cell(1, 2, ' ');
        cell.makeMove('O');
        chronicler.addWalk(cell);

        cell = new Cell(2, 1, ' ');
        cell.makeMove('X');
        chronicler.addWalk(cell);

        cell = new Cell(2, 2, ' ');
        cell.makeMove('O');
        chronicler.addWalk(cell);

        String checkString = "Step - 1. Figure X on x = 1 y = 1\n" +
                "Step - 2. Figure O on x = 1 y = 2\n" +
                "Step - 3. Figure X on x = 2 y = 1\n" +
                "Step - 4. Figure O on x = 2 y = 2\n";
        assertEquals(checkString, chronicler.toString());
        assertEquals(4, chronicler.getStepCount());

        String checkStrings[] = new String[8];
        checkStrings[0] = checkString;
        checkStrings[1] = checkString;
        checkStrings[2] = "Step - 1. Figure X on x = 1 y = 1\n" +
                        "Step - 2. Figure O on x = 1 y = 2\n" +
                        "Step - 3. Figure X on x = 2 y = 1\n";
        checkStrings[3] = "Step - 1. Figure X on x = 1 y = 1\n" +
                "Step - 2. Figure O on x = 1 y = 2\n";
        checkStrings[4] = "Step - 1. Figure X on x = 1 y = 1\n";
        checkStrings[5] = "";
        checkStrings[6] = "";
        checkStrings[7] = "";

        int i = 0;
        for(int stepNum = chronicler.getStepCount() + 1; stepNum > -2; stepNum--) {

            chronicler.revertTo(stepNum, null);
            assertEquals(checkStrings[i++], chronicler.toString());
        }
    }
}
