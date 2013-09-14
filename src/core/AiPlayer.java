package core;

import java.util.Random;

public class AiPlayer extends Player {

    private static Random random;

    public AiPlayer(char figure, Field playGround) {

        super(figure, playGround, Type.AI);
        if (random == null) {

            random = new Random();
        }
    }

    public AiPlayer(char figure) {

        this(figure, null);
    }

    @Override
    public Cell makeDesign() {

        if (Player.playGround == null) {

            return null;
        }

        Cell cells[] = Player.playGround.getFreeCells();

        if (cells.length == 0) {

            return null;
        } else if (cells.length == 1) {

            return (cells[0].setFigure(getFigure()) ? cells[0] : null);
        }

        int cellNum = 0;
        while (cellNum < cells.length) {

            if (cells[cellNum++] == null) {

                break;
            }
        }

        cellNum = AiPlayer.random.nextInt(cellNum - 1);

        return (cells[cellNum].setFigure(getFigure()) ? cells[cellNum] : null);
    }
}
