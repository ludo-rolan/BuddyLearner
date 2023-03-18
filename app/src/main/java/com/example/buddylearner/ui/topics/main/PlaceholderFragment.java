package com.example.buddylearner.ui.topics.main;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.buddylearner.R;
import com.example.buddylearner.data.model.Topic;
import com.example.buddylearner.data.model.TopicsCategory;
import com.example.buddylearner.databinding.FragmentTopicsBinding;
import com.example.buddylearner.ui.elements.ModalBottomSheet;
import com.example.buddylearner.ui.follow.FollowTopicsCategoryActivity;
import com.example.buddylearner.ui.topics.TopicGridViewAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private String topicCategory;

    private PageViewModel pageViewModel;
    private FragmentTopicsBinding binding;
    private GridView gridView;
    private ChipGroup topicsChipGroup;

    String [] categoriesTopics = {
            "engineering",
            "management"
    };

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        pageViewModel = new ViewModelProvider(this, new TopicsPageViewModelFactory())
                .get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);


        // topics added to view

        //binding = FragmentTopicsBinding.inflate(getLayoutInflater());

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        // attach to parent = false
        binding = FragmentTopicsBinding.inflate(inflater, container, true);
        View root = binding.getRoot();

        final TextView textView = binding.sectionLabel;

        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        pageViewModel.loadTopicsCategory();

        gridView = binding.topicsGridview;

        MutableLiveData<TopicGridViewAdapter> topicGridViewAdapter = new MutableLiveData<>();

        // display in the second tab
        if(getArguments() != null && getArguments().getInt(ARG_SECTION_NUMBER) == 2) {

//        if(pageViewModel.getTopicsCategories() != null)
//            Log.d(TAG, "first topic category: " + pageViewModel.getTopicsCategories().get(0));

            pageViewModel.getTopicsCategories().observe(getViewLifecycleOwner(), topicsCategories -> {

//                TopicGridViewAdapter topicGridViewAdapter = new TopicGridViewAdapter(
//                        getContext(),
//                        topicsCategories
//                );

                topicGridViewAdapter.setValue(new TopicGridViewAdapter(getContext(), topicsCategories));

//                for (int i=0; i<topicsCategories.size(); i++) {
//                    Log.d(TAG, "card name : " + ((TextView) topicGridViewAdapter.getView(i, null, binding.topicsGridview).findViewById(R.id.topic_name)).getText());
//                }

            });


            // set gridview item click listener
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Log.d(TAG, "GridView view clicked ! ");
//
//                    //TODO: open bottomsheet when click on the card view
//                    ModalBottomSheet modalBottomSheet = new ModalBottomSheet();
//                    modalBottomSheet.show(getParentFragmentManager(), ModalBottomSheet.TAG);

                    Intent intent = new Intent(getContext(), FollowTopicsCategoryActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("topicsCategory", ((TextView)view.findViewById(R.id.topic_name)).getText().toString());

                    intent.putExtra("topicsCategory", ((TextView)view.findViewById(R.id.topic_name)).getText().toString());

                    Log.d(TAG, "topics category name : " + ((TextView)view.findViewById(R.id.topic_name)).getText().toString());

                    // startActivity(intent, bundle);
                    startActivity(intent);

                }
            });


            topicGridViewAdapter.observe(getViewLifecycleOwner(), new Observer<TopicGridViewAdapter>() {
                @Override
                public void onChanged(TopicGridViewAdapter topicGridViewAdapter) {

                    for(int i=0; i<topicGridViewAdapter.getCount(); i++) {

                        CardView cardView = (CardView) topicGridViewAdapter.getView(i,null, binding.topicsGridview);
                        cardView.setId(View.generateViewId());
                        cardView.setOnClickListener(view -> {

                            //TODO: open bottomsheet when click on the card view
                            ModalBottomSheet modalBottomSheet = new ModalBottomSheet();
                            modalBottomSheet.show(getParentFragmentManager(), ModalBottomSheet.TAG);

                        });

                    }


                    gridView.setAdapter(topicGridViewAdapter);
                }
            });


            //Log.d(TAG, "card name : " + ((TextView) topicGridViewAdapter.getView(i, null, binding.topicsGridview).findViewById(R.id.topic_name)).getText());


            // add topics to fragment
            topicsChipGroup = binding.topicsChipGroup;

            pageViewModel.loadTopics();
            // topics chip
            pageViewModel.getTopics().observe(getActivity(), topics -> {
//            for(int i=0; i<topics.size(); i++) {
//                addChip(topics.get(i).getName());
//            }

                Log.d(TAG, "topic name : " + topics.get(0).getName());

                // add topic to chip
                for(Topic topic : topics) {
                    addChip(topic.getName());
                }
            });

        }


        if(gridView == null)
            Log.d(TAG, "gridView is null !!!!");
        else Log.d(TAG, "gridView is not null !!!!");




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addChip (String text) {
        Chip topicChip = new Chip(getContext());
        topicChip.setId(View.generateViewId());
        topicChip.setText(text);
        topicChip.setCloseIconVisible(true);
        topicChip.setChipIconResource(R.drawable.baseline_person_24);

//        topicChip.setOnClickListener(view -> {
//
//            Log.d(TAG, "topicChip clicked ! " + topicChip.getText());
//
//            // it works!
//            //TODO: open bottomsheet when click on the topicChip
//            ModalBottomSheet modalBottomSheet = new ModalBottomSheet();
//            modalBottomSheet.show(getParentFragmentManager(), ModalBottomSheet.TAG);
//
//        });

        topicChip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicsChipGroup.removeView(topicChip);
            }
        });

        topicsChipGroup.addView(topicChip);
    }
}