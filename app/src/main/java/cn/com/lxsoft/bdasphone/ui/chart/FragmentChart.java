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
import android.widget.RadioGroup;


import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.FragmentChartBinding;
import cn.com.lxsoft.bdasphone.entity.Chart;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import cn.com.lxsoft.bdasphone.utils.BottomNavigationViewHelper;
import me.goldze.mvvmhabit.base.BaseFragment;

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

        binding.tableLayoutChartSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String  leftChart=SystemConfig.Chart_Left_Qlfl;
                if(checkedId==R.id.rdbt_chart_pingJia)
                    leftChart=SystemConfig.Chart_Left_qljspj;
                viewModel.onChartIDSelected(leftChart);
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

        viewModel.obChartTypeName.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.tvChartInfo.setText(viewModel.obChartTypeName.get());
            }
        });

        viewModel.layoutChartViewModel.dealChartData(true);
        viewModel.setChartName();
    }
}
