package com.example.guessthenumber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scoreboardDB";
    private static final String TABLE_SCORE = "scoreboard";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "nickname";
    private static final String KEY_SCORE = "score";
    private static final String KEY_DT = "dateTime";
    private static final String KEY_ATT = "attempts";
    private static final String KEY_TIME = "time_spent";
    private static final String KEY_LEVEL = "level";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORE_TABLEEZ = "CREATE TABLE " + TABLE_SCORE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SCORE + " NUMBER," + KEY_DT + " TEXT," + KEY_ATT + " TEXT," + KEY_TIME + " TEXT," + KEY_LEVEL + " TEXT )";

        db.execSQL(CREATE_SCORE_TABLEEZ);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);

        // Create tables again
        onCreate(db);
    }

    // code to add the new score
    void addScore(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, score.getNickname()); // Nickname
        values.put(KEY_SCORE, score.getScore()); // Score
        values.put(KEY_DT, score.getDateTime()); // DateTime
        values.put(KEY_ATT, score.getAttempts()); // attempts
        values.put(KEY_TIME, score.getTime_spent()); // time spent
        values.put(KEY_LEVEL, score.getLevel()); // difficulty level

        // Inserting Row
        db.insert(TABLE_SCORE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get all the scores in a list view
    public List<Score> getAllScores_EZ() {
        List<Score> scoreList = new ArrayList<Score>();
        // Select All Query
        //score,attempts,time_spent
        String selectQuery = "SELECT  * FROM " + TABLE_SCORE+" WHERE level='EASY' ORDER BY score DESC, attempts,time_spent ASC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Score score = new Score();
                score.set_id(Integer.parseInt(cursor.getString(0)));
                score.setNickname(cursor.getString(1));
                score.setScore(cursor.getInt(2));
                score.setDateTime(cursor.getString(3));
                score.setAttempts(cursor.getString(4));
                score.setTime_spent(cursor.getString(5));
                score.setLevel(cursor.getString(6));
                // Adding score to list
                scoreList.add(score);
            } while (cursor.moveToNext());
        }

        // return score list
        return scoreList;
    }

    public List<Score> getAllScores_MD() {
        List<Score> scoreList = new ArrayList<Score>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SCORE+" WHERE level='MEDIUM' ORDER BY score DESC, attempts,time_spent ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Score score = new Score();
                score.set_id(Integer.parseInt(cursor.getString(0)));
                score.setNickname(cursor.getString(1));
                score.setScore(cursor.getInt(2));
                score.setDateTime(cursor.getString(3));
                score.setAttempts(cursor.getString(4));
                score.setTime_spent(cursor.getString(5));
                score.setLevel(cursor.getString(6));

                // Adding score to list
                scoreList.add(score);
            } while (cursor.moveToNext());
        }

        // return score list
        return scoreList;
    }

    public List<Score> getAllScores_HD() {
        List<Score> scoreList = new ArrayList<Score>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SCORE+" WHERE level='HARD' ORDER BY score DESC, attempts,time_spent ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Score score = new Score();
                score.set_id(Integer.parseInt(cursor.getString(0)));
                score.setNickname(cursor.getString(1));
                score.setScore(cursor.getInt(2));
                score.setDateTime(cursor.getString(3));
                score.setAttempts(cursor.getString(4));
                score.setTime_spent(cursor.getString(5));
                score.setLevel(cursor.getString(6));

                // Adding score to list
                scoreList.add(score);
            } while (cursor.moveToNext());
        }

        // return score list
        return scoreList;
    }

    //Delete all the records
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_SCORE);
    }

}
