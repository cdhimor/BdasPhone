package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.BridgeBaseViewModel;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;


public class BaseBrowseFragmentViewModel extends BridgeBaseViewModel {

    public ObservableList<ItemViewModel<BaseViewModel>> observableListMain;
    public ItemBinding<ItemViewModel<BaseViewModel>> itemBindingMain;
    public BindingRecyclerViewAdapter<ItemViewModel<BaseViewModel>> adapterMain;



    protected WhereCondition whereConditionMain;
    protected Property propertyOrderMain;
    public ObservableInt pageIndexMain = new ObservableInt(-1);
    public ObservableBoolean oBoolMainOrderUp=new ObservableBoolean(true);
    public String whereSearchString="";
    public String orderKeyString="";
    public int pageCount=1;
    public long dataNum=0;

        //boolean bDWDataInit

    public BaseBrowseFragmentViewModel(@NonNull Application application) {
        super(application);
        nActivityPosition=1;
    }

    @Override
    public void onCreate() {

    }


    protected void setSearchData(String key,String res)
    {
        whereSearchString=SystemConfig.BuildSearchData(key,res);
        setBridgeSearchData();
    }

    protected void setSearchData(String res)
    {
        whereSearchString=res;
        setBridgeSearchData();
    }

    protected void setOrderData(String key,boolean bUp)
    {
        orderKeyString=key;
        oBoolMainOrderUp.set(bUp);
        setBridgeSearchData();
    }

    protected void setBridgeSearchData(){
        DataBase database = AppApplication.dataBase;
        database.setBridgeSearchData(whereSearchString,orderKeyString,oBoolMainOrderUp.get());
        dataNum=database.getBridgeNum();
        pageCount=(int)Math.ceil((float)dataNum/SystemConfig.PageSizeBridge);
        pageIndexMain.set(-1);
        observableListMain.clear();
    }

    protected String joinSearchRes(String res1,String res2)
    {
        return res1+"┼"+res2;
    }

    protected void dealMainItem(Object obj){}


    protected void dealMainDataBinding(Property orderProperty,boolean bUp,int pageIndex){
        //propertyOrderMain=orderProperty;
        oBoolMainOrderUp.set(bUp);
        dealMainDataBinding(pageIndex);
    }

    protected void dealMainDataBinding(WhereCondition whereCondition,int pageIndex){
        whereConditionMain=whereCondition;
        dealMainDataBinding(pageIndex);
    }

    protected List<Object> getMainListData(WhereCondition whereCondition, Property property,boolean bUp,int pageIndex)
    {
        DataBase database = AppApplication.dataBase;
        //List tplist=database.getQiaoLiangData(whereConditionMain,propertyOrderMain,oBoolMainOrderUp.get(),pageIndex);
        List tplist=database.getBridgePageData(pageIndex);
        return tplist;
    }

    protected void dealMainDataBinding(int pageIndex) {
        //pageIndexMain.set(pageIndex);

        io.reactivex.Observable.create(new ObservableOnSubscribe<List<Object>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Object>> emitter) throws Exception{
                emitter.onNext(getMainListData(whereConditionMain,propertyOrderMain,oBoolMainOrderUp.get(),pageIndex));
                emitter.onComplete();

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<Object>>() {
                            @Override
                            public void accept(List<Object> qiaoLiangs) {
                                pageIndexMain.set(pageIndex);
                                for (int i = 0; i < qiaoLiangs.size(); i++) {
                                    dealMainItem(qiaoLiangs.get(i));
                                }
                            }
                        }
                );
    }

    public BindingCommand onLoadMoreCommandQiaoLiang = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(pageIndexMain.get()==pageCount-1)
                return;
            else
                dealMainDataBinding(pageIndexMain.get()+1);
        }
    });



    @Override
    public void onDestroy() {
        super.onDestroy();

        observableListMain.clear();
        observableListMain=null;
    }

}