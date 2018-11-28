package fall2018.csc2017.sudoku;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import fall2018.csc2017.R;

/**
 * The adapter that processes a sudokuBoardManager and displays it in a gridview
 */
public class SudokuBoardAdapter extends BaseAdapter {

    /**
     * The sudoku board and boardmanager to be processed
     */
    private SudokuPlayBoard sudokuBoard;
    private SudokuBoardManager sudokuBoardManager;

    /**
     * Context of the adapter
     */
    private Context context;

    SudokuBoardAdapter (SudokuBoardManager sudokuBoardManager, Context context) {
        this.sudokuBoardManager = sudokuBoardManager;
        this.sudokuBoard = sudokuBoardManager.getActiveBoard();
        this.context = context;

    }

    @Override
    public int getCount() {
        return 81;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Determine the current number
        int row = position / 9;
        int col = position % 9;
        Integer currentNumber = sudokuBoard.getSudokuBoard()[row][col];

        // Inflate the layout
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.layout_sudoku_number, null);
        }
        // Display the number in the layout
        TextView number = convertView.findViewById(R.id.sudoku_number);
        number.setTextColor(ContextCompat.getColor(context, R.color.black));
        // update text displays
        setTextDisplay(currentNumber, number);
        setTextHighlight(position, number);
        //update layout background
        updateGridBackground(position, convertView);
        // Return the view
        return convertView;
    }

    /**
     * Set text highlight if there are repeats
     * @param position the current position
     * @param number the textview to be updated
     */
    private void setTextHighlight(int position, TextView number) {
        for (ArrayList<Integer> group : sudokuBoard.getRepeats()) {
            if (group.contains(position)) {
                number.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
        }
    }

    /**
     * Sets the text of the view
     * @param currentNumber the number to be displayed
     * @param number the textview to be updated
     */
    private void setTextDisplay(Integer currentNumber, TextView number) {
        if (currentNumber == 0) {
            number.setText("");
        } else {
            number.setText(String.format("%s", currentNumber));
        }
    }

    /**
     * Updates the grid background depending on whether it is selected,
     * to be filled, or cannot be filled
     * @param position the current position
     * @param convertView the view
     */
    private void updateGridBackground(int position, View convertView) {
        // Display the highlighted grid and empty grids
        if (position == sudokuBoardManager.getPositionSelected()) {
            convertView.setBackground(ContextCompat.getDrawable(
                    context,R.drawable.sudoku_border_selected));
        } else if (sudokuBoard.getRemovedNumbers().contains(position)) {
            convertView.setBackground(ContextCompat.getDrawable(context, R.drawable.sudoku_border_empty));
        } else {
            convertView.setBackground(ContextCompat.getDrawable(context,R.drawable.sudoku_border));
        }
    }

}
