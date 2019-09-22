package cn.com.lxsoft.bdasphone.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.text.DecimalFormat;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.ui.examine.FragmentPatrol;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.ContainerActivity;
import me.goldze.mvvmhabit.utils.ConvertUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ActivityUtils {

    public static Activity getActivityFromView(View view) {
        if (null != view) {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity) context;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        return null;
    }

    public static Object getActivityFromViewX(View view) {
        if (null != view) {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity) context;
                }
                else if(context instanceof ContainerActivity) {
                    return (ContainerActivity) context;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        return null;
    }


    public static void initRecyclerView(RecyclerView recyclerView,Context context)
    {
        //recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dv=new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        //dv.setDrawable(ContextCompat.getDrawable(this,R.drawable.shape_line_qllist));
        recyclerView.addItemDecoration(dv);

    }

    public static void showDrawerPanel(DrawerLayout mDrawerLayout,Activity activity,boolean bShow){
        if(bShow  && !mDrawerLayout.isDrawerOpen(Gravity.END))
            mDrawerLayout.openDrawer(Gravity.END);
        else if(!bShow && mDrawerLayout.isDrawerOpen(Gravity.END))
            mDrawerLayout.closeDrawer(Gravity.END);
        //((InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showDrawerPanelStart(DrawerLayout mDrawerLayout,Activity activity,boolean bShow){
        if(bShow  && !mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.openDrawer(Gravity.START);
        else if(!bShow && mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START);
        //((InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static Bitmap getBridgeIconBitmap(QiaoLiang ql, Resources res)
    {
        GradientDrawable grad = (GradientDrawable)ql.getBridgeDrawable();
        grad.setCornerRadius(24);
        grad.setSize(128,128);

        int w = grad.getIntrinsicWidth();
        int h = grad.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        grad.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        grad.draw(canvas);

        Bitmap iconBitmap=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.mipmap.ic_qiaoxing_gong),128,128,true);
        canvas.drawBitmap(iconBitmap,0,0,new Paint());

        return bitmap;

    }

    public static void openImageLoadDialog(View view,Fragment fragment){
        RxPermissions rxPermissions = new RxPermissions(getActivityFromView(view));
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from(fragment)
                                    .choose(MimeType.ofAll(), false) // 选择 mime 的类型
                                    .countable(true)
                                    .capture(true)//选择照片时，是否显示拍照
                                    .captureStrategy(new CaptureStrategy(true, "cn.com.lxsoft.bdasphone"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                                    .maxSelectable(9) // 图片选择的最多数量
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f) // 缩略图的比例
                                    .imageEngine(new GlideEnginex()) // 使用的图片加载引擎
                                    .forResult(SystemConfig.MATISSE_REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
                        } else {
                            ToastUtils.showShort("权限被拒绝");
                        }
                    }
                });
    }

    /**
     * 启动高德App进行导航
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:58
     * <h3>UpdateTime</h3> 2016/6/27,13:58
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param poiname 非必填 POI 名称
     * @param lat 必填 纬度
     * @param lon 必填 经度
     * @param dev 必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style 必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static  void goToNaviActivity(Context context,String sourceApplication , String poiname , String lat , String lon , String dev , String style){
        StringBuffer stringBuffer  = new StringBuffer("androidamap://navi?sourceApplication=")
                .append(sourceApplication);
        if (!poiname.isEmpty()){
            stringBuffer.append("&poiname=").append(poiname);
        }
        stringBuffer.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                .append("&style=").append(style);

        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    //0:初始化，1：位置校验成功，2：图片上传成功，200：错误。
    public static void checkPositionData(Activity activity,double lat,double lng,float blong,ObservableInt obState){
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            AMapLocationClient locationClientSingle = new AMapLocationClient(activity);
                            AMapLocationClientOption option = new AMapLocationClientOption();
                            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                            option.setOnceLocation(true);
                            option.setOnceLocationLatest(true);
                            locationClientSingle.setLocationOption(option);

                            AMapLocationListener locationSingleListener = new AMapLocationListener() {
                                @Override
                                public void onLocationChanged(AMapLocation location) {
                                    if (location.getErrorCode() == 0) {
                                        DPoint lta=new DPoint(location.getLatitude(), location.getLongitude());
                                        DPoint ltb=new DPoint(lat,lng);
                                        float distance=CoordinateConverter.calculateLineDistance(lta,ltb);
                                        float fw=(blong>10)?blong+30:10;
                                        if(distance<fw)
                                            obState.set(1);
                                        else {
                                            DecimalFormat df = new DecimalFormat(".0");
                                            ToastUtils.showShort("距离标注点" + df.format(distance) + "米,不在该桥梁范围内");
                                            obState.set(100);
                                        }
                                    } else {
                                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                                        ToastUtils.showShort(location.getErrorInfo());
                                        locationClientSingle.stopLocation();
                                        obState.set(100);
                                    }
                                }
                            };
                            locationClientSingle.setLocationListener(locationSingleListener);
                            locationClientSingle.stopLocation();
                            locationClientSingle.startLocation();
                        } else {
                            ToastUtils.showShort("GPS权限被拒绝");
                            obState.set(100);
                        }
                    }
                });
    }


    public static void refreshViewHeight(ViewGroup viewGroup){
        int height=0;
        for(int i=0;i<viewGroup.getChildCount();i++)
        {
            View view =viewGroup.getChildAt(i);
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            height += view.getMeasuredHeight();
            //int width = view.getMeasuredWidth();
        }

        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,height);
        viewGroup.setLayoutParams(layoutParams);

    }

    public static float packageCode() {
        Context context=AppApplication.getInstance().getBaseContext();
        PackageManager manager = context.getPackageManager();
        String name = "";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Float.parseFloat(name);
    }

}
