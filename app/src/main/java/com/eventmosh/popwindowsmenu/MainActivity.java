package com.eventmosh.popwindowsmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopWindowsMenu.OnItemMenuListener {


    private PopWindowsMenu mPopWindowsMenu;


    private List list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPopWindowsMenu = (PopWindowsMenu) findViewById(R.id.pop_windows_menu);

        for (int i = 0; i < 8; i++) {
            list.add("lee" + i);
        }

        mPopWindowsMenu.setMenuData(list);
        mPopWindowsMenu.setOnItemMenuClickListener(this);
        mPopWindowsMenu.setSelectPosition(3);
    }

    @Override
    public void onItemMenuClick(int position) {
        Toast.makeText(this, "lee" + position, Toast.LENGTH_SHORT).show();
    }
}
