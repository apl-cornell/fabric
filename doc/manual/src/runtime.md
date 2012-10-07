The Fabric Runtime System {#runtime}
=========================
There are three types of nodes in the Fabric design: stores, workers,
and dissemination nodes. In the current implementation, there are no
separate dissemination nodes; rather each worker and store participates
as a peer in the dissemination network.

  * @subpage node-config
  * @subpage start-nodes


@page node-config Configuring Fabric nodes




@page start-nodes Starting Fabric nodes

To start a store, run:
~~~
  $ fab-store --name [store_name]
~~~
Unless you are using mobile code, the store must have the classes of
objects that it will store on its classpath.  This can be specified with
the `--jvm-cp` option:
~~~
  $ fab-store --jvm-cp /path/to/classfiles --name [store_name]
~~~

