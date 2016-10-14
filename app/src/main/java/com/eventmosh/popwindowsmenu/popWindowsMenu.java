package com.eventmosh.popwindowsmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by android on 2016/10/13.
 */
public class PopWindowsMenu extends RelativeLayout implements View.OnClickListener, PopupWindow.OnDismissListener, AdapterView.OnItemClickListener {


    private float menuButtonSize;
    private int numColumns;
    private int openIcon;
    private int closeIcon;
    private int textColor;
    private int menuAlpha;
    private int menuBackgroundColor;
    private float menuItemHeight;
    private float textSize;
    private float menuPopMargin;  //popWindows 距离左右的距离
    private int menuButtonMarginBottom;
    private int menuButtonMarginRight;

    private int selectPosition = 0;  //默认选中的item
    private int menuItemCount = 0;

    private View contextView;

    private ImageView menu;
    private Context context;
    private View dialogView;
    private List mData;
    private boolean isOpen = false;
    private RotateAnimation rotateOpen;
    private RotateAnimation rotateClose;


    private PopupWindow popWindow;
    private GridView gridView;
    private GridViewAdapter adapter = new GridViewAdapter();
    private int popupHeight;


    public PopWindowsMenu(Context context) {
        this(context, null);
    }

    public PopWindowsMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopWindowsMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contextView = inflater.inflate(R.layout.pop_windows_menu_layout, this);

        init(attrs);
        initAnim();

    }

    private void init(AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PopWindowsMenu);
        menuButtonSize = a.getDimension(R.styleable.PopWindowsMenu_menuButtonSize, 0);
        numColumns = a.getInt(R.styleable.PopWindowsMenu_numColumns, 3);
        openIcon = a.getResourceId(R.styleable.PopWindowsMenu_openImage, R.mipmap.open_icon);
        closeIcon = a.getResourceId(R.styleable.PopWindowsMenu_closeImage, R.mipmap.close_icon);
        textColor = a.getColor(R.styleable.PopWindowsMenu_textColor, getResources().getColor(R.color.colorAccent));
        menuAlpha = a.getInt(R.styleable.PopWindowsMenu_menuAlpha, 200);
        menuBackgroundColor = a.getColor(R.styleable.PopWindowsMenu_menuBackgroundColor, 0);
        menuItemHeight = a.getDimension(R.styleable.PopWindowsMenu_menuItemHeight, 38);
        textSize = a.getFloat(R.styleable.PopWindowsMenu_textSize, 14);
        menuPopMargin = a.getDimension(R.styleable.PopWindowsMenu_menuPopMargin, 0);

        menuButtonMarginBottom = (int) a.getDimension(R.styleable.PopWindowsMenu_menuButtonMarginBottom, 0);
        menuButtonMarginRight = (int) a.getDimension(R.styleable.PopWindowsMenu_menuButtonMarginRight, 0);

        a.recycle();

        menu = (ImageView) contextView.findViewById(R.id.menu);
        menu.setImageResource(openIcon);
        menu.setOnClickListener(this);

        LayoutParams params = (LayoutParams) menu.getLayoutParams();
        if (menuButtonSize != 0) {
            params.height = (int) menuButtonSize;
            params.width = (int) menuButtonSize;
        }
        params.setMargins(0, 0, menuButtonMarginRight, menuButtonMarginBottom);
        menu.setLayoutParams(params);
    }


    private void initPop() {


        dialogView = View.inflate(context, R.layout.pop_layout, null);
        View triangle = dialogView.findViewById(R.id.triangle);
        LinearLayout linearLayout = (LinearLayout) dialogView.findViewById(R.id.linear_layout);


        if (menuPopMargin != 0) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            params.setMargins((int) menuPopMargin, 0, (int) menuPopMargin, 0);
            linearLayout.setLayoutParams(params);
        }


        triangle.getBackground().setAlpha(menuAlpha);


        gridView = (GridView) dialogView.findViewById(R.id.grid_view);
        gridView.setNumColumns(numColumns);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        popWindow = new PopupWindow(dialogView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popWindow.setAnimationStyle(R.style.AnimationFade);

        popWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popWindow.setFocusable(true);
        popWindow.setOnDismissListener(this);

        // dialogView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        // int popupWidth = dialogView.getMeasuredWidth();
        //  popupHeight = dialogView.getMeasuredHeight();

    }


    private void showMenu() {
        menu.setImageResource(closeIcon);
        menu.startAnimation(rotateOpen);

        int[] location = new int[2];
        menu.getLocationOnScreen(location);

        popWindow.showAtLocation(contextView, Gravity.CENTER | Gravity.TOP, 0, location[1] - popupHeight - ScreenUtil.dip2px(context, 12));
    }

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

        if (position < menuItemCount) {
            if (listener != null) {
                selectPosition = position;
                adapter.notifyDataSetChanged();
                listener.onItemMenuClick(position);
            }
        }
        gridView.postDelayed(new Runnable() {
            @Override
            public void run() {
                popWindow.dismiss();
            }
        }, 10);
    }

    @Override
    public void onDismiss() {
        menu.setImageResource(openIcon);
        menu.startAnimation(rotateClose);
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


    public void setMenuData(List mData) {
        this.mData = mData;
        menuItemCount = mData.size();
        int i = mData.size() / numColumns;
        if (mData.size() % numColumns != 0) {
            i++;
        }

        popupHeight = ScreenUtil.dip2px(context, menuItemHeight * i);

        while (mData.size() % numColumns != 0) {
            mData.add("");
        }
        initPop();
    }


    public void setSelectPosition(int position) {
        this.selectPosition = position;
    }


    class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return mData.size();
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

            TextView textView = new TextView(context);
            textView.setHeight(ScreenUtil.dip2px(context, menuItemHeight));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(textSize);

            textView.setTextColor(textColor);
            textView.setText(mData.get(i).toString());

            textView.setBackgroundColor(menuBackgroundColor);

            textView.getBackground().setAlpha(menuAlpha);

            if (selectPosition == i) {
                textView.setBackgroundColor(menuBackgroundColor);
            }
            return textView;
        }
    }

    private OnItemMenuListener listener;

    public void setOnItemMenuClickListener(OnItemMenuListener listener) {
        this.listener = listener;
    }

    public interface OnItemMenuListener {
        void onItemMenuClick(int position);
    }

}
