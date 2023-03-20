package com.example.buddylearner.ui.topics.main;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.buddylearner.R;
import com.example.buddylearner.data.model.Topic;
import com.example.buddylearner.databinding.FragmentTopicsBinding;
import com.example.buddylearner.ui.elements.ModalBottomSheet;
import com.example.buddylearner.ui.follow.topicsCategory.FollowTopicsCategoryActivity;
import com.example.buddylearner.ui.topics.TopicGridViewAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

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

    private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                //chipNumber = chipNumber + 1;
            } else {
//                    if (chipNumber > 0) {
//                        chipNumber = chipNumber - 1;
//                    }
            }
        }
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


            // set gridview items
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
//            List<Topic> topics;
//            if(pageViewModel.getTopics().getValue() != null) {
//                topics = pageViewModel.getTopics().getValue();
//
//                // add topic to chip
//                for(Topic topic : topics) {
//                    addChip(topic.getName());
//                }
//            }

//             topics chip
            pageViewModel.getTopics().observe(getActivity(), topics -> {
                for(int i=0; i<topics.size(); i++) {
                    addChip(topics.get(i).getName());

                    // Vérifier si nous avons atteint la limite de chips par ligne
                    // regrouper les chips par 10 dans le chipgroup
                    // it doesn't work !
//                    if ((i+1) % 5 == 0) {
//                        // Ajouter une nouvelle ligne de Chips
//                        topicsChipGroup.addView(new View(getContext()), new ViewGroup.LayoutParams(0,100));
//                    }
                }

//                Log.d(TAG, "topic name in placeholderFragment : " + topics.get(0).getName());

                // add topic to chip
//                for(Topic topic : topics) {
//                    addChip(topic.getName());
//                }
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

    private void addChip (String topicName) {

        Chip topicChip = new Chip(getContext());
        //Chip topicChip = (Chip) LayoutInflater.from(getContext()).inflate(R.layout.chip_item, null);
        topicChip.setId(View.generateViewId());
        topicChip.setText(topicName);
        // topicChip.setCloseIconVisible(true);
        topicChip.setCheckedIconVisible(true);
        topicChip.setCheckedIconResource(R.drawable.baseline_add_24);
        topicChip.setChipIconResource(R.drawable.baseline_person_24);
        topicChip.setClickable(true);
        //topicChip.setCheckable(true);

        topicChip.setOnCheckedChangeListener(changeListener);

        topicsChipGroup.addView(topicChip);

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

//        topicChip.setOnCloseIconClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                topicsChipGroup.removeView(topicChip);
//            }
//        });


//        pageViewModel.loadUsername();
//
//        // get the username
//        pageViewModel.getUsername().observe(getActivity(), username -> {
//
//            pageViewModel.loadIsFollowingTopic(username, topicName);
//
//            // get is topic is already followed
//            pageViewModel.getIsFollowingTopic().observe(getActivity(), isFollowingTopic -> {
//
//                if(isFollowingTopic) {
//                    //TODO: chip button icon on following topic
//                    topicChip.setCheckedIconVisible(true);
//                    topicChip.setBackgroundColor(getResources().getColor(R.color.purple_500));
//                } else {
//                    //TODO: chip button icon on unfollowing topic
//                    topicChip.setCheckedIconVisible(true);
//                    topicChip.setCheckedIconResource(R.drawable.baseline_add_24);
//                }
//
//            });
//
//        });


        // already loaded on top
        //pageViewModel.loadTopics();
        pageViewModel.loadUsername();

        topicChip.setOnClickListener(view -> {

            List<Topic> topicsList;
            Topic topicValue = null;

            if(pageViewModel.getTopics().getValue() != null) {
                topicsList = pageViewModel.getTopics().getValue();

                for(Topic topic : topicsList) {
                    if(topic.getName().equalsIgnoreCase(topicName)) {
                        topicValue = topic;
                    }
                }

            }

            if(topicValue != null) {
                Log.d(TAG, "topic name clicked on : " + topicValue.getName());
            }

            String usernameValue = null;
            if(pageViewModel.getUsername().getValue() != null) {
                usernameValue = pageViewModel.getUsername().getValue();
            }

            if(usernameValue != null) {
                Log.d(TAG, "username value : " + usernameValue);
            }

            pageViewModel.loadIsFollowingTopic(usernameValue, topicValue.getName());

            boolean isFollowingTopic = false;
            if(pageViewModel.getIsFollowingTopic().getValue() != null) {
                isFollowingTopic = pageViewModel.getIsFollowingTopic().getValue();
            }

            if(isFollowingTopic) {
                Log.d(TAG, "topic is already followed : " + true);
            }
            else {
                Log.d(TAG, "topic is already followed : " + false);
            }

            if(isFollowingTopic) {
                pageViewModel.stopFollowingTopic(usernameValue, topicValue.getName(), topicValue.getTopicCategory());
                //TODO: change the chip button state on stop following topic
                topicChip.setCheckedIconVisible(true);
                topicChip.setCheckedIconResource(R.drawable.baseline_add_24);
            } else {
                pageViewModel.startFollowingTopic(usernameValue, topicValue.getName(), topicValue.getTopicCategory());
                //TODO: change the chip button state on start following topic
                topicChip.setCheckedIconVisible(true);
                topicChip.setBackgroundColor(getResources().getColor(R.color.purple_500));
            }


            // find topic element
            // topicsList.indexOf();

            // get the selected topic category
//            pageViewModel.getTopics().observe(getActivity(), topics -> {
//
//               String topicCategory = null;
//
//                for(Topic topic : topics) {
//                    if(topic.getName().equalsIgnoreCase(topicName)) {
//                        topicCategory = topic.getTopicCategory();
//                        break;
//                    }
//                }
//
//                Log.d(TAG, "I clicked on topic category : " + topicCategory);
//
//                final String finalTopicCategory = topicCategory;
//
//                pageViewModel.loadUsername();
//
//                String usernameValue;
//                if(pageViewModel.getUsername().getValue() != null) {
//                    usernameValue = pageViewModel.getUsername().getValue();
//                    Log.d(TAG, "username value : " + usernameValue);
//                }
//
//                // get the username
//                pageViewModel.getUsername().observe(getActivity(), username -> {
//
//                    pageViewModel.loadIsFollowingTopic(username, topicName);
//
//                    // get is topic is already followed
//                    pageViewModel.getIsFollowingTopic().observe(getActivity(), isFollowingTopic -> {
//
//                        if(isFollowingTopic) {
//                            pageViewModel.stopFollowingTopic(username, topicName, finalTopicCategory);
//                            //TODO: change the chip button state on stop following topic
//                            topicChip.setCheckedIconVisible(true);
//                            topicChip.setCheckedIconResource(R.drawable.baseline_add_24);
//                        } else {
//                            pageViewModel.startFollowingTopic(username, topicName, finalTopicCategory);
//                            //TODO: change the chip button state on start following topic
//                            topicChip.setCheckedIconVisible(true);
//                            topicChip.setBackgroundColor(getResources().getColor(R.color.purple_500));
//                        }
//
//                    });
//
//                });
//
//            });

        });

    }
}