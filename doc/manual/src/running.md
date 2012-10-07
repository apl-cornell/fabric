Running Fabric Programs {#running}
=======================
Once this is done, you can start the worker by running:
~~~
  $ fab --name [worker_name] [main_class]
~~~
where [main_class] is the fully-qualified name of the class you wish to
invoke. If the main class is omitted, the worker will simply start and
wait for remote calls. As with stores, workers must have the classes
they will use on their classpath, which can be specified with the
-classpath option or in the CLASSPATH environment variable.
