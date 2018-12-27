package app.chess.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.chess.ChessBoardTile;
import app.chess.KnightPath;
import app.chess.R;

/**
 * Created by Paul on 27/12/2018
 */
public class SolutionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int HEADER = 0;
    private final int SOLUTION = 1;

    private ArrayList<KnightPath> mPaths;
    private ChessBoardTile mStartingTile;
    private ChessBoardTile mEndingTile;
    private Context mContext;

    public SolutionsAdapter(Context context, ArrayList<KnightPath> mPaths, ChessBoardTile starting, ChessBoardTile ending) {
        this.mPaths = mPaths;
        mContext = context;
        mStartingTile = starting;
        mEndingTile = ending;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        switch (type) {
            case HEADER:
                View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_solutions_header, parent, false);
                return new HeaderViewHolder(headerView);
            default:
                View solutionsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_solution, parent, false);
                return new SolutionViewHolder(solutionsView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof SolutionViewHolder) {
            int arrayPosition = i - 1;
            ((SolutionViewHolder) viewHolder).bind(arrayPosition, mPaths.get(arrayPosition));
        }
    }

    @Override
    public int getItemCount() {
        return mPaths != null ? mPaths.size() + 1 : 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEADER;
        return SOLUTION;
    }

    private class SolutionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatTextView solution;

        SolutionViewHolder(View view) {
            super(view);
            solution = view.findViewById(R.id.tv_solution);
        }

        void bind(int position, KnightPath path) {
            solution.setText(String.format("%s. %s", position + 1, path.getName()));
        }

        @Override
        public void onClick(View view) {


        }

    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView headerText;

        HeaderViewHolder(View view) {
            super(view);
            headerText = view.findViewById(R.id.tv_header);
            if (mPaths.isEmpty()) {
                headerText.setText(mContext.getResources().getString(R.string.solutions___header_no_values, mStartingTile.getName(), mEndingTile.getName()));
            } else {
                headerText.setText(mContext.getResources().getString(R.string.solutions___header_with_values, mPaths.size(), mStartingTile.getName(), mEndingTile.getName()));
            }
        }
    }

}
