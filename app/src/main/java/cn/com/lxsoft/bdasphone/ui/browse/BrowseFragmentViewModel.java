package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import org.greenrobot.greendao.Property;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.utils.ConvertUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;


public class BrowseFragmentViewModel extends BaseBrowseFragmentViewModel {
    public ObservableInt oIntToolbarSelectedPanel = new ObservableInt(0);
    public ObservableInt oIntClearCheck=new ObservableInt(0);

    public SlideBrowseViewModel slideBrowseViewModel;

    public List<ObservableBoolean> oOrderList=null;

    protected String whereSearchA="";
    protected String whereSearchB="";

    public BrowseFragmentViewModel(@NonNull Application application) {

        super(application);
        slideBrowseViewModel=new SlideBrowseViewModel(application);
    }

    //type:1-quicksearch,2-lx/deptsearch
    protected void setSearchData(String key,String res,int type)
    {
        whereSearchString=SystemConfig.BuildSearchData(key,res);
        if(type==1)
            whereSearchA=SystemConfig.BuildSearchData(key,res);
        else if(type==2)
            whereSearchB=SystemConfig.BuildSearchData(key,res);

        if(!ConvertUtils.isSpace(whereSearchA) && !ConvertUtils.isSpace(whereSearchB)) {
            whereSearchString=SystemConfig.BuildMutipleSearchData(whereSearchA, whereSearchB);
        }
        else if(!ConvertUtils.isSpace(whereSearchA)){
            whereSearchString=whereSearchA;
        }
        else if(!ConvertUtils.isSpace(whereSearchB)){
            whereSearchString=whereSearchB;
        }
        setBridgeSearchData();
    }


    @Override
    public void onCreate() {

        observableListMain = new ObservableArrayList<>();
        itemBindingMain = ItemBinding.of(BR.viewModel, R.layout.layout_qiaoliang_item);
        adapterMain = new BindingRecyclerViewAdapter<>();
        //dealMainDataBinding(pageIndexMain.get());

        slideBrowseViewModel.setItemClickListener(new SlideBrowseViewModel.OnItemClickListener() {
            @Override
            public void onItemClick(short listType, Object object) {
                if(listType==SystemConfig.SlideList_Type_Path){
                    setSearchData("m0.4",(((LuXian)object).getBianHao()),2);
                }
                else if(listType==SystemConfig.SlideList_Type_Dept) {
                    setSearchData("m0.3",(((DanWei)object).getDaiMa()),2);
                }
                dealMainDataBinding(0);
            }
        });

        slideBrowseViewModel.onCreate();

        oOrderList=new ArrayList<>();
        for (int i = 0; i < 4; i++)
            oOrderList.add(new ObservableBoolean(true));

        /*
        if(DataSession.getSearchInfo()!="")
            initSearchInfo=DataSession.getSearchInfo();
        DataSession.setSearchInfo("");
        */
    }

    protected void dealMainItem(Object tpobj){
        QiaoLiang tpEntity=(QiaoLiang)tpobj;
        QiaoLiangListItemViewModel itemViewModel = new QiaoLiangListItemViewModel(BrowseFragmentViewModel.this, tpEntity);
        //itemViewModel.parentViewModel=BrowseFragmentViewModel.this;
        //双向绑定动态添加Item
        observableListMain.add(itemViewModel);
        itemViewModel.setItemClickListener(new QiaoLiangListItemViewModel.OnItemClickListener() {
            @Override
            public void onClick(QiaoLiang tpQiaoLiang) {
                //oStringChooseQiaoLiang.set(tpQiaoLiang.getDaiMa());
                DataSession.setCurrentQiaoLiang(tpQiaoLiang);
                startContainerActivity(ContentFragment.class.getCanonicalName());
            }
        });
    }

    public void onToolbarSelectedPanelChange(int i) {
        if (i == 5) {//luxian
            slideBrowseViewModel.initData(SystemConfig.SlideList_Type_Path);
        }
        else if(i==4){
            slideBrowseViewModel.initData(SystemConfig.SlideList_Type_Dept);
        }
        //else if (oIntToolbarSelectedPanel.get() != i)
        oIntToolbarSelectedPanel.set(i);
    }

    public void onToolbarQuickSearch(int searchNum) {
        String searchRes="";
        if(searchNum==-1 || searchNum==10 || searchNum==20)
            setSearchData("m0.1","NOTNULL",1);
        else if(searchNum>20) {
            setSearchData("m1.1", Integer.toString(searchNum - 20), 1);
            oIntClearCheck.set(1);
        }
        else if (searchNum > 10) {
            setSearchData("m6.0", Integer.toString(searchNum - 10), 1);
            oIntClearCheck.set(2);
        }
        dealMainDataBinding(0);
    }

    private int curOrderNum=0;

    public void onToolbarOrderSet(int orderNum)
    {
        ObservableBoolean tporder=oOrderList.get(orderNum);
        if(curOrderNum==orderNum)
            tporder.set(!tporder.get());
        //boolean bUp=oBoolMainOrderUp.get();
        //boolean bUp=oOrderList.get(orderNum).get();
        String pt="m0.1";
        switch (orderNum)
        {
            case 1:
                pt="m0.4";
                break;
            case 2:
                pt="i1.2";
                break;
            case 3:
                pt="i1.1";
                break;
        }

        curOrderNum=orderNum;
        setOrderData(pt,tporder.get());
        dealMainDataBinding(0);
    }


    protected List<Object> getMainListData(WhereCondition whereCondition, Property property,boolean bUp,int pageIndex)
    {
        DataBase database = AppApplication.dataBase;
        List tplist;
        //    tplist=database.getQiaoLiangData(whereConditionMain,propertyOrderMain,oBoolMainOrderUp.get(),pageIndex);
        //if(pageIndex==0)
        //    tplist=database.getQiaoLiangDataAdv(advSearchCondition,propertyOrderMain,oBoolMainOrderUp.get(),pageIndex);
        //else
            tplist=database.getBridgePageData(pageIndex);
        return tplist;
    }

    public void setSearchHistoryData(String name,String data){
        if(name=="")
            return;
        DataBase database = AppApplication.dataBase;
        database.addSearchHistoryData(name,data);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        slideBrowseViewModel.onDestroy();

    }



}