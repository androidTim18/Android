package android.projekat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

import java.util.Date;


public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ads.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "ads";
    public static final String COLUMN_AD_ID = "UserId";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_BREED = "Breed";
    public static final String COLUMN_SPECIES = "Species";
    public static final String COLUMN_BIRTHDAY = "Birthday";
    public static final String COLUMN_SEX = "Sex";
    public static final String COLUMN_LOCATION = "Location";
    public static final String COLUMN_OWNER = "Owner";
    public static final String COLUMN_INFO = "Info";
    public static final String COLUMN_PRICE = "Price";
    public static final Drawable COLUMN_PHOTO = null;
    public static final Boolean COLUMN_AVAILABLE = true;
    public static final String COLUMN_FAVORITE = "Favorite";

    private SQLiteDatabase mDb = null;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AD_ID + " TEXT, " +
                COLUMN_BREED + " TEXT, " +
                COLUMN_SPECIES + " TEXT, " +
                COLUMN_BIRTHDAY + " TEXT, " +
                COLUMN_SEX + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_OWNER + " TEXT, " +
                COLUMN_INFO + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_AVAILABLE + " BOOLEAN, " +
                COLUMN_FAVORITE + " BOOLEAN, " +
                COLUMN_PHOTO + " DRAWABLE);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Ad ad) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, ad.getName());
        values.put(COLUMN_AD_ID, ad.getAdId());
        values.put(COLUMN_BREED, ad.getBreed());
        values.put(COLUMN_SPECIES, ad.getSpecies());
        values.put(COLUMN_BIRTHDAY, ad.getBirthday());
        values.put(COLUMN_SEX, ad.getSex());
        values.put(COLUMN_LOCATION, ad.getLocation());
        values.put(COLUMN_OWNER, ad.getOwner());
        values.put(COLUMN_INFO, ad.getInfo());
        values.put(COLUMN_PRICE, ad.getPrice());
        values.put(String.valueOf(COLUMN_AVAILABLE), ad.isAvailable());
        values.put(COLUMN_FAVORITE, ad.isFavorite());
        values.put(String.valueOf(COLUMN_PHOTO), String.valueOf(ad.getPhoto()));

        db.insert(TABLE_NAME, null, values);
        close();
    }

    public Ad[] readAds() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }

        Ad[] ads = new Ad[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            ads[i++] = createAd(cursor);
        }

        close();
        return ads;
    }

    public Ad readAd(String index) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_AD_ID + "=?",
                new String[] {index}, null, null, null);
        cursor.moveToFirst();
        Ad ad = createAd(cursor);

        close();
        return ad;
    }

    public void deleteAd(String index) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_AD_ID + "=?", new String[] {index});
        close();
    }

    private Ad createAd(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        String adId = cursor.getString(cursor.getColumnIndex(COLUMN_AD_ID));
        String breed = cursor.getString(cursor.getColumnIndex(COLUMN_BREED));
        String species = cursor.getString(cursor.getColumnIndex(COLUMN_SPECIES));
        String birthday = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY));
        String sex = cursor.getString(cursor.getColumnIndex(COLUMN_SEX));
        String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
        String owner = cursor.getString(cursor.getColumnIndex(COLUMN_OWNER));
        String info = cursor.getString(cursor.getColumnIndex(COLUMN_INFO));
        String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
        Boolean available = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(String.valueOf(COLUMN_AVAILABLE))));
        Boolean favorite = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_FAVORITE)));
        Drawable photo = Drawable.createFromPath(cursor.getString(cursor.getColumnIndex(String.valueOf(COLUMN_PHOTO))));
        Date dateAdded = new Date();

        return new Ad(adId, dateAdded.toString(), species, breed, name, birthday, sex, location, owner,
                info, price, available, favorite, photo);
    }
}
