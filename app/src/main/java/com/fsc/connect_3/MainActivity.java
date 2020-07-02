package com.fsc.connect_3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //0: X and 1: O and 2: empty
    int activePlayer = 0, count = 0;

    boolean gameActivity = true , flag = true;

    int[] currentStatus = {2, 2, 2, 2, 2, 2, 2, 2, 2};        //2: place is empty

    int[][] winningStatus = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void gameOver(String win) {

        MediaPlayer winning = MediaPlayer.create(this,R.raw.winning);
        winning.start();

        TextView winnerTextview = (TextView) findViewById(R.id.winnerTextView);
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        winnerTextview.setText(win);
        winnerTextview.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.VISIBLE);
    }

    public void dropin1(View view) {

        ImageView counter = (ImageView) view;

        int tapCounter = Integer.parseInt(counter.getTag().toString());
        if (currentStatus[tapCounter] !=2) {
            MediaPlayer invlaid = MediaPlayer.create(this,R.raw.invlaid);
            invlaid.start();
        }

        if (currentStatus[tapCounter] == 2 && gameActivity) {

            MediaPlayer move = MediaPlayer.create(this,R.raw.move);
            move.start();

            currentStatus[tapCounter] = activePlayer;

            count += 1;

            if (activePlayer == 0) {

                counter.setTranslationY(-1000);
                counter.setImageResource(R.drawable.star);
                counter.animate().translationYBy(1000).setDuration(500);
                activePlayer = 1;
            } else {

                counter.setTranslationY(1000);
                counter.setImageResource(R.drawable.circle);
                counter.animate().translationYBy(-1000).setDuration(500);
                activePlayer = 0;
            }
            for (int[] winningStat : winningStatus) {
                if (currentStatus[winningStat[0]] == currentStatus[winningStat[1]] && currentStatus[winningStat[1]] == currentStatus[winningStat[2]] && currentStatus[winningStat[0]] != 2) {

                    gameActivity = false;

                    String message;
                    if (activePlayer == 1) {
                        message = "Star has win";
                        flag = false;
                    }
                    else {
                        message = "Circle has win";
                        flag = false;
                    }
                    gameOver(message);
                }
                else if(count >= 9 && flag)
                    gameOver("Match Draw");
            }
        }
    }

    public  void playAgain(View view) {

        TextView winnerTextview = (TextView) findViewById(R.id.winnerTextView);

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        GridLayout myGlayout = (GridLayout) findViewById(R.id.gridLayout);

        winnerTextview.setVisibility(View.INVISIBLE);

        playAgainButton.setVisibility(View.INVISIBLE);

        for (int i = 0; i<myGlayout.getChildCount();i++) {

            ImageView counter = (ImageView) myGlayout.getChildAt(i);

            counter.setImageDrawable(null);

        }
        activePlayer = 0;
        count = 0;

        gameActivity = true;

        for(int j = 0 ; j <currentStatus.length ; j++){

            currentStatus[j] = 2;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

