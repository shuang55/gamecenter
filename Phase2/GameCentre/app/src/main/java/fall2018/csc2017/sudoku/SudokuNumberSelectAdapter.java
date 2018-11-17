package fall2018.csc2017.sudoku;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import fall2018.csc2017.R;

public class SudokuNumberSelectAdapter extends BaseAdapter {

    private Context context;
    private int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    public SudokuNumberSelectAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 9;
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

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.layout_sudoku_number, null);
        }

        TextView displayNumber = convertView.findViewById(R.id.sudoku_number);
        displayNumber.setText(String.format("%s", numbers[position]));

        convertView.setBackground(ContextCompat.getDrawable(context, R.drawable.sudoku_select_background));
        return convertView;
    }
}
