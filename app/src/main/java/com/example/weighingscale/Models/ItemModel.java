package com.example.weighingscale.Models;

public class ItemModel {
    String Name, Price, Id, Kg, Total,TotalAmount;
    public ItemModel(){}

    ItemModel(String name, String price, String id,String Kg,String Total, String TotalAmount){
        this.Name = name;
        this.Price = price;
        this.Id = id;
        this.Kg = Kg;
        this.Total = Total;
        this.TotalAmount = TotalAmount;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getKg() {
        return Kg;
    }

    public void setKg(String kg) {
        Kg = kg;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
