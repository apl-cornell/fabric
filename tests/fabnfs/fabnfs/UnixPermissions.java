package fabnfs;

interface UnixPermissions {
    public long UPDIR = 0040000; // This is a directory;
    public long UPCHRS = 0020000; // This is a character special file;
    public long UPBLKS = 0060000; // This is a block special file;
    public long UPFILE = 0100000; // This is a regular file;
    public long UPSLINK = 0120000; // This is a symbolic link file;
    public long UPSOCK = 0140000; // This is a named socket;
    public long UPSUID = 0004000; // Set user id on execution.
    public long UPSGID = 0002000; // Set group id on execution.
    public long UPSTICKY = 0001000; // Save swapped text even after use.
    public long UP_OREAD = 0000400; // Read permission for owner.
    public long UP_OWRITE = 0000200; // Write permission for owner.
    public long UP_OEXEC = 0000100; // Execute and search permission owner.
    public long UP_GREAD = 0000040; // Read permission for group.
    public long UP_GWRITE = 0000020; // Write permission for group.
    public long UP_GEXEC = 0000010; // Execute and search permission group.
    public long UP_WREAD = 0000004; // Read permission for world.
    public long UP_WWRITE = 0000002; // Write permission for world.
    public long UP_WEXEC = 0000001; // Execute and search permission world.
};
