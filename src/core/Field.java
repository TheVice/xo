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

    public static StartCellStyle int2style(int styleNumber) {

        int count = 1;
        for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

            if (count++ == styleNumber) {

                return scs;
            }
        }

        return StartCellStyle.BOTTOM_LEFT;
    }

    public Field(int width, int height, char defFigureValue, StartCellStyle startCellStyle) {

        this.width = validateValue(width, MIN_WIDTH, MAX_WIDTH);
        this.height = validateValue(height, MIN_HEIGHT, MAX_HEIGHT);

        cells = new Cell[this.width * this.height];
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

    public boolean isFigureFillDiagonal(char figure) {

        if (height != width) {

            return false;
        }

        final int count = height;
        boolean match = false;

        if(getCellAt(0, 0).getFigure() == figure) {

            match = true;
            for(int i = 1; i < count; i++) {

                if(getCellAt(i, i).getFigure() != figure) {

                    match = false;
                    break;
                }
            }
        }

        if(match) {

            return true;
        }
        else if (getCellAt(count - 1, 0).getFigure() == figure) {

            match = true;
            for(int i = count - 2; i >= 0; i--) {

                if(getCellAt(i, count - i - 1).getFigure() != figure) {

                    match = false;
                    break;
                }
            }
        }

        return match;
    }

    public boolean isFull() {

        for (int j = 0; j < height; j++) {

            for (int i = 0; i < width; i++) {

                if (getCellAt(i, j).getFigure() == Cell.getDefFigureValue()) {

                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValidCellNumber(int x, int y) {

        return 0 < x && x <= width && 0 < y && y <= height;
    }

    @Override
    public String toString() {

        String str = "";
        for (int j = 0; j < height; j++) {

            for (int i = 0; i < width; i++) {

                str += "[" + getCellAt(i, j) + "]";
            }
            str += "\n";
        }
        return str;
    }

    private void setupCells(char defFigureValue, StartCellStyle startCellStyle) {

        for (int j = 0; j < height; j++) {

            for (int i = 0; i < width; i++) {

                createCellAt(i, j, new Cell(getValue4Style(i, startCellStyle, true),
                        getValue4Style(j, startCellStyle, false), defFigureValue));
            }
        }
    }

    private int getValue4Style(int value, StartCellStyle startCellStyle, boolean isWidth) {

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

            for (int j = 0; j < height; j++) {

                for (int i = 0; i < width; i++) {

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

    private int validateValue(int value, int min, int max) {

        if (value < min) {

            return min;

        } else if (value > max) {

            return max;
        }

        return value;
    }
}