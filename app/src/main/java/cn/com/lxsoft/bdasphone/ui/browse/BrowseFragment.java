package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.ui.component.ToolBarBdas;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import cn.com.lxsoft.bdasphone.utils.BottomNavigationViewHelper;
import cn.com.lxsoft.bdasphone.utils.RadioGroupUtils;
import cn.com.lxsoft.bdasphone.utils.StatusBarUtils;
import me.goldze.mvvmhabit.base.BaseFragment;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.databinding.FragmentBrowseBinding;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;


public class BrowseFragment extends BaseFragment<FragmentBrowseBinding, BrowseFragmentViewModel> {
    @Override
    public void initParam() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_browse;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        //获取列表传入的实体
        /*
        ToolBarPanelChooseClickListener toolBarPanelChooseClickListener=new ToolBarPanelChooseClickListener();
        binding.qlListToobarPaiXu.setOnClickListener(toolBarPanelChooseClickListener);
        binding.qlListToobarLeiXing.setOnClickListener(toolBarPanelChooseClickListener);
        binding.qlListToobarDanWei.setOnClickListener(toolBarPanelChooseClickListener);
        binding.qlListToolbarLuXian.setOnClickListener(toolBarPanelChooseClickListener);
        binding.qlListToobarZhuangKuang.setOnClickListener(toolBarPanelChooseClickListener);
        */
        StatusBarUtils.setBar(this.getActivity(),R.color.colorPrimary,false);

        ActivityUtils.initRecyclerView(binding.idRecyclerview,this.getContext());

        ActivityUtils.initRecyclerView(binding.layoutSlideList.idDrawerList,this.getContext());

        //RadioGroupUtils rGU=new RadioGroupUtils(binding.rdoGpQuickChoose);
        //rGU.supportNest();

        BottomNavigationViewHelper.disableShiftMode((BottomNavigationView) binding.navigation);

        BrowseFragment bf=this;
        binding.qlListToolbarLuXian.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                viewModel.slideBrowseViewModel.initData(SystemConfig.SlideList_Type_Path);
                ActivityUtils.showDrawerPanel(binding.drawerLayout,bf.getActivity(),true);
            }
        });
        binding.qlListToobarDanWei.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                viewModel.slideBrowseViewModel.initData(SystemConfig.SlideList_Type_Dept);
                ActivityUtils.showDrawerPanel(binding.drawerLayout,bf.getActivity(),true);
            }
        });

        binding.layoutToolbarTitle.setSearchListener(new ToolBarBdas.OnSetSearchOK() {
            @Override
            public void onSearch(String name,String sSearch) {
                viewModel.setSearchData(sSearch);
                viewModel.setSearchHistoryData(name,sSearch);
                viewModel.dealMainDataBinding(0);
            }
        });


        //桥梁类型 是 小桥╳//m1.1┬1┼m1.2┬01
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            String[] res=SystemConfig.parseBundleSearchData(mBundle);
            binding.layoutToolbarTitle.addSearchInfo(res[0],res[1]);
        }
        else {
            viewModel.setOrderData("m0.1",true);
            viewModel.dealMainDataBinding(0);
        }

        binding.layoutToolbarTitle.setViewModel(viewModel);
    }

    @Override
    public void initViewObservable() {

        BrowseFragment bf=this;
        viewModel.oIntToolbarSelectedPanel.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                RadioGroup rgLeXing = binding.rdoGpQuickChooseLX;
                RadioGroup rgZK = binding.rdoGpQuickChooseDJ;
                RadioGroup rgPaiXu = binding.idRadioGpPaiXu;
                switch(viewModel.oIntToolbarSelectedPanel.get()) {
                    case 1://paixu
                        rgLeXing.setVisibility(View.GONE);
                        rgZK.setVisibility(View.GONE);
                        rgPaiXu.setVisibility(View.VISIBLE);
                        break;
                    case 2://leixing
                        rgZK.setVisibility(View.GONE);
                        rgPaiXu.setVisibility(View.GONE);
                        rgLeXing.setVisibility(View.VISIBLE);
                        //rgZK.clearCheck();
                        break;
                    case 3://zhuangkuang
                        rgLeXing.setVisibility(View.GONE);
                        rgPaiXu.setVisibility(View.GONE);
                        rgZK.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        viewModel.oIntClearCheck.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(viewModel.oIntClearCheck.get()==1)
                    binding.rdoGpQuickChooseDJ.clearCheck();
                else
                    binding.rdoGpQuickChooseLX.clearCheck();
            }
        });

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


        for(int i=0;i<viewModel.oOrderList.size();i++) {
            int pos=i;
            viewModel.oOrderList.get(i).addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable observable, int i) {
                    RadioButton btn = getActivity().findViewById(binding.idRadioGpPaiXu.getCheckedRadioButtonId());
                    if (btn == null)
                        return;
                    String text = btn.getText().toString();
                    if (viewModel.oOrderList.get(pos).get()) {
                        btn.setText(text.substring(0, text.length() - 1).concat("▲"));
                    } else {
                        btn.setText(text.substring(0, text.length() - 1).concat("▼"));
                    }
                }
            });
        }

        viewModel.slideBrowseViewModel.bSlideOpen.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if(!viewModel.slideBrowseViewModel.bSlideOpen.get())
                    ActivityUtils.showDrawerPanel(binding.drawerLayout,bf.getActivity(),false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    String[] res=returnedData.split("#");
                    binding.layoutToolbarTitle.addSearchInfo(res[0],res[1]);
                }
                    break;
            default:
        }
    }
}
