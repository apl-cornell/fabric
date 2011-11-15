#ifdef __linux__
#endif

#ifdef __sun__
/* Solaris */
#endif

#include <stdio.h>
#include <stdlib.h>
#include <pwd.h>
#include <grp.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#include <jni.h>
#include "jif_runtime_FileSystem.h"
#include "jif_runtime_Runtime.h"

JNIEXPORT jstring JNICALL Java_jif_runtime_Runtime_currentUserImpl(JNIEnv* env, 
	jclass clazz)
{
    jstring juser;
    uid_t userid = getuid();
    struct passwd* pwent = getpwuid(userid);
    
    juser = env->NewStringUTF(pwent->pw_name);
    return juser;
}

/*
 * Class:     jif_runtime_FileSystem
 * Method:    setPolicy
 * Signature: (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_jif_runtime_FileSystem_setPolicy
  (JNIEnv *env, jclass clazz, jstring jfile, jstring jowner, jobjectArray jreaders)
{
    struct stat buf;
    const char* file = env->GetStringUTFChars(jfile, 0);

    if (stat(file, &buf) != 0) {
    	// failed, the file does not exist
        return;
    }

    mode_t mode = buf.st_mode;
	
    if (jowner == NULL)
	buf.st_mode |= S_IROTH;
    else {
	//set the owner
	const char* owner_name = env->GetStringUTFChars(jowner, 0);
	struct passwd* pwent = getpwnam(owner_name);
	buf.st_uid = pwent->pw_uid;
		
	if (jreaders == NULL) {
	    //everyone can read the file
	    buf.st_mode |= S_IROTH;
	}	
	else {
	    //assume the first reader is the group
	    jstring jgrp_name = (jstring) env->GetObjectArrayElement(jreaders, 0);
	    const char* grp_name = env->GetStringUTFChars(jgrp_name, 0);
	    struct group* grent = getgrnam(grp_name);
	    if (grent != NULL) {
		buf.st_gid = grent->gr_gid;
		buf.st_mode |= S_IRGRP;
	    }
	    env->DeleteLocalRef(jgrp_name);	    
	}
    }
}		

/*
 * Class:     jif_runtime_FileSystem
 * Method:    readers
 * Signature: (Ljava/lang/String;)[Ljava/lang/String;
 */
JNIEXPORT jobjectArray JNICALL Java_jif_runtime_FileSystem_readers
  (JNIEnv *env, jclass clazz, jstring jfile)
{
    jobjectArray readers = NULL;	
    jclass string_clazz = env->FindClass("java/lang/String");
    struct stat buf;

    const char* file = env->GetStringUTFChars(jfile, 0);

    if (stat(file, &buf) != 0) {
    	// failed
        // printf("Error in jif_runtime_FileSystem_readers");
        return NULL;
    }
	
    mode_t mode = buf.st_mode;

    if (mode & S_IROTH) {
        //add Everyone
        readers = env->NewObjectArray(1, string_clazz, NULL);
        jstring everyone = env->NewStringUTF("Everyone");
        env->SetObjectArrayElement(readers, 0, everyone);
        env->DeleteLocalRef(everyone);
    }
    else {    
        if (mode & S_IRGRP) {	
            readers = env->NewObjectArray(2, string_clazz, NULL);
            gid_t gid = buf.st_gid;
            struct group* grent = getgrgid(gid);
            //TODO: error process
            jstring grp_name = env->NewStringUTF(grent->gr_name);
            env->SetObjectArrayElement(readers, 1, grp_name);
            env->DeleteLocalRef(grp_name);
        }
        else {
            readers = env->NewObjectArray(1, string_clazz, NULL);
        }

        //add owner
                    
        jstring owner = Java_jif_runtime_FileSystem_owner(env, clazz, jfile);
        env->SetObjectArrayElement(readers, 0, owner);
        env->DeleteLocalRef(owner);
    }

    return readers;
}

/*
 * Class:     jif_runtime_FileSystem
 * Method:    owner
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_jif_runtime_FileSystem_owner
  (JNIEnv *env, jclass clazz, jstring jfile)
{
    struct stat buf;

    const char* file = env->GetStringUTFChars(jfile, 0);

    if (stat(file, &buf) != 0) {
    	// failed
        // printf("Error in jif_runtime_FileSystem_owner");
        return NULL;
    }

    uid_t uid = buf.st_uid;

    struct passwd* pwent = getpwuid(uid);

    if (pwent == NULL) {
        printf("Error: user %d is not found.", uid);
        exit(1);
    }

    jstring owner = env->NewStringUTF(pwent->pw_name);
    
    env->ReleaseStringUTFChars(jfile, file);
    return owner;
}



/*
 * Class:     jif_policy_Passwd
 * Method:    users
 * Signature: ()[Ljif/policy/Principal;
 */
JNIEXPORT jobjectArray JNICALL Java_jif_policy_Passwd_users
  (JNIEnv *env, jclass clazz) 
{
    jint usernum = 0;
    struct passwd* pEntry;
    char buf[1024];

    setpwent();
    while ((pEntry = getpwent()) != NULL) {
        usernum++;
        //printf("%d: %ld", usernum, pEntry);
    }
    endpwent();
    
    jclass uclazz = env->FindClass("jif/policy/User");
    jmethodID constructor = env->GetMethodID(uclazz, "<init>", 
	"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    jobjectArray userArray = env->NewObjectArray(usernum, uclazz, NULL);
    
    setpwent();
    
    for (int i=0; i < usernum; i++) {
        pEntry = getpwent();    
        printf("### user %d: %s\n", i, pEntry->pw_name);
        jstring name = env->NewStringUTF(pEntry->pw_name);
        sprintf(buf, "%d", pEntry->pw_uid);
        jstring uid = env->NewStringUTF(buf);
        sprintf(buf, "%d", pEntry->pw_gid);
        jstring gid = env->NewStringUTF(buf);
        jobject user = env->NewObject(uclazz, constructor, name, uid, gid);
        env->SetObjectArrayElement(userArray, i, user);
        env->DeleteLocalRef(name);
        env->DeleteLocalRef(uid);
        env->DeleteLocalRef(gid);
        env->DeleteLocalRef(user);
    }

    endpwent();

    return userArray;
}

/*
 * Class:     jif_policy_Passwd
 * Method:    groups
 * Signature: ()[Ljif/policy/Group;
 */
JNIEXPORT jobjectArray JNICALL Java_jif_policy_Passwd_groups
  (JNIEnv *env, jclass clazz)
{
    jint grpnum = 0;
    struct group* pEntry;
    char buf[1024];

    setgrent();
    while (getgrent() != NULL) {
	grpnum++;
    }
    endgrent();

    jclass uclazz = env->FindClass("jif/policy/Group");
    jmethodID constructor = env->GetMethodID(uclazz, "<init>", 
	"(Ljava/lang/String;Ljava/lang/String;)V");
    jmethodID addMember = env->GetMethodID(uclazz, "addMember",
	"(Ljava/lang/String;)V");
    jobjectArray grpArray = env->NewObjectArray(grpnum, uclazz, NULL);
    
    setgrent();
    for (int i=0; i < grpnum; i++) {
        pEntry = getgrent();
        printf("### group %d: %s\n", i, pEntry->gr_name);
        jstring name = env->NewStringUTF(pEntry->gr_name);
        sprintf(buf, "%d", pEntry->gr_gid);
        jstring gid = env->NewStringUTF(buf);
        jobject group = env->NewObject(uclazz, constructor, name, gid);
        char** members = pEntry->gr_mem;
        while (*members != NULL) {
            jstring member = env->NewStringUTF(*members);
            env->CallVoidMethod(group, addMember, member);
            env->DeleteLocalRef(member);
            members++;
        }
        env->SetObjectArrayElement(grpArray, i, group);
        env->DeleteLocalRef(name);
        env->DeleteLocalRef(gid);
        env->DeleteLocalRef(group);
    }
    endgrent();

    return grpArray;
}
