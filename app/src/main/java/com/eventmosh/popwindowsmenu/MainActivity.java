package com.eventmosh.popwindowsmenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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


    private void save() {
        try {
            FileOutputStream fos = openFileOutput("lxx.txt", Activity.MODE_PRIVATE);
            fos.write("lixinin".getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read() {
        try {
            FileInputStream fis = openFileInput("lxx.txt");
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (fis.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            fis.close();
            arrayOutputStream.close();
            String content = new String(arrayOutputStream.toByteArray());
            content=content.trim();
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
