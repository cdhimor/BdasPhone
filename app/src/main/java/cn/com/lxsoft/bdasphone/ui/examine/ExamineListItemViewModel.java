package cn.com.lxsoft.bdasphone.ui.examine;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.entity.Check;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.YearTest;
import cn.com.lxsoft.bdasphone.ui.browse.QiaoLiangListItemViewModel;
import cn.com.lxsoft.bdasphone.utils.ConvertUtils;
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

        String ds="";
        switch(type){
            case SystemConfig.ExamineStyle_Patrol:
                Patrol patrol=AppApplication.dataBase.getPatrolDataFromBridge(tpEntity.getDaiMa());
                info4 = ConvertUtils.getDateNameNoYear(patrol.getDate());
                ds=patrol.getDiseaseInfo();
                if(ds.equals("")){
                    info5="未发现异常";
                }
                else{
                    info5=ds;
                }
                break;
            case SystemConfig.ExamineStyle_Check:
                Check check=AppApplication.dataBase.getCheckDataFromBridge(tpEntity.getDaiMa());
                info4 = ConvertUtils.getDateNameNoYear(check.getDate());
                ds=check.getDiseaseInfo();
                if(ds.equals("")){
                    info5="未发现异常";
                }
                else{
                    info5=ds;
                }
                break;
            case SystemConfig.ExamineStyle_Test:
                YearTest test=AppApplication.dataBase.getYearTestDataFromBridge(tpEntity.getDaiMa());
                if(test!=null){
                    info4 = ConvertUtils.getDateName(test.getDate());
                    info5="评分"+Integer.toString(test.getPingFen())+"▪上部"+Integer.toString(test.getShangBuJieGouPingFen())+
                            "▪下部"+Integer.toString(test.getXiaBuJieGouPingFen())+
                            "▪桥面系"+Integer.toString(test.getQiaoMianXiPingFen());
                }
                else {
                    info4 = "未定期检查";
                    info5 = jieGou;
                }
                break;
        }
    }
}
