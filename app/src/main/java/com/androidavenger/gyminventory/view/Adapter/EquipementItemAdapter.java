package com.androidavenger.gyminventory.view.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidavenger.gyminventory.R;
import com.androidavenger.gyminventory.model.db.data.Equipement;

import java.util.List;

public class EquipementItemAdapter extends BaseAdapter {

    private List<Equipement> availableEquipements;
    private EquipementDelegate equipementDelegate;

    public interface EquipementDelegate {
        void selectEquipement(Equipement selectedEquipement);
    }

    public EquipementItemAdapter(List<Equipement> availableEquipements, EquipementDelegate equipementDelegate) {
        this.availableEquipements = availableEquipements;
        this.equipementDelegate = equipementDelegate;
    }


    @Override
    public int getCount() {
        return availableEquipements.size();
    }

    @Override
    public Object getItem(int position) {
        return availableEquipements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Equipement item = availableEquipements.get(position);

        View mainView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gym_item_layout,parent, false);

        mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equipementDelegate.selectEquipement(item);
            }
        });
        TextView equipementName = mainView.findViewById(R.id.equip_name);
        TextView equipementPrice = mainView.findViewById(R.id.equip_price_textview);

        equipementName.setText(item.getEquipName());
        equipementPrice.setText("$" + item.getEquipPrice());

        return mainView;


    }
}



