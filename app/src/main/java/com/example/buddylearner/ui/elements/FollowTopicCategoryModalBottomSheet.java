package com.example.buddylearner.ui.elements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.buddylearner.databinding.BottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FollowTopicCategoryModalBottomSheet extends BottomSheetDialogFragment {

    public final static String TAG = "ModalBottomSheet";

    private String title;
    private String text;
    BottomSheetBinding bottomSheetBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        bottomSheetBinding = BottomSheetBinding.inflate(inflater, container, false);
        View root = bottomSheetBinding.getRoot();

        if(savedInstanceState != null) {
            title = savedInstanceState.getString("title");
            text = savedInstanceState.getString("text");
        }

        if(!title.isEmpty() && !text.isEmpty()){
            TextView titleBottomSheet = bottomSheetBinding.titleBottomSheet;
            titleBottomSheet.setText(title);
            TextView textBottomSheet = bottomSheetBinding.textBottomSheet;
            textBottomSheet.setText(text);
        }

        // return super.onCreateView(inflater, container, savedInstanceState);
        return root;
    }

}
