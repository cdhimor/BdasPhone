package cn.com.lxsoft.bdasphone.ui.chart;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import cn.com.lxsoft.bdasphone.app.BridgeBaseViewModel;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragment;
import cn.com.lxsoft.bdasphone.ui.browse.SlideBrowseViewModel;

public class FragmentChartViewModel extends BridgeBaseViewModel {
    public SlideBrowseViewModel slideBrowseViewModel;
    public LayoutChartViewModel layoutChartViewModel;

    public int chartID=1;
    public String leftChart="";
    public String rightChart;
    public String chartType="";
    public String codeChart="";
    protected String chartTypeName;
    ObservableField<String> obChartTypeName=new ObservableField<>();

    public ObservableBoolean bNumOrLen=new ObservableBoolean(true); //num:true,len:false


    public FragmentChartViewModel(@NonNull Application application) {

        super(application);
        slideBrowseViewModel=new SlideBrowseViewModel(application);
        layoutChartViewModel=new LayoutChartViewModel(application);
        layoutChartViewModel.parentViewModel=this;

        nActivityPosition=3;
    }

    public void openFragment(){
        startContainerActivity(BrowseFragment.class.getCanonicalName());
    }

    @Override
    public void onCreate()
    {
        slideBrowseViewModel.setItemClickListener(new SlideBrowseViewModel.OnItemClickListener() {
            @Override
            public void onItemClick(short listType, Object object) {
                String tpcode="";
                String tpright="";
                if(listType==SystemConfig.SlideList_Type_Path){
                    tpcode=((LuXian)object).getBianHao();
                    chartTypeName=((LuXian)object).getMingCheng();
                    tpright=SystemConfig.Chart_Right_Lx;
                }
                else if(listType==SystemConfig.SlideList_Type_Dept) {
                    tpcode=((DanWei)object).getDaiMa();
                    chartTypeName=((DanWei)object).getMingCheng();
                    tpright=SystemConfig.Chart_Right_Gydw;
                }
                if(!tpcode.equals(codeChart)){
                    layoutChartViewModel.chartCode=tpcode;
                    layoutChartViewModel.chartRight=tpright;
                    layoutChartViewModel.dealChartData(true);
                    setChartName();
                }
            }
        });
        chartType=DataSession.getCurrentDanWei().getDaiMa();
        chartTypeName=DataSession.getCurrentDanWei().getMingCheng();
        slideBrowseViewModel.onCreate();
    }

    public void onChartIDSelected(String type){
        leftChart=type;
        layoutChartViewModel.chartLeft=leftChart;
        layoutChartViewModel.dealChartData(true);
        setChartName();
    }

    public void setChartName(){
        String idstr="类型";
        if(leftChart.equals(SystemConfig.Chart_Left_qljspj)){
            idstr="技术状况";
        }
        obChartTypeName.set(chartTypeName+" 所属桥梁 "+idstr+" 统计");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
