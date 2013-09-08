package core;

public class Cell {

    private static boolean defValueSet;
    private static char DEF_FIGURE_VALUE;

    private int x;
    private int y;
    private char figure;

    public Cell(int x, int y, char defFigureValue) {

        if (!defValueSet) {
            DEF_FIGURE_VALUE = defFigureValue;
            defValueSet = true;
        }

        this.x = x;
        this.y = y;
        this.figure = DEF_FIGURE_VALUE;
    }

    public static char getDefFigureValue() {

        return DEF_FIGURE_VALUE;
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

    public boolean setFigure(char figureType) {

        if (figureType == DEF_FIGURE_VALUE || figure == DEF_FIGURE_VALUE) {

            figure = figureType;
            return true;
        }

        return false;
    }

    @Override
    public String toString() {

        return "" + figure;
    }
}
