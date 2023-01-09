package com.example.cardiary.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarDiaryFuel {

    int id;
    float loadedFuel;
    BigDecimal pricePerLiter;
    BigDecimal discount;
    BigDecimal totalPrice;
    float scienceLastTimeKm;
    float totalKm;
    Date createDate;

    public CarDiaryFuel(int id, float loadedFuel, BigDecimal pricePerLiter, BigDecimal discount, BigDecimal totalPrice,
                        float scienceLastTimeKm, float totalKm, Date createDate){

        this.id = id;
        this.loadedFuel = loadedFuel;
        this.pricePerLiter = pricePerLiter;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.scienceLastTimeKm = scienceLastTimeKm;
        this.totalKm = totalKm;
        this.createDate = createDate;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLoadedFuel() {
        return loadedFuel;
    }

    public void setLoadedFuel(float loadedFuel) {
        this.loadedFuel = loadedFuel;
    }

    public BigDecimal getPricePerLiter() {
        return pricePerLiter;
    }

    public void setPricePerLiter(BigDecimal pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getScienceLastTimeKm() {
        return scienceLastTimeKm;
    }

    public void setScienceLastTimeKm(float scienceLastTimeKm) {
        this.scienceLastTimeKm = scienceLastTimeKm;
    }

    public float getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(float totalKm) {
        this.totalKm = totalKm;
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
