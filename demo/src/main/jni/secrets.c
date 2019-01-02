#include <jni.h>

// Use this to securely store keys

JNIEXPORT jstring JNICALL
Java_me_shkschneider_skeleton_demo_main_MainActivity_secretKey(JNIEnv *env, jobject instance) {
  return (*env)->  NewStringUTF(env, "Skeleton");
}
