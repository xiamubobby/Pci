package com.wonders.xlab.patient.login;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.wonders.xlab.patient.module.auth.login.LoginActivity;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Created by hua on 16/4/28.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginScreenTest {
    public IntentsTestRule<LoginActivity> mIntentsTestRule = new IntentsTestRule<LoginActivity>(LoginActivity.class);

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests significantly
     * more reliable.
     */
    @Before
    public void registerIdlingResource() {
    }
}
