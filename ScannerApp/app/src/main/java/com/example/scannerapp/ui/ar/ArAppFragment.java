package com.example.scannerapp.ui.ar;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.Fragment;

import com.example.scannerapp.MainActivity;
import com.example.scannerapp.ar;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.example.scannerapp.R;
import com.google.ar.sceneform.ux.ArFragment;
import android.widget.Toast;
import android.content.Context;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import android.view.MotionEvent;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ux.TransformableNode;
import android.util.Log;
import android.widget.Button;

public class ArAppFragment extends Fragment {

    private ArAppViewModel arAppViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        arAppViewModel =
                ViewModelProviders.of(this).get(ArAppViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ar, container, false);
        
        startActivity(new Intent(getActivity(), ar.class));

        return root;
    }
}

