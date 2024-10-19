package com.e2240438.wordguess;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    Button myButton; // next button in open page

    EditText txtUserNameFirst;

    public static Player player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButton= findViewById(R.id.btnSave);
        txtUserNameFirst=findViewById(R.id.txtUserName);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=txtUserNameFirst.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(MainActivity.this, "Invalid user name", Toast.LENGTH_SHORT).show();
                }else{
                    if(Controller.serchPlayer(userName)==null){
                        Controller.playerList.add(new Player(userName));
                        player=Controller.serchPlayer(userName);
                    }else{
                        player=Controller.serchPlayer(userName);
                    }

                    Toast.makeText(MainActivity.this, "Welcome To "+player.getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                }

            }
        });
    }



}