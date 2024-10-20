package com.e2240438.wordguess;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

public class WordGuessActivity extends AppCompatActivity {

    private String ranWords="Mango";
    private double point=100;
    private boolean wordLenGet=false;
    private int guessTime,wordNumber,timeElapsed=0;
    TextView disName,disMark,disPoint,timeSet;
    EditText getUserGuessWord;
    Button smilerWord,submitBtn,getLen;
    private Timer timer;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_guess);
        startTimer();
        //text box
        disName = findViewById(R.id.txtDisplayName);
        disMark = findViewById(R.id.txtDisplayMarks);
        disPoint=findViewById(R.id.txtDisplayPoints);
        getUserGuessWord=findViewById(R.id.txtGuessWord);
        timeSet=findViewById(R.id.txtDisplayTime);
        //button
        smilerWord=findViewById(R.id.btnRhymes);
        submitBtn=findViewById(R.id.btnSubmitGuessWord);
        getLen=findViewById(R.id.btnGetLength);

        //display open activity
        disName.setText(MainActivity.player.getName());
        disMark.setText(String.valueOf(MainActivity.player.getMark()));
        disPoint.setText(String.valueOf(point));

        //first randem word genarate
        //ranWordGenarate();

        //enter word and click submit button
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get guess word varible after cut emptry space
                String wordGuess = getUserGuessWord.getText().toString().trim();

                //one word guess wroung subtract 10 point. so check have point 10 or grater than
                if (point >=10) {
                    //edit text can not be submit emptry
                    if (!wordGuess.isEmpty()) {
                        if (wordGuess.equalsIgnoreCase(ranWords)) {
                            //correct guess mark and 10 and give new word
                            double newPoint = MainActivity.player.getMark() + 10;
                            MainActivity.player.setPoint(newPoint);
                            disMark.setText(String.valueOf(MainActivity.player.getMark()));
                            Toast.makeText(WordGuessActivity.this, "Correct Guess!", Toast.LENGTH_SHORT).show();
                            wordNumber++;
                            //get anther randem number when user give correct word
                            ranWordGenarate();
                        } else {
                            //incorrect guess point will subtract 10
                            guessTime++;
                            //five time guess after aler can get Similar word App = cap ,map ,nap
                            if(guessTime>=5){
                                smilerWord.setText("Smilar word(enable)");
                            }
                            point -= 10;
                            disPoint.setText(String.valueOf(point));
                            Toast.makeText(WordGuessActivity.this, "Guess word is invalid", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //if user give emtry value
                        Toast.makeText(WordGuessActivity.this, "Please enter a guess word", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //10 time give player cant guess word give anther word but not addition mark
                    Toast.makeText(WordGuessActivity.this, "No more points! Let try new word again.", Toast.LENGTH_SHORT).show();
                    point = 100;
                    disPoint.setText(String.valueOf(point));
                    guessTime = 0;
                    wordLenGet=false;
                    ranWordGenarate();
                    smilerWord.setText("Smilar word(disable)");
                }


            }
        });

        //player can one time get word length but subtract 5 point
        getLen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!wordLenGet) && point>=5){
                    Toast.makeText(WordGuessActivity.this, "Word length: "+ranWords.length(), Toast.LENGTH_SHORT).show();
                    point-=5;
                    disPoint.setText(String.valueOf(point));
                    getLen.setText("Get Word Length(disable)");
                    wordLenGet=true;
                }

            }
        });


        //after guess 5 time can get similar word but subtract 5 point
        smilerWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(guessTime>=5){
                    //String tip = getSimilarWord(ranWords);
                    fetchRhymingWords();
                    point-=5;
                    disPoint.setText(String.valueOf(point));
                    //Toast.makeText(WordGuessActivity.this, tip, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //genarate similar word method
    private void fetchRhymingWords() {
        String apiUrl = "https://api.api-ninjas.com/v1/rhyme?word=" + ranWords;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() > 0) {
                            String tip = jsonArray.getString(0);
                            //showResult("Tip: One rhyming word is '" + tip + "'.");
                            Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
                        } else {
                           // showResult("No rhyming words found.");
                            Toast.makeText(this, "No rhyming words found.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //showResult("Error fetching rhymes.");
                        Toast.makeText(this, "Error fetching rhymes..", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    //showResult("Error fetching rhymes: " + error.getMessage());
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Api-Key", "zZ21Eu8d7K+C4XQMp0EHzg==OL7tejgzcAMMVE8BDM");
                /* Replace with your API key  zZ21Eu8d7K+C4XQMp0EHzg==OL7tejgzcAMMVE8BDM  */
                return headers;
            }
        };

        queue.add(stringRequest);
    }

    //randem word genarate method
    private void ranWordGenarate() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://random-word-api.herokuapp.com/word";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String word = response.getString(0); // Get the first word from the array
                        ranWords = word.trim();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error processing response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error fetching word: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        queue.add(jsonArrayRequest);
    }
    private void startTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // Increment time elapsed
                timeElapsed++;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timeSet.setText(String.valueOf(timeElapsed)+" seconds");
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the timer when the activity is destroyed
        if (timer != null) {
            timer.cancel();
        }
    }
}