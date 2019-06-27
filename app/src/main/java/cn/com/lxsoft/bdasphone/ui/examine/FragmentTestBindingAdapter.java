package cn.com.lxsoft.bdasphone.ui.examine;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.databinding.LayoutTestViewpagerItemBinding;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.DiseaseInfo;
import cn.com.lxsoft.bdasphone.ui.component.DiseaseInput;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import me.goldze.mvvmhabit.utils.ConvertUtils;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.LayoutManagers;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by goldze on 2018/6/21.
 */

public class FragmentTestBindingAdapter extends BindingViewPagerAdapter<FragmentTestItemViewModel> implements TabAdapter {
    //public FragmentTestViewModel fragmentTestViewModel;
    //public FragmentCheck fragmentCheck;
    public List<String> titles;
    protected Map<String,MaterialDialog> gjDialogMap;
    protected Map<String,MaterialDialog> dsDialogMap;

    @Override
    public void onBindBinding(final ViewDataBinding _binding, int variableId, int layoutRes, final int position, FragmentTestItemViewModel viewModel) {
        super.onBindBinding(_binding, variableId, layoutRes, position, viewModel);

        LayoutTestViewpagerItemBinding binding = (LayoutTestViewpagerItemBinding) _binding;
        Context context=_binding.getRoot().getContext();

        binding.butAddTestDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disease disease=viewModel.addDisease();
                addDiseaseData(binding,viewModel,disease);
            }
        });

        List<Disease> dList=viewModel.getDiseaseList();
        for(int i=0;i<dList.size();i++)
        {
            addDiseaseData(binding,viewModel,dList.get(i));
        }

        return;
    }

    void addDiseaseData(LayoutTestViewpagerItemBinding binding,FragmentTestItemViewModel viewModel,Disease disease){
        Context context=binding.getRoot().getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup view=(ViewGroup)inflater.inflate(R.layout.layout_test_disease_item,binding.layoutTestDiseaseList,false);

        List<RadioButtonDiesaseInfo> rbList=new ArrayList<>();

        TextView tvGouJian=(TextView)view.findViewById(R.id.test_add_gouJian);
        tvGouJian.setText(disease.getGouJian());

        tvGouJian.setOnClickListener(new View.OnClickListener() {
            MaterialDialog dialog=null;
            @Override
            public void onClick(View v) {
                List<String> gjList = Arrays.asList(viewModel.getGouJianList());
                if(dialog==null) {
                    dialog = new MaterialDialog.Builder(context)
                            .title("构件编号")
                            .items(gjList)
                            .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View itemView, int which,
                                                           CharSequence text) {

                                    tvGouJian.setText(gjList.get(which));
                                    disease.setGouJian(gjList.get(which));
                                    return true; // allow selection
                                }
                            })
                            .negativeText("取消")
                            .positiveText("选择").build();
                }
                dialog.show();
            }
        });

        DiseaseInput inputDisease=(DiseaseInput)view.findViewById(R.id.test_add_disease);
        inputDisease.setData(disease.getBingHai());

        inputDisease.setOnClickListener(new View.OnClickListener() {
            MaterialDialog dialog=null;
            @Override
            public void onClick(View v) {
                if(dialog!=null) {
                    dialog.show();
                    return;
                }
                //List<String> gjList= Arrays.asList(viewModel.getGouJianList());
                //MaterialDialogUtils.showSingleListDialog(context,"构件编号",gjList).show();
                dialog=MaterialDialogUtils.showCustomDialog(context,"病害",R.layout.layout_disese_info_list)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            List<DiseaseInfo> checkDiseaseList=null;
                            for(int i=0;i<rbList.size();i++)
                            {
                                if(rbList.get(i).isChecked())
                                {
                                    if(checkDiseaseList==null)
                                        checkDiseaseList=new ArrayList<>();
                                    checkDiseaseList.add(rbList.get(i).diseaseInfo);
                                }
                            }
                            if(checkDiseaseList!=null)
                            {
                                inputDisease.setData(checkDiseaseList);
                                disease.setBingHai(checkDiseaseList.get(0).getBianMa());
                                //viewModel.insertDiseaseItem(tvGouJian.getText().toString(),checkDiseaseList.get(0));
                            }
                        }
                    }).build();

                //MaterialDialog dialog=dialogBd.build();

                Construct construct=viewModel.getConstruct();

                LinearLayout listPanel = (LinearLayout) dialog.getCustomView().findViewById(R.id.diseaseInfo_list_panel);
                LinkedHashMap<String,HashMap<String,DiseaseInfo>> hashData=DataDict.getDiseaseList(construct.getBuJian(),construct.getCaiZhi());

                for(String key:hashData.keySet()) {

                    //layoutParams = getLayoutParams(LayoutParams.WRAP_CONTENT, height, top);

                    TextView tv = new TextView(context);
                    FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,ConvertUtils.dp2px(32));
                    layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(8));
                    tv.setLayoutParams(layoutParams);
                    tv.setText(key);
                    tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                    tv.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_label_gray_24dp),null,null,null);
                    tv.setBackgroundResource(R.drawable.shape_rect_bottomline_gray);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setPadding(10,10,10,10);

                    listPanel.addView(tv);

                            /*
                            RadioGroup rd=new RadioGroup(context);
                            LinearLayout.LayoutParams rdParams=new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                            rd.setLayoutParams(rdParams);
                            rd.setOrientation(LinearLayout.VERTICAL);
                            listPanel.addView(rd);
                            */

                    Collection<DiseaseInfo> dlist=hashData.get(key).values();
                    Iterator it = dlist.iterator();
                    while(it.hasNext())
                    {
                        DiseaseInfo dix=(DiseaseInfo)it.next();
                        RadioButtonDiesaseInfo button=new RadioButtonDiesaseInfo(context,dix);
                        listPanel.addView(button);
                        rbList.add(button);
                    }
                }
                dialog.show();
            }
        });
        binding.layoutTestDiseaseList.addView(view);
    }



    @Override
    public TabView.TabBadge getBadge(int position) {
        //if (position == 2) return new TabView.TabBadge.Builder().setBadgeNumber(666)
          //      .setExactMode(true).build();
        return null;
    }

    @Override
    public TabView.TabIcon getIcon(int position) {
        return null;
    }


    @Override
    public TabView.TabTitle getTitle(int position) {

        String title=titles.get(position);
        return new TabView.TabTitle.Builder()
                .setContent(title)
                .setTextColor(0xFF009900, Color.BLACK)
                .build();
    }

    @Override
    public int getBackground(int position) {
        return R.drawable.shape_rect_botomrightline_lightgray;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title=titles.get(position);
        return title;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    class RadioButtonDiesaseInfo extends AppCompatRadioButton {
        public DiseaseInfo diseaseInfo;


        public RadioButtonDiesaseInfo(Context context,DiseaseInfo di) {
            super(context);

            FrameLayout.LayoutParams buttonParams=new FrameLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
            buttonParams.setMargins(0, 0, 0, ConvertUtils.dp2px(8));
            this.setLayoutParams(buttonParams);
            this.setText(di.getDingXingMiaoShu());

            diseaseInfo=di;
        }

    }
}
