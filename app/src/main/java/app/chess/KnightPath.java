package app.chess;

import java.util.ArrayList;

/**
 * Created by Paul on 27/12/2018
 */
public class KnightPath {
    boolean isCorrect;
    private ChessBoardTile mStartingTile;
    ArrayList<ChessBoardTile> tileArray;

    public KnightPath(ChessBoardTile startingTile) {
        mStartingTile = startingTile;
        tileArray = new ArrayList<>();
    }

    void addTile(ChessBoardTile tile) {
        tileArray.add(tile);
    }

    public ChessBoardTile getTile(int position) {
        return tileArray.get(position);
    }

    boolean isCorrect() {
        return isCorrect;
    }

    public int size() {
        return tileArray.size();
    }

    public String getName(){
        String temp = String.format("(%s)",mStartingTile.getName());
        for (int i = 0; i < size(); i++) {
            temp = temp.concat(String.format("->(%s)", getTile(i).getName()));
        }
        return temp;
    }
}
