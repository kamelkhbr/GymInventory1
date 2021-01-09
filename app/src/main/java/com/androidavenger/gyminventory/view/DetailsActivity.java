package com.androidavenger.gyminventory.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavenger.gyminventory.R;
import com.androidavenger.gyminventory.model.db.data.Equipement;
import com.androidavenger.gyminventory.model.db.data.EquipementDatabaseHelper;

import static com.androidavenger.gyminventory.utils.constants.GYM_EQUIPMENT;

public class DetailsActivity extends AppCompatActivity {

    EquipementDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        databaseHelper= new EquipementDatabaseHelper(this);


        Intent intent = getIntent();
        Equipement myOrder = intent.getParcelableExtra(GYM_EQUIPMENT);
        if(myOrder != null) {
            displayDetails(myOrder);
        }
        Button purchase = findViewById(R.id.purchase_button);
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText purchaseNumber = findViewById(R.id.num_items);
                String purchaseString = purchaseNumber.getText().toString();
                if("".equals(purchaseString)) {
                    Toast.makeText(DetailsActivity.this, "Enter a valid number", Toast.LENGTH_LONG).show();
                } else {
                    int numPurchase = Integer.parseInt(purchaseNumber.getText().toString());
                    databaseHelper.purchaseEquipment(myOrder, numPurchase);
                    finish();
                }

            }
        });
    }

    private void displayDetails(Equipement myOrder) {

        TextView nameEquip = findViewById(R.id.name_equip_textview);
        String itemName = "Name: " + myOrder.getEquipName();
        nameEquip.setText(itemName);

        TextView descriptionItem = findViewById(R.id.item_descrp);
        String itemDes = myOrder.getEquipDescr();
        descriptionItem.setText(itemDes);

        TextView priceItem = findViewById(R.id.price_textview);
        String price = String.valueOf(myOrder.getEquipPrice());
        priceItem.setText(price);

        TextView numOwned = findViewById(R.id.owned_view);
        String owned = String.valueOf(myOrder.getOwned());
        numOwned.setText(owned);
    }


}