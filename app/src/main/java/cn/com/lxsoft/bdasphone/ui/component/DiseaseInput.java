package cn.com.lxsoft.bdasphone.ui.component;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.DiseaseInfo;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import me.goldze.mvvmhabit.utils.ConvertUtils;

public class DiseaseInput extends LinearLayout {

    public DiseaseInput(Context context, AttributeSet attrs) {
        super(context, attrs);

        TextView tv = new TextView(this.getContext());
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,ConvertUtils.dp2px(32));
        tv.setLayoutParams(layoutParams);
        this.addView(tv);
    }

    public void setData(List<DiseaseInfo> dList){
        if(dList.isEmpty())
            return;
        this.removeAllViews();
        for(int i=0;i<dList.size();i++)
        {
            addDiseaseLabel(dList.get(i).getDingXingMiaoShu());
        }
    }

    public void setData(String bhID)
    {
        if(bhID==null)
            return;
        this.removeAllViews();
        DiseaseInfo di=DataDict.getBHInfo(bhID);
        if(di!=null)
            addDiseaseLabel(di.getDingXingMiaoShu());
    }

    protected void addDiseaseLabel(String text){
        TextView tv = new TextView(this.getContext());
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(4));
        int padding=ConvertUtils.dp2px(4);
        tv.setPadding(padding,padding,padding,padding);
        tv.setLayoutParams(layoutParams);
        tv.setText(text);
        tv.setBackgroundResource(R.drawable.shape_rect_noborder_lightgray);
        this.addView(tv);
    }

}
