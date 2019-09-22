package cn.com.lxsoft.bdasphone.ui.examine;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.ImageData;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by goldze on 2018/7/18.
 */

public class FragmentCheckItemViewModel extends ItemViewModel {
    public String name;
    public String dict;

    ObservableField<String> disease;
    List<ImageData> imageList;

    public String selectStrA;
    public String selectStr1;
    public String selectStr2;

    public ObservableField<String> worker;
    public ObservableField<String> owner;
    public ObservableField<String> date;
    public ObservableField<String> dateNext;

    public FragmentCheckViewModel checkViewModel;

    public FragmentCheckItemViewModel(@NonNull BaseViewModel viewModel, String nm,ObservableField<String> di,List<ImageData> iml,String dt) {
        super(viewModel);
        this.name = nm;
        disease=di;
        imageList=iml;
        dict=dt;


        Map<String,String> dictMap=DataDict.getDictMap(dict);
        selectStrA=dictMap.get("1");
        selectStr1=dictMap.get("2");
        selectStr2=dictMap.get("3");
    }

    public FragmentCheckItemViewModel(@NonNull BaseViewModel viewModel, String wk,String ow,String dt,String dtn) {
        super(viewModel);
        name="基本信息";
        worker = new ObservableField<>(wk);
        owner=new ObservableField<>(ow);
        date=new ObservableField<>(dt);
        dateNext=new ObservableField<>(dtn);
    }

}
