package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import core.Field;

public class FieldTest extends Assert {

    private Field field;

    @Before
    public void setUp() throws Exception {

        field = new Field(-1, -1);
        assertEquals(2, field.getWidthCount());
        assertEquals(2, field.getHeightCount());

        field = new Field(-1, 2);
        assertEquals(2, field.getWidthCount());
        assertEquals(2, field.getHeightCount());

        field = new Field(2, -1);
        assertEquals(2, field.getWidthCount());
        assertEquals(2, field.getHeightCount());

        field = new Field(128, 128);
        assertEquals(64, field.getWidthCount());
        assertEquals(64, field.getHeightCount());

        field = new Field(128, 64);
        assertEquals(64, field.getWidthCount());
        assertEquals(64, field.getHeightCount());

        field = new Field(64, 128);
        assertEquals(64, field.getWidthCount());
        assertEquals(64, field.getHeightCount());

        field = new Field(3, 4);
        assertEquals(3, field.getWidthCount());
        assertEquals(4, field.getHeightCount());
        //assertEquals(' ', field.getDefFigureValue());   different behavior at Eclipse ('\0') and IDEA (' ')
    }


    @Test
    public void setCell() {

        assertFalse(field.setCell(0, 0, 'X'));
        assertFalse(field.setCell(field.getWidthCount() - 1, field.getHeightCount() - 1, 'O'));
        assertFalse(field.setCell(0, 0, field.getDefFigureValue()));
        assertFalse(field.setCell(field.getWidthCount() - 1, field.getHeightCount() - 1, field.getDefFigureValue()));
        assertFalse(field.setCell(0, 0, 'X'));
        assertFalse(field.setCell(field.getWidthCount() - 1, field.getHeightCount() - 1, 'O'));

        assertFalse(field.setCell(field.getWidthCount(), field.getHeightCount(), field.getDefFigureValue()));
        assertFalse(field.setCell(field.getWidthCount() - 1, field.getHeightCount(), field.getDefFigureValue()));
        assertFalse(field.setCell(field.getWidthCount(), field.getHeightCount() - 1, field.getDefFigureValue()));
        assertFalse(field.setCell(-1, -1, field.getDefFigureValue()));
        assertFalse(field.setCell(-1, 0, field.getDefFigureValue()));
        assertFalse(field.setCell(0, -1, field.getDefFigureValue()));

        field.setupCells(' ');

        for (int i = 0; i < field.getWidthCount(); i++) {

            for (int j = 0; j < field.getHeightCount(); j++) {

                assertTrue(field.setCell(i, j, 'X'));
                assertFalse(field.setCell(i, j, 'O'));
                assertTrue(field.setCell(i, j, field.getDefFigureValue()));
                assertTrue(field.setCell(i, j, 'O'));
                assertTrue(field.setCell(i, j, field.getDefFigureValue()));
            }
        }

        assertFalse(field.setCell(field.getWidthCount(), field.getHeightCount(), field.getDefFigureValue()));
        assertFalse(field.setCell(field.getWidthCount() - 1, field.getHeightCount(), field.getDefFigureValue()));
        assertFalse(field.setCell(field.getWidthCount(), field.getHeightCount() - 1, field.getDefFigureValue()));
        assertFalse(field.setCell(-1, -1, field.getDefFigureValue()));
        assertFalse(field.setCell(-1, 0, field.getDefFigureValue()));
        assertFalse(field.setCell(0, -1, field.getDefFigureValue()));
    }
}