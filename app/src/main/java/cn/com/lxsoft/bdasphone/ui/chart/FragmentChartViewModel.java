package cn.com.lxsoft.bdasphone.ui.chart;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.Nullable;
import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.DataSession;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.entity.Chart;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.ui.browse.QiaoLiangListItemViewModel;
import cn.com.lxsoft.bdasphone.ui.browse.SlideBrowseViewModel;
import cn.com.lxsoft.bdasphone.ui.examine.FragmentCheckBindingAdapter;
import cn.com.lxsoft.bdasphone.ui.examine.FragmentCheckItemViewModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class FragmentChartViewModel extends BaseViewModel {
    public SlideBrowseViewModel slideBrowseViewModel;
    public LayoutChartViewModel layoutChartViewModel;

    public int chartID=1;
    public String chartType;

    public ObservableBoolean bNumOrLen=new ObservableBoolean(true); //num:true,len:false


    public FragmentChartViewModel(@NonNull Application application) {

        super(application);
        slideBrowseViewModel=new SlideBrowseViewModel(application);
        layoutChartViewModel=new LayoutChartViewModel(application);
    }

    @Override
    public void onCreate()
    {
        slideBrowseViewModel.setItemClickListener(new SlideBrowseViewModel.OnItemClickListener() {
            @Override
            public void onItemClick(short listType, Object object) {
                String tptype="";
                if(listType==SystemConfig.SlideList_Type_Path){
                    tptype=((LuXian)object).getBianHao();
                }
                else if(listType==SystemConfig.SlideList_Type_Dept) {
                    tptype=((DanWei)object).getDaiMa();
                }
                if(!tptype.equals(chartType)){
                    chartType=tptype;
                    layoutChartViewModel.dealChartData();
                }
            }
        });
        chartType=DataSession.getCurrentDanWei().getDaiMa();
        slideBrowseViewModel.onCreate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
