package com.AndyH.volleytools;

public class Game {

    private String fbKey;
    //only initialized after data is retrieved from firebase

    private int goodpeople_points;
    private int badpeople_points;
    private int goodpeople_sets;
    private int badpeople_sets;
    private String goodpeople_teamname;
    private String badpeople_teamname;
    private String gameEndTime;
    private int backgroundResource;


    public Game() {
    }

    public Game(int goodpeople_points, int badpeople_points, int goodpeople_sets,
                int badpeople_sets, String goodpeople_teamname, String badpeople_teamname, String gameendtime) {

        this.goodpeople_points = goodpeople_points;
        this.badpeople_points = badpeople_points;
        this.goodpeople_sets = goodpeople_sets;
        this.badpeople_sets = badpeople_sets;
        this.goodpeople_teamname = goodpeople_teamname;
        this.badpeople_teamname = badpeople_teamname;
        this.gameEndTime = gameendtime;
    }

    public String getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(String time) {
        this.gameEndTime = time;
    }

    public int getGoodpeople_points() {
        return goodpeople_points;
    }

    public void setGoodpeople_points(int goodpeople_points) {
        this.goodpeople_points = goodpeople_points;
    }

    public int getBadpeople_points() {
        return badpeople_points;
    }

    public void setBadpeople_points(int badpeople_points) {
        this.badpeople_points = badpeople_points;
    }

    public int getGoodpeople_sets() {
        return goodpeople_sets;
    }

    public void setGoodpeople_sets(int goodpeople_sets) {
        this.goodpeople_sets = goodpeople_sets;
    }

    public int getBadpeople_sets() {
        return badpeople_sets;
    }

    public void setBadpeople_sets(int badpeople_sets) {
        this.badpeople_sets = badpeople_sets;
    }

    public String getGoodpeople_teamname() {
        return goodpeople_teamname;
    }

    public void setGoodpeople_teamname(String goodpeople_customize_name) {
        this.goodpeople_teamname = goodpeople_customize_name;
    }

    public String getBadpeople_teamname() {
        return badpeople_teamname;
    }

    public void setBadpeople_teamname(String badpeople_customize_name) {
        this.badpeople_teamname = badpeople_customize_name;
    }

    public String getFbKey() {
        return fbKey;
    }

    public void setFbKey(String fbKey) {
        this.fbKey = fbKey;
    }

    public int getBackgroundResource() {
        return backgroundResource;
    }

    public void setBackgroundResource(int colorDisplay) {
        this.backgroundResource = colorDisplay;
    }

    public void goodpeople_gain_point() {
        goodpeople_points += 1;
        if (goodpeople_points == 100) {
            goodpeople_points = 0;
        }
    }

    public void badpeople_gain_point() {
        badpeople_points += 1;
        if (badpeople_points == 100) {
            badpeople_points = 0;
        }
    }

    public void goodpeople_lose_point() {
        goodpeople_points -= 1;
        if (goodpeople_points == -1) {
            goodpeople_points = 99;
        }
    }

    public void badpeople_lose_point() {
        badpeople_points -= 1;
        if (badpeople_points == -1) {
            badpeople_points = 99;
        }
    }

    public void goodpeople_gain_set() {
        goodpeople_sets += 1;
        if (goodpeople_sets == 10) {
            goodpeople_sets = 0;
        }
    }

    public void badpeople_gain_set() {
        badpeople_sets += 1;
        if (badpeople_sets == 10) {
            badpeople_sets = 0;
        }
    }
}
