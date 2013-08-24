package test;

import org.junit.Test;
import org.junit.Assert;

import core.Player;
import core.Field;

public class PlayerTest extends Assert {

    @Test
    public void playerCreate() {

        Player player = new Player(null, null);
        assertFalse(player.makeMove(1, 1));
        player = new Player(null);
        assertFalse(player.makeMove(1, 1));
        player = new Player(new Field(3, 4, Field.getDefFigureValue(), Field.int2style(4)), null);
        assertFalse(player.makeMove(1, 1));
        player = new Player(null, Player.PlayerFigure.X);
        assertEquals("X", player.toString());
        player = new Player(Player.PlayerFigure.O);
        assertEquals("O", player.toString());
        new Player(new Field(3, 4, Field.getDefFigureValue(), Field.int2style(4)), Player.PlayerFigure.X);
        assertTrue(player.makeMove(1, 1));
    }
}
