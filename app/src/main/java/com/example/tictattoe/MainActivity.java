package com.example.tictattoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnXO[][]=new Button[3][3];
    private int player1Points;
    private int player2Points;
    private boolean player1Turn=true;

    //since we have 9 buttons ie[3][3] so if we dont get any player who has won than it will direct say as DRAW
    private int roundCount;

    //use to view player points
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1=findViewById(R.id.TVPlayer1Score);
        textViewPlayer2=findViewById(R.id.TVPlayer2Score);

        for(int i=0;i<3;i++)
        {for(int j=0;j<3;j++)
            {
                String btnId="btn_"+i+j;
                int resId=getResources().getIdentifier(btnId,"id",getPackageName());
                btnXO[i][j]=findViewById(resId);
                btnXO[i][j].setOnClickListener(this);
            }
        }

        Button btnReset=findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });


    }

    @Override
    public void onClick(View v){
           if(!((Button)v).getText().toString().equals(""))
           {
               return;
           }

           if(player1Turn){
               ((Button)v).setText("X");
           }else{
               ((Button)v).setText("0");
           }

           roundCount++;

           if(checkForWin()){
               if(player1Turn){
                   player1Win();
               }else{
                   player2Win();
               }
           }
           else if(roundCount==9){
               draw();
           }
           else{
               player1Turn=!player1Turn;
           }

    }

private boolean checkForWin(){
        String field[][]=new String[3][3];
        //getting Button text[X][0] to field String
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j]=btnXO[i][j].getText().toString();
            }
        }

        //checking rows
        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2]) && !field[i][0].equals("") ){
                return true;
            }
        }
//checking columns
    for(int i=0;i<3;i++){
        if(field[0][i].equals(field[1][i])
                && field[0][i].equals(field[2][i]) && !field[0][i].equals("") ){
            return true;
        }
    }
//checking diagonal--1
for(int i=0;i<3;i++){
    if(field[0][0].equals(field[1][1])
            && field[0][0].equals(field[2][2]) && !field[0][0].equals("") ){
        return true;
    }
}
//checking diagonal--2
    for(int i=0;i<3;i++){
        if(field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0]) && !field[0][2].equals("") ){
            return true;
        }
    }

        return false;
}

// player 1 win
private void player1Win(){
        player1Points++;
        Toast.makeText(this,"Player 1 Wins ! ",Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
}
// player 2 win
private void player2Win(){
    player2Points++;
    Toast.makeText(this,"Player 2 Wins ! ",Toast.LENGTH_LONG).show();
    updatePointsText();
    resetBoard();
}

//if the game draws
private void draw(){
        Toast.makeText(this," Draw ! ",Toast.LENGTH_LONG).show();
        resetBoard();
}

//to reset the board ie to reset the text of button to blank space
private void resetBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                btnXO[i][j].setText("");
            }
        }
        roundCount=0;
        player1Turn=true;
}
//update player points
private void updatePointsText(){
        textViewPlayer1.setText("Player 1 : "+ player1Points);
        textViewPlayer2.setText("Player 2 : "+ player2Points);
}

//resetting the game by resetting the board and resetting the player points
private void resetGame(){
        player1Points=0;
        player2Points=0;
        updatePointsText();
        resetBoard();
}
//use for orientation change
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount",roundCount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1Turn",player1Turn);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount=savedInstanceState.getInt("roundCount");
        player1Points=savedInstanceState.getInt("player1Points");
        player2Points=savedInstanceState.getInt("player2Points");
        player1Turn=savedInstanceState.getBoolean("player1Turn");

    }
}
