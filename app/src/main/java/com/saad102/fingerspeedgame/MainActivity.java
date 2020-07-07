package com.saad102.fingerspeedgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaring the variables

    private TextView timerTextView;
    private TextView textViewThousand;
    private Button tapTapButton;


    private CountDownTimer countDownTimer;

    private long initialCountDownInMillis = 60000;
    private int timerInterval = 1000;

    private   int remainingTime = 10;

    private int aThousand = 300;

    private final String REMAINING_TIME = "remaining time key";
    private final String A_THOUSAND = "a thousand key";


    @Override
    protected void onDestroy() {
        super.onDestroy();

        showToast("onDestroyed method is called");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(REMAINING_TIME, remainingTime);
        outState.putInt(A_THOUSAND, aThousand) ;
        showToast(remainingTime+"remaining time in save instance");
        countDownTimer.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //debugging the on create

        showToast("This is onCreate");


        timerTextView = findViewById(R.id.txt_timer);
        textViewThousand = findViewById(R.id.txt_a_thousand);
        tapTapButton = findViewById(R.id.btn_tap);

        textViewThousand.setText(aThousand + "");


        if (savedInstanceState != null){
            remainingTime = savedInstanceState.getInt(REMAINING_TIME);
            aThousand = savedInstanceState.getInt(A_THOUSAND);
            showToast(remainingTime + "");
            restoreTheGame();
        }


        //Onclick of Tap Tap button



        tapTapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                aThousand --;

                textViewThousand.setText(aThousand + "");

                if (aThousand <= 0 && remainingTime > 0){


                    Toast.makeText(MainActivity.this , "You have won the game", Toast.LENGTH_SHORT).show();


                    showAlert("Congrats!", "would you like to reset the game?");


                }

            }
        });



        //showing value to aThousand

        //countdown when the app starts

        if(savedInstanceState == null){


            countDownTimer = new CountDownTimer(initialCountDownInMillis, timerInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTime = (int)millisUntilFinished/1000;
                    timerTextView.setText(remainingTime + "");





                }

                @Override
                public void onFinish() {

                    Toast.makeText(MainActivity.this , "You have lost the game", Toast.LENGTH_SHORT).show();


                    showAlert("Very Disapointing", "would you like to try again?");



                }
            };
            countDownTimer.start();
        }













    }

    private void  restoreTheGame() {

        int restoredRemainingTime = remainingTime;
        int restoredAThousand = aThousand;
        timerTextView.setText(restoredRemainingTime + "");
        textViewThousand.setText(restoredAThousand + "");



        countDownTimer = new CountDownTimer((long)remainingTime * 1000, timerInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = (int)millisUntilFinished/1000;
                timerTextView.setText(remainingTime + "");


            }

            @Override
            public void onFinish() {
                showAlert("Very Disapointing", "would you like to start again?");

            }
        };
        countDownTimer.start();

    }


    private void resetTheGame(){

        //nullify the countdown to reset

        if(countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer = null;
            aThousand = 300;
            textViewThousand.setText(aThousand + "");
            timerTextView.setText(remainingTime + "");
            countDownTimer = new CountDownTimer(initialCountDownInMillis , timerInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTime = (int)millisUntilFinished/1000;
                    timerTextView.setText(remainingTime + "");




                }

                @Override
                public void onFinish() {



                    Toast.makeText(MainActivity.this , "You have lost the game", Toast.LENGTH_SHORT).show();


                    showAlert("Very Disapointing", "would you like to try again?");



                }
            };
            countDownTimer.start();

        }


    }
    private void showAlert(String title, String message, boolean win){

        final boolean has_won = win;

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
//set title
                .setTitle(title)
////set message
                .setMessage(message)
//set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        if (has_won == true){

                        }

                        resetTheGame();
                    }
                })
                .show();
        alertDialog.setCancelable(false);

    }
    private void showAlert(String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
//set title
                .setTitle(title)
////set message
                .setMessage(message)
//set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        resetTheGame();
                    }
                })
                .show();
        alertDialog.setCancelable(false);

    }

    private void showToast(String message){
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}