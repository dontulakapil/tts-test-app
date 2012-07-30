#include <string.h>
#include <stdio.h>
#include <jni.h>
JNIEnv* env1;

jstring Java_com_app_MyTextToSpeech_MyTextToSpeech_getJniString( JNIEnv* env, jobject obj){

env1=env;
    jstring jstr = (*env1)->NewStringUTF(env1, "This comes from jni.");
    jclass clazz = (*env1)->FindClass(env1, "com/app/MyTextToSpeech/MyTextToSpeech");
    jmethodID messageMe = (*env1)->GetMethodID(env1, clazz, "messageMe", "(Ljava/lang/String;)Ljava/lang/String;");
    jobject result = (*env1)->CallObjectMethod(env1, obj, messageMe, jstr);

    const char* str = (*env1)->GetStringUTFChars(env1,(jstring) result, NULL); // should be released but what a heck, it's a tutorial :)
    printf("%s\n", str);

    return (*env1)->NewStringUTF(env1, str);
}
