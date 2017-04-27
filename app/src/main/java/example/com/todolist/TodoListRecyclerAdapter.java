package example.com.todolist;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TodoListRecyclerAdapter extends RecyclerView.Adapter<TodoViewHolder>{

    private List<Todo> todoList;

    public TodoListRecyclerAdapter(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row, null);
        TodoViewHolder viewHolder = new TodoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.populate(todo);
    }

    @Override
    public int getItemCount() {
        return todoList == null ? 0 : todoList.size();
    }
}
