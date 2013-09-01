package core;

import java.util.Scanner;

public class Referee {

    private static Field field;
    private static Chronicler chronicler;
    private static Player winner;
    private static int x;
    private static int y;
    private static Boolean exitNow = false;

    private static int determineStartPositionAtField(int styleNumber) {

        String fieldStyles[] = {"Top Left", "Top Right", "Bottom Right", "Bottom Left"};

        if (styleNumber == 0) {

            System.out.print("Choose the position\n" +
                    "(1 - " + fieldStyles[0] + ";" +
                    " 2 - " + fieldStyles[1] + ";" +
                    "\n3 - " + fieldStyles[2] + ";" +
                    "4 - " + fieldStyles[3] + ")\n" +
                    "from what calculate the cells: ");

            styleNumber = getIntFromInput();
        }

        if (styleNumber > 4) {

            styleNumber = 4;
        } else if (styleNumber < 1) {

            styleNumber = 1;
        }

        System.out.println("Field type: " + fieldStyles[styleNumber - 1]);

        return styleNumber;
    }

    private static void prepareField4Game(int width, int height, int styleNumber) {

        if (width == 0 || height == 0) {

            System.out.println("What the size of field do you want (width x height)?");

            width = getIntFromInput();
            height = getIntFromInput();
        }

        styleNumber = determineStartPositionAtField(styleNumber);

        field = new Field(width, height, ' ', Field.int2style(styleNumber));

        System.out.println("Field size: " + field.getWidthCount() + " x " + field.getHeightCount());

        field.setCell(1, 1, 'S');
        System.out.println("Start position marked as S\n" +
                "From this point (that is 1 1) to width and height" +
                " (that is " + field.getWidthCount() + " " + field.getHeightCount() + ")");
        System.out.println("The complex field is\n---------\n" + field + "---------");
        field.setCell(1, 1, Cell.getDefFigureValue());
    }

    private static char letsPlayerMakeADesign(Player player) {

        System.out.println("To undo type 'step'.\n");//To exit from game type 'exit'.");
        System.out.print("Player of " + player + " please make your move (position x, y):");

        x = -1;
        y = -1;

        if(checkForUndo(true)) {

            return makeUndo();
        }
        else {

            if(x == -1) {

                x = getIntFromInput();
            }
        }

        if(checkForUndo(false)) {

            return makeUndo();
        }
        else {

            if(y == -1) {

                y = getIntFromInput();
            }
        }

        if (!player.makeMove(x, y)) {

            System.out.println("x = " + x + " y = " + y);
            if (field.isValidCellNumber(x, y)) {

                System.out.println("Position is busy by non default figure.");
            } else {

                System.out.println("Position is out side of the field.");
            }

            return letsPlayerMakeADesign(player);
        }

        System.out.println("\n---------\n" + field + "---------");

        Cell cell = new Cell(x, y, Cell.getDefFigureValue());
        cell.makeMove(player.getFigure());
        chronicler.addWalk(cell);

        return player.getFigure();
    }

    private static void gameStart() {

        final int playerCount = 2;
        Player players[] = new Player[playerCount];
        players[0] = new Player(field, Player.PlayerFigure.X);
        players[1] = new Player(Player.PlayerFigure.O);
        chronicler = new Chronicler(field.getWidthCount() * field.getHeightCount());

        int playerNumber = 0;
        do {
            if (playerNumber >= playerCount) {

                playerNumber = 0;
            }

            char figure = letsPlayerMakeADesign(players[playerNumber]);

            if (field.isFigureFillDiagonal(players[playerNumber].getFigure()) ||
                    field.isFigureFillLine(players[playerNumber].getFigure())) {

                winner = players[playerNumber];
                break;
            }

            if (figure != Cell.getDefFigureValue() && figure == players[playerNumber].getFigure()) {

                playerNumber++;
            }
            else if(figure == Cell.getDefFigureValue())
            {
                playerNumber = 0;
            }

        } while (!field.isFull() || !exitNow);
    }

    public static void gameLoop() {

        System.out.println("Welcome in Xs and Os (a.k.a. Tic-tac-toe)!");
        prepareField4Game(3, 3, 4);
        gameStart();
        gameOver();
    }

    private static void gameOver() {

        System.out.println("The game is over");
        if (winner != null) {

            System.out.println("The winner is player " + winner);
        } else {

            System.out.println("The friendship is win");
        }
        System.out.println("Game chronics");
        System.out.println(chronicler);
    }

    private static int getIntFromInput() {

        int iValue = 0;
        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNextInt()) {

            iValue = scanner.nextInt();
        }
        else {

            System.out.print("\nPlease enter integer value ");
            iValue = getIntFromInput();
        }

        return iValue;
    }

    private static boolean checkForUndo(boolean isForX) {

        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNextLine()) {

            String step = scanner.nextLine();

            if(!step.equals("step")) {

                try {
                    int i = Integer.valueOf(step);
                    if(isForX) {

                        x = i;
                    }
                    else {

                        y = i;
                    }
                }
                catch (NumberFormatException exc)
                {
                    System.out.println("Please enter integer value ");
                }

                return false;
            }
        }
        return true;
    }

    private static char makeUndo() {

        System.out.println("There few next steps made at this point.");
        System.out.println(chronicler);
        System.out.print("Which one do you what to revert?\n" +
                         "Type step num (0 (clean field)" +
        (chronicler.getStepCount() > 0 ?  "- " + chronicler.getStepCount() : "") + ") ");

        int stepNum = getIntFromInput();

        return chronicler.revertTo(stepNum, field);
    }
}
