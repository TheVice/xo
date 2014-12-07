package core;

public class Player {

	public static enum Type {
		HUMAN, AI
	}

	public static char[] getPlayersFigures(Player players[]) {

		char figures[] = new char[players.length];

		int i = 0;
		for (Player player : players) {

			if (player != null) {

				figures[i++] = player.getFigure();
			}
		}

		return figures;
	}

	public static int getNextPlayer(char figure, Player players[]) {

		int i = 0;

		for (Player player : players) {

			if (player != null && figure == player.getFigure()) {

				break;
			}

			i++;
		}

		return (i + 1 >= players.length) ? 0 : i + 1;
	}

	public static boolean isFigurePresent(char figures[], char figure) {

		for (char presentFigure : figures) {

			if (figure == presentFigure) {

				return true;
			}
		}

		return false;
	}

	private final Type type;
	private char figure;

	protected Player(char figure, Type type) {

		this.figure = figure;
		this.type = type;
	}

	public Type getType() {

		return type;
	}

	public char getFigure() {

		return figure;
	}

	public Cell makeMove(Field playGround, int x, int y) {

		return playGround.setCell(x, y, getFigure());
	}

	@Override
	public String toString() {

		return "" + getFigure();
	}

}
