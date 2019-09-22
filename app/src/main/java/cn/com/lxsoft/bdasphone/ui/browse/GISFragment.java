package cn.com.lxsoft.bdasphone.ui.browse;

import android.databinding.Observable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.databinding.FragmentContentBinding;
import cn.com.lxsoft.bdasphone.databinding.FragmentGisBinding;
import cn.com.lxsoft.bdasphone.entity.BridgeGisData;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import cn.com.lxsoft.bdasphone.utils.StatusBarUtils;
import me.goldze.mvvmhabit.base.BaseFragment;


public class GISFragment extends BaseFragment<FragmentGisBinding,GISFragmentViewModel>{
    Bundle savedInstanceState;
    MapView mapView;
    AMap aMap;

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

        aMap= mapView.getMap();
        //aMap.setOnMapClickListener(this);

        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                DataSession.setCurrentQiaoLiang(viewModel.getBridge(marker.getSnippet()));
                startContainerActivity(ContentFragment.class.getCanonicalName());
            }
        });



        binding.buttonGisSelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealBridgeData(viewModel.getGISData());
            }
        });

        binding.buttonGisSelDept.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                viewModel.slideBrowseViewModel.initData(SystemConfig.SlideList_Type_Dept);
                ActivityUtils.showDrawerPanel(binding.drawerLayout,GISFragment.this.getActivity(),true);
            }
        });

        binding.buttonGisSelPath.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                viewModel.slideBrowseViewModel.initData(SystemConfig.SlideList_Type_Path);
                ActivityUtils.showDrawerPanel(binding.drawerLayout,GISFragment.this.getActivity(),true);
            }
        });
        ActivityUtils.initRecyclerView(binding.layoutSlideList.idDrawerList,this.getContext());
    }

    protected void dealBridgeData(List<QiaoLiang> qlList){
        aMap.clear();
        if(qlList==null || qlList.size()==0)
            return;
        List<LatLng> ltList=getLatLngList(qlList);
        if(qlList.size()<50){
            for(int i=0;i<qlList.size();i++){
                addGisMarker(qlList.get(i),ltList.get(i));
            }
            LatLngBounds bounds = getLatLngBounds(ltList);
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
            qlList.clear();
        }
    }

    List<LatLng> getLatLngList(List<QiaoLiang> qlList)
    {
        ArrayList<LatLng> ltList=new ArrayList();
        for(int i=0;i<qlList.size();i++){
            QiaoLiang ql=qlList.get(i);
            ltList.add(new LatLng(ql.getLat(),ql.getLng()));
        }
        return ltList;
    }

    private LatLngBounds getLatLngBounds(List<LatLng> pointList) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < pointList.size(); i++) {
            LatLng p = pointList.get(i);
            b.include(p);
        }
        return b.build();
    }


    @Override
    public void initViewObservable() {
        viewModel.slideBrowseViewModel.bSlideOpen.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if(!viewModel.slideBrowseViewModel.bSlideOpen.get())
                    ActivityUtils.showDrawerPanel(binding.drawerLayout,GISFragment.this.getActivity(),false);
            }
        });



    }


    public void addGisMarker(QiaoLiang ql,LatLng latLng){
        MarkerOptions marker = new MarkerOptions();
        marker.icon(BitmapDescriptorFactory.fromBitmap(ActivityUtils.getBridgeIconBitmap(ql,GISFragment.this.getResources())));
        marker.position(latLng);
        marker.title(ql.getMingCheng());
        marker.snippet(ql.getDaiMa());
        aMap.addMarker(marker);
    }
}
