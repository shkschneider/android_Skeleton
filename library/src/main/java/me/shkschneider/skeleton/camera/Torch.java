package me.shkschneider.skeleton.camera;

import android.Manifest;
import android.support.annotation.RequiresPermission;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.FeaturesHelper;
import me.shkschneider.skeleton.helper.LogHelper;

public class Torch {

    private static ITorch TORCH;
    private static Boolean ASAP;

    public static boolean available() {
        return FeaturesHelper.feature(FeaturesHelper.FEATURE_CAMERA_FLASH);
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    private static ITorch get() throws SecurityException {
        if (TORCH == null) {
            if (AndroidHelper.api() < AndroidHelper.API_21) {
                LogHelper.info("Using Camera1 API");
                TORCH = new Torch1(ASAP);
            }
            else {
                LogHelper.info("Using Camera2 API");
                TORCH = new Torch2(ASAP);
            }
        }
        return TORCH;
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public Torch(final Boolean asap) throws SecurityException {
        ASAP = asap;
        get(); // warmUp
    }

    public boolean status() {
        if (TORCH == null) {
            return false;
        }
        return TORCH.status();
    }

    public boolean on() {
        if (TORCH == null) {
            return false;
        }
        return TORCH.on();
    }

    public boolean off() {
        if (TORCH == null) {
            return false;
        }
        return TORCH.off();
    }

    public void release() {
        if (TORCH == null) {
            return ;
        }
        TORCH.release();
        TORCH = null;
    }

}
