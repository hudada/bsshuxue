package com.example.bsproperty.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.bean.UserObjBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;
import com.example.bsproperty.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wdxc1 on 2018/3/21.
 */

public class UserFragment02 extends BaseFragment {
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_addr)
    EditText tvAddr;
    @BindView(R.id.tv_tel)
    EditText tvTel;
    @BindView(R.id.btn_out)
    Button btnOut;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void loadData() {
        tvName.setText(MyApplication.getInstance().getUserBean().getUserName());
        if (!TextUtils.isEmpty(MyApplication.getInstance().getUserBean().getAddr())){
            tvAddr.setText(MyApplication.getInstance().getUserBean().getAddr());
        }
        if (!TextUtils.isEmpty(MyApplication.getInstance().getUserBean().getTel())){
            tvTel.setText(MyApplication.getInstance().getUserBean().getTel());
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("我的");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("保存");
        btnBack.setVisibility(View.GONE);
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_user02;
    }

    @OnClick({R.id.btn_right, R.id.btn_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_right:
                String addr = tvAddr.getText().toString().trim();
                String tel = tvTel.getText().toString().trim();
                if (TextUtils.isEmpty(addr)){
                    showToast(tvAddr.getHint().toString());
                    return;
                }
                if (TextUtils.isEmpty(tel)){
                    showToast(tvTel.getHint().toString());
                    return;
                }
                OkHttpTools.sendPost(mContext, ApiManager.USER_MODIFY)
                        .addParams("addr",addr)
                        .addParams("tel",tel)
                        .addParams("id",MyApplication.getInstance().getUserBean().getId()+"")
                        .build()
                        .execute(new BaseCallBack<UserObjBean>(mContext,UserObjBean.class) {
                            @Override
                            public void onResponse(UserObjBean userObjBean) {
                                MyApplication.getInstance().setUserBean(userObjBean.getData());
                                SpUtils.setUserBean(mContext,userObjBean.getData());
                                showToast("修改成功");
                            }
                        });
                break;
            case R.id.btn_out:
                if (SpUtils.cleanUserBean(mContext)) {
                    System.exit(0);
                }
                break;
        }
    }
}
