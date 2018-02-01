package comaveryscottnorris.httpsgithub.team4sconnect4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class Choose_Name_2P extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__name_2_p);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set objects for play button and radioGroup
        Button button = findViewById(R.id.playButton);
        final RadioGroup radioGroup = findViewById(R.id.GridList);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Determine which size was selected;
                int selectedButtonID = radioGroup.getCheckedRadioButtonId();
                Intent myIntent;

                // Set columns and rows based on size selected
                if(selectedButtonID == R.id.radioButtonSize1) {
                    myIntent = new Intent(Choose_Name_2P.this, GameActivity.class);
                }
                else if(selectedButtonID == R.id.radioButtonSize2) {
                    myIntent = new Intent(Choose_Name_2P.this, GameActivity.class);
                }
                else {
                    myIntent = new Intent(Choose_Name_2P.this, GameActivity.class);
                }

                //Save player names from text boxes
                EditText player1Name = findViewById(R.id.player1_2P);
                EditText player2Name = findViewById(R.id.player2_2P);

                // Add information to send to new activity
                myIntent.putExtra("player1Name", player1Name.toString());
                myIntent.putExtra("player2Name", player2Name.toString());
                startActivity(myIntent);
            }
        });
    }

}
