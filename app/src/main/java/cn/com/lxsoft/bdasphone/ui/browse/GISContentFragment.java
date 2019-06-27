package cn.com.lxsoft.bdasphone.ui.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.databinding.FragmentGisBinding;
import me.goldze.mvvmhabit.base.BaseFragment;


public class GISContentFragment extends BaseFragment<FragmentGisBinding,GISFragmentViewModel> implements AMap.OnMapClickListener {
    Bundle savedInstanceState;
    MapView mapView;


    @Override
    public void initParam() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle sState) {
        savedInstanceState=sState;
        return R.layout.fragment_gis_content;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        //StatusBarUtils.setLightStatusBar(this.getActivity(),false);
        /*
        mapView = binding.gisMap;
        mapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        AMap aMap= mapView.getMap();

        MarkerOptions markerOption = new MarkerOptions();
        LatLng
        markerOption.position(Constants.XIAN);
        markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");

        markerOption.draggable(true);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),R.drawable.location_marker)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        */

    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        /*
        AMap aMap= mapView.getMap();
        aMap.clear();
        latitude=latLng.latitude;
        longtitude=latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder));
        markerOptions.position(latLng);
        aMap.addMarker(markerOptions);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        */
    }

}
