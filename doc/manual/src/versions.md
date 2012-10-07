Version history {#versions}
===============

Version 0.2.0 (Oct 2012)
-------------
* Support for mobile code (see @cite mobile-fabric-2012)
  * Mobile-code label checking
  * Provider-bounded label checking
  * Type fingerprint checking for remote calls
  * Type fingerprint checking when objects are loaded
  * Access-label checking

* Support for heterogeneous field labels

* Workers have interactive consoles. These can be used to run Fabric
  programs or invoke the Fabric compiler from within the worker.

* Workers listen for remote administrative connections. If a second
  instance of a worker is launched, it will attach to the first instance
  on the administrative port and execute commands within the first
  instance. This is a convenience feature to enable scripting of worker
  commands.

* Added `DummyFetchManager`, which implements a degenerate dissemination
  layer.

* Added `bin/dump-bdb`, which dumps the contents of a store's BDB-backed
  object database

* Various performance improvements.


Version 0.1.0 (Sep 2010)
-------------
Initial release.
