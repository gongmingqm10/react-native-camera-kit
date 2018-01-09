package com.wix.RNCameraKit.camera.commands;

import android.content.Context;
import android.hardware.Camera;

import com.facebook.react.bridge.Promise;

import com.wix.RNCameraKit.camera.CameraViewManager;
import com.wix.RNCameraKit.SaveImageTask;

public class Capture implements Command {

    private final Context context;
    private boolean saveToCameraRoll;
    private final int quality;

    public Capture(Context context, boolean saveToCameraRoll) {
        this(context, saveToCameraRoll, 100);
    }

    public Capture(Context context, boolean saveToCameraRoll, int quality) {
        this.context = context;
        this.saveToCameraRoll = saveToCameraRoll;
        this.quality = quality;
    }

    @Override
    public void execute(final Promise promise) {
        try {
            tryTakePicture(promise);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tryTakePicture(final Promise promise) throws Exception {
        CameraViewManager.getCamera().takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                camera.stopPreview();
                new SaveImageTask(context, promise, saveToCameraRoll, quality).execute(data);
            }
        });
    }
}
