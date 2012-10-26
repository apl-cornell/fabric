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

def parse_args():
    parser = argparse.ArgumentParser(description='Deploy Fabric to the machines specified in the IP address file.')
    parser.add_argument('--user', metavar='USERNAME', type=str, default='fabric',
                        help='The user for ssh login')
    parser.add_argument('--store', metavar='STORE', type=str, default='128.84.154.167',
                        help='The ip address for the store')
    parser.add_argument('--file', metavar='FILENAME', type=str,  default='./ips.txt',
                        help='The name of the file that contains the i.p. addresses of available hosts.')
    parser.add_argument('--distro', metavar='DISTRO', type=str,  default='fabric-0.2.0',
                        help='The fabric distribution to copy.')
    parser.add_argument('--app', metavar='APP', type=str,  default='./readwritemix',
                        help='The application for the experiment.')
    return parser.parse_args()

def clean(user, pw, ip):
    cmd = 'ssh ' + user  + '@' + ip + ' rm -rf *' 
    print cmd
    child = pexpect.spawn (cmd)
    child.expect ('.*password:')
    child.sendline (pw)
    child.expect(pexpect.EOF)
    
def main():
    args = parse_args()
   
    pw = getpass.getpass()
    user = args.user

    store_ip = '128.84.154.167'
    clean(user, pw, store_ip)

    with open(args.file, 'r') as f:
        for line in f:
            server = line.rstrip()
            print 'server=' + server
            clean(user, pw, server)

if __name__ == "__main__":
    main()
