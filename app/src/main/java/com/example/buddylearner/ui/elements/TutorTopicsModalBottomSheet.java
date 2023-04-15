package com.example.buddylearner.ui.elements;

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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.example.buddylearner.ui.base.transform.TransformFragment;
import com.example.buddylearner.ui.base.transform.TransformViewModel;
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
    TutorTopicsBottomSheetBinding bottomSheetBinding;
    List<String> oppositeRoleUsers;

    public TutorTopicsModalBottomSheet(List<String> oppositeRoleUsers) {
        this.oppositeRoleUsers = oppositeRoleUsers;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            // set action on button click
            holder.button.setOnClickListener(view -> {

//                SendTutorRequest tutorRequest = new SendTutorRequest();
//                tutorRequest.send(view.getContext());
                // Toast.makeText(view.getContext(), "clic - " + view.getId(), Toast.LENGTH_LONG).show();

                // transformViewModel.loadCurrentUser();
//                transformViewModel.loadTutor(getItem(position));
//                transformViewModel.loadTutorTopics(getItem(position));

//                transformViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
//
//                    transformViewModel.load
//
//                });

//                //TODO: open bottomsheet when click on following button
//                FollowTopicCategoryModalBottomSheet modalBottomSheet = new FollowTopicCategoryModalBottomSheet();
//                // modalBottomSheet.show(getSupportFragmentManager(), ModalBottomSheet.TAG);
//                modalBottomSheet.show(getParentFragmentManager(), FollowTopicCategoryModalBottomSheet.TAG);
//
//                Bundle bundle = new Bundle();
//                bundle.putString("title", "tutor topics");
//                bundle.putString("text", "topic i " + "topicName");
//
//                View bottomSheetView = LayoutInflater.from(view.getContext()).inflate(
//                        R.layout.bottom_sheet,
//                        (CoordinatorLayout) view.findViewById(R.id.bottom_sheet_container)
//                );
//
//                modalBottomSheet.onCreateView(getLayoutInflater(), bottomSheetView.findViewById(R.id.bottom_sheet_container), bundle);

//                transformViewModel.getTutor().observe(getViewLifecycleOwner(), tutor -> {
//
//                });

            });
        }
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
