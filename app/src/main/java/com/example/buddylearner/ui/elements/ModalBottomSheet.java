package com.example.buddylearner.ui.elements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.buddylearner.databinding.BottomSheetBinding;
import com.example.buddylearner.databinding.FragmentTopicsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalBottomSheet extends BottomSheetDialogFragment {

    public final static String TAG = "ModalBottomSheet";
    BottomSheetBinding bottomSheetBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        bottomSheetBinding = BottomSheetBinding.inflate(inflater, container, false);
        View root = bottomSheetBinding.getRoot();

        // return super.onCreateView(inflater, container, savedInstanceState);
        return root;
    }

}
