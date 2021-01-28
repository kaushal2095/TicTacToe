package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private GridAdapter gridAdapter;
    private TextView turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView=findViewById(R.id.gridView);
        turn=findViewById(R.id.turn);

        gridAdapter=new GridAdapter(MainActivity.this,gridView.getWidth(),gridView.getHeight(),turn);
        gridView.setAdapter(gridAdapter);

    }
}
