package com.androidavenger.gyminventory.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavenger.gyminventory.R;
import com.androidavenger.gyminventory.model.db.data.Equipement;
import com.androidavenger.gyminventory.model.db.data.EquipementDatabaseHelper;
import com.androidavenger.gyminventory.view.Adapter.EquipementItemAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.androidavenger.gyminventory.model.db.data.EquipementDatabaseHelper.COLUMN_EQUIPEMENT_DESCRIPTION;
import static com.androidavenger.gyminventory.model.db.data.EquipementDatabaseHelper.COLUMN_EQUIPEMENT_NAME;
import static com.androidavenger.gyminventory.model.db.data.EquipementDatabaseHelper.COLUMN_EQUIPEMENT_PRICE;
import static com.androidavenger.gyminventory.utils.constants.DB_INITIALIZED_KEY;
import static com.androidavenger.gyminventory.utils.constants.GYM_EQUIPMENT;

public class MainActivity extends AppCompatActivity implements EquipementItemAdapter.EquipementDelegate {

    EquipementDatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    Equipement selectedEquipment = null;

    public void initEquipement (){
        Equipement treadmill = new Equipement("Incline Treadmill", "Keeps your heart rate elevated", 999.99,0 );
        Equipement weightBench = new Equipement("Weight Bench", "Great for dumbbels workout", 299.99, 0);
        Equipement legPress = new Equipement("Leg Press Machine", "Builds Strong Legs", 399.00, 0);
        Equipement rowing = new Equipement("Rowing Machine", "Steady state and interval Cardio", 195.99, 0);
        Equipement spinBike = new Equipement("Spin Bike ", "Targets lower body, and upper body", 789.99, 0);
        Equipement abCrunsh = new Equipement("ABS crunsh ", "Get your six pack faster than ever",379.99,0 );
        Equipement climbingRope = new Equipement("Climbing Rope ", "Targets absolutely every muscle in body", 49.99,0);


        ArrayList<Equipement> equipementArrayList = new ArrayList<>(7);
        equipementArrayList.add(treadmill);
        equipementArrayList.add(weightBench );
        equipementArrayList.add(legPress);
        equipementArrayList.add(rowing);
        equipementArrayList.add(spinBike);
        equipementArrayList.add(abCrunsh);
        equipementArrayList.add(climbingRope);


        databaseHelper.populateTable(equipementArrayList);
        sharedPreferences.edit().putBoolean(DB_INITIALIZED_KEY, true).apply();

    }

    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new EquipementDatabaseHelper(this);
        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean dbInitialized = sharedPreferences.getBoolean(DB_INITIALIZED_KEY, false);
        if(!dbInitialized) {
            initEquipement();
        }

        List<Equipement> equipementList = databaseHelper.getGymEquipmentList();
        ListView lvEquipment = findViewById(R.id.equip_listview);
        displayTotalSpent(equipementList);
        EquipementItemAdapter adapter = new EquipementItemAdapter(equipementList, this);
        lvEquipment.setAdapter(adapter);

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedEquipment != null) {
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra(GYM_EQUIPMENT, selectedEquipment);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Select an item.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Equipement> equipementList = databaseHelper.getGymEquipmentList();
        displayTotalSpent(equipementList);

        if(selectedEquipment != null) {
            selectedEquipment = databaseHelper.getEquipement(selectedEquipment.getEquipName());
        }
    }

    @Override
    public void selectEquipement (Equipement selectedEquipement) {

        selectedEquipment = selectedEquipement;

        TextView selectName = findViewById(R.id.select_name);
        String itemName = "Name: " + selectedEquipement.getEquipName();
        selectName.setText(itemName);

        TextView selectDesciption = findViewById(R.id.select_descr);
        String itemdesciption = "Brand: " + selectedEquipement.getEquipDescr();
        selectDesciption.setText(itemdesciption);

        TextView selectPrice = findViewById(R.id.select_price);
        String itemPrice = "Price: $" +selectedEquipement.getEquipPrice();
        selectPrice.setText(itemPrice);
    }

    private void displayTotalSpent(List<Equipement> equipmentList) {

        ArrayList<Equipement> equipmentArrayList = new ArrayList<>(equipmentList);
        double totalPrice = 0;
        for(int i = 0; i < equipmentArrayList.size(); i++) {
            int owned = equipmentArrayList.get(i).getOwned();
            double price = equipmentArrayList.get(i).getEquipPrice();
            totalPrice += ((double) owned * price);
        }

        TextView totalSpent = findViewById(R.id.total_spent);
        String totalPriceString = ("TOTAL SPENT: $" + totalPrice);
        totalSpent.setText(totalPriceString);
    }

}