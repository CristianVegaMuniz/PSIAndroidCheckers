
package com.example.checkers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    private int easy = 0;
    private int normal = 1;
    private int hard = 2;
    private CheckBox cbBlacks, cbWhites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cbBlacks = (CheckBox) findViewById(R.id.checkBox_blacks);
        cbWhites = (CheckBox) findViewById(R.id.checkBox_whites);

        cbBlacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbWhites.isChecked()){
                    cbWhites.setChecked(false);
                } else {
                    cbBlacks.setChecked(true);
                }
            }
        });

        cbWhites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbBlacks.isChecked()){
                    cbBlacks.setChecked(false);
                } else {
                    cbWhites.setChecked(true);
                }
            }
        });
    }

    public void PlayEasy(View view){
        Intent playActivity = new Intent(this, GameActivity.class);
        playActivity.putExtra("LEVEL", easy);
        if (cbWhites.isChecked()) {
            playActivity.putExtra("WHITES", true);
        }
        startActivity(playActivity);
    }

    public void PlayNormal(View view){
        Intent playActivity = new Intent(this, GameActivity.class);
        playActivity.putExtra("LEVEL", normal);
        if (cbWhites.isChecked()) {
            playActivity.putExtra("WHITES", true);
        }
        startActivity(playActivity);
    }

    public void PlayHard(View view){
        Intent playActivity = new Intent(this, GameActivity.class);
        playActivity.putExtra("LEVEL", hard);
        if (cbWhites.isChecked()) {
            playActivity.putExtra("WHITES", true);
        }
        startActivity(playActivity);
    }


}