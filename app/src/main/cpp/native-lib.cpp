#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_spotifyplayer_utils_BaseApp_clientId(
        JNIEnv *env,
        jobject /* this */) {
    std::string clientId= "50ff9f0569f0439087bbc05acb38db43";
    return env->NewStringUTF(clientId.c_str());
}

extern "C"
jstring
Java_com_spotifyplayer_utils_BaseApp_clientSecret(
        JNIEnv *env,
        jobject /* this */) {
    std::string clientSecret= "785e208d5eef44a28941bf47146c0873";
    return env->NewStringUTF(clientSecret.c_str());
}
