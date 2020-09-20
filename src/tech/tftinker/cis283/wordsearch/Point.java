package tech.tftinker.cis283.wordsearch;

public class Point {
    public int x;
    public int y;

    public Point(){

    }

    public Point(int x, int y){
        this();
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "["+x+","+y+"]";
    }
}
