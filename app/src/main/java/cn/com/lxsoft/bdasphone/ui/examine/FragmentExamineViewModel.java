package cn.com.lxsoft.bdasphone.ui.examine;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.BridgeBaseViewModel;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBaseGreenImpl;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.ImageData;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.PatrolTemp;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.net.BridegeNetObserver;
import cn.com.lxsoft.bdasphone.net.RequestBase;
import cn.com.lxsoft.bdasphone.net.ResponseInfo;
import cn.com.lxsoft.bdasphone.ui.browse.ContentFragment;
import cn.com.lxsoft.bdasphone.ui.browse.QiaoLiangListItemViewModel;
import cn.com.lxsoft.bdasphone.utils.ConvertUtils;
import me.goldze.mvvmhabit.http.NetworkUtil;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class FragmentExamineViewModel extends BridgeBaseViewModel {
    QiaoLiang bridge;
    public QiaoLiangListItemViewModel qiaoLiangViewModel;
    boolean bNewData = false;
    public String sBridgeName="";

    public FragmentExamineViewModel(@NonNull Application application){
        super(application);
    }

    @Override
    public void onCreate() {
        bridge=DataSession.getCurrentQiaoLiang();
        sBridgeName=bridge.getMingCheng();
        qiaoLiangViewModel = new QiaoLiangListItemViewModel(this, bridge);
        //qiaoLiangViewModel.bCanClick = false;
        qiaoLiangViewModel.setItemClickListener(new QiaoLiangListItemViewModel.OnItemClickListener() {
            @Override
            public void onClick(QiaoLiang tpQiaoLiang) {
                //oStringChooseQiaoLiang.set(tpQiaoLiang.getDaiMa());
                DataSession.setCurrentQiaoLiang(tpQiaoLiang);
                startContainerActivity(ContentFragment.class.getCanonicalName());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
