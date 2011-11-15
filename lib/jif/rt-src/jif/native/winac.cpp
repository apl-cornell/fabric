#include "winac.h"

#include <windows.h>
#include <tchar.h>
#include <lmaccess.h>
#include <lmapibuf.h>
#include <lm.h>

//-------------------------------------------------------------------
//Dump an ACL
Principal::Principal(LPWSTR name, LPWSTR domain) {
    this->name = name;
    this->domain = domain;
    this->fullName = NULL;
}

Principal::Principal(PSID sid) {
    name = (LPWSTR) malloc(1024 * sizeof(WCHAR));
    domain = (LPWSTR) malloc(1024 * sizeof(WCHAR));
    DWORD nameSize = 1024;
    DWORD dnSize = 1024;
    SID_NAME_USE sidUse;

    if (!LookupAccountSidW(NULL, sid, name, &nameSize, domain, &dnSize, 
		&sidUse)) {
	wcscpy(name, L"Unknown");
	wcscpy(domain, L"Unknown");
    }    

    this->fullName = NULL;
}

Principal::~Principal() {
    free(name);
    free(domain);
    free(fullName);
}

LPWSTR Principal::FullName() {
    if (fullName == NULL) {
	int len = wcslen(name);
	if (!IsLocal()) 
	    len += wcslen(domain) + 1;
	fullName = (LPWSTR) calloc(len + 1, sizeof(WCHAR));
	if (!IsLocal()) {
	    wcscpy(fullName, domain);
	    wcscat(fullName, L"\\");
	}
	wcscat(fullName, name);
    }
    return fullName;
}

bool Principal::IsLocal() {
    if (domain == NULL) return true;

    LPWSTR localHost = (LPWSTR) calloc(1024, sizeof(WCHAR));
    DWORD size = 1024;
	    
    GetComputerNameW(localHost, &size);
    
    if (wcscmp(domain, L"") == 0 ||
	    wcscmp(domain, localHost) == 0 ||
	    wcscmp(domain, L"NT AUTHORITY") == 0 ||
	    wcscmp(domain, L"BUILTIN") == 0 ) {
	free(domain);
	domain = NULL;
	return true;
    }
    
    return false;
}

bool Principal::ActsFor(Principal that) {
    NET_API_STATUS nStatus;
    LPWSTR dcName;
    GROUP_USERS_INFO_0* grpInfo;
    LOCALGROUP_USERS_INFO_0* lgrpInfo;
    DWORD grpNum;
    DWORD totalGrpNum;
    bool result = false;

    try {
	if (!that.IsLocal()) {
	    if (this->IsLocal() || wcscmp(this->domain, that.domain) != 0) 
		return false;
	    
	    //get domain controller
	    nStatus = NetGetDCName(NULL, domain, (LPBYTE*) &dcName);
	    if (nStatus != NERR_Success) 
		throw "NetGetDcName Error";

	    //get groups
	    nStatus = NetUserGetGroups(dcName, name, 0, (LPBYTE*) &grpInfo, 
		    MAX_PREFERRED_LENGTH, &grpNum, &totalGrpNum);

	    if (nStatus != NERR_Success)
		throw "NetUserGetGroups Error";

	    for (DWORD i = 0; i < grpNum; i++) 
		if (wcscmp(that.name, grpInfo[i].grui0_name) == 0) {
		    result = true;
		    break;
		}		    
	}
	else {
	    nStatus = NetUserGetLocalGroups(NULL, FullName(), 0, 
		    LG_INCLUDE_INDIRECT, (LPBYTE*) &lgrpInfo, 0, 
		    &grpNum, &totalGrpNum);

	    if (nStatus != NERR_Success) 
		throw "NetUserGetLocalGroups Error";
	    
	    for (DWORD i = 0; i < grpNum; i++) 
		if (wcscmp(that.name, lgrpInfo[i].lgrui0_name) == 0) {
		    result = true;
		    break;
		}
	}
    }
    catch (LPSTR errmsg) {
	fprintf(stderr, "%s: %d\n", errmsg, nStatus);
    }
    
    if (dcName != NULL) 
	NetApiBufferFree(dcName);
    
    if (grpInfo != NULL) 
	NetApiBufferFree(grpInfo);

    if (lgrpInfo != NULL)
	NetApiBufferFree(lgrpInfo);
    
    return result;
}    

void DumpACL( PACL pACL ){
    if (pACL == NULL){
	_tprintf(TEXT("NULL DACL\n"));
	return;
    }

    ACL_SIZE_INFORMATION aclSize = {0};
    if (!GetAclInformation(pACL, &aclSize, sizeof(aclSize), AclSizeInformation))
	return;
    
    _tprintf(TEXT("ACL ACE count: %d\n"), aclSize.AceCount);
    
    struct{
	BYTE  lACEType;
	PTSTR pszTypeName;
    }aceTypes[6] = {
	{ACCESS_ALLOWED_ACE_TYPE, TEXT("ACCESS_ALLOWED_ACE_TYPE")},
	{ACCESS_DENIED_ACE_TYPE, TEXT("ACCESS_DENIED_ACE_TYPE")},
	{SYSTEM_AUDIT_ACE_TYPE, TEXT("SYSTEM_AUDIT_ACE_TYPE")},
	{ACCESS_ALLOWED_OBJECT_ACE_TYPE,
	    TEXT("ACCESS_ALLOWED_OBJECT_ACE_TYPE")},
	{ACCESS_DENIED_OBJECT_ACE_TYPE,
	    TEXT("ACCESS_DENIED_OBJECT_ACE_TYPE")},
	{SYSTEM_AUDIT_OBJECT_ACE_TYPE,
	    TEXT("SYSTEM_AUDIT_OBJECT_ACE_TYPE")}};

    struct{
	ULONG lACEFlag;
	PTSTR pszFlagName;
    }aceFlags[7] = {
	{INHERITED_ACE, TEXT("INHERITED_ACE")},
	{CONTAINER_INHERIT_ACE, TEXT("CONTAINER_INHERIT_ACE")},
	{OBJECT_INHERIT_ACE, TEXT("OBJECT_INHERIT_ACE")},
	{INHERIT_ONLY_ACE, TEXT("INHERIT_ONLY_ACE")},
	{NO_PROPAGATE_INHERIT_ACE, TEXT("NO_PROPAGATE_INHERIT_ACE")},
	{FAILED_ACCESS_ACE_FLAG, TEXT("FAILED_ACCESS_ACE_FLAG")},
	{SUCCESSFUL_ACCESS_ACE_FLAG, 
	    TEXT("SUCCESSFUL_ACCESS_ACE_FLAG")}};

    for (ULONG i = 0; i < aclSize.AceCount; i++){
	ACCESS_ALLOWED_ACE* pACE;
	if (!GetAce(pACL, i, (PVOID*)&pACE))
	    return;

	_tprintf(TEXT("\nACE #%d\n"), i);
	
	ULONG j = 6;
	PTSTR pszString = TEXT("Unknown ACE Type");
	while (j--){
	    if(pACE->Header.AceType == aceTypes[j].lACEType){
		pszString = aceTypes[j].pszTypeName;
	    }
	}
	_tprintf(TEXT("  ACE Type =\n  \t%s\n"), pszString);
	
	_tprintf(TEXT("  ACE Flags = \n"));
	j = 7;
	while (j--){
	    if ((pACE->Header.AceFlags & aceFlags[j].lACEFlag) != 0)
		_tprintf(TEXT("  \t%s\n"), aceFlags[j].pszFlagName);
	}

	_tprintf(TEXT("  ACE Mask (32->0) =\n  \t"));
	j = (ULONG)1<<31;
	while (j){
	    _tprintf(((pACE->Mask & j) != 0)?TEXT("1"):TEXT("0"));
	    j>>=1;
	}
    }
}

//---------------------------------------------------------------------------
// Calculate the size of a ACL
 
ULONG CalculateACLSize(PACL pACLOld, PSID* ppSidArray, int nNumSids,
	PSID owner, int nNumACEs )
{
    ULONG lACLSize = 0;
    
    try {
	// If we are including an existing ACL, then find its size
	if (pACLOld != NULL){
	    ACL_SIZE_INFORMATION aclSize;
	    if(!GetAclInformation(pACLOld, &aclSize, sizeof(aclSize), 
		AclSizeInformation)){            
		goto leave;
	    }
	    lACLSize = aclSize.AclBytesInUse;
	}

	if (ppSidArray != NULL){
	    // Step through each SID
	    while (nNumSids--){
		// If a SID isn't valid, then we bail
		if (!IsValidSid(ppSidArray[nNumSids])){
		lACLSize = 0;
		goto leave;
		}
		// Get the SID's length
		lACLSize += GetLengthSid(ppSidArray[nNumSids]);
		// Add the ACE structure size, minus the 
		// size of the SidStart member
		lACLSize += sizeof(ACCESS_ALLOWED_ACE) - 
		sizeof(((ACCESS_ALLOWED_ACE*)0)->SidStart);         
	    } 
	}

	lACLSize += GetLengthSid(owner);
	lACLSize += sizeof(ACCESS_ALLOWED_ACE) -
	    sizeof(((ACCESS_ALLOWED_ACE*)0)->SidStart);		 
	
	// Add in the ACL structure itself
	lACLSize += sizeof(ACL);

    leave:;
    }catch(...){
	// An exception means we fail the function
	lACLSize = 0;      
    }
    return (lACLSize);
}

//----------------------------------------------------------------------
//Create a new ACL which grant "read" permissions to a set of principals

PACL CreateAcl(PSID owner, PSID* readers, int readerNum) {
    try {
	ULONG aclSize = CalculateACLSize(NULL, readers, readerNum, owner, 0);
	if (aclSize == 0 )
	    throw "CalculateACLSize Error";

	PACL pDACL = (PACL) HeapAlloc(GetProcessHeap(), 0, aclSize);

	if (pDACL == NULL) 
	    throw "Allocate ACL memory error";

	if (! InitializeAcl(pDACL, aclSize, ACL_REVISION) ) 
	    throw "InitializeACL error";

	if (! AddAccessAllowedAce(pDACL, ACL_REVISION, GENERIC_ALL, owner) )
	    throw "Add owner access error";

	for (int i=0; i < readerNum; i++) {
	    if (! AddAccessAllowedAce(pDACL, ACL_REVISION, GENERIC_READ, 
			readers[i]) )
		throw "Add reader error";
	}

	return pDACL;
    } 
    catch (const char* errorMsg) {
	fprintf(stderr, "%s: %d\n", errorMsg, GetLastError());
    }
    
    return NULL;
}

//----------------------------------------------------------------------
//Get the SID corresponding to a user name

PSID GetSID(LPCTSTR name) {
    PSID pSid = malloc(1024);
    DWORD bufsize = 1024;
    LPTSTR domainName = (LPTSTR) calloc(1024, sizeof(TCHAR));
    DWORD dnSize = 1024;
    SID_NAME_USE use;
    
    if (!LookupAccountName(NULL, name, pSid, &bufsize, domainName, 
		&dnSize, &use)) {
	free(pSid);
	pSid = malloc(bufsize);
	free(domainName);
	domainName = (LPTSTR) malloc(dnSize);
	if (!LookupAccountName(NULL, name, pSid, &bufsize, domainName,
		    &dnSize, &use) ) {
	    free(pSid);
	    free(domainName);
	    return NULL;
	}
    }

    _tprintf(TEXT("Domain Name: %s\n"), domainName);
    free(domainName);

    return pSid;    
}

//---------------------------------------------------------------------
//Update the security information of a file

int SetSecurityInfo(LPCTSTR fname,  LPCTSTR owner, LPCTSTR* reader, 
	int readerNum) 
{
    int ret;
    PSID ownerId;
    PSID* readerId = (PSID*) calloc(readerNum, sizeof(PSID));

    ownerId = GetSID(owner);
    if (ownerId == NULL) 
	return FAIL;

    for (int i=0; i<readerNum; i++) {
	readerId[i] = GetSID(reader[i]);
	if ( readerId[i] == NULL )
	    return FAIL;
    }

    PACL pAcl = CreateAcl(ownerId, readerId, readerNum);
    free(readerId);

    char fname1[1024];
    strcpy(fname1, fname);

    if (DEBUG) {
	printf("-----------------\n");
	printf("owner: %s\n", owner);
    
	for (int i=0; i<readerNum; i++)
	    printf("reader %d: %s\n", i, reader[i]);
    }

    if (SetNamedSecurityInfo (fname1, SE_FILE_OBJECT, 
		OWNER_SECURITY_INFORMATION | DACL_SECURITY_INFORMATION,
		ownerId, NULL, NULL, NULL) != ERROR_SUCCESS) {
	//clear inheritance
	printf("set secure info error: %d\n", GetLastError());
	ret = FAIL;
    }
    else {
	if (SetNamedSecurityInfo (fname1, SE_FILE_OBJECT, 
		OWNER_SECURITY_INFORMATION | DACL_SECURITY_INFORMATION,
		ownerId, NULL, pAcl, NULL) != ERROR_SUCCESS) {
	    printf("set secure info error: %d\n", GetLastError());
	    ret = FAIL;
	}
	else 
	    ret = SUCCESS;
    }

    return ret; 
}

//-----------------------------------------------------------------
//Get the owner name of a file

Principal* GetFileOwner(LPCTSTR fname) 
{
    PSECURITY_DESCRIPTOR pSD;
    PSID pSID;
    PACL pDACL;

    HANDLE hFile = CreateFile(fname, GENERIC_READ, FILE_SHARE_READ, NULL, 
	    OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);

    ULONG lErr = GetSecurityInfo(hFile, SE_FILE_OBJECT, 
	    OWNER_SECURITY_INFORMATION | DACL_SECURITY_INFORMATION,
	    &pSID, NULL, &pDACL, NULL, &pSD);

    CloseHandle(hFile);

    if (lErr != ERROR_SUCCESS) {
	printf("error: %ld\n", GetLastError());
	return NULL;
    }		    
    return new Principal(pSID);
}

//-----------------------------------------------------------------------
//Get file readers

int GetFileReaders(LPCTSTR fname, DWORD* pNum, LPBYTE* buf) {
    PSECURITY_DESCRIPTOR pSD;
    PSID pSID = NULL;
    PSID pSIDGroup = NULL;
    PACL pDACL = NULL;
    PACL pSACL = NULL;
    HANDLE hFile = NULL;
    ULONG err;

      if (DEBUG) printf("GetFileReaders: Got security info, pDacl null? %d %d\n", (pDACL == NULL), GetLastError());
      hFile = CreateFile(fname, GENERIC_READ, FILE_SHARE_READ, NULL, 
 	    OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);
      if (hFile != NULL) {
          if (DEBUG) printf("GetFileReaders: Got security info, pDacl null? %d %d\n", (pDACL == NULL), GetLastError());
          err = GetSecurityInfo(hFile, SE_FILE_OBJECT, 
              OWNER_SECURITY_INFORMATION | DACL_SECURITY_INFORMATION,
              &pSID, &pSIDGroup, &pDACL, &pSACL, &pSD);
          if (DEBUG) printf("Got security info, pDacl null? %d %d  err is %d   ERROR_INSUFF = %d\n", (pDACL == NULL), GetLastError(), err, ERROR_INSUFFICIENT_BUFFER);
          CloseHandle(hFile);
      }
      
      if (err == ERROR_SUCCESS && pDACL != NULL) {
          err = GetReadersFromACL(pDACL, pNum, buf);
      }
      
      return err;
}

//-----------------------------------------------------------------
//Get the set of principals who have "read" permission according to 
//a given ACL.

int GetReadersFromACL(PACL pACL, DWORD* num, LPBYTE* buf) {
    Principal** readers;
    DWORD readerNum = 0;

    try {
	if (pACL == NULL) 
	    throw "NULL DACL\n";

	ACL_SIZE_INFORMATION aclSize;
	
	if (!GetAclInformation(pACL, &aclSize, sizeof(aclSize),
	    AclSizeInformation)) 
	    throw "GetAclInformation Error";

	if (DEBUG) _tprintf("ACL ACE count: %d\n", aclSize.AceCount);
	
	readers = (Principal**) calloc(aclSize.AceCount, sizeof(Principal*));

	for (ULONG i = 0; i < aclSize.AceCount; i++){
	    ACCESS_ALLOWED_ACE* pACE;
	    if (!GetAce(pACL, i, (PVOID*)&pACE))
		throw "GetAce Error";

	    if (DEBUG) _tprintf(TEXT("\nACE #%d\n"), i);
	    
	    if (pACE->Header.AceType == ACCESS_ALLOWED_ACE_TYPE) {
		if (pACE->Mask & FILE_READ_DATA) {
		    PSID sid = PSIDFromPACE(pACE);
		    readers[readerNum++] = new Principal(sid);
		}
	    }
	}

	*num = readerNum;
	*buf = (LPBYTE) readers;
	
	return ERROR_SUCCESS;
    }
    catch (LPTSTR msg) {
	_tprintf("%s\n", msg);
	throw msg;
    }

    return GetLastError();
}

//--------------------------------------------------------------------
//AllocTokenInfo

LPVOID AllocTokenInfo(HANDLE token, TOKEN_INFORMATION_CLASS tokenClass) {
    PVOID buf = NULL;

    BOOL success;
    ULONG size = 0;

    do {
	if (size != 0) {
	    if (buf != NULL) 
		free(buf);
	    buf = malloc(size);
	    if (buf == NULL)
		goto leave;
	}

	success = GetTokenInformation(token, tokenClass, buf, size, &size);

    } while (!success && (GetLastError() == ERROR_INSUFFICIENT_BUFFER) );

    if (! success) {
	if (buf != NULL)
	    free(buf);
	buf = NULL;
    }

leave:

    return buf;
}

Principal* GetCurrentUser() {
    HANDLE token;
    TOKEN_USER* tokenUser;
    Principal* pr = NULL;
    
    if (OpenProcessToken(GetCurrentProcess(), TOKEN_QUERY, &token) == FALSE) {
	fprintf(stderr, "OpenProcessToken Error: %d", GetLastError());
    }

    tokenUser = (TOKEN_USER*) AllocTokenInfo(token, TokenUser);

    if (tokenUser != NULL) {
	PSID sid = tokenUser->User.Sid;
	pr = new Principal(sid);
	free(tokenUser);
    }   

    return pr;
}

//--------------------------------------------------------------------
//main function, the testing engine

int main(int argc, char* argv[]) {
    //get the current user
    printf("Test harness\n");
    Principal* user = GetCurrentUser();
    
    if (user != NULL)
	wprintf(L"Current User: %s\n", user->FullName());
    
    HANDLE token;
    TOKEN_GROUPS* tokenGrps;
    
    if (OpenProcessToken(GetCurrentProcess(), TOKEN_QUERY, &token) == FALSE) {
	fprintf(stderr, "OpenProcessToken Error: %d", GetLastError());
    }

    tokenGrps = (TOKEN_GROUPS*) AllocTokenInfo(token, TokenGroups);

    if (tokenGrps != NULL) {
	DWORD grpNum = tokenGrps->GroupCount;

	for (DWORD i = 0; i < grpNum; i++) {
	    Principal p(tokenGrps->Groups[i].Sid);
	    wprintf(L"Group %d: %s\n", i+1, p.FullName());
	}

	free(tokenGrps);
    }

    NET_API_STATUS status;
    LPBYTE buf;
    USER_INFO_0* userBuf;
    GROUP_INFO_0* grpBuf;
    DWORD num;
    DWORD totalNum;
    DWORD resumeHandle = 0;
    
    //get DC name
    status = NetGetDCName(NULL, L"SWORD", &buf);

    if (status == NERR_Success) {
	wprintf(L"Domain Controller: %s\n", (LPWSTR) buf);
	status = NetUserEnum((LPWSTR) buf, 0, 0, (LPBYTE*) &userBuf, 
		MAX_PREFERRED_LENGTH, &num, &totalNum, &resumeHandle);
	if (status == NERR_Success) {
	    printf("Total user number: %d\n", totalNum);
	    //for (i = 0; i < num; i++) {
	    //   wprintf(L"user %d: %s\n", i+1, userBuf[i].usri0_name);
	    //}
	}
	else {
	    printf("NetUserEnum Error: %d\n", status);
	    printf("ERROR_ACCESS_DENIED = %d \n", ERROR_ACCESS_DENIED);
	    printf("NERR_InvalidComputer = %d \n", NERR_InvalidComputer);
	    printf("ERROR_MORE_DATA = %d \n", ERROR_MORE_DATA);
	}

	status = NetGroupEnum((LPWSTR) buf, 0, (LPBYTE*) &grpBuf, 
		MAX_PREFERRED_LENGTH, &num, &totalNum, &resumeHandle);
	if (status == NERR_Success) {
	    printf("Total group number: %d\n", totalNum);
	    for (DWORD i = 0; i < num; i++) {
		wprintf(L"group %d: %s\n", i+1, grpBuf[i].grpi0_name);
	    }
	}
	else {
	    printf("NetUserEnum Error: %d\n", status);
	    printf("ERROR_ACCESS_DENIED = %d \n", ERROR_ACCESS_DENIED);
	    printf("NERR_InvalidComputer = %d \n", NERR_InvalidComputer);
	    printf("ERROR_MORE_DATA = %d \n", ERROR_MORE_DATA);
	}	    
    }
    else {
	printf("NetGetDCName Error: %d\n", status);
    }

    if (buf != NULL) 
	NetApiBufferFree(buf);

    if (userBuf != NULL) 
	NetApiBufferFree(userBuf);

    if (grpBuf != NULL)
	NetApiBufferFree(grpBuf);

    printf("ERROR_INVALID_NAME: %d\n", ERROR_INVALID_NAME);
    printf("NERR_DCNotFound: %d\n", NERR_DCNotFound);
    
    LPCTSTR name = "zlt";
    
    PSID sid = GetSID(name);

    if (sid != NULL) {
        PSID_IDENTIFIER_AUTHORITY psia = GetSidIdentifierAuthority(sid);
        if (psia != NULL)
	    for (int i = 0; i < 6; i++) 
                printf("value[%d] = %d\n", i, psia->Value[i]);

        free(sid);
    }
    
    PSECURITY_DESCRIPTOR pSD;
    PSID pSID;
    PACL pDACL;

    HANDLE hFile = CreateFile("test.txt", GENERIC_READ, FILE_SHARE_READ, 
		NULL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);

    ULONG lErr = GetSecurityInfo(hFile, SE_FILE_OBJECT, 
	    OWNER_SECURITY_INFORMATION | DACL_SECURITY_INFORMATION,
	    &pSID, NULL, &pDACL, NULL, &pSD);

    if (lErr != ERROR_SUCCESS) {
	// Error case
	printf("fail!\n");
    }
    else printf("success!\n");

    //perform work here

    Principal owner(pSID);

    wprintf(L"owner: %s\n", owner.FullName());
 
    ACL_SIZE_INFORMATION aclSize = {0};
    if (pDACL != NULL) {
	if (!GetAclInformation(pDACL, &aclSize, sizeof(aclSize), AclSizeInformation)) {
	    printf("error2: %ld\n", GetLastError());
	} else {
	    printf("ACE count: %ld\n", aclSize.AceCount);
	}
    }

    DumpACL(pDACL);
    wprintf(L"\n\n");

    Principal** readers;

    int err;

    err = GetReadersFromACL(pDACL, &num, (LPBYTE*) &readers);
    if (err == ERROR_SUCCESS) {
	for (DWORD i=0; i < num; i++) {
	    wprintf(L"reader %d: %s\n", i+1, readers[i]->FullName());
	    delete readers[i];
	}

	free(readers);
    }	

    //if (GetLastError() == ERROR_NONE_MAPPED) printf("none mapped\n");

    //clean up

    LocalFree(pSD);
    CloseHandle(hFile);
    
    err = GetFileReaders("test.txt", &num, (LPBYTE*) readers);
    if (err == ERROR_SUCCESS) {
	for (DWORD i=0; i < num; i++) {
	    wprintf(L"reader %d: %s\n", i+1, readers[i]->FullName());
	    delete readers[i];
	}

	free(readers);
    }	

    const char* reader1[] = { "zlt", "Everyone" };

    SetSecurityInfo("test.txt", "Administrators", reader1, 2);

    if (user != NULL)
	delete user;

    return 0;
}

//--------------------------------------------------------------
//GetReaders (of a file)



