package com.AndyH.volleytools;

import android.os.Bundle;

public class Game {
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
    public void goodpeopl_gain_set(){
        goodpeople_sets+=1;
    }
    public void badpeopl_gain_set(){
        badpeople_sets+=1;
    }


    public void load_bundle_data_in(Bundle bundle){
        //bundle should either have all the keys, or non of the keys

    }

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
