package core;

public class Field {

	public static enum StartCellStyle {
		TOP_LEFT, TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT
	}

	private static final int MIN_WIDTH = 2;
	private static final int MIN_HEIGHT = 2;

	private static final int MAX_WIDTH = 64;
	private static final int MAX_HEIGHT = 64;

	public static int getMinWidth() {

		return MIN_WIDTH;
	}

	public static int getMinHeight() {

		return MIN_HEIGHT;
	}

	public static int getMaxWidth() {

		return MAX_WIDTH;
	}

	public static int getMaxHeight() {

		return MAX_HEIGHT;
	}

	private final int width;
	private final int height;
	private Cell cells[];
	private Cell freeCells[];

	public Field(int width, int height, StartCellStyle startCellStyle) {

		this.width = validateValue(width, MIN_WIDTH, MAX_WIDTH);
		this.height = validateValue(height, MIN_HEIGHT, MAX_HEIGHT);

		cells = new Cell[this.width * this.height];
		freeCells = new Cell[this.width * this.height];
		setupCells(startCellStyle);
	}

	public int getWidthCount() {

		return width;
	}

	public int getHeightCount() {

		return height;
	}

	public Cell setCell(int x, int y, char figure) {

		Cell cell = findCell(x, y);
		if (cell != null) {

			if (!cell.setFigure(figure)) {

				cell = null;
			}
		}
		return cell;
	}

	public boolean isFigureTakeAWin(char figure) {

		return (isFigureFillDiagonal(figure) || isFigureFillLine(figure));
	}

	public boolean isFull() {

		for (Cell cell : cells) {

			if (cell.getFigure() == Cell.getDefaultFigure()) {

				return false;
			}
		}
		return true;
	}

	public boolean isValidCellNumber(int x, int y) {

		return 0 < x && x <= width && 0 < y && y <= height;
	}

	public Cell[] getFreeCells() {

		int cellNum = 0;
		for (Cell cell : cells) {

			if (cell.getFigure() == Cell.getDefaultFigure()) {

				freeCells[cellNum++] = cell;
			}
		}

		for (; cellNum < freeCells.length; cellNum++) {

			freeCells[cellNum] = null;
		}

		return freeCells;
	}

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder(256);
		for (int j = 0; j < height; j++) {

			for (int i = 0; i < width; i++) {

				str.append("[" + getCellAt(i, j) + "]");
			}
			str.append(System.getProperty("line.separator"));
		}
		return str.toString();
	}

	private boolean isFigureFillDiagonal(char figure) {

		if (height != width) {

			return false;
		}

		final int count = height;
		boolean match = false;

		if (getCellAt(0, 0).getFigure() == figure) {

			match = true;
			for (int i = 1; i < count; i++) {

				if (getCellAt(i, i).getFigure() != figure) {

					match = false;
					break;
				}
			}
			if (match) {

				return true;
			}
		}

		if (getCellAt(count - 1, 0).getFigure() == figure) {

			match = true;
			for (int i = count - 2; i >= 0; i--) {

				if (getCellAt(i, count - i - 1).getFigure() != figure) {

					match = false;
					break;
				}
			}
		}

		return match;
	}

	private boolean isFigureFillLine(char figure) {

		boolean match = false;

		for (int j = 0; j < height; j++) {

			match = true;
			for (int i = 0; i < width; i++) {

				if (getCellAt(i, j).getFigure() != figure) {

					match = false;
					break;
				}
			}
			if (match) {

				return true;
			}
		}

		for (int i = 0; i < width; i++) {

			match = true;
			for (int j = 0; j < height; j++) {

				if (getCellAt(i, j).getFigure() != figure) {

					match = false;
					break;
				}
			}
			if (match) {

				break;
			}
		}

		return match;
	}

	private void setupCells(StartCellStyle startCellStyle) {

		for (int j = 0; j < height; j++) {

			for (int i = 0; i < width; i++) {

				placeCellAt(i, j,
						new Cell(getValue4Style(i, startCellStyle, true),
								getValue4Style(j, startCellStyle, false)));
			}
		}
	}

	private int getValue4Style(int value, StartCellStyle startCellStyle,
			boolean isWidth) {

		int retValue = value;

		switch (startCellStyle) {

		case TOP_LEFT:
			retValue += 1;
			break;
		case TOP_RIGHT:
			if (isWidth) {

				retValue = width - retValue;
			} else {

				retValue += 1;
			}
			break;
		case BOTTOM_RIGHT:
			if (isWidth) {

				retValue = width - retValue;
			} else {

				retValue = height - retValue;
			}
			break;
		case BOTTOM_LEFT:
			if (isWidth) {

				retValue += 1;
			} else {

				retValue = height - retValue;
			}
		}

		return retValue;
	}

	private Cell findCell(int x, int y) {

		if (isValidCellNumber(x, y)) {

			for (Cell cell : cells) {

				if (cell.getX() == x && cell.getY() == y) {

					return cell;
				}
			}
		}
		return null;
	}

	private Cell getCellAt(int x, int y) {

		return cells[twoDimension2OneIndex(x, y)];
	}

	private void placeCellAt(int x, int y, Cell cell) {

		cells[twoDimension2OneIndex(x, y)] = cell;
	}

	private int twoDimension2OneIndex(int x, int y) {

		return x * height + y;
	}

	private int validateValue(int value, int min, int max) {

		if (value < min) {

			return min;

		} else if (value > max) {

			return max;
		}

		return value;
	}

}
