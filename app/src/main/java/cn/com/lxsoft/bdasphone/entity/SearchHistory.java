package cn.com.lxsoft.bdasphone.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity


public class SearchHistory {
    @Id
    public String searchData;

    private String name;

    private Long time;

    @Generated(hash = 480298403)
    public SearchHistory(String searchData, String name, Long time) {
        this.searchData = searchData;
        this.name = name;
        this.time = time;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public String getSearchData() {
        return this.searchData;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

}
