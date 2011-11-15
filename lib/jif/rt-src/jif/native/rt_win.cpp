#include <Windows.h>
#include <malloc.h>
#include <stdio.h>
#include <winnt.h>
#include <aclapi.h>
#include <tchar.h>
#include <string.h>
#include <lmaccess.h>
#include <lmapibuf.h>

#include <jni.h>
#include "jif_runtime_FileSystem.h"
#include "jif_runtime_Runtime.h"

#include "winac.h"

JNIEXPORT jstring JNICALL Java_jif_runtime_Runtime_currentUserImpl(
	JNIEnv *env, jclass clazz) 
{
    jstring user;
    Principal* pr = GetCurrentUser();

    if (pr != NULL) {
	LPWSTR prName = pr->FullName();
	user = env->NewString((jchar*)prName, wcslen(prName));
	delete pr;
    }

    return user;
}    

/*
 * Class:     jif_runtime_FileSystem
 * Method:    readers
 * Signature: (Ljava/lang/String;)[Ljava/lang/String;
 */
JNIEXPORT jobjectArray JNICALL Java_jif_runtime_FileSystem_readers
  (JNIEnv *env, jclass clazz, jstring fname)
{
    LPCSTR f = env->GetStringUTFChars(fname, 0);
    DWORD num = 0;
    Principal** readers = NULL;
    jobjectArray newArr = NULL;
    int err;
    
    try {
	err = GetFileReaders(f, &num, (LPBYTE*) &readers);
	if (err != ERROR_SUCCESS) {
	    throw "GetFileReaders Error";
	}
	
	jint jnum = num;
	
	if (DEBUG) printf("reader number: %ld\n", jnum);
	
	jclass str_class = env->FindClass("java/lang/String");
	newArr = env->NewObjectArray(jnum, str_class, NULL);

	for (DWORD i=0; i<num; i++) {
	    if (DEBUG) printf("reader %d: %s\n", i+1, readers[i]->FullName());
	    jsize size = wcslen(readers[i]->FullName());
	    jstring pname = env->NewString((const jchar*) readers[i]->FullName(), size);
	    env->SetObjectArrayElement(newArr, i, pname);
	    env->DeleteLocalRef(pname);
	    delete readers[i];
	}
    }
    catch (LPTSTR msg) {
        _tprintf("Error in jif_runtime_FileSystem_readers: %s\n", msg);
    }
    catch (...) { 
        printf("Error in jif_runtime_FileSystem_readers: %d\n", GetLastError());
    }

    if (readers!=NULL)
	free(readers);

    return newArr;
}

/*
 * Class:     jif_runtime_FileSystem
 * Method:    owner
 * Signature: (Ljava/lang/String;)Ljif/lang/Principal;
 */
JNIEXPORT jstring JNICALL Java_jif_runtime_FileSystem_owner
  (JNIEnv *env, jclass clazz, jstring fname)
{
    LPCSTR str = env->GetStringUTFChars(fname, 0);
    
    Principal* owner = GetFileOwner(str);       
    LPWSTR prName = owner->FullName();
    jstring ret = env->NewString((const jchar*) prName, wcslen(prName));
    free(owner);   
    env->ReleaseStringUTFChars(fname, str);

    return ret;
}

/*
 * Class:     jif_runtime_FileSystem
 * Method:    setPolicy
 * Signature: (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_jif_runtime_FileSystem_setPolicy
  (JNIEnv *env, jclass jc, jstring fname, jstring owner, jobjectArray readers)
{
    LPCSTR file = env->GetStringUTFChars(fname, 0);
    LPCSTR ownerName = env->GetStringUTFChars(owner, 0);
    jint size = env->GetArrayLength(readers);

    LPCSTR* readerName = (LPCSTR*) malloc(size * sizeof(char *));
    
    for (int i=0; i < size; i++) {
	jstring j_str = (jstring) env->GetObjectArrayElement(readers, i);
	readerName[i] = env->GetStringUTFChars(j_str, 0);
    }

    if (!SetSecurityInfo(file, ownerName, readerName, size))
	printf("Setting security info error: %d\n", GetLastError());
    else 
	printf("Setting security info succeed.\n");

    free(readerName);
}

/*
 * Class:     jif_policy_Passwd
 * Method:    users
 * Signature: ()[Ljif/policy/User;
 */
JNIEXPORT jobjectArray JNICALL Java_jif_policy_Passwd_users
  (JNIEnv *env, jclass clazz) 
{
    jint usernum = 0;

    DWORD resume = 0;
    DWORD num_entries = 0;
    DWORD total = 0;
    USER_INFO_0* buf = NULL;
    jobjectArray userArray = NULL;

    if (NetUserEnum(NULL, 0, FILTER_NORMAL_ACCOUNT, (PBYTE*)&buf, MAX_PREFERRED_LENGTH,
	&num_entries, &total, &resume) == ERROR_SUCCESS) {
	int i;

	jint usernum = num_entries;

	jclass uclazz = env->FindClass("jif/policy/User");
	jmethodID constructor = env->GetMethodID(uclazz, "<init>", "(Ljava/lang/String;)V");
	jmethodID joinGroup = env->GetMethodID(uclazz, "joinGroup", "(Ljava/lang/String;)V");
	userArray = env->NewObjectArray(num_entries, uclazz, NULL);

	for (i=0; i < usernum; i++) {
	    
	    jstring name = env->NewString((jchar*)buf[i].usri0_name, 
		                          wcslen(buf[i].usri0_name));
	    jobject user = env->NewObject(uclazz, constructor, name);
	    //get groups
	    LOCALGROUP_USERS_INFO_0* grpbuf = NULL;
	    DWORD ent_read = 0;
	    DWORD ent_left = 100;
	    if (NetUserGetLocalGroups(NULL, buf[i].usri0_name, 0, 0, (LPBYTE*) &grpbuf,       
				 MAX_PREFERRED_LENGTH, &ent_read, &ent_left) == ERROR_SUCCESS) 
	    {
		for (DWORD j=0; j<ent_read; j++) {
		    jstring grp_name = env->NewString((const jchar*) grpbuf[j].lgrui0_name, 
			wcslen(grpbuf[j].lgrui0_name));
		    env->CallVoidMethod(user, joinGroup, grp_name);
		    env->DeleteLocalRef(grp_name);
		}
		NetApiBufferFree(grpbuf);
	    }	
	    else {
		printf("get group error: %d\n\n", GetLastError());
		exit(1);
	    }
		
	    env->SetObjectArrayElement(userArray, i, user);
	    env->DeleteLocalRef(name);
	    env->DeleteLocalRef(user);

	}
	NetApiBufferFree(buf);
    }
    else {
	printf("error: %d\n\n", GetLastError());
	exit(1);
    }
    
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
    DWORD resume = 0;
    DWORD num_entries = 0;
    DWORD total = 0;
    LOCALGROUP_INFO_0* buf = NULL;
    jobjectArray grpArray = NULL;

    if (NetLocalGroupEnum(NULL, 0, (PBYTE*)&buf, MAX_PREFERRED_LENGTH,
	&num_entries, &total, &resume) == ERROR_SUCCESS) {
	jint grpnum = num_entries;

	jclass uclazz = env->FindClass("jif/policy/Group");
	jmethodID constructor = env->GetMethodID(uclazz, "<init>", 
	    "(Ljava/lang/String;)V");
	grpArray = env->NewObjectArray(grpnum, uclazz, NULL);
	
	for (DWORD i=0; i < num_entries; i++) {
	    jstring name = env->NewString((jchar*)buf[i].lgrpi0_name, 
		                          wcslen(buf[i].lgrpi0_name));
	    jobject group = env->NewObject(uclazz, constructor, name);
	    env->SetObjectArrayElement(grpArray, i, group);
	    env->DeleteLocalRef(name);
	    env->DeleteLocalRef(group);
	}
	NetApiBufferFree(buf);
    }
    else {
	printf("Enum groups error: %d\n", GetLastError());
	exit(1);
    }

    return grpArray;
}

