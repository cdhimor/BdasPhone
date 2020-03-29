package cn.com.lxsoft.bdasphone.ui.examine;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;


import java.text.DecimalFormat;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.FragmentCheckBinding;
import cn.com.lxsoft.bdasphone.ui.component.ToolBarBdas;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;
import q.rorbin.verticaltablayout.*;
import q.rorbin.verticaltablayout.widget.TabView;

import static android.app.Activity.RESULT_OK;


public class FragmentCheck extends BaseFragment<FragmentCheckBinding, FragmentCheckViewModel> {
    MaterialDialog hisDialog;

    @Override
    public void initParam() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_check;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        //MyPagerAdapter mAdapter = new MyPagerAdapter(getFragmentManager());
        binding.viewPager.setAdapter(viewModel.adapter);
        binding.verTablayout.setupWithViewPager(binding.viewPager);
        viewModel.adapter.fragmentCheck=this;

        binding.toolbarCheck.setConfirmOKListener(new ToolBarBdas.OnConfirmOK() {
            @Override
            public void onConfirmOK() {
                viewModel.saveData();
                //checkPositionData();
            }
        });

        binding.verTablayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            int prevIndex=-1;
            @Override
            public void onTabSelected(TabView tab, int position) {
                if(prevIndex>=0 && position!=prevIndex)
                    binding.verTablayout.getTabAt(prevIndex).setBackground(getContext().getResources().getDrawable(R.drawable.shape_rect_botomrightline_lightgray));
                tab.setBackground(getContext().getResources().getDrawable(R.drawable.shape_rect_noborderradius_lightgray));
                prevIndex=position;
            }

            @Override
            public void onTabReselected(TabView tab, int position) {
                tab.setBackground(getContext().getResources().getDrawable(R.drawable.shape_rect_botomrightline_lightgray));
            }
        });

        if(viewModel.obNewData.get() || viewModel.obHistory.get()){
            binding.pannelCreateNew.setVisibility(View.GONE);
        }

        binding.patrolTvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hisDialog==null) {
                    hisDialog = new MaterialDialog.Builder(FragmentCheck.this.getContext())
                            .title("历史记录")
                            .items()
                            .alwaysCallSingleChoiceCallback()
                            .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View itemView, int which,
                                                           CharSequence text) {
                                    viewModel.openHistoryActivity(viewModel.hisKeySet.get(which).toString());
                                    return true; // allow selection
                                }
                            })
                            .negativeText("取消").show();
                    viewModel.getHistoryData();
                }
                hisDialog.show();
                hisDialog.setSelectedIndex(-1);
            }
        });

        viewModel.openHisDialogEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                String[] array=(String[])viewModel.hisValueSet.toArray(new String[viewModel.hisValueSet.size()]);
                hisDialog.setItems(array);
            }
        });


        viewModel.initCheckData();

    }

    public void setBadgeColor(int pos,int color){
        binding.verTablayout.getTabAt(pos).getBadgeView().setBadgeNumber(-1).setBadgeBackgroundColor(color);
    }

    @Override
    public void initViewObservable() {

    }
}
