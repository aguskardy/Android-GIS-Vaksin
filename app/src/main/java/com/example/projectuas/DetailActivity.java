package com.example.projectuas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
TextView tvnama, tvalamat, tvjumlah;
public static String nama, alamat, jumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvnama = findViewById(R.id.txtnama);
        tvalamat = findViewById(R.id.txtalamat);
        tvjumlah = findViewById(R.id.txtjumlah);

        tvnama.setText(nama);
        tvalamat.setText(alamat);
        tvjumlah.setText(jumlah);


    }
}