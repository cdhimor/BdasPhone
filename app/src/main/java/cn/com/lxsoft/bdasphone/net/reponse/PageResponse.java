package cn.com.lxsoft.bdasphone.net.reponse;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data

public class PageResponse {
    public List<Map> dataList;
    //public List<EntityParser> columnInfo;
    public String info;
    public long totalNum;
    public int curPage;

}
