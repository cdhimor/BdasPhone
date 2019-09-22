package cn.com.lxsoft.bdasphone.ui.browse;

import android.support.annotation.NonNull;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.entity.BridgeJCSB;
import cn.com.lxsoft.bdasphone.entity.BridgeJGSJ;
import cn.com.lxsoft.bdasphone.entity.BridgeJJZB;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;

public class ContentViewPagerViewModel extends ItemViewModel {
    public short contentType;
    protected QiaoLiang bridge;

    public BridgeJCSB jcsb;
    public BridgeJGSJ jgsj;
    public BridgeJJZB jjzb;

    public double gisPosLat=0;
    public double gisPosLng=0;

    public ContentViewPagerViewModel(@NonNull BaseViewModel viewModel,short type,QiaoLiang ql,Object data) {
        super(viewModel);
        bridge=ql;
        contentType=type;
        switch (contentType) {
            case SystemConfig.ContentPager_Type_JCSB:
                jcsb = (BridgeJCSB)data;
            break;
            case SystemConfig.ContentPager_Type_JGSJ:
                jgsj = (BridgeJGSJ)data;
                break;
            case SystemConfig.ContentPager_Type_JJZB:
                jjzb = DataSession.getTestJJZB().get(0);
                break;
            case SystemConfig.ContentPager_Type_DLXX:
                gisPosLat=bridge.getLat();
                gisPosLng=bridge.getLng();
                break;
        }
    }

    public void saveOrReplaceData(){
        switch (contentType) {
            case SystemConfig.ContentPager_Type_DLXX:
                if(gisPosLat>1)
                {
                    bridge.setLat(gisPosLat);
                    bridge.setLng(gisPosLng);
                    DataBase database = AppApplication.dataBase;
                    database.insertOrReplaceBridgeData(bridge);
                }
                break;
        }
    }
}
