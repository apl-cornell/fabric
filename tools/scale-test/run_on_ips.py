#!/usr/bin/env python
import argparse
import os
import subprocess
import time
import re
import math
import threading
import datetime
import pexpect
import getpass

times = []

#/home/soule/workspace/promises/examples/microbenchmarks/readwritemix/bin/start-store
# bin/create-db store0 1000
#./bin/worker zoe1 n 1000 50


def parse_args():
    parser = argparse.ArgumentParser(description='Run Fabric app on the machines specified in the IP address file.')
    parser.add_argument('--user', metavar='USERNAME', type=str, default='fabric',
                        help='The user for ssh login')
    parser.add_argument('--store', metavar='STORE', type=str, default='128.84.154.167',
                        help='The ip address for the store')
    parser.add_argument('--file', metavar='FILENAME', type=str,  default='./ips.txt',
                        help='The name of the file that contains the i.p. addresses of available hosts.')
    parser.add_argument('--app', metavar='APP', type=str,  default='./readwritemix',
                        help='The application for the experiment.')
    return parser.parse_args()


class ThreadClass(threading.Thread):
    def __init__(self, h, w, t, s, p, user, pw):
        threading.Thread.__init__(self)
        self.host = h
        self.worker = w
        self.hot = t
        self.size = s
        self.percentage = p
        self.user = user
        self.pw = pw
    def run(self):
        cmd = 'ssh %s@%s /home/fabric/%s/readwritemix/bin/worker'  % (self.user, self.worker, self.worker)
        child = pexpect.spawn (cmd, timeout=None)
        print cmd
        child.expect ('.*password:')
        print 'got password from ' + self.worker
        child.sendline (self.pw)
        # exp = '\s*Total:\s*(\d+)\s*ms'
        child.expect(pexpect.EOF, timeout=None)
        # child.expect (exp)
        print self.worker + ' : ' + child.before
        text = child.before
        
        # ssh = 'ssh'
        # user = 'fabric@%s' % self.host
        # cmd = '/home/fabric/%s/readwritemix/bin/worker'  % (self.worker)
        # if (self.hot) :
        #     cmd += ' --hot'
        # exe = [ssh, user, cmd]
        # (self.fout, self.ferr) = subprocess.Popen(exe, stdout=subprocess.PIPE, stderr=subprocess.PIPE).communicate()
        regex = re.compile('\s*Total:\s*(\d+)\s*ms')
        #for m in regex.finditer(self.fout):
        for m in regex.finditer(text):
            result = m.group(1)
            times.append(int(result))
            print 'host: %s time %s' % (self.worker, result)       

def mean_and_dev(l):         
    #average = float(sum(times)) / len(times)
    mean = float(reduce(lambda x, y: x + y, l)) / len(l)
    deviations = map(lambda x: x - mean, l)
    squares = map(lambda x: x * x, deviations)
    dev = 0
    if len(squares) > 1:
        dev = math.sqrt(reduce(lambda x, y: x + y, squares) /  (len(squares) - 1))
    return (mean, dev)
        
def create_db(user, pw, store_ip, size):
    cmd = 'ssh %s@%s /home/fabric/%s/readwritemix/bin/create-db'  % (user, store_ip, store_ip)
    print cmd
    child = pexpect.spawn (cmd, timeout=None)
    child.expect ('.*password:')
    child.sendline (pw)
    print 'got passwd'
    child.expect(pexpect.EOF, timeout=None)
    #child.expect ('done.*')
    print "database created..."
     
def start_store(user, pw, store_ip):
    cmd = 'ssh %s@%s /home/fabric/%s/readwritemix/bin/start-store'  % (user, store_ip, store_ip)
    print cmd
    child = pexpect.spawn (cmd, timeout=None)
    child.expect ('.*password:')
    child.sendline (pw)
    exp = '%s>.*'  % (store_ip)
    print "store running..."
    return child

def stop_store(child):
    print "store stopping..."
    child.sendline ('exit')

def plot(name):
    data = name + ".dat"
    gnu = "./" + name + ".gnu"
    ps = "./" + name + ".ps"
    cmd = "plot "
    cmd +=  "\"" + data + "\" u 1:2 t \'commit\' w linespoints, "
    cmd += "\"\" u 1:2:3 notitle w yerrorbars "    
    with open(gnu, 'w') as f:
        f.write('set terminal postscript\n')
        f.write('set output \"' + ps + '\"\n')
        f.write('set title \"Read Write Mix ' + name + '\"\n')
        f.write('set xlabel \"workers\"\n');
        f.write('set ylabel \"time\"\n');
        f.write(cmd)
    os.system('gnuplot ' + gnu)
    os.system('ps2pdf ' + ps)


def test_warm(worker_names, size, percentage,user, pw, store_ip, num_runs):
    threads = []
    global times
    splot = 'warm-%d-%d' % (size, percentage)
    fdata = './' + splot + '.dat'
    with open(fdata, 'w') as f:
        store = start_store(user, pw, store_ip)
        print "populating the store..."
        create_db(user, pw, store_ip, size)
        # run once to warm the store
        print "warming store..."
        (worker, host) = worker_names[0]
        t = ThreadClass(host, worker, False, size, percentage, user, pw)    
        t.start()
        t.join()
        print "beginning test..."
        s = '#%s\t%s\t%s' % ( 'workers', 'commit_time', 'commit_dev')
        print s
        f.write(s + '\n')
        for num_workers in range(1,len(worker_names)+1):
            run_times = []
            for runs in range(num_runs):
                workers = worker_names[0:num_workers]
                commit_time = 0.0
                commit_dev = 0.0
                # start all the workers...
                times = []
                for (worker, host) in workers:              
                    t = ThreadClass(host, worker, False, size, percentage, user, pw)
                    t.start()
                    threads.append(t)
                    # wait for the workers to finish
                for t in threads:
                    t.join()
                (average, dev) = mean_and_dev(times)                
                print "workers =" + str(num_workers) + ", average=" + str(average) + ", dev=" + str(dev)
                run_times.append(int(average))
            (runs_average, runs_dev) = mean_and_dev(run_times) 
            commit_time = runs_average
            commit_dev = runs_dev
            s = '%d\t%0.2f\t%0.2f' % (num_workers, commit_time, commit_dev)
            print s
            f.write(s + '\n')
        stop_store(store)
    plot(splot)
        

def test_cold(worker_names, size, percentage, user, pw, store_ip, num_runs):
    threads = []
    global times
    splot = 'cold-%d-%d' % (size, percentage)
    fdata = './' + splot + '.dat'
    with open(fdata, 'w') as f:
        s = '#%s\t%s\t%s' % ( 'workers', 'commit_time', 'commit_dev')
        print s
        f.write(s + '\n')
        for num_workers in range(1,len(worker_names)+1):
            run_times = []
            for runs in range(num_runs):
                workers = worker_names[0:num_workers]
                commit_time = 0.0
                commit_dev = 0.0
                store = start_store(user, pw, store_ip)
                print "populating the store..."
                create_db(user, pw, store_ip, size)            
                # start all the workers...
                times = []
                for (worker, host) in workers:              
                    t = ThreadClass(host, worker, False, size, percentage, user, pw)
                    t.start()
                    threads.append(t)
                    # wait for the workers to finish
                for t in threads:
                    t.join()
                (average, dev) = mean_and_dev(times)
                print "workers =" + str(num_workers) + ", average=" + str(average) + ", dev=" + str(dev)
                run_times.append(int(average))
                stop_store(store)
            (runs_average, runs_dev) = mean_and_dev(run_times) 
            commit_time = runs_average
            commit_dev = runs_dev
            s = '%d\t%0.2f\t%0.2f' % (num_workers, commit_time, commit_dev)
            print s
            f.write(s + '\n')                              
    plot(splot)
        
def main():
  
    args = parse_args()
    pw = getpass.getpass()
    user = args.user
    store_ip = '128.84.154.167'
    worker_names = []
    num_runs = 3

    with open(args.file, 'r') as f:
        for line in f:
            server = line.rstrip()
            worker_names.append((server, server))  

    print worker_names
    test_warm(worker_names, 1000, 100, user, pw, store_ip, num_runs)
    #test_cold(worker_names, 100, 100, user, pw, store_ip, num_runs)


if __name__ == "__main__":
    main()
