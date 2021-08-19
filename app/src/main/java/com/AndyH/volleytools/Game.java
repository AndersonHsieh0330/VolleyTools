package com.AndyH.volleytools;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Game implements Parcelable {
    int points_to_win_set;
    int sets_to_win_game;
    int goodpeople_points;
    int badpeople_points;
    int goodpeople_sets;
    int badpeople_sets;
    String goodpeople_customize_name;
    String badpeople_customize_name;



    public Game(){

    }

    public Game(int points_to_win_set, int sets_to_win_game, int goodpeople_points, int badpeople_points, int goodpeople_sets,
                int badpeople_sets, String goodpeople_customize_name, String badpeople_customize_name) {
        this.points_to_win_set = points_to_win_set;
        this.sets_to_win_game = sets_to_win_game;
        this.goodpeople_points = goodpeople_points;
        this.badpeople_points = badpeople_points;
        this.goodpeople_sets = goodpeople_sets;
        this.badpeople_sets = badpeople_sets;
        this.goodpeople_customize_name = goodpeople_customize_name;
        this.badpeople_customize_name = badpeople_customize_name;
    }

    protected Game(Parcel in) {
        points_to_win_set = in.readInt();
        sets_to_win_game = in.readInt();
        goodpeople_points = in.readInt();
        badpeople_points = in.readInt();
        goodpeople_sets = in.readInt();
        badpeople_sets = in.readInt();
        goodpeople_customize_name = in.readString();
        badpeople_customize_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(points_to_win_set);
        dest.writeInt(sets_to_win_game);
        dest.writeInt(goodpeople_points);
        dest.writeInt(badpeople_points);
        dest.writeInt(goodpeople_sets);
        dest.writeInt(badpeople_sets);
        dest.writeString(goodpeople_customize_name);
        dest.writeString(badpeople_customize_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public int getPoints_to_win_set() {
        return points_to_win_set;
    }

    public void setPoints_to_win_set(int points_to_win_set) {
        this.points_to_win_set = points_to_win_set;
    }

    public int getSets_to_win_game() {
        return sets_to_win_game;
    }

    public void setSets_to_win_game(int sets_to_win_game) {
        this.sets_to_win_game = sets_to_win_game;
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

    public String getGoodpeople_customize_name() {
        return goodpeople_customize_name;
    }

    public void setGoodpeople_customize_name(String goodpeople_customize_name) {
        this.goodpeople_customize_name = goodpeople_customize_name;
    }

    public String getBadpeople_customize_name() {
        return badpeople_customize_name;
    }

    public void setBadpeople_customize_name(String badpeople_customize_name) {
        this.badpeople_customize_name = badpeople_customize_name;
    }

    public void goodpeople_gain_point(){
        goodpeople_points += 1;
    }

    public void badpeople_gain_point(){
        badpeople_points += 1;
    }

    public void goodpeople_lose_point(){
        goodpeople_points -= 1;
    }

    public void badpeople_lose_point(){
        badpeople_points -= 1;
    }
    public void goodpeople_gain_set(){
        goodpeople_sets+=1;
        if(goodpeople_sets==10){
            goodpeople_sets=0;
        };
    }
    public void badpeople_gain_set(){
        badpeople_sets+=1;
        if(badpeople_sets==10){
            badpeople_sets=0;
        };
    }


//    public void load_bundle_data_in(Bundle bundle){
//        //bundle should either have all the keys, or non of the keys
//        if(bundle.containsKey("is_game_on")){
//            try {
//                goodpeople_points = bundle.getInt("goodpeople_points");
//                goodpeople_sets = bundle.getInt("goodpeople_sets");
//                badpeople_sets = bundle.get("badpeople_sets");
//            }catch (Exception e){
//                Log.d("Missing_key", e.getMessage());
//            }
//        }else{
//            throw new RuntimeException("string key not found while loading data");
//        }
//
//    }

    public boolean set_end_check(){
        if ((goodpeople_points == points_to_win_set)||(badpeople_points==points_to_win_set)){
            return true;
        }else{
            return false;
        }
    }

    public boolean game_end_check(){
        if ((goodpeople_sets == sets_to_win_game)||(badpeople_sets==sets_to_win_game)){
            return true;
        }else{
            return false;
        }
    }


}
