package cn.com.lxsoft.bdasphone.ui.engineer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;

import cn.com.lxsoft.bdasphone.databinding.FragmentEngineerBinding;
import cn.com.lxsoft.bdasphone.utils.StatusBarUtils;
import me.goldze.mvvmhabit.base.BaseFragment;

public class FragmentEngineer extends BaseFragment<FragmentEngineerBinding, FragmentEngineerViewModel> {

    @Override
    public void initParam() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_engineer;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }



    @Override
    public void initData() {
        StatusBarUtils.setBar(this.getActivity(),R.color.colorPrimary,false);

        FragmentEngineer bf=this;

    }

    @Override
    public void initViewObservable() {
        FragmentEngineer bf = this;
    }
}
