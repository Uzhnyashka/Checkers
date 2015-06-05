package com.example.bobyk.checkers;

/**
 * Created by bobyk on 05/06/15.
 */
public class Square {

    private Player player = null;
    private boolean ok = false;

    public void fill(Player player){
        this.player = player;
    }

    public void unfill() {
        this.player = null;
    }

    public boolean isFilled(){
        if (player != null) {
            return true;
        }
        return false;
    }

    public void isKing(){
        this.ok = true;
    }

    public void isNotKing(){
        this.ok = false;
    }

    public boolean getKing(){
        if (ok) return true;
        else return false;
    }

    public Player getPlayer(){
        return player;
    }

}