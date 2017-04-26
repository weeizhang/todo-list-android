package example.com.todolist;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TodoListContentProvider extends ContentProvider {

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;
    private static final int URI_TYPE_TODO_LIST = 0;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, TodoListColumns.TABLE_NAME, URI_TYPE_TODO_LIST);
    }

    private TodoListDatabase todoListDatabase;
    private ContentResolver contentResolver;

    @Override
    public boolean onCreate() {
        todoListDatabase = TodoListDatabase.getInstance(getContext());
        todoListDatabase.getWritableDatabase();
        contentResolver = getContext().getContentResolver();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = todoListDatabase.getReadableDatabase().query(getTable(uri), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(contentResolver, uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_TODO_LIST:
                return TYPE_CURSOR_DIR + TodoListColumns.TABLE_NAME;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private String getTable(Uri uri) {
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_TODO_LIST:
                return TodoListColumns.TABLE_NAME;
        }
        return null;
    }
}
