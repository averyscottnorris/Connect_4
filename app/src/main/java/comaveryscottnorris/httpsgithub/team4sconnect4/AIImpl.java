package comaveryscottnorris.httpsgithub.team4sconnect4.AIMode;

/**
 * Created by krahul .
 */

public class AIImpl {

    public static final int PLAYER1 = 1;//you
    public static final int PLAYER2 = 2;//Ai or friend



    private AIMove chooseMove(int player, int opponent,
                            int alpha, int beta, int depth) {
        AIMove bestmove = new AIMove(-1, player == PLAYER2 ? alpha : beta);
        // finding a non column from left to right
        for (int i = 0; i < GameActivityAI_76.NUM_COLS; i++) {
            if (GameActivityAI_76.NUM_ROWS > 0) {
                // check for win-condition

                // score this move and all its children
                int score = 0;
                if (checkMatch(i, GameActivityAI_76.NUM_ROWS)) {
                    // this move is a winning move for the player
                    score = player == PLAYER2 ? 1 : -1;
                } else if (depth != 1) {
                    // this move wasn't a win or a draw, so go to the next move
                    score = chooseMove(opponent, player, alpha, beta,
                            depth - 1).getScore();
                }
                undoMove(i);
                // if this move beats this player's best move so far, record it
                if (player == PLAYER2 && score > bestmove.getScore()) {
                    bestmove = new AIMove(i, score);
                    alpha = score;
                } else if (player == PLAYER1 && score < bestmove.getScore()) {
                    bestmove = new AIMove(i, score);
                    beta = score;
                }
                // don't continue with this branch, we've already found better
                if (alpha >= beta) {
                    return bestmove;
                }
            }
        }

        return bestmove;
    }
    public boolean checkMatch(int column, int row) {
        int horizontal_matches = 0;
        int vertical_matches = 0;
        int forward_diagonal_matches = 0;
        int backward_diagonal_matches = 0;

        // horizontal matches
        for (int i = 1; i < 4; i++) {
            if (GameActivityAI_76.countermatch(column, row, column + i, row)) {
                horizontal_matches++;
            } else break;
        }

        for (int i = 1; i < 4; i++) {
            if (GameActivityAI_76.countermatch(column, row, column - i, row)) {
                horizontal_matches++;
            } else break;
        }

        // vertical matches
        for (int i = 1; i < 4; i++) {
            if (GameActivityAI_76.countermatch(column, row, column, row + i)) {
                vertical_matches++;
            } else break;
        }

        for (int i = 1; i < 4; i++) {
            if (GameActivityAI_76.countermatch(column, row, column, row - i)) {
                vertical_matches++;
            } else break;
        }

        // backward diagonal matches ( \ )
        for (int i = 1; i < 4; i++) {
            if (GameActivityAI_76.countermatch(column, row, column + i, row - i)) {
                backward_diagonal_matches++;
            } else break;
        }

        for (int i = 1; i < 4; i++) {
            if (GameActivityAI_76.countermatch(column, row, column - i, row + i)) {
                backward_diagonal_matches++;
            } else break;
        }

        // forward diagonal matches ( / )
        for (int i = 1; i < 4; i++) {
            if (GameActivityAI_76.countermatch(column, row, column + i, row + i)) {
                forward_diagonal_matches++;
            } else break;
        }

        for (int i = 1; i < 4; i++) {
            if (GameActivityAI_76.countermatch(column, row, column - i, row - i)) {
                forward_diagonal_matches++;
            } else break;
        }

        return horizontal_matches >= 4 - 1
                || vertical_matches >= 4 - 1
                || forward_diagonal_matches >= 4 - 1
                || backward_diagonal_matches >= 4 - 1;
    }
    public void undoMove(int column) {
        if (column < GameActivityAI_76.NUM_ROWS) {
            column++;


        }
    }
}
