package com.andorid.freemind.jokewizardandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.JokeTreasure;

public class JokeActivity extends AppCompatActivity {

    TextView jokeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        jokeView = (TextView)findViewById(R.id.jokeTextView);

        com.example.JokeTreasure js = new JokeTreasure();
       // String jokefromJava = js.jokeSource();

        Intent jokeIntent = getIntent();



        String jokefromJava = jokeIntent.getExtras().getString("jokefrommaf");
        jokeView.setText(jokefromJava);
       Log.d("value of joke ", jokefromJava);



    }


}
