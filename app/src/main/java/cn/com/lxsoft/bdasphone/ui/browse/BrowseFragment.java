package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
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
import android.widget.Toast;

import cn.com.lxsoft.bdasphone.utils.BottomNavigationViewHelper;
import cn.com.lxsoft.bdasphone.utils.RadioGroupUtils;
import cn.com.lxsoft.bdasphone.utils.StatusBarUtils;
import me.goldze.mvvmhabit.base.BaseFragment;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.databinding.FragmentBrowseBinding;
import me.goldze.mvvmhabit.utils.ToastUtils;

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
        /*
        ToolBarPanelChooseClickListener toolBarPanelChooseClickListener=new ToolBarPanelChooseClickListener();
        binding.qlListToobarPaiXu.setOnClickListener(toolBarPanelChooseClickListener);
        binding.qlListToobarLeiXing.setOnClickListener(toolBarPanelChooseClickListener);
        binding.qlListToobarDanWei.setOnClickListener(toolBarPanelChooseClickListener);
        binding.qlListToolbarLuXian.setOnClickListener(toolBarPanelChooseClickListener);
        binding.qlListToobarZhuangKuang.setOnClickListener(toolBarPanelChooseClickListener);
        */
        StatusBarUtils.setBar(this.getActivity(),R.color.colorPrimary,false);

        RecyclerView recyclerView = binding.idRecyclerview;
        //recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dv=new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL);
        //dv.setDrawable(ContextCompat.getDrawable(this,R.drawable.shape_line_qllist));
        recyclerView.addItemDecoration(dv);

        RadioGroupUtils rGU=new RadioGroupUtils(binding.rdoGpQuickChoose);
        rGU.supportNest();

        BottomNavigationViewHelper.disableShiftMode((BottomNavigationView) binding.navigation);
    }

    @Override
    public void initViewObservable() {
        viewModel.oIntToolbarSelectedPanel.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                LinearLayout rgLeXing = binding.layoutPanelLeiXing;
                LinearLayout rgZK = binding.layoutPanelPingJia;
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
                        break;
                    case 3://zhuangkuang
                        rgLeXing.setVisibility(View.GONE);
                        rgPaiXu.setVisibility(View.GONE);
                        rgZK.setVisibility(View.VISIBLE);
                        break;
                    case 4://danwei:
                        binding.idLayoutDWChoose.setVisibility(View.VISIBLE);
                        binding.idLayoutLXChoose.setVisibility(View.GONE);
                        //ShowDrawerPanel(true);
                        break;
                    case 5://luxian
                        binding.idLayoutDWChoose.setVisibility(View.GONE);
                        binding.idLayoutLXChoose.setVisibility(View.VISIBLE);
                        ShowDrawerPanel(true);
                        break;
                }
            }
        });

        viewModel.pageIndexQiaoLiang.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //结束刷新
                ToastUtils.showLong("第"+(viewModel.pageIndexQiaoLiang.get()+1)+"页");
                binding.twinklingRefreshLayout.finishLoadmore();
            }
        });


        viewModel.oBoolQiaoLiangOrderUp.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                RadioButton btn=getActivity().findViewById(binding.idRadioGpPaiXu.getCheckedRadioButtonId());
                if(btn==null)
                    return;
                String text=btn.getText().toString();
                if(viewModel.oBoolQiaoLiangOrderUp.get()) {
                    btn.setText(text.substring(0, text.length() - 1).concat("▲"));
                }
                else {
                    btn.setText(text.substring(0, text.length() - 1).concat("▼"));
                }
            }
        });
    }


    boolean bLuXianListInit=false;
    protected void ShowDrawerPanel(boolean bShow){
        if(!bLuXianListInit){
            RecyclerView recyclerView = binding.idDrawerListLuXian;
            //recyclerView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration dv=new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL);
            //dv.setDrawable(ContextCompat.getDrawable(this,R.drawable.shape_line_qllist));
            recyclerView.addItemDecoration(dv);
            bLuXianListInit=true;
        }
        DrawerLayout mDrawerLayout=binding.drawerLayout;
        if(bShow  && !mDrawerLayout.isDrawerOpen(Gravity.END))
            mDrawerLayout.openDrawer(Gravity.END);
        else if(!bShow && mDrawerLayout.isDrawerOpen(Gravity.END))
            mDrawerLayout.closeDrawer(Gravity.END);
        ((InputMethodManager)this.getActivity().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
