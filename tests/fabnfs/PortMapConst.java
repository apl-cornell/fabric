package fabnfs;

// See rfc 1057 for the definition of these constants.
interface PortMapConst {
    public long PMAP_PORT = 111;
    
    public long PMAP_NULL = 0;
    public long PMAP_SET = 1;
    public long PMAP_UNSET = 2;
    public long PMAP_GETPORT = 3;
    public long PMAP_DUMP = 4;
    public long PMAP_CALLIT = 5;

    public long PM_PROG = 100000;
    public long PM_VERS = 2;

    public long PM_SUCCESS = 0;
    public long PM_PROG_UNAVAIL = 1;
    public long PM_PROG_MISMATCH = 2;
    public long PM_PROC_UNAVAIL = 3;
    public long PM_GARBAGE_ARGS = 4;
    
    public long PM_TRUE = 1;
    public long PM_FALSE = 0;
};

