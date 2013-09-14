package core;

import java.util.Vector;

public class Chronicler {

    private final int cellCount;
    private Vector<Cell> cells;

    public Chronicler(int cellCount) {

        if (cellCount < Field.getMinWidth() * Field.getMinHeight()) {

            cellCount = Field.getMinWidth() * Field.getMinHeight();
        } else if (cellCount > Field.getMaxWidth() * Field.getMaxHeight()) {

            cellCount = Field.getMaxWidth() * Field.getMaxHeight();
        }

        this.cellCount = cellCount;
        cells = new Vector<Cell>(this.cellCount);
    }

    public boolean addWalk(Cell cell) {

        if (cell == null || cells.size() == cellCount ||
                cell.getFigure() == Cell.getDefFigureValue() || findCellWithPosition(cell)) {

            return false;
        }
        return cells.add(cell);
    }

    public void undoLastWalk(Field field) {

        int index = cells.size();
        revertTo(index - 1, field);
    }

    public int getStepCount() {

        return cells.size();
    }

    public char revertTo(int stepNum, Field field) {

        if (stepNum == 0) {

            if (field != null) {

                for (Cell cell : cells) {

                    field.setCell(cell.getX(), cell.getY(), Cell.getDefFigureValue());
                }
            }
            cells.removeAllElements();
        }

        int index = cells.size();
        if (index == 0) {

            return Cell.getDefFigureValue();
        }

        if (stepNum >= index || stepNum < 0) {

            return cells.elementAt(index - 1).getFigure();
        }


        int count = index - stepNum;
        for (int i = 1; i <= count; i++) {

            if (field != null) {

                Cell cell = cells.elementAt(index - i);
                field.setCell(cell.getX(), cell.getY(), Cell.getDefFigureValue());
            }

            cells.removeElementAt(index - i);
        }

        return cells.elementAt(stepNum - 1).getFigure();
    }

    @Override
    public String toString() {

        String str = "";
        int stepNum = 1;
        for (Cell cell : cells) {

            str += "Step - " + (stepNum++) + ". Figure " + cell.getFigure() + " on x = " +
                    cell.getX() + " y = " + cell.getY() + "\n";
        }
        return str;
    }

    private boolean findCellWithPosition(Cell cellToFind) {

        for (Cell cell : cells) {

            if (cell.getX() == cellToFind.getX() && cell.getY() == cellToFind.getY()) {

                return true;
            }
        }
        return false;
    }
}
