package comaveryscottnorris.httpsgithub.team4sconnect4;

/**
 * Created by kavinarasu on 2/13/18.
 */

class Cell_1088 {
    public boolean empty;
    public Board_1088.Turn player;

    public Cell_1088() {
        empty = true;
    }

    public void setPlayer(Board_1088.Turn player) {
        this.player = player;
        empty = false;
    }

}
