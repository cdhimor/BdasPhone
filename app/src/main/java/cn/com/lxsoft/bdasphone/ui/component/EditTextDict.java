package cn.com.lxsoft.bdasphone.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.support.v7.widget.AppCompatEditText;
import java.util.jar.Attributes;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EditTextDict extends AppCompatEditText {
    String dictStr = "";
    boolean bDictInit=false;

    public EditTextDict(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextDict);
        int indexCount = typedArray.getIndexCount();

        for (int i = 0; i < indexCount; i++) {
            int id = typedArray.getIndex(i);
            switch (id) {
                case R.styleable.EditTextDict_dict:
                    dictStr = typedArray.getString(id);
                    break;
                default:
                    break;
            }
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable res) {
                if(res.equals(""))
                    return;
                if(bDictInit)
                    return;
                bDictInit=true;
                DataBase database = AppApplication.dataBase;
                switch(dictStr.substring(0,1)) {
                    case "m":
                        int pos=dictStr.indexOf("|");
                        if(pos>0) {
                            int reslen=res.length()/2;
                            String resx=DataDict.getDictMap(dictStr.substring(1, pos)).get(res.toString().substring(0,reslen-1));
                            resx.concat(DataDict.getDictMap(dictStr.substring(pos+2)).get(res.toString().substring(reslen)));
                            setText(resx);
                        }
                        else
                            setText(DataDict.getDictMap(dictStr.substring(1)).get(res.toString()));
                        break;
                    case "d":
                        Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                emitter.onNext(database.getDanWeiName(res.toString()));
                                emitter.onComplete();
                            }
                        }).subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String resend) throws Exception {
                                        if(resend!="")
                                            setText(resend);
                                    }
                                });
                        break;
                }
            }
        });


    }

}
