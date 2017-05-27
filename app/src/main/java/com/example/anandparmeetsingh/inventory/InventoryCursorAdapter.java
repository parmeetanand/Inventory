package com.example.anandparmeetsingh.inventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
    public void bindView(View view, final Context context, final Cursor cursor) {

        /* Find fields to populate in inflated template */
        TextView name = (TextView) view.findViewById(R.id.productName);
        TextView price = (TextView) view.findViewById(R.id.price);
        final TextView quantities = (TextView) view.findViewById(R.id.quantity);

        int idIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry._ID);
        int nameIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_INVENTORY_NAME);
        int priceIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_INVENTORY_PRICE);
        int quantityIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_INVENTORY_QUANTITY);

        final int rowId = cursor.getInt(idIndex);
        String inventoryName = cursor.getString(nameIndex);
        String inventoryPrice = cursor.getString(priceIndex);
        String inventoryQuantity = cursor.getString(quantityIndex);

        name.setText(inventoryName);
        price.setText(inventoryPrice);
        quantities.setText(inventoryQuantity);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Editor activity
                Intent intent = new Intent(context, EditorActivity.class);

                Uri currentInventoryUri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI,
                        rowId);

                intent.setData(currentInventoryUri);

                context.startActivity(intent);
            }
        });

        Button sellButton = (Button) view.findViewById(R.id.sell);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int q = Integer.parseInt(quantities.getText().toString());

                if (q == 0) {
                    Toast.makeText(context, "No Stock",
                            Toast.LENGTH_SHORT).show();
                } else if (q > 0) {
                    q = q - 1;

                    String quantityString = Integer.toString(q);

                    ContentValues values = new ContentValues();
                    values.put(InventoryContract.InventoryEntry.COLUMN_INVENTORY_QUANTITY, quantityString);

                    Uri currentInventoryUri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI, rowId);

                    int rowsAffected = context.getContentResolver().update(currentInventoryUri, values,
                            null, null);

                    if (rowsAffected != 0) {
                        quantities.setText(quantityString);

                    } else {
                        Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Button addButton = (Button) view.findViewById(R.id.add_product);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int q = Integer.parseInt(quantities.getText().toString());

                if (q == 0) {
                    Toast.makeText(context, "No Stock",
                            Toast.LENGTH_SHORT).show();
                } else if (q > 0) {
                    q = q + 1;

                    String quantityString = Integer.toString(q);

                    ContentValues values = new ContentValues();
                    values.put(InventoryContract.InventoryEntry.COLUMN_INVENTORY_QUANTITY, quantityString);

                    Uri currentInventoryUri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI, rowId);

                    int rowsAffected = context.getContentResolver().update(currentInventoryUri, values,
                            null, null);

                    if (rowsAffected != 0) {
                        quantities.setText(quantityString);

                    } else {
                        Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}