package app.com.googlelogindemo;

/**
 * Created by technicise on 31/7/14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper
{
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "myCuratioChartManager";

    // Chart Details table name
    private static final String TABLE_INDIVO_DEMOGRAPHICS = "indivo_demographics";

    //indivo_demographics Table Columns names
    private static final String KEY_NAME = "name_middle";
    private static final String KEY_FACT_PTR_ID = "fact_ptr_id";
    private static final String KEY_PROVIDER_IMAGE = "provider_name";


    public Context context;

    public DBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE_INDIVO_DEMOGRAPHICS = "CREATE TABLE " + TABLE_INDIVO_DEMOGRAPHICS + "("
                + KEY_FACT_PTR_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT, "
                + KEY_PROVIDER_IMAGE + " BLOB )";
        db.execSQL(CREATE_TABLE_INDIVO_DEMOGRAPHICS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDIVO_DEMOGRAPHICS);
        // Create tables again
        onCreate(db);
    }

    // updateIndivoProviderDetails
    public void updateValue(Contact contact)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact._name);
        values.put(KEY_PROVIDER_IMAGE, contact._image);

        // Inserting Row
        db.insert(TABLE_INDIVO_DEMOGRAPHICS, null, values);
        db.close();
    }

    // Getting All Contacts
    public List<Contact> getAllContacts()
    {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM indivo_demographics";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst())
        {
            do
            {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setImage(cursor.getBlob(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return contactList;
    }
}