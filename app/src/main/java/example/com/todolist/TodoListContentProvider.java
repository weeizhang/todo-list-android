package example.com.todolist;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Arrays;

public class TodoListContentProvider extends ContentProvider {
    private static final String TAG = "TodoListContentProvider";

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;
    private static final int URI_TYPE_TODO_LIST = 0;
    private static final int URI_TYPE_TODO_DETAIL = 1;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, TodoListColumns.TABLE_NAME, URI_TYPE_TODO_LIST);
        URI_MATCHER.addURI(AUTHORITY, TodoDetailColumns.TABLE_NAME, URI_TYPE_TODO_DETAIL);
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
        Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder);
        QueryParams queryParams = getQueryParams(uri, selection);
        projection = ensureIdIsFullyQualified(projection, queryParams.table);
        Cursor cursor = todoListDatabase.getReadableDatabase().query(queryParams.tablesWithJoins, projection, queryParams.selection, selectionArgs, null, null, sortOrder);
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
            case URI_TYPE_TODO_DETAIL:
                return TYPE_CURSOR_ITEM + TodoDetailColumns.TABLE_NAME;
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
            case URI_TYPE_TODO_DETAIL:
                return TodoDetailColumns.TABLE_NAME;
        }
        return null;
    }

    private QueryParams getQueryParams(Uri uri, String selection) {
        QueryParams res = new QueryParams();
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_TODO_LIST:
                res.table = TodoListColumns.TABLE_NAME;
                res.tablesWithJoins = TodoListColumns.TABLE_NAME;
                break;
            case URI_TYPE_TODO_DETAIL:
                res.table = TodoDetailColumns.TABLE_NAME;
                res.tablesWithJoins = TodoDetailColumns.TABLE_NAME;
                res.tablesWithJoins += " JOIN " + TodoListColumns.TABLE_NAME + " ON " + TodoDetailColumns.TABLE_NAME + "." + TodoDetailColumns.TODO_LIST_ID + "=" + TodoListColumns.TABLE_NAME + "." + TodoListColumns._ID;
                break;
        }
        res.selection = selection;
        return res;
    }

    private static class QueryParams {
        public String table;
        public String tablesWithJoins;
        public String selection;
    }

    private String[] ensureIdIsFullyQualified(String[] projection, String tableName) {
        if (projection == null) return null;
        String[] res = new String[projection.length];
        for (int i = 0; i < projection.length; i++) {
            if (projection[i].equals(BaseColumns._ID)) {
                res[i] = tableName + "." + BaseColumns._ID + " AS " + BaseColumns._ID;
            } else {
                res[i] = projection[i];
            }
        }
        return res;
    }
}
