package cn.com.lxsoft.bdasphone.ui.browse;

import android.databinding.Observable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.Map;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.FragmentContentBinding;
import cn.com.lxsoft.bdasphone.utils.ConvertUtils;
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

        viewModel.getContentData();
    }

    @Override
    public void initViewObservable() {

        viewModel.bDataInitOK.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                viewModel.addPage();
                binding.viewPager.setAdapter(viewModel.adapter);
                binding.tabLayout.setViewPager(binding.viewPager);
                viewModel.adapter.contentFragment=ContentFragment.this;
                if(viewModel.content.dazl==null)
                    return;
                String sZP=viewModel.content.dazl.getZongTiZhaoPian();
                if(ConvertUtils.isSpace(sZP))
                    return;
                Map map=SystemConfig.parseNetPicData(viewModel.content.dazl.getZongTiZhaoPian());
                if(map!=null)
                    Glide.with(getContext()).load(SystemConfig.BASE_URL+"Content/pic/"+viewModel.bridge.getDaiMa()+"/"+map.keySet().toArray()[0]).into(binding.mvBridgeContent);
            }
        });

    }

}
