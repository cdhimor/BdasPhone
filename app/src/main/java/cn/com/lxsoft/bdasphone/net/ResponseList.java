package cn.com.lxsoft.bdasphone.net;

import com.google.gson.annotations.Expose;

import java.util.List;


public class ResponseList<T> extends ResponseBase {
    //@Expose(serialize = false, deserialize = false)

    public List<T> DATA;

    public List<T> rows;

    public ResponseList(List<T> data,String code) {
        DATA = DATA;
        rows=data;
        CODE=code;
    }

}
