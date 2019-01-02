LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := secrets
LOCAL_SRC_FILES := secrets.c

include $(BUILD_SHARED_LIBRARY)
