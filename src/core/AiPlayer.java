package core;

import java.util.Random;

public class AiPlayer extends Player {

	private static Random random;

	public AiPlayer(char figure) {

		super(figure, Type.AI);
		if (random == null) {

			random = new Random();
		}
	}

	@Override
	public Cell makeDesign(Field playGround) {

		if (playGround == null) {

			return null;
		}

		Cell cells[] = playGround.getFreeCells();

		int cellNum = 0;

		if (cells.length == 0) {

			return null;
		} else if (cells.length > 0) {

			cellNum = 1;
			do {

				if (cells[cellNum++] == null) {

					break;
				}
			} while (cellNum < cells.length);

			cellNum = AiPlayer.random.nextInt(cellNum - 1);
		}

		return (cells[cellNum].setFigure(getFigure()) ? cells[cellNum] : null);
	}

}
