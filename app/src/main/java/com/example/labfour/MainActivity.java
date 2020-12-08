package com.example.labfour;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public WordGame thisGame = new WordGame();
    public com.example.labthree.WordList unscrambledList = new com.example.labthree.WordList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public int GenerateNumber(int minValue, int maxValue) {
        Random numGen = new Random();
        int randNum = numGen.nextInt(maxValue - minValue) + minValue;
        return randNum;
    }

    public void shuffle(StringBuilder sb) {
        Random rand = new Random();
        for (int i = sb.length() - 1; i > 1; i--) {
            int swapWith = rand.nextInt(i);
            char tmp = sb.charAt(swapWith);
            sb.setCharAt(swapWith, sb.charAt(i));
            sb.setCharAt(i, tmp);
        }
    }

    private void SetUpBG(){
        // Get String from random index in WordList.java
        thisGame.plaintextWord = unscrambledList.unscrambledWordList[GenerateNumber(0,thisGame.arrLength)];
        String plaintextString = thisGame.plaintextWord;
        //Scramble the string
        StringBuilder scrambledString = new StringBuilder(plaintextString);
        shuffle(scrambledString);
        thisGame.scrambledWord = scrambledString.toString();
        thisGame.triesLeft = 3;
    }

    public void StartGame(View v) {
        SetUpBG();

        TextView DisplayText = findViewById(R.id.viewDisplay);
        DisplayText.setText("Unscramble: " + thisGame.scrambledWord);

        Button SubmitButton = findViewById(R.id.buttonSubmit);
        SubmitButton.setText("Submit Guess (" + thisGame.triesLeft + ")");

        EditText guessedWordValue = findViewById(R.id.txtGuess);
        guessedWordValue.setText("");

        TextView displayResults = findViewById(R.id.viewDisplayResults);
        displayResults.setText("");
    }

    @SuppressLint("SetTextI18n")
    public void PlayGame(View v) {
        EditText guessValue = findViewById(R.id.txtGuess);
        String guessedWord = guessValue.getText().toString();

        Button SubmitButton = findViewById(R.id.buttonSubmit);
        TextView displayResults = findViewById(R.id.viewDisplayResults);

        if (guessedWord.equalsIgnoreCase(thisGame.plaintextWord)){
            displayResults.setText("\uD83C\uDF1F Correct! \uD83C\uDF1F");
        } else {
            thisGame.triesLeft = thisGame.triesLeft - 1;
            String[] responseArray = {"Not quite it", "YOU DID I- wait, no you didn't", "That might be it, why don't you try it again?", "Nope, not that one", "Nuh-uh", "Definitely not that one", "Not even close", "Close but no cigar", "Are you even trying?", "Obviously it isn't that one"};
            String response = responseArray[GenerateNumber(0,9)];
            if (thisGame.triesLeft <= 0){
                SubmitButton.setText("Submit Guess (0)");
                displayResults.setText("Game Over :(\nThe word was: " + thisGame.plaintextWord);
            } else {
                SubmitButton.setText("Submit Guess (" + thisGame.triesLeft + ")");
                displayResults.setText(response + "\nYour last guess: " + guessedWord);
            }
        }
    }
}