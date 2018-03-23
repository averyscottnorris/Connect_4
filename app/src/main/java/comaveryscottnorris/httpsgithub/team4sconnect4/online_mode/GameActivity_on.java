package comaveryscottnorris.httpsgithub.team4sconnect4.online_mode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import comaveryscottnorris.httpsgithub.team4sconnect4.Board;
import comaveryscottnorris.httpsgithub.team4sconnect4.GameActivity;
import comaveryscottnorris.httpsgithub.team4sconnect4.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import comaveryscottnorris.httpsgithub.team4sconnect4.online_mode.Board_on.Turn;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by kavinarasu on 3/11/18.
 */

public class GameActivity_on extends AppCompatActivity{
    private ImageView[][] cells = null;
    private View boardView;
    private Board_on board;
    //private LinearLayout mlinearLayout;
    private GameActivity.ViewHolder viewHolder = null;
    private static int NUM_ROWS = 6;
    private static int NUM_COLS = 7;
    public static final String TAG = "GameActivity";

    public static class ViewHolder {
        public TextView winnerText;
        // Changes by Avery
        public TextView currentPlayer;
        // End Every's Changes
        public ImageView turnIndicatorImageView;
    }
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.86.32:8080");
            Log.e("Hi","connected successfully");
            String msg ="connect successfully";
            mSocket.emit("new msg",msg);
        } catch (Exception e) {}

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mSocket.on("column_event", onColumnEvent);
        mSocket.connect();
        viewHolder = new GameActivity.ViewHolder();
        board = new Board_on(NUM_COLS, NUM_ROWS);
        boardView = findViewById(R.id.game_board);
        buildCells();
        Log.d(TAG, "Oncreate");
        // TODO :  Dynamic Cell creation
        //mlinearLayout = new LinearLayout(R.id.);
        boardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_UP: {
                        int col = colAtX(motionEvent.getX());
                        //int rows=board.lastAvailableRow(col);
                        if (col != -1)
                            drop_online(col);
                    }
                }
                return true;
            }
        });
        Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        viewHolder.turnIndicatorImageView = findViewById(R.id.turn_indicator_image_view);
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
        // Changes by Avery
       // viewHolder.currentPlayer = findViewById(R.id.playerName);
        //viewHolder.currentPlayer.setText("   " + getPlayerOneName());
        // End avery's changes
        viewHolder.winnerText = findViewById(R.id.winner_text);
        viewHolder.winnerText.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private void buildCells() {

        cells = new ImageView[NUM_ROWS][NUM_COLS];
        if (cells != null) {
            Log.d(TAG, " entered");
            for (int r = 0; r < NUM_ROWS; r++) {
                ViewGroup row = (ViewGroup) ((ViewGroup) boardView).getChildAt(r);
                row.setClipChildren(false);
                for (int c = 0; c < NUM_COLS; c++) {
                    ImageView imageView = (ImageView) row.getChildAt(c);
                    imageView.setImageResource(android.R.color.transparent);
                    cells[r][c] = imageView;
                }
            }
        } else {
            Log.d(TAG, "Not entered");
        }

    }

    private void drop(int col) {
        if (board.hasWinner)
            return;
        int row = board.lastAvailableRow(col);
        if (row == -1)
            return;
        final ImageView cell = cells[row][col];
        float move = -(cell.getHeight() * row + cell.getHeight() + 15);
        cell.setY(move);
        cell.setImageResource(resourceForTurn());
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, Math.abs(move));
        anim.setDuration(850);
        anim.setFillAfter(true);
        cell.startAnimation(anim);
        board.occupyCell(col, row);
        String pos=row+","+col+","+ board.turn;
        mSocket.emit("call",pos);
        if (board.checkForWin(col, row)) {
            win();
        } else {
            changeTurn();
        }
    }

    private void drop_online(int col) {
        if (board.hasWinner)
            return;
        int row = board.lastAvailableRow(col);
        if (row == -1)
            return;
        final ImageView cell = cells[row][col];
        float move = -(cell.getHeight() * row + cell.getHeight() + 15);
        cell.setY(move);
        cell.setImageResource(resourceForTurn());
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, Math.abs(move));
        anim.setDuration(850);
        anim.setFillAfter(true);
        cell.startAnimation(anim);
        board.occupyCell(col, row);
        String pos=row+","+col+","+board.turn;
        mSocket.emit("call",pos);
        if (board.checkForWin(col, row)) {
            win();
        } else {
            changeTurn();
        }
    }

    private void win() {
        int color = board.turn == Board_on.Turn.FIRST ? getResources().getColor(R.color.primary_player) : getResources().getColor(R.color.secondary_player);
        viewHolder.winnerText.setTextColor(color);
        viewHolder.winnerText.setVisibility(View.VISIBLE);
        // Changes by Avery
        //updateScores((board.turn == Board_on.Turn.FIRST) ? getPlayerOneName() : getPlayerTwoName());
        //alert((board.turn == Board_on.Turn.FIRST) ? getPlayerOneName() : getPlayerTwoName());
        // End of Avery's changes
    }

    private void changeTurn() {
        board.toggleTurn();
        // Changes by Avery
        //viewHolder.currentPlayer = findViewById(R.id.playerName);
        //if (board.turn == Board_on.Turn.FIRST) {
        //   viewHolder.currentPlayer.setText("   " + getPlayerOneName());
        //} else {
        //    viewHolder.currentPlayer.setText("   " + getPlayerTwoName());
        //}
        // End Avery's Changes
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
    }

    private int colAtX(float x) {
        float colWidth = cells[0][0].getWidth();
        int col = (int) x / (int) colWidth;
        if (col < 0 || col > 6)
            return -1;
        return col;
    }

    private int resourceForTurn() {
        switch (board.turn) {
            case FIRST:
                return R.drawable.red;
            case SECOND:
                return R.drawable.yellow;
        }
        return R.drawable.red;
    }

    private Emitter.Listener onColumnEvent = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            GameActivity_on.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int row1;
                    int col1;
                    //String Playername;
                    try {
                        row1 = data.getInt("row");
                        col1 = data.getInt("column");
                        //board.turn = data.get("player");

                    } catch (JSONException e) {
                        return;
                    }
                    drop_online(col1);
                }
            });
        }
    };


    private void reset() {
        board.reset();
        viewHolder.winnerText.setVisibility(View.GONE);
        // Changes by Avery
        // Change current player back to player 1
        //viewHolder.currentPlayer.setText("   " + getPlayerOneName());
        // End Avery's Changes
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
        for (int r = 0; r < NUM_ROWS; r++) {
            for (int c = 0; c < NUM_COLS; c++) {
                cells[r][c].setImageResource(android.R.color.transparent);
            }
        }
    }

}
