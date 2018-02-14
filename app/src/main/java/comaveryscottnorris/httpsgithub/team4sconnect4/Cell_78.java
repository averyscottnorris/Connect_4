package comaveryscottnorris.httpsgithub.team4sconnect4;

import android.app.Activity;

/**
 * Created by kavinarasu on 1/31/18.
 */

public class Cell_78 extends Activity {

    public boolean empty;
    public Board_78.Turn player;

    public Cell_78() {
        empty = true;
    }

    public void setPlayer(Board_78.Turn player) {
        this.player = player;
        empty = false;
    }

}
