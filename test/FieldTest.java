import org.junit.Test;
import org.junit.Assert;

import core.Cell;
import core.Field;

public class FieldTest extends Assert {

	@Test
	public void fieldConstructor() throws Exception {

		Field field;

		for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

			field = new Field(0, 0, scs);
			assertEquals(Field.getMinWidth(), field.getWidthCount());
			assertEquals(Field.getMinHeight(), field.getHeightCount());

			field = new Field(0, Field.getMinHeight(), scs);
			assertEquals(Field.getMinWidth(), field.getWidthCount());
			assertEquals(Field.getMinHeight(), field.getHeightCount());

			field = new Field(Field.getMinWidth(), 0, scs);
			assertEquals(Field.getMinWidth(), field.getWidthCount());
			assertEquals(Field.getMinHeight(), field.getHeightCount());

			field = new Field(128, 128, scs);
			assertEquals(Field.getMaxWidth(), field.getWidthCount());
			assertEquals(Field.getMaxHeight(), field.getHeightCount());

			field = new Field(128, Field.getMaxHeight(), scs);
			assertEquals(Field.getMaxWidth(), field.getWidthCount());
			assertEquals(Field.getMaxHeight(), field.getHeightCount());

			field = new Field(Field.getMaxWidth(), 128, scs);
			assertEquals(Field.getMaxWidth(), field.getWidthCount());
			assertEquals(Field.getMaxHeight(), field.getHeightCount());
		}
	}

	@Test
	public void fieldSetCell() throws Exception {

		Field field = new Field(3, 4, Field.StartCellStyle.BOTTOM_LEFT);
		assertEquals(3, field.getWidthCount());
		assertEquals(4, field.getHeightCount());

		assertEquals(null, field.setCell(0, 0, 'X'));
		assertEquals(null, field.setCell(0, 0, Cell.getDefaultFigure()));
		assertEquals(null, field.setCell(1, 0, Cell.getDefaultFigure()));
		assertEquals(null, field.setCell(0, 1, Cell.getDefaultFigure()));

		assertEquals(null, field.setCell(field.getWidthCount() + 1,
				field.getHeightCount() + 1, 'O'));
		assertEquals(null, field.setCell(field.getWidthCount() + 1,
				field.getHeightCount() + 1, Cell.getDefaultFigure()));
		assertEquals(null, field.setCell(field.getWidthCount(),
				field.getHeightCount() + 1, Cell.getDefaultFigure()));
		assertEquals(null, field.setCell(field.getWidthCount() + 1,
				field.getHeightCount(), Cell.getDefaultFigure()));

		for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

			field = new Field(5, 16, scs);

			assertFalse(field.isFull());

			for (int j = 1; j <= field.getHeightCount(); j++) {

				for (int i = 1; i <= field.getWidthCount(); i++) {

					assertNotEquals(null, field.setCell(i, j, 'X'));
					assertEquals(null, field.setCell(i, j, 'O'));
					assertNotEquals(null,
							field.setCell(i, j, Cell.getDefaultFigure()));
					assertNotEquals(null, field.setCell(i, j, 'O'));
					assertNotEquals(null,
							field.setCell(i, j, Cell.getDefaultFigure()));
					assertNotEquals(null, field.setCell(i, j, 'X'));

					if (j != field.getHeightCount()
							&& i != field.getWidthCount()) {

						assertFalse(field.isFull());
					} else if (j == field.getHeightCount()
							&& i == field.getWidthCount()) {

						assertTrue(field.isFull());
					}
				}
			}
		}

		for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

			field = new Field(12, 4, scs);

			for (int j = field.getHeightCount(); j > 0; j--) {

				for (int i = field.getWidthCount(); i > 0; i--) {

					assertNotEquals(null, field.setCell(i, j, 'X'));
					assertEquals(null, field.setCell(i, j, 'O'));
					assertNotEquals(null,
							field.setCell(i, j, Cell.getDefaultFigure()));
					assertNotEquals(null, field.setCell(i, j, 'O'));
					assertNotEquals(null,
							field.setCell(i, j, Cell.getDefaultFigure()));
					assertNotEquals(null, field.setCell(i, j, 'X'));

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

		final String lineSeparator = System.getProperty("line.separator");
		String checkString[] = {
				"[1][2][3]" + lineSeparator + "[4][5][6]" + lineSeparator,
				"[3][2][1]" + lineSeparator + "[6][5][4]" + lineSeparator,
				"[6][5][4]" + lineSeparator + "[3][2][1]" + lineSeparator,
				"[4][5][6]" + lineSeparator + "[1][2][3]" + lineSeparator };

		int stringNum = 0;
		for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

			Field field = new Field(3, 2, scs);

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

			Field field = new Field(3, 4, scs);
			assertTrue(field.isFigureTakeAWin(Cell.getDefaultFigure()));
			assertFalse(field.isFigureTakeAWin('X'));
			assertFalse(field.isFigureTakeAWin('O'));

			fillLine(field, 1, 'X', true);
			assertTrue(field.isFigureTakeAWin('X'));
			assertFalse(field.isFigureTakeAWin('O'));
			fillLine(field, field.getHeightCount(), 'O', false);

			assertTrue(field.isFigureTakeAWin('X'));
			assertFalse(field.isFigureTakeAWin('O'));

			fillLine(field, 1, Cell.getDefaultFigure(), true);
			fillLine(field, field.getHeightCount(), 'O', false);
			assertTrue(field.isFigureTakeAWin('O'));
			assertFalse(field.isFigureTakeAWin('X'));
		}
	}

	@Test
	public void isFigureFillDiagonal() throws Exception {

		for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

			Field field = new Field(3, 4, scs);

			fillDiagonal(field, 'X', true);
			assertFalse(field.isFigureTakeAWin('X'));

			fillDiagonal(field, 'O', false);
			assertFalse(field.isFigureTakeAWin('O'));

			field = new Field(6, 6, scs);
			assertTrue(field.isFigureTakeAWin(Cell.getDefaultFigure()));

			fillDiagonal(field, 'X', true);
			assertTrue(field.isFigureTakeAWin('X'));

			fillDiagonal(field, 'O', false);
			assertTrue(field.isFigureTakeAWin('O'));

			field.setCell(3, 3, Cell.getDefaultFigure());
			field.setCell(3, 4, Cell.getDefaultFigure());

			assertFalse(field.isFigureTakeAWin('X'));
			assertFalse(field.isFigureTakeAWin('O'));
		}
	}

	private static void fillLine(Field field, int lineNum, char figure,
			boolean vertical) {

		if (vertical) {

			for (int i = 1; i <= field.getHeightCount(); i++) {

				field.setCell(lineNum, i, figure);
			}
		} else {

			for (int i = 1; i <= field.getWidthCount(); i++) {

				field.setCell(i, lineNum, figure);
			}
		}
	}

	private static void fillDiagonal(Field field, char figure, boolean main) {

		final int count = min(field.getWidthCount(), field.getHeightCount());
		if (main) {

			for (int i = 1; i <= count; i++) {

				field.setCell(i, i, Cell.getDefaultFigure());
				field.setCell(i, i, figure);
			}
		} else {

			for (int i = count; i > 0; i--) {

				field.setCell(i, count - i + 1, Cell.getDefaultFigure());
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
