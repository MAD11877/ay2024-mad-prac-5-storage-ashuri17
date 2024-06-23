package sg.edu.np.mad.madpractical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "users.db";
    private static final String USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "description";
    private static final String COLUMN_FOLLOWED = "followed";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Database operations","Creating USERS Table");
        try{
            //Generate 20 Random Users
            String CREATE_USERS_TABLE = "CREATE TABLE " + USERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
                    + COLUMN_DESC + " TEXT, " + COLUMN_FOLLOWED + " BOOLEAN " + ")";
            db.execSQL(CREATE_USERS_TABLE);
            for (int i = 0; i < 20; i++) {
                int name = new Random().nextInt(999999999);
                int description = new Random().nextInt(999999999);
                boolean followed = new Random().nextBoolean();
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME,"Name" + name);
                values.put(COLUMN_DESC,"Description"+ description);
                values.put(COLUMN_FOLLOWED, followed);
                db.insert(USERS, null, values);
            }
            Log.i("Database Operations", "Table created successfully");
        }
        catch(SQLiteException e){
            Log.i("Database Operations", "Error creating table", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS );
        onCreate(db);
    }

    public ArrayList<User> getUsers() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM " + USERS;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt((int) cursor.getColumnIndex("id"));
            String name = cursor.getString((int) cursor.getColumnIndex("name"));
            String desc = cursor.getString((int) cursor.getColumnIndex("description"));
            boolean followed = cursor.getInt((int) cursor.getColumnIndex("followed")) == 1;

            User user = new User(name, desc, id, followed);
            userList.add(user);
        }
        cursor.close();
        // db.close();
        return userList;
    }
    public void updateUser(int userID,boolean followedState) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("followed", followedState);
        db.update("users", values, "id = ?", new String[]{String.valueOf(userID)});
        db.close();
    }
    @Override
    public void close() {
        Log.i("Database Operations", "Database is closed.");
        super.close();
    }
}
