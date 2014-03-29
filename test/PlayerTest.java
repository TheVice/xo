
import org.junit.Test;
import org.junit.Assert;

import core.*;

public class PlayerTest extends Assert {

	private static char figures[] = { Cell.getDefaultFigure(), 'X', 'O' };

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

		Field fields[] = new Field[2];
		fields[0] = new Field(4, 5, Field.StartCellStyle.BOTTOM_LEFT);

		for (Field field : fields) {

			for (char pf : figures) {

				Player player = new AiPlayer(pf);
				assertEquals(Player.Type.AI, player.getType());
				assertEquals(pf, player.getFigure());
				assertEquals("" + pf, player.toString());
				if (field != null) {

					assertNotEquals(null, ((AiPlayer) player).makeDesign(field));
				} else {

					assertEquals(null, ((AiPlayer) player).makeDesign(field));
				}
			}

			for (char pf : figures) {

				Player player = new AiPlayer(pf);
				assertEquals(Player.Type.AI, player.getType());
				assertEquals(pf, player.getFigure());
				assertEquals("" + pf, player.toString());
				if (field != null) {

					assertNotEquals(null, ((AiPlayer) player).makeDesign(field));
				} else {

					assertEquals(null, ((AiPlayer) player).makeDesign(field));
				}
			}
		}

		fields[0] = new Field(2, 2, Field.StartCellStyle.BOTTOM_LEFT);
		fields[0].setCell(1, 1, figures[1]);
		fields[0].setCell(1, 2, figures[1]);
		fields[0].setCell(2, 1, figures[1]);
		fields[0].setCell(2, 2, figures[1]);

		for (Field field : fields) {

			for (char pf : figures) {

				Player player = new AiPlayer(pf);
				assertEquals(null, ((AiPlayer) player).makeDesign(field));
			}
		}
		
		fields[0] = new Field(2, 2, Field.StartCellStyle.BOTTOM_LEFT);
		fields[0].setCell(1, 1, figures[1]);
		fields[0].setCell(1, 2, figures[1]);
		fields[0].setCell(2, 1, figures[1]);

		for (Field field : fields) {

			for (char pf : figures) {

				Player player = new AiPlayer(pf);
				if (field != null) {

					assertNotEquals(null, ((AiPlayer) player).makeDesign(field));
					fields[0].setCell(2, 2, figures[0]);
				} else {

					assertEquals(null, ((AiPlayer) player).makeDesign(field));
				}
			}
		}
	}

}
