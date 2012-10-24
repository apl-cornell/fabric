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

#/home/soule/workspace/promises/examples/microbenchmarks/readwritemix/bin/start-store
# bin/create-db store0 1000
#./bin/worker zoe1 n 1000 50


class ThreadClass(threading.Thread):
    def __init__(self, h, w, c, t, s, p):
        threading.Thread.__init__(self)
        self.host = h
        self.worker = w
        self.commit = c
        self.hot = t
        self.size = s
        self.percentage = p
    def run(self):
        ssh = 'ssh'
        user = 'soule@%s' % self.host
        cmd = '/home/soule/workspace/promises/examples/microbenchmarks/readwritemix/bin/worker %s %s %d %d'  % (self.worker, self.commit, self.size, self.percentage)
        if (self.hot) :
            cmd += ' --hot'
        exe = [ssh, user, cmd]
        (self.fout, self.ferr) = subprocess.Popen(exe, stdout=subprocess.PIPE, stderr=subprocess.PIPE).communicate()
        regex = re.compile('\s*Total:\s*(\d+)\s*ms')
        for m in regex.finditer(self.fout):
            result = m.group(1)
            times.append(int(result))
            #print 'host: %s time %s' % (self.worker, result)       

def mean_and_dev(l):         
    #average = float(sum(times)) / len(times)
    mean = float(reduce(lambda x, y: x + y, l)) / len(l)
    deviations = map(lambda x: x - mean, l)
    squares = map(lambda x: x * x, deviations)
    dev = 0
    if len(squares) > 1:
        dev = math.sqrt(reduce(lambda x, y: x + y, squares) /  (len(squares) - 1))
    return (mean, dev)
        
def create_db(size):
    cmd = '/home/soule/workspace/promises/examples/microbenchmarks/readwritemix/bin/create-db store0 %d'  % (size)
    child = pexpect.spawn ('ssh soule@mal.cs.cornell.edu /home/soule/workspace/promises/examples/microbenchmarks/readwritemix/bin/start-store')
    child.expect ('done.*')
    print "database created..."
     
def start_store():
    child = pexpect.spawn ('ssh soule@mal.cs.cornell.edu /home/soule/workspace/promises/examples/microbenchmarks/readwritemix/bin/start-store')
    child.expect ('store0>.*')
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
    cmd += "\"\" u 1:2:3 notitle w yerrorbars, "    
    cmd +=  "\"" + data + "\" u 1:4 t \'nocommit\' w linespoints, "
    cmd += "\"\" u 1:4:5 notitle w yerrorbars"    
    with open(gnu, 'w') as f:
        f.write('set terminal postscript\n')
        f.write('set output \"' + ps + '\"\n')
        f.write('set title \"Read Write Mix ' + name + '\"\n')
        f.write('set xlabel \"workers\"\n');
        f.write('set ylabel \"time\"\n');
        f.write(cmd)
    os.system('gnuplot ' + gnu)
    os.system('ps2pdf ' + ps)


def test_warm(worker_names, size, percentage):
    threads = []
    global times
    splot = 'warm-%d-%d' % (size, percentage)
    fdata = './' + splot + '.dat'
    with open(fdata, 'w') as f:
        store = start_store()
        # run once to warm the store
        print "warming store..."
        (worker, host) = worker_names[0]
        t = ThreadClass(host, worker, 'n', False, size, percentage)
        t.start()
        t.join()
        print "beginning test..."
        s = '#%s\t%s\t%s\t%s\t%s' % ( 'workers', 'commit_time', 'commit_dev', 'nocommit_time', 'nocommit_dev')
        print s
        f.write(s + '\n')
        for num_workers in range(1,7):
            workers = worker_names[0:num_workers]
            commit_time = 0.0
            nocommit_time = 0.0
            commit_dev = 0.0
            nocommit_dev = 0.0
            for commit in ['y', 'n']:
                # start all the workers...
                times = []
                for (worker, host) in workers:              
                    t = ThreadClass(host, worker, commit, False, size, percentage)
                    t.start()
                    threads.append(t)
                    # wait for the workers to finish
                for t in threads:
                    t.join()
                (average, dev) = mean_and_dev(times)                
                print "workers =" + str(num_workers) + ", commit=" + commit + ", average=" + str(average) + ", dev=" + str(dev)
                if commit == 'y':
                    commit_time = average
                    commit_dev = dev
                else:
                    nocommit_time = average
                    nocommit_dev = dev
            s = '%d\t%0.2f\t%0.2f\t%0.2f\t%0.2f' % (num_workers, commit_time, commit_dev, nocommit_time, nocommit_dev)
            print s
            f.write(s + '\n')
        stop_store(store)
    plot(splot)
        

def test_cold(worker_names, size, percentage):
    threads = []
    global times
    splot = 'cold-%d-%d' % (size, percentage)
    fdata = './' + splot + '.dat'
    with open(fdata, 'w') as f:
        s = '#%s\t%s\t%s\t%s\t%s' % ( 'workers', 'commit_time', 'commit_dev', 'nocommit_time', 'nocommit_dev')
        print s
        f.write(s + '\n')
        for num_workers in range(1,7):
            workers = worker_names[0:num_workers]
            commit_time = 0.0
            nocommit_time = 0.0
            commit_dev = 0.0
            nocommit_dev = 0.0
            for commit in ['y', 'n']:        
                store = start_store()
                # start all the workers...
                times = []
                for (worker, host) in workers:              
                    t = ThreadClass(host, worker, commit, False, size, percentage)
                    t.start()
                    threads.append(t)
                    # wait for the workers to finish
                for t in threads:
                    t.join()
                (average, dev) = mean_and_dev(times)
                print "workers =" + str(num_workers) + ", commit=" + commit + ", average=" + str(average) + ", dev=" + str(dev)
                stop_store(store)
                if commit == 'y':
                    commit_time = average
                    commit_dev = dev
                else:
                    nocommit_time = average
                    nocommit_dev = dev            
            s = '%d\t%0.2f\t%0.2f\t%0.2f\t%0.2f' % (num_workers, commit_time, commit_dev, nocommit_time, nocommit_dev)
            print s
            f.write(s + '\n')
    plot(splot)
        
def main():
    worker_names = [ ('zoe1', 'zoe.cs.cornell.edu'),
                ('wash1', 'wash.systems.cs.cornell.edu'),
                ('inara1', 'inara.systems.cs.cornell.edu'),
                ('zoe2', 'zoe.cs.cornell.edu'),
                ('wash2', 'wash.systems.cs.cornell.edu'),
                ('inara2', 'inara.systems.cs.cornell.edu')
                ]
    #test_warm(worker_names, 100, 50)
    test_cold(worker_names, 100, 50)
    #plot('hot')

if __name__ == "__main__":
    main()
