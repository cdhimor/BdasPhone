package cn.com.lxsoft.bdasphone.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragment;
import cn.com.lxsoft.bdasphone.ui.chart.BdasChart;
import cn.com.lxsoft.bdasphone.utils.BottomNavigationViewHelper;
import cn.com.lxsoft.bdasphone.utils.StatusBarUtils;

import cn.com.lxsoft.bdasphone.databinding.ActivityMainBinding;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.ContainerActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public MainViewModel initViewModel() {
        //View持有ViewModel的引用，如果没有特殊业务处理，这个方法可以不重写
        //return ViewModelProviders.of(this).get(LoginViewModel.class);
        //binding.ivSwichPasswrod.setB(viewModel.passwordShowSwitchOnClickCommand);
        return null;
    }

    BdasChart bdasChart;

    @Override
    public void initViewObservable() {

        StatusBarUtils.setBar(this,R.color.colorPrimary,false);

        BottomNavigationViewHelper.disableShiftMode((BottomNavigationView) binding.navigation);

        bdasChart=new BdasChart(viewModel.layoutChartViewModel);
        bdasChart.initPieChart(binding.includeChart.chartMain);

        viewModel.layoutChartViewModel.dealChartData();

       DisplayMetrics dm = new DisplayMetrics();
       getWindowManager().getDefaultDisplay().getMetrics(dm); // 为获取屏幕宽、高

        ViewGroup.LayoutParams windowLayoutParams =binding.layoutChart.getLayoutParams(); // 获取对话框当前的参数值
       windowLayoutParams.width = (int) (dm.widthPixels -32); // 宽度设置为屏幕的0.95
       windowLayoutParams.height = (int) (dm.widthPixels -32); // 高度设置为屏幕的0.6

        binding.layoutToolbarSearch.setViewModel(viewModel);

        //binding.iconTest.setImageBitmap(bitmap);
    }

}
