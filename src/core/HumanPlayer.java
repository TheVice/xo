package core;

public class HumanPlayer extends Player {

	public HumanPlayer(char figure) {

		super(figure, Type.HUMAN);
	}

	@Override
	public Cell makeDesign(int x, int y) {

		if (Player.playGround == null) {

			return null;
		}
		return Player.playGround.setCell(x, y, getFigure());
	}

}
