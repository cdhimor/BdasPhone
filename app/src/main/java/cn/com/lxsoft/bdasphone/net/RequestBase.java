package cn.com.lxsoft.bdasphone.net;

import java.util.ArrayList;
import java.util.List;

public class RequestBase<T> {
    public List<T> AddItems;
    public List<T> EditItems;
    public List<T> DeleteItems;

    public RequestBase(){
        AddItems=new ArrayList<>();
        EditItems=new ArrayList<>();
        DeleteItems=new ArrayList<>();
    }
}
