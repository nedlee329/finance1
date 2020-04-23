package com.nedlee.finance.po;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_product")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date jyTime;
    private String jyType;
    private String jrType;
    private Integer zqCode;
    private String zqname;
    private String jyprice;
    private String amount;
    private String money;

    @ManyToOne
    private Customer customer;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getJyTime() {
        return jyTime;
    }

    public void setJyTime(Date jyTime) {
        this.jyTime = jyTime;
    }

    public String getJyType() {
        return jyType;
    }

    public void setJyType(String jyType) {
        this.jyType = jyType;
    }

    public String getJrType() {
        return jrType;
    }

    public void setJrType(String jrType) {
        this.jrType = jrType;
    }

    public Integer getZqCode() {
        return zqCode;
    }

    public void setZqCode(Integer zqCode) {
        this.zqCode = zqCode;
    }

    public String getZqname() {
        return zqname;
    }

    public void setZqname(String zqname) {
        this.zqname = zqname;
    }

    public String getJyprice() {
        return jyprice;
    }

    public void setJyprice(String jyprice) {
        this.jyprice = jyprice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", jyTime=" + jyTime +
                ", jyType='" + jyType + '\'' +
                ", jrType='" + jrType + '\'' +
                ", zqCode=" + zqCode +
                ", zqname='" + zqname + '\'' +
                ", jyprice='" + jyprice + '\'' +
                ", amount='" + amount + '\'' +
                ", money='" + money + '\'' +
                ", customer=" + customer +
                '}';
    }
}
