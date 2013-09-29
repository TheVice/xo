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
	public Cell makeDesign() {

		if (Player.playGround == null) {

			return null;
		}

		Cell cells[] = Player.playGround.getFreeCells();

		if (cells.length == 0) {

			return null;
		} else if (cells.length == 1) {

			return makeMove(cells, 0);
		}

		int cellNum = 0;
		while (cellNum < cells.length) {

			if (cells[cellNum++] == null) {

				break;
			}
		}

		cellNum = AiPlayer.random.nextInt(cellNum - 1);

		return makeMove(cells, cellNum);
	}

	private Cell makeMove(Cell cells[], int cellNum) {

		return (cells[cellNum].setFigure(getFigure()) ? cells[cellNum] : null);
	}

}
