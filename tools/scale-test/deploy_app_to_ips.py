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

# bin/worker
worker_sh_tmpl='''#!/bin/bash
APP_HOME="$$(dirname $$0)/.."
export ANT_HOME=/opt/apach-ant-1.8.4
export PATH=/opt/apache-ant-1.8.4/bin:$fabric/bin:$$PATH
export FABRIC_HOME="$fabric"
COMMIT=$$1 ; shift 1 ;
fab --app-home "$${APP_HOME}" --name "$name" --commit-reads "$${COMMIT}" --jvm-cp "$${APP_HOME}/classes" readwritemix.ReadWriteMix01 $store "$$@"'''
#fab --app-home "$${APP_HOME}" --name "$name" --commit-reads "${COMMIT}" --jvm-cp "$${APP_HOME}/classes" readwritemix.ReadWriteMix01 1000 100 $store'''
#fab --app-home "$${APP_HOME}" --name "$name" --jvm-cp "$${APP_HOME}/classes" readwritemix.ReadWriteMix01 "$worker"'''

# bin/start-store
start_store_sh_tmpl='''#!/bin/bash
APP_HOME="$$(dirname $$0)/.."
export ANT_HOME=/opt/apach-ant-1.8.4
export PATH=/opt/apache-ant-1.8.4/bin:$fabric/bin:$$PATH
export FABRIC_HOME="$fabric"
fab-store  --app-home "$${APP_HOME}" --name $store --jvm-cp "$${APP_HOME}/classes" '''
#fab-store --jvm-cp "$${APP_HOME}/classes" --app-home "$${APP_HOME}" --name $store "$$@"'''


# bin/create-db
create_db_sh_tmpl='''#!/bin/bash
APP_HOME="$$(dirname $$0)/.."
export ANT_HOME=/opt/apach-ant-1.8.4
export PATH=/opt/apache-ant-1.8.4/bin:$fabric/bin:$$PATH
export FABRIC_HOME="$fabric"
fab  --app-home "$${APP_HOME}" --name $store --jvm-cp "$${APP_HOME}/classes" readwritemix.ReadWriteMixCreate01 $store "$$@"'''
#fab  --app-home "$${APP_HOME}" --name $store --jvm-cp "$${APP_HOME}/classes" readwritemix.ReadWriteMixCreate01 1000 $store'''
# NAME=$$1 ; shift 2 ;
#fab --jvm-cp "$${APP_HOME}/classes" --app-home "$${APP_HOME}" --name "$${NAME}" reedwritemix.ReadWreMixCreate01 fab://$server/$store "$$@"'''




# etc/config/worker0.properties
worker_properies_tmpl='''fabric.node.hostname  = $host
fabric.node.fetchmanager.class = fabric.dissemination.DummyFetchManager
fabric.worker.homeStore = $store'''

# etc/config/store0.properties
store_properties_tmpl='''fabric.node.hostname  = $host
fabric.worker.homeStore = $name'''

# etc/keys/store0.keystore
# etc/keys/worker0.keystore

# /home/soule/workspace/promises/examples/microbenchmarks/readwritemix/etc/keys/mal0.keystore
worker_keystore_tmpl='''${fabric_home}/bin/make-node --name $hostname --keystore $keyfile --pass password'''

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
    parser.add_argument('--app', metavar='APP', type=str,  default='./readwritemix',
                        help='The application for the experiment.')
    return parser.parse_args()

def mkdir(name):
    #print 'mkdir called on ' + name
    if not os.path.exists(name):
        os.makedirs(name)

# bin/start-store
def mk_create_db_sh(worker_bin_dir, store_name, server, distro):
    create_db_sh = worker_bin_dir + "/create-db"                        
    with open(create_db_sh, 'w') as f:
        s=string.Template(create_db_sh_tmpl)
        exp = s.substitute(store=store_name, server=server, fabric='~/' + distro)
        #print exp
        f.write(exp)

# bin/start-store
def mk_start_store_sh(worker_bin_dir, store_name, distro):
    start_store_sh = worker_bin_dir + "/start-store"                        
    with open(start_store_sh, 'w') as f:
        s=string.Template(start_store_sh_tmpl)
        exp = s.substitute(store=store_name, fabric='~/' + distro)
        #print exp
        f.write(exp)

def mk_worker_sh(worker_bin_dir, worker_name, store_name, distro):
    # bin/worker
    worker_sh = worker_bin_dir + "/worker"
    with open(worker_sh, 'w') as f:
        s=string.Template(worker_sh_tmpl)
        exp = s.substitute(name=worker_name, commit='n', worker=worker_name, fabric='~/' + distro, store=store_name)
        #print exp
        f.write(exp)


def mk_worker_prop(worker_config_dir, worker_name, store_name, ip):
    # etc/config/worker.properties
    worker_prop = worker_config_dir + "/" + worker_name + ".properties"   
    with open(worker_prop, 'w') as f:
        s=string.Template(worker_properies_tmpl)
        exp = s.substitute(host=ip, store=store_name)
        #print exp
        f.write(exp)

def mk_store_prop(worker_config_dir, store_name, ip):
  # etc/config/store.properties
    store_prop = worker_config_dir + "/" + store_name + ".properties"   
    with open(store_prop, 'w') as f:
        s=string.Template(store_properties_tmpl)
        exp = s.substitute(host=ip, name=store_name)
        #print exp
        f.write(exp)

def mk_keystore_file(host_name, key_file):
    s=string.Template(worker_keystore_tmpl)
    exp = s.substitute(fabric_home=fabric_home, hostname=host_name, keyfile=key_file)
    #print exp
    child = pexpect.spawn (exp)
    child.expect ('Trust this certificate?.*:')
    child.sendline ('yes')

def cp_app(app_root, dest_root):
    cmd = 'cp -r ' + app_root + ' ' + dest_root 
    print cmd
    child = pexpect.spawn (cmd, timeout=None)
    child.expect(pexpect.EOF)

def mk_worker(worker_name, app_name, store_name, server, distro):
    worker_root_dir = './' + worker_name  + '/' + app_name
    worker_bin_dir = worker_root_dir + '/' + "bin"
    worker_etc_dir = worker_root_dir + '/' + "etc"
    worker_config_dir = worker_etc_dir + '/' + "config"
    worker_keys_dir =  worker_etc_dir + '/' + "keys"
    key_file =  worker_keys_dir + '/' + worker_name + '.keystore'
    store_root_dir = './' + store_name  + '/' + app_name
    mkdir(worker_root_dir)
    cp_app(app_name, worker_name) 
    mkdir(worker_bin_dir)
    mkdir(worker_etc_dir)
    mkdir(worker_config_dir)
    mkdir(worker_keys_dir)
    cp_app(app_name, worker_root_dir) 
    mk_worker_sh(worker_bin_dir, worker_name, store_name, distro)
    mk_start_store_sh(worker_bin_dir, store_name, distro)
    mk_create_db_sh(worker_bin_dir, store_name, server, distro)
    mk_worker_prop(worker_config_dir, worker_name, store_name, server)
    mk_keystore_file(server, key_file)
    shutil.copy2(store_root_dir + '/etc/config/' + store_name + '.properties',  worker_config_dir)
    shutil.copy2(worker_config_dir + '/' + worker_name + '.properties',  store_root_dir + '/etc/config/')

def mk_store(store_name, app_name, server, distro):
    store_root_dir = './' + store_name  + '/' + app_name
    store_bin_dir = store_root_dir + '/' + "bin"
    store_etc_dir = store_root_dir + '/' + "etc"
    store_config_dir = store_etc_dir + '/' + "config"
    store_keys_dir =  store_etc_dir + '/' + "keys"
    key_file =  store_keys_dir + '/' + store_name + '.keystore'  
    mkdir(store_root_dir)
    cp_app(app_name, store_name) 
    mkdir(store_bin_dir)
    mkdir(store_etc_dir)
    mkdir(store_config_dir)
    mkdir(store_keys_dir)
    mk_worker_sh(store_bin_dir, store_name, store_name, distro)
    mk_start_store_sh(store_bin_dir, store_name, distro)
    mk_create_db_sh(store_bin_dir, store_name, server, distro)
    # mk_keystore_file(store_name, key_file)
    mk_keystore_file(server, key_file)
    mk_store_prop(store_config_dir, server, server)

def tgz(dir):
    # tar config
    cmd = 'tar -cf ' + dir + '.tar ' + dir
    print cmd
    child = pexpect.spawn (cmd, timeout=None)
    child.expect(pexpect.EOF)
    
    # gzip config
    cmd = 'gzip ' + dir + '.tar'
    print cmd
    child = pexpect.spawn (cmd, timeout=None)
    child.expect(pexpect.EOF)

def expand(dir):
    # expand
    cmd = 'gunzip ' + dir + '.tar.gz'
    print cmd
    child = pexpect.spawn (cmd)
    child.expect(pexpect.EOF)
    cmd = 'tar -xf ' + dir + '.tar'    
    print cmd
    child = pexpect.spawn (cmd)
    child.expect(pexpect.EOF)

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

def cleanup(dir):    
    # cleanup
    cmd = 'rm -rf ' + dir + '.tar.gz ' + dir
    print cmd
    child = pexpect.spawn (cmd, timeout=None)
    child.expect(pexpect.EOF)

       
def main():
    args = parse_args()
   
    app_name = args.app
    print app_name

    distro = args.distro
    print app_name
    
    expand(app_name)
    pw = getpass.getpass()

    # store_name = 'store0'
    worker_id = 0

    store_ip = '128.84.154.167'
    store_name = store_ip
    mk_store(store_name, app_name, store_ip, distro)

    # setup the workers
    with open(args.file, 'r') as f:
        for line in f:
            server = line.rstrip()
            # worker_name = 'w%0.6d' % worker_id
            worker_name = server
            print 'server=' + server
            mkdir(worker_name)
            mk_worker(worker_name, app_name, store_name, server, distro)
            tgz(worker_name)
            cp_expand(worker_name, args.user, pw, server)
            cleanup(worker_name)
            worker_id += 1

    # cp the store
    tgz(store_name)
    cp_expand(store_name, args.user, pw, store_ip)
    cleanup(store_name)

if __name__ == "__main__":
    main()
