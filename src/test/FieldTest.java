package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import core.Field;

public class FieldTest extends Assert {

    private Field field;

    @Before
    public void setUp() throws Exception {

        for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

            field = new Field(-1, -1, '*', scs);
            assertEquals(Field.getMinWidth(), field.getWidthCount());
            assertEquals(Field.getMinHeight(), field.getHeightCount());

            field = new Field(-1, Field.getMinHeight(), '?', scs);
            assertEquals(Field.getMinWidth(), field.getWidthCount());
            assertEquals(Field.getMinHeight(), field.getHeightCount());

            field = new Field(Field.getMinWidth(), -1, '@', scs);
            assertEquals(Field.getMinWidth(), field.getWidthCount());
            assertEquals(Field.getMinHeight(), field.getHeightCount());

            field = new Field(128, 128, '!', scs);
            assertEquals(Field.getMaxWidth(), field.getWidthCount());
            assertEquals(Field.getMaxHeight(), field.getHeightCount());

            field = new Field(128, Field.getMaxHeight(), '#', scs);
            assertEquals(Field.getMaxWidth(), field.getWidthCount());
            assertEquals(Field.getMaxHeight(), field.getHeightCount());

            field = new Field(Field.getMaxWidth(), 128, '$', scs);
            assertEquals(Field.getMaxWidth(), field.getWidthCount());
            assertEquals(Field.getMaxHeight(), field.getHeightCount());

            field = new Field(3, 4, '%', scs);
            assertEquals(3, field.getWidthCount());
            assertEquals(4, field.getHeightCount());
        }
    }

    @Test
    public void setCell() {

        assertFalse(field.setCell(0, 0, 'X'));
        assertFalse(field.setCell(0, 0, Field.getDefFigureValue()));
        assertFalse(field.setCell(1, 0, Field.getDefFigureValue()));
        assertFalse(field.setCell(0, 1, Field.getDefFigureValue()));

        assertFalse(field.setCell(field.getWidthCount() + 1, field.getHeightCount() + 1, 'O'));
        assertFalse(field.setCell(field.getWidthCount() + 1, field.getHeightCount() + 1, Field.getDefFigureValue()));
        assertFalse(field.setCell(field.getWidthCount(), field.getHeightCount() + 1, Field.getDefFigureValue()));
        assertFalse(field.setCell(field.getWidthCount() + 1, field.getHeightCount(), Field.getDefFigureValue()));

        for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

            field = new Field(5, 16, ' ', scs);

            for (int i = 1; i <= field.getWidthCount(); i++) {

                for (int j = 1; j <= field.getHeightCount(); j++) {

                    assertTrue(field.setCell(i, j, 'X'));
                    assertFalse(field.setCell(i, j, 'O'));
                    assertTrue(field.setCell(i, j, Field.getDefFigureValue()));
                    assertTrue(field.setCell(i, j, 'O'));
                    assertTrue(field.setCell(i, j, Field.getDefFigureValue()));
                }
            }
        }

        for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

            field = new Field(12, 4, ' ', scs);

            for (int i = field.getWidthCount(); i > 0; i--) {

                for (int j = field.getHeightCount(); j > 0; j--) {

                    assertTrue(field.setCell(i, j, 'X'));
                    assertFalse(field.setCell(i, j, 'O'));
                    assertTrue(field.setCell(i, j, Field.getDefFigureValue()));
                    assertTrue(field.setCell(i, j, 'O'));
                    assertTrue(field.setCell(i, j, Field.getDefFigureValue()));
                }
            }
        }

        assertFalse(field.setCell(field.getWidthCount() + 1, field.getHeightCount() + 1, Field.getDefFigureValue()));
        assertFalse(field.setCell(field.getWidthCount(), field.getHeightCount() + 1, Field.getDefFigureValue()));
        assertFalse(field.setCell(field.getWidthCount() + 1, field.getHeightCount(), Field.getDefFigureValue()));
        assertFalse(field.setCell(0, 0, Field.getDefFigureValue()));
        assertFalse(field.setCell(1, 0, Field.getDefFigureValue()));
        assertFalse(field.setCell(0, 1, Field.getDefFigureValue()));
    }
}