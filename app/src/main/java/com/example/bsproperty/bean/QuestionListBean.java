package com.example.bsproperty.bean;

import java.util.List;

public class QuestionListBean extends BaseResponse {
    private List<QuestionBean> data;

    public List<QuestionBean> getData() {
        return data;
    }

    public void setData(List<QuestionBean> data) {
        this.data = data;
    }
}
