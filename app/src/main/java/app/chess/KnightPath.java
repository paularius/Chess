package app.chess;

import java.util.ArrayList;

public class KnightPath {
    String id;
    boolean isCorrect;
    ArrayList<ChessBoardTile> tileArray;

    public KnightPath(int tileArraySize) {
        tileArray = new ArrayList<>();
    }

    void addTile(ChessBoardTile tile) {
        tileArray.add(tile);
    }

    ChessBoardTile getTile(int position) {
        return tileArray.get(position);
    }
}
