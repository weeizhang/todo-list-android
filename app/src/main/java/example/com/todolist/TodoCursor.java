package example.com.todolist;


import android.database.Cursor;
import android.database.CursorWrapper;

public class TodoCursor extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TodoCursor(Cursor cursor) {
        super(cursor);
    }

    public String getId() {
        Integer index = getColumnIndex(TodoListColumns._ID);
        return getString(index);
    }

    public String getDate() {
        Integer index = getColumnIndex(TodoListColumns.DATE);
        return getString(index);
    }

    public String getName() {
        Integer index = getColumnIndex(TodoListColumns.NAME);
        return getString(index);
    }
}
