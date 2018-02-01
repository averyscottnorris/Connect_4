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

public class Choose_Name_1P extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_name_1p);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set objects for play button and radioGroup
        Button button = findViewById(R.id.playButton);
        final RadioGroup radioGroup = findViewById(R.id.GridList);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Determine which size was selected;
                int selectedButtonID = radioGroup.getCheckedRadioButtonId();
                int columns;
                int rows;

                // Set columns and rows based on size selected
                if(selectedButtonID == R.id.radioButtonSize1) {
                    columns = 7;
                    rows = 6;
                }
                else if(selectedButtonID == R.id.radioButtonSize2) {
                    columns = 8;
                    rows = 7;
                }
                else {
                    columns = 10;
                    rows = 8;
                }
                EditText player1Name = findViewById(R.id.player1_1P);
                Intent myIntent = new Intent(Choose_Name_1P.this, PLAYGAMEPLACEHOLDER.class);

                // Add information to send to new activity
                myIntent.putExtra("player1Name", player1Name.toString());
                myIntent.putExtra("columns",  columns);
                myIntent.putExtra("rows", rows);
                startActivity(myIntent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
