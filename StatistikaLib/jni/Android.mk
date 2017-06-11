LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libstatistika
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := statistika.c

include $(BUILD_SHARED_LIBRARY)
