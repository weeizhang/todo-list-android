package example.com.todolist;


import android.net.Uri;
import android.provider.BaseColumns;

public class TodoDetailColumns implements BaseColumns {
    public static final String TABLE_NAME = "todo_detail";
    public static final Uri CONTENT_URI = Uri.parse(TodoListContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String TODO_LIST_ID = "todo_list_id";
    public static final String DESCRIPTION = "description";
}
