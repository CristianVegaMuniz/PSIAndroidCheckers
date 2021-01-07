
package com.example.checkers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private int easy = 0;
    private int normal = 1;
    private int hard = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void PlayEasy(View view){
        Intent playActivity = new Intent(this, GameActivity.class);
        playActivity.putExtra("LEVEL", easy);
        startActivity(playActivity);
    }

    public void PlayNormal(View view){
        Intent playActivity = new Intent(this, GameActivity.class);
        playActivity.putExtra("LEVEL", normal);
        startActivity(playActivity);
    }

    public void PlayHard(View view){
        Intent playActivity = new Intent(this, GameActivity.class);
        playActivity.putExtra("LEVEL", hard);
        startActivity(playActivity);
    }


}