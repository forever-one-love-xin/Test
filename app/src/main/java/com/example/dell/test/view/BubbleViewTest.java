package com.example.dell.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.test.CallBackTest;
import com.example.dell.test.R;
import com.example.dell.test.fragments.Write_fragment;

/*
 * 1.要自定义view首先要创建一个 类继承View类如下：
 * 2.就是重写他的3个构造方法，一个属性的构造方法调用两个属性的构造方法两个属性的构造方法调用三个属性的构造方法，
 * 最后的初始化数据都放在第三个中，这样写不管调用哪一个构造方法，初始化的数据都不会空指针。
 * 3.以上写完之后就要去定义我的view的属性，属性在res下面创建一个attrs.xml页面。
 * 这个页面的作用就是定义我们的参数，不把参数写死，在布局中我们可以任意定义大小尺寸方法如下：
 * 4.参数定义好了我们就要使用，接下来就是如何使用刚刚定义的参数，
 * 在哪里使用两个问题:首先是在我们布局中使用我们的参数，在跟布局中定义：
 * 5.参数定义好了，下面就是到我们的view类中去获取参数值来使用了。
 * 我们之前说了初始化数据都要放到第三个构造方法中去，所以获取参数就是在第三个构造方法中获取
 * */
public class BubbleViewTest extends LinearLayout implements CallBackTest {
    private Paint paint;
    private Paint paint1;
    //圆角半径
    private float ridus;
    //背景颜色
    private int popu_bg = 0xFF4081;
    //箭头方向
    private String direction;
    //是否对应控件居中
    private int mdie;
    private float widthSize;
    private float heightSize;
    private float width;

    private static ListView listView;

    //遮罩层颜色值
    private static final int TBG = Color.argb (50, 0, 0, 0);
    //气泡背景颜色值
    private static final int BG = Color.argb (255, 255, 255, 255);

    public BubbleViewTest(Context context) {
        this (context, null);

    }

    public BubbleViewTest(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public BubbleViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
        //获取属性
        TypedArray a = context.obtainStyledAttributes (attrs, R.styleable.BubuView, 0, 0);
        ridus = a.getDimension (R.styleable.BubuView_ridus, 10);
        popu_bg = a.getColor (R.styleable.BubuView_bubule_bg, BG);
        direction = a.getString (R.styleable.BubuView_direction);
        mdie = a.getInt (R.styleable.BubuView_arrows_mediate, 1);
        a.recycle ();
        paint = new Paint ();
        paint1 = new Paint ();
        //设置抗锯齿
        paint.setAntiAlias (true);
        //设置填充
        paint.setStyle (Paint.Style.FILL_AND_STROKE);
        //设置防抖动
        paint.setDither (true);
        paint.setColor (popu_bg);
        paint1.setColor (TBG);
    }

    //必不可少的方法就两个，一个是测量view的方法onMersure（）和绘制view的方法onDraw（）；
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure (widthMeasureSpec, heightMeasureSpec);
        //获得当前view的宽高限制的类型
        int widthMode = MeasureSpec.getMode (widthMeasureSpec);
        int heightMode = MeasureSpec.getMode (heightMeasureSpec);
        //获得view的默认尺寸
        widthSize = MeasureSpec.getSize (widthMeasureSpec);
        heightSize = MeasureSpec.getSize (heightMeasureSpec);
        //判断view的mode类型
        //这种是 warp_parent 类型 就是view的宽高不确定，所以我们要给view 赋值。是在 dimen.xml 里面写的
        //如果我们布局中用wrap_parent 就是MeasureSpec.AT_MOST这样的模型。就是我们view没有值。所以我们要给他赋值。
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            widthSize = getResources ().getDimension (R.dimen.pwidth);
            heightSize = getResources ().getDimension (R.dimen.pheight);
        }
        //最后把宽，高设置到view中
        setMeasuredDimension ((int) widthSize, (int) heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Write_fragment.SetCallBackTest (this);
        super.onDraw (canvas);
        draws (canvas, width);
    }

    public void draws(Canvas canvas, float width) {
        //画阴影遮罩，先画的显示在下面
        RectF rectF = new RectF (0, 0, widthSize, heightSize);
        canvas.drawRoundRect (rectF, 0, 0, paint1);

        canvas.save ();
        //该方法是绘制圆角矩形的主要方法，同时也可以通过设置画笔的空心效果来绘制空心的圆角矩形。
        //rect：RectF对象。
        //rx：x方向上的圆角半径。
        //ry：y方向上的圆角半径。
        //paint：绘制时所使用的画笔。
        RectF rectF1 = new RectF (40, 540, widthSize - 40, heightSize / 2 + 100);
        canvas.drawRoundRect (rectF1, ridus, ridus, paint);
        //画三角形（这里是基于path路径的绘制）
        Path path = new Path ();
        switch (direction) {
            case "LEFT":
                Log.d ("Tag", "getActivity" + widthSize + "   " + heightSize);
                path.moveTo (50, (heightSize - 540) / 2 - 50);
                path.lineTo (0, (heightSize - 540) / 2);
                path.lineTo (50, (heightSize - 540) / 2 + 50);
                path.close ();
                canvas.drawPath (path, paint);
                break;
            case "RIGHT":
                Log.d ("Tag", "getActivity" + widthSize + "   " + heightSize);
                path.moveTo (widthSize - 50, (heightSize - 540) / 2 - 50);
                path.lineTo (widthSize, (heightSize - 540) / 2);
                path.lineTo (widthSize - 50, (heightSize - 540) / 2 + 50);
                path.close ();
                canvas.drawPath (path, paint);
                break;
            case "NONE":
            case "TOP":
                Log.d ("Tag", "getActivity" + widthSize + "   " + heightSize);
                path.moveTo ((widthSize / 2) - 50 - width, 560);
                path.lineTo (widthSize / 2 - width, 500);
                path.lineTo ((widthSize / 2) + 50 - width, 560);
                path.close ();
                canvas.drawPath (path, paint);
                break;
            case "BOTTOM":
                Log.d ("Tag", "getActivity" + widthSize + "   " + heightSize);
                path.moveTo ((widthSize / 2) - 50, heightSize / 2 + 100);
                path.lineTo (widthSize / 2, heightSize / 2 + 120);
                path.lineTo ((widthSize / 2) + 50, heightSize / 2 + 100);
                path.close ();
                canvas.drawPath (path, paint);
        }
    }

    /*
     * 根据传过来的按钮id设置气泡箭头偏移量
     * */
    @Override
    public void demo(View view) {
        if (mdie == 0) {
            switch (view.getId ()) {
                case R.id.test_t:
                    View view1 = view.getRootView ().findViewById (R.id.test_btn);
                    mediateArrow (view1);
                    break;
                case R.id.ping:
                    view1 = view.getRootView ().findViewById (R.id.ping_btn);
                    mediateArrow (view1);
                    break;
                case R.id.time:
                    view1 = view.getRootView ().findViewById (R.id.time_btn);
                    mediateArrow (view1);
                    break;
            }
        } else {
            width = 0;
        }
    }

    private void mediateArrow(View view) {
        int[] location = new int[2];
        view.getLocationInWindow (location);
        this.width = widthSize / 2 - (location[0] + view.getWidth () / 2);
    }

    /*
     * 对外暴露的一个方法，根据传入的参数，设置Listview中的数据
     * @listview：需要设置数据的listview
     * @context: 传入的上下文对象
     * @view：传入在哪个控件下面生成所对应的气泡
     * @strings：传入listview中的数据对象
     * 点击气泡其他地方消失气泡和遮罩层，所以返回为false
     * */
    public static boolean setListView(ListView listView, final Context context, View view, final String[] strings) {
        BubbleViewTest.listView = listView;
        BubbleViewTest.listView.getAdapter ();
        ArrayAdapter adapter = new ArrayAdapter (context, android.R.layout.simple_list_item_1, strings);
        BubbleViewTest.listView.setAdapter (adapter);
        Log.d ("LIU", "setListView:  awdawa" + BubbleViewTest.listView.getAdapter () + "//////////  " + view.getId ());
        BubbleViewTest.listView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BubbleViewTest bubbleViewTest = view.getRootView ().findViewById (R.id.bubu);
                Toast.makeText (view.getContext (), "你点击了" + strings[position], Toast.LENGTH_SHORT).show ();
                bubbleViewTest.setVisibility (View.GONE);
            }
        });

        return false;
    }
}