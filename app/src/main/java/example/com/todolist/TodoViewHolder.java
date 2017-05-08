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

    private View itemView;
    private Callback callback;

    public TodoViewHolder(View itemView, Callback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
        this.callback = callback;
    }

    public void populate(final Todo todo) {
        todoNameView.setText(todo.getName());
        todoDescription.setText(todo.getDate());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onTodoItemSelected(todo.getId());
            }
        });
    }

    public interface Callback{
        void onTodoItemSelected(String todoId);
    }
}
