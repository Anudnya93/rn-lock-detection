package com.rnlock;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import android.util.Log;

import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.KeyguardManager;

public class RNLockDetectionModule extends ReactContextBaseJavaModule {

   private final ReactApplicationContext reactContext;

   public RNLockDetectionModule(ReactApplicationContext reactContext) {
      super(reactContext);
      this.reactContext = reactContext;
      registerBroadcastReceiver();
   }

   @Override
   public String getName() {
      return "RNLockDetection";
   }

   @Nullable
   @Override
   public Map<String, Object> getConstants() {
      Map<String, Object> constants = new HashMap<>();


      IntentFilter filter = new IntentFilter();
      filter.addAction(Intent.ACTION_SCREEN_ON);
      filter.addAction(Intent.ACTION_SCREEN_OFF);
      
      return constants;
   }

   private void registerBroadcastReceiver() {
      
      IntentFilter filter = new IntentFilter();
      /** System Defined Broadcast */
      filter.addAction(Intent.ACTION_SCREEN_ON);
      filter.addAction(Intent.ACTION_SCREEN_OFF);

      BroadcastReceiver screenOnOffReceiver = new BroadcastReceiver() {
         @Override
         public void onReceive(Context context, Intent intent) {

            boolean isLock = isLockSync();
            sendEvent("phoneStatus", isLock);
         }
      };
      getReactApplicationContext().registerReceiver(screenOnOffReceiver, filter);
   }

   @ReactMethod(isBlockingSynchronousMethod = true)
   public boolean isLockSync(){

      KeyguardManager myKM = (KeyguardManager)getReactApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
      return myKM.inKeyguardRestrictedInputMode() ? true : false;
   }
   @ReactMethod
   public void isLock(Promise p) {p.resolve(isLockSync());}


   private void sendEvent(String eventName, @Nullable Object data) {
      
      this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, data);
   }

}