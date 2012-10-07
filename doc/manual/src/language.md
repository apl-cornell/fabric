The Fabric Language {#language}
===================
The Fabric programming language is an extension of the Jif programming
language @cite jif-popl1999. A good place to start is with the [Jif
manual](http://www.cs.cornell.edu/jif/doc/jif-3.3.0/manual.html)

Fabric extends Jif with support for nested transactions, explicit remote
calls, and the creation of persistent objects on stores.

  - Nested transactions are specified with an atomic block, for example:
~~~
      atomic {
        o1.f();
        o2.g();
      }
~~~
    The semantics of the atomic block are that statements inside the
    atomic block will be executed simultaneously and without
    interference from other simultaneous transactions, even those taking
    place at other network nodes.

  - Explicit remote calls are specified using the syntax `o.m@w(x)`:
~~~
      RemoteWorker w = FabricWorker.getWorker().getWorker("workername");
      o.m@w(args);
~~~
    Unlike many other distributed systems with remote calls, the objects
    used during the computation of the method `m` need not reside at the
    remote worker `w`. Also note that transactions can span multiple
    remote calls; these calls will be executed as a single transaction.

  - Persistent objects are created by specifying a store to store them.
    For example:
~~~
      Store s = FabricWorker.getWorker().getStore("storename");
      Object o = new Object@s(args);
~~~
    If a store is not specified, objects are created at the same store
    as the object `this`. Each worker also contains a local,
    non-persistent store. A reference to this store can be obtained by
    calling `FabricWorker.getWorker().getLocalStore()`.
