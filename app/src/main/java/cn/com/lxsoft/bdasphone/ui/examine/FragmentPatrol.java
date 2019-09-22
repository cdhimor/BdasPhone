package cn.com.lxsoft.bdasphone.ui.examine;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.amap.api.maps2d.model.LatLng;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;

import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.FragmentPatrolNewBinding;
import cn.com.lxsoft.bdasphone.entity.ImageData;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragmentViewModel;
import cn.com.lxsoft.bdasphone.ui.browse.ContentViewPagerAdapter;
import cn.com.lxsoft.bdasphone.ui.component.BridgeImagePanel;
import cn.com.lxsoft.bdasphone.ui.component.ToolBarBdas;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import cn.com.lxsoft.bdasphone.utils.GlideEnginex;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static android.app.Activity.RESULT_OK;
//import q.rorbin.verticaltablayout.VerticalTabLayout;


public class FragmentPatrol extends BaseFragment<FragmentPatrolNewBinding, FragmentPatrolViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_patrol_new;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        addPatrolPanel("桥路连接处","1",viewModel.patrolData.qiaoLuLianJie);
        addPatrolPanel("桥面铺装、伸缩缝","2",viewModel.patrolData.puZhuangSSF);
        addPatrolPanel("栏杆或护栏","3",viewModel.patrolData.lanGan);
        addPatrolPanel("标志标牌","4",viewModel.patrolData.biaoZhi);
        addPatrolPanel("桥梁线形","5",viewModel.patrolData.xianXing);

        binding.toolbarPatrol.setConfirmOKListener(new ToolBarBdas.OnConfirmOK() {
            @Override
            public void onConfirmOK() {
                viewModel.saveData();
                //checkPositionData();
                //binding.verTablayout.setTabBadge(1,3);
            }
        });

        BridgeImagePanel imgPanel=binding.patrolIamgePanel;
        imgPanel.init(this);
        imgPanel.initNet(viewModel.subscribeBase,viewModel.bridge.getDaiMa());
        imgPanel.onLoadImageDataOK=new BridgeImagePanel.OnLoadImageData() {
            @Override
            public boolean onLoad(List<ImageData> tplist) {
                viewModel.addDiseaseImages(tplist);
                return false;
            }
        };

        viewModel.patrolData.images.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<ImageData>>() {
            @Override
            public void onChanged(ObservableList<ImageData> sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<ImageData> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<ImageData> sender, int positionStart, int itemCount) {
                imgPanel.showImage(sender.subList(positionStart,positionStart+itemCount));
            }

            @Override
            public void onItemRangeMoved(ObservableList<ImageData> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<ImageData> sender, int positionStart, int itemCount) {

            }
        });

        viewModel.initPatrolData();

    }

    @Override
    public void initViewObservable() {

    }

    void addPatrolPanel(String bjName,String disType,ObservableArrayList<String> disList){
        Context context=binding.getRoot().getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup panel=(ViewGroup)inflater.inflate(R.layout.layout_patrol_item,binding.patrolDiseasePannel,false);

        TextView tvName=panel.findViewById(R.id.tv_buJianName);
        tvName.setText(bjName);

        Switch rbtn=panel.findViewById(R.id.switch_checkDisease);
        LinearLayout dListPanel=panel.findViewById(R.id.panel_diseasList);
        ImageView butAdd=panel.findViewById(R.id.but_addPatrolDisease);

        disList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<String>>() {
            @Override
            public void onChanged(ObservableList<String> sender) {
            }

            @Override
            public void onItemRangeChanged(ObservableList<String> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<String> sender, int positionStart, int itemCount) {
                if(sender.size()==1)
                    setPanelState(false);
                for(int i=positionStart;i<itemCount;i++){
                    DiseaseTextView tv=new DiseaseTextView(context);
                    String tpcode=sender.get(positionStart);
                    tv.setData(tpcode,viewModel.transDisName(tpcode));
                    dListPanel.addView(tv);
                }
            }

            @Override
            public void onItemRangeMoved(ObservableList<String> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<String> sender, int positionStart, int itemCount) {
                if(sender.size()==0)
                    setPanelState(true);
            }

            void setPanelState(boolean bNoDisease){
                if(bNoDisease) {
                    rbtn.setChecked(true);
                    dListPanel.setVisibility(View.VISIBLE);
                    butAdd.setVisibility(View.GONE);
                }
                else {
                    rbtn.setChecked(false);
                    dListPanel.setVisibility(View.VISIBLE);
                    butAdd.setVisibility(View.VISIBLE);
                }
            }
        });

        rbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    dListPanel.setVisibility(View.GONE);
                    butAdd.setVisibility(View.GONE);
                }
                else {
                    dListPanel.setVisibility(View.VISIBLE);
                    butAdd.setVisibility(View.VISIBLE);
                }
            }
        });

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList keySet=new ArrayList();
                ArrayList valueSet=new ArrayList();
                viewModel.getDiseasData(disType,keySet,valueSet);
                if(!disType.equals("2")) {
                    viewModel.addDiseaseData(disType, keySet);
                    return;
                }
                MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                        .title("病害选择")
                        .items(valueSet)
                        .itemsCallbackMultiChoice(null, new MaterialDialog
                                .ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                ArrayList resList=new ArrayList();
                                for(int i=0;i<which.length;i++){
                                    resList.add(keySet.get(which[i]));
                                }
                                viewModel.addDiseaseData(disType,resList);
                                return true; // allow selection
                            }
                        })
                        .positiveText("确定")
                        .negativeText("取消")
                        .neutralText("清除");
                builder.show();

            }
        });


        binding.patrolDiseasePannel.addView(panel);
        /*
        List<RadioButtonDiesaseInfo> rbList=new ArrayList<>();

        TextView tvGouJian=(TextView)view.findViewById(R.id.test_add_gouJian);
        tvGouJian.setText(disease.getGouJian());
        */
    }


    class DiseaseTextView extends AppCompatTextView{
        public String code;

        public DiseaseTextView(Context context) {
            super(context);

            FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 12);
            setLayoutParams(layoutParams);
            setBackgroundResource(R.drawable.shape_rect_border_primarycolor);
            Drawable drawable= getResources().getDrawable(R.drawable.ic_dot_green);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            setCompoundDrawables(drawable,null,null,null);
            setGravity(Gravity.CENTER_VERTICAL);
            setPadding(24,16,24,16);
            setTextColor(Color.BLACK);
        }

        public void setData(String cd,String res){
            code=cd;
            setText(res);
        }
    }


}
