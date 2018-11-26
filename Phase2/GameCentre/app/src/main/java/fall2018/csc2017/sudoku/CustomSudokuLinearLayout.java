package fall2018.csc2017.sudoku;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * A custom linear layout with equal width and height, used for displaying sudoku number
 */
public class CustomSudokuLinearLayout extends LinearLayout {

    public CustomSudokuLinearLayout(Context context) {
        super(context);
    }

    public CustomSudokuLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSudokuLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
