package app.chess;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mBoardRecyclerView;
    ChessBoard mBoard;
    AppCompatButton mStartButton;
    ChessBoardAdapter mBoardAdapter;
    AppCompatRadioButton mStartRadio;
    AppCompatRadioButton mEndRadio;
    RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBoardRecyclerView = findViewById(R.id.gv_chess_board);
        mStartButton = findViewById(R.id.btn_start);
        mStartRadio = findViewById(R.id.rb_start);
        mEndRadio = findViewById(R.id.rb_end);
        mRadioGroup = findViewById(R.id.rg_selection_type);
        mEndRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mBoardAdapter.setSelectEndPositionEnabled();
            }
        });
        mStartRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mBoardAdapter.setSelectStartPositionEnabled();
            }
        });
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBoard.initPathSearch(mBoard.getStartingTile(), mBoard.getEndingTile());
                ArrayList<KnightPath> paths = mBoard.getCorrectPaths();
                for (KnightPath path :
                        paths) {
                    if (!path.isCorrect)
                        continue;
                    String fullPathString = mBoard.getStartingTile().getName();
                    for (int i = 0; i < path.tileArray.size(); i++) {
                        fullPathString = fullPathString.concat(" " + path.tileArray.get(i).getName());
                    }
                    Log.d("LOG::", fullPathString);
                }
                Log.d("LOG", "COMPLETED");
            }
        });
        selectBoardSize();
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

    //Number picker dialog for the board size
    private void selectBoardSize() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Please select the size of the board");
        final NumberPicker input = new NumberPicker(this);
        input.setMinValue(6);
        input.setMaxValue(16);
        alert.setView(input);
        alert.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                drawBoard(input.getValue());
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
