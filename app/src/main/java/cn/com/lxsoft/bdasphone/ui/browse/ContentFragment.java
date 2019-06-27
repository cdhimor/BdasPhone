package cn.com.lxsoft.bdasphone.ui.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.databinding.FragmentContentBinding;
import cn.com.lxsoft.bdasphone.utils.StatusBarUtils;
import me.goldze.mvvmhabit.base.BaseFragment;



public class ContentFragment extends BaseFragment<FragmentContentBinding, ContentFragmentViewModel> {
    public Bundle savedInstanceState;
    @Override
    public void initParam() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        savedInstanceState=state;
        return R.layout.fragment_content;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        //StatusBarUtils.setLightStatusBar(this.getActivity(),false);
        StatusBarUtils.setBar(this.getActivity(),R.color.colorPrimary,false);

        viewModel.addPage();
        binding.viewPager.setAdapter(viewModel.adapter);
        binding.tabLayout.setViewPager(binding.viewPager);
        viewModel.adapter.contentFragment=this;

        Glide.with(getContext()).load(R.mipmap.testql).into(binding.mvBridgeContent);
    }

    @Override
    public void initViewObservable() {

    }

}
