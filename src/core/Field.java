package core;

public class Field {

    private static final int MIN_WIDTH = 2;
    private static final int MIN_HEIGHT = 2;

    private static final int MAX_WIDTH = 64;
    private static final int MAX_HEIGHT = 64;

    private int width;
    private int height;
    private Cell cells[];

    public Field(int width, int height) {

        this.width = validateValue(width, MIN_WIDTH, MAX_WIDTH);
        this.height = validateValue(height, MIN_HEIGHT, MAX_HEIGHT);
    }

    public int getWidthCount() {

        return width;
    }

    public int getHeightCount() {

        return height;
    }

    public char getDefFigureValue() {

        return Cell.getDefFigureValue();
    }

    public void setupCells(char defFigureValue) {

        cells = new Cell[width * height];

        for (int i = 0; i < width; i++) {

            for (int j = 0; j < height; j++) {

                createCellAt(i, j, new Cell(i, j, defFigureValue));
            }
        }
    }

    public boolean setCell(int x, int y, char figureType) {

        Cell cell = findCell(x, y);
        if (cell != null) {

            return cell.makeMove(figureType);
        }

        return false;
    }

    private Cell findCell(int x, int y) {

        if (cells != null && isValidCellNumber(x, y)) {

            for (int i = 0; i < width; i++) {

                for (int j = 0; j < height; j++) {

                    Cell cell = getCellAt(i, j);

                    if (cell.getX() == x && cell.getY() == y) {

                        return cell;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {

        String str = "";
        for (int i = 0; i < width; i++) {

            for (int j = 0; j < height; j++) {

                str += "[" + getCellAt(i, j) + "]";
            }
            str += "\n";
        }
        return str;
    }

    private Cell getCellAt(int x, int y) {

        return cells[twoDimension2OneIndex(x, y)];
    }

    private void createCellAt(int x, int y, Cell cell) {

        cells[twoDimension2OneIndex(x, y)] = cell;
    }

    private int twoDimension2OneIndex(int x, int y) {

        return x * height + y;
    }

    private boolean isValidCellNumber(int x, int y) {

        return 0 <= x && x < width && 0 <= y && y < height;
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