package cn.com.lxsoft.bdasphone.ui.component;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.DiseaseInfo;
import cn.com.lxsoft.bdasphone.entity.ImageData;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ConvertUtils;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;

public class DiseaseInputPanel extends LinearLayout {

    //缓存构件列表、构件选择对话框、病害选择对话框
    static HashMap<String,DisSelData> mapDisSelData=new HashMap<>();
    static DiseaseInput currentDiseaseInput;
    static Disease currentDisease;
    static TextView currentGjTextView;

    class DisSelData{
        public List<String> gjList=null;
        public MaterialDialog diseaseDialog=null;
        public MaterialDialog gjDialog=null;
        public List<RadioButtonDiesaseInfo> dInfoRbList;
    }

    public boolean bShowGJ=true;
    public boolean bInitGJ=true;
    public String sAutoGJ="";


    public DiseaseInputPanel(Context context) {
        super(context);

        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
    }

    public void init(Construct construct,Disease disease,LinearLayout parentLayout,BaseFragment fragment){

        Context context=this.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup view=(ViewGroup)inflater.inflate(R.layout.layout_test_disease_item,this,true);


        DisSelData tpData=mapDisSelData.get(construct.getBuJian()+construct.getCaiZhi());
        if(tpData==null) {
            final DisSelData mapData = new DisSelData();
            tpData = mapData;
            mapDisSelData.put(construct.getBuJian() + construct.getCaiZhi(), mapData);

            List<RadioButtonDiesaseInfo> rbList = new ArrayList<>();
            mapData.dInfoRbList = rbList;

            mapData.diseaseDialog = MaterialDialogUtils.showCustomDialog(context, "病害", R.layout.layout_disese_info_list)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        List<RadioButtonDiesaseInfo> rbDgList = rbList;

                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            List<DiseaseInfo> checkDiseaseList = null;
                            for (int i = 0; i < rbDgList.size(); i++) {
                                if (rbDgList.get(i).isChecked()) {
                                    if (checkDiseaseList == null)
                                        checkDiseaseList = new ArrayList<>();
                                    checkDiseaseList.add(rbDgList.get(i).diseaseInfo);
                                }
                            }
                            if (checkDiseaseList != null) {
                                currentDiseaseInput.setData(checkDiseaseList);
                                currentDisease.setBingHai(checkDiseaseList.get(0).getBianMa());
                                //viewModel.insertDiseaseItem(tvGouJian.getText().toString(),checkDiseaseList.get(0));
                            }
                        }
                    }).build();

            LinearLayout listPanel = (LinearLayout) mapData.diseaseDialog.getCustomView().findViewById(R.id.diseaseInfo_list_panel);
            LinkedHashMap<String, HashMap<String, DiseaseInfo>> hashData = DataDict.getDiseaseList(construct.getBuJian(), construct.getCaiZhi());

            for (String key : hashData.keySet()) {
                TextView tv = new TextView(context);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(32));
                layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(8));
                tv.setLayoutParams(layoutParams);
                tv.setText(key);
                tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                //tv.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_label_gray_24dp),null,null,null);
                tv.setBackgroundResource(R.drawable.shape_rect_bottomline_gray);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setPadding(10, 10, 10, 10);
                listPanel.addView(tv);

                        /*
                        RadioGroup rd=new RadioGroup(context);
                        LinearLayout.LayoutParams rdParams=new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                        rd.setLayoutParams(rdParams);
                        rd.setOrientation(LinearLayout.VERTICAL);
                        listPanel.addView(rd);
                        */

                Collection<DiseaseInfo> dlist = hashData.get(key).values();
                Iterator it = dlist.iterator();
                while (it.hasNext()) {
                    DiseaseInfo dix = (DiseaseInfo) it.next();
                    RadioButtonDiesaseInfo button = new RadioButtonDiesaseInfo(context, dix);
                    listPanel.addView(button);
                    rbList.add(button);
                }
            }

            if (bInitGJ) {
                mapData.gjList = Arrays.asList(construct.getGouJian().split(","));

                mapData.gjDialog = new MaterialDialog.Builder(context)
                        .title("构件编号")
                        .items(mapData.gjList)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which,
                                                       CharSequence text) {

                                currentGjTextView.setText(mapData.gjList.get(which));
                                currentDisease.setGouJian(mapData.gjList.get(which));
                                return true; // allow selection
                            }
                        })
                        .negativeText("取消")
                        .positiveText("选择").build();
            }
        }
        final DisSelData disSelData=tpData;


        TextView tvGouJian=(TextView)view.findViewById(R.id.test_add_gouJian);
        tvGouJian.setText(disease.getGouJian());

        if(bShowGJ==false || bInitGJ==false)
            ((LinearLayout)tvGouJian.getParent()).setVisibility(GONE);

        if(!sAutoGJ.isEmpty()){
            disease.setGouJian(sAutoGJ);
        }

        tvGouJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDisease=disease;
                currentGjTextView=tvGouJian;
                disSelData.gjDialog.show();
            }
        });

        DiseaseInput inputDisease=(DiseaseInput)view.findViewById(R.id.test_add_disease);
        inputDisease.setData(disease.getBingHai());

        inputDisease.setOnClickListener(new View.OnClickListener() {
            MaterialDialog dialog=null;
            @Override
            public void onClick(View v) {
                currentDisease=disease;
                currentDiseaseInput=inputDisease;
                disSelData.diseaseDialog.show();
                List<RadioButtonDiesaseInfo> tplist=disSelData.dInfoRbList;
                for(int i=0;i<tplist.size();i++){
                    RadioButtonDiesaseInfo tpinfo=tplist.get(i);
                    tpinfo.setChecked(false);
                    if(!currentDiseaseInput.getData().isEmpty() && tpinfo.diseaseInfo.getBianMa().equals(currentDiseaseInput.getData()))
                        tpinfo.setChecked(true);
                }
                //List<String> gjList= Arrays.asList(viewModel.getGouJianList());
                //MaterialDialogUtils.showSingleListDialog(context,"构件编号",gjList).show();
            }
        });

        BridgeImagePanel imgPanel=view.findViewById(R.id.test_image_panel);
        imgPanel.init(fragment);
        imgPanel.setButtonHidden();

        imgPanel.onLoadImageOK=new BridgeImagePanel.OnLoadImage() {
            @Override
            public boolean onLoad(List<Uri> tplist) {
                disease.getImageList().addAll(ImageData.createImageList(tplist,true));
                return true;
            }
        };
        imgPanel.showImage(ImageData.parseImageData(disease.getImages()));

        ImageView ivDelete=(ImageView)view.findViewById(R.id.test_delete_disease);

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
                        .title("是否删除该病害？")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //viewModel.deleteDisease(disease);
                                parentLayout.removeView(DiseaseInputPanel.this);
                            }
                        }).show();
            }
        });

        ImageView ivRemark=(ImageView)view.findViewById(R.id.test_add_remark);
        EditText etRemark=(EditText)view.findViewById(R.id.test_remark_edit);

        ivRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.test_add_remark_panel).setVisibility(View.VISIBLE);
            }
        });
        if(!cn.com.lxsoft.bdasphone.utils.ConvertUtils.isSpace(disease.getRemark())){
            etRemark.setText(disease.getRemark());
            view.findViewById(R.id.test_add_remark_panel).setVisibility(View.VISIBLE);
        }
        etRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                disease.setRemark(s.toString());
            }
        });

        ImageView ivImage=(ImageView)view.findViewById(R.id.test_add_image);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPanel.openChooseDialog();
                return;
            }
        });

        parentLayout.addView(this);
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
