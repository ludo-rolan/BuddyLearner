package com.example.buddylearner.ui.base.transform;

import static android.content.ContentValues.TAG;
import static android.provider.Settings.Global.getString;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
import com.example.buddylearner.ui.elements.FollowTopicCategoryModalBottomSheet;
import com.example.buddylearner.ui.elements.TutorTopicsModalBottomSheet;
import com.example.buddylearner.ui.notifications.SendTutorRequest;
import com.example.buddylearner.ui.topics.main.PageViewModel;
import com.example.buddylearner.ui.topics.main.TopicsPageViewModelFactory;
import com.google.android.material.button.MaterialButton;

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

    private static final String CHANNEL_ID = "my_channel_01";
    private FragmentTransformBinding fragmentTransformBinding;
    private TransformViewModel transformViewModel;
    List<String> oppositeRoleUsers = new ArrayList<>();
    TutorTopicsModalBottomSheet modalBottomSheet;


//    public TransformFragment() {
//
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transformViewModel = new ViewModelProvider(this, new TransformViewModelFactory())
                .get(TransformViewModel.class);


        //TODO: create proper view model for bottom sheet
        //modalBottomSheet = new TutorTopicsModalBottomSheet(oppositeRoleUsers);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        TransformViewModel transformViewModel =
//                new ViewModelProvider(this).get(TransformViewModel.class);

        fragmentTransformBinding = FragmentTransformBinding.inflate(inflater, container, false);
        View root = fragmentTransformBinding.getRoot();

        RecyclerView recyclerView = fragmentTransformBinding.recyclerviewTransform;
        ListAdapter<String, TransformViewHolder> adapter = new TransformAdapter();
        // recyclerView.setAdapter(adapter);
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

        // String currentUserUsername = super.getActivity().getIntent().getStringExtra("username");
        transformViewModel.loadCurrentUser();
        transformViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            Log.d(TAG, "current user displayname : " + user.getUserName());

            transformViewModel.loadUserFollowedTopics(user);
            transformViewModel.getUserFollowedTopics().observe(getViewLifecycleOwner(), userFollowedTopics -> {

                transformViewModel.loadTutorUsers(user, userFollowedTopics);
                transformViewModel.getTutorUsers().observe(getViewLifecycleOwner(), tutorUsersUserTopics -> {

                    Log.d(TAG, "list of tutors :");
                    //List<String> oppositeRoleUsers = new ArrayList<>();
                    if (tutorUsersUserTopics != null) {

                        for (UserTopic tutorUserUserTopic : tutorUsersUserTopics) {
                            if (tutorUserUserTopic != null)
                                Log.d(TAG, "user finded with topic : " + tutorUserUserTopic.getTopicName());

                            oppositeRoleUsers.add(tutorUserUserTopic.getUserName());
                        }

                        adapter.submitList(oppositeRoleUsers);

                    }

                });

            });

        });


        // doesn't work stop the app continuously
        // test of another way to get data in the bottom sheet recycleview
//        transformViewModel.loadCurrentUser();
//        User user = transformViewModel.getCurrentUser().getValue();
//
//        if(user != null)
//            transformViewModel.loadUserFollowedTopics(user);
//
//        List<UserTopic> userFollowedTopics = transformViewModel.getUserFollowedTopics().getValue();
//
//        if(user != null && userFollowedTopics != null)
//            transformViewModel.loadTutorUsers(user, userFollowedTopics);
//
//        List<UserTopic> tutorUsersUserTopics = transformViewModel.getTutorUsers().getValue();
//        if(tutorUsersUserTopics != null) {
//
//            for(UserTopic tutorUserUserTopic : tutorUsersUserTopics) {
//                if(tutorUserUserTopic != null)
//                    Log.d(TAG, "user finded with topic : " + tutorUserUserTopic.getTopicName());
//
//                oppositeRoleUsers.add(tutorUserUserTopic.getUserName());
//            }
//
//            adapter.submitList(oppositeRoleUsers);
//
//        }

        recyclerView.setAdapter(adapter);
        // test of another way to get data in the bottom sheet recycleview


//        if(oppositeRoleUsers != null) {
//            Log.d(TAG, "opposite role users transform fragment : " + oppositeRoleUsers);
//            adapter.submitList(oppositeRoleUsers);
//        }


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


        // create a notification
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
//                .setSmallIcon(R.drawable.baseline_person_24)
//                .setContentTitle("content title")
//                .setContentText("text content")
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("Much longer text that cannot fit one line..."))
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        // action to be performed on notification click
        // Create an explicit intent for an Activity in your app
//        Intent intent = new Intent(this, AlertDetails.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.notification_icon)
//                .setContentTitle("My notification")
//                .setContentText("Hello World!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                // Set the intent that will fire when the user taps the notification
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);


        // display the notification
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//
//        int notificationId = 300;
//        // notificationId is a unique int for each notification that you must define
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return null;
//        }
//        notificationManager.notify(notificationId, builder.build());



        return root;
    }


    // added


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentTransformBinding = null;
    }

    private class TransformAdapter extends ListAdapter<String, TransformViewHolder> {

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

        // use this method to perform actions on recycle view items - buttons - textview - imageview ...
        @Override
        public void onBindViewHolder(@NonNull TransformViewHolder holder, int position) {
            holder.textView.setText(getItem(position));
            holder.imageView.setImageDrawable(
                    ResourcesCompat.getDrawable(holder.imageView.getResources(),
                            drawables.get(position),
                            null));

            // set action on button click
            holder.button.setOnClickListener(view -> {

                SendTutorRequest tutorRequest = new SendTutorRequest();
                tutorRequest.send(view.getContext());
                // Toast.makeText(view.getContext(), "clic - " + view.getId(), Toast.LENGTH_LONG).show();

                // transformViewModel.loadCurrentUser();
                transformViewModel.loadTutor(getItem(position));
                transformViewModel.loadTutorTopics(getItem(position));

                List<String> listOfTutorTopics = new ArrayList<>();
                transformViewModel.getTutorTopics().observe(getViewLifecycleOwner(), tutorTopics -> {
                    if(tutorTopics != null) {
                        for(UserTopic tutorTopic: tutorTopics) {
                            listOfTutorTopics.add(tutorTopic.getTopicName());
                        }
                    }
                });


                // test with static list
//                oppositeRoleUsers = new ArrayList<String>(){
//                    {
//                        add("communication");
//                        add("programming");
//                        add("web");
//                        add("mobile");
//                    }
//                };


                //TODO: open bottomsheet when click on following button
                //TODO: create proper view model for bottom sheet
                TutorTopicsModalBottomSheet modalBottomSheet = new TutorTopicsModalBottomSheet(listOfTutorTopics);
//                TutorTopicsModalBottomSheet modalBottomSheet = new TutorTopicsModalBottomSheet();
                // modalBottomSheet.show(getSupportFragmentManager(), ModalBottomSheet.TAG);
                modalBottomSheet.show(getParentFragmentManager(), TutorTopicsModalBottomSheet.TAG);

                Bundle bundle = new Bundle();
                if(listOfTutorTopics != null) {
                    bundle.putStringArrayList("listOfTutorTopics", (ArrayList<String>) listOfTutorTopics);
                }
                bundle.putString("title", "tutor topics");
                bundle.putString("text", "topic i " + "topicName");

                View bottomSheetView = LayoutInflater.from(view.getContext()).inflate(
                        R.layout.bottom_sheet,
                        (CoordinatorLayout) view.findViewById(R.id.bottom_sheet_container)
                );

                //                modalBottomSheet.onCreateView(getLayoutInflater(), bottomSheetView.findViewById(R.id.bottom_sheet_container), bundle);

                if(bundle != null) {

                    Log.d(TAG, "listOfTutorTopics list : " + bundle.getStringArrayList("listOfTutorTopics"));
                    modalBottomSheet.onCreateView(getLayoutInflater(), bottomSheetView.findViewById(R.id.bottom_sheet_container), bundle);

                }

                //adapter.submitList(oppositeRoleUsers);
            });
        }
    }

    private static class TransformViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;
        private final MaterialButton button;

        public TransformViewHolder(ItemTransformBinding binding) {
            super(binding.getRoot());
            imageView = binding.imageViewItemTransform;
            textView = binding.textViewItemTransform;
            button = binding.materialButton;
            button.setId(View.generateViewId());
        }
    }

    private void createNotificationChannel() {



        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // create a notification channel
            getString(R.string.channel_name);
            CharSequence name = getString(R.string.channel_name);
            // String description = getString(R.string.channel_description);
            String description = getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            //NotificationManager notificationManager = getSystemService(NotificationManager.class);
            NotificationManager notificationManager = getSystemService(getContext(), NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }



    }
}