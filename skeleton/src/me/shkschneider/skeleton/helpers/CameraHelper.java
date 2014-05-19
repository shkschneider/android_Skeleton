package me.shkschneider.skeleton.helpers;

import android.hardware.Camera;

public class CameraHelper {

    public static final int NOT_FOUND = -1;

    public static int cameras() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        return Camera.getNumberOfCameras();
    }

    public static int backCameraId() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0 ; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return i;
            }
        }
        return CameraHelper.NOT_FOUND;
    }

    public static int frontCameraId() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0 ; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return i;
            }
        }
        return CameraHelper.NOT_FOUND;
    }

}
