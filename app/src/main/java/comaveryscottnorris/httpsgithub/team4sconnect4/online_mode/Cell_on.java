package comaveryscottnorris.httpsgithub.team4sconnect4.online_mode;

import android.support.v7.app.AppCompatActivity;

import comaveryscottnorris.httpsgithub.team4sconnect4.Board;

/**
 * Created by kavinarasu on 3/11/18.
 */

public class Cell_on extends AppCompatActivity{
    public boolean empty;
    public Board_on.Turn player;

    public Cell_on() {
        empty = true;
    }

    public void setPlayer(Board_on.Turn player) {
        this.player = player;
        empty = false;
    }
}
