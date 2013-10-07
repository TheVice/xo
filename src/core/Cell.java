package core;

public class Cell {

	private static char defaultFigure = ' ';

	private final int x;
	private final int y;
	private char figure;

	public static void setDefaultFigure(char defaultFigure) {

		Cell.defaultFigure = defaultFigure;
	}

	public Cell(int x, int y) {

		this.x = x;
		this.y = y;
		this.figure = Cell.defaultFigure;
	}

	public static char getDefaultFigure() {

		return Cell.defaultFigure;
	}

	public int getX() {

		return x;
	}

	public int getY() {

		return y;
	}

	public char getFigure() {

		return figure;
	}

	public boolean setFigure(char figure) {

		if (figure == Cell.defaultFigure || this.figure == Cell.defaultFigure) {

			this.figure = figure;
			return true;
		}

		return false;
	}

	@Override
	public String toString() {

		return "" + figure;
	}
}
