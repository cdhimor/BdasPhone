package cn.com.lxsoft.bdasphone.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.Observable;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragment;
import cn.com.lxsoft.bdasphone.ui.browse.ContentFragment;
import cn.com.lxsoft.bdasphone.ui.chart.BdasChart;
import cn.com.lxsoft.bdasphone.utils.BottomNavigationViewHelper;
import cn.com.lxsoft.bdasphone.utils.StatusBarUtils;

import cn.com.lxsoft.bdasphone.databinding.ActivityMainBinding;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.ContainerActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

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
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });
        //View持有ViewModel的引用，如果没有特殊业务处理，这个方法可以不重写
        //return ViewModelProviders.of(this).get(LoginViewModel.class);
        //binding.ivSwichPasswrod.setB(viewModel.passwordShowSwitchOnClickCommand);
        return null;
    }

    BdasChart bdasChart;

    @Override
    public void initViewObservable() {

        StatusBarUtils.setBar(this,R.color.colorPrimary,false);

        bdasChart=new BdasChart(viewModel.layoutChartViewModel);
        bdasChart.initPieChart(binding.includeChart.chartMain);



        BottomNavigationViewHelper.disableShiftMode(binding.navigation);


       DisplayMetrics dm = new DisplayMetrics();
       getWindowManager().getDefaultDisplay().getMetrics(dm); // 为获取屏幕宽、高

        ViewGroup.LayoutParams windowLayoutParams =binding.layoutChart.getLayoutParams(); // 获取对话框当前的参数值
       windowLayoutParams.width = (int) (dm.widthPixels -32); // 宽度设置为屏幕的0.95
       windowLayoutParams.height = (int) (dm.widthPixels -32); // 高度设置为屏幕的0.6

        binding.layoutToolbarSearch.setViewModel(viewModel);
        binding.layoutToolbarSearch.bOpenNew=true;

        //binding.tvTongjiInfo.setText(bdasChart.getTongJiInfo(1));

        ZXingLibrary.initDisplayOpinion(this);
        //binding.iconTest.setImageBitmap(bitmap);

        viewModel.layoutChartViewModel.chartData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.tvTongjiInfo.setText(bdasChart.getTongJiInfo("Qlfl_Gydw"));
            }
        });

        binding.layoutToolbarSearch.qRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
                rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.VIBRATE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                                    startActivityForResult(intent, SystemConfig.ActivityResult_QrCode);
                                } else {
                                    ToastUtils.showShort("权限被拒绝");
                                }
                            }
                        });
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == SystemConfig.ActivityResult_QrCode) {
            //处理扫描结果（在界面上显示）
            if (null != intent) {
                Bundle bundle = intent.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    DataSession.setCurrentQiaoLiang(AppApplication.dataBase.getBridge(result));
                    startContainerActivity(ContentFragment.class.getCanonicalName());
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            viewModel.closeAPP();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
