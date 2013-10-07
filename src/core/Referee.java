package core;

import ui.Ui;

public class Referee {

	private static boolean isCommandEntered;

	private static int getIntFromInput(Ui ui) {

		int integerValue = 0;
		boolean integerEntered = false;

		do {
			try {

				integerValue = ui.onInputInt();
				integerEntered = true;
			} catch (NumberFormatException nexc) {

				ui.onOutputLine("Please enter integer value.");
			}
		} while (!integerEntered);

		return integerValue;
	}

	private static int getCommandFromInput(Ui ui, String[] commands) {

		int num = 0;
		boolean commandEntered = false;

		do {
			ui.onOutputLine("Please type one of the commands below:");

			for (int commandNum = 0; commandNum < commands.length; commandNum++) {

				ui.onOutputLine(commands[commandNum]);
			}

			String command = ui.onInput();

			for (int commandNum = 0; commandNum < commands.length; commandNum++) {

				if (command.equals(commands[commandNum])) {

					num = commandNum;
					commandEntered = true;
					break;
				}
			}

		} while (!commandEntered);

		return num;
	}

	private static int getCommandOrIntFromInput(Ui ui, String[] commands) {

		int num = 0;
		boolean commandEntered = false;
		isCommandEntered = false;

		do {

			String command = ui.onInput();

			for (int commandNum = 0; commandNum < commands.length; commandNum++) {

				if (command.equals(commands[commandNum])) {

					num = commandNum;
					commandEntered = true;
					isCommandEntered = true;
					break;
				}
			}
			if (!commandEntered) {

				try {

					num = Integer.valueOf(command);
					commandEntered = true;
				} catch (NumberFormatException nexc) {

				}
			}

		} while (!commandEntered);

		return num;
	}

	private static boolean isFigurePresent(char figures[], char figure) {

		if (figures != null) {

			for (char presentFigure : figures) {

				if (figure == presentFigure) {

					return true;
				}
			}
		}

		return false;
	}

	private Ui ui;
	private Player players[];
	private Player winner;
	private char playerFigures[];
	private Field playGround;
	private Chronicler chronicler;
	private boolean exitNow;

	public Referee(Ui ui) {

		this.ui = ui;

		boolean playAgain = false;
		do {

			this.players = null;
			this.winner = null;
			this.playerFigures = null;
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

		if (playerCount <= 0
				|| playerCount > Field.getMaxWidth() * Field.getMaxHeight()) {

			boolean playerCountSet = false;
			do {

				ui.onOutputLine("How many player would you like to play in game?");
				playerCount = getIntFromInput(ui);

				if (playerCount < 1) {

					ui.onOutputLine("Player count can not be less than - 1");
				} else if (playerCount > Field.getMaxWidth()
						* Field.getMaxHeight()) {

					ui.onOutputLine("Player count can not be greate than maximum cell count - "
							+ (Field.getMaxWidth() * Field.getMaxHeight())
							+ ".");
				} else {

					playerCountSet = true;
				}

			} while (!playerCountSet);
		}

		ui.onOutputLine("Player count - " + playerCount);

		players = new Player[playerCount];
		boolean playerFigurePreset = (playerFigures != null && playerCount == playerFigures.length);

		if (!playerFigurePreset) {

			this.playerFigures = new char[playerCount];
		}

		for (int playerNum = 0; playerNum < playerCount; playerNum++) {

			ui.onOutputLine("Determining player num - " + (playerNum + 1));

			if (playerFigurePreset) {

				players[playerNum] = setupPlayer(playerFigures[playerNum]);
			} else {

				players[playerNum] = setupPlayer();
				this.playerFigures[playerNum] = players[playerNum].getFigure();
			}
		}
	}

	private Player setupPlayer() {

		char figure = '\0';
		boolean playerFigureSet = false;

		do {

			ui.onOutput("Please type the player figure: ");
			figure = ui.onInput().charAt(0);

			playerFigureSet = !isFigurePresent(playerFigures, figure);
			if (!playerFigureSet) {

				ui.onOutputLine("Figure '" + figure
						+ "' already set to other player.");
			}
		} while (!playerFigureSet);

		return setupPlayer(figure);
	}

	private Player setupPlayer(char figure) {

		ui.onOutputLine("Player figure will be '" + figure
				+ "' (with out gaps(') of corse).");

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

				defaultFieldFigureSet = !isFigurePresent(playerFigures, figure);
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

		if (styleNumber < 0 || styleNumber >= fieldNum) {

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

		final int playerCount = players.length;
		int playerNumber = 0;

		do {

			if (playerNumber == playerCount) {

				playerNumber = 0;
			}

			char figure = Cell.getDefaultFigure();

			if (players[playerNumber].getType() == Player.Type.AI) {

				ui.onOutput("Player of " + players[playerNumber]
						+ " please make your move.");
				chronicler
						.addWalk(players[playerNumber].makeDesign(playGround));
				figure = players[playerNumber].getFigure();
			} else {

				figure = letsHumanPlayerMakeADesign(players[playerNumber]);
			}

			ui.onOutputLine(System.getProperty("line.separator") + "---------"
					+ System.getProperty("line.separator") + playGround
					+ "---------");

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

	private char letsHumanPlayerMakeADesign(Player player) {

		Cell cell = null;

		String commands[] = { "step", "exit" };
		ui.onOutputLine("To undo type 'step'."
				+ System.getProperty("line.separator")
				+ "To exit from game type 'exit'.");
		ui.onOutput("Player of " + player
				+ " please make your move (position x, y): ");

		int x = getCommandOrIntFromInput(ui, commands);
		if (Referee.isCommandEntered) {

			if (x == 0) {

				exitNow = true;
				return player.getFigure();
			}
			return makeUndo();
		}

		int y = getCommandOrIntFromInput(ui, commands);
		if (Referee.isCommandEntered) {

			if (y == 0) {

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

			return letsHumanPlayerMakeADesign(player);
		}

		chronicler.addWalk(cell);

		return player.getFigure();
	}

	private char makeUndo() {

		ui.onOutputLine("There few next steps made at this point.");
		ui.onOutputLine(chronicler);
		ui.onOutput("Which one do you what to revert?"
				+ System.getProperty("line.separator")
				+ "Type step num (0 (clean field)"
				+ (chronicler.getStepCount() > 0 ? "- "
						+ chronicler.getStepCount() : "") + ") ");

		int stepNum = ui.onInputInt();
		//TODO: stepNum may check via say to user possible values
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

		ui.onOutputLine(System.getProperty("line.separator") + "---------"
				+ System.getProperty("line.separator") + playGround
				+ "---------");

		if (chronicler.getStepCount() > 0) {

			ui.onOutputLine("Game chronics.");
			ui.onOutputLine(chronicler);
		}
	}

}
