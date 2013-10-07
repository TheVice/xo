package test;

import org.junit.Test;
import org.junit.Assert;

import core.*;

public class PlayerTest extends Assert {

	private char figures[] = { Cell.getDefaultFigure(), 'X', 'O' };

	@Test
	public void playerCreate() {

		for (char pf : figures) {

			Player player = new HumanPlayer(pf);
			assertEquals(Player.Type.HUMAN, player.getType());
			assertEquals(pf, player.getFigure());
			assertEquals("" + pf, player.toString());
			assertEquals(null, player.makeMove(null, 1, 1));

			player = new HumanPlayer(pf);
			assertEquals(Player.Type.HUMAN, player.getType());
			assertEquals(pf, player.getFigure());
			assertEquals("" + pf, player.toString());
			assertEquals(null, player.makeMove(null, 1, 1));
		}

		Field field = new Field(4, 5, Field.StartCellStyle.BOTTOM_LEFT);

		int i = 1;
		int j = 1;

		for (char pf : figures) {

			Player player = new HumanPlayer(pf);
			assertEquals(Player.Type.HUMAN, player.getType());
			assertEquals(pf, player.getFigure());
			assertNotEquals(null, player.makeMove(field, i, j++));
		}

		for (char pf : figures) {

			Player player = new HumanPlayer(pf);
			assertEquals(Player.Type.HUMAN, player.getType());
			assertEquals(pf, player.getFigure());
			assertEquals("" + pf, player.toString());
			assertNotEquals(null, player.makeMove(field, i++, j));
		}

	}

	@Test
	public void aiPlayerCreate() {

		for (char pf : figures) {

			Player player = new AiPlayer(pf);
			assertEquals(Player.Type.AI, player.getType());
			assertEquals(pf, player.getFigure());
			assertEquals("" + pf, player.toString());
			assertEquals(null, player.makeMove(null, 1, 1));

			player = new AiPlayer(pf);
			assertEquals(Player.Type.AI, player.getType());
			assertEquals(pf, player.getFigure());
			assertEquals("" + pf, player.toString());
			assertEquals(null, player.makeMove(null, 1, 1));
		}

		Field field = new Field(4, 5, Field.StartCellStyle.BOTTOM_LEFT);

		for (char pf : figures) {

			Player player = new AiPlayer(pf);
			assertEquals(Player.Type.AI, player.getType());
			assertEquals(pf, player.getFigure());
			assertEquals("" + pf, player.toString());
			assertNotEquals(null, player.makeDesign(field));
		}

		for (char pf : figures) {

			Player player = new AiPlayer(pf);
			assertEquals(Player.Type.AI, player.getType());
			assertEquals(pf, player.getFigure());
			assertEquals("" + pf, player.toString());
			assertNotEquals(null, player.makeDesign(field));
		}
	}

}
