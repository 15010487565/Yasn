package www.xcd.com.mylibrary.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Android on 2017/7/19.
 */

public class BaseMeGridView extends GridView {

    public BaseMeGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseMeGridView(Context context) {
        super(context);
    }

    public BaseMeGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}