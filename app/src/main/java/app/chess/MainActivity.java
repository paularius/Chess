package app.chess;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import java.util.ArrayList;

import app.chess.adapters.ChessBoardAdapter;
import app.chess.adapters.SolutionsAdapter;

/**
 * Created by Paul on 27/12/2018
 */
public class MainActivity extends AppCompatActivity {
    RecyclerView mBoardRecyclerView;
    RecyclerView mSolutionsRecyclerView;
    ChessBoard mBoard;
    AppCompatButton mStartButton;
    ChessBoardAdapter mBoardAdapter;
    AppCompatRadioButton mStartRadio;
    AppCompatRadioButton mEndRadio;
    RadioGroup mRadioGroup;
    ConstraintLayout mSettingsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBoardRecyclerView = findViewById(R.id.gv_chess_board);
        mSolutionsRecyclerView = findViewById(R.id.rv_solutions);
        mSettingsView = findViewById(R.id.cl_configuration);
        mStartButton = findViewById(R.id.btn_start);
        mStartRadio = findViewById(R.id.rb_start);
        mEndRadio = findViewById(R.id.rb_end);
        mRadioGroup = findViewById(R.id.rg_selection_type);
        mEndRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBoardAdapter.setEnabled(true);
                if (isChecked)
                    mBoardAdapter.setSelectEndPositionEnabled();
            }
        });
        mStartRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBoardAdapter.setEnabled(true);

                if (isChecked)
                    mBoardAdapter.setSelectStartPositionEnabled();
            }
        });
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPathsAndShow();
            }
        });
        selectBoardSize();
    }

    private void findPathsAndShow() {
        if (mBoard.getStartingTile() == null || mBoard.getEndingTile() == null)
            return;
        mSettingsView.setVisibility(View.GONE);
        mBoardAdapter.setEnabled(false);
        mBoard.initPathSearch(mBoard.getStartingTile(), mBoard.getEndingTile(), 3);
        ArrayList<KnightPath> paths = mBoard.getAllPaths();
        ArrayList<KnightPath> correctPaths = new ArrayList<>();
        for (KnightPath path :
                paths) {
            if (!path.isCorrect)
                continue;
            correctPaths.add(path);
        }
        mSolutionsRecyclerView.setVisibility(View.VISIBLE);
        mSolutionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSolutionsRecyclerView.setAdapter(new SolutionsAdapter(this, correctPaths, mBoard.getStartingTile(), mBoard.getEndingTile()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_game:
                mRadioGroup.clearCheck();
                selectBoardSize();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectBoardSize() {
        mSolutionsRecyclerView.setVisibility(View.GONE);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Please select the size of the board");
        final NumberPicker input = new NumberPicker(this);
        input.setMinValue(6);
        input.setMaxValue(16);
        alert.setView(input);
        alert.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                drawBoard(input.getValue());
                mSettingsView.setVisibility(View.VISIBLE);
            }
        });
        alert.setCancelable(false);
        alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        alert.show();
    }

    private void drawBoard(int size) {
        mBoardRecyclerView.setLayoutManager(new GridLayoutManager(this, size));
        mBoard = new ChessBoard(size);
        mBoardAdapter = new ChessBoardAdapter(this, mBoard);
        mBoardRecyclerView.setAdapter(mBoardAdapter);
    }
}
