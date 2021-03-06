package com.example.anandparmeetsingh.inventory;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anandparmeetsingh.inventory.data.InventoryContract;
import com.example.anandparmeetsingh.inventory.data.InventoryDbHelper;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int INVENTORY_LOADER = 0;
    int rowsDeleted;


    InventoryCursorAdapter mCursorLoader;

    private InventoryDbHelper mInventoryHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        //need to find listView
        ListView petListView = (ListView) findViewById(R.id.list);
        //find empty view and set it when only have 0 item
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        //set up adapter
        mCursorLoader = new InventoryCursorAdapter(this, null);
        petListView.setAdapter(mCursorLoader);

        //kick off the loader
        getLoaderManager().initLoader(INVENTORY_LOADER, null, this);

        //set on Item click listener
        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //create an intent to go to editor Activity
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                //form a Uri content that represent specific pet that press on
                Uri currentPet = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI, id);

                //set the Uri data field of the content
                intent.setData(currentPet);

                //start the activity
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    private void insertInventory() {

        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_INVENTORY_NAME, "Name");
        values.put(InventoryContract.InventoryEntry.COLUMN_INVENTORY_PRICE, 10);
        values.put(InventoryContract.InventoryEntry.COLUMN_INVENTORY_QUANTITY, 10);
        values.put(InventoryContract.InventoryEntry.COLUMN_INVENTORY_DETAIL, "Something");


        Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);
        Log.v("CatalogActivity", "New row ID: " + newUri);
    }

    private void deleteAllInventories() {
        rowsDeleted = getContentResolver().delete(InventoryContract.InventoryEntry.CONTENT_URI, null, null);
        if (rowsDeleted == 0) {
            Toast.makeText(this, "Delete Unsuccessful",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Entry Deleted",
                    Toast.LENGTH_SHORT).show();
        }
        getContentResolver().notifyChange(InventoryContract.InventoryEntry.CONTENT_URI, null);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertInventory();
//                displayDataInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Delete all pet
                deleteAllInventories();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //Define the projection that specifies the column that we need
        String[] projection = {
                InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.COLUMN_INVENTORY_NAME,
                InventoryContract.InventoryEntry.COLUMN_INVENTORY_DETAIL,
                InventoryContract.InventoryEntry.COLUMN_INVENTORY_PRICE,
                InventoryContract.InventoryEntry.COLUMN_INVENTORY_QUANTITY
        };

        return new CursorLoader(this, InventoryContract.InventoryEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //Update new Cursor that contain the updated data
        mCursorLoader.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //callback called when the data need to be deleted
        getContentResolver().notifyChange(InventoryContract.InventoryEntry.CONTENT_URI, null);
        mCursorLoader.swapCursor(null);
    }
}