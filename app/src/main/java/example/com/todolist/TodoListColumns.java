package example.com.todolist;


import android.net.Uri;
import android.provider.BaseColumns;

public class TodoListColumns implements BaseColumns {
    public static final String TABLE_NAME = "todo_list";
    public static final Uri CONTENT_URI = Uri.parse(TodoListContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String NAME = "name";
    public static final String DATE = "date";

}
