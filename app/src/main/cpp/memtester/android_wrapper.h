//
// Created by kenny on 12/18/18.
//

#ifndef MEMTESTER_ANDROID_WRAPPER_H
#define MEMTESTER_ANDROID_WRAPPER_H

#include <android/log.h>

#define TAG "memtester"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)
#define printf LOGV
#define fprintf(file, ...) LOGE(__VA_ARGS__)
#define fflush
#define putchar
#define exit

void onTestStart(int index, char* name);

void onTestProgress(int index, float progress);

void onTestCompleted(int index, int status);

#endif //MEMTESTER_ANDROID_WRAPPER_H
