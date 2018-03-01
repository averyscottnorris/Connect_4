package comaveryscottnorris.httpsgithub.team4sconnect4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {


    public Button aboutButton;
    public Button helpButton;
    public Button highscoreButton;
    Button singlePlayerButton;
    Button doublePlayerButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  init();
        aboutButton= findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy=new Intent(MainActivity.this,About.class);
                startActivity(toy);

            }
        });


        helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy=new Intent(MainActivity.this,Help.class);
                startActivity(toy);
            }
        });

        highscoreButton= findViewById(R.id.highscoreButton);
        highscoreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent toy=new Intent(MainActivity.this, High_Scores.class);
                startActivity(toy);
            }
        });

        singlePlayerButton= findViewById(R.id.singlePlayer);
        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy=new Intent(MainActivity.this,Choose_Name_1P.class);
                startActivity(toy);

            }
        });

        doublePlayerButton= findViewById(R.id.doublePlayer);
        doublePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy=new Intent(MainActivity.this,Choose_Name_2P.class);
                startActivity(toy);

            }
        });

    }







}
