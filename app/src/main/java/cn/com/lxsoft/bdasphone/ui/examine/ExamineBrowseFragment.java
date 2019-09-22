package cn.com.lxsoft.bdasphone.ui.examine;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.FragmentBrowseBinding;
import cn.com.lxsoft.bdasphone.databinding.FragmentExamineBrowseBinding;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragmentViewModel;
import cn.com.lxsoft.bdasphone.ui.component.ToolBarBdas;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import cn.com.lxsoft.bdasphone.utils.BottomNavigationViewHelper;
import cn.com.lxsoft.bdasphone.utils.RadioGroupUtils;
import cn.com.lxsoft.bdasphone.utils.StatusBarUtils;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class ExamineBrowseFragment extends BaseFragment<FragmentExamineBrowseBinding, ExamineBrowseFragmentViewModel> {
    @Override
    public void initParam() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_examine_browse;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public ExamineBrowseFragmentViewModel initViewModel() {
        //View持有ViewModel的引用，如果没有特殊业务处理，这个方法可以不重写
        ExamineBrowseFragmentViewModel vm=ViewModelProviders.of(this).get(ExamineBrowseFragmentViewModel.class);
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            vm.nBrowseType = mBundle.getShort("type");
        }
        return vm;
        //binding.ivSwichPasswrod.setB(viewModel.passwordShowSwitchOnClickCommand);

    }

    @Override
    public void initData() {
        //获取列表传入的实体

        StatusBarUtils.setBar(this.getActivity(),R.color.colorPrimary,false);
        BottomNavigationViewHelper.disableShiftMode((BottomNavigationView) binding.navigation);

        //ActivityUtils.initRecyclerView(binding.layoutSlideList.idDrawerList,this.getContext());

        RecyclerView recyclerView = binding.idRecyclerview;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dv=new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL);
        //dv.setDrawable(ContextCompat.getDrawable(this,R.drawable.shape_line_qllist));
        recyclerView.addItemDecoration(dv);

        /*
        binding.qlListToolbarLuXian.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                viewModel.slideBrowseViewModel.initData(SystemConfig.SlideList_Type_Path);
                ActivityUtils.showDrawerPanel(binding.drawerLayout,ExamineBrowseFragment.this.getActivity(),true);
            }
        });
        binding.qlListToobarDanWei.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                viewModel.slideBrowseViewModel.initData(SystemConfig.SlideList_Type_Dept);
                ActivityUtils.showDrawerPanel(binding.drawerLayout,ExamineBrowseFragment.this.getActivity(),true);
            }
        });
        */
    }

    @Override
    public void initViewObservable() {
        ToolBarBdas barBdas= binding.layoutToolbarTitle;
        switch(viewModel.nBrowseType){
            case SystemConfig.ExamineStyle_Patrol:
                barBdas.setName("日常巡查");
                barBdas.setContent();
                break;
            case SystemConfig.ExamineStyle_Check:
                barBdas.setName("经常检查");
                barBdas.setContent();
                break;
            case SystemConfig.ExamineStyle_Test:
                barBdas.setName("定期检查");
                barBdas.setContent();
                break;
        }


        viewModel.pageIndexMain.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //结束刷新
                int pageIndex=viewModel.pageIndexMain.get();
                if(pageIndex==-1)
                    return;
                if(pageIndex==0)
                    ToastUtils.showLong("共"+viewModel.dataNum+"条数据，"+viewModel.pageCount+"页");
                else
                    ToastUtils.showLong("第"+(viewModel.pageIndexMain.get()+1)+"页");
                binding.twinklingRefreshLayout.finishLoadmore();
            }
        });


        /*
        viewModel.slideBrowseViewModel.bSlideOpen.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if(!viewModel.slideBrowseViewModel.bSlideOpen.get())
                    ActivityUtils.showDrawerPanel(binding.drawerLayout,ExamineBrowseFragment.this.getActivity(),false);
            }
        });
        */
    }

}
