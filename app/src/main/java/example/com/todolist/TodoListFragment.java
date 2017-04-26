package example.com.todolist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

import static example.com.todolist.R.layout.fragment_todo_list;


public class TodoListFragment extends Fragment {

    public static final String TAG = "TodoListFragment";

    @BindView(R.id.todo_list_view)
    RecyclerView todoListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_todo_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
