package android.projekat;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.Date;


public class AdDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ads.adDb";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "ads";
    public static final String COLUMN_AD_ID = "AdId";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_BREED = "Breed";
    public static final String COLUMN_SPECIES = "Species";
    public static final String COLUMN_BIRTHDAY = "Birthday";
    public static final String COLUMN_SEX = "Sex";
    public static final String COLUMN_LOCATION = "Location";
    public static final String COLUMN_OWNER = "Owner";
    public static final String COLUMN_INFO = "Info";
    public static final String COLUMN_PRICE = "Price";
    public static final String COLUMN_PHOTO = "Photo";
    public static final String COLUMN_AVAILABLE = "true";
    public static final String COLUMN_FAVORITE = "false";
    public static final String COLUMN_DATE_ADDED = "dateAdded";


    private SQLiteDatabase adDb = null;

    public AdDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase adDb) {
        adDb.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
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
                COLUMN_AVAILABLE + " INTEGER, " +
                COLUMN_FAVORITE + " INTEGER, " +
                COLUMN_DATE_ADDED + " TEXT, " +
                COLUMN_PHOTO + " BLOB);"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase adDb, int oldVersion, int newVersion) {

    }

    public void insert(Ad ad) {
        SQLiteDatabase adDb = getWritableDatabase();

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
        values.put(COLUMN_AVAILABLE, 1);
        values.put(COLUMN_FAVORITE, 0);
        values.put(COLUMN_DATE_ADDED, ad.getDateAdded());

        Drawable d = ad.photo;
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte [] imageInBytes = stream.toByteArray();
        values.put(COLUMN_PHOTO, imageInBytes);

        adDb.insert(TABLE_NAME, null, values);
        close();
    }

    public Ad[] readAds() {
        SQLiteDatabase adDb = getReadableDatabase();
        Cursor cursor = adDb.query(TABLE_NAME, null, null, null,
                null, null, null, null);

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
    public Ad[] readFavorites() {
        SQLiteDatabase adDb = getReadableDatabase();
        Integer index =1;
        Cursor cursor = adDb.query(TABLE_NAME, null, COLUMN_FAVORITE + "=?",
                new String[]{index.toString()}, null, null, null, null);

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

    public Ad[] searchBreed(String breed) {
        SQLiteDatabase adDb = getReadableDatabase();
        Cursor cursor = adDb.query(TABLE_NAME, null, COLUMN_BREED + "=?",
                new String[] {breed}, null, null, null, null);

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

    public Ad[] searchSpecies(String species) {
        SQLiteDatabase adDb = getReadableDatabase();
        Cursor cursor = adDb.query(TABLE_NAME, null, COLUMN_SPECIES + "=?",
                new String[] {species}, null, null, null, null);

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
        SQLiteDatabase adDb = getReadableDatabase();
        Cursor cursor = adDb.query(TABLE_NAME, null, COLUMN_AD_ID + "=?",
                new String[] {index}, null, null, null);
        cursor.moveToFirst();
        Ad ad = createAd(cursor);

        close();

        return ad;
    }

    public void deleteAd(String index) {
        SQLiteDatabase adDb = getWritableDatabase();
        adDb.delete(TABLE_NAME, COLUMN_AD_ID + "=?", new String[] {index});
        close();
    }

    public void makeAdFavorite(Ad ad){
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
        values.put(COLUMN_AVAILABLE, ad.isAvailable());
        values.put(COLUMN_FAVORITE, 1);
        values.put(COLUMN_DATE_ADDED, ad.getDateAdded());

        Drawable d = ad.photo;
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte [] imageInBytes = stream.toByteArray();
        values.put(COLUMN_PHOTO, imageInBytes);
        db.update(TABLE_NAME,values, "adId=?", new String[]{ad.getAdId()} );

    }
    public void removeAdFavorite(Ad ad){
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
        values.put(COLUMN_AVAILABLE, ad.isAvailable());
        values.put(COLUMN_FAVORITE, 0);
        values.put(COLUMN_DATE_ADDED, ad.getDateAdded());

        Drawable d = ad.photo;
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte [] imageInBytes = stream.toByteArray();
        values.put(COLUMN_PHOTO, imageInBytes);
        db.update(TABLE_NAME,values, "adId=?", new String[]{ad.getAdId()} );

    }
    public void makeAdUnavailable(Ad ad){
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
        values.put(COLUMN_AVAILABLE, 0);
        values.put(COLUMN_FAVORITE, ad.isAvailable());
        values.put(COLUMN_DATE_ADDED, ad.getDateAdded());

        Drawable d = ad.photo;
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte [] imageInBytes = stream.toByteArray();
        values.put(COLUMN_PHOTO, imageInBytes);
        db.update(TABLE_NAME,values, "adId=?", new String[]{ad.getAdId()} );

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
        String dateAdded = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_ADDED));
        Integer available = cursor.getInt(cursor.getColumnIndex(COLUMN_AVAILABLE));

        Integer favorite = cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITE));
        byte[] photo = cursor.getBlob(cursor.getColumnIndex(COLUMN_PHOTO));

        return new Ad(species, breed, name, birthday, sex, location, owner,
                info, price, available, favorite, photo, adId, dateAdded);
    }
}
