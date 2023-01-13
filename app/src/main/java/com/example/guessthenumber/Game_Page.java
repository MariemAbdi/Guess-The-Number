package com.example.guessthenumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Time;
import android.text.method.KeyListener;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;
import java.util.Random;

public class Game_Page extends AppCompatActivity {
    int num=0, random, score,time, attempts;
    String nickname,difficulty;

    TableLayout tl;
    EditText guess;
    Button btn;
    private KeyListener listener;
    DatabaseHandler db = new DatabaseHandler(this);
    CountDownTimer mCountDownTimer ;
    View mView;
    TextView counttime,nametv,scoretv,difftv,timetv,attemptstv,attemptsCount;
    android.app.AlertDialog dialog,fdialog;
    long remainingTime;
    ImageView clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Make Page Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        tl = (TableLayout) findViewById(R.id.table_layout);
        btn = findViewById(R.id.submitguess);
        guess= findViewById(R.id.guess);
        listener = guess.getKeyListener();
        counttime=findViewById(R.id.counttime);
        attemptsCount=findViewById(R.id.attempts_counting);
        clear=findViewById(R.id.clear);

        guess.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(guess.getText().toString().isEmpty()){
                    clear.setVisibility(View.GONE);
                }else{
                    clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Generate A Random Number
        RandomNumber();

        nickname=getIntent().getStringExtra("NICKNAME");
        difficulty=getIntent().getStringExtra("DIFF");

        //Set Timer's Count Depending On The Difficulty Level
        switch(difficulty){
            case "HARD":
                time=30000;
                break;
            case "MEDIUM":
                time=60000;
                break;
            case"EASY":
                time=100000;
                break;
        }

        //Start Timer
        Timer();
    }



    //Start Timer
    public void Timer(){
        mCountDownTimer= new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counttime.setText(String.valueOf(String.format("%02d",(millisUntilFinished/1000)/60))+":"+String.format("%02d", (millisUntilFinished/1000)%60));
                remainingTime=millisUntilFinished;
                score--;
                if(score==0){
                    mCountDownTimer.onFinish();
                }
            }
            @Override
            public void onFinish() {
                counttime.setText("GAME OVER");
                showfail();
                btn.setEnabled(false);
                guess.setEnabled(false);
                guess.setKeyListener(null);
            }
        }.start();
    }

    //Stop Timer & Disable The Field & Button
    public void stopTimer(){
        mCountDownTimer.cancel();
        btn.setEnabled(false);
        guess.setFocusable(false);
        guess.setKeyListener(null);
        counttime.setText("00:00");
    }

    //Make A Random Number And Initialize The Fields
    public void RandomNumber(){
        //Generate random number
        random = new Random().nextInt(1001);
        showToast(String.valueOf(random));

        score=100;
        attempts=0;
        btn.setEnabled(true);
        guess.setText("");
        guess.setEnabled(true);
        guess.setFocusableInTouchMode(true);
        guess.setKeyListener(listener);
        attemptsCount.setText("0");
        tl.removeAllViews();
    }

    //Go Back To The Previous Page
    public void go_backHP(View view){
        Intent i = new Intent(getApplicationContext(), Home_Page.class);
        startActivity(i);
    }

    //Alert When User Fails
    public void showfail(){
        // create an AlertDialog
        android.app.AlertDialog.Builder fBuilder = new android.app.AlertDialog.Builder(Game_Page.this);

        // create a View to  inflate the layout we just created -  (resource file, we dont have a view group so we will leave it null - root= null
        View fView = getLayoutInflater().inflate(R.layout.activity_fail, null);

        fBuilder.setCancelable(false);

        //set the dialog view (mView) for the mBulider
        fBuilder.setView(fView);
        fdialog= fBuilder.create();
        fdialog.show();

        //hide keyboard
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); }
    }

    //Alert When User Wins
    public void show_result(View view){

            // create an AlertDialog
            android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(Game_Page.this);

            // create a View to  inflate the layout we just created -  (resource file, we dont have a view group so we will leave it null - root= null
            mView = getLayoutInflater().inflate(R.layout.activity_score_result, null);

            // Set up the input
            nametv = (TextView) mView.findViewById(R.id.name);
            scoretv = (TextView) mView.findViewById(R.id.result);
            timetv = (TextView) mView.findViewById(R.id.time_spent);
            attemptstv = (TextView) mView.findViewById(R.id.attempts);
            difftv = (TextView) mView.findViewById(R.id.difficult);

            nametv.setText(nickname);
            scoretv.setText(String.valueOf(score));
            timetv.setText(String.valueOf(Math.round((time - remainingTime) / 1000.0))+"s");
            attemptstv.setText(String.valueOf(attempts));
            difftv.setText(difficulty);

            mBuilder.setCancelable(false);


            //set the dialog view (mView) for the mBulider
            mBuilder.setView(mView);
            dialog= mBuilder.create();
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            //hide keyboard
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Game_Page.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    //Hide Success Alert & Restart Game
    public void replay_success(View view){
        dialog.hide();
        restart(view);
    }

    //Hide fail Alert & Restart Game
    public void replay_fail(View view){
        fdialog.hide();
        restart(view);
    }

    //Show Toast
    public void showToast(String msg){
        Toast.makeText(Game_Page.this, msg, Toast.LENGTH_SHORT).show();
    }

    //Go To The ScoreBoard Page
    public void showscroeboard(View view){
        Intent i = new Intent(getApplicationContext(), Scoreboard_Page.class);
        startActivity(i);
    }

    //Restart Timer & Start A New Game
    public void restart(View view){
        RandomNumber();
        mCountDownTimer.cancel();//stop timer
        Timer();//then restart it
    }

    public void clearGuess(View view){
        guess.setText("");
    }

    //Verify The Input Format & Show The Corresponding Toast
    public void submit(View view){
        String my_guess = guess.getText().toString();

        Time now = new Time();
        now.setToNow();
        String sTime = now.format("%H:%M");
        String dateTime=now.format("%d-%m-%y %H:%M");

        if(TextUtils.isEmpty(my_guess)){
            showToast("Please Type Your Guess");
        }else if(Integer.valueOf(my_guess)<0||Integer.valueOf(my_guess)>1000){
            showToast("The Number Is Between 0-1000");
        }
        else{
            attempts++;
            attemptsCount.setText(String.valueOf(attempts));
            guess.setText("");
            //create the first TableRow
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            num++;

            if(Integer.valueOf(my_guess)<random){
                //add 3 TextViews in TableRow1 and add TableRow1 to TableLayout
                tableRow.addView(getTextView(android.R.color.holo_green_dark, String.valueOf(num)));
                tableRow.addView(getTextView(android.R.color.holo_green_dark, my_guess));
                tableRow.addView(getTextView(android.R.color.holo_green_dark,sTime));
                showToast("The Number Is Greater Than "+my_guess);
                score--;
            }else if(Integer.valueOf(my_guess)>random){
                //add 3 TextViews in TableRow1 and add TableRow1 to TableLayout
                tableRow.addView(getTextView(android.R.color.holo_red_light, String.valueOf(num)));
                tableRow.addView(getTextView(android.R.color.holo_red_light, my_guess));
                tableRow.addView(getTextView(android.R.color.holo_red_light,sTime));
                showToast("The Number Is Lesser Than "+my_guess);
                score--;
            }else{
                //add 3 TextViews in TableRow1 and add TableRow1 to TableLayout
                tableRow.addView(getTextView(android.R.color.black, String.valueOf(num)));
                tableRow.addView(getTextView(android.R.color.black, my_guess));
                tableRow.addView(getTextView(android.R.color.black,sTime));
                //showToast("Congrats! The Number Is "+my_guess+"\nYour Score Is "+score);
                show_result(view);
                db.addScore(new Score(nickname, score, dateTime, String.valueOf(attempts), String.valueOf(Math.round((time - remainingTime) / 1000.0))+"s",difficulty));

                stopTimer();
            }
            tl.addView(tableRow);
        }



    }

    //TableLayout TextView Layout
    private TextView getTextView(int textColorId, String text){

        TextView tv = new TextView(this);
        tv.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        tv.setTextColor(ContextCompat.getColor(this, textColorId));
        tv.setText(text);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setTextSize(20);
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        tv.setPaddingRelative(padding, padding, padding, padding);

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1; //this is mandatory to get equal width between all columns in TableRow
        tv.setLayoutParams(layoutParams);

        return tv;
    }

    //Drop Game
    public void quitgame(View view){
        new AlertDialog.Builder(this)
                .setMessage("Are You Sure You Want To Exit? Your Achievement Won't Be Saved.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        go_backHP(view);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}