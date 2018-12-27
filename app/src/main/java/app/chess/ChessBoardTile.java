package app.chess;

public class ChessBoardTile {
    private int mRow;
    private int mColumn;
    private int mIndex;
    private boolean isBlack;
    private String mName;

    public ChessBoardTile(int index, int row, int column) {
        mRow = row;
        mColumn = column;
        isBlack = mRow % 2 == 0 ? mColumn % 2 == 0 : (mColumn + 1) % 2 == 0;
        mName = String.format("%s-%s", mRow, mColumn);
        mIndex = index;
    }


    public int getColumn() {
        return mColumn;
    }

    public int getRow() {
        return mRow;
    }

    public int getIndex() {
        return mIndex;
    }


    public boolean isBlack() {
        return isBlack;
    }

    public String getName() {
        return mName;
    }
}
