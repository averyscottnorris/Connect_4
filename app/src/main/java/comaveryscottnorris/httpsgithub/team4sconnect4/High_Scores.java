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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

        // Get all scores from SharedPreferences
        Map<String,?> unsortedScores = preferences.getAll();

        // Put them in a new map as Integers
        Map<String,Integer> newMap = new HashMap<String, Integer>();

        for (Map.Entry<String,?> entry : unsortedScores.entrySet()) {
            newMap.put(entry.getKey(), (Integer) entry.getValue());
        }

        // Sort scores by value in descending order
        MyComparator comparator = new MyComparator(newMap);
        Map<String,Integer> scores = new TreeMap(comparator);
        scores.putAll(newMap);

        // Print first 5 values to a text field in the High Scores activity
        int i = 0;
        for(Map.Entry<String,?> entry : scores.entrySet()) {
            if(i >= 5) {
                break;
            }
            else {
                textview.append(entry.getKey() + "  -  " + entry.getValue().toString() + "\n");
                ++i;
            }
        }

        // Button to reset all saved high scores
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

// This class courtesy of StackOverflow user "amicngh"
class MyComparator implements Comparator {

    Map map;

    public MyComparator(Map map) {
        this.map = map;
    }

    public int compare(Object o1, Object o2) {
        // Cast ? to Integer (as the scores should be Integers)
        int compare = ((Integer) map.get(o2)).compareTo((Integer) map.get(o1));
        // if the values are the same
        if(compare == 0) {
            //add them both
            return 1;
        }
        return compare;

    }
}