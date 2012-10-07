Running Fabric {#running}
==============

There are three types of nodes in the Fabric design: stores, workers,
and dissemination nodes. In the current implementation, there are no
separate dissemination nodes; rather each worker and store participates
as a peer in the dissemination network.


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


Once this is done, you can start the worker by running:
~~~
  $ fab --name [worker_name] [main_class]
~~~
where [main_class] is the fully-qualified name of the class you wish to
invoke. If the main class is omitted, the worker will simply start and
wait for remote calls. As with stores, workers must have the classes
they will use on their classpath, which can be specified with the
-classpath option or in the CLASSPATH environment variable.
