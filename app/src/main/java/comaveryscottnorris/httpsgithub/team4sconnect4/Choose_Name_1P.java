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
                Intent myIntent;
                Bundle bundle = new Bundle();

                // Set columns and rows based on size selected
                if(selectedButtonID == R.id.radioButtonSize1) {
                    myIntent = new Intent(Choose_Name_1P.this, GameActivity.class);
                }
                else if(selectedButtonID == R.id.radioButtonSize2) {
                    myIntent = new Intent(Choose_Name_1P.this, ai.class);
                }
                else {
                    myIntent = new Intent(Choose_Name_1P.this, GameActivity_1088.class);
                }
                EditText player1Name = findViewById(R.id.player1_1P);

                // Add information to send to new activity
                bundle.putString("PLAYER1NAME", String.valueOf(player1Name.getText()));
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
    }
}
