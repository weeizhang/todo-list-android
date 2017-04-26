package example.com.todolist;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoListDatabase extends SQLiteOpenHelper {

    private static final int VER_1_0_0 = 1000;
    private static final int CUR_DATABASE_VERSION = VER_1_0_0;
    public static final String DATABASE_FILE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = CUR_DATABASE_VERSION;

    private static final String SQL_CREATE_TABLE_TODO_LIST = "CREATE TABLE IF NOT EXISTS "
            + TodoListColumns.TABLE_NAME + " ( "
            + TodoListColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TodoListColumns.NAME + " TEXT NOT NULL, "
            + TodoListColumns.DATE + " TEXT NOT NULL "
            + " );";
    private static final String ADD_TEST_DATA = "INSERT INTO "
            + TodoListColumns.TABLE_NAME
            + " ("
            + TodoListColumns.NAME + ", "
            + TodoListColumns.DATE
            + ")"
            + "VALUES ('name1', '2017-04-10'), ('name2', '2017-04-11'), ('name3', '2017-04-11'), ('name4', '2017-04-13');";

    public static TodoListDatabase getInstance(Context context) {
        return new TodoListDatabase(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    public TodoListDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TODO_LIST);
        //TODO add test data
        db.execSQL(ADD_TEST_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
