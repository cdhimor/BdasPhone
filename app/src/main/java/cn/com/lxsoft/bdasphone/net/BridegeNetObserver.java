package cn.com.lxsoft.bdasphone.net;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.http.ExceptionHandle;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.ToastUtils;
import retrofit2.HttpException;

import static android.support.constraint.Constraints.TAG;

public class BridegeNetObserver<T> implements Observer<T>,SingleObserver<T> {
    public interface DealData<T>{
        void execute(T t);
    }
    protected   DealData dealDataFunc;

    Disposable disposable;
    @Override
    public void onSubscribe(@NonNull Disposable d){
        disposable=d;
    }

    @Override
    public void onNext(@NonNull T t){
        if(dealDataFunc!=null)
            dealDataFunc.execute(t);
    }

    @Override
    public void onSuccess(@NonNull T t){
        if(dealDataFunc!=null)
            dealDataFunc.execute(t);
    }

    @Override
    public void onError(@NonNull Throwable e){
        ResponseThrowable ex=(ResponseThrowable)e;

            //访问获得对应的Exception
        Log.d(TAG, "======================Error：" + e.getMessage());
        ToastUtils.showShort(ex.message);
        disposable.dispose();

    }

    @Override
    public void onComplete(){

    }

    public BridegeNetObserver<T> dealData(DealData dt) {
        dealDataFunc = dt;
        return this;
    }
}
