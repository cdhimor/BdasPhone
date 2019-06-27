package cn.com.lxsoft.bdasphone.ui.examine;

import android.content.Intent;
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
        viewModel.addPage();
        binding.viewPager.setAdapter(viewModel.adapter);
        binding.verTablayout.setupWithViewPager(binding.viewPager);
        //viewModel.adapter.fragmentCheck=this;

        binding.toolbarTestDisease.setConfirmOKListener(new ToolBarBdas.OnConfirmOK() {
            @Override
            public void onConfirmOK() {

                viewModel.saveData();
            }
        });


    }

    @Override
    public void initViewObservable() {

    }
}
