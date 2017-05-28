#!/usr/bin/python

# Adds the nodes listed in "nodes.txt" to our planetlab slice.

import xmlrpclib
server = xmlrpclib.ServerProxy('https://www.planet-lab.org/PLCAPI/')
auth = {}
auth['Username'] = "mdgeorge@cs.cornell.edu"
auth['AuthString'] = "PL4apl"
auth['AuthMethod'] = "password"

node_list = [line.strip() for line in open("nodes.txt")]
server.AddSliceToNodes(auth, "cornell_andru", node_list)

