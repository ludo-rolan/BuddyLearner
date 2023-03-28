package com.example.buddylearner.ui.base.transform;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddylearner.R;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.model.UserTopic;
import com.example.buddylearner.databinding.FragmentTransformBinding;
import com.example.buddylearner.databinding.ItemTransformBinding;
import com.example.buddylearner.ui.topics.main.PageViewModel;
import com.example.buddylearner.ui.topics.main.TopicsPageViewModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
public class TransformFragment extends Fragment {

    private FragmentTransformBinding fragmentTransformBinding;
    private TransformViewModel transformViewModel;


//    public TransformFragment() {
//
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transformViewModel = new ViewModelProvider(this, new TransformViewModelFactory())
                .get(TransformViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        TransformViewModel transformViewModel =
//                new ViewModelProvider(this).get(TransformViewModel.class);

        fragmentTransformBinding = FragmentTransformBinding.inflate(inflater, container, false);
        View root = fragmentTransformBinding.getRoot();

        RecyclerView recyclerView = fragmentTransformBinding.recyclerviewTransform;
        ListAdapter<String, TransformViewHolder> adapter = new TransformAdapter();
        recyclerView.setAdapter(adapter);
        //transformViewModel.getTexts().observe(getViewLifecycleOwner(), adapter::submitList);

        // new implementation
//        List<String> texts = new ArrayList<>();

//        if(transformViewModel.getTexts() == null) {
//            Log.d(TAG, "transform view model texts is null");
//        } else {
//            Log.d(TAG, "transform view model texts is not null");
//            if(transformViewModel.getTexts().getValue() != null) {
//
//                texts.addAll(transformViewModel.getTexts().getValue().isEmpty() ? null : transformViewModel.getTexts().getValue());
//                Log.d(TAG, "text 1 : " + texts.get(0));
//
//            } else {
//                Log.d(TAG, "no data in texts");
//            }
//        }

//        if(transformViewModel != null && transformViewModel.getTexts() != null) {
//            texts.addAll(transformViewModel != null ? transformViewModel.getTexts().getValue() : null);
//        }
//        adapter.submitList(texts);

        // doesn't work!
//        transformViewModel.getTexts().observe(getViewLifecycleOwner(), texts -> {
//            for (String text: texts) {
//                Log.d(TAG, "transform text : " + text);
//            }
//        });


        // users following the same topic

        // transformViewModel.loadCurrentUser();
//
//        if(transformViewModel != null) {
//            Log.d(TAG, "transformViewModel not null");
//        } else {
//            Log.d(TAG, "transformViewModel null");
//        }

        // get username from parent HomeActivity passed by LoginActivity in the intent
//        String currentUsername = super.getActivity().getIntent().getStringExtra("username");
//        Log.d(TAG, "username in transform fragment : " + currentUsername);
//
//        //TODO: pass the username to loadCurrentUser method
//        transformViewModel.loadCurrentUser(currentUsername);
//
//        User user = null;
//        if(transformViewModel.getCurrentUser() != null) {
//            Log.d(TAG, "current user username : " + transformViewModel.getCurrentUser().getValue().getUserName());
//            user = transformViewModel.getCurrentUser().getValue();
//        }
//
//        transformViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user1 -> {
//
//        });
//
//         transformViewModel.loadTutorUsers(user.getUserName());
//         transformViewModel.getTutorUsers().observe(getViewLifecycleOwner(), users -> {
//
//             // do sth to all tutor users
//             Log.d(TAG, "user 1 : " + users.get(0).getUserName());
//
//         });

        String currentUserUsername = super.getActivity().getIntent().getStringExtra("username");
        transformViewModel.loadCurrentUser(currentUserUsername);
        transformViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            Log.d(TAG, "current user displayname : " + user.getUserName());

            transformViewModel.loadUserFollowedTopics(user);
            transformViewModel.getUserFollowedTopics().observe(getViewLifecycleOwner(), userFollowedTopics -> {

                transformViewModel.loadTutorUsers(user, userFollowedTopics);
                transformViewModel.getTutorUsers().observe(getViewLifecycleOwner(), tutorUsersUserTopics -> {

                    Log.d(TAG, "list of tutors :");
                    List<String> oppositeRoleUsers = new ArrayList<>();
                    if(tutorUsersUserTopics != null) {

                        for(UserTopic tutorUserUserTopic : tutorUsersUserTopics) {
                            if(tutorUserUserTopic != null)
                                Log.d(TAG, "user finded with topic : " + tutorUserUserTopic.getTopicName());

                            oppositeRoleUsers.add(tutorUserUserTopic.getUserName());
                        }

                        adapter.submitList(oppositeRoleUsers);

                    }

                });

            });

        });

        // doesn't work!
//        User user = null;
//        if(transformViewModel.getCurrentUser().getValue() != null)
//            user = transformViewModel.getCurrentUser().getValue();
//
//        if(user != null) {
//            Log.d(TAG, "current user displayname new user variable : " + user.getUserName());
//        } else {
//            Log.d(TAG, "current user displayname new user variable null");
//        }



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentTransformBinding = null;
    }

    private static class TransformAdapter extends ListAdapter<String, TransformViewHolder> {

        private final List<Integer> drawables = Arrays.asList(
                R.drawable.avatar_1,
                R.drawable.avatar_2,
                R.drawable.avatar_3,
                R.drawable.avatar_4,
                R.drawable.avatar_5,
                R.drawable.avatar_6,
                R.drawable.avatar_7,
                R.drawable.avatar_8,
                R.drawable.avatar_9,
                R.drawable.avatar_10,
                R.drawable.avatar_11,
                R.drawable.avatar_12,
                R.drawable.avatar_13,
                R.drawable.avatar_14,
                R.drawable.avatar_15,
                R.drawable.avatar_16);

        protected TransformAdapter() {
            super(new DiffUtil.ItemCallback<String>() {
                @Override
                public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                    return oldItem.equals(newItem);
                }
            });
        }

        @NonNull
        @Override
        public TransformViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemTransformBinding binding = ItemTransformBinding.inflate(LayoutInflater.from(parent.getContext()));
            return new TransformViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull TransformViewHolder holder, int position) {
            holder.textView.setText(getItem(position));
            holder.imageView.setImageDrawable(
                    ResourcesCompat.getDrawable(holder.imageView.getResources(),
                            drawables.get(position),
                            null));
        }
    }

    private static class TransformViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        public TransformViewHolder(ItemTransformBinding binding) {
            super(binding.getRoot());
            imageView = binding.imageViewItemTransform;
            textView = binding.textViewItemTransform;
        }
    }
}