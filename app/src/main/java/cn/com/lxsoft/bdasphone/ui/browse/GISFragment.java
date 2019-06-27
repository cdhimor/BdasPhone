package cn.com.lxsoft.bdasphone.ui.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.databinding.FragmentContentBinding;
import cn.com.lxsoft.bdasphone.databinding.FragmentGisBinding;
import cn.com.lxsoft.bdasphone.utils.StatusBarUtils;
import me.goldze.mvvmhabit.base.BaseFragment;


public class GISFragment extends BaseFragment<FragmentGisBinding,GISFragmentViewModel> implements AMap.OnMapClickListener {
    Bundle savedInstanceState;
    MapView mapView;

    @Override
    public void initParam() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle sState) {
        savedInstanceState=sState;
        return R.layout.fragment_gis;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {

        //StatusBarUtils.setLightStatusBar(this.getActivity(),false);
        StatusBarUtils.setBar(this.getActivity(),R.color.colorPrimary,false);

        mapView = binding.gisMap;
        mapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
//初始化地图控制器对象

        AMap aMap= mapView.getMap();
        aMap.setOnMapClickListener(this);


/*

        */

    }

    @Override
    public void initViewObservable() {

    }



    @Override
    public void onMapClick(LatLng latLng) {

    }

}
