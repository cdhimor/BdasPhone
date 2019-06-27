package cn.com.lxsoft.bdasphone.ui.examine;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.FragmentBrowseBinding;
import cn.com.lxsoft.bdasphone.databinding.FragmentExamineBrowseBinding;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragmentViewModel;
import cn.com.lxsoft.bdasphone.ui.component.ToolBarBdas;
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

        RecyclerView recyclerView = binding.idRecyclerview;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dv=new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL);
        //dv.setDrawable(ContextCompat.getDrawable(this,R.drawable.shape_line_qllist));
        recyclerView.addItemDecoration(dv);


    }

    @Override
    public void initViewObservable() {
        ToolBarBdas barBdas= binding.layoutToolbarTitle;
        switch(viewModel.nBrowseType){
            case SystemConfig.ExamineStyle_Patrol:
                barBdas.setType(1);
                barBdas.setName("日常巡查");
                barBdas.setContent();
                break;
            case SystemConfig.ExamineStyle_Check:
                barBdas.setType(1);
                barBdas.setName("经常检查");
                barBdas.setContent();
                break;
        }


        viewModel.pageIndexMain.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //结束刷新
                ToastUtils.showLong("第"+(viewModel.pageIndexMain.get()+1)+"页");
                binding.twinklingRefreshLayout.finishLoadmore();
            }
        });
    }

}
