package test;

import org.junit.Test;
import org.junit.Assert;

import core.*;

public class PlayerTest extends Assert {

    private char figures[] = {Cell.getDefaultFigure(), 'X', 'O'};
    private Player player;
    private Field field;

    @Test
    public void playerCreate() {

        for (char pf : figures) {

            player = new HumanPlayer(pf);
            assertEquals(Player.Type.HUMAN, player.getType());
            assertEquals(pf, player.getFigure());
            assertEquals("" + pf, player.toString());
            assertEquals(null, player.makeDesign(1, 1));

            player = new HumanPlayer(pf, null);
            assertEquals(Player.Type.HUMAN, player.getType());
            assertEquals(pf, player.getFigure());
            assertEquals("" + pf, player.toString());
            assertEquals(null, player.makeDesign(1, 1));
        }

        field = new Field(4, 5, Cell.getDefaultFigure(), Field.int2style(4));

        int i = 1;
        int j = 1;

        for (char pf : figures) {

            player = new HumanPlayer(pf, field);
            assertEquals(Player.Type.HUMAN, player.getType());
            assertEquals(pf, player.getFigure());
            assertNotEquals(null, player.makeDesign(i, j++));
        }

        for (char pf : figures) {

            player = new HumanPlayer(pf);
            assertEquals(Player.Type.HUMAN, player.getType());
            assertEquals(pf, player.getFigure());
            assertEquals("" + pf, player.toString());
            assertNotEquals(null, player.makeDesign(i++, j));
        }

    }

    @Test
    public void aiPlayerCreate() {

        for (char pf : figures) {

            player = new AiPlayer(pf);
            assertEquals(Player.Type.AI, player.getType());
            assertEquals(pf, player.getFigure());
            assertEquals("" + pf, player.toString());
            assertNotEquals(null, player.makeDesign());
        }

        for (char pf : figures) {

            player = new AiPlayer(pf, field);
            assertEquals(Player.Type.AI, player.getType());
            assertEquals(pf, player.getFigure());
            assertEquals("" + pf, player.toString());
            assertNotEquals(null, player.makeDesign());
        }
    }

}
