import org.junit.Test;
import org.junit.Assert;

import core.*;

public class PlayerTest extends Assert {

    private static final char figures[] = {Cell.getDefaultFigure(), 'X', 'O'};

    @Test
    public void playerCreate() {

        for (char pf : figures) {

            Player player = new HumanPlayer(pf);
            assertEquals(Player.Type.HUMAN, player.getType());
            assertEquals(pf, player.getFigure());
            assertEquals("" + pf, player.toString());

            player = new HumanPlayer(pf);
            assertEquals(Player.Type.HUMAN, player.getType());
            assertEquals(pf, player.getFigure());
            assertEquals("" + pf, player.toString());
        }

        Field field = new Field(4, 5, Field.StartCellStyle.BOTTOM_LEFT);

        int i = 1;
        int j = 1;

        for (char pf : figures) {

            Player player = new HumanPlayer(pf);
            assertEquals(Player.Type.HUMAN, player.getType());
            assertEquals(pf, player.getFigure());
            assertNotNull(player.makeMove(field, i, j++));
        }

        for (char pf : figures) {

            Player player = new HumanPlayer(pf);
            assertEquals(Player.Type.HUMAN, player.getType());
            assertEquals(pf, player.getFigure());
            assertEquals("" + pf, player.toString());
            assertNotNull(player.makeMove(field, i++, j));
        }

    }

    @Test
    public void aiPlayerCreate() {

        for (char pf : figures) {

            Player player = new AiPlayer(pf);
            assertEquals(Player.Type.AI, player.getType());
            assertEquals(pf, player.getFigure());
            assertEquals("" + pf, player.toString());

            player = new AiPlayer(pf);
            assertEquals(Player.Type.AI, player.getType());
            assertEquals(pf, player.getFigure());
            assertEquals("" + pf, player.toString());
        }

        Field field = new Field(4, 5, Field.StartCellStyle.BOTTOM_LEFT);

        for (char pf : figures) {

            Player player = new AiPlayer(pf);
            assertEquals(Player.Type.AI, player.getType());
            assertEquals(pf, player.getFigure());
            assertEquals("" + pf, player.toString());
            if (field != null) {

                assertNotNull(((AiPlayer) player).makeDesign(field
                        .getFreeCells()));
            }
        }

        for (char pf : figures) {

            Player player = new AiPlayer(pf);
            assertEquals(Player.Type.AI, player.getType());
            assertEquals(pf, player.getFigure());
            assertEquals("" + pf, player.toString());
            if (field != null) {

                assertNotNull(((AiPlayer) player).makeDesign(field
                        .getFreeCells()));
            }
        }

        field = new Field(2, 2, Field.StartCellStyle.BOTTOM_LEFT);
        field.setCell(1, 1, figures[1]);
        field.setCell(1, 2, figures[1]);
        field.setCell(2, 1, figures[1]);
        field.setCell(2, 2, figures[1]);

        for (char pf : figures) {

            Player player = new AiPlayer(pf);
            assertEquals(null,
                    ((AiPlayer) player).makeDesign(field.getFreeCells()));
        }

        field = new Field(2, 2, Field.StartCellStyle.BOTTOM_LEFT);
        field.setCell(1, 1, figures[1]);
        field.setCell(1, 2, figures[1]);
        field.setCell(2, 1, figures[1]);

        for (char pf : figures) {

            Player player = new AiPlayer(pf);
            if (field != null) {

                assertNotNull(((AiPlayer) player).makeDesign(field
                        .getFreeCells()));
                field.setCell(2, 2, figures[0]);
            }
        }
    }

    @Test
    public void testGetPlayersFigures() {

        Player players[] = new Player[4];
        players[0] = new HumanPlayer('A');
        players[1] = new HumanPlayer('B');
        players[2] = new HumanPlayer('C');

        assertArrayEquals(new char[]{'A', 'B', 'C', '\0'},
                Player.getPlayersFigures(players));
    }

    @Test
    public void testGetNextPlayer() {

        Player players[] = new Player[3];
        players[0] = new HumanPlayer('A');
        players[1] = new HumanPlayer('B');
        players[2] = new HumanPlayer('C');

        assertEquals(0, Player.getNextPlayer('C', players));
        assertEquals(1, Player.getNextPlayer('A', players));
        assertEquals(2, Player.getNextPlayer('B', players));
        assertEquals(0, Player.getNextPlayer('\0', players));

        players = new Player[4];
        players[0] = new HumanPlayer('A');
        players[1] = new HumanPlayer('B');
        players[2] = new HumanPlayer('C');

        assertEquals(3, Player.getNextPlayer('C', players));
        assertEquals(1, Player.getNextPlayer('A', players));
        assertEquals(2, Player.getNextPlayer('B', players));
        assertEquals(0, Player.getNextPlayer('\0', players));
    }

    @Test
    public void testIsFigurePresent() {

        char playerFigures[] = new char[]{'A', 'B', 'C'};
        assertFalse(Player.isFigurePresent(playerFigures, '\0'));
        assertTrue(Player.isFigurePresent(playerFigures, 'A'));
        assertTrue(Player.isFigurePresent(playerFigures, 'B'));
        assertTrue(Player.isFigurePresent(playerFigures, 'C'));
    }

}
