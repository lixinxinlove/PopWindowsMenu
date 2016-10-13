package com.eventmosh.popwindowsmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements popWindowsMenu.OnItemMenuListener {


    private popWindowsMenu mPopWindowsMenu;


    private List list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPopWindowsMenu = (popWindowsMenu) findViewById(R.id.pop_windows_menu);

        for (int i = 0; i < 7; i++) {
            list.add("lee" + i);
        }

        mPopWindowsMenu.setMenuItems(list);
        mPopWindowsMenu.setOnItemMenuListener(this);

    }

    @Override
    public void onItemMenuClick(int position) {
        Toast.makeText(this, "lee" + position, Toast.LENGTH_SHORT).show();
    }
}
