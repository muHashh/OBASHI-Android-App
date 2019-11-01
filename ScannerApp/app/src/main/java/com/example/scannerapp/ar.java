package com.example.scannerapp;

import android.app.Activity;
import android.app.ActivityManager;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.RequiresApi;

import com.example.scannerapp.ui.ar.ArAppViewModel;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ar extends AppCompatActivity {
    private static final String TAG = ar.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ModelRenderable renderable ;
    private ArFragment arCoreFragment;

    @RequiresApi(api = VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkDevice((this))) {
            return;
        }

        setContentView(R.layout.ar);
        arCoreFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);

        if (Build.VERSION.SDK_INT >= VERSION_CODES.N) {
            ModelRenderable.builder()
                    .setSource(this, R.raw.model)
                    .build()
                    .thenAccept(renderable -> renderable  = renderable)
                    .exceptionally(
                            throwable -> {
                                Log.e(TAG, "Unable to load renderable");
                                return null;
                            });
        }

        arCoreFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (renderable == null) {
                        return;
                    }

                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arCoreFragment.getArSceneView().getScene());

                    TransformableNode transformableNode = new TransformableNode(arCoreFragment.getTransformationSystem());
                    transformableNode.setParent(anchorNode);
                    transformableNode.setRenderable(renderable);
                    transformableNode.select();
                });

    }

    public static boolean checkDevice(final Activity activity) {
        if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or higher");
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Requires OpenGL ES 3.0 or higher");
            activity.finish();
            return false;
        }
        return true;
    }
}

