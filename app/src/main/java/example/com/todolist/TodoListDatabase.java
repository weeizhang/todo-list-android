package example.com.todolist;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoListDatabase extends SQLiteOpenHelper {

    private static final int VER_1_0_0 = 1000;
    private static final int VER_1_0_1 = 1001;
    private static final int CUR_DATABASE_VERSION = VER_1_0_1;
    public static final String DATABASE_FILE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = CUR_DATABASE_VERSION;

    private static final String SQL_CREATE_TABLE_TODO_LIST = "CREATE TABLE IF NOT EXISTS "
            + TodoListColumns.TABLE_NAME + " ( "
            + TodoListColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TodoListColumns.NAME + " TEXT NOT NULL, "
            + TodoListColumns.DATE + " TEXT NOT NULL "
            + " );";
    private static final String SQL_CREATE_TABLE_TODO_DETAIL = "CREATE TABLE IF NOT EXISTS "
            + TodoDetailColumns.TABLE_NAME + " ( "
            + TodoDetailColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TodoDetailColumns.TODO_LIST_ID + " TEXT NOT NULL, "
            + TodoDetailColumns.DESCRIPTION + " TEXT NOT NULL "
            + " );";
    private static final String ADD_LIST_TEST_DATA = "INSERT INTO "
            + TodoListColumns.TABLE_NAME
            + " ("
            + TodoListColumns.NAME + ", "
            + TodoListColumns.DATE
            + ")"
            + "VALUES ('name1', '2017-04-10'), ('name2', '2017-04-11'), ('name3', '2017-04-11'), ('name4', '2017-04-13');";

    private static final String ADD_DETAIL_TEST_DATA = "INSERT INTO "
            + TodoDetailColumns.TABLE_NAME
            + " ("
            + TodoDetailColumns.TODO_LIST_ID + ", "
            + TodoDetailColumns.DESCRIPTION
            + ")"
            + "VALUES ('1', 'description1'), ('2', 'description2'), ('3', 'description3'), ('4', 'description4');";

    public static TodoListDatabase getInstance(Context context) {
        return new TodoListDatabase(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    public TodoListDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TODO_LIST);
        db.execSQL(SQL_CREATE_TABLE_TODO_DETAIL);
        //TODO add test data
        db.execSQL(ADD_LIST_TEST_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int version = oldVersion;
        if (version < VER_1_0_1) {
            doMigration_1_0_1(db);
            version = VER_1_0_1;
        }
        if (version != CUR_DATABASE_VERSION) {
            recreateDatabase(db);
        }
    }

    private void doMigration_1_0_1(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TODO_DETAIL);
        //TODO add test data
        db.execSQL(ADD_DETAIL_TEST_DATA);
    }

    private void recreateDatabase(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TodoListColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TodoDetailColumns.TABLE_NAME);
        onCreate(db);
    }
}
