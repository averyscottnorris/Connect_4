package comaveryscottnorris.httpsgithub.team4sconnect4.AIMode;

/**
 * Created by rahul .
 */

public class AIMove {



    private final int mColumn;
    private final int mScore;

    /**
     * @param column mColumn the move is in
     * @param score  the mScore of the move
     */
    public AIMove(int column, int score) {
        this.mColumn = column;
        this.mScore = score;
    }

    /**
     * Get the mColumn the move is in
     *
     * @return the mColumn the move is in
     */
    public int getColumn() {
        return mColumn;
    }

    /**
     * Get the mScore of the move
     *
     * @return the mScore of the move
     */
    public int getScore() {
        return mScore;
    }

}