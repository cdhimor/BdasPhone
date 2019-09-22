package cn.com.lxsoft.bdasphone.ui.chart;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.util.List;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.BridgeBaseViewModel;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.entity.BridgeChart;
import cn.com.lxsoft.bdasphone.entity.Chart;
import cn.com.lxsoft.bdasphone.net.BridegeNetObserver;
import cn.com.lxsoft.bdasphone.net.ResponseList;
import me.goldze.mvvmhabit.base.BaseViewModel;

public class LayoutChartViewModel extends BridgeBaseViewModel {

    public String chartRight=SystemConfig.Chart_Right_Gydw;
    public String chartLeft=SystemConfig.Chart_Left_Qlfl;
    public String chartCode="";
    public BaseViewModel parentViewModel;

    public ObservableList<Chart> observableListChart=new ObservableArrayList<>();
    public ObservableField<BridgeChart> chartData=new ObservableField<>();
    public ObservableBoolean bNumOrLen=new ObservableBoolean(true); //num:true,len:false

    public LayoutChartViewModel(@NonNull Application application) {

        super(application);
    }

    @Override
    public void onCreate()
    {
        //
    }

    public ObservableList<Chart> dealChartData(Boolean bFromNet){
        if(chartCode.isEmpty())
            chartCode=DataSession.getCurrentUser().getDanWeiID();
        observableListChart.clear();
        String tptype=chartLeft+"_"+chartRight;
        BridgeChart tpData=AppApplication.dataBase.getChartData(tptype,chartCode);
        if(tpData==null && bFromNet==true) {
            subscribeBase.getReportData(DataSession.getCurrentUser().getDanWeiID(), tptype)
                    .subscribe(new BridegeNetObserver<ResponseList<BridgeChart>>() {
                        @Override
                        public void onNext(ResponseList<BridgeChart> resData) {
                            List<BridgeChart> tplist = resData.DATA;
                            AppApplication.dataBase.insertChartData(tplist,resData.CODE);
                            BridgeChart tpData=AppApplication.dataBase.getChartData(resData.CODE,chartCode);
                            chartData.set(tpData);
                        }
                    });
        }
        if(tpData!=null)
            chartData.set(tpData);
        //observableListChart.addAll(tplist);
        return observableListChart;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
