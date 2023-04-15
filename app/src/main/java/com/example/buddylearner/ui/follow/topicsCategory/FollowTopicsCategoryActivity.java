package com.example.buddylearner.ui.follow.topicsCategory;

import static com.example.buddylearner.ui.elements.FollowTopicCategoryModalBottomSheet.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.buddylearner.R;
import com.example.buddylearner.databinding.ActivityFollowTopicsCategoryBinding;
import com.example.buddylearner.databinding.ActivityHomeBinding;
import com.example.buddylearner.databinding.BottomSheetBinding;
import com.example.buddylearner.ui.elements.FollowTopicCategoryModalBottomSheet;

public class FollowTopicsCategoryActivity extends AppCompatActivity {

    ActivityFollowTopicsCategoryBinding binding;
    BottomSheetBinding bottomSheetBinding;

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

        Button followTopicsCategoryButton = binding.followTopicsCategory;
        Button followingButton = binding.followingTopicsCategory;
        Button notInsterestedButton = binding.notInterestedTopicsCategory;


        followTopicsCategoryButton.setOnClickListener(view -> {

            ((ViewGroup) notInsterestedButton.getParent()).removeView(notInsterestedButton);
            ((ViewGroup) view.getParent()).removeView(view);

        });

        followingButton.setOnClickListener(view -> {

            //TODO: open bottomsheet when click on following button
            FollowTopicCategoryModalBottomSheet modalBottomSheet = new FollowTopicCategoryModalBottomSheet();
            modalBottomSheet.show(getSupportFragmentManager(), FollowTopicCategoryModalBottomSheet.TAG);

            Bundle bundle = new Bundle();
            bundle.putString("title", "Unfollow");
            bundle.putString("text", "Unfollow " + topicsCategoryName);

            View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                    R.layout.bottom_sheet,
                    (CoordinatorLayout) findViewById(R.id.bottom_sheet_container)
                    );

            modalBottomSheet.onCreateView(getLayoutInflater(), bottomSheetView.findViewById(R.id.bottom_sheet_container), bundle);

        });

    }
}