package fall2018.csc2017.sudoku;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import fall2018.csc2017.R;

/**
 * Adapter that displays the number select buttons from 1-9
 */
// Excluded from tests because its a view class
public class SudokuNumberSelectAdapter extends BaseAdapter {

    /**
     * Context of the adapater
     */
    private Context context;
    /**
     * integer array from 1-9 to display in grid
     */
    private int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    /**
     * Constructor of the adapter
     * @param context context of the adapter
     */
    SudokuNumberSelectAdapter(Context context) {
        this.context = context;
    }

    /**
     * Getter for the number of items in dataset
     * @return number of items, 81 in sudokuBoard
     */
    @Override
    public int getCount() {
        return 9;
    }

    /**
     * Getter for the object at position
     * @param position position of object
     * @return the object at position
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Getter for object id at position
     * @param position position of object
     * @return the object id
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Returns the view of the gridView
     * @param position position in the grid view
     * @param convertView the view to be reused
     * @param parent the viewgroup of the view to be returned
     * @return a view for the position in gridview
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.layout_sudoku_number, null);
        }
        // Set text for each button
        TextView displayNumber = convertView.findViewById(R.id.sudoku_number);
        displayNumber.setText(String.format("%s", numbers[position]));
        // set background
        convertView.setBackground(ContextCompat.getDrawable(context, R.drawable.sudoku_select_background));
        return convertView;
    }
}
