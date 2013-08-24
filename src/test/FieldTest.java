package test;

import org.junit.Test;
import org.junit.Assert;

import core.Field;

public class FieldTest extends Assert {

    @Test
    public void fieldConstructor() throws Exception {

        assertEquals(Field.StartCellStyle.BOTTOM_LEFT, Field.int2style(0));
        assertEquals(Field.StartCellStyle.BOTTOM_LEFT, Field.int2style(5));

        Field field;

        for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

            field = new Field(0, 0, '*', scs);
            assertEquals(Field.getMinWidth(), field.getWidthCount());
            assertEquals(Field.getMinHeight(), field.getHeightCount());

            field = new Field(0, Field.getMinHeight(), '?', scs);
            assertEquals(Field.getMinWidth(), field.getWidthCount());
            assertEquals(Field.getMinHeight(), field.getHeightCount());

            field = new Field(Field.getMinWidth(), 0, '@', scs);
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
        }
    }

    @Test
    public void fieldSetCell() throws Exception {

        Field field = new Field(3, 4, '%', Field.StartCellStyle.BOTTOM_LEFT);
        assertEquals(3, field.getWidthCount());
        assertEquals(4, field.getHeightCount());

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

            assertFalse(field.isFull());

            for (int j = 1; j <= field.getHeightCount(); j++) {

                for (int i = 1; i <= field.getWidthCount(); i++) {

                    assertTrue(field.setCell(i, j, 'X'));
                    assertFalse(field.setCell(i, j, 'O'));
                    assertTrue(field.setCell(i, j, Field.getDefFigureValue()));
                    assertTrue(field.setCell(i, j, 'O'));
                    assertTrue(field.setCell(i, j, Field.getDefFigureValue()));
                    assertTrue(field.setCell(i, j, 'X'));

                    if (j != field.getHeightCount() && i != field.getWidthCount()) {

                        assertFalse(field.isFull());
                    } else if (j == field.getHeightCount() && i == field.getWidthCount()) {

                        assertTrue(field.isFull());
                    }
                }
            }
        }

        for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

            field = new Field(12, 4, ' ', scs);

            for (int j = field.getHeightCount(); j > 0; j--) {

                for (int i = field.getWidthCount(); i > 0; i--) {

                    assertTrue(field.setCell(i, j, 'X'));
                    assertFalse(field.setCell(i, j, 'O'));
                    assertTrue(field.setCell(i, j, Field.getDefFigureValue()));
                    assertTrue(field.setCell(i, j, 'O'));
                    assertTrue(field.setCell(i, j, Field.getDefFigureValue()));
                    assertTrue(field.setCell(i, j, 'X'));

                    if (j != 1 && i != 1) {

                        assertFalse(field.isFull());
                    } else if (j == 1 && i == 1) {

                        assertTrue(field.isFull());
                    }
                }
            }
        }
    }

    @Test
    public void fieldCheckSetStartStyle() throws Exception {

        String checkString[] = {"[1][2][3]\n[4][5][6]\n",
                "[3][2][1]\n[6][5][4]\n",
                "[6][5][4]\n[3][2][1]\n",
                "[4][5][6]\n[1][2][3]\n"};

        int stringNum = 0;
        for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

            Field field = new Field(3, 2, ' ', scs);

            Integer numToCell = 1;
            for (int j = 1; j <= field.getHeightCount(); j++) {
                for (int i = 1; i <= field.getWidthCount(); i++) {

                    field.setCell(i, j, (numToCell++).toString().charAt(0));
                }
            }

            assertEquals(checkString[stringNum++], field.toString());
        }
    }
}