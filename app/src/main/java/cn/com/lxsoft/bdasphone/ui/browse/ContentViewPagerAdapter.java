package cn.com.lxsoft.bdasphone.ui.browse;

import android.Manifest;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.FragmentContentQrcodeBinding;
import cn.com.lxsoft.bdasphone.databinding.FragmentGisContentBinding;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.ToastUtils;
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
            UiSettings mUiSettings;//定义一个UiSettings对象
            mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
            mUiSettings.setCompassEnabled(true);
            mUiSettings.setZoomGesturesEnabled(true);

            ((FragmentGisContentBinding) _binding).buttonLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AMapLocationClient locationClientSingle = new AMapLocationClient(_binding.getRoot().getContext());

                    AMapLocationClientOption option = new AMapLocationClientOption();
                    option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                    option.setOnceLocation(true);
                    option.setOnceLocationLatest(true);

                    RxPermissions rxPermissions = new RxPermissions(ActivityUtils.getActivityFromView(_binding.getRoot()));
                    rxPermissions.request(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
                            .subscribe(new Consumer<Boolean>() {
                                           @Override
                                           public void accept(Boolean aBoolean) throws Exception {
                                               if (aBoolean) {
                                                   AMapLocationListener locationSingleListener = new AMapLocationListener() {
                                                       @Override
                                                       public void onLocationChanged(AMapLocation location) {
                                                           if (location.getErrorCode() == 0) {
                                                               //可在其中解析amapLocation获取相应内容。
                                                               LatLng lt=new LatLng(location.getLatitude(), location.getLongitude());
                                                               gisViewModel.gisPosLat=lt.latitude;
                                                               gisViewModel.gisPosLng=lt.longitude;
                                                               ContentViewPagerAdapter.this.setGisPos(lt);
                                                           } else {
                                                               //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                                                               //ToastUtils.showShort(location.getErrorInfo());
                                                               locationClientSingle.stopLocation();
                                                           }
                                                       }
                                                   };
                                                   locationClientSingle.setLocationListener(locationSingleListener);
                                                   locationClientSingle.stopLocation();
                                                   locationClientSingle.startLocation();
                                               } else {
                                                   ToastUtils.showShort("权限被拒绝");
                                               }
                                           }
                                       });
                }
            });

            ((FragmentGisContentBinding) _binding).buttonGisNavigate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(new File("/data/data/com.autonavi.minimap").exists()){
                        ActivityUtils.goToNaviActivity(contentFragment.getContext(),"Bdas",null,String.valueOf(itemVM.gisPosLat),String.valueOf(itemVM.gisPosLng),"1","2");
                    }
                }
            });

            if(itemVM.gisPosLng>1){
                setGisPos(new LatLng(itemVM.gisPosLat,itemVM.gisPosLng));
            }
        }
        if(itemVM.contentType==SystemConfig.ContentPager_Type_QRCODE){
            Bitmap mBitmap = CodeUtils.createImage(itemVM.bridge.getDaiMa(), 400, 400, BitmapFactory.decodeResource(_binding.getRoot().getResources(), R.mipmap.ic_launcher));
            ((FragmentContentQrcodeBinding)_binding).imageViewQrCode.setImageBitmap(mBitmap);
        }

        return;
    }

    @Override
    public int getCount() {
        if(contentFragmentViewModel==null)
            return 0;
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
