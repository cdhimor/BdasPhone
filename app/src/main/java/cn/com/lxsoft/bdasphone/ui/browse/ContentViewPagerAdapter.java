package cn.com.lxsoft.bdasphone.ui.browse;

import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.FragmentGisContentBinding;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;

/**
 * Created by goldze on 2018/6/21.
 */

public class ContentViewPagerAdapter extends BindingViewPagerAdapter<ContentViewPagerViewModel> implements AMap.OnMapClickListener {

    public ContentFragmentViewModel contentFragmentViewModel;
    FragmentGisContentBinding gisBinding;
    ContentViewPagerViewModel gisViewModel;
    public ContentFragment contentFragment;



    @Override
    public void onBindBinding(final ViewDataBinding _binding, int variableId, int layoutRes, final int position, ContentViewPagerViewModel itemVM) {
        super.onBindBinding(_binding, variableId, layoutRes, position, itemVM);

        if(itemVM.contentType==SystemConfig.ContentPager_Type_DLXX)
        {
            gisViewModel=itemVM;
            gisBinding = (FragmentGisContentBinding) _binding;
            gisBinding.gisMap.onCreate(contentFragment.savedInstanceState);
            AMap aMap= gisBinding.gisMap.getMap();
            aMap.setMyLocationEnabled(true);
            aMap.setOnMapClickListener(this);

            if(itemVM.gisPosLng>1){
                setGisPos(new LatLng(itemVM.gisPosLat,itemVM.gisPosLng));
            }
        }

        return;
    }


    @Override
    public int getCount() {
        return contentFragmentViewModel.titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return contentFragmentViewModel.titles.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        setGisPos(latLng);
        double latitude=latLng.latitude;
        double longtitude=latLng.longitude;
        gisViewModel.gisPosLat=latitude;
        gisViewModel.gisPosLng=longtitude;
    }

    public void setGisPos(LatLng latLng){
        MapView mapView = gisBinding.gisMap;
        AMap aMap= mapView.getMap();
        aMap.clear();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(ActivityUtils.getBridgeIconBitmap(contentFragmentViewModel.bridge,contentFragment.getResources())));

        markerOptions.position(latLng);
        aMap.addMarker(markerOptions);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(aMap.getMaxZoomLevel())); // 设置地图可视缩放大小
    }
}
