package com.example.dell.test;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.test.adapter.FragmentAdapter;
import com.example.dell.test.fragments.Create_fragment;
import com.example.dell.test.fragments.Diary_fragment;
import com.example.dell.test.fragments.Join_fragment;
import com.example.dell.test.fragments.Other_fragment;
import com.example.dell.test.fragments.Write_fragment;
import com.example.dell.test.view.BubbleViewTest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView item_write, item_diary, item_create, item_join, item_other, line;
    private LinearLayout s;
    private TextView[] tabs;
    private ViewPager viewPager;
    private Write_fragment write_fragment;
    private Diary_fragment diary_fragment;
    private Create_fragment create_fragment;
    private Join_fragment join_fragment;
    private Other_fragment other_fragment;
    private List<Fragment> fragmentList = new ArrayList<Fragment> ();
    private FragmentAdapter fragmentAdapter;


    //偏移量（手机屏幕宽度 / 选项卡总数 - 选项卡长度） / 2
    private int offset = 0;
    //下划线宽度
    private int lineWidth;
    //当前选项卡的位置
    private int current_index = 0;
    private int current_color;
    //选项卡总数
    private static final int TAB_COUNT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        supportRequestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView (R.layout.main);

        //让背景与状态栏切合
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow ().getDecorView ();
            decorView.setSystemUiVisibility (
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow ().setStatusBarColor (Color.TRANSPARENT);
        }

        //初始化各组件以及下划线
        initViews ();
        initline ();


        //为ViewPager加载adpter
        fragmentAdapter = new FragmentAdapter (this.getSupportFragmentManager (), fragmentList);
        //ViewPager的缓存为5帧
        viewPager.setOffscreenPageLimit (5);
        //初始设置ViewPager选中第一帧
        viewPager.setCurrentItem (0);
        viewPager.setAdapter (fragmentAdapter);
        item_write.setTextColor (Color.parseColor ("#2cf1f7"));

        //ViewPager的监听事件
        viewPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener () {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //导航栏移动，下划线也移动
                lineSlide (position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //点击空白处，气泡消失
        final BubbleViewTest bubbleView = findViewById (R.id.bubu);
        bubbleView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                bubbleView.setVisibility (View.GONE);
                write_fragment.T = false;
            }
        });
        //为返回按钮添加事件兼听
        findViewById (R.id.back).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText (MainActivity.this, "点击返回", Toast.LENGTH_SHORT).show ();
                finish ();
            }
        });



    }

    /*
     * 初始化布局
     * */
    private void initViews() {
        item_write = (TextView) findViewById (R.id.item_write);
        item_diary = (TextView) findViewById (R.id.item_diary);
        item_create = (TextView) findViewById (R.id.item_create);
        item_join = (TextView) findViewById (R.id.item_join);
        item_other = (TextView) findViewById (R.id.item_other);
        tabs = new TextView[]{item_write, item_diary, item_create, item_join, item_other};
        //为导航栏添加点击事件
        item_write.setOnClickListener (this);
        item_diary.setOnClickListener (this);
        item_create.setOnClickListener (this);
        item_join.setOnClickListener (this);
        item_other.setOnClickListener (this);
        //初始化ViewPager
        viewPager = (ViewPager) findViewById (R.id.mainViewPager);
        //初始化fragment
        write_fragment = new Write_fragment (MainActivity.this);
        diary_fragment = new Diary_fragment ();
        create_fragment = new Create_fragment ();
        join_fragment = new Join_fragment ();
        other_fragment = new Other_fragment ();
        //为FragmentList添加数据
        fragmentList.add (write_fragment);
        fragmentList.add (diary_fragment);
        fragmentList.add (create_fragment);
        fragmentList.add (join_fragment);
        fragmentList.add (other_fragment);
    }

    /**
     * 初始化底部下划线
     */
    private void initline() {
        line = (TextView) findViewById (R.id.line);
        s = findViewById (R.id.t);
        lineWidth = line.getWidth ();
        // Android提供的DisplayMetrics可以很方便的获取屏幕分辨率
        DisplayMetrics dm = new DisplayMetrics ();
        getWindowManager ().getDefaultDisplay ().getMetrics (dm);
        int screenW = dm.widthPixels; // 获取分辨率宽度
        offset = (screenW / TAB_COUNT - lineWidth) / 2;  // 计算偏移值
        line.setWidth (offset * 2);
    }

    /*
     * 导航栏切换，下划线滑动
     * */
    private void lineSlide(int position) {
        // 下划线开始移动前的位置
        int one = offset * 2 + lineWidth;
        float fromX = one * current_index;
        // 下划线移动完毕后的位置
        float toX = (one + 10) * position;

        Log.d ("Tag", "lineSlide: " + fromX + "  " + toX);
        //当切换到第五个导航标签时，整个linearLayout左移。
        if (position == 4) {
            TextView textView = findViewById (R.id.item_write);
            DisplayMetrics dm = new DisplayMetrics ();
            getWindowManager ().getDefaultDisplay ().getMetrics (dm);
            int screenW = dm.widthPixels; // 获取分辨率宽度
            int w = screenW - 5 * textView.getWidth ();
            Log.d ("LIU", "lineSlide: " + w);
            Animation animation1 = new TranslateAnimation (0, -w - 20, 0, 0);
            s.setAnimation (animation1);
            animation1.setFillAfter (true);
            animation1.setDuration (500);
            Animation animation = new TranslateAnimation (fromX, toX - w - 20, 0, 0);
            //添加动画
            line.startAnimation (animation);
            animation.setFillAfter (true);
            animation.setDuration (500);
        } else {
            Animation animation = new TranslateAnimation (fromX, toX, 0, 0);
            //添加动画
            line.startAnimation (animation);
            animation.setFillAfter (true);
            animation.setDuration (500);
            Animation animation1 = new TranslateAnimation (0, 0, 0, 0);
            s.setAnimation (animation1);
            animation1.setFillAfter (true);
            animation1.setDuration (500);
        }
        //改变字体颜色
        current_color = tabs[position].getCurrentTextColor ();
        tabs[position].setTextColor (Color.parseColor ("#4cd3ef"));
        tabs[current_index].setTextColor (current_color);
        current_index = position;
    }


    /*
     * 导航栏的点击事件
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.item_write:
                viewPager.setCurrentItem (0, true);
                break;
            case R.id.item_diary:
                viewPager.setCurrentItem (1, true);
                break;
            case R.id.item_create:
                viewPager.setCurrentItem (2, true);
                break;
            case R.id.item_join:
                viewPager.setCurrentItem (3, true);
                break;
            case R.id.item_other:
                viewPager.setCurrentItem (4, true);
                break;
        }
    }
}
