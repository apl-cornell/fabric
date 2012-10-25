#!/usr/bin/env python
import os
import subprocess
import time
import re
import math
import threading
import datetime
import pexpect

times = []

class ThreadClass(threading.Thread):
    def __init__(self, h, w, c, t):
        threading.Thread.__init__(self)
        self.host = h
        self.worker = w
        self.commit = c
        self.hot = t
    def run(self):
        ssh = 'ssh'
        user = 'soule@%s' % self.host
        cmd = '/home/soule/workspace/promises/examples/microbenchmarks/readwritemix/bin/worker %s %s'  % (self.worker, self.commit)
        if (self.hot) :
            cmd += ' --hot'
        exe = [ssh, user, cmd]
        (self.fout, self.ferr) = subprocess.Popen(exe, stdout=subprocess.PIPE, stderr=subprocess.PIPE).communicate()
        regex = re.compile('\s*Total:\s*(\d+)\s*ms')
        for m in regex.finditer(self.fout):
            result = m.group(1)
            times.append(int(result))
            #print 'host: %s time %s' % (self.worker, result)       
        
def start_store():
    child = pexpect.spawn ('ssh soule@mal.cs.cornell.edu /home/soule/workspace/promises/examples/microbenchmarks/readwritemix/bin/start-store')
    child.expect ('mal0>.*')
    print "store running..."
    return child

def stop_store(child):
    print "store stopping..."
    child.sendline ('exit')

class Configs:
    static, dynamic = range(2)

def plot(name):
    data = name + ".dat"
    gnu = "./" + name + ".gnu"
    ps = "./" + name + ".ps"
    cmd = "plot "
    cmd +=  "\"" + data + "\" u 1:2 t \'commit\' w linespoints, "
    cmd +=  "\"" + data + "\" u 1:3 t \'nocommit\' w linespoints "
    with open(gnu, 'w') as f:
        f.write('set terminal postscript\n')
        f.write('set output \"' + ps + '\"\n')
        f.write('set title \"007 ' + name + '\"\n')
        f.write('set xlabel \"workers\"\n');
        f.write('set ylabel \"time\"\n');
        f.write(cmd)
    os.system('gnuplot ' + gnu)
    os.system('ps2pdf ' + ps)



def test_hot(worker_names):
    threads = []
    with open('./hot.dat', 'w') as f:
        store = start_store()
        # run once to warm the store
        print "warming store..."
        (worker, host) = worker_names[0]
        t = ThreadClass(host, worker, 'n', True)
        t.start()
        t.join()
        print "beginning test..."
        s = '#%s\t%s\t%s' % ( 'workers', 'commit_time', 'nocommit_time')
        print s
        f.write(s + '\n')
        for num_workers in range(1,7):
            workers = worker_names[0:num_workers]
            commit_time = 0.0
            nocommit_time = 0.0           
            for commit in ['y', 'n']:
                # start all the workers...
                for (worker, host) in workers:              
                    t = ThreadClass(host, worker, commit, True)
                    t.start()
                    threads.append(t)
                    # wait for the workers to finish
                    for t in threads:
                        t.join()
                average = float(sum(times)) / len(times)
                print "workers =" + str(num_workers) + ", commit=" + commit + ", average=" + str(average)
                if commit == 'y':
                    commit_time = average
                else:
                    nocommit_time = average
            s = '%d\t%0.2f\t%0.2f' % (num_workers, commit_time, nocommit_time)
            print s
            f.write(s + '\n')
        stop_store(store)
    plot('hot')

def test_warm(worker_names):
    threads = []
    with open('./warm.dat', 'w') as f:
        store = start_store()
        # run once to warm the store
        print "warming store..."
        (worker, host) = worker_names[0]
        t = ThreadClass(host, worker, 'n', False)
        t.start()
        t.join()
        print "beginning test..."
        s = '#%s\t%s\t%s' % ( 'workers', 'commit_time', 'nocommit_time')
        print s
        f.write(s + '\n')
        for num_workers in range(1,7):
            workers = worker_names[0:num_workers]
            commit_time = 0.0
            nocommit_time = 0.0           
            for commit in ['y', 'n']:
                # start all the workers...
                for (worker, host) in workers:              
                    t = ThreadClass(host, worker, commit, False)
                    t.start()
                    threads.append(t)
                    # wait for the workers to finish
                    for t in threads:
                        t.join()
                average = float(sum(times)) / len(times)
                print "workers =" + str(num_workers) + ", commit=" + commit + ", average=" + str(average)
                if commit == 'y':
                    commit_time = average
                else:
                    nocommit_time = average
            s = '%d\t%0.2f\t%0.2f' % (num_workers, commit_time, nocommit_time)
            print s
            f.write(s + '\n')
        stop_store(store)
    plot('warm')
        

def test_cold(worker_names):
    threads = []
    with open('./cold.dat', 'w') as f:
        s = '#%s\t%s\t%s' % ( 'workers', 'commit_time', 'nocommit_time')
        print s
        f.write(s + '\n')
        for num_workers in range(1,7):
            workers = worker_names[0:num_workers]
            commit_time = 0.0
            nocommit_time = 0.0
            for commit in ['y', 'n']:        
                store = start_store()
                # start all the workers...
                for (worker, host) in workers:              
                    t = ThreadClass(host, worker, commit, False)
                    t.start()
                    threads.append(t)
                    # wait for the workers to finish
                    for t in threads:
                        t.join()
                average = float(sum(times)) / len(times)
                print "workers =" + str(num_workers) + ", commit=" + commit + ", average=" + str(average)
                stop_store(store)
                if commit == 'y':
                    commit_time = average
                else:
                    nocommit_time = average
            s = '%d\t%0.2f\t%0.2f' % (num_workers, commit_time, nocommit_time)
            print s
            f.write(s + '\n')
    plot('cold')
        
def main():
    worker_names = [ ('zoe1', 'zoe.cs.cornell.edu'),
                ('wash1', 'wash.systems.cs.cornell.edu'),
                ('inara1', 'inara.systems.cs.cornell.edu'),
                ('zoe2', 'zoe.cs.cornell.edu'),
                ('wash2', 'wash.systems.cs.cornell.edu'),
                ('inara2', 'inara.systems.cs.cornell.edu')
                ]
    #test_warm(worker_names)
    test_cold(worker_names)
    #test_hot(worker_names)
    #plot('hot')

if __name__ == "__main__":
    main()
