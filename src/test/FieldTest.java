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
    public void fieldStartCellStyle() throws Exception {

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

    @Test
    public void isFigureFillLine() throws Exception {

        for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

            Field field = new Field(3, 4, ' ', scs);
            assertTrue(field.isFigureFillLine(Field.getDefFigureValue()));
            assertFalse(field.isFigureFillLine('X'));
            assertFalse(field.isFigureFillLine('O'));

            fillLine(field, 1, 'X', true);
            assertTrue(field.isFigureFillLine('X'));
            assertFalse(field.isFigureFillLine('O'));
            fillLine(field, field.getHeightCount(), 'O', false);

            assertTrue(field.isFigureFillLine('X'));
            assertFalse(field.isFigureFillLine('O'));

            fillLine(field, 1, Field.getDefFigureValue(), true);
            fillLine(field, field.getHeightCount(), 'O', false);
            assertTrue(field.isFigureFillLine('O'));
            assertFalse(field.isFigureFillLine('X'));
        }
    }

    @Test
    public void isFigureFillDiagonal() throws Exception {

        for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

            Field field = new Field(3, 4, ' ', scs);
            assertFalse(field.isFigureFillDiagonal(Field.getDefFigureValue()));

            fillDiagonal(field, 'X', true);
            assertFalse(field.isFigureFillDiagonal('X'));

            fillDiagonal(field, 'O', false);
            assertFalse(field.isFigureFillDiagonal('O'));

            field = new Field(6, 6, ' ', scs);
            assertTrue(field.isFigureFillDiagonal(Field.getDefFigureValue()));

            fillDiagonal(field, 'X', true);
            assertTrue(field.isFigureFillDiagonal('X'));

            fillDiagonal(field, 'O', false);
            assertTrue(field.isFigureFillDiagonal('O'));

            field.setCell(3, 3, Field.getDefFigureValue());
            field.setCell(3, 4, Field.getDefFigureValue());

            assertFalse(field.isFigureFillDiagonal('X'));
            assertFalse(field.isFigureFillDiagonal('O'));
        }
    }

    private static void fillLine(Field field, int lineNum, char figure, boolean vertical) {

        if (vertical) {

            if (lineNum > field.getWidthCount()) {

                lineNum = field.getWidthCount();
            } else if (lineNum < 1) {

                lineNum = 1;
            }

            for (int i = 1; i <= field.getHeightCount(); i++) {

                field.setCell(lineNum, i, figure);
            }
        } else {

            if (lineNum > field.getHeightCount()) {

                lineNum = field.getHeightCount();
            } else if (lineNum < 1) {

                lineNum = 1;
            }

            for (int i = 1; i <= field.getWidthCount(); i++) {

                field.setCell(i, lineNum, figure);
            }
        }
    }

    private static void fillDiagonal(Field field, char figure, boolean main) {

        final int count = min(field.getWidthCount(), field.getHeightCount());
        if (main) {

            for (int i = 1; i <= count; i++) {

                field.setCell(i, i, Field.getDefFigureValue());
                field.setCell(i, i, figure);
            }
        } else {

            for (int i = count; i > 0; i--) {

                field.setCell(i, count - i + 1, Field.getDefFigureValue());
                field.setCell(i, count - i + 1, figure);
            }
        }
    }

    private static int min(int x, int y) {

        if (x < y) {

            return x;
        }
        return y;
    }
}