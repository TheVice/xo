package core;

public class Player {

	public static enum PlayerType {

		HUMAN, AI
	}

	public static enum PlayerFigure {

		X, O
	}

	protected static Field playGround;

	private PlayerType playerType;
	private PlayerFigure playerFigure;

	protected Player(PlayerType playerType, Field playGround,
			PlayerFigure playerFigure) {

		this.playerType = playerType;

		if (Player.playGround == null) {

			Player.playGround = playGround;
		}

		this.playerFigure = playerFigure;
	}

	public Player(Field playGround, PlayerFigure playerFigure) {

		this(PlayerType.HUMAN, playGround, playerFigure);
	}

	public Player(PlayerFigure playerFigure) {

		this(null, playerFigure);
	}

	public PlayerFigure getPlayerFigure() {

		return playerFigure;
	}

	public PlayerType getPlayerType() {

		return playerType;
	}

	public boolean makeMove(int x, int y) {

		if (Player.playGround == null || playerFigure == null) {

			return false;
		}
		return Player.playGround.setCell(x, y, getFigure());
	}

	public char getFigure() {

		return getCharFromFigure(playerFigure);
	}

	@Override
	public String toString() {

		return "" + getFigure();
	}

	private static char getCharFromFigure(PlayerFigure playerFigure) {

		if (playerFigure != null) {

			switch (playerFigure) {

			case X:
				return 'X';
			case O:
				return 'O';
			}
		}

		return Cell.getDefFigureValue();
	}
}
