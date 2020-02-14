package com.example.scannerapp;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.Instrumentation;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.scannerapp.ui.ar.ArActivity;
import com.example.scannerapp.ui.ar.ArAppFragment;
import com.example.scannerapp.ui.home.ScanCodeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.sceneform.Camera;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class ArAppFragmentTest {
    @Rule
    public ActivityTestRule<ArActivity> activityRule = new ActivityTestRule<>(ArActivity.class);
    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    private ArActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = activityRule.getActivity();
    }

    @Test
    public void testArLaunch() {
        //Camera cam = new Camera();
        FrameLayout frContainer = mActivity.findViewById(R.id.main_fragment);

        //cam.rotateX(100);
        //cam.rotateY(360);
        //cam.rotateZ(180);
        assertNotNull(frContainer);
        ArAppFragment fragment = new ArAppFragment();

        mActivity.getSupportFragmentManager().beginTransaction().add(frContainer.getId(), fragment).commitAllowingStateLoss();

        getInstrumentation().waitForIdleSync();

        int[] test = new int[2];

        test[0] = R.id.main_fragment;
        test[1] = R.id.sceneform_ar_scene_view;

        for (int i: test) {
            if (fragment.getView().findViewById(i) != null) {
                assert(true);
            }
        }
        assert(false);
        //View view = fragment.getView().findViewById(R.id.ar);
        //assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}

