package cn.com.lxsoft.bdasphone.net;

public class ResponseInfo extends ResponseBase {
    public String state="";
    public String message="";
    public String data="";
    public String result="";

    public Boolean checkSuccess(){
        if(state.equals("success"))
            return true;
        else
            return false;
    }
}
