package core;

public class Referee {

    public static Referee createInstance() {

        Field field = new Field(3, 3, Cell.getDefaultFigure(), Field.StartCellStyle.BOTTOM_LEFT);
        Player players[] = new Player[2];
        players[0] = new AiPlayer('X', field);
        players[1] = new AiPlayer('O');

        return new Referee(field, players);
    }

    private Field playGround;
    private Player players[];
//    private Player winner;
//    private Chronicler chronicler;

    private Referee(Field playGround, Player players[]) {

        this.playGround = playGround;
        this.players = new Player[players.length];
        System.arraycopy(players, 0, this.players, 0, players.length);
//        chronicler = new Chronicler(this.playGround.getHeightCount() * this.playGround.getWidthCount());
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

                players[playerNumber].makeDesign();
                figure = players[playerNumber].getFigure();
            } else {

                //figure = letsPlayerMakeADesign(players[playerNumber]);
            }
//            if (exitNow) {
//
//                break;
//            }

            if (playGround.isFigureFillDiagonal(players[playerNumber].getFigure()) ||
                    playGround.isFigureFillLine(players[playerNumber].getFigure())) {

//                winner = players[playerNumber];
                break;
            }

            if (figure != Cell.getDefaultFigure() && figure == players[playerNumber].getFigure()) {

                playerNumber++;
            } else if (figure == Cell.getDefaultFigure()) {

                playerNumber = 0;
            }

        } while (!playGround.isFull());
    }
}

