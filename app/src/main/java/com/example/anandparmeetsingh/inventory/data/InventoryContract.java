package com.example.anandparmeetsingh.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ParmeetSingh on 5/25/2017.
 */

public class InventoryContract {


    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryApp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_INVENTORY = "inventory";

    private InventoryContract() {
    }

    public static final class InventoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        public final static String TABLE_NAME = "Inventory";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_INVENTORY_NAME = "name";

        public final static String COLUMN_INVENTORY_DETAIL = "detail";

        public final static String COLUMN_INVENTORY_PRICE = "price";

        public final static String COLUMN_INVENTORY_QUANTITY = "quantity";

        public static final String COLUMN_IMAGE = "image";

    }

}


