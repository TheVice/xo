package core;

import java.util.Scanner;

public class Referee {

    private static Field field;
    private static Chronicler chronicler;
    private static Player winner;

    private static void prepareField4Game(int width, int height, int styleNumber) {

        if (width == 0 || height == 0) {

            System.out.println("What the size of field do you want (width x height)?");

            width = getIntFromInput();
            height = getIntFromInput();
        }
        System.out.println("Field size: " + width + " x " + height);

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

        field = new Field(width, height, ' ', Field.int2style(styleNumber));

        field.setCell(1, 1, 'S');
        System.out.println("Start position marked as S\n" +
                "From this point (that is 1 1) to width and height" +
                " (that is " + width + " " + height + ")");
        System.out.println("The complex field is\n---------\n" + field + "---------");
        field.setCell(1, 1, Cell.getDefFigureValue());
    }

    private static void letsPlayerMakeADesign(Player player) {

        System.out.print("Player of " + player + " please make your move (position x, y):");

        int x = getIntFromInput();
        int y = getIntFromInput();

        if (!player.makeMove(x, y)) {

            if (field.isValidCellNumber(x, y)) {

                System.out.println("Probably position busy by non default figure.");
            } else {

                System.out.println("Position is out side of the field.");
            }

            letsPlayerMakeADesign(player);
        } else {

            System.out.println("\n---------\n" + field + "---------");
        }

        Cell cell = new Cell(x, y, Cell.getDefFigureValue());
        cell.makeMove(player.getFigure());
        chronicler.addWalk(cell);
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
            letsPlayerMakeADesign(players[playerNumber]);

            if (field.isFigureFillDiagonal(players[playerNumber].getFigure()) ||
                    field.isFigureFillLine(players[playerNumber].getFigure())) {

                winner = players[playerNumber];
                break;
            }

            char figure = askForUndo(players[playerNumber].getFigure());

            if (figure != Cell.getDefFigureValue() && figure == players[playerNumber].getFigure()) {

                playerNumber++;
            }
            else if(figure == Cell.getDefFigureValue())
            {
                playerNumber = 0;
            }

        } while (!field.isFull());
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
        System.out.println(chronicler);
    }

    private static int getIntFromInput() {

        Scanner scanner = new Scanner(System.in);

        int iValue = 0;

        if (scanner.hasNextInt()) {

            iValue = scanner.nextInt();
        }
        else {

            System.out.print("\nPlease enter integer value ");
            iValue = getIntFromInput();
        }

        return iValue;
    }

    private static char askForUndo(char current) {

        System.out.print("If you want you can undo to some step in this game.\n" +
                "Please type 'step' word ");
        Scanner scanner = new Scanner(System.in);

        String step;
        if (scanner.hasNextLine()) {

            step = scanner.nextLine();
            if(!step.equals("step")) {
                return current;
            }

            System.out.println("There few next steps made at this point.");
            System.out.println(chronicler);
            System.out.print("Which one do you what to revert?\n" +
                    "Type step num (0 (clean field) - " +
                    chronicler.getStepCount() + ") ");

            int stepNum = getIntFromInput();
            return chronicler.revertTo(stepNum, field);
        }

        return current;
    }
}
