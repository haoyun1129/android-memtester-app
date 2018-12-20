#include <jni.h>
#include <string>
#include <android/log.h>

#define TAG "native-lib"
extern "C" {
    int main(int argc, char **argv);
}

jmethodID jMtdOnTestStart;
jmethodID jMtdOnTestProgress;
JNIEnv *g_env;
jobject g_instance;

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    __android_log_print(ANDROID_LOG_VERBOSE, TAG, "JNI_OnLoad");
    JNIEnv* env;
    if (vm->GetEnv((void**)&env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR; // JNI version not supported.
    }
    jclass clz = env->FindClass("com/haoyun/memtester/MemTester");
    jMtdOnTestStart = env->GetMethodID(clz, "onTestStart", "(ILjava/lang/String;)V");
    jMtdOnTestProgress = env->GetMethodID(clz, "onTestProgress", "(II)V");
    return JNI_VERSION_1_6;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_haoyun_memtester_MemTester_native_1start(JNIEnv *env, jobject instance) {
    g_env = env;
    g_instance = instance;
    char* argv[] = {"./memtester", "4M", "1"};
    main(3, argv);
}

extern "C"
void onTestStart(int index, char* name) {
    __android_log_print(ANDROID_LOG_VERBOSE, TAG, "onTestStart: %d, %s", index, name);
    jint javaIndex= index;
    jstring javaName = g_env->NewStringUTF(name);
    g_env->CallVoidMethod(g_instance, jMtdOnTestStart, javaIndex, javaName);
}

extern "C"
void onTestProgress(int index, int progress) {
    __android_log_print(ANDROID_LOG_VERBOSE, TAG, "onTestProgress: %d, %d", index, progress);
    jint javaIndex= index;
    jint javaProgress = progress;
    g_env->CallVoidMethod(g_instance, jMtdOnTestProgress, javaIndex, javaProgress);
}
