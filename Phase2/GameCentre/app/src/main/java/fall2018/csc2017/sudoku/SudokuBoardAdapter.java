package fall2018.csc2017.sudoku;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import fall2018.csc2017.R;

public class SudokuBoardAdapter extends BaseAdapter {

    private SudokuBoard sudokuBoard;
    private Context context;
    private int selected;

    public SudokuBoardAdapter (SudokuBoard sudokuBoard, Context context) {
        this.sudokuBoard = sudokuBoard;
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
        int row = position / 9;
        int col = position % 9;
        Integer currentNumber = sudokuBoard.getSudokuBoard()[row][col];

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.layout_sudoku_number, null);
        }

        TextView number = convertView.findViewById(R.id.sudoku_number);

        if (currentNumber == 0) {
            number.setText("");
        } else {
            number.setText(String.format("%s", currentNumber));
        }

        if (position == selected) {
            convertView.setBackground(ContextCompat.getDrawable(
                    context,R.drawable.sudoku_border_selected));
        } else {
            convertView.setBackground(ContextCompat.getDrawable(context,R.drawable.sudoku_border));
        }

        return convertView;
    }

    public void setSelected(int position) {
        this.selected = position;
    }
}
