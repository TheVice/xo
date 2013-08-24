package core;

public class Player {

    public static Field playGround;

    public static enum PlayerFigure {X, O}

    private static PlayerFigure figure;

    private PlayerFigure playerFigure;

    public Player(Field field, PlayerFigure playerFigure) {

        if (playGround == null && field != null) {

            playGround = field;
        }

        if (Player.figure == null && playerFigure != null) {

            Player.figure = playerFigure;
            this.playerFigure = playerFigure;
        } else if (Player.figure != playerFigure) {

            this.playerFigure = playerFigure;
        }
    }

    public Player(PlayerFigure playerFigure) {

        this(null, playerFigure);
    }

    public boolean makeMove(int x, int y) {

        if (playGround != null && playerFigure != null) {

            return playGround.setCell(x, y, getCharFromFigure(playerFigure));
        }
        return false;
    }

    @Override
    public String toString() {

        return "" + getCharFromFigure(playerFigure);
    }

    private static char getCharFromFigure(PlayerFigure playerFigure) {

        char retVal = '\0';

        switch (playerFigure) {

            case X:
                retVal = 'X';
                break;
            case O:
                retVal = 'O';
                break;
        }

        return retVal;
    }
}
