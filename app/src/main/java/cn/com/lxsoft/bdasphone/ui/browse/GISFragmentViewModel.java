package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.Application;
import android.support.annotation.NonNull;

import com.amap.api.maps2d.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.entity.BridgeGisData;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import me.goldze.mvvmhabit.base.BaseViewModel;

public class GISFragmentViewModel extends BaseViewModel {
    public String sBridgeName;

    public SlideBrowseViewModel slideBrowseViewModel;
    List<LatLng> latLngList=new ArrayList<>();
    List<BridgeGisData> bridgeGisList=new ArrayList<>();



    public GISFragmentViewModel(@NonNull Application application) {
        super(application);
        slideBrowseViewModel=new SlideBrowseViewModel(application);
        slideBrowseViewModel.setItemClickListener(new SlideBrowseViewModel.OnItemClickListener() {
            @Override
            public void onItemClick(short listType, Object object) {
                /*
                String tptype="";
                if(listType==SystemConfig.SlideList_Type_Path){
                    tptype=((LuXian)object).getBianHao();
                    //chartTypeName=((LuXian)object).getMingCheng();
                }
                else if(listType==SystemConfig.SlideList_Type_Dept) {
                    tptype=((DanWei)object).getDaiMa();
                    //chartTypeName=((DanWei)object).getMingCheng();
                }
                if(!tptype.equals(chartType)){
                    //layoutChartViewModel.chartType=tptype;
                    //layoutChartViewModel.dealChartData();
                    //setChartName();
                }
                */
            }
        });
    }

    @Override
    public void onCreate() {
        //sBridgeName= DataSession.getCurrentQiaoLiang().getMingCheng();
        slideBrowseViewModel.onCreate();
    }

    public List<QiaoLiang> getGISData(){
        DataBase database = AppApplication.dataBase;
        return database.getBridgeGisData();
    }

    public QiaoLiang getBridge(String qldm){
        DataBase database = AppApplication.dataBase;
        return database.getBridge(qldm);
    }
}