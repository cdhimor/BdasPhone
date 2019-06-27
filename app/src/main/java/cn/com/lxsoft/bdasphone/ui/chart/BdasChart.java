package cn.com.lxsoft.bdasphone.ui.chart;

import android.app.Activity;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import cn.com.lxsoft.bdasphone.entity.Chart;

public class BdasChart {
    PieChart pieChart;
    LayoutChartViewModel viewModel;
    ArrayList<Integer> chartColors;
    ArrayList<PieEntry> chartDataList = new ArrayList<>();

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
        pieChart.setCenterText("测试报表");
        pieChart.setHoleRadius(35f);
        pieChart.setTransparentCircleRadius(40f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setDrawCenterText(true);

        chartColors = new ArrayList<>();
        chartColors.add(0xFF00CC00);
        chartColors.add(0xff0000CC);
        chartColors.add(0xFFFFC400);
        chartColors.add(0xffff6600);
        chartColors.add(0xffFF3300);
        for (int c : ColorTemplate.PASTEL_COLORS)
            chartColors.add(c);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        //l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

       viewModel.observableListChart.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Chart>>() {
           @Override
           public void onChanged(ObservableList<Chart> sender) {

           }

           @Override
           public void onItemRangeChanged(ObservableList<Chart> sender, int positionStart, int itemCount) {

           }

           @Override
           public void onItemRangeInserted(ObservableList<Chart> sender, int positionStart, int itemCount) {
               setChartData(sender,viewModel.bNumOrLen.get());
           }

           @Override
           public void onItemRangeMoved(ObservableList<Chart> sender, int fromPosition, int toPosition, int itemCount) {

           }

           @Override
           public void onItemRangeRemoved(ObservableList<Chart> sender, int positionStart, int itemCount) {

           }
       });

       viewModel.bNumOrLen.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
           @Override
           public void onPropertyChanged(Observable sender, int propertyId) {
               setChartData(viewModel.observableListChart,viewModel.bNumOrLen.get());
           }
       });

    }


    public void setChartData(ObservableList tplist, boolean bNum){
        chartDataList.clear();

        for(int i=0;i<tplist.size();i++){
            Chart chart=(Chart)tplist.get(i);
            if(bNum)
                chartDataList.add(new PieEntry((float)chart.getNum(),chart.getName()));
            else
                chartDataList.add(new PieEntry((float)chart.getLen(),chart.getName()));
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
    }
}
