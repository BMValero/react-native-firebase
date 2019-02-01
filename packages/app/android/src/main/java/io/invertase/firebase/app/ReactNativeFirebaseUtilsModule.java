package io.invertase.firebase.app;

/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import android.app.Activity;
import android.content.IntentSender;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.HashMap;
import java.util.Map;

import io.invertase.firebase.common.ReactNativeFirebaseModule;
import io.invertase.firebase.common.SharedUtils;

public class ReactNativeFirebaseUtilsModule extends ReactNativeFirebaseModule {
  private static final String TAG = "Utils";

  ReactNativeFirebaseUtilsModule(ReactApplicationContext reactContext) {
    super(reactContext, TAG);
  }

  @ReactMethod
  public void androidGetPlayServicesStatus(Promise promise) {
    promise.resolve(getPlayServicesStatusMap());
  }

  /**
   * Prompt the device user to update play services
   */
  @ReactMethod
  public void androidPromptForPlayServices() {
    int status = isGooglePlayServicesAvailable();

    if (
      status != ConnectionResult.SUCCESS &&
        GoogleApiAvailability.getInstance().isUserResolvableError(status)
    ) {
      Activity activity = getActivity();
      if (activity != null) {
        GoogleApiAvailability.getInstance()
          .getErrorDialog(activity, status, status)
          .show();
      }
    }
  }

  /**
   * Prompt the device user to update play services
   */
  @ReactMethod
  public void androidResolutionForPlayServices() {
    int status = isGooglePlayServicesAvailable();
    ConnectionResult connectionResult = new ConnectionResult(status);

    if (!connectionResult.isSuccess() && connectionResult.hasResolution()) {
      Activity activity = getActivity();
      if (activity != null) {
        try {
          connectionResult.startResolutionForResult(activity, status);
        } catch (IntentSender.SendIntentException error) {
          Log.d(TAG, "resolutionForPlayServices", error);
        }
      }
    }
  }

  /**
   * Prompt the device user to update Play Services
   */
  @ReactMethod
  public void androidMakePlayServicesAvailable() {
    int status = isGooglePlayServicesAvailable();

    if (status != ConnectionResult.SUCCESS) {
      Activity activity = getActivity();
      if (activity != null) {
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(activity);
      }
    }
  }

  private int isGooglePlayServicesAvailable() {
    GoogleApiAvailability gapi = GoogleApiAvailability.getInstance();
    return gapi.isGooglePlayServicesAvailable(getContext());
  }

  private WritableMap getPlayServicesStatusMap() {
    WritableMap result = Arguments.createMap();
    GoogleApiAvailability gapi = GoogleApiAvailability.getInstance();

    int status = gapi.isGooglePlayServicesAvailable(getContext());
    result.putInt("status", status);

    if (status == ConnectionResult.SUCCESS) {
      result.putBoolean("isAvailable", true);
    } else {
      result.putBoolean("isAvailable", false);
      result.putString("error", gapi.getErrorString(status));
      result.putBoolean("isUserResolvableError", gapi.isUserResolvableError(status));
      result.putBoolean("hasResolution", new ConnectionResult(status).hasResolution());
    }

    return result;
  }

  @Override
  public Map<String, Object> getConstants() {
    Map<String, Object> constants = new HashMap<>();
    constants.put("androidPlayServices", getPlayServicesStatusMap());
    constants.put("isFirebaseTestLab", SharedUtils.isFirebaseTestLab());
    return constants;
  }
}
