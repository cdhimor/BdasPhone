package cn.com.lxsoft.bdasphone.entity;

import java.util.Date;

import cn.com.lxsoft.bdasphone.utils.ConvertUtils;

public class Engineer {
    String name;
    Boolean sex;
    Date birthday;
    String position;
    String duty;
    String DepartmentID;
    Date workTime;
    Double score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex?"男":"女";
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public void setSex(String sex) {
        //this.sex = sex;
    }

    public String getBirthday() {
        return ConvertUtils.getDateName(birthday);
    }

    public void setBirthday(String birthday) {
        //this.birthday = birthday;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }

    public String getWorkTime() {
        return ConvertUtils.getDateName(workTime);
    }

    public void setWorkTime(String workTime) {
        //this.workTime = workTime;
    }

    public String getScore() {
        return score.toString();
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setScore(String score) {
        //this.score = score;
    }
}
