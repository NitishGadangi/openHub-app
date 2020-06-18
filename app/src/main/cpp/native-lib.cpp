#include <jni.h>
#include <string>
#include <android/log.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_net_vidflix_Activity_SplashActivity_casdAA(JNIEnv *env, jobject thiz) {
    jclass native_context = env->GetObjectClass(thiz);

    // context.getPackageManager()
    jmethodID methodID_func = env->GetMethodID(native_context, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject package_manager  = env->CallObjectMethod(thiz,methodID_func);
    jclass pm_clazz = env->GetObjectClass(package_manager);

    //packageManager.getPackageInfo()
    jmethodID methodId_pm = env->GetMethodID(pm_clazz,"getPackageInfo", "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");

    //context.getPackageName()
    jmethodID methodID_packagename = env->GetMethodID(native_context,"getPackageName","()Ljava/lang/String;");
    jstring name_str = static_cast<jstring>(env->CallObjectMethod(thiz,methodID_packagename));
    jobject package_info = env->CallObjectMethod(package_manager,methodId_pm,name_str,64);
    jclass pi_clazz = env->GetObjectClass(package_info);

    //packageInfo.signatures
    jfieldID fieldID_signatures = env->GetFieldID(pi_clazz,"signatures","[Landroid/content/pm/Signature;");
    jobject signatur = env->GetObjectField(package_info,fieldID_signatures);
    jobjectArray  signatures = reinterpret_cast<jobjectArray>(signatur);

    //signatures[0]
    jobject signature = env->GetObjectArrayElement(signatures,0);
    jclass s_clazz = env->GetObjectClass(signature);

    //signatures[0].toCharString()
    jmethodID methodId_ts = env->GetMethodID(s_clazz,"toCharsString","()Ljava/lang/String;");
    jobject ts = env->CallObjectMethod(signature,methodId_ts);
    jstring  final=reinterpret_cast<jstring>(ts);
    const char *nativeString = env->GetStringUTFChars(final, 0);

    if (strcmp(nativeString,"308202d1308201b9a0030201020204363af5b6300d06092a864886f70d01010b05003018311630140603550403130d416e64726f6964204c6f7665723020170d3230303530343137343433365a180f32323230303331373137343433365a3018311630140603550403130d416e64726f6964204c6f76657230820122300d06092a864886f70d01010105000382010f003082010a02820101009dbcb91e0af09d3764d5e366a5312ef6b2e52a511da0e7309974d6e3777213513ee43033ad8508fd63b32053b691efa0cbd270574c590d6ae0a7a185a94d2b9fc2f26f0f30f231e8731fc64d82eb1c503ffb140281f94e520341f0b3a3c605d10d32d2c7a7e27a7d2bdfd5070805f1a45d12982399ecc00f2eb37b27217fa5d164297218474e092ff6fe119765716db8eb759554a86907c7882f1bd52c44fcf02da5bd4dc62396ba0221d71107ff9e4b2f401272e27079ea4b52272f5a996f0e16dcbd70fd585212ea70c83549f0eea437680751dcff0cd10258c85007d489432260b308f22ef04cf77c58539e4abf6c9a95a028890bfed62afacd7cbff897f50203010001a321301f301d0603551d0e04160414401b2ec366bfbd5a6f0b6839d4a0a594b5cedb52300d06092a864886f70d01010b050003820101000f84db4ab499860490cfcac132a37423ff10d1f38ef620d8c94c8f25e9b433520f6e859ce7338e1d64c673f89625cf19591aa3d2e302b7c1e79053820dba63d8224934020b6d5a84a5f6b17aff651e8d0c87e18bc6467f385b414186ec7a61ab619d893edba948e348d2fbd8f567a74c3dcb0191032066c6a02bac2a476f9cb9acc59f1e7be48b725c45eeb49944a4b8116282bda24b01465162aa45015770ce89af8007971c2ef3a2e170824fab334dd963459cfc3381d2fc34b84d4a644d8fb180b6c79c753b564f1646b791743d207a278af5d8bc28d8e3c9ccbc1ad9b33f1b88baa9c00d76f5af572c3353c631b49d4da1ddc76902ffaf7929799b407021")==0)
        return final;
    else
    {
            abort();
    }
}

extern "C"
JNIEXPORT jstring JNICALL
Java_net_vidflix_task_DecoderTask_mali(JNIEnv *env, jobject instance) {
    std::string ap = "_ZN10__caaabiv119__start_handles";
    return env->NewStringUTF(ap.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_net_vidflix_task_DecoderTask_toAo(JNIEnv *env, jobject instance) {
    std::string az = "O+poSr5nJhAy98ClpdgeZMTXklYHX0pS4ayn1UGJ42zRK/eKp+lt+QAwew2dUEPuF9kZwZjG1NWURnEfrZNDPsSv4fuDgvokWmIWx05mcYBKuG9Go7tkXbC4cg7cEZNAUUuoynx61W4w/cr4fyIOYg==";
    return env->NewStringUTF(az.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_net_vidflix_Model_Auth_ApiTokenModel_sec(JNIEnv *env, jobject thiz) {
    std::string ap = "PvmPdnGJkMK6d9qfQUBA720pidraV4/07MxN4fFHDSPWxHZsS2Z+t3FZnL+MmjRALDbfgSju9IenwXuy0rDKhg==";
    return env->NewStringUTF(ap.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_net_vidflix_Model_Auth_ApiTokenModel_salt(JNIEnv *env, jobject thiz) {
    std::string ap = "gV5Vnn8GahHiFHASXz+Ihh4SjwK1ecrU147TGDKL2nc=";
    return env->NewStringUTF(ap.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_net_vidflix_Model_Auth_ApiTokenModel_varSZION(JNIEnv *env, jobject thiz) {
    std::string ap = "ysUZRvNen3MX0Q/kPwwjkDaE/Rg2LUFIsbtL5JSdw68=";
    return env->NewStringUTF(ap.c_str());
}