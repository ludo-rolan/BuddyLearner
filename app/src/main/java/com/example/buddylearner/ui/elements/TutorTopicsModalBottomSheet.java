package com.example.buddylearner.ui.elements;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddylearner.R;
import com.example.buddylearner.data.model.UserTopic;
import com.example.buddylearner.databinding.BottomSheetBinding;
import com.example.buddylearner.databinding.ItemTransformBinding;
import com.example.buddylearner.databinding.ItemTutorTopicsBottomSheetBinding;
import com.example.buddylearner.databinding.TutorTopicsBottomSheetBinding;
import com.example.buddylearner.services.NotifyService;
import com.example.buddylearner.ui.base.transform.TransformFragment;
import com.example.buddylearner.ui.base.transform.TransformViewModel;
import com.example.buddylearner.ui.base.transform.TransformViewModelFactory;
import com.example.buddylearner.ui.notification.NotificationActivity;
import com.example.buddylearner.ui.notifications.SendTutorRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TutorTopicsModalBottomSheet extends BottomSheetDialogFragment {

    public final static String TAG = "ModalBottomSheet";

    private RecyclerView recyclerView;

    private String title;
    private String text;

    TransformViewModel transformViewModel;
    TutorTopicsBottomSheetBinding bottomSheetBinding;
    List<String> oppositeRoleUsers;

    public TutorTopicsModalBottomSheet(List<String> oppositeRoleUsers) {
        this.oppositeRoleUsers = oppositeRoleUsers;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transformViewModel = new ViewModelProvider(this, new TransformViewModelFactory())
                .get(TransformViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        bottomSheetBinding = TutorTopicsBottomSheetBinding.inflate(inflater, container, false);
        View root = bottomSheetBinding.getRoot();

        recyclerView = bottomSheetBinding.recyclerviewTransform;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        ListAdapter<String, TutorTopicsModalBottomSheet.TutorTopicsBottomSheetViewHolder> adapter = new TutorTopicsModalBottomSheet.TutorTopicsModalBottomSheetAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //Log.d(TAG, "oppositeRoleUsers size : " + oppositeRoleUsers.size());
//        List<String> oppositeRoleUsers = new ArrayList<>();
//        if(savedInstanceState != null) {
//            Log.d(TAG, "oppositeRoleUsers list in bottom sheet : " + savedInstanceState.getStringArrayList("oppositeRoleUsers"));
//            oppositeRoleUsers.addAll(savedInstanceState.getStringArrayList("oppositeRoleUsers").stream().collect(Collectors.toList()));
//            adapter.submitList(oppositeRoleUsers);
//        }
        adapter.submitList(oppositeRoleUsers);



//        if(savedInstanceState != null) {
//            title = savedInstanceState.getString("title");
//            text = savedInstanceState.getString("text");
//        }
//
//        if(!title.isEmpty() && !text.isEmpty()){
//            TextView titleBottomSheet = bottomSheetBinding.;
//            titleBottomSheet.setText(title);
//            TextView textBottomSheet = bottomSheetBinding.textBottomSheet;
//            textBottomSheet.setText(text);
//        }

        // return super.onCreateView(inflater, container, savedInstanceState);
        return root;
    }


    // TODO: Populate the recycleview with all the topics followed by the tutor
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bottomSheetBinding = null;
    }

    private class TutorTopicsModalBottomSheetAdapter extends ListAdapter<String, TutorTopicsModalBottomSheet.TutorTopicsBottomSheetViewHolder> {

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

        protected TutorTopicsModalBottomSheetAdapter() {
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
        public TutorTopicsModalBottomSheet.TutorTopicsBottomSheetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemTutorTopicsBottomSheetBinding binding = ItemTutorTopicsBottomSheetBinding.inflate(LayoutInflater.from(parent.getContext()));
            return new TutorTopicsModalBottomSheet.TutorTopicsBottomSheetViewHolder(binding);
        }

        // use this method to perform actions on recycle view items - buttons - textview - imageview ...
        @Override
        public void onBindViewHolder(@NonNull TutorTopicsModalBottomSheet.TutorTopicsBottomSheetViewHolder holder, int position) {
            holder.textView.setText(getItem(position));
            holder.imageView.setImageDrawable(
                    ResourcesCompat.getDrawable(holder.imageView.getResources(),
                            drawables.get(position),
                            null));


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                if(
                        ContextCompat.checkSelfPermission(
                                getContext(),
                                android.Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                ){
                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.POST_NOTIFICATIONS}, 101);
                }

            }


            // set action on button click
            // TODO: implement the logic of sending and removing tutor request
            holder.button.setOnClickListener(view -> {

//                SendTutorRequest tutorRequest = new SendTutorRequest();
//                tutorRequest.send(view.getContext());

                // TODO: get the request on database to create notification
                // makeNotification(getContext());

                transformViewModel.loadCurrentUser();
                transformViewModel.getCurrentUser().observe(getViewLifecycleOwner(), currentUser -> {
                    // TODO: get the actual topic name and topic category of the tutor
                    transformViewModel.sendTutorRequest(currentUser, holder.textView.getText().toString(), "topic name", "topicCategory");
                });

            });
        }
    }

    // create an in app notification
//    public void makeNotification(Context context, String title, String message, int smallIcon, int bigIcon, Class<?> intentClass) {
    public void makeNotification(Context context) {

    // must be extend by a class activity
//        NotifyService notifyService = new NotifyService() {
//            @Override
//            protected Notification serviceNotification() {
//                // createNotification is defined in the service class
//                Notification notification = createNotification(title, message, smallIcon, bigIcon, intentClass);
//                return notification;
//            }
//        };
//
//        notifyService.showNotification("hello world", context);

        String channelId = "my_channel_0";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context,
                channelId
        );

        builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("Notification title")
                .setContentText("Some text for notification here")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(context, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data", "Some value to be passed here");

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_MUTABLE
        );

        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);

            if(notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(
                        channelId,
                        "some descripotion",
                        importance
                );
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationManager.notify(0, builder.build());

    }

    private static class TutorTopicsBottomSheetViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;
        private final MaterialButton button;

        public TutorTopicsBottomSheetViewHolder(ItemTutorTopicsBottomSheetBinding binding) {
            super(binding.getRoot());
            imageView = binding.imageViewItemTutorTopics;
            textView = binding.textViewItemTutorTopics;
            button = binding.materialButton;
            button.setId(View.generateViewId());
        }
    }

}
