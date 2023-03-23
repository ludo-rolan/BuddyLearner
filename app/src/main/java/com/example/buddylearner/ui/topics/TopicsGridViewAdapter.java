package com.example.buddylearner.ui.topics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.buddylearner.R;
import com.example.buddylearner.data.model.TopicsCategory;
import com.example.buddylearner.databinding.FragmentTopicsBinding;

import java.util.List;

public class TopicsGridViewAdapter extends BaseAdapter {

    Context context;
    List<TopicsCategory> topicsCategories;
    String [] categoriesTopics;
    LayoutInflater layoutInflater;
    private FragmentTopicsBinding binding;

    public TopicsGridViewAdapter(
            Context applicationContext,
            List<TopicsCategory> topicsCategories
    ) {
        this.context = applicationContext;
        this.topicsCategories = topicsCategories;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
//        if(topicsCategories != null)
//            return Objects.requireNonNull(topicsCategories).size();
        return topicsCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.topic_item, null);
        TextView topicNameTextView = convertView.findViewById(R.id.topic_name);

        // Log.d(TAG, "topicsCategories in topicsgridviewadapter : " + topicsCategories.get(position).getName());

        if(topicsCategories != null)
            topicNameTextView.setText(topicsCategories.get(position).getName());
        else topicNameTextView.setText("oops!");
        // topicNameTextView.setText(categoriesTopics[position]);
        return convertView;
    }
}
