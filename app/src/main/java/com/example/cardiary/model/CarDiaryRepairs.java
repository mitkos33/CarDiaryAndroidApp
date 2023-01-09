package com.example.cardiary.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CarDiaryRepairs {

    public int id;
    public String nameRepair;
    public String description;
    public Date createDate;

    public CarDiaryRepairs(int id, String nameRepair, String description, Date createDate){
        this.id = id;
        this.nameRepair = nameRepair;
        this.description = description;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRepair() {
        return nameRepair;
    }

    public void setNameRepair(String nameRepair) {
        this.nameRepair = nameRepair;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getFormatDate(String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return  formatter.format(this.createDate);
    }
}
