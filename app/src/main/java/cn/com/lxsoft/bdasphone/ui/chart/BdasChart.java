package cn.com.lxsoft.bdasphone.ui.chart;

import android.app.Activity;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.entity.BridgeChart;
import cn.com.lxsoft.bdasphone.entity.Chart;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragment;

public class BdasChart implements OnChartValueSelectedListener {
    PieChart pieChart;
    LayoutChartViewModel viewModel;
    ArrayList<Integer> chartColors;
    ArrayList<PieEntry> chartDataList = new ArrayList<>();
    int totalNum=0;
    float totalLen=0;
    TextView sumTextView=null;

    public BdasChart(LayoutChartViewModel vm){
        viewModel=vm;
    }


   public  void initPieChart(PieChart chart){

        pieChart=chart;
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 0, 5, 5);

        //Chart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setCenterTextSize(14f);
        //pieChart.setCenterText("测试报表");
        pieChart.setHoleRadius(35f);
        pieChart.setTransparentCircleRadius(40f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setDrawCenterText(true);

       pieChart.setOnChartValueSelectedListener(this);

        chartColors = new ArrayList<>();
        chartColors.add(0xFF00CC00);
        chartColors.add(0xff0000CC);
        chartColors.add(0xFFFFC400);
        chartColors.add(0xffff6600);
        chartColors.add(0xffFF3300);
        for (int c : ColorTemplate.PASTEL_COLORS)
            chartColors.add(c);


        Legend l = pieChart.getLegend();
        l.setEnabled(false);
        //l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //l.setDrawInside(false);
        //l.setXEntrySpace(7f);
        //l.setYEntrySpace(0f);
        //l.setYOffset(0f);
       viewModel.chartData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
           @Override
           public void onPropertyChanged(Observable sender, int propertyId) {
               setChartData(viewModel.chartData.get(),viewModel.chartLeft,viewModel.chartRight,viewModel.bNumOrLen.get());
           }
       });

       viewModel.bNumOrLen.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
           @Override
           public void onPropertyChanged(Observable sender, int propertyId) {
               setChartData(viewModel.chartData.get(),viewModel.chartLeft,viewModel.chartRight,viewModel.bNumOrLen.get());
           }
       });
    }

    public String getTongJiInfo(String type){
        String info="";
        if(type.equals(SystemConfig.Chart_Left_Qlfl+"_"+SystemConfig.Chart_Right_Gydw)) {
                totalNum =chartData.getNum_all();
                totalLen =chartData.getLen_all();
            int num = chartData.getNum_a();
            float len = chartData.getLen_a();
            info = "　　本单位所属桥梁共" + totalNum + "座（" + totalLen + "延米）。其中，特大桥" + num + "座(" + len + ")";

            num = chartData.getNum_b();
            len = chartData.getLen_b();
            info=info.concat("，大桥" + num + "座(" + len + "延米)");

            num = chartData.getNum_c();
            len = chartData.getLen_c();
            info=info.concat("，中桥" + num + "座(" + len + "延米)");

            num = chartData.getNum_d();
            len = chartData.getLen_d();
            info=info.concat("，小桥" + num + "座(" + len + "延米)");
        }
        return info;
    }

    ObservableList chartList;
    /*
    public void setChartData(ObservableList tplist, boolean bNum) {
        chartDataList.clear();
        totalLen = 0;
        totalNum = 0;

        chartList=tplist;

        for (int i = 0; i < tplist.size(); i++) {
            Chart chart = (Chart) tplist.get(i);
            if (bNum) {
                chartDataList.add(new PieEntry((float) chart.getNum(), chart.getName()));
                totalNum += chart.getNum();
            } else {
                chartDataList.add(new PieEntry((float) chart.getLen(), chart.getName()));
                totalLen += chart.getLen();
            }
        }

        PieDataSet pieDataSet = new PieDataSet(chartDataList, "类型");

        pieDataSet.setDrawIcons(false);

        pieDataSet.setSliceSpace(3f);
        pieDataSet.setIconsOffset(new MPPointF(0, 40));
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setColors(chartColors);


        PieData pieData = new PieData(pieDataSet);
        //data.setValueFormatter(new PercentFormatter(chartMain));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);



        //data.setDrawValues(true);

        //data.setValueTypeface(tfLight);
        pieChart.setData(pieData);

        pieChart.highlightValues(null);
        pieChart.invalidate();
        //chartMain.re

            if (bNum) {
                pieChart.setCenterText(totalNum + "\n(座)");
            } else {
                pieChart.setCenterText(totalLen + "\n(延米)");
            }
    }
    */

    BridgeChart chartData;
    public void setChartData(BridgeChart tpdata, String leftType,String rightType,boolean bNum) {
        chartData=tpdata;
        chartDataList.clear();
        List<DataDict.DictItem> list=null;

        switch (leftType){
            case SystemConfig.Chart_Left_Qlfl:
                list=DataDict.getDictList("1.1");
                break;
            case SystemConfig.Chart_Left_qljspj:
                list=DataDict.getDictList("6.0");
                break;
        }
        if (bNum) {
            chartDataList.add(new PieEntry((float) chartData.getNum_a(), list.get(0).name));
            chartDataList.add(new PieEntry((float) chartData.getNum_b(), list.get(1).name));
            chartDataList.add(new PieEntry((float) chartData.getNum_c(), list.get(2).name));
            chartDataList.add(new PieEntry((float) chartData.getNum_d(), list.get(3).name));
            if(list.size()>4)
                chartDataList.add(new PieEntry((float) chartData.getNum_d(), list.get(4).name));
            if(list.size()>5)
                chartDataList.add(new PieEntry((float) chartData.getNum_d(), list.get(5).name));

        } else {
            chartDataList.add(new PieEntry((float) chartData.getLen_a(), list.get(0).name));
            chartDataList.add(new PieEntry((float) chartData.getLen_b(), list.get(1).name));
            chartDataList.add(new PieEntry((float) chartData.getLen_c(), list.get(2).name));
            chartDataList.add(new PieEntry((float) chartData.getLen_d(), list.get(3).name));
            if(list.size()>4)
                chartDataList.add(new PieEntry((float) chartData.getLen_d(), list.get(4).name));
            if(list.size()>5)
                chartDataList.add(new PieEntry((float) chartData.getLen_d(), list.get(5).name));
        }


        totalNum=chartData.getNum_all();
        totalLen=chartData.getLen_all();

        PieDataSet pieDataSet = new PieDataSet(chartDataList, "类型");

        pieDataSet.setDrawIcons(false);

        pieDataSet.setSliceSpace(3f);
        pieDataSet.setIconsOffset(new MPPointF(0, 40));
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setColors(chartColors);


        PieData pieData = new PieData(pieDataSet);
        //data.setValueFormatter(new PercentFormatter(chartMain));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);

        //data.setDrawValues(true);

        //data.setValueTypeface(tfLight);
        pieChart.setData(pieData);

        pieChart.highlightValues(null);
        pieChart.invalidate();
        //chartMain.re

        if (bNum) {
            pieChart.setCenterText(totalNum + "\n(座)");
        } else {
            pieChart.setCenterText(totalLen + "\n(延米)");
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        String leftType=viewModel.chartLeft;
        String rightType=viewModel.chartRight;
        PieEntry pieEntry=(PieEntry)e;
        String tpname=pieEntry.getLabel();
        //searchDictList.put("m0.3", new SearchData("BASE", QiaoLiangDao.Properties.DanWeiID));
        //searchDictList.put("m0.4", new SearchData("BASE", QiaoLiangDao.Properties.LuXianID));
        //searchDictList.put("m1.1", new SearchData("BASE", QiaoLiangDao.Properties.LeiXing));
//        searchDictList.put("m6.0", new SearchData("BASE", QiaoLiangDao.Properties.PingJi));
        String str1="";
        if(rightType.equals(SystemConfig.Chart_Right_Gydw)){
            str1=SystemConfig.BuildSearchData("m0.3",viewModel.chartCode);
        }
        else{
            str1=SystemConfig.BuildSearchData("m0.4",viewModel.chartCode);
        }

       switch (tpname){
           case  "特大桥":
               tpname="1";
            break;
           case "大桥":
               tpname="2";
               break;
           case "中桥":
               tpname="3";
               break;
           case "小桥":
               tpname="4";
               break;
           case "一类":
               tpname="1";
               break;
           case "二类":
               tpname="2";
               break;
           case "三类":
               tpname="3";
               break;
           case "四类":
               tpname="4";
               break;
           case "五类":
               tpname="5";
               break;
       }

       String str2="";
       if(leftType.equals(SystemConfig.Chart_Left_Qlfl))
            str2=SystemConfig.BuildSearchData("m1.1",tpname);
       else
           str2=SystemConfig.BuildSearchData("m6.0",tpname);


        viewModel.parentViewModel.startContainerActivity(BrowseFragment.class.getCanonicalName(),SystemConfig.buildBundleSearchData(pieEntry.getLabel(),SystemConfig.BuildMutipleSearchData(str1,str2)));
        //((FragmentChartViewModel)viewModel.parentViewModel).openFragment();
    }


    @Override
    public void onNothingSelected() {

    }


}
