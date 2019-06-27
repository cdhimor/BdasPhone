package cn.com.lxsoft.bdasphone.ui.examine;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.DataSession;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.ui.browse.BaseBrowseFragmentViewModel;
import cn.com.lxsoft.bdasphone.ui.browse.QiaoLiangListItemViewModel;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;


public class ExamineBrowseFragmentViewModel extends BaseBrowseFragmentViewModel {
    public short nBrowseType=SystemConfig.ExamineStyle_Patrol;

    public ObservableField<String> oStringChooseQiaoLiang = new ObservableField<>();


    //boolean bDWDataInit

    public ExamineBrowseFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {

        whereConditionMain=QiaoLiangDao.Properties.DaiMa.isNotNull();
        propertyOrderMain=QiaoLiangDao.Properties.DaiMa;

        observableListMain = new ObservableArrayList<>();
        itemBindingMain = ItemBinding.of(BR.viewModel, R.layout.layout_examine_item);
        adapterMain = new BindingRecyclerViewAdapter<>();

        onToolbarDiseaseSearch(1);

        //dealMainDataBinding(pageIndexMain.get());

    }

    protected void dealMainItem(Object tpobj) {
        QiaoLiang tpEntity = (QiaoLiang) tpobj;
        ExamineListItemViewModel itemViewModel = new ExamineListItemViewModel(ExamineBrowseFragmentViewModel.this,tpEntity,nBrowseType);
        //itemViewModel.parentViewModel=ExamineBrowseFragmentViewModel.this;
        //双向绑定动态添加Item
        observableListMain.add(itemViewModel);
        itemViewModel.setItemClickListener(new QiaoLiangListItemViewModel.OnItemClickListener() {
            @Override
            public void onClick(QiaoLiang tpQiaoLiang) {
                //oStringChooseQiaoLiang.set(tpQiaoLiang.getDaiMa());
                //DataSession.setCurrentQiaoLiang(tpQiaoLiang);
                DataSession.setCurrentQiaoLiang(tpQiaoLiang);
                switch (nBrowseType){
                    case SystemConfig.ExamineStyle_Patrol:
                        //mBundle.putString("id",tpQiaoLiang.getPatrolID());
                        startContainerActivity(FragmentPatrol.class.getCanonicalName());
                        break;
                    case SystemConfig.ExamineStyle_Check:
                        startContainerActivity(FragmentCheck.class.getCanonicalName());
                        break;
                    case SystemConfig.ExamineStyle_Test:
                        startContainerActivity(FragmentTest.class.getCanonicalName());
                        break;
                }
            }
        });
    }

    int searchType=1;
    protected List<Object> getMainListData(WhereCondition whereCondition, Property property,boolean bUp,int pageIndex)
    {
        List tplist;
        DataBase database = AppApplication.dataBase;
        if(searchType==3) {
            tplist=database.getBridgePatrolDiseaseData(pageIndex);
        }
        else
        {
            tplist = database.getQiaoLiangData(whereConditionMain, propertyOrderMain, oBoolMainOrderUp.get(), pageIndex);
        }
        return tplist;
    }


    public void onToolbarDiseaseSearch(int type)
    {
        searchType=type;
        switch (nBrowseType) {
            case SystemConfig.ExamineStyle_Patrol:
            case SystemConfig.ExamineStyle_Test:
                switch (type) {
                    case 1:
                        whereConditionMain = QiaoLiangDao.Properties.PatrolID.isNotNull();
                        break;
                    case 2:
                        whereConditionMain = QiaoLiangDao.Properties.PatrolID.isNull();
                        break;
                }
                break;
            case SystemConfig.ExamineStyle_Check:
                switch (type) {
                    case 1:
                        whereConditionMain = QiaoLiangDao.Properties.CheckID.isNotNull();
                        break;
                    case 2:
                        whereConditionMain = QiaoLiangDao.Properties.CheckID.isNull();
                        break;
                }
                break;
        }
        dealMainDataBinding(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}