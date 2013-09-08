package core;

import java.util.Random;

public class AiPlayer extends Player {

    private static Random random;

    public AiPlayer(Field playGround, PlayerFigure playerFigure) {

        super(PlayerType.AI, playGround, playerFigure);
        if (random == null) {

            random = new Random();
        }
    }

    public AiPlayer(PlayerFigure playerFigure) {

        this(null, playerFigure);
    }

    @Override
    public boolean makeMove(int x, int y) {

        if (Player.playGround == null) {
            return false;
        }

        Cell cells[] = Player.playGround.getFreeCellNumbers();

        int cellNum = 0;
        while (cellNum < cells.length) {

            if (cells[cellNum++] == null) {

                break;
            }
        }
        cellNum = AiPlayer.random.nextInt(cellNum - 1);

        return super.makeMove(cells[cellNum].getX(), cells[cellNum].getY());
    }
}
