package core;

import ui.Ui;

public class Referee {

	private static int getIntFromInput(Ui ui) {

		int i = 0;
		try {

			i = ui.onInputInt();
		} catch (Exception exc) {

			ui.onOutputLine("Please enter integer value");
			i = getIntFromInput(ui);
		}

		return i;
	}

	private static int getCommandFromInput(Ui ui, String[] commands) {

		ui.onOutputLine("Please type one of the command(s) below:");

		for (int commandNum = 0; commandNum < commands.length; commandNum++) {

			ui.onOutputLine(commands[commandNum]);
		}

		String command = ui.onInput();

		for (int commandNum = 0; commandNum < commands.length; commandNum++) {

			if (command.equals(commands[commandNum])) {

				return commandNum;
			}
		}

		return getCommandFromInput(ui, commands);
	}

	private static char determineDefaultFigure(Ui ui, char figure) {

		if (figure == '\0') {

			ui.onOutput("Please type the default (neutral) figure: ");
			figure = ui.onInput().charAt(0);
		}

		ui.onOutputLine("Default figure will be '" + figure
				+ "' (with out gaps(') of corse)");

		return figure;
	}

	private static int determineStartPositionAtField(Ui ui, int width,
			int height, char figure, int styleNumber) {

		String fieldStyles[] = { "Top Left", "Top Right", "Bottom Right",
				"Bottom Left" };

		if (styleNumber <= 0) {

			ui.onOutputLine("Choose the position from what calculate the cells.");

			int i = 0;
			for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

				Field field = new Field(width, height, figure, scs);

				ui.onOutputLine((i + 1) + " " + fieldStyles[i++]);
				showField(ui, field);
			}

			styleNumber = getIntFromInput(ui);
		}

		if (styleNumber > 4) {

			styleNumber = 4;
		} else if (styleNumber < 1) {

			styleNumber = 1;
		}

		ui.onOutputLine("Field type: " + fieldStyles[styleNumber - 1]);

		return styleNumber;
	}

	private static void showField(Ui ui, Field field) {

		char figure = Cell.getDefaultFigure();

		ui.onOutputLine("Field size: " + field.getWidthCount() + " x "
				+ field.getHeightCount());
		field.setCell(1, 1, figure != 'S' ? 'S' : '*');
		ui.onOutputLine("Start position marked as "
				+ ((figure != 'S') ? "S" : "*"));
		ui.onOutputLine("From this point (that is 1 1) to width and height"
				+ " (that is " + field.getWidthCount() + " "
				+ field.getHeightCount() + ")");
		ui.onOutputLine("The complex field is\n---------\n" + field
				+ "---------");
		field.setCell(1, 1, figure);
	}

	private static Field prepareField4Game(Ui ui, int width, int height,
			char figure, int styleNumber) {

		if (width <= 0 || height <= 0) {

			ui.onOutputLine("What the size of field do you want (width x height)?");

			ui.onOutput("Width - ");
			width = getIntFromInput(ui);

			ui.onOutput("Height - ");
			height = getIntFromInput(ui);
		}

		figure = determineDefaultFigure(ui, figure);
		styleNumber = determineStartPositionAtField(ui, width, height, figure,
				styleNumber);

		Field field = new Field(width, height, figure,
				Field.int2style(styleNumber));

		showField(ui, field);

		return field;
	}

	private static Player[] setupPlayers(Ui ui, int playerCount,
			char[] playerFigures) {

		Player players[] = null;

		if (playerCount <= 0) {

			ui.onOutputLine("How many player would you like to play?");
			playerCount = getIntFromInput(ui);

			if (playerCount < 0) {

				playerCount = 1;
			}
		}

		ui.onOutputLine("Player count - " + playerCount);

		players = new Player[playerCount];

		for (int playerNum = 0; playerNum < playerCount; playerNum++) {

			Player player = null;

			String commands[] = { "Human", "AI" };
			ui.onOutputLine("Select player " + (playerNum + 1) + " type.");

			if (getCommandFromInput(ui, commands) == 0) {

				if (playerFigures == null
						|| playerFigures.length != playerCount) {

					ui.onOutput("Please type the player figure: ");
					char figure = ui.onInput().charAt(0);
					ui.onOutputLine("Player figure will be '" + figure
							+ "' (with out gaps(') of corse)");

					player = new HumanPlayer(figure);
				} else {

					player = new HumanPlayer(playerFigures[playerNum]);
				}

			} else {

				if (playerFigures == null
						|| playerFigures.length != playerCount) {

					ui.onOutput("Please type the player figure: ");
					char figure = ui.onInput().charAt(0);
					ui.onOutputLine("Player figure will be '" + figure
							+ "' (with out gaps(') of corse)");

					player = new AiPlayer(figure);
				} else {

					player = new AiPlayer(playerFigures[playerNum]);
				}
			}

			players[playerNum] = player;

		}

		return players;
	}

	public static Referee createInstance(Ui ui) {

		ui.onOutputLine("Welcome in Xs and Os (a.k.a. Tic-tac-toe)!");
		ui.onOutputLine("Would you play classic Tic-tac-toe (field 3 x 3, start cell left bottom)?");

		String commands[] = { "Classic", "Custom" };

		Field field = null;
		Player players[] = null;

		int commandNum = getCommandFromInput(ui, commands);
		ui.onOutputLine("Game type - " + commands[commandNum]);

		if (commandNum == 0) {

			field = prepareField4Game(ui, 3, 3, ' ', 4);
			char playerFigures[] = { 'X', 'O' };
			players = setupPlayers(ui, 2, playerFigures);

		} else {

			field = prepareField4Game(ui, 0, 0, '\0', 0);
			players = setupPlayers(ui, 0, null);
		}

		Player.setPlayGround(field);
		return new Referee(ui, field, players);
	}

	private Ui ui;
	private Field playGround;
	private Player players[];
	private Chronicler chronicler;
	private Player winner;
	private int x;
	private int y;
	private boolean exitNow;

	private Referee(Ui ui, Field playGround, Player players[]) {

		this.ui = ui;
		this.playGround = playGround;
		this.players = new Player[players.length];
		System.arraycopy(players, 0, this.players, 0, players.length);
		chronicler = new Chronicler(this.playGround.getHeightCount()
				* this.playGround.getWidthCount());
	}

	private char letsHumanPlayerMakeADesign(Player player) {

		Cell cell = null;

		ui.onOutputLine("To undo type 'step'.\nTo exit from game type 'exit'.");
		ui.onOutput("Player of " + player
				+ " please make your move (position x, y):");

		x = -1;
		y = -1;

		do {

			if (checkForCommandInput(true)) {

				if (exitNow) {

					return player.getFigure();
				}
				return makeUndo();
			}
		} while (x == -1);

		do {

			if (checkForCommandInput(false)) {

				if (exitNow) {

					return player.getFigure();
				}
				return makeUndo();
			}
		} while (y == -1);

		cell = player.makeDesign(x, y);
		if (cell != null) {

			ui.onOutputLine("x = " + x + " y = " + y);
			if (playGround.isValidCellNumber(x, y)) {

				ui.onOutputLine("Position is busy by non default figure.");
			} else {

				ui.onOutputLine("Position is out side of the field.");
			}

			return letsHumanPlayerMakeADesign(player);
		}

		ui.onOutputLine("\n---------\n" + playGround + "---------");
		chronicler.addWalk(cell);

		return player.getFigure();
	}

	public void gameLoop() {

		final int playerCount = players.length;
		int playerNumber = 0;

		do {

			if (playerNumber == playerCount) {

				playerNumber = 0;
			}

			char figure = Cell.getDefaultFigure();

			if (players[playerNumber].getType() == Player.Type.AI) {

				Cell cell = players[playerNumber].makeDesign();
				chronicler.addWalk(cell);
				figure = players[playerNumber].getFigure();
			} else {

				figure = letsHumanPlayerMakeADesign(players[playerNumber]);
			}

			if (exitNow) {

				break;
			}

			if (playGround.isFigureFillDiagonal(players[playerNumber]
					.getFigure())
					|| playGround.isFigureFillLine(players[playerNumber]
							.getFigure())) {

				winner = players[playerNumber];
				break;
			}

			if (figure != Cell.getDefaultFigure()
					&& figure == players[playerNumber].getFigure()) {

				playerNumber++;
			} else if (figure == Cell.getDefaultFigure()) {

				playerNumber = 0;
			}

		} while (!playGround.isFull());

		gameOver();
	}

	private boolean checkForCommandInput(boolean isForX) {

		String command = ui.onInput();

		if (!command.equals("step") && !command.equals("exit")) {

			try {

				int i = Integer.valueOf(command);
				if (isForX) {

					x = i;
				} else {

					y = i;
				}
			} catch (NumberFormatException exc) {

				ui.onOutputLine("Please enter integer value ");
			}

			return false;
		}

		if (command.equals("exit")) {

			exitNow = true;
		}

		return true;
	}

	private char makeUndo() {

		ui.onOutputLine("There few next steps made at this point.");
		ui.onOutputLine(chronicler.toString());
		ui.onOutput("Which one do you what to revert?\n"
				+ "Type step num (0 (clean field)"
				+ (chronicler.getStepCount() > 0 ? "- "
						+ chronicler.getStepCount() : "") + ") ");

		int stepNum = ui.onInputInt();

		return chronicler.revertTo(stepNum, playGround);
	}

	private void gameOver() {

		if (!exitNow) {

			ui.onOutputLine("The game is over");
			if (winner != null) {

				ui.onOutputLine("The winner is player " + winner);
			} else {

				ui.onOutputLine("The friendship is win");
			}
		} else {

			ui.onOutputLine("Game interrupted ");
		}

		ui.onOutputLine("\n---------\n" + playGround + "---------");

		if (chronicler.getStepCount() > 0) {

			ui.onOutputLine("Game chronics");
			ui.onOutputLine(chronicler.toString());
		}
	}

}
