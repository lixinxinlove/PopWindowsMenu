package com.eventmosh.popwindowsmenu;

import android.content.Context;
import android.widget.GridView;

public class ChimelongGridView extends GridView {

	public ChimelongGridView(Context context, android.util.AttributeSet attrs) {
		super(context, attrs);
	}

	public ChimelongGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
