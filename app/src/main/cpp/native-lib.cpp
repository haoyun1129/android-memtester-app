#include <jni.h>
#include <string>

extern "C" {
    int main(int argc, char **argv);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_haoyun_memtester_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    char* argv[] = {"./memtester", "10M", "1"};
    main(3, argv);
    return env->NewStringUTF(hello.c_str());
}
