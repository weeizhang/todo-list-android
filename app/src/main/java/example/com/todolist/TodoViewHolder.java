package example.com.todolist;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.todo_name)
    TextView todoNameView;
    @BindView(R.id.todo_description)
    TextView todoDescription;

    public TodoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populate(Todo todo) {
        todoNameView.setText(todo.getName());
        todoDescription.setText(todo.getDate());
    }
}
