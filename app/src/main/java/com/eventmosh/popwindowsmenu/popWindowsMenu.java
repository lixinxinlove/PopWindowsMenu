package com.eventmosh.popwindowsmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.PaintDrawable;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by android on 2016/10/13.
 */
public class popWindowsMenu extends RelativeLayout implements View.OnClickListener, PopupWindow.OnDismissListener, AdapterView.OnItemClickListener {


    private int numColumns;
    private int openIcon;
    private int closeIcon;
    private int textColor;


    private View contextView;
    private RelativeLayout rootView;
    private ImageView menu;
    private Context context;
    private View dialogView;
    private List mData;
    private boolean isOpen = false;
    private RotateAnimation rotateOpen;
    private RotateAnimation rotateClose;


    public popWindowsMenu(Context context) {
        this(context, null);

    }

    public popWindowsMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
//        init(attrs);
//        initAnim();
//        initPop();
    }

    public popWindowsMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("popWindowsMenu","popWindowsMenu");
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contextView = inflater.inflate(R.layout.pop_windows_menu_layout, this);

        init(attrs);
        initAnim();
        initPop();

    }

    private void init(AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.popWindowsMenu);
        numColumns = a.getInt(R.styleable.popWindowsMenu_numColumns, 3);
        openIcon = a.getResourceId(R.styleable.popWindowsMenu_openImage, R.mipmap.open_icon);
        closeIcon = a.getResourceId(R.styleable.popWindowsMenu_closeImage, R.mipmap.close_icon);
        textColor = a.getColor(R.styleable.popWindowsMenu_textColor,getResources().getColor(R.color.colorAccent));

        a.recycle();


        Log.e("numColumns",numColumns+"");


        rootView = (RelativeLayout) contextView.findViewById(R.id.root_view);
        menu = (ImageView) contextView.findViewById(R.id.menu);
        menu.setImageResource(openIcon);
        menu.setOnClickListener(this);


    }


    private PopupWindow popWindow;
    private GridView gridView;
    private int h;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu:
                showMenu();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        popWindow.dismiss();
        if (listener != null) {
            listener.onItemMenuClick(position);
        }
    }

    @Override
    public void onDismiss() {
        menu.setImageResource(openIcon);
        menu.startAnimation(rotateClose);
    }

    private void showMenu() {
        menu.setImageResource(closeIcon);
        menu.startAnimation(rotateOpen);

        int[] location = new int[2];
        menu.getLocationOnScreen(location);

        popWindow.showAtLocation(contextView, Gravity.NO_GRAVITY, location[0], location[1] - h - 5);
    }


    private void initPop() {

        dialogView = View.inflate(context, R.layout.pop_layout, null);
        gridView = (GridView) dialogView.findViewById(R.id.grid_view);
        gridView.setNumColumns(numColumns);

        gridView.setAdapter(new GridViewAdapter());
        gridView.setOnItemClickListener(this);

        popWindow = new PopupWindow(dialogView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popWindow.setAnimationStyle(R.style.AnimationFade);
        popWindow.setBackgroundDrawable(new PaintDrawable());
        popWindow.setFocusable(true);
        popWindow.setOnDismissListener(this);

        //  dialogView.measure(0, 0);

        dialogView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int popupWidth = dialogView.getMeasuredWidth();
        int popupHeight = dialogView.getMeasuredHeight();

        h = popupHeight;
    }


    private void initAnim() {
        rotateOpen = new RotateAnimation(0f, 90f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateOpen.setFillAfter(true);
        rotateOpen.setDuration(200);

        rotateClose = new RotateAnimation(90f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateClose.setFillAfter(true);
        rotateClose.setDuration(200);
    }


    public void setmData(List mData) {
        this.mData = mData;
    }


    class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View v = View.inflate(context, R.layout.item_menu, null);
            TextView menuItem = (TextView) v.findViewById(R.id.menu_item);
            menuItem.setTextColor(textColor);
            menuItem.setText("lllll");

            return v;
        }
    }


    private OnItemMenuListener listener;

    public void setOnItemMenuListener(OnItemMenuListener listener) {
        this.listener = listener;
    }

    public interface OnItemMenuListener {
        void onItemMenuClick(int position);
    }

}
