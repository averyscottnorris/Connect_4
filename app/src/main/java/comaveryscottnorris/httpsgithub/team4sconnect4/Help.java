package comaveryscottnorris.httpsgithub.team4sconnect4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Help extends AppCompatActivity {

    public Button backButton;


    public void init() {
        backButton = (Button) findViewById(R.id.backtoMenu);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(Help.this, MainActivity.class);
                startActivity(toy);

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        init();
    }
}
