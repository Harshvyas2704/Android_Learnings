package com.example.foodapplication.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.foodapplication.Domain.FoodDomain;

import java.util.ArrayList;

public class ManagementCart {

    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(FoodDomain item) {

        ArrayList<FoodDomain> listFood = tinyDB.getListObject("CartList");
        boolean existAlready = false;
        int n = 0;
        for(int i = 0; i < listFood.size(); i++) {

            if(listFood.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }

        }

        if(existAlready) listFood.get(n).setNumberInCart(item.getNumberInCart());
        else listFood.add(item);

        tinyDB.putListObject("CardList", listFood);
        Toast.makeText(context, "Item added to your cart", Toast.LENGTH_LONG).show();

    }

}
