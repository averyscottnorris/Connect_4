package comaveryscottnorris.httpsgithub.team4sconnect4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {


    public Button aboutButton;
    public Button helpButton;
    Button singlePlayerButton;
    Button doublePlayerButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  init();
        aboutButton=(Button) findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy=new Intent(MainActivity.this,About.class);
                startActivity(toy);

            }
        });


        helpButton =(Button) findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy=new Intent(MainActivity.this,Help.class);
                startActivity(toy);

            }
        });
        singlePlayerButton=(Button) findViewById(R.id.singlePlayer);
        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy=new Intent(MainActivity.this,Choose_Name_1P.class);
                startActivity(toy);

            }
        });

        doublePlayerButton=(Button) findViewById(R.id.doublePlayer);
        doublePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy=new Intent(MainActivity.this,Choose_Name_2P.class);
                startActivity(toy);

            }
        });

    }







}
