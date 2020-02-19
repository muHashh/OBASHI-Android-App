package com.example.scannerapp.ui.ar;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.scannerapp.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.Plane;
import com.google.ar.core.Frame;
import com.google.ar.core.Pose;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Collection;

public class ArActivity extends AppCompatActivity {
    private static final String TAG = ArActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    private static final int REQUEST_CODE = 1;

    private ModelRenderable MRenderable;
    private ArFragment arFragment;
    private boolean isModelPlaced;
    private String type;

    @RequiresApi(api = VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkDevice((this))) {
            return;
        }

        verifyPermissions();

        Intent i = getIntent();
        type = i.getStringExtra("NODE");

        if(type == null) {
            type = "null";
        }


    }

    private void onUpdate(FrameTime frameTime) {

        if (isModelPlaced) {
            return;
        }

        Frame frame = arFragment.getArSceneView().getArFrame();

        Collection<Plane> planes = frame.getUpdatedTrackables(Plane.class);



        for (Plane plane: planes) {
            if (plane.getTrackingState() == TrackingState.TRACKING) {
                Anchor anchor = plane.createAnchor(plane.getCenterPose());


                makeCube(anchor, new Vector3(0f, 0f, 0f), new Color(new Color(0, 1, 0, (float)0.5)));
                if(type.equals("prev") || type.equals("null")) {
                    makePipe(anchor, new Vector3(0f, 0.5f, 0f),new Color(new Color(1, 0, 0, (float)0.5)));
                    makeCube(anchor, new Vector3(-1f, 0f, 0f), new Color(new Color(1, 0, 0, (float)0.5)));
                }
                if(type.equals("next") || type.equals("null")) {
                    makePipe(anchor, new Vector3(0f, -0.5f, 0f),new Color(new Color(0, 0, 1, (float)0.5)));
                    makeCube(anchor, new Vector3(1f, 0f, 0f), new Color(new Color(0, 0, 1, (float)0.5)));
                }

                break;
            }
        }
    }

    private void makeCube(Anchor anchor, Vector3 pos, Color color) {

        isModelPlaced = true;

        MaterialFactory
                .makeTransparentWithColor(this, color)
                .thenAccept(material -> {

                    ModelRenderable cubeRenderable = ShapeFactory.makeCube(new Vector3(0.2f, 0.2f, 0.2f),
                            pos, material);

                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setRenderable(cubeRenderable);
                    arFragment.getArSceneView().getScene().addChild(anchorNode);
                });
    }

    private void makePipe(Anchor anchor, Vector3 pos, Color color) {

        isModelPlaced = true;

        MaterialFactory
                .makeTransparentWithColor(this, color)
                .thenAccept(material -> {

                    ModelRenderable pipeRenderable = ShapeFactory.makeCylinder((float)0.01, (float)1, pos,  material);
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
                    node.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 90));
                    node.setRenderable(pipeRenderable);
                    node.setParent(anchorNode);

                    arFragment.getArSceneView().getScene().addChild(anchorNode);
                });
    }

    private void verifyPermissions() {
        Log.d(TAG, "Verifying permissions: Asking user for camera permission.");
        String[] permissions = {Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            setContentView(R.layout.ar);

            arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
            arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdate);

        }
        else {
            ActivityCompat.requestPermissions(ArActivity.this, permissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
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

