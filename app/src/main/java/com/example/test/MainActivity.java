package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout cont;
    public static Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont = findViewById(R.id.layout);

    }

    public void generateWarmColourOnClick(View view) {
        cont.setBackgroundColor(Color.parseColor(generateWarmColour()));
    }

    public void generateColdColourOnClick(View view) {
        cont.setBackgroundColor(Color.parseColor(generateColdColour()));
    }

    private static String generateWarmColour() {
        int red = r.nextInt(127) + 128;
        int green = r.nextInt(128);
        int blue = r.nextInt(red);

        return String.format("#%02x%02x%02x", red, green, blue);
    }

    private static String generateColdColour() {
        int blue = r.nextInt(127) + 128;
        int green = r.nextInt(255);
        int red = r.nextInt(blue);
        return String.format("#%02x%02x%02x", red, green, blue);
    }
}