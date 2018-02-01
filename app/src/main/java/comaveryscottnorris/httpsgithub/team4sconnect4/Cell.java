package comaveryscottnorris.httpsgithub.team4sconnect4;

/**
 * Created by kavinarasu on 1/30/18.
 */

public class Cell {
    public boolean empty;
    public Board.Turn player;

    public Cell() {
        empty = true;
    }

    public void setPlayer(Board.Turn player) {
        this.player = player;
        empty = false;
    }
}
