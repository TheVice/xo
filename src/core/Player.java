package core;

public class Player {

	public static enum Type {
		HUMAN, AI
	}

	private final Type type;
	private char figure = ' ';

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

		if (playGround == null) {

			return null;
		}
		return playGround.setCell(x, y, getFigure());
	}

	@Override
	public String toString() {

		return "" + figure;
	}

}
