package com.example.scannerapp;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.scannerapp.ui.ar.ArAppFragment;
import com.example.scannerapp.ui.home.ScanCodeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class MainActivityTest {

    //Create new activity instance
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    private MainActivity mainActivity = null;

    //Monitor is used to keep track of activities that are opened within activities
    // (e.g. ScanCodeActivity is opened from MainActivity
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ScanCodeActivity.class.getName(),
                                                                                null, false);

    //Instantiate activity before every test
    @Before
    public void setUp() throws Exception {
        mainActivity = activityRule.getActivity();
    }

    //Check that some of the UI's are generated
    @Test
    public void checkToolbarExists() {
        View view = mainActivity.findViewById(R.id.toolbar);
        assertNotNull(view);
    }

    @Test
    public void checkDrawerExists() {
        View view = mainActivity.findViewById(R.id.drawer_layout);
        assertNotNull(view);

    }

    @Test
    public void checkNavExists() {

        View nav_view = mainActivity.findViewById(R.id.nav_view);
        assertNotNull(nav_view);

        View nav_host_fragment = mainActivity.findViewById(R.id.nav_host_fragment);
        assertNotNull(nav_host_fragment);

    }

    //Check that floating action button takes user to scanner
    @Test
    public void testScanningLaunch() {

        //Make sure button exists
        FloatingActionButton fab =  mainActivity.findViewById(R.id.fab);
        assertNotNull(fab);

        //Click button and check if the scanning activity is launched
        onView(withId(R.id.fab)).perform(click());
        //If nothing happens within 5 seconds return null
        Activity scanCode = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(scanCode);
    }

    /*
    @Test
    public void testAr() {
        //Camera test = new Camera();

        DrawerLayout dlContainer = mainActivity.findViewById(R.id.drawer_layout);
        //test.rotateX(100);
        assertNotNull(dlContainer);

        NavController mockNavController = mock(NavController.class);

        FragmentScenario<ArAppFragment> arScenario = FragmentScenario.launchInContainer(ArAppFragment.class);

        arScenario.onFragment(fragment -> Navigation.setViewNavController(fragment.requireView(), mockNavController));

        onView(ViewMatchers.withId(R.id.nav_ar)).perform(ViewActions.click());
        verify(mockNavController).navigate(R.id.main_fragment);
        //View view = mainActivity.findViewById(R.id.nav_ar);
        //assertNotNull(view);

    }
    */

    //Reset activity to null after every test
    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}