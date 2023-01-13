package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Home_Page extends AppCompatActivity {

    Spinner mySpinner;
    View mView;
    EditText nickname;
    ImageView clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    //Show Toast
    public void showToast(String msg){
        Toast.makeText(Home_Page.this, msg, Toast.LENGTH_SHORT).show();
    }

    //Check That Both Fields Are Filled Then Go To The next Page
    public void gotoplay(View view){
        String level= mySpinner.getSelectedItem().toString();
        String player_nickname=nickname.getText().toString();

        if(TextUtils.isEmpty(player_nickname)){
            showToast("Please Type A Nickname");
        }else if(level.equals("DIFFICULTY LEVEL")){
            showToast("Please Choose A Difficulty Level");
        }else{
            nickname.setText("");
            mySpinner.setSelection(0);
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(nickname.getWindowToken(), 0);
            Intent i = new Intent(getApplicationContext(), Game_Page.class);
            i.putExtra("NICKNAME",player_nickname);
            i.putExtra("DIFF",level);
            startActivity(i);
        }

    }

    //Forwarded To The Help Page
    public void gotohelp(View view){
        Intent i = new Intent(getApplicationContext(), Help_Page.class);
        startActivity(i);
    }

    //Leave The App Confirmation
    public void quitApp(View view){
        new AlertDialog.Builder(this)
                .setMessage("Are You Sure You Want To Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Home_Page.this.finishAffinity();//closes all ongoing activities and closes the app
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    //Show Dialog Alert On Play Button Click
    public void showAlertDialogButtonClicked(View view)
    {
        // create an AlertDialog
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(Home_Page.this);
        // create a View to  inflate the layout we just created -  (resource file, we dont have a view group so we will leave it null - root= null
        mView = getLayoutInflater().inflate(R.layout.activity_game_settings_page, null);
        // Set up the input
        nickname = (EditText) mView.findViewById(R.id.nickname);
        mySpinner=(Spinner) mView.findViewById(R.id.difficultySpinner);
        clear= (ImageView) mView.findViewById(R.id.clearNickname);


        mySpinner.setOnTouchListener(spinnerOnTouch);

        mBuilder.setCancelable(false);

        mySpinner.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        ArrayAdapter<String> mAdapter= new ArrayAdapter<String>(Home_Page.this, R.layout.simple_spinner_item, getResources().getStringArray(R.array.difficulty_levels));

        mAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(mAdapter);


        //set the dialog view (mView) for the mBulider
        mBuilder.setView(mView);
        android.app.AlertDialog dialog= mBuilder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        nickname.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(nickname.getText().toString().isEmpty()){
                    clear.setVisibility(View.GONE);
                }else{
                    clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //Hide The Keyboard On Spinner Touch
    public View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
               hideKeyboardFrom(getApplicationContext(),mView);
            }
            return false;
        }
    };

    //Hide The Keyboard
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Home_Page.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //Go To The ScoreBoard Page
    public void show_scroeboard(View view){
        Intent i = new Intent(getApplicationContext(), Scoreboard_Page.class);
        startActivity(i);
    }


    public void clearNickname(View view) {
        nickname.setText("");
    }
}
