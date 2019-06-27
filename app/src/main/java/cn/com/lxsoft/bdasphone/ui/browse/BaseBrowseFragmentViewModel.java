package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
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
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.ui.examine.FragmentCheck;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;


public class BaseBrowseFragmentViewModel extends BaseViewModel {

    public ObservableList<ItemViewModel<BaseViewModel>> observableListMain;
    public ItemBinding<ItemViewModel<BaseViewModel>> itemBindingMain;
    public BindingRecyclerViewAdapter<ItemViewModel<BaseViewModel>> adapterMain;

    protected WhereCondition whereConditionMain;
    protected Property propertyOrderMain;
    public ObservableInt pageIndexMain = new ObservableInt(0);
    public ObservableBoolean oBoolMainOrderUp=new ObservableBoolean(true);

        //boolean bDWDataInit

    public BaseBrowseFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {

    }

    private WhereCondition getWhereCondition(String type, String res){
        WhereCondition wc=null;
        switch (type)
        {
            case "LuXian":
                wc= QiaoLiangDao.Properties.LuXianID.eq(res);
                break;
        }
        whereConditionMain=wc;
        return wc;
    }

    protected void setSearchData(String key,String res)
    {
        DataBase database = AppApplication.dataBase;
        database.setBridgeSearchData(SystemConfig.BuildSearchData(key,res));
    }

    protected void setSearchData(String res)
    {
        DataBase database = AppApplication.dataBase;
        database.setBridgeSearchData(res);
    }

    protected String joinSearchRes(String res1,String res2)
    {
        return res1+"┼"+res2;
    }


    protected void dealMainDataBinding(Property orderProperty,boolean bUp,int pageIndex){
        propertyOrderMain=orderProperty;
        oBoolMainOrderUp.set(bUp);
        dealMainDataBinding(pageIndex);
    }

    protected void dealMainDataBinding(WhereCondition whereCondition,int pageIndex){
        whereConditionMain=whereCondition;
        dealMainDataBinding(pageIndex);
    }

    protected void dealMainItem(Object obj){}

    protected List<Object> getMainListData(WhereCondition whereCondition, Property property,boolean bUp,int pageIndex)
    {
        DataBase database = AppApplication.dataBase;
        List tplist=database.getQiaoLiangData(whereConditionMain,propertyOrderMain,oBoolMainOrderUp.get(),pageIndex);
        return tplist;
    }

    protected void dealMainDataBinding(int pageIndex) {
        pageIndexMain.set(pageIndex);

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
                                if(pageIndex==0)
                                    observableListMain.clear();
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