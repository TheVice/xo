import org.junit.Test;
import org.junit.Assert;

import core.Cell;

public class CellTest extends Assert {

	@Test
	public void cellCreate() {

		Cell.setDefaultFigure(' ');

		Cell cell = new Cell(1, 2);
		assertEquals(1, cell.getX());
		assertEquals(2, cell.getY());
		assertTrue(cell.setFigure('X'));
		assertTrue(cell.setFigure(Cell.getDefaultFigure()));
		assertTrue(cell.setFigure('O'));
		assertFalse(cell.setFigure('X'));
		assertTrue(cell.setFigure(Cell.getDefaultFigure()));

		cell = new Cell(4, 6);
		assertEquals(4, cell.getX());
		assertEquals(6, cell.getY());
		assertEquals("" + Cell.getDefaultFigure(), cell.toString());
		assertTrue(cell.setFigure('O'));
		assertEquals("O", cell.toString());
		assertTrue(cell.setFigure(Cell.getDefaultFigure()));
		assertTrue(cell.setFigure('X'));
		assertEquals("X", cell.toString());
	}

}
