package com.org.news.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * This class is used to define custom text view with bold style
 */
public class RobotoBoldTextView extends AppCompatTextView {


    public RobotoBoldTextView(Context context) {
        super(context);
        init(null);
    }


    public RobotoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RobotoBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        if (attrs!=null) {
            Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto.bold.ttf");
            setTypeface(myTypeface);
        }
    }
}
