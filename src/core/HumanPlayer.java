package core;

public class HumanPlayer extends Player {

    public HumanPlayer(char figure, Field playGround) {

        super(figure, playGround, Type.HUMAN);
    }

    public HumanPlayer(char playerFigure) {

        this(playerFigure, null);
    }

    @Override
    public Cell makeDesign(int x, int y) {

        if (Player.playGround == null) {

            return null;
        }
        return Player.playGround.setCell(x, y, getFigure());
    }

}
