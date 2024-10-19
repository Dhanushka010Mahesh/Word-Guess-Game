package com.e2240438.wordguess;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BestRateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_rate);


        TextView dataShow=findViewById(R.id.txtDisplayRate);
        String data=dataShow.getText().toString();
        dataShow.setText(Controller.toStringList());

        Toast.makeText(BestRateActivity.this, "Welcome To "+MainActivity.player.getName(), Toast.LENGTH_SHORT).show();
        Button backBtn=findViewById(R.id.btnBackToMenu);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button resetBtn=findViewById(R.id.btnReset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.listClear();
            }
        });
    }
}