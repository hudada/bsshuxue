package com.example.bsproperty.ui;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.QuestionBean;
import com.example.bsproperty.bean.QuestionObjBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.tv_answer)
    TextView tvAnswer;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.ll_gif)
    RelativeLayout llGif;
    @BindView(R.id.iv_gif)
    ImageView ivGif;
    @BindView(R.id.btn_go)
    Button btnGo;


    private ArrayList<String> mData;
    private MyAdapter adapter;
    private QuestionBean questionBean;
    private int limit;

    private int rightCount = 0;
    private SoundPool soundPool;
    int soundID_ok, soundID_right, soundID_error;

    @Override
    protected void initView(Bundle savedInstanceState) {
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        soundID_ok = soundPool.load(getApplicationContext(), R.raw.ok, 1);
        soundID_right = soundPool.load(getApplicationContext(), R.raw.right, 1);
        soundID_error = soundPool.load(getApplicationContext(), R.raw.error, 1);
        tvTitle.setText("练一练");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("下一题");
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(i + "");
        }
        mData.add("取消");
        mData.add("确认");
        rvList.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new MyAdapter(mContext, R.layout.item_answer, mData);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position) {
                if (mData.get(position).equals("取消")) {
                    tvAnswer.setText("");
                } else if (mData.get(position).equals("确认")) {
                    String curr = tvAnswer.getText().toString().trim();
                    if (curr.equals("")) {
                        showToast("请先输入你的答案*-*");
                    } else {
                        if (curr.equals(questionBean.getAn())) {
                            soundPool.play(soundID_right,
                                    0.8f, 0.8f, 1, 0, 1.0f);
                            rightCount++;
                            if (rightCount >= 5) {
                                rightCount = 0;
                                showGifView();
                            }
                            showToast("回答正确");
                            rightNext();
                        } else {
                            showToast("回答错误");
                            soundPool.play(soundID_error,
                                    0.8f, 0.8f, 1, 0, 1.0f);
                            errorNext();
                        }

                    }
                } else {
                    String an = mData.get(position);
                    String curr = tvAnswer.getText().toString().trim();
                    tvAnswer.setText(curr + an);
                }
            }
        });
        rvList.setAdapter(adapter);

    }

    private void showGifView() {
        llGif.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(R.drawable.ok).asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivGif);
        soundPool.play(soundID_ok,
                0.8f, 0.8f, 1, 0, 1.0f);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_test;
    }

    @Override
    protected void loadData() {
        Intent intent = getIntent();
        limit = intent.getIntExtra("limit", 6);

        loadWebData();
    }

    private void loadWebData() {
        OkHttpTools.sendGet(mContext, ApiManager.QU_LIST)
                .addParams("type", limit + "")
                .build()
                .execute(new BaseCallBack<QuestionObjBean>(mContext, QuestionObjBean.class) {
                    @Override
                    public void onResponse(QuestionObjBean questionObjBean) {
                        initData(questionObjBean);
                    }
                });
    }

    private void initData(QuestionObjBean questionObjBean) {
        questionBean = questionObjBean.getData();
        tvTest.setText(questionBean.getQu());
        tvAnswer.setText("");
    }

    private void rightNext() {
        OkHttpTools.sendGet(mContext, ApiManager.QU_RIGHT)
                .addParams("type", limit + "")
                .addParams("qid", questionBean.getId() + "")
                .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                .build()
                .execute(new BaseCallBack<QuestionObjBean>(mContext, QuestionObjBean.class) {
                    @Override
                    public void onResponse(QuestionObjBean questionObjBean) {
                        initData(questionObjBean);
                    }
                });
    }

    private void errorNext() {
        OkHttpTools.sendGet(mContext, ApiManager.QU_ERROR)
                .addParams("type", limit + "")
                .addParams("qid", questionBean.getId() + "")
                .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                .build()
                .execute(new BaseCallBack<QuestionObjBean>(mContext, QuestionObjBean.class) {
                    @Override
                    public void onResponse(QuestionObjBean questionObjBean) {
                        initData(questionObjBean);
                    }
                });
    }

    @OnClick({R.id.btn_back, R.id.btn_right, R.id.btn_go, R.id.ll_gif})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.btn_right:
                loadWebData();
                break;
            case R.id.btn_go:
                llGif.setVisibility(View.GONE);
                break;

        }
    }

    private class MyAdapter extends BaseAdapter<String> {

        public MyAdapter(Context context, int layoutId, ArrayList<String> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseAdapter.BaseViewHolder holder, String string, int position) {
            holder.setText(R.id.tv01, string);
        }
    }
}
