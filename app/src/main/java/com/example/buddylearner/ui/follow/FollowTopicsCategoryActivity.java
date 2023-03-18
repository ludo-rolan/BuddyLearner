package com.example.buddylearner.ui.follow;

import static com.example.buddylearner.ui.elements.ModalBottomSheet.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.buddylearner.R;
import com.example.buddylearner.databinding.ActivityFollowTopicsCategoryBinding;
import com.example.buddylearner.databinding.ActivityHomeBinding;

public class FollowTopicsCategoryActivity extends AppCompatActivity {

    ActivityFollowTopicsCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_follow_topics_category);

        binding = ActivityFollowTopicsCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Bundle topicsCategoryBundle = getIntent().getBundleExtra("topicsCategory");
//        String topicsCategoryName = topicsCategoryBundle.toString();

        String topicsCategoryName = getIntent().getStringExtra("topicsCategory");

        Log.d(TAG, "topics category followtopics activity : " + topicsCategoryName);

        TextView followTopicsCategoryTextView =  binding.followTopicsCategoryTextView;
        followTopicsCategoryTextView.setText(topicsCategoryName);

    }
}