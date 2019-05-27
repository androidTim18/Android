package android.projekat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;


public class CommentDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "comments.commentDb";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "comments";
    public static final String COLUMN_AD_ID = "AdId";
    public static final String COLUMN_COMMENT_ID = "commentId";
    public static final String COLUMN_COMMENT_TEXT = "commentText";
    public static final String COLUMN_COMMENT_DATE = "commentDate";


    private SQLiteDatabase commentsDb = null;

    public CommentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase commentsDb) {
        commentsDb.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_AD_ID + " TEXT, " +
                COLUMN_COMMENT_ID + " TEXT, " +
                COLUMN_COMMENT_DATE + " TEXT, " +
                COLUMN_COMMENT_TEXT + " TEXT);"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase adDb, int oldVersion, int newVersion) {

    }

    public void insert(Comment comment) {
        SQLiteDatabase commentsDb = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_AD_ID, comment.getAdId());
        values.put(COLUMN_COMMENT_ID, comment.getCommentId());
        values.put(COLUMN_COMMENT_TEXT, comment.getText());
        values.put(COLUMN_COMMENT_DATE, comment.getDate());

        commentsDb.insert(TABLE_NAME, null, values);
        close();
    }

    public Comment[] readComments(String id) {
        SQLiteDatabase commentsDb = getReadableDatabase();
        Cursor cursor = commentsDb.query(TABLE_NAME, null, COLUMN_AD_ID + "=?", new String[]{id},
                null, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }

        Comment[] comments = new Comment[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            comments[i++] = createComment(cursor);
        }

        close();
        return comments;
    }

    public void deleteComment(String index) {
        SQLiteDatabase adDb = getWritableDatabase();
        adDb.delete(TABLE_NAME, COLUMN_COMMENT_ID + "=?", new String[] {index});
        close();
    }

    private Comment createComment(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT_ID));
        String adId = cursor.getString(cursor.getColumnIndex(COLUMN_AD_ID));
        String text = cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT_TEXT));
        String date = cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT_DATE));
        return new Comment(text, date, adId, id);
    }
}
