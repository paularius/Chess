package app.chess;

import java.util.ArrayList;
import java.util.HashMap;

public class ChessBoard {
    ArrayList<KnightPath> allPaths = new ArrayList<>();
    private ArrayList<ChessBoardTile> mTiles;
    private HashMap<String, ChessBoardTile> mTilesMap;
    private HashMap<String, ChessBoardTile> mVisitedTilesMap;
    private int mSize;
    private ChessBoardTile mStartingTile;
    private ChessBoardTile mEndingTile;

    public ChessBoard(int size) {
        mSize = size;
        mTiles = new ArrayList<>();
        mTilesMap = new HashMap<>();
        mVisitedTilesMap = new HashMap<>();
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                ChessBoardTile tile = new ChessBoardTile((row * size) + column, row, column);
                mTiles.add(tile);
                mTilesMap.put(tile.getName(), tile);
            }
        }
    }

    public void initPathSearch(ChessBoardTile startingTile, ChessBoardTile endingTile) {
        allPaths.clear();
        mVisitedTilesMap.clear();
        mVisitedTilesMap.put(startingTile.getName(), startingTile);
        getNextStepTiles(startingTile, endingTile, null, 0);
    }

    ArrayList<KnightPath> getCorrectPaths() {
        return allPaths;
    }

    private void getNextStepTiles(ChessBoardTile startingTile, ChessBoardTile endingTile, KnightPath existingPath, int currentDepth) {
        if (startingTile == null)
            return;
        int startingRow = startingTile.getRow();
        int startingColumn = startingTile.getColumn();
        String one = String.format("%s-%s", startingRow + 2, startingColumn - 1);
        String two = String.format("%s-%s", startingRow + 2, startingColumn + 1);
        String three = String.format("%s-%s", startingRow - 1, startingColumn + 2);
        String four = String.format("%s-%s", startingRow + 1, startingColumn + 2);
        String five = String.format("%s-%s", startingRow - 2, startingColumn + 1);
        String six = String.format("%s-%s", startingRow - 2, startingColumn - 1);
        String seven = String.format("%s-%s", startingRow - 1, startingColumn - 2);
        String eight = String.format("%s-%s", startingRow + 1, startingColumn - 2);

        createKnightPath(endingTile, existingPath, currentDepth, one);
        createKnightPath(endingTile, existingPath, currentDepth, two);
        createKnightPath(endingTile, existingPath, currentDepth, three);
        createKnightPath(endingTile, existingPath, currentDepth, four);
        createKnightPath(endingTile, existingPath, currentDepth, five);
        createKnightPath(endingTile, existingPath, currentDepth, six);
        createKnightPath(endingTile, existingPath, currentDepth, seven);
        createKnightPath(endingTile, existingPath, currentDepth, eight);
    }

    private void createKnightPath(ChessBoardTile endingTile, KnightPath existingPath, int currentDepth, String one) {
        if (mVisitedTilesMap.containsKey(one))
            return;
        if (mTilesMap.containsKey(one)) {
            KnightPath path = new KnightPath(currentDepth);
            ChessBoardTile newTile = mTilesMap.get(one);
            if (endingTile != null && one.equals(endingTile.getName())) {
                path.isCorrect = true;
            }
            switch (currentDepth) {
                case 0:
                    path.addTile(newTile);
                    if (!path.isCorrect) {
                        mVisitedTilesMap.put(one, newTile);
                        getNextStepTiles(path.getTile(0), endingTile, path, 1);
                    }
                    break;
                case 1:
                    path.addTile(existingPath.getTile(0));
                    path.addTile(newTile);
                    if (!path.isCorrect) {
                        mVisitedTilesMap.put(one, newTile);
                        getNextStepTiles(path.getTile(1), endingTile, path, 2);
                    }
                    break;
                case 2:
                    path.addTile(existingPath.getTile(0));
                    path.addTile(existingPath.getTile(1));
                    path.addTile(newTile);
                    break;
                default:
                    break;
            }

            allPaths.add(path);
        }
    }


    public ArrayList<ChessBoardTile> getTiles() {
        return mTiles;
    }

    public ChessBoardTile getStartingTile() {
        return mStartingTile;
    }

    public void setStartingTile(ChessBoardTile mStartingTile) {
        this.mStartingTile = mStartingTile;
    }

    public ChessBoardTile getEndingTile() {
        return mEndingTile;
    }

    public void setEndingTile(ChessBoardTile mEndingTile) {
        this.mEndingTile = mEndingTile;
    }

    public int getSize() {
        return mSize;
    }


}
