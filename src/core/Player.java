package core;

public class Player {

	public static enum Type {
		HUMAN, AI
	}

	protected static Field playGround;

	private final Type type;
	private char figure = ' ';

	public static void setPlayGround(Field playGround) {

		if (Player.playGround == null) {

			Player.playGround = playGround;
		}
	}

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

	public Cell makeDesign() {

		return null;
	}

	public Cell makeDesign(int x, int y) {

		return null;
	}

	@Override
	public String toString() {

		return "" + figure;
	}

}
