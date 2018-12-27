package app.chess;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class ChessBoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int RADIO_NONE = 0;
    private final int RADIO_START = 1;
    private final int RADIO_END = 2;


    private int whiteColor;
    private int blackColor;
    private int startColor;
    private int endColor;

    private int mTileSize;
    private ChessBoard mBoard;
    private Context mContext;
    private int mSelectedRadio = RADIO_NONE;

    public ChessBoardAdapter(Context context, ChessBoard board) {
        mBoard = board;
        mContext = context;
        mTileSize = getScreenSize().x / mBoard.getSize();
        initColors();
    }

    public void setSelectEndPositionEnabled() {
        mSelectedRadio = RADIO_END;
    }

    public void setSelectStartPositionEnabled() {
        mSelectedRadio = RADIO_START;
    }

    private void initColors() {
        whiteColor = mContext.getResources().getColor(android.R.color.white);
        blackColor = mContext.getResources().getColor(android.R.color.black);
        startColor = mContext.getResources().getColor(android.R.color.holo_green_dark);
        endColor = mContext.getResources().getColor(android.R.color.holo_purple);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_grid, parent, false);
        headerView.getLayoutParams().width = mTileSize;
        headerView.getLayoutParams().height = mTileSize;
        return new TileViewHolder(headerView, i);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((TileViewHolder) viewHolder).bind(i);
    }

    @Override
    public int getItemCount() {
        return mBoard.getTiles().size();
    }

    private Point getScreenSize() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    private class TileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View container;
        TextView button;
        ChessBoardTile tile;

        TileViewHolder(View view, int position) {
            super(view);
            container = view.findViewById(R.id.content);
            button = view.findViewById(R.id.info_text);
            bind(position);
        }

        void bind(int i) {
            if (mContext == null)
                return;
            tile = mBoard.getTiles().get(i);
            int colorId = mBoard.getEndingTile() != null && mBoard.getEndingTile().getName().equals(tile.getName())
                    ? endColor
                    : mBoard.getStartingTile() != null && mBoard.getStartingTile().getName().equals(tile.getName())
                    ? startColor
                    : tile.isBlack()
                    ? blackColor
                    : whiteColor;
            container.setBackgroundColor(colorId);
            button.setText(tile.getName());
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mSelectedRadio == RADIO_START) {
                int indexThatChanged = -1;
                if (mBoard.getEndingTile() != null && mBoard.getEndingTile().equals(tile))
                    mBoard.setEndingTile(null);
                if (mBoard.getStartingTile() != null) {
                    indexThatChanged = mBoard.getStartingTile().getIndex();
                }
                mBoard.setStartingTile(tile);
                if (indexThatChanged > -1)
                    notifyItemChanged(indexThatChanged);
                notifyItemChanged(tile.getIndex());
                return;
            }
            if (mSelectedRadio == RADIO_END) {
                int indexThatChanged = -1;
                if (mBoard.getStartingTile() != null && mBoard.getStartingTile().equals(tile))
                    mBoard.setStartingTile(null);
                if (mBoard.getEndingTile() != null) {
                    indexThatChanged = mBoard.getEndingTile().getIndex();
                }
                if (indexThatChanged > -1)
                    notifyItemChanged(indexThatChanged);
                mBoard.setEndingTile(tile);
                notifyItemChanged(tile.getIndex());
            }
        }

    }
}
