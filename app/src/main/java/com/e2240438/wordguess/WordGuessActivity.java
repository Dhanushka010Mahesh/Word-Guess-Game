package com.e2240438.wordguess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WordGuessActivity extends AppCompatActivity {

    private String [] words={"Dhanushka","Mahesh","Eranga","Jayarathna"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_guess);


        TextView disName = findViewById(R.id.txtDisplayName);
        TextView disPoint = findViewById(R.id.txtDisplayPoint);
        EditText getUserGuessWord=findViewById(R.id.txtGuessWord);

        disName.setText(MainActivity.player.getName());
        disPoint.setText(String.valueOf(MainActivity.player.getPoint()));

        Button submitBtn=findViewById(R.id.btnSubmitGuessWord);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordGuess=(getUserGuessWord.getText().toString()).trim();

                boolean checkValid=false;
                for (int i=0;i<words.length;i++){
                    if (wordGuess.equalsIgnoreCase(words[i])){
                        checkValid=true;

                        break;
                    }
                }

                if (checkValid){
                    Intent intent = new Intent(WordGuessActivity.this, WinActivity.class);
                    startActivity(intent);
                    Toast.makeText(WordGuessActivity.this, "Word is "+wordGuess, Toast.LENGTH_SHORT).show();
                }else {
                    double newPoint=MainActivity.player.getPoint()-10;
                    MainActivity.player.setPoint(newPoint);
                    disPoint.setText(String.valueOf(MainActivity.player.getPoint()));
                    Toast.makeText(WordGuessActivity.this, "Guess word is invalid", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}