package app.chess;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Paul on 27/12/2018
 */
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

    public void initPathSearch(ChessBoardTile startingTile, ChessBoardTile endingTile, int maxSteps) {
        allPaths.clear();
        mVisitedTilesMap.clear();
        mVisitedTilesMap.put(startingTile.getName(), startingTile);
        getNextStepTiles(startingTile, endingTile, null, 0, maxSteps);
    }

    ArrayList<KnightPath> getAllPaths() {
        return allPaths;
    }


    /**
     * Compute recursively every path available from a starting tile and with a max depth given
     *
     * @param startingTile The tile from which the next steps are computed
     * @param endingTile   If this tile is found, the path is considered correct and the calculation for more tiles stops.
     * @param existingPath The path computed so far, so as to add new tiles.
     * @param currentDepth The depth of the path so far, so as to compare it with the max depth requested by the user.
     * @param maxDepth     The max depth of the path.
     */
    private void getNextStepTiles(ChessBoardTile startingTile, ChessBoardTile endingTile, KnightPath existingPath, int currentDepth, int maxDepth) {
        if (startingTile == null)
            return;
        int startingRow = startingTile.getRow();
        int startingColumn = startingTile.getColumn();
        int[] dx = {2, 2, -1, 1, -2, -2, -1, 1};
        int[] dy = {-1, 1, 2, 2, 1, -1, -2, -2};

        for (int i = 0; i < 8; i++) {
            String tileName = String.format("%s-%s", startingRow + dx[i], startingColumn + dy[i]);
            createKnightPath(endingTile, existingPath, currentDepth, maxDepth, tileName);
        }
    }

    private void createKnightPath(ChessBoardTile endingTile, KnightPath existingPath, int currentDepth, int maxSteps, String currentTileName) {
        if (mVisitedTilesMap.containsKey(currentTileName))
            return;
        if (mTilesMap.containsKey(currentTileName)) {
            KnightPath path = new KnightPath(mStartingTile);
            ChessBoardTile newTile = mTilesMap.get(currentTileName);
            if (endingTile != null && currentTileName.equals(endingTile.getName())) {
                path.isCorrect = true;
            }
            for (int i = 0; i < currentDepth; i++) {
                if (i < existingPath.tileArray.size()) {
                    path.addTile(existingPath.getTile(i));
                }
            }
            path.addTile(newTile);
            if (!path.isCorrect && currentDepth + 1 < maxSteps) {
                mVisitedTilesMap.put(currentTileName, newTile);
                getNextStepTiles(path.getTile(currentDepth), endingTile, path, currentDepth + 1, maxSteps);
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
