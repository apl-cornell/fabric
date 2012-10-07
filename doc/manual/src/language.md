The Fabric Language and Compiler {#language}
================================
The Fabric programming language is an extension of the Jif programming
language @cite jif-popl1999. A good place to start is with the [Jif
manual](http://www.cs.cornell.edu/jif/doc/jif-3.3.0/manual.html).

  * @subpage language-overview
  * @subpage compiling


@page language-overview Language overview

Fabric extends Jif with support for nested transactions, explicit remote
calls, and the creation of persistent objects on stores.


Nested transactions
-------------------
Nested transactions are specified with an atomic block, for example:
~~~
  atomic {
    o1.f();
    o2.g();
  }
~~~
The semantics of the atomic block are that statements inside the atomic
block will be executed simultaneously and without interference from
other simultaneous transactions, even those taking place at other
network nodes.


Remote calls
------------
Remote calls are specified explicitly using the syntax `o.m@w(x)`:
~~~
  RemoteWorker w = FabricWorker.getWorker().getWorker("workername");
  o.m@w(args);
~~~
Unlike many other distributed systems with remote calls, the objects
used during the computation of the method `m` need not reside at the
remote worker `w`. Also note that transactions can span multiple remote
calls; these calls will be executed as a single transaction.


Persistent objects
------------------
Persistent objects are created by specifying the storage nodes on which
the objects are to be created. For example:
~~~
  Store s = FabricWorker.getWorker().getStore("storename");
  Object o = new Object@s(args);
~~~
If a store is not specified, objects are created at the same store as
the object `this`. Each worker also contains a local, non-persistent
store. A reference to this store can be obtained by calling
`FabricWorker.getWorker().getLocalStore()`.


@page compiling Compiling and publishing Fabric programs

Compiling
---------
To compile a Fabric program `MyClass.fab`, run the Fabric compiler, `fabc`:
~~~
  $ fabc MyClass.fab
~~~
The `fabc` compiler has many options similar to the `javac` compiler,
including the `-d` option to output classes in a different directory,
the `-classpath` option to specify the classpath, and the `-sourcepath`
option to specify the source path. For a complete list of options, run:
~~~
  $ fabc -help
~~~


Publishing
----------
The Fabric compiler can publish code to a Fabric store, making the code
available for download and use by Fabric workers. Publishing code
requires a running store and a configured worker. (See @ref running.) To
publish, the Fabric compiler needs a few additional parameters:

  * the name of the store that will host the published code,
  * the name of the worker to use for publishing, and
  * a file to which to write codebase information.

The following command will use the worker `MyWorker` to publish
`MyClass.fab` to the store `MyStore`, outputting the URL of the
resulting codebase to the file `codebase.url`:
~~~
  $ fabc -deststore MyStore -worker MyWorker -publish-only \
      -codebase-output-file codebase.url MyClass.fab
~~~

The Fabric compiler can also compile against published code by
specifying the codebase file on the classpath:
~~~
  $ fabc -worker MyWorker -classpath @codebase.url MyClass2.fab
~~~

Code dependent on published code can similarly be published:
~~~
  $ fabc -deststore MyStore -worker MyWorker -publish-only \
      -codebase-output-file codebase2.url \
      -classpath @codebase.url MyClass2.fab
~~~
