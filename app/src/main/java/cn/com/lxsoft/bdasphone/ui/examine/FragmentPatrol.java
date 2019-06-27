package cn.com.lxsoft.bdasphone.ui.examine;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.databinding.FragmentPatrolBinding;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragmentViewModel;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
//import q.rorbin.verticaltablayout.VerticalTabLayout;


public class FragmentPatrol extends BaseFragment<FragmentPatrolBinding, FragmentPatrolViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_patrol;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }



    @Override
    public void initData() {
        //viewModel.setPatrolData(sExamineID);
        //MyPagerAdapter mAdapter = new MyPagerAdapter(getFragmentManager());
        viewModel.addPage();
        binding.viewPager.setAdapter(viewModel.adapter);
        binding.tabLayout.setViewPager(binding.viewPager);
        //
        ImageView imageView=binding.toolbarPatrol.findViewById(R.id.toolbar_iv_ok);
        imageView.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View v) {
                MaterialDialogUtils.showBasicDialog(getContext(),"是否提交巡查数据")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if(which==DialogAction.POSITIVE)
                                    viewModel.dealPatrolCreateOK();
                            }
                        })
                        .show();
                }
        });

        //binding.patrolViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.patrolVerticalTabLayout));


        //binding.tabLayout.setViewPager(binding.viewPager);



    }

    @Override
    public void initViewObservable() {

    }

}
