package com.example.guessthenumber;

public class Score {
    int _id;
    String nickname;
    int score;
    String dateTime;
    String attempts;
    String time_spent;
    String level;

    public Score(){}

    public Score(int id, String nickname, int score, String dateTime, String attempts, String time_spent, String level){
        this._id=id;
        this.nickname=nickname;
        this.score=score;
        this.dateTime=dateTime;
        this.attempts=attempts;
        this.time_spent=time_spent;
        this.level=level;
    }

    public Score(String nickname, int score, String dateTime,String attempts, String time_spent, String level){
        this.nickname = nickname;
        this.score = score;
        this.dateTime=dateTime;
        this.attempts=attempts;
        this.time_spent=time_spent;
        this.level=level;
    }

    public String getAttempts() {
        return attempts;
    }

    public void setAttempts(String attempts) {
        this.attempts = attempts;
    }

    public String getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(String time_spent) {
        this.time_spent = time_spent;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}

