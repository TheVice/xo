package core;

import java.util.Random;

public class AiPlayer extends Player {

	private static Random random = new Random();

	public AiPlayer(char figure) {

		super(figure, Type.AI);
	}

	public Cell makeDesign(Field playGround) {

		if (playGround == null) {

			return null;
		}

		Cell cells[] = playGround.getFreeCells();

		int cellNum = 0;
		while (cellNum < cells.length && cells[cellNum] != null) {

			cellNum++;
		}

		if (cellNum == 0) {

			return null;
		} else if (cellNum == 1) {

			cellNum = 0;
		} else {

			cellNum = AiPlayer.random.nextInt(cellNum);
		}

		return (cells[cellNum].setFigure(getFigure()) ? cells[cellNum] : null);
	}

}
