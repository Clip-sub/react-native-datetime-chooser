package com.clipsub.RNDateTimeChooser;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RNDateTimeChooserModule extends ReactContextBaseJavaModule {

  private static final String MODULE_NAME = "RNDateTimeChooser";
  private ReactContext reactContext;

  public RNDateTimeChooserModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return MODULE_NAME;
  }

  @ReactMethod
  public void show(@NonNull ReadableMap options, @NonNull final Callback cancelCallback, @NonNull final Callback pickedCallback) {
    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
    String titleText = options.getString("titleText");

    new SingleDateAndTimePickerDialog.Builder(this.reactContext)
        .title(TextUtils.isEmpty(titleText) ? "Noddier" : titleText)
        .mustBeOnFuture()
        .backgroundColor(Color.WHITE)
        .mainColor(Color.parseColor("#642580"))
        .listener(new SingleDateAndTimePickerDialog.Listener() {
          @Override
          public void onDateSelected(Date date) {
            String result = format.format(date);
            pickedCallback.invoke(result);
            cancelCallback.invoke(null);
          }
        }).display();
  }
}
