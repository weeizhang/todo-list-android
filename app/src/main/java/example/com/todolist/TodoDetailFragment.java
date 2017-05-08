package example.com.todolist;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static example.com.todolist.R.layout.fragment_todo_detail;

public class TodoDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "TodoDetailFragment";
    public static final String ITEM_ID = "item_id";
    private static final int TODO_DETAIL_LOADER = 0;

    @BindView(R.id.todo_detail_name)
    TextView todoDetailNameView;
    @BindView(R.id.todo_detail_description)
    TextView todoDetailDescriptionView;
    @BindView(R.id.todo_detail_date)
    TextView todoDetailDateView;

    private String itemId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemId = getArguments().getString(ITEM_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_todo_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(TODO_DETAIL_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == TODO_DETAIL_LOADER) {
            CursorLoader cursorLoader = new CursorLoader(getContext());
            cursorLoader.setUri(TodoDetailColumns.CONTENT_URI);
            cursorLoader.setSelection(TodoDetailColumns.TODO_LIST_ID + "=?");
            cursorLoader.setSelectionArgs(new String[]{itemId});
            cursorLoader.setProjection(new String[]{TodoDetailColumns._ID, TodoDetailColumns.TODO_LIST_ID, TodoDetailColumns.DESCRIPTION, TodoListColumns.NAME, TodoListColumns.DATE});
            return cursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            TodoCursor todoCursor = new TodoCursor(data);
            todoDetailNameView.setText(todoCursor.getName());
            todoDetailDateView.setText(todoCursor.getDate());
            todoDetailDescriptionView.setText(todoCursor.getDescription());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
