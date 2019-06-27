package cn.com.lxsoft.bdasphone.ui.examine;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;

import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.entity.Check;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.ui.browse.QiaoLiangListItemViewModel;
import me.goldze.mvvmhabit.base.BaseViewModel;


public class ExamineListItemViewModel extends QiaoLiangListItemViewModel {
    //public ObservableField<QiaoLiang> entity = new ObservableField<>();
    public String info5;


    interface OnItemClickListener {
        // 回调方法
        void onClick(QiaoLiang tpQiaoLiang);
    }

    OnItemClickListener mOnItemClickListener;


    public ExamineListItemViewModel(@NonNull BaseViewModel viewModel, QiaoLiang tpEntity,short type)
    {
        super(viewModel,tpEntity);

        switch(type){
            case SystemConfig.ExamineStyle_Patrol:
                if (tpEntity.getPatrolID()!=null) {
                    Patrol patrol=tpEntity.getPatrol();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                    info4 = sdf.format(patrol.getDate());
                    String ds=patrol.getDiseaseInfo();
                    if(ds.equals("")){
                        info5="未发现异常";
                    }
                    else{
                        info5="异常部位：".concat(ds);
                    }
                }
                else {
                    info4 = "未巡查";
                    info5 = jieGou;
                }
                break;
            case SystemConfig.ExamineStyle_Check:
                if (tpEntity.getCheckID()!=null) {
                    Check check=tpEntity.getCheck();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                    info4 = sdf.format(check.getDate());
                    String ds=check.getDiseaseInfo();
                    if(ds.equals("")){
                        info5="未发现异常";
                    }
                    else{
                        info5="异常部位：".concat(ds);
                    }
                }
                else {
                    info4 = "未巡查";
                    info5 = jieGou;
                }
                break;
        }
    }
}
