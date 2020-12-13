package com.example.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //Map m = new Map();
        boolean fin = false;
        System.out.println("\n**********************************");
        System.out.println("         Starting match!");
        System.out.println("*********************************\n");
        //m.showMap();
        System.out.println("\n       You are Player 1 (blacks)");

        String s = "";
    }



}