Introduction {#index}
============

Fabric is a language and runtime system that supports secure federated
distributed systems. This document briefly describes the contents of
the package and should get you started running some Fabric examples.
For more information, see our paper "Fabric: A Platform for Secure
Distributed Computation and Storage" published in SOSP 2009
@cite fabric2009.


Requirements
------------
This Fabric distribution builds on Unix. We recommend that you use Java
6 or later. We have experienced problems with older versions. Fabric
is compiled with the [Apache Ant build tool](http://ant.apache.org/).


Package Contents
----------------
The Fabric distribution package contains the following directories:

  - `bin`:
      Scripts for invoking the compiler, runtime, and for configuration
      tasks.

  - `doc`:
      The Fabric internal API documentation and licenses.

      - `doc/api`:
	  The Fabric internal API documentation.

      - `doc/licenses`:
	  Licensing information for Fabric and its packaged
	  dependencies.

      - `doc/manual`:
	  This manual.

  - `etc`:
      Configuration files for Fabric.

  - `lib`:
      Other software that Fabric depends on.

  - `src`:
      Source code for the Fabric compiler and runtime, and some
      libraries built using Fabric.

      - `src/compiler`:
	  The Fabric and FabIL compilers (FabIL is the intermediate
	  language that Fabric compiles into).

      - `src/system`:
	  The parts of the Fabric runtime system that are implemented in
	  Java.

      - `src/runtime`:
	  The parts of the Fabric runtime system that are implemented in
	  FabIL.

      - `src/lib`:
	  Libraries that are built using Fabric, including the Fabric
	  port of the Servlets with Information Flow (SIF) library
	  @cite sif2007.

  - `examples`:
      Fabric example programs (details [here](@ref examples)).

  - `tools`:
      A browser to inspect the contents of a store. A useful aid in the
      development of Fabric programs.
