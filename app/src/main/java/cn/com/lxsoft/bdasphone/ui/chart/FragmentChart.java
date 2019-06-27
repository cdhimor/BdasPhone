package cn.com.lxsoft.bdasphone.ui.chart;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.FragmentChartBinding;
import cn.com.lxsoft.bdasphone.entity.Chart;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import cn.com.lxsoft.bdasphone.utils.BottomNavigationViewHelper;
import cn.com.lxsoft.bdasphone.utils.RadioGroupUtils;
import cn.com.lxsoft.bdasphone.utils.StatusBarUtils;
import me.goldze.mvvmhabit.base.BaseFragment;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.TabView;

import static android.app.Activity.RESULT_OK;


public class FragmentChart extends BaseFragment<FragmentChartBinding, FragmentChartViewModel> {
    @Override
    public void initParam() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_chart;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }



    BdasChart bdasChart;
    @Override
    public void initData() {
        //StatusBarUtils.setBar(this.getActivity(),R.color.colorPrimary,false);

        BottomNavigationViewHelper.disableShiftMode((BottomNavigationView) binding.navigation);

        FragmentChart bf=this;
        binding.buttonChartSelPath.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                viewModel.slideBrowseViewModel.initData(SystemConfig.SlideList_Type_Path);
                ActivityUtils.showDrawerPanel(binding.drawerLayout,bf.getActivity(),true);
            }
        });

        binding.buttonChartSelDept.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                viewModel.slideBrowseViewModel.initData(SystemConfig.SlideList_Type_Dept);
                ActivityUtils.showDrawerPanel(binding.drawerLayout,bf.getActivity(),true);
            }
        });

        ActivityUtils.initRecyclerView(binding.layoutSlideList.idDrawerList,this.getContext());

        bdasChart=new BdasChart(viewModel.layoutChartViewModel);
        bdasChart.initPieChart(binding.includeChart.chartMain);



        //Chart.setRotationAngle(0);
        // enable rotation of the Chart by touch
        //Chart.setRotationEnabled(true);
        //Chart.setHighlightPerTapEnabled(true);

        //Chart.setOnChartValueSelectedListener(this);
        //Chart.animateY(1400, Easing.EaseInOutQuad);



        Activity activity=this.getActivity();
        binding.floatButtonChartSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ActivityUtils.showDrawerPanelStart(binding.drawerLayout,activity,true);
            }
        });

    }

    @Override
    public void initViewObservable() {
        FragmentChart bf=this;

        viewModel.slideBrowseViewModel.bSlideOpen.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if(!viewModel.slideBrowseViewModel.bSlideOpen.get())
                    ActivityUtils.showDrawerPanel(binding.drawerLayout,bf.getActivity(),false);
            }
        });


        viewModel.bNumOrLen.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                viewModel.layoutChartViewModel.bNumOrLen.set(viewModel.bNumOrLen.get());
            }
        });

        viewModel.layoutChartViewModel.dealChartData();
    }
}
