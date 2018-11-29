package com.clipsub.RNDateTimeChooser;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RNDateTimeChooserModule extends ReactContextBaseJavaModule {

  private static final String MODULE_NAME = "RNDateTimeChooser";
  private ReactContext reactContext;

  public RNDateTimeChooserModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return MODULE_NAME;
  }

  @ReactMethod
  public void show(@NonNull ReadableMap options, @NonNull final Callback callback) throws ParseException {
    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    String titleText = options.getString("titleText");
    String titleBgColor = options.getString("titleBgColor");

    SingleDateAndTimePickerDialog.Builder builder = new SingleDateAndTimePickerDialog.Builder(getCurrentActivity());

    builder.title(TextUtils.isEmpty(titleText) ? "" : titleText);
    builder.backgroundColor(Color.WHITE);
    builder.mainColor(Color.parseColor(titleBgColor != null ? titleBgColor : "#642580"));

    Date minDate = new Date();
    String minimumDate = options.getString("minimumDate");
    if (minimumDate != null) {
      minDate = format.parse(minimumDate);
    }
    builder.minDateRange(minDate);

    Date maxDate = new Date();
    String maximumDate = options.getString("maximumDate");
    if (maximumDate != null) {
      maxDate = format.parse(maximumDate);
    }
    builder.maxDateRange(maxDate);

    builder.listener(new SingleDateAndTimePickerDialog.Listener() {
      @Override
      public void onDateSelected(Date date) {
        String result = format.format(date);
        WritableMap map = Arguments.createMap();
        map.putString("type", "done");
        map.putString("selectedDate", result);
        callback.invoke(map);
      }
    }).display();
  }
}
