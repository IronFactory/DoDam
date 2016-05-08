package jm.dodamdodam.dodamdodam.data;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by IronFactory on 15. 11. 29..
 */
public class DBManager extends SQLiteOpenHelper {

    private static final String TAG = "DBManger";

    public static final String TABLE_NAME = "DIARY_LIST";
    public static final String COL_CONTENT = "content";
    public static final String COL_FEEL = "feel";
    public static final String COL_DATE = "date";
    public static final String COL_WORD = "word";

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL_CONTENT + " TEXT, " + COL_FEEL + " INTEGER, " + COL_DATE + " INTEGER, " + COL_WORD + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // TODO: 15. 11. 29. 일기 DB 삽입
    public void insertDiary(DiaryModel diary) {
        String content = diary.getContent();
        int feel = diary.getFeel();
        long date = diary.getDate();
        String word = diary.getWord();

        String command = "INSERT INTO " + TABLE_NAME + " values('" + content + "', " + feel + ", " + date + ", '" + word + "');";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(command);
        db.close();
    }


    // TODO: 15. 11. 29. 일기 모두 가져오기
    public ArrayList<DiaryModel> getAllDiary() {
        String command = "SELECT * FROM " + TABLE_NAME + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(command, null);
        ArrayList<DiaryModel> diarys = new ArrayList<>();
        while (cursor.moveToNext()) {
            DiaryModel diaryModel = new DiaryModel();
            String content = cursor.getString(0);
            int feel = cursor.getInt(1);
            long date = cursor.getLong(2);
            String word = cursor.getString(3);

            diaryModel.setContent(content);
            diaryModel.setFeel(feel);
            diaryModel.setDate(date);
            diaryModel.setWord(word);
            diarys.add(diaryModel);
        }
        return diarys;
    }


    // TODO: 15. 11. 29. 다이어리 수정
    public void updateDiary(DiaryModel beforeDiary, DiaryModel afterModel) {
        String command = "UPDATE " + TABLE_NAME + " set " +
                COL_CONTENT + " = '" + afterModel.getContent() + "' " +
                COL_FEEL + " = " + afterModel.getFeel() + " " +
                COL_DATE + " = " + afterModel.getDate() + " " +
                "WHERE " + COL_CONTENT + " = '" + beforeDiary.getContent() + "' " +
                COL_DATE + " = " + beforeDiary.getDate() + " " +
                COL_FEEL + " = " + beforeDiary.getFeel() + ";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(command);
        db.close();
    }


    // TODO: 15. 11. 29. 다이어리 삭제
    public void deleteDiary(DiaryModel diaryModel) {
        String command = "DELETE FROM " + TABLE_NAME + " WHERE " +
                COL_CONTENT + " = '" + diaryModel.getContent() + "' " +
                COL_DATE + " = " + diaryModel.getDate() + " " +
                COL_FEEL + " = " + diaryModel.getFeel() + ";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(command);
        db.close();
    }
}
