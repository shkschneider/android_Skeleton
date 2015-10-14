package me.shkschneider.skeleton.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.view.Surface;

import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LogHelper;

// <https://github.com/pinguo-yuyidong/Camera2>
@TargetApi(AndroidHelper.API_21)
public class Torch2 extends ITorch {

    private static final String CAMERA_ID = "0";

    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private CameraCaptureSession mCameraCaptureSession;
    private SurfaceTexture mSurfaceTexture;
    private int mRequestedFlashMode;

    @RequiresPermission(Manifest.permission.CAMERA)
    @SuppressWarnings("ConstantConditions")
    public Torch2(final boolean asap) throws SecurityException {
        mRequestedFlashMode = (asap ? CameraMetadata.FLASH_MODE_TORCH : CameraMetadata.FLASH_MODE_OFF);
        try {
            final CameraManager cameraManager = (CameraManager) ApplicationHelper.context().getSystemService(Context.CAMERA_SERVICE);
            final CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(CAMERA_ID);
            if (! cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                LogHelper.error("Camera does not support TORCH mode");
                return ;
            }
            cameraManager.openCamera(CAMERA_ID, new MyCameraDeviceStateCallback(), null);
        }
        catch (final SecurityException e) {
            LogHelper.error("SecurityException: Manifest.permissions.CAMERA");
            throw e;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
    }

    public boolean status() {
        if (mCameraCaptureSession == null) {
            return false;
        }
        if (mCaptureRequestBuilder == null) {
            return false;
        }
        final Integer flashMode = mCaptureRequestBuilder.get(CaptureRequest.FLASH_MODE);
        if (flashMode == null) {
            return false;
        }
        return (flashMode == CameraMetadata.FLASH_MODE_TORCH);
    }

    public boolean on() {
        mRequestedFlashMode = CameraMetadata.FLASH_MODE_TORCH;
        if (mCameraCaptureSession == null) {
            // CameraCaptureSession.StateCallback sometimes gets called after this point
            LogHelper.info("Not CaptureSession alive: triggering pending requested flash mode (assuming it will work)");
            return true;
        }
        try {
            mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, mRequestedFlashMode);
            mCameraCaptureSession.setRepeatingRequest(mCaptureRequestBuilder.build(), null, null);
            return true;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    public boolean off() {
        mRequestedFlashMode = CameraMetadata.FLASH_MODE_OFF;
        if (mCameraCaptureSession == null) {
            // CameraCaptureSession.StateCallback sometimes gets called after this point
            LogHelper.info("Not CaptureSession alive: triggering pending requested flash mode (assuming it will work)");
            return true;
        }
        try {
            mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, mRequestedFlashMode);
            mCameraCaptureSession.setRepeatingRequest(mCaptureRequestBuilder.build(), null, null);
            return true;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    private class MyCameraDeviceStateCallback extends CameraDevice.StateCallback {

        @Override
        public void onOpened(@NonNull final CameraDevice camera) {
            mCameraDevice = camera;
            try {
                mCaptureRequestBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AF_MODE_AUTO);
                mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, mRequestedFlashMode);
                mSurfaceTexture = new SurfaceTexture(1);
                final List<Surface> surfaces = new ArrayList<Surface>() {
                    {
                        add(new Surface(mSurfaceTexture));
                    }
                };
                mCaptureRequestBuilder.addTarget(surfaces.get(0));
                mCameraDevice.createCaptureSession(surfaces, new MyCameraCaptureSessionStateCallback(), null);
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
        }

        @Override
        public void onDisconnected(@NonNull final CameraDevice camera) {
            LogHelper.warning("Camera DISCONNECTED");
        }

        @Override
        public void onError(@NonNull final CameraDevice camera, int error) {
            LogHelper.warning("Camera ERROR: " + error);
        }

    }

    private class MyCameraCaptureSessionStateCallback extends CameraCaptureSession.StateCallback {

        @Override
        public void onConfigured(@NonNull final CameraCaptureSession cameraCaptureSession) {
            mCameraCaptureSession = cameraCaptureSession;
            LogHelper.debug("CaptureSession acquired");
            try {
                mCameraCaptureSession.setRepeatingRequest(mCaptureRequestBuilder.build(), null, null);
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
        }

        @Override
        public void onConfigureFailed(@NonNull final CameraCaptureSession cameraCaptureSession) {
            LogHelper.warning("Configuration FAILED");
        }

        @Override
        public void onReady(@NonNull final CameraCaptureSession cameraCaptureSession) {
            super.onReady(cameraCaptureSession);
            mCameraCaptureSession = cameraCaptureSession;
        }

    }

    public void release() {
        try {
            if (mCameraCaptureSession != null) {
                mCameraCaptureSession.stopRepeating();
                mCameraCaptureSession.close();
                mCameraCaptureSession = null;
            }
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (mSurfaceTexture != null) {
                mSurfaceTexture.release();
                mSurfaceTexture = null;
            }
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
    }

}
