package com.example.dell.test.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.test.CallBackTest;
import com.example.dell.test.Data;
import com.example.dell.test.R;
import com.example.dell.test.adapter.UserTalkAdapter;
import com.example.dell.test.utils.HttpUtil;
import com.example.dell.test.view.BubbleViewTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/*
 * 主要的fragment
 * */
@SuppressLint("ValidFragment")
public class Write_fragment extends Fragment {
    private static ListView mylistview;
    private String[] strings;
    private List<Data> datas;
    private ListView listView;
    public boolean T = false;
    static Context mcontext;
    private static View view1;
    private static final String url = null;
    public Write_fragment(Context mcontext) {
        this.mcontext = mcontext;
    }

    public static void SetCallBackTest(CallBackTest callBack) {
        if (callBack != null) {
            callBack.demo (view1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate (R.layout.fragment_zong, container, false);
        //为三个按钮添加点击事件，根据传入的参数，设置气泡中listview的数据
        view.findViewById (R.id.test_btn).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Write_fragment.this.view1 = view.findViewById (R.id.test_t);
                Write_fragment.this.mylistview = getActivity ().findViewById (R.id.test_list);
                strings = new String[]{"全部学科", "数学", "化学", "手工"};
                T = BubbleViewTest.setListView (mylistview, getContext (), view1, strings);
                if (!T) {
                    getActivity ().findViewById (R.id.bubu).setVisibility (View.VISIBLE);
                    T = true;
                } else {
                    getActivity ().findViewById (R.id.bubu).setVisibility (View.GONE);
                    T = false;
                }
            }
        });

        view.findViewById (R.id.ping_btn).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                view.findViewById (R.id.ping_btn).getLocationInWindow (location);
                Log.d (TAG, "onClick: X:" + location[0] + "  Y:" + location[1]);
                Write_fragment.this.view1 = view.findViewById (R.id.ping);
                Write_fragment.this.mylistview = getActivity ().findViewById (R.id.test_list);
                strings = new String[]{"学员评价", "老师评价", "用户评价", "系统评价"};
                T = BubbleViewTest.setListView (mylistview, getContext (), view1, strings);
                if (!T) {
                    getActivity ().findViewById (R.id.bubu).setVisibility (View.VISIBLE);
                    T = true;
                } else {
                    getActivity ().findViewById (R.id.bubu).setVisibility (View.GONE);
                    T = false;
                }
            }
        });

        view.findViewById (R.id.time_btn).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Write_fragment.this.view1 = view.findViewById (R.id.time);
                Write_fragment.this.mylistview = getActivity ().findViewById (R.id.test_list);
                strings = new String[]{"18-10-01-18-10-03", "18-10-04-18-10-06",
                        "18-10-07-18-10-09", "18-10-10-18-10-12"};
                T = BubbleViewTest.setListView (mylistview, getContext (), view1, strings);
                if (!T) {
                    getActivity ().findViewById (R.id.bubu).setVisibility (View.VISIBLE);
                    T = true;
                } else {
                    getActivity ().findViewById (R.id.bubu).setVisibility (View.GONE);
                    T = false;
                }
            }
        });
        //设置write_fragement的listview，adpter为自定义的adpter，继承自BaseAdapter
        listView = view.findViewById (R.id.user_lists);
        datas = new ArrayList<> ();
        for (int i = 0; i < 10; i++) {
            Data data = new Data (R.drawable.user_img, "林正东", String.valueOf (4 + i));
            datas.add (data);
        }
        UserTalkAdapter adapter = new UserTalkAdapter (getContext (), datas);
        listView.setAdapter (adapter);

        return view;
    }

    private void init(ListView listView) {
        datas = new ArrayList<> ();
        sendRequestWithOkHttp (url);
    }

    private void sendRequestWithOkHttp(final String url) {
        new Thread (new Runnable () {
            @Override
            public void run() {
                HttpUtil.sendOkHttpRequest (url, new okhttp3.Callback () {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText (mcontext,"数据读取失败！",Toast.LENGTH_SHORT).show ();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body ().string ();
                        datas = parseJsonWithJSONObject (responseData);
                        getActivity ().runOnUiThread (new Runnable () {
                            @Override
                            public void run() {
                                UserTalkAdapter adapter = new UserTalkAdapter (getContext (), datas);
                                listView.setAdapter (adapter);
                                listView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    }
                                });
                            }
                        });
                    }
                });
            }
        }).start ();
    }

    private List<Data> parseJsonWithJSONObject(String JsonData) {
        List<Data> dataList = new ArrayList<> ();
        if (!JsonData.isEmpty ()) {

        }

        return dataList;
    }
}
