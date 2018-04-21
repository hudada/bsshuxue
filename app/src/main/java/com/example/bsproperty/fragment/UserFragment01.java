package com.example.bsproperty.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsproperty.R;
import com.example.bsproperty.ui.ErrorListActivity;
import com.example.bsproperty.ui.TestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wdxc1 on 2018/3/21.
 */

public class UserFragment01 extends BaseFragment {


    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        btnBack.setVisibility(View.GONE);
        btnRight.setVisibility(View.VISIBLE);
        tvTitle.setText("首页");
        btnRight.setText("错题库");
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_user01;
    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,R.id.btn_right})
    public void onViewClicked(View view) {
        Intent intent=new Intent(mContext, TestActivity.class);
        switch (view.getId()) {
            case R.id.btn1:
                intent.putExtra("limit",6);
                startActivity(intent);
                break;
            case R.id.btn2:
                intent.putExtra("limit",7);
                startActivity(intent);
                break;
            case R.id.btn3:
                intent.putExtra("limit",9);
                startActivity(intent);
                break;
            case R.id.btn4:
                intent.putExtra("limit",9);
                startActivity(intent);
                break;
            case R.id.btn5:
                intent.putExtra("limit",10);
                startActivity(intent);
                break;
            case R.id.btn_right:
                startActivity(new Intent(mContext, ErrorListActivity.class));
                break;
        }
    }
}
