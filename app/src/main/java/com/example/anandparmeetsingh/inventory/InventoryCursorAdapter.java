package com.example.anandparmeetsingh.inventory;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.anandparmeetsingh.inventory.data.InventoryContract;

public class InventoryCursorAdapter extends CursorAdapter {

    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //first need to find list item
        TextView name = (TextView) view.findViewById(R.id.productName);
        TextView summary = (TextView) view.findViewById(R.id.price);
        TextView quantity = (TextView) view.findViewById(R.id.quantity);

        //use the Cursor to find column in the table that needed
        int nameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_INVENTORY_NAME);
        int summaryColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_INVENTORY_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_INVENTORY_QUANTITY);

        String nameCursor = cursor.getString(nameColumnIndex);
        String summaryCursor = cursor.getString(summaryColumnIndex);


        name.setText(nameCursor);
        summary.setText(summaryCursor);
        quantity.setText(quantityColumnIndex);

    }
}