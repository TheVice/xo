package core;

import ui.Ui;

public class Referee {

	private static final String lineSeparator = System
			.getProperty("line.separator");
	private static boolean isCommandEntered;

	private static int getIntFromInput(Ui ui) {

		int integerValue = 0;

		do {
			try {

				integerValue = ui.onInputInt();
				break;
			} catch (NumberFormatException nexc) {

				ui.onOutputLine("Please enter integer value.");
			}
		} while (true);

		return integerValue;
	}

	private static int getCommandFromInput(Ui ui, String[] commands) {

		int commandNumber = 0;

		boolean commandEntered = false;
		do {
			ui.onOutputLine("Please type one of the commands below:");

			for (int commandNum = 0; commandNum < commands.length; commandNum++) {

				ui.onOutputLine(commands[commandNum]);
			}

			String command = ui.onInput();

			for (int num = 0; num < commands.length; num++) {

				if (command.equals(commands[num])) {

					commandNumber = num;
					commandEntered = true;
					break;
				}
			}

		} while (!commandEntered);

		return commandNumber;
	}

	private static int getCommandOrIntFromInput(Ui ui, String[] commands) {

		isCommandEntered = false;
		int commandNumber = 0;

		boolean commandEntered = false;
		do {

			String command = ui.onInput();

			for (int num = 0; num < commands.length; num++) {

				if (command.equals(commands[num])) {

					commandNumber = num;
					isCommandEntered = true;
					commandEntered = true;
					break;
				}
			}
			if (!commandEntered) {

				try {

					commandNumber = Integer.parseInt(command);
					commandEntered = true;
				} catch (NumberFormatException nexc) {

				}
			}

		} while (!commandEntered);

		return commandNumber;
	}

	private Ui ui;
	private Player players[];
	private Player winner;
	private Field playGround;
	private Chronicler chronicler;
	private boolean exitNow;

	public Referee(Ui ui) {

		this.ui = ui;

		boolean playAgain = false;
		do {

			this.players = null;
			this.winner = null;
			this.playGround = null;
			this.chronicler = null;
			this.exitNow = false;

			this.ui.onOutputLine("Welcome in Xs and Os (a.k.a. Tic-tac-toe)!");
			init();

			this.ui.onOutput("Would you like play another party (y/n): ");
			String str = ui.onInput();
			if (str.charAt(0) == 'y') {

				playAgain = true;
			} else {

				playAgain = false;
			}

		} while (playAgain);
	}

	private void init() {

		ui.onOutputLine("Would you play classic Tic-tac-toe (field 3 x 3, start cell left bottom)?");
		String commands[] = { "Classic", "Custom" };
		int commandNum = getCommandFromInput(ui, commands);
		ui.onOutputLine("Game type - " + commands[commandNum] + ".");

		if (commandNum == 0) {

			char playerFigures[] = { 'X', 'O' };
			setupPlayers(2, playerFigures);
			playGround = setupField(3, 3, ' ', 3);
		} else {

			setupPlayers(0, null);
			playGround = setupField(0, 0, '\0', -1);
		}

		chronicler = new Chronicler(this.playGround.getHeightCount()
				* this.playGround.getWidthCount());
		gameLoop();
	}

	private void setupPlayers(int playerCount, char[] playerFigures) {

		if (playerCount == 0) {

			boolean playerCountSet = false;
			do {

				ui.onOutputLine("How many player would you like to play in game?");
				playerCount = getIntFromInput(ui);

				if (playerCount < 1) {

					ui.onOutputLine("Player count can not be less than - 1.");
				} else if (playerCount > Field.getMaxWidth()
						* Field.getMaxHeight()) {

					ui.onOutputLine("Player count can not be great than maximum cell count - "
							+ (Field.getMaxWidth() * Field.getMaxHeight())
							+ ".");
				} else {

					playerCountSet = true;
				}

			} while (!playerCountSet);
		}

		ui.onOutputLine("Player count - " + playerCount);
		players = new Player[playerCount];

		for (int playerNum = 0; playerNum < playerCount; playerNum++) {

			ui.onOutputLine("Determining player num - " + (playerNum + 1));

			if (playerFigures != null) {

				players[playerNum] = setupPlayer(playerFigures[playerNum]);
			} else {

				players[playerNum] = setupPlayer();
			}
		}
	}

	private Player setupPlayer() {

		char figure = '\0';
		boolean playerFigureSet = false;

		do {

			ui.onOutput("Please type the player figure: ");
			figure = ui.onInput().charAt(0);

			playerFigureSet = !Player.isFigurePresent(
					Player.getPlayersFigures(this.players), figure);
			if (!playerFigureSet) {

				ui.onOutputLine("Figure '" + figure
						+ "' already set to another player.");
			}
		} while (!playerFigureSet);

		return setupPlayer(figure);
	}

	private Player setupPlayer(char figure) {

		ui.onOutputLine("Player figure will be '" + figure + "'.");

		ui.onOutputLine("Select player type.");

		String commands[] = { "Human", "AI" };
		int commandNum = getCommandFromInput(ui, commands);

		Player player = null;

		if (commandNum == 0) {

			player = new HumanPlayer(figure);
		} else {

			player = new AiPlayer(figure);
		}

		ui.onOutputLine("Player type - '" + commands[commandNum] + "'.");

		return player;
	}

	private char setupDefaultFieldFigure(char figure) {

		if (figure == '\0') {

			boolean defaultFieldFigureSet = false;
			do {

				ui.onOutput("Please type the default (neutral) figure: ");
				figure = ui.onInput().charAt(0);

				defaultFieldFigureSet = !Player.isFigurePresent(
						Player.getPlayersFigures(this.players), figure);
				if (!defaultFieldFigureSet) {

					ui.onOutputLine("Figure '" + figure
							+ "' already set to one of players.");
				}

			} while (!defaultFieldFigureSet);

		}

		ui.onOutputLine("Default figure will be '" + figure
				+ "' (with out gaps(') of corse).");

		return figure;
	}

	private void showField(Field field) {

		char figure = Cell.getDefaultFigure();

		ui.onOutputLine("Field size: " + field.getWidthCount() + " x "
				+ field.getHeightCount() + ".");

		field.setCell(1, 1, figure != 'S' ? 'S' : '*');

		ui.onOutputLine("Start position marked as "
				+ ((figure != 'S') ? "S" : "*") + ".");
		ui.onOutputLine("From this point (that is {1; 1}) to width and height"
				+ " (that is {" + field.getWidthCount() + "; "
				+ field.getHeightCount() + "}).");
		ui.onOutputLine("---------");
		ui.onOutput(field);
		ui.onOutputLine("---------");

		field.setCell(1, 1, figure);
	}

	private Field setupStartPositionAtField(int width, int height, char figure,
			int styleNumber) {

		String fieldStyles[] = { "Top Left", "Top Right", "Bottom Right",
				"Bottom Left" };

		Field fields[] = new Field[fieldStyles.length];

		int fieldNum = 0;
		for (Field.StartCellStyle scs : Field.StartCellStyle.values()) {

			fields[fieldNum++] = new Field(width, height, scs);
		}

		if (styleNumber < 0) {

			ui.onOutputLine("Choose the position from what calculate the cells.");

			fieldNum = 0;
			for (String style : fieldStyles) {

				ui.onOutputLine(style);
				showField(fields[fieldNum++]);
			}

			styleNumber = getCommandFromInput(ui, fieldStyles);
		}

		ui.onOutputLine("Selected field type: " + fieldStyles[styleNumber]);
		showField(fields[styleNumber]);

		return fields[styleNumber];
	}

	private Field setupField(int width, int height, char figure, int styleNumber) {

		if (width == 0 || height == 0) {

			ui.onOutputLine("What the size of field do you want (width x height)?");

			boolean intSet = false;
			do {

				ui.onOutput("Width - ");
				width = getIntFromInput(ui);
				ui.onOutputLine("Width - " + width);

				if (width < Field.getMinWidth()) {

					ui.onOutputLine("Width can not be less than - "
							+ Field.getMinWidth() + ".");
				} else if (width > Field.getMaxWidth()) {

					ui.onOutputLine("Width can not be greate than - "
							+ Field.getMaxWidth() + ".");
				} else {

					intSet = true;
				}

			} while (!intSet);

			intSet = false;
			do {

				ui.onOutput("Height - ");
				height = getIntFromInput(ui);
				ui.onOutputLine("Height - " + height);

				if (height < Field.getMinHeight()) {

					ui.onOutputLine("Height can not be less than - "
							+ Field.getMinHeight() + ".");
				} else if (height > Field.getMaxHeight()) {

					ui.onOutputLine("Height can not be greate than - "
							+ Field.getMaxHeight() + ".");
				} else {

					intSet = true;
				}

			} while (!intSet);

		}

		figure = setupDefaultFieldFigure(figure);
		Cell.setDefaultFigure(figure);
		Field field = setupStartPositionAtField(width, height, figure,
				styleNumber);

		return field;
	}

	public void gameLoop() {

		int playerNumber = 0;

		do {

			char figure = Cell.getDefaultFigure();

			if (players[playerNumber].getType() == Player.Type.AI) {

				ui.onOutput("Player of " + players[playerNumber]
						+ " please make your move.");
				chronicler.addWalk(((AiPlayer) players[playerNumber])
						.makeDesign(playGround));
				figure = players[playerNumber].getFigure();
			} else {

				figure = letsHumanPlayerMakeADesign(players[playerNumber]);
			}

			ui.onOutputLine(Referee.lineSeparator + "---------"
					+ Referee.lineSeparator + playGround + "---------");

			if (exitNow) {

				break;
			}

			if (playGround.isFigureTakeAWin(players[playerNumber].getFigure())) {

				winner = players[playerNumber];
				break;
			}

			playerNumber = Player.getNextPlayer(figure, players);

		} while (!playGround.isFull());

		gameOver();
	}

	private char letsHumanPlayerMakeADesign(Player player) {

		String commands[] = { "step", "exit" };
		Cell cell = null;

		do {

			ui.onOutputLine("To undo type 'step'." + Referee.lineSeparator
					+ "To exit from game type 'exit'.");
			ui.onOutput("Player of " + player
					+ " please make your move (position x, y): ");

			int x = getCommandOrIntFromInput(ui, commands);

			if (Referee.isCommandEntered) {

				if (x == 1) {

					exitNow = true;
					return player.getFigure();
				}
				return makeUndo();
			}

			int y = getCommandOrIntFromInput(ui, commands);

			if (Referee.isCommandEntered) {

				if (y == 1) {

					exitNow = true;
					return player.getFigure();
				}
				return makeUndo();
			}

			cell = player.makeMove(playGround, x, y);

			if (cell == null) {

				ui.onOutputLine("x = " + x + " y = " + y);
				if (playGround.isValidCellNumber(x, y)) {

					ui.onOutputLine("Position is busy by non default figure.");
				} else {

					ui.onOutputLine("Position is out side of the field.");
				}
			} else {

				break;
			}

		} while (true);

		chronicler.addWalk(cell);

		return player.getFigure();
	}

	private char makeUndo() {

		ui.onOutputLine("There few next steps made at this point.");
		ui.onOutputLine(chronicler);
		ui.onOutputLine("Which one do you what to revert?");

		final int index = chronicler.getStepCount();
		int stepNum = 0;

		do {

			ui.onOutput("Type step num (0 (clean field)"
					+ (index > 0 ? " - " + index : "") + ") ");

			stepNum = getIntFromInput(ui);

			if (stepNum < 0) {

				ui.onOutputLine("Number must be possitive or 0.");
			} else if (stepNum > index) {

				ui.onOutputLine("Number can not be more than - " + index + ".");
			} else {

				break;
			}

		} while (true);

		return chronicler.revertTo(stepNum, playGround);
	}

	private void gameOver() {

		if (!exitNow) {

			ui.onOutputLine("The game is over.");
			if (winner != null) {

				ui.onOutputLine("The winner is player " + winner + ".");
			} else {

				ui.onOutputLine("The friendship is win.");
			}
		} else {

			ui.onOutputLine("Game interrupted.");
		}

		ui.onOutputLine(Referee.lineSeparator + "---------"
				+ Referee.lineSeparator + playGround + "---------");

		if (chronicler.getStepCount() > 0) {

			ui.onOutputLine("Game chronics.");
			ui.onOutputLine(chronicler);
		}
	}

}
