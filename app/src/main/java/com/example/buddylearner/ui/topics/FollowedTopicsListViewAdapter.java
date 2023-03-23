package com.example.buddylearner.ui.topics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddylearner.R;
import com.example.buddylearner.data.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class FollowedTopicsListViewAdapter extends RecyclerView.Adapter<FollowedTopicsListViewAdapter.ViewHolder> {

    Context context;
    List<Topic> topics = new ArrayList<>();
    LayoutInflater layoutInflater;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView followedTopicTextView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            followedTopicTextView = (TextView) view.findViewById(R.id.followed_topic_textView);
        }

        public TextView getFollowedTopicTextView() {
            return followedTopicTextView;
        }
    }


    /**
     * Initialize the topics list of the Adapter
     *
     * @param topics List<Topic> containing the data to populate views to be used
     * by RecyclerView
     */
    public FollowedTopicsListViewAdapter(
            Context applicationContext,
            List<Topic> topics
    ) {
        this.context = applicationContext;
        this.topics = topics;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = layoutInflater.from(parent.getContext())
                .inflate(R.layout.followed_topics_listview_item, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.getFollowedTopicTextView().setText(topics.get(position).getName());
    }

    // Return the size of your topics (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return topics.size();
    }
}
