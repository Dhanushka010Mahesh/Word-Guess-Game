package com.e2240438.wordguess;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class WordGuessActivity extends AppCompatActivity {

    private String ranWords="m";
    private double point=100;
    private boolean wordLenGet=false;
    private int guessTime,wordNumber=0;
    TextView disName,disMark,disPoint;
    EditText getUserGuessWord;
    Button smilerWord,submitBtn,getLen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_guess);
        //text box
        disName = findViewById(R.id.txtDisplayName);
        disMark = findViewById(R.id.txtDisplayMarks);
        disPoint=findViewById(R.id.txtDisplayPoints);
        getUserGuessWord=findViewById(R.id.txtGuessWord);
        //button
        smilerWord=findViewById(R.id.btnRhymes);
        submitBtn=findViewById(R.id.btnSubmitGuessWord);
        getLen=findViewById(R.id.btnGetLength);

        //display open activity
        disName.setText(MainActivity.player.getName());
        disMark.setText(String.valueOf(MainActivity.player.getMark()));
        disPoint.setText(String.valueOf(point));

        //first randem word genarate
        ranWords=ranWordGenarate();

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
                            ranWords=ranWordGenarate();
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
                    ranWords=ranWordGenarate();
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
                    String tip = getSimilarWord(ranWords);
                    point-=5;
                    disPoint.setText(String.valueOf(point));
                    Toast.makeText(WordGuessActivity.this, tip, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //genarate similar word method
    public static String getSimilarWord(String word){
        String apiKey = "";  /* Replace with your API key
        zZ21Eu8d7K+C4XQMp0EHzg==OL7tejgzcAMMVE8BDM  */
        String wordToRhyme = word;
        String wordArr="";

        try {
            URL url = new URL("https://api.api-ninjas.com/v1/rhyme?word=" + wordToRhyme);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Set the API key in the request header
            connection.setRequestProperty("X-Api-Key", apiKey);

            // Get the response from the API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            // Read the response line by line
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close the connections
            in.close();
            connection.disconnect();

            // Print the response (it will be in JSON format)
            wordArr=content.toString();
            System.out.println("Rhyming words: " +wordArr );

            // You can parse the response here if needed (e.g., using a JSON library)

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return wordArr;
        }
    }

    //randem word genarate method
    public String ranWordGenarate(){
        String randomWord="";
        try {
            URL url = new URL("https://random-word-api.herokuapp.com/word");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response from the API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            // Read the response line by line
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close the connections
            in.close();
            connection.disconnect();

            // Print the random word (it's returned as an array, so we need to remove the brackets)
            randomWord = content.toString();
            randomWord = randomWord.substring(2, randomWord.length() - 2);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return randomWord;
        }

    }
}