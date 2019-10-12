package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ColumnarView columnar_view;
    ArrayList<ColumnarBean> columnarBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        columnar_view = findViewById(R.id.columnar_view);
        columnarBeans.add(new ColumnarBean(15, "阳光力：15分"));
        columnarBeans.add(new ColumnarBean(10, "温暖力：10分"));
        columnarBeans.add(new ColumnarBean(30, "睿智力：30分"));
        columnarBeans.add(new ColumnarBean(20, "底蕴力：20分"));
        columnarBeans.add(new ColumnarBean(30, "职业力：30分"));
        columnar_view.setNewData(columnarBeans).setNarFull(100).build();
    }
}
