package com.androidavenger.gyminventory.model.db.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipement implements Parcelable {

    String equipName;
    double equipPrice;
    String equipDescr;
    int owned;


    public Equipement(String equipName,String equipDescr, double equipPrice, int owned) {
        this.equipName = equipName;
        this.equipPrice = equipPrice;
        this.equipDescr = equipDescr;
        this.owned = owned;
    }


    protected Equipement(Parcel in) {
        equipName = in.readString();
        equipPrice = in.readDouble();
        equipDescr = in.readString();
        owned = in.readInt();
    }

    public static final Creator<Equipement> CREATOR = new Creator<Equipement>() {
        @Override
        public Equipement createFromParcel(Parcel in) {
            return new Equipement(in);
        }

        @Override
        public Equipement[] newArray(int size) {
            return new Equipement[size];
        }
    };

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public double getEquipPrice() {
        return equipPrice;
    }

    public void setEquipPrice(double equipPrice) {
        this.equipPrice = equipPrice;
    }
    public String getEquipDescr() {
        return equipDescr;
    }

    public void setEquipDescr(String equipPrice) {
        this.equipDescr= equipDescr;
    }

    public int getOwned() {
        return owned;
    }

    public void setOwned(int owned) {
        this.owned = owned;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(equipName);
        dest.writeDouble(equipPrice);
        dest.writeString(equipDescr);
        dest.writeInt(owned);
    }
}
