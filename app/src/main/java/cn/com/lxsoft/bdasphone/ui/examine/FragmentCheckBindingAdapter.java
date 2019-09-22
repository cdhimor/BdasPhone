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
import cn.com.lxsoft.bdasphone.entity.ImageData;
import cn.com.lxsoft.bdasphone.ui.component.BridgeImagePanel;
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
import android.widget.RadioGroup;

import java.util.List;

/**
 * Created by goldze on 2018/6/21.
 */

public class FragmentCheckBindingAdapter extends BindingViewPagerAdapter<FragmentCheckItemViewModel> implements TabAdapter {
    public FragmentCheckViewModel fragmentCheckViewModel;
    public FragmentCheck fragmentCheck;

    @Override
    public void onBindBinding(final ViewDataBinding _binding, int variableId, int layoutRes, final int position, FragmentCheckItemViewModel viewModel) {
        super.onBindBinding(_binding, variableId, layoutRes, position, viewModel);
        if(position==0)
            return;

        LayoutCheckViewpagerItemBinding binding = (LayoutCheckViewpagerItemBinding) _binding;
        //Context context=binding.getRoot().getContext();

        RadioGroup rdGroup=binding.checkSelectRdGroup;

        switch (viewModel.disease.get()) {
            case "1":
                rdGroup.check(R.id.check_select_A);
                break;
            case "2":
                rdGroup.check(R.id.check_select_1);
                break;
            case "3":
                rdGroup.check(R.id.check_select_2);
                break;
        }

        BridgeImagePanel imgPanel=binding.checkImagePanel;
        imgPanel.init(fragmentCheck);

        imgPanel.onLoadImageOK=new BridgeImagePanel.OnLoadImage() {
            @Override
            public boolean onLoad(List<Uri> tplist) {
                viewModel.imageList.addAll(ImageData.createImageList(tplist,true));
                return true;
            }
        };
        imgPanel.showImage(viewModel.imageList);

        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int pos=position;
                String code="1";
                switch (checkedId){
                    case R.id.check_select_1:
                        code="2";
                        break;
                    case R.id.check_select_2:
                        code="3";
                        break;
                }
                viewModel.disease.set(code);
                fragmentCheck.setBadgeColor(pos,fragmentCheckViewModel.getPageTitleBadgeColor(pos));
            }
        });

    }



    @Override
    public TabView.TabBadge getBadge(int position) {
        int color=fragmentCheckViewModel.getPageTitleBadgeColor(position);
        if(color==0)
            return null;
        return new TabView.TabBadge.Builder().setBadgeNumber(-1).setExactMode(true).setBackgroundColor(color).build();
    }

    @Override
    public TabView.TabIcon getIcon(int position) {
        return null;
    }


    @Override
    public TabView.TabTitle getTitle(int position) {

        String title=fragmentCheckViewModel.getPageTitle(position);
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
        return fragmentCheckViewModel.viewModelsItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        //String title=fragmentCheckViewModel.getTitles().get(position);
        return "";
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
