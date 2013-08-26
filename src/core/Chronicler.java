package core;

import java.util.Vector;

public class Chronicler {

    private int cellCount;
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

        if (cells.size() == cellCount || cell == null || findCellWithPosition(cell)) {

            return false;
        }
        return cells.add(cell);
    }

    public Cell undoLastWalk() {

        Cell cell = null;
        if (cells.size() > 0) {

            int index = cells.size() - 1;
            cell = cells.elementAt(index);
            cells.remove(index);
        }
        return cell;
    }

    @Override
    public String toString() {

        String str = "";
        int stepNum = 1;
        for (Cell cell : cells) {

            if (cell.getFigure() == Cell.getDefFigureValue()) {

                break;
            }
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
