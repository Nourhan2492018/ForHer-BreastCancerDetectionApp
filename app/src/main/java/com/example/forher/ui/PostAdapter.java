package com.example.forher.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forher.R;
import com.example.forher.data.PostItem;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.postViewHolder> {
    ArrayList<PostItem> getPosts;

    public PostAdapter(ArrayList<PostItem> getPosts) {
        this.getPosts = getPosts;
    }

    @NonNull
    @Override
    public postViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posr_item, null, false);
        return new PostAdapter.postViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postViewHolder holder, int position) {
        PostItem postItem = getPosts.get(position);
        holder.userImage.setImageResource(postItem.getUserImage());
        holder.username.setText(postItem.getUserName());
        holder.postImage.setImageResource(postItem.getPostImage());
        holder.publisher.setText("#publisher: " + postItem.getUserName());
        holder.likes.setText("Likes : " + postItem.getLikes());
        holder.desc.setText(postItem.getDiscription());

    }

    @Override
    public int getItemCount() {
        return getPosts.size();
    }

    public class postViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage, postImage, comment, like;
        TextView desc, username, likes, publisher;

        public postViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.pass_change);
            userImage = itemView.findViewById(R.id.profile_edit_image);
            postImage = itemView.findViewById(R.id.post_image);
            comment = itemView.findViewById(R.id.comment);
            like = itemView.findViewById(R.id.like);
            desc = itemView.findViewById(R.id.description);
            likes = itemView.findViewById(R.id.likes);
            publisher = itemView.findViewById(R.id.publisher);


        }
    }
}
