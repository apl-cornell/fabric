#!/usr/bin/env python
"""
Deploy a fabric distribution and an application to each IP address specified in the
input file. The script will prompt the user for a password, which is assumed to be
the same for every IP address.

"""
import argparse
import errno
import os
import getpass
import pexpect
import shutil 
import string

fabric_home = '/Users/soule/workspace/fabric/'

def parse_args():
    parser = argparse.ArgumentParser(description='Deploy Fabric to the machines specified in the IP address file.')
    parser.add_argument('--user', metavar='USERNAME', type=str, default='fabric',
                        help='The user for ssh login')
    parser.add_argument('--store', metavar='STORE', type=str, default='128.84.154.167',
                        help='The ip address for the store')
    parser.add_argument('--file', metavar='FILENAME', type=str,  default='./ips.txt',
                        help='The name of the file that contains the i.p. addresses of available hosts.')
    parser.add_argument('--distro', metavar='DISTRO', type=str,  default='fabric',
                        help='The fabric distribution to copy.')
    return parser.parse_args()

def cp_expand(dir, user, pw, ip):
    #cp the config
    cmd = 'scp ' + dir + '.tar.gz ' + user  + '@' +  ip + ':' + dir + '.tar.gz'  
    print cmd
    child = pexpect.spawn (cmd, timeout=None)
    child.expect ('^.*password:')
    child.sendline (pw)
    child.expect(pexpect.EOF)
    #print dir + '.tar.gz copied to ' +  ip + '.'
    
    # expand
    cmd = 'ssh ' + user  + '@' + ip + ' \'gunzip ' + dir + '.tar.gz; tar -xf ' + dir + '.tar\'' 
    print cmd
    child = pexpect.spawn (cmd)
    child.expect ('.*password:')
    child.sendline (pw)
    child.expect(pexpect.EOF)
    #print dir + '.tar.gz expanded to worker on ' + ip + '.'

def init_fab(user, pw, ip, distro):
    cmd = 'ssh ' + user  + '@' + ip + ' \' rm ' + distro + '/bin/defs; cd ' + distro + '; export PATH=/opt/apache-ant-1.8.4/bin:$PATH; export ANT_HOME=/opt/apache-ant-1.8.4; ant bin\'' 
    print cmd
    child = pexpect.spawn (cmd)
    child.expect ('.*password:')
    child.sendline (pw)
    child.expect(pexpect.EOF)
   
    
def main():
    args = parse_args()
    distro = args.distro
    pw = getpass.getpass()

    # cp the store
    store_ip = '128.84.154.167'
    #cp_expand(distro, args.user, pw, store_ip)
    init_fab(args.user, pw, store_ip, distro)

    # setup the workers
    with open(args.file, 'r') as f:
        for line in f:
            server = line.rstrip()
            #cp_expand(distro, args.user, pw, server)
            init_fab(args.user, pw, server, distro)

if __name__ == "__main__":
    main()
