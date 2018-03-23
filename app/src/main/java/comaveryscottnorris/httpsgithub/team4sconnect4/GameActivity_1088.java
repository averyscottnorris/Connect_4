package comaveryscottnorris.httpsgithub.team4sconnect4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;

/**
 * Created by kavinarasu on 2/13/18.
 */

public class GameActivity_1088 extends AppCompatActivity{
    private ImageView[][] cells2 = null;
    private View boardView2;
    private Board_1088 board2;
    //private LinearLayout mlinearLayout;
    private GameActivity.ViewHolder viewHolder = null;
    private static int NUM_ROWS = 8;
    private static int NUM_COLS = 10;
    public static final String TAG = "GameActivity";


    public static class ViewHolder {
        public TextView winnerText;
        // Changes by Avery
        public TextView currentPlayer;
        // End Every's Changes
        public ImageView turnIndicatorImageView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_1088);
        viewHolder = new GameActivity.ViewHolder();
        board2 = new Board_1088(NUM_COLS, NUM_ROWS);
        boardView2 = findViewById(R.id.game_board);
        buildCells();
        Log.d(TAG,"Oncreate");
        // TODO :  Dynamic Cell creation
        //mlinearLayout = new LinearLayout(R.id.);
        boardView2.setOnTouchListener(new View.OnTouchListener() {
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

        viewHolder.turnIndicatorImageView = findViewById(R.id.turn_indicator_image_view);
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
        // Changes by Avery
        viewHolder.currentPlayer = findViewById(R.id.playerName);
        viewHolder.currentPlayer.setText("   " + getPlayerOneName());
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

        cells2 = new ImageView[NUM_ROWS][NUM_COLS];
        if (cells2 != null) {
            Log.d(TAG, " entered");
            for (int r = 0; r < NUM_ROWS; r++) {
                ViewGroup row = (ViewGroup) ((ViewGroup) boardView2).getChildAt(r);
                row.setClipChildren(false);
                for (int c = 0; c < NUM_COLS; c++) {
                    ImageView imageView = (ImageView) row.getChildAt(c);
                    imageView.setImageResource(android.R.color.transparent);
                    cells2[r][c] = imageView;
                }
            }
        }
        else    {
            Log.d(TAG, "Not entered");
        }

    }

    private void drop(int col) {
        if (board2.hasWinner)
            return;
        int row = board2.lastAvailableRow(col);
        if (row == -1)
            return;
        final ImageView cell = cells2[row][col];
        float move = -(cell.getHeight() * row + cell.getHeight() + 15);
        cell.setY(move);
        cell.setImageResource(resourceForTurn());
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, Math.abs(move));
        anim.setDuration(850);
        anim.setFillAfter(true);
        cell.startAnimation(anim);
        board2.occupyCell(col, row);
        if (board2.checkForWin(col, row)) {
            win();

        } else {
            changeTurn();
        }
    }

    private void win() {
        int color = (board2.turn == Board_1088.Turn.FIRST) ? getResources().getColor(R.color.primary_player) : getResources().getColor(R.color.secondary_player);
        viewHolder.winnerText.setTextColor(color);
        viewHolder.winnerText.setVisibility(View.VISIBLE);
        ArrayList<ImageView> winseq = board2.getWinDiscs(cells2);
        //ArrayList<ImageView> winDiscs = board2.getWinDiscs(boardView2.);


        // Changes by Avery
        updateScores((board2.turn == Board_1088.Turn.FIRST) ? getPlayerOneName() : getPlayerTwoName());
        alert((board2.turn == Board_1088.Turn.FIRST) ? getPlayerOneName() : getPlayerTwoName());
        // End of Avery's changes
    }

    private void changeTurn() {
        board2.toggleTurn();
        // Changes by Avery
        viewHolder.currentPlayer = findViewById(R.id.playerName);
        if (board2.turn == Board_1088.Turn.FIRST) {
            viewHolder.currentPlayer.setText("   " + getPlayerOneName());
        }
        else {
            viewHolder.currentPlayer.setText("   " + getPlayerTwoName());
        }
        // End Avery's Changes
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
    }

    private int colAtX(float x) {
        float colWidth = cells2[0][0].getWidth();
        int col = (int) x / (int) colWidth;
        if (col < 0 || col > 10)
            return -1;
        return col;
    }

    private int resourceForTurn() {
        switch (board2.turn) {
            case FIRST:
                return R.drawable.red;
            case SECOND:
                return R.drawable.yellow;
        }
        return R.drawable.red;
    }

    private void reset() {
        board2.reset();
        viewHolder.winnerText.setVisibility(View.GONE);
        // Change by Avery
        viewHolder.currentPlayer.setText("   " + getPlayerOneName());
        // End Avery's Changes
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
        for (int r=0; r<NUM_ROWS; r++) {
            for (int c=0; c<NUM_COLS; c++) {
                cells2[r][c].setImageResource(android.R.color.transparent);
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int score = preferences.getInt(playerName, 0);
        final int p1score = (board2.turn == Board_1088.Turn.FIRST) ? ( getPlayerOneScore() + 1 ) : getPlayerOneScore() ;
        final int p2score = (board2.turn == Board_1088.Turn.FIRST) ? getPlayerTwoScore() : ( getPlayerTwoScore() + 1 ) ;
        String title = "";
        String print = "";

        // If no more rounds, then declare winner or tie
        if(getNumberRounds() <= 1) {
            if(p1score > p2score) {
                title += getPlayerOneName() + " is the winner!!\n";
            }
            else if(p1score < p2score) {
                title += getPlayerTwoName() + " is the winner!!\n";
            }
            else {
                title += getPlayerOneName() + " and " + getPlayerTwoName() + " have tied!!\n";
            }
        }
        else {
            title += "Score!";
        }

        alertDialog.setTitle(title);
        print += playerName + "'s total score is now: " + score + "!\n";
        // Print how many rounds the each player has won
        print += getPlayerOneName() + " has won " + p1score + " rounds!\n";
        print += getPlayerTwoName() + " has won " + p2score + " rounds!\n";
        print +=  "Rounds remaining: " + (getNumberRounds()-1);
        alertDialog.setMessage(print);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                // Check to see if multiple rounds is in progress, if so start new game activity
                // with same info and current-1 number of rounds. (switch P1 and P2 each round?)
                if (getNumberRounds() > 1) {
                    Intent myIntent = new Intent(GameActivity_1088.this, GameActivity_1088.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("PLAYER1NAME", getPlayerTwoName());
                    bundle.putString("PLAYER2NAME", getPlayerOneName());
                    bundle.putInt("NUMBEROFROUNDS", (getNumberRounds()-1) );
                    // If player 1 won, then add one to their score
                    bundle.putInt("PLAYER2SCORE", p1score );
                    // If player 2 won, then add one to their score
                    bundle.putInt("PLAYER1SCORE", p2score );
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                }
                else {
                    // No rounds left, go back to choose game/names page
                    Intent myIntent = new Intent(GameActivity_1088.this, Choose_Name_2P.class);
                    startActivity(myIntent);
                }
            }
        });
        alertDialog.show();
    }

    private String getPlayerOneName() {
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("PLAYER1NAME", "Player 1");
        return name;
    }

    private String getPlayerTwoName() {
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("PLAYER2NAME", "Player 2");
        return name;
    }

    // Obtains number of rounds desired from extras
    private Integer getNumberRounds() {
        Bundle extras = getIntent().getExtras();
        Integer rounds = extras.getInt("NUMBEROFROUNDS", 1);
        return rounds;
    }

    private Integer getPlayerOneScore() {
        Bundle extras = getIntent().getExtras();
        Integer score = extras.getInt("PLAYER1SCORE", 0);
        return score;
    }

    private Integer getPlayerTwoScore() {
        Bundle extras = getIntent().getExtras();
        Integer score = extras.getInt("PLAYER2SCORE", 0);
        return score;
    }
    // End of Avery's new functions
}
