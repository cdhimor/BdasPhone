package cn.com.lxsoft.bdasphone.ui.chart;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.util.List;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.DataSession;
import cn.com.lxsoft.bdasphone.entity.Chart;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.ui.browse.SlideBrowseViewModel;
import me.goldze.mvvmhabit.base.BaseViewModel;

public class LayoutChartViewModel extends BaseViewModel {

    public int chartID=1;
    public String chartType;

    public ObservableList<Chart> observableListChart=new ObservableArrayList<>();
    public ObservableBoolean bNumOrLen=new ObservableBoolean(true); //num:true,len:false


    public LayoutChartViewModel(@NonNull Application application) {

        super(application);
    }

    @Override
    public void onCreate()
    {
        //
    }

    public ObservableList<Chart> dealChartData(){
        if(chartType==null)
            chartType=DataSession.getCurrentDanWei().getDaiMa();
        observableListChart.clear();
        DataBase database = AppApplication.dataBase;
        List tplist=database.getChartData(chartID,chartType);
        observableListChart.addAll(tplist);
        return observableListChart;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
