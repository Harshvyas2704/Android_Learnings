package com.example.foodapplication.Helper;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.foodapplication.Domain.FoodDomain;
import com.example.foodapplication.Interface.ChangeNumberItemListener;

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

        tinyDB.putListObject("CartList", listFood);
        Toast.makeText(context, "Item added to your cart", Toast.LENGTH_LONG).show();

    }

    public ArrayList<FoodDomain> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void plusNumberFood(ArrayList<FoodDomain> listFood, int position, ChangeNumberItemListener changeNumberItemListener) {

        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CartList", listFood);
        changeNumberItemListener.changed();

    }

    public void minusNumberFood(ArrayList<FoodDomain> listFood, int position, ChangeNumberItemListener changeNumberItemListener) {

        if(listFood.get(position).getNumberInCart() == 1) {
            listFood.remove(position);
            changeNumberItemListener.changed();
        }
        else if(listFood.get(position).getNumberInCart() == 0) {
            changeNumberItemListener.changed();
        }
        else{

            listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() - 1);
        }

        tinyDB.putListObject("CartList", listFood);
        changeNumberItemListener.changed();

    }

    public Double getTotalFee() {

        ArrayList<FoodDomain> listFood = getListCart();
        double totalFee = 0;

        for(int i = 0; i < listFood.size(); i++) {

            totalFee = totalFee + (listFood.get(i).getFee() * listFood.get(i).getNumberInCart());
        }
        return totalFee;
    }
}
