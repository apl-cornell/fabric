#!/usr/bin/python

# Removes the nodes listed in "nodes.txt" from our planetlab slice.

import xmlrpclib
server = xmlrpclib.ServerProxy('https://www.planet-lab.org/PLCAPI/')
auth = {}
auth['Username'] = "mdgeorge@cs.cornell.edu"
auth['AuthString'] = "PL4apl"
auth['AuthMethod'] = "password"

node_list = [line.strip() for line in open("nodes.txt")]
server.DeleteSliceFromNodes(auth, "cornell_andru", node_list)

