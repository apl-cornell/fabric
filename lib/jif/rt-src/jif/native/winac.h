#include <stdio.h>
#include <windows.h>
#include <winnt.h>
#include "aclapi.h"
#include <tchar.h>

#define PSIDFromPACE(pACE)  ((PSID)(&((pACE)->SidStart)))
#define SUCCESS		1
#define FAIL		0
#define DEBUG		0
#define ACCESS_ALLOWED_OBJECT_ACE_TYPE	0x05
#define ACCESS_DENIED_OBJECT_ACE_TYPE   0x06
#define SYSTEM_AUDIT_OBJECT_ACE_TYPE	0x07
#define INHERITED_ACE			0x08

typedef union _ACE_UNION {
    ACE_HEADER		aceHeader;
    ACCESS_ALLOWED_ACE	aceAllowed;
    ACCESS_DENIED_ACE	aceDenied;
    SYSTEM_AUDIT_ACE	aceAudit;
}* PACE_UNION;

extern PACL CreateAcl(PSID owner, PSID* readers, int readerNum);

extern PSID GetSID(LPCTSTR name);

extern int GetReadersFromACL(PACL pACL, DWORD* num, LPBYTE* buf);
    
extern int GetFileReaders(LPCTSTR fname, DWORD* pNum, LPBYTE* buf);

extern int SetSecurityInfo(const char* fname, const char* owner, 
			   const char** reader, int readerNum);

class Principal 
{
    LPWSTR name;
    LPWSTR domain;
    LPWSTR fullName;
    
    public:
	Principal(LPWSTR name, LPWSTR domain);
	Principal(PSID sid);

	LPWSTR Name() { 
	    return name;
	}
	
	LPWSTR Domain() {
	    return domain;
	}

	LPWSTR FullName(); 
	bool IsLocal();
	bool ActsFor(Principal p);
	~Principal();
};

extern Principal* GetFileOwner(LPCTSTR fname);
extern Principal* GetCurrentUser();    
	
