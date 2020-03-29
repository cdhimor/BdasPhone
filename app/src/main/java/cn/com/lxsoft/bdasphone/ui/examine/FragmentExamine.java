package cn.com.lxsoft.bdasphone.ui.examine;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.databinding.FragmentExamineDealBinding;
import cn.com.lxsoft.bdasphone.databinding.FragmentPatrolNewBinding;
import cn.com.lxsoft.bdasphone.entity.ImageData;
import cn.com.lxsoft.bdasphone.ui.component.BridgeImagePanel;
import cn.com.lxsoft.bdasphone.ui.component.ToolBarBdas;
import me.goldze.mvvmhabit.base.BaseFragment;
//import q.rorbin.verticaltablayout.VerticalTabLayout;


public class FragmentExamine extends BaseFragment<FragmentExamineDealBinding, FragmentExamineViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_examine_deal;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

}
