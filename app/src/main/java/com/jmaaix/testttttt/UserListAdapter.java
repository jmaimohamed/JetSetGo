package com.jmaaix.testttttt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jmaaix.testttttt.entities.User;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private List<User> userList; // Change the list type to User
    private OnUserClickListener listener;

    public UserListAdapter(List<User> userList, OnUserClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView usernameTextView;
        private ImageView deleteButton;
        UserViewHolder(View itemView) {
            super(itemView);

            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            deleteButton = itemView.findViewById(R.id.Delete); // Initialize the delete button

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    User user = userList.get(position);
                    listener.onUserClick(user);
                }
            });
            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    User user = userList.get(position);
                    listener.onDeleteClick(user);
                }
            });
        }

        void bind(User user) {
            usernameTextView.setText(user.getUsername());
        }
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
        void onDeleteClick(User user);
    }
}
