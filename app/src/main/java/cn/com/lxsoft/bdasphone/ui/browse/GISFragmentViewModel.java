package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.database.DataSession;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class GISFragmentViewModel extends BaseViewModel {
    public String sBridgeName;

    public SlideBrowseViewModel slideBrowseViewModel;


    public GISFragmentViewModel(@NonNull Application application) {
        super(application);
        slideBrowseViewModel=new SlideBrowseViewModel(application);
    }

    @Override
    public void onCreate() {
        //sBridgeName= DataSession.getCurrentQiaoLiang().getMingCheng();
        slideBrowseViewModel.onCreate();

    }




}
