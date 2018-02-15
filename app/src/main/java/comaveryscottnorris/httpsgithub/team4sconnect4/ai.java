package comaveryscottnorris.httpsgithub.team4sconnect4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

/**
 * Created by kavinarasu on 2/13/18.
 */

public class ai extends AppCompatActivity {

    private ImageView[][] cells1 = null;
    private View boardView1;
    private Board_78 board1;
    //private LinearLayout mlinearLayout;
    private GameActivity.ViewHolder viewHolder1 = null;
    private static int NUM_ROWS = 7;
    private static int NUM_COLS = 8;
    public static final String TAG = "GameActivity";

    private class ViewHolder {
        public TextView winnerText;
        public ImageView turnIndicatorImageView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_78);
        viewHolder1 = new GameActivity.ViewHolder();
        board1 = new Board_78(NUM_COLS, NUM_ROWS);
        boardView1 = findViewById(R.id.game_board);
        buildCells();
        Log.d(TAG,"Oncreate");
        // TODO :  Dynamic Cell creation
        //mlinearLayout = new LinearLayout(R.id.);
        boardView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_UP: {
                        int col = colAtX(motionEvent.getX());
                        if (col != -1)
                            drop(col);
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

        viewHolder1.turnIndicatorImageView = findViewById(R.id.turn_indicator_image_view);
        viewHolder1.turnIndicatorImageView.setImageResource(resourceForTurn());
        viewHolder1.winnerText = findViewById(R.id.winner_text);
        viewHolder1.winnerText.setVisibility(View.GONE);

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

        cells1 = new ImageView[NUM_ROWS][NUM_COLS];
        if (cells1 != null) {
            Log.d(TAG, " entered");
            for (int r = 0; r < NUM_ROWS; r++) {
                ViewGroup row = (ViewGroup) ((ViewGroup) boardView1).getChildAt(r);
                row.setClipChildren(false);
                for (int c = 0; c < NUM_COLS; c++) {
                    ImageView imageView = (ImageView) row.getChildAt(c);
                    imageView.setImageResource(android.R.color.transparent);
                    cells1[r][c] = imageView;
                }
            }
        }
        else    {
            Log.d(TAG, "Not entered");
        }

    }

    private void drop(int col) {
        if (board1.hasWinner)
            return;
        int row = board1.lastAvailableRow(col);
        if (row == -1)
            return;
        final ImageView cell = cells1[row][col];
        float move = -(cell.getHeight() * row + cell.getHeight() + 15);
        cell.setY(move);
        cell.setImageResource(resourceForTurn());
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, Math.abs(move));
        anim.setDuration(850);
        anim.setFillAfter(true);
        cell.startAnimation(anim);
        board1.occupyCell(col, row);
        if (board1.checkForWin(col, row)) {
            win();
        } else {
            changeTurn();
        }
    }

    private void win() {
        int color = board1.turn == Board_78.Turn.FIRST ? getResources().getColor(R.color.primary_player) : getResources().getColor(R.color.secondary_player);
        viewHolder1.winnerText.setTextColor(color);
        viewHolder1.winnerText.setVisibility(View.VISIBLE);
        // Changes by Avery
        updateScores((board1.turn == Board_78.Turn.FIRST) ? getPlayerOneName() : getPlayerTwoName());
        alert((board1.turn == Board_78.Turn.FIRST) ? getPlayerOneName() : getPlayerTwoName());
        // End of Avery's changes
    }

    private void changeTurn() {
        board1.toggleTurn();
        viewHolder1.turnIndicatorImageView.setImageResource(resourceForTurn());
    }

    private int colAtX(float x) {
        float colWidth = cells1[0][0].getWidth();
        int col = (int) x / (int) colWidth;
        if (col < 0 || col > 7)
            return -1;
        return col;
    }

    private int resourceForTurn() {
        switch (board1.turn) {
            case FIRST:
                return R.drawable.red;
            case SECOND:
                return R.drawable.yellow;
        }
        return R.drawable.red;
    }

    private void reset() {
        board1.reset();
        viewHolder1.winnerText.setVisibility(View.GONE);
        viewHolder1.turnIndicatorImageView.setImageResource(resourceForTurn());
        for (int r=0; r<NUM_ROWS; r++) {
            for (int c=0; c<NUM_COLS; c++) {
                cells1[r][c].setImageResource(android.R.color.transparent);
            }
        }
    }


    // Added by Avery Norris
    private void updateScores(String playerName) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        int score;

        score = preferences.getInt(playerName, 0);
        score++;
        editor.putInt(playerName, score);
        editor.apply();
    }

    private void alert(String playerName) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("New Score!");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int score = preferences.getInt(playerName, 0);
        String print = playerName + "'s score is now: " + score + "!";
        alertDialog.setMessage(print);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private String getPlayerOneName() {
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("player1name", "Player 1");
        return name;
    }

    private String getPlayerTwoName() {
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("player2name", "Player 2");
        return name;
    }
    // End of Avery's new functions
}

