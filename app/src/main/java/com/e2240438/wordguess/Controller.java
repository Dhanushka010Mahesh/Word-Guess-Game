package com.e2240438.wordguess;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static List<Player> playerList = new ArrayList<>();

    public Controller(){

    }

    public static Player serchPlayer(String name){
        for (Player p : playerList){
            if((p.getName().trim()).equalsIgnoreCase(name.trim())){
                return p;
            }

        }
        return null;
    }

    public static String toStringList(){
        String data="";
        for (Player p : playerList){
            data +=p.toString();

        }
        return data;
    }

    public static void listClear(){
        playerList.clear();
    }

}
class Player{
    private String name;
    private double point;

    public Player(String name){
        this.name=name;
        this.point=100;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return " " + name + " - " +" point = " + point +"\n";
    }
}
