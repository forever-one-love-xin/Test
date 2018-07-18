package com.example.dell.test;

import android.widget.TextView;

/*
 * 一个数据类
 * */
public class Data {
    public int user_img;
    public String user_name;
    public String ping_num;

    public Data(int user_img, String user_name, String ping_num) {
        this.user_img = user_img;
        this.user_name = user_name;
        this.ping_num = ping_num;
    }
}
