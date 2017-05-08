package example.com.todolist;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static example.com.todolist.R.layout.fragment_todo_list;


public class TodoListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, TodoViewHolder.Callback {

    public static final String TAG = "TodoListFragment";

    @BindView(R.id.todo_list_view)
    RecyclerView todoListView;

    TodoListRecyclerAdapter todoListRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_todo_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycleView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(getContext());
        cursorLoader.setUri(TodoListColumns.CONTENT_URI);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<Todo> todoList = new ArrayList<>();
            do {
                TodoCursor todoCursor = new TodoCursor(data);
                todoList.add(new Todo(todoCursor.getId(), todoCursor.getName(), todoCursor.getDate()));
            } while (data.moveToNext());
            todoListRecyclerAdapter.setTodoList(todoList);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onTodoItemSelected(String todoId) {
        TodoDetailFragment fragment = new TodoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TodoDetailFragment.ITEM_ID, todoId);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment, TodoDetailFragment.TAG)
                .addToBackStack(TodoDetailFragment.TAG)
                .commit();
    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        todoListRecyclerAdapter = new TodoListRecyclerAdapter(null, this);
        todoListView.setLayoutManager(linearLayoutManager);
        todoListView.setAdapter(todoListRecyclerAdapter);
    }
}
