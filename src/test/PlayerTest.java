package test;

import org.junit.Test;
import org.junit.Assert;

import core.AiPlayer;
import core.Player;
import core.Cell;
import core.Field;

public class PlayerTest extends Assert {

	@Test
	public void playerCreate() {

		Player player = new Player(null);
		assertEquals(Player.PlayerType.HUMAN, player.getPlayerType());
		assertEquals(null, player.getPlayerFigure());
		assertEquals("" + Cell.getDefFigureValue(), player.toString());
		assertFalse(player.makeMove(1, 1));

		player = new Player(null, null);
		assertEquals(Player.PlayerType.HUMAN, player.getPlayerType());
		assertEquals(null, player.getPlayerFigure());
		assertEquals("" + Cell.getDefFigureValue(), player.toString());
		assertFalse(player.makeMove(1, 1));

		for (Player.PlayerFigure pf : Player.PlayerFigure.values()) {

			player = new Player(null, pf);
			assertEquals(Player.PlayerType.HUMAN, player.getPlayerType());
			assertEquals(pf, player.getPlayerFigure());

			assertFalse(player.makeMove(1, 1));
		}

		Field field = new Field(3, 4, Cell.getDefFigureValue(),
				Field.int2style(4));

		player = new Player(field, null);
		assertEquals(Player.PlayerType.HUMAN, player.getPlayerType());
		assertEquals(null, player.getPlayerFigure());
		assertEquals("" + Cell.getDefFigureValue(), player.toString());
		assertFalse(player.makeMove(1, 1));

		int i = 1;
		int j = 1;
		for (Player.PlayerFigure pf : Player.PlayerFigure.values()) {

			player = new Player(pf);
			assertEquals(Player.PlayerType.HUMAN, player.getPlayerType());
			assertEquals(pf, player.getPlayerFigure());
			assertTrue(player.makeMove(i++, j));
		}

		for (Player.PlayerFigure pf : Player.PlayerFigure.values()) {

			player = new Player(field, pf);
			assertEquals(Player.PlayerType.HUMAN, player.getPlayerType());
			assertEquals(pf, player.getPlayerFigure());
			assertTrue(player.makeMove(i, j++));
		}

	}

	@Test
	public void aiPlayerCreate() {

		Player player = new AiPlayer(null);
		assertEquals(Player.PlayerType.AI, player.getPlayerType());
		assertEquals(null, player.getPlayerFigure());
		assertEquals("" + Cell.getDefFigureValue(), player.toString());
		assertFalse(player.makeMove(1, 1));

		player = new AiPlayer(null, null);
		assertEquals(Player.PlayerType.AI, player.getPlayerType());
		assertEquals(null, player.getPlayerFigure());
		assertEquals("" + Cell.getDefFigureValue(), player.toString());
		assertFalse(player.makeMove(1, 1));

		Field field = new Field(3, 4, Cell.getDefFigureValue(),
				Field.int2style(4));

		player = new AiPlayer(field, null);
		assertEquals(Player.PlayerType.AI, player.getPlayerType());
		assertEquals(null, player.getPlayerFigure());
		assertEquals("" + Cell.getDefFigureValue(), player.toString());
		assertFalse(player.makeMove(1, 1));

		int i = 1;
		int j = 1;
		for (Player.PlayerFigure pf : Player.PlayerFigure.values()) {

			player = new AiPlayer(pf);
			assertEquals(Player.PlayerType.AI, player.getPlayerType());
			assertEquals(pf, player.getPlayerFigure());
			assertTrue(player.makeMove(i++, j));
		}

		for (Player.PlayerFigure pf : Player.PlayerFigure.values()) {

			player = new AiPlayer(field, pf);
			assertEquals(Player.PlayerType.AI, player.getPlayerType());
			assertEquals(pf, player.getPlayerFigure());
			assertTrue(player.makeMove(i, j++));
		}
	}

}
