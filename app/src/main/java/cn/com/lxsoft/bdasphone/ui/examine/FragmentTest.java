package cn.com.lxsoft.bdasphone.ui.examine;

import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zhihu.matisse.Matisse;

import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.FragmentTestBinding;
import cn.com.lxsoft.bdasphone.ui.component.ToolBarBdas;
import me.goldze.mvvmhabit.base.BaseFragment;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.TabView;

import static android.app.Activity.RESULT_OK;


public class FragmentTest extends BaseFragment<FragmentTestBinding, FragmentTestViewModel> {
    @Override
    public void initParam() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_test;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {


        viewModel.bInitDataOK.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                //viewModel.addPage();
                binding.viewPager.setAdapter(viewModel.adapter);
                binding.verTablayout.setupWithViewPager(binding.viewPager);
                viewModel.adapter.fragmentTest=FragmentTest.this;
            }
        });

        binding.toolbarTestDisease.setConfirmOKListener(new ToolBarBdas.OnConfirmOK() {
            @Override
            public void onConfirmOK() {
                viewModel.saveData();
            }
        });

        binding.verTablayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            int prevIndex=-1;
            @Override
            public void onTabSelected(TabView tab, int position) {
                if(viewModel.unitPosList.indexOf(position)!=-1)
                    return;
                if(prevIndex>=0 && position!=prevIndex)
                    binding.verTablayout.getTabAt(prevIndex).setBackground(getContext().getResources().getDrawable(R.drawable.shape_rect_botomrightline_lightgray));
                tab.setBackground(getContext().getResources().getDrawable(R.drawable.shape_rect_noborderradius_lightgray));
                prevIndex=position;
            }

            @Override
            public void onTabReselected(TabView tab, int position) {
                if(viewModel.unitPosList.indexOf(position)!=-1)
                    return;
                tab.setBackground(getContext().getResources().getDrawable(R.drawable.shape_rect_botomrightline_lightgray));
            }
        });
    }

    @Override
    public void initViewObservable() {
        List<ObservableInt> obNumList=viewModel.listBadgeNum;
        List<ObservableInt> obColorList=viewModel.listBadgeColor;
        for(int i=0;i<obNumList.size();i++){
            int pos=i;
            obNumList.get(i).addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    if(obNumList.get(pos).get()==0)
                        binding.verTablayout.getTabAt(pos).getBadgeView().hide(true);
                    else
                        binding.verTablayout.getTabAt(pos).getBadgeView().setBadgeNumber(obNumList.get(pos).get());
                    //binding.verTablayout.setBa
                }
            });
            obColorList.get(i).addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    int mcolor=obColorList.get(pos).get();
                    int tpcolor=0xffffffff;
                    if(mcolor==SystemConfig.getPingJiColor(3))
                        tpcolor=0xff000000;
                    binding.verTablayout.getTabAt(pos).getBadgeView().setBadgeTextColor(tpcolor).setBadgeBackgroundColor(mcolor);
                }
            });
        }
        //viewModel.updatePingJiaData();
    }

}
