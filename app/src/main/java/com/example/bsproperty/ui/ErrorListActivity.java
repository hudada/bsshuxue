package com.example.bsproperty.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.BaseResponse;
import com.example.bsproperty.bean.QuestionBean;
import com.example.bsproperty.bean.QuestionListBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ErrorListActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private ArrayList<QuestionBean> mData;
    private ErrorListActivity.MyAdapter adapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("错题库");
        mData = new ArrayList<>();
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ErrorListActivity.MyAdapter(mContext, R.layout.item_error, mData);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("提示")
                        .setMessage("是否删除该错题？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OkHttpTools.sendGet(mContext, ApiManager.ERROR_DEL)
                                        .addParams("qid", mData.get(position).getId() + "")
                                        .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                                        .build()
                                        .execute(new BaseCallBack<BaseResponse>(mContext, BaseResponse.class) {
                                            @Override
                                            public void onResponse(BaseResponse baseResponse) {
                                                showToast("删除成功");
                                                mData.remove(position);
                                                adapter.notifyDataSetChanged(mData);
                                            }
                                        });
                            }
                        }).show();
            }
        });
        rvList.setAdapter(adapter);
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_error_list;
    }

    @Override
    protected void loadData() {
        OkHttpTools.sendGet(mContext, ApiManager.ERROR_LIST)
                .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                .build()
                .execute(new BaseCallBack<QuestionListBean>(mContext, QuestionListBean.class) {
                    @Override
                    public void onResponse(QuestionListBean questionListBean) {
                        mData = (ArrayList<QuestionBean>) questionListBean.getData();
                        adapter.notifyDataSetChanged(mData);
                    }
                });
    }


    @OnClick({R.id.btn_back, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                this.finish();
                break;

            case R.id.btn_right:
                break;
        }
    }

    private class MyAdapter extends BaseAdapter<QuestionBean> {

        public MyAdapter(Context context, int layoutId, ArrayList<QuestionBean> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, QuestionBean questionBean, int position) {
            holder.setText(R.id.tv01, "问题：" + questionBean.getQu());
            holder.setText(R.id.tv02, "答案：" + questionBean.getAn());
        }
    }
}

