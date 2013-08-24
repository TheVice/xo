package core;

import java.util.Scanner;

public class Referee {

    private static Scanner scanner = new Scanner(System.in);
    private static Field field;

    private static void prepareField4Game(int width, int height, int styleNumber) {

        if (width == 0 || height == 0) {

            System.out.println("What the size of field do you want (width x height)?");

            width = scanner.nextInt();
            height = scanner.nextInt();
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
            styleNumber = scanner.nextInt();
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

        int x = scanner.nextInt();
        int y = scanner.nextInt();

        if (!player.makeMove(x, y)) {

            if (field.isValidCellNumber(x, y)) {

                System.out.println("Probably position busy by figure of other player.");
            } else {

                System.out.println("Position is out side of the field.");
            }

            letsPlayerMakeADesign(player);
        } else {

            System.out.println("\n---------\n" + field + "---------");
        }
    }

    private static void gameStart() {

        final int playerCount = 2;
        Player players[] = new Player[playerCount];
        players[0] = new Player(field, Player.PlayerFigure.X);
        players[1] = new Player(Player.PlayerFigure.O);

        int playerNumber = 0;
        do {
            if (playerNumber >= playerCount) {

                playerNumber = 0;
            }
            letsPlayerMakeADesign(players[playerNumber]);
            playerNumber++;
        } while (!field.isFull());
    }

    public static void gameLoop() {

        System.out.println("Welcome in Xs and Os (a.k.a. Tic-tac-toe)!");
        prepareField4Game(3, 3, 4);
        gameStart();
    }
}