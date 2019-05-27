package android.projekat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UserDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users.userDb";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_RATED = "rated";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_FULL_NAME = "fullName";


    private SQLiteDatabase userDb = null;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase usersDb) {
        usersDb.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_USER_ID + " TEXT, " +
                COLUMN_RATING + " INTEGER, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_FULL_NAME + " TEXT, " +
                COLUMN_RATED + " BLOB);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase usersDb, int oldVersion, int newVersion) {

    }

    public void insert(User user) {
        SQLiteDatabase userDb = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, user.getUserId());
        values.put(COLUMN_RATING, user.getRating());
        values.put(COLUMN_RATING, user.getRated());
        values.put(COLUMN_USERNAME, user.getUserName());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_FULL_NAME, user.getFullName());


        userDb.insert(TABLE_NAME, null, values);
        close();
    }
    public User[] readRatings() {
        SQLiteDatabase usersDb = getReadableDatabase();
        Cursor cursor = usersDb.query(TABLE_NAME, null, COLUMN_RATED + "=?", new String[]{"true"},
                null, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }

        User[] users = new User[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            users[i++] = createUser(cursor);
        }

        close();
        return users;
    }


    public void rate(User user, float d){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, user.getUserId());
        values.put(COLUMN_RATING, d);
        values.put(COLUMN_RATED, "true");
        values.put(COLUMN_USERNAME, user.getUserName());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_FULL_NAME, user.getFullName());

        db.update(TABLE_NAME,values, "userId=?", new String[]{user.getUserId()} );

    }
    private User createUser(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
        Float rating = cursor.getFloat(cursor.getColumnIndex(COLUMN_RATING));
        String rated = cursor.getString(cursor.getColumnIndex(COLUMN_RATED));
        String userName = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
        String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
        String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
        String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
        String fullName = cursor.getString(cursor.getColumnIndex(COLUMN_FULL_NAME));
        return new User(id, rating, rated, firstName, lastName, userName, email);
    }

    public User readUserByEmail(String index) {
        SQLiteDatabase userDb = getReadableDatabase();
        Cursor cursor = userDb.query(TABLE_NAME, null, COLUMN_EMAIL + "=?",
                new String[] {index}, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }

        cursor.moveToFirst();
        User user = createUser(cursor);

        close();

        return user;
    }

    public User readUserById(String index) {
        SQLiteDatabase userDb = getReadableDatabase();
        Cursor cursor = userDb.query(TABLE_NAME, null, COLUMN_USER_ID + "=?",
                new String[] {index}, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }
        cursor.moveToFirst();
        User user = createUser(cursor);

        close();

        return user;
    }
    public User isUserNameUnique(String index) {
        SQLiteDatabase userDb = getReadableDatabase();
        Cursor cursor = userDb.query(TABLE_NAME, null, COLUMN_USERNAME + "=?",
                new String[] {index}, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }

        cursor.moveToFirst();
        User user = createUser(cursor);

        close();

        return user;
    }
    public Boolean isAdmin(String index) {
        SQLiteDatabase userDb = getReadableDatabase();
        Cursor cursor = userDb.query(TABLE_NAME, null, COLUMN_USER_ID + "=?",
                new String[] {index}, null, null, null);

        if (cursor.getCount() <= 0) {
            return false;
        }

        cursor.moveToFirst();
        User user = createUser(cursor);
        close();

        if (user.email == "@admin"){
            return true;
        }
        else {
            return false;
        }
    }
}
