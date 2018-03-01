package comaveryscottnorris.httpsgithub.team4sconnect4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

public class High_Scores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textview = findViewById(R.id.HighScoreDisplay);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        /*
        if(preferences.getAll() == null) {
            textview.append("No Scores!");
        }*/

        Map<String,?> scores = preferences.getAll();
        for(Map.Entry<String,?> entry : scores.entrySet()) {
            textview.append(entry.getKey() + "  -  " + entry.getValue().toString() + "\n");
        }

        Button button = findViewById(R.id.resetscoresButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(High_Scores.this);
                preferences.edit().clear().apply();
                Intent intent = new Intent(High_Scores.this, High_Scores.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
