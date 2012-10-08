Compiling and publishing programs {#compiling}
=================================

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
