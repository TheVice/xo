package core;

public class Field {

    public static enum StartCellStyle {TOP_LEFT, TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT}

    private static final int MIN_WIDTH = 2;
    private static final int MIN_HEIGHT = 2;

    private static final int MAX_WIDTH = 64;
    private static final int MAX_HEIGHT = 64;

    private int width;
    private int height;
    private Cell cells[];

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

    public static char getDefFigureValue() {

        return Cell.getDefFigureValue();
    }

    public Field(int width, int height, char defFigureValue, StartCellStyle startCellStyle) {

        this.width = validateValue(width, MIN_WIDTH, MAX_WIDTH);
        this.height = validateValue(height, MIN_HEIGHT, MAX_HEIGHT);

        setupCells(defFigureValue, startCellStyle);
    }

    public int getWidthCount() {

        return width;
    }

    public int getHeightCount() {

        return height;
    }

    public boolean setCell(int x, int y, char figureType) {

        Cell cell = findCell(x, y);
        if (cell != null) {

            return cell.makeMove(figureType);
        }
        return false;
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

    private void setupCells(char defFigureValue, StartCellStyle startCellStyle) {

        cells = new Cell[width * height];

        switch (startCellStyle) {
            case TOP_LEFT:
                for (int i = 0; i < width; i++) {

                    for (int j = 0; j < height; j++) {

                        createCellAt(i, j, new Cell(i + 1, j + 1, defFigureValue));
                    }
                }
                break;
            case TOP_RIGHT:
                for (int i = 0; i < width; i++) {

                    for (int j = 0; j < height; j++) {

                        createCellAt(i, j, new Cell(i + 1, height - j, defFigureValue));
                    }
                }
                break;
            case BOTTOM_RIGHT:
                for (int i = 0; i < width; i++) {

                    for (int j = 0; j < height; j++) {

                        createCellAt(i, j, new Cell(width - i, height - j, defFigureValue));
                    }
                }
                break;
            case BOTTOM_LEFT:
                for (int i = 0; i < width; i++) {

                    for (int j = 0; j < height; j++) {

                        createCellAt(i, j, new Cell(width - i, j + 1, defFigureValue));
                    }
                }
                break;
        }
    }

    private Cell findCell(int x, int y) {

        if (isValidCellNumber(x, y)) {

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

        return 0 < x && x <= width && 0 < y && y <= height;
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