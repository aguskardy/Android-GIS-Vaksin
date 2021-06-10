package com.example.projectuas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivmap, ivlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivmap = findViewById(R.id.btnpeta);
        ivlist = findViewById(R.id.btnkontak);
        ivmap.setOnClickListener(this);
        ivlist.setOnClickListener(this);

    }

        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnpeta:
            Intent pindahpeta = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(pindahpeta);
            break;
            case R.id.btnkontak:
                Intent pindahkontak = new Intent(MainActivity.this, KontakActivity.class);
                startActivity(pindahkontak);
                break;
        }

    }
}