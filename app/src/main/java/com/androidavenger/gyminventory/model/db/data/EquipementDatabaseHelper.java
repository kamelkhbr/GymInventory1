package com.androidavenger.gyminventory.model.db.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EquipementDatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "gym_inventory.db";
    public static int DATABASE_VERSION = 1;

    public static final String EQUIPEMENT_TABLE_NAME = "GYM_INVENTORY";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_EQUIPEMENT_NAME = "equipement_name";
    public static final String COLUMN_EQUIPEMENT_DESCRIPTION = "equipement_description";
    public static final String COLUMN_EQUIPEMENT_PRICE = "equipement_price";
    public static final String COLUMN_EQUIPEMENT_OWNED = "equipment_number_owned";



    public EquipementDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + EQUIPEMENT_TABLE_NAME
                + " ("+ COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EQUIPEMENT_NAME + " TEXT, "
                + COLUMN_EQUIPEMENT_PRICE + " TEXT, "
                + COLUMN_EQUIPEMENT_DESCRIPTION + " TEXT, "
                + COLUMN_EQUIPEMENT_OWNED + " INTEGER)";


        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String update = "DROP TABLE IF EXISTS "+ EQUIPEMENT_TABLE_NAME;
        db.execSQL(update);
        DATABASE_VERSION = newVersion;
        onCreate(db);

    }

    public List<Equipement> getGymEquipmentList() {

        List<Equipement> gymEquipmentList = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + EQUIPEMENT_TABLE_NAME, null);

        cursor.moveToPosition(-1);
        while(cursor.moveToNext()) {

            String equipmentName = cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPEMENT_NAME));
            double equipmentPrice = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPEMENT_PRICE)));
            String equipmentDescr = cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPEMENT_DESCRIPTION));
            int equipmentOwned = cursor.getInt(cursor.getColumnIndex(COLUMN_EQUIPEMENT_OWNED));

            Equipement newEquipment = new Equipement(
                    equipmentName,
                    equipmentDescr,
                    equipmentPrice,
                    equipmentOwned);
            newEquipment.setOwned(equipmentOwned);
            gymEquipmentList.add(newEquipment);
        }

        cursor.close();

        return gymEquipmentList;
    }
    public void purchaseEquipment(Equipement Equipement, int purchased) {

        SQLiteDatabase db = getReadableDatabase();
        int newTotal = purchased + Equipement.getOwned();

        String UPDATE_QUERY = "UPDATE " + EQUIPEMENT_TABLE_NAME  + " SET "
                + COLUMN_EQUIPEMENT_OWNED + "=" + newTotal + " WHERE equipement_name='" + Equipement.getEquipName() + "'";
        db.execSQL(UPDATE_QUERY);
    }

    public Equipement getEquipement(String equipementNameString) {

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + EQUIPEMENT_TABLE_NAME + " WHERE equipement_name='" + equipementNameString + "'", null);
        cursor.moveToFirst();
        String equipementName = cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPEMENT_NAME));
        String equipementDescription = cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPEMENT_DESCRIPTION));
        double equipementPrice = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_EQUIPEMENT_PRICE)));
        int equipementOwned = cursor.getInt(cursor.getColumnIndex(COLUMN_EQUIPEMENT_OWNED));

        Equipement newEquipment = new Equipement(
                equipementName,
                equipementDescription,
                equipementPrice,
                equipementOwned);
        newEquipment.setOwned(equipementOwned);

        return newEquipment;
    }

    public void populateTable(ArrayList<Equipement> equipmentArrayList) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues equipementContentValues = new ContentValues();
        for(int i = 0; i < equipmentArrayList.size(); i++) {
            equipementContentValues.put(EquipementDatabaseHelper.COLUMN_EQUIPEMENT_NAME, equipmentArrayList.get(i).getEquipName());
            equipementContentValues.put(EquipementDatabaseHelper.COLUMN_EQUIPEMENT_DESCRIPTION, equipmentArrayList.get(i).getEquipDescr());
            equipementContentValues.put(EquipementDatabaseHelper.COLUMN_EQUIPEMENT_PRICE, equipmentArrayList.get(i).getEquipPrice() + "");
            equipementContentValues.put(EquipementDatabaseHelper.COLUMN_EQUIPEMENT_OWNED, 0);

            db.insert(EQUIPEMENT_TABLE_NAME, null, equipementContentValues);
            equipementContentValues.clear();
        }
    }
}
