package cn.com.lxsoft.bdasphone.entity;


import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.net.BridegeNetObserver;
import cn.com.lxsoft.bdasphone.net.ResponseInfo;
import cn.com.lxsoft.bdasphone.net.SubscribeBase;
import cn.com.lxsoft.bdasphone.utils.ConvertUtils;
import cn.com.lxsoft.bdasphone.utils.FileUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.utils.ImageUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ImageData {

    private String Id;
    private String name="";
    private Uri uri;
    private String netName;
    public  boolean bShowInList=false;
    public boolean bUpLoad=false;


    public ImageData(){
        /*
        String code="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<18;i++){
            int number=random.nextInt(62);
            sb.append(code.charAt(number));
        }
        Id=qldm.concat(sb.toString());
        */
    }

    //name┄uri┆name┄url
    public static List<ImageData> parseImageData(String res){
        List<ImageData> imageList=new ArrayList<>();
        if(res==null || res.isEmpty())
            return imageList;
        String[] tplist=res.split("[┄┆]");
        if(tplist.length>=2 && tplist.length%2==0) {
            for (int i = 0; i < tplist.length; i++) {
                ImageData image = new ImageData();
                image.setName(tplist[i]);
                i += 1;
                image.setUri(Uri.parse(tplist[i]));
                image.setNetName(tplist[i]);
                imageList.add(image);
            }
        }
        return imageList;
    }

    public Uri getImgNetUri(){
        if(!ConvertUtils.isSpace(netName))
            return Uri.parse(SystemConfig.BASE_URL+"Content/pic/"+netName);
        else
            return uri;
    }

    public static String createImageData(List<ImageData> tplist){
        if(tplist==null)
            return "";
        String res="";
        for(int i=0;i<tplist.size();i++){
            ImageData data=tplist.get(i);
            if(i!=0)
                res=res.concat("┆");
            if(data.getNetName()!=null)
                res=res.concat(data.getName()+"┄"+data.getNetName());
            else
                res=res.concat(data.getName()+"┄"+data.getUri().toString());
        }
        return res;
    }

    public static List<ImageData> createImageList(List<Uri> uris,boolean bUpLoad){
        List<ImageData> imageList=new ArrayList<>();
        if(uris!=null) {
            for (int i = 0; i < uris.size(); i++) {
                ImageData image = new ImageData();
                image.setName("");
                image.setUri(uris.get(i));
                if(bUpLoad) {
                    image.bUpLoad = true;
                }
                imageList.add(image);
            }
        }
        return imageList;
    }

    public static List<ImageData> createImageList(List<Uri> uris){
        List<ImageData> imageList=new ArrayList<>();
        if(uris!=null) {
            for (int i = 0; i < uris.size(); i++) {
                ImageData image = new ImageData();
                image.setName("");
                image.setUri(uris.get(i));
                imageList.add(image);
            }
        }
        return imageList;
    }

    public void setName(String nm){
        name=nm;
    }

    public String getName() {
        return name;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public void setNetNameFromNet(String netName) {
        String[] tplist=netName.split("┄");
        this.netName = tplist[1];
        this.name=tplist[0];
    }

    //0:初始化，1：位置校验成功，2：图片上传成功，200：错误。
    public static void uploadImageList(SubscribeBase subscribeBase, List<ImageData> imageDataList, String bridgeID, ObservableInt obState){
        List<ImageData> dataList=new ArrayList<>();
        for(int i=0;i<imageDataList.size();i++){
            if(imageDataList.get(i).bUpLoad)
                dataList.add(imageDataList.get(i));
        }
        if(dataList.size()==0){
            obState.set(2);
        }
        else {
            //for(int i=0;i<dataList.size();i++) {
                //ImageData imageData = dataList.get(i);
                Observable.fromIterable(dataList)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .flatMap(new Function<ImageData, Observable<ResponseInfo>>() {
                            @Override
                            public Observable<ResponseInfo> apply(ImageData data) throws Exception {
                                Context context = AppApplication.getInstance().getBaseContext();
                                File file = ImageUtils.compressBitmap(FileUtils.getFilePathByUri(context, data.getUri()), ImageUtils.getDiskCacheDir(context), "patrol");
                                //File file = new File(FileUtils.getFilePathByUri(getApplication().getBaseContext(), patrolData.images.get(i).getUri()));
                                return subscribeBase.uploadCommMedia(bridgeID, file);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        /*
                        .subscribe(new BridegeNetObserver<ResponseInfo>() {
                            @Override
                            public void onNext(ResponseInfo data) {
                                imageData.setNetNameFromNet(data.data);
                                imageData.bUpLoad = false;
                                for (int x = 0; x < dataList.size(); x++) {
                                    if (dataList.get(x).bUpLoad)
                                        return;
                                }
                                bUploadOK.set(true);
                            }
                            */
                            .subscribe(new BridegeNetObserver<ResponseInfo>() {
                                int i=0;
                                @Override
                                public void onNext(ResponseInfo res) {
                                    if(i<dataList.size()) {
                                        dataList.get(i).setNetNameFromNet(res.data);
                                        dataList.get(i).bUpLoad = false;
                                    }
                                    i++;
                                    if(i==dataList.size())
                                        obState.set(2);
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    super.onError(e);
                                    obState.set(200);
                                }
                        });
            }
        //}
    }

}
