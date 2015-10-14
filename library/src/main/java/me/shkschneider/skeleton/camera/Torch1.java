package me.shkschneider.skeleton.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import me.shkschneider.skeleton.helper.LogHelper;

@SuppressWarnings("deprecation")
public class Torch1 extends ITorch {

    private static final String FLAG_ON = Camera.Parameters.FLASH_MODE_TORCH;
    private static final String FLAG_OFF = Camera.Parameters.FLASH_MODE_OFF;

    private Camera mCamera;

    public Torch1(final Boolean asap) {
        try {
            mCamera = Camera.open();
            mCamera.setPreviewTexture(new SurfaceTexture(1));
            if (asap) {
                on();
            }
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
    }

    public boolean on() {
        if (mCamera == null) {
            LogHelper.warning("Camera was NULL");
            return false;
        }
        final Camera.Parameters parameters = mCamera.getParameters();
        if (! parameters.getSupportedFlashModes().contains(FLAG_ON)) {
            LogHelper.warning("Camera does not support TORCH mode");
            return false;
        }
        parameters.setFlashMode(FLAG_ON);
        try {
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
        return true;
    }

    public boolean off() {
        if (mCamera == null) {
            LogHelper.warning("Camera was NULL");
            return false;
        }

        final Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFlashMode(FLAG_OFF);
        try {
            mCamera.setParameters(parameters);
            mCamera.stopPreview();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
        return true;
    }

    public boolean status() {
        if (mCamera == null) {
            return false;
        }
        return (mCamera.getParameters().getFlashMode().equals(FLAG_ON));
    }

    public void release() {
        if (mCamera == null) {
            return ;
        }

        try {
            mCamera.release();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
        mCamera = null;
    }

}
