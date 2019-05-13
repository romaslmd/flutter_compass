package com.ancient.dev.flutter_compass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity implements SensorEventListener {
  public static final String CHANNEL = "com.ancient.dev.flutter_compass/compass";

  SensorManager sensorManager;
  Sensor accelerometer;
  Sensor magnetometer;

  EventChannel.EventSink stream;

  private float[] mGravity;
  private float[] mGeomagnetic;
  private double rad = 0.0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);

    new EventChannel(getFlutterView(), CHANNEL).setStreamHandler(
      new EventChannel.StreamHandler() {
        @Override
        public void onListen(Object args, final EventChannel.EventSink events) {
          stream = events;
        }

        @Override
        public void onCancel(Object args) {
          unregister();
        }
      }
    );
  }

  @SuppressLint("NewApi")
  @Override
  public void onSensorChanged(SensorEvent event) {
    try {
      if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        mGravity = event.values;
      if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        mGeomagnetic = event.values;

      if (mGravity != null && mGeomagnetic != null) {
        float R[] = new float[9];
        float I[] = new float[9];

        boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

        if (success && stream != null) {
          float[] orientation = new float[3];
          SensorManager.getOrientation(R, orientation);

          double azimuth = orientation[0] < 0 ?  2 * Math.PI + Math.abs(orientation[0]) : orientation[0];

          stream.success(azimuth);
        } else {
          throw new Exception("Failed to get rotation matrix!");
        }
      }
    } catch(Exception e) {}
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {}

  void unregister() {
    if(accelerometer != null) {
      sensorManager.unregisterListener(this, accelerometer);
    }

    if(magnetometer != null) {
      sensorManager.unregisterListener(this, magnetometer);
    }

    stream = null;
  }
}
