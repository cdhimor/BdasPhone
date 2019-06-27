package cn.com.lxsoft.bdasphone.ui.examine;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.LayoutCheckViewpagerItemBinding;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import cn.com.lxsoft.bdasphone.utils.GlideEnginex;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;

import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.TabView;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by goldze on 2018/6/21.
 */

public class FragmentCheckBindingAdapter extends BindingViewPagerAdapter<FragmentCheckItemViewModel> implements TabAdapter {
    public FragmentCheckViewModel fragmentCheckViewModel;
    public FragmentCheck fragmentCheck;



    @Override
    public void onBindBinding(final ViewDataBinding binding, int variableId, int layoutRes, final int position, FragmentCheckItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);

        LayoutCheckViewpagerItemBinding _binding = (LayoutCheckViewpagerItemBinding) binding;
        Context context=_binding.getRoot().getContext();

        item.oALDiseasePicList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Uri>>() {
            @Override
            public void onChanged(ObservableList<Uri> sender){}


            public void onItemRangeChanged(ObservableList<Uri> sender, int positionStart, int itemCount){

            }

            public void onItemRangeInserted(ObservableList<Uri> sender, int positionStart, int itemCount)
            {
                if(sender.size()==0)
                    return;
                ImageView imageView = new ImageView(fragmentCheck.getContext());
                LinearLayout.LayoutParams para=new LinearLayout.LayoutParams(300,300);
                //ViewGroup.LayoutParams para = imageView.getLayoutParams();
                para.height = 300;
                para.width = 300;
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(para);
                _binding.layoutDiseasePicList.addView(imageView);
                for(int i=0;i<sender.size();i++) {
                    Glide.with(fragmentCheck)
                            .asBitmap() // some .jpeg files are actually gif
                            .load(sender.get(i))
                            .centerCrop()
                            .into(imageView);
                }
            }

            public void onItemRangeMoved(ObservableList<Uri> sender, int fromPosition, int toPosition,int itemCount){}

            public void onItemRangeRemoved(ObservableList<Uri> sender, int positionStart, int itemCount){}

            });

        item.clickEvent.observe((LifecycleOwner) context, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                /*
                if(ContextCompat.checkSelfPermission(_binding.getRoot().getContext(),Manifest.permission.READ_EXTERNAL_STORAGE )!= PackageManager.PERMISSION_GRANTED
                   || ContextCompat.checkSelfPermission(_binding.getRoot().getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(ActivityUtils.getActivityFromView(_binding.getRoot()), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    return;
                }
                */

                RxPermissions rxPermissions = new RxPermissions(fragmentCheck.getActivity());
                rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE/*,Manifest.permission.CAMERA*/)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    Matisse.from(fragmentCheck)
                                            .choose(MimeType.ofAll(), false) // 选择 mime 的类型
                                            .countable(true)
                                            //.capture(true)//选择照片时，是否显示拍照
                                            //.captureStrategy(new CaptureStrategy(true, "cn.com.lxsoft.bdasphone"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
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
        });

        return;
    }



    @Override
    public TabView.TabBadge getBadge(int position) {
        //if (position == 2) return new TabView.TabBadge.Builder().setBadgeNumber(666)
          //      .setExactMode(true).build();
        return null;
    }

    @Override
    public TabView.TabIcon getIcon(int position) {
        return null;
    }


    @Override
    public TabView.TabTitle getTitle(int position) {

        String title=fragmentCheckViewModel.getTitles().get(position);
        return new TabView.TabTitle.Builder()
                .setContent(title)
                .setTextColor(0xFF009900, Color.BLACK)
                .build();
    }

    @Override
    public int getBackground(int position) {
        return R.drawable.shape_rect_botomrightline_lightgray;
    }

    @Override
    public int getCount() {
        return fragmentCheckViewModel.getTitles().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title=fragmentCheckViewModel.getTitles().get(position);
        return title;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
