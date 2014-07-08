/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
/**
 * <p>
 * New AST nodes for the Fabric language extension. This package also includes
 * with all of the node, extension, and delegate factories.
 * </p>
 * <h3>Extension Factory Classes</h3>
 * <p>
 * There are a number of factory classes associated with building the extension
 * objects. As documented in <a
 * href="http://www.cs.cornell.edu/andru/papers/polyglot.pdf">the CC'03 Polyglot
 * paper</a>, there should be a separate extension object for each language
 * extension. In the case of Fabric (which is an extension of Jif), a properly
 * constructed fabric node should look as follows: <center><img
 * src="doc-files/extobjects.png"/></center> The node itself will be created by
 * a {@link fabric.ast.FabricNodeFactory_c} object, and the delegate will be
 * created by a {@link fabric.ast.FabricDelFactory_c} object, but the extension
 * class factories are a little more complicated.
 * </p>
 * <p>
 * All of the extension factories implement the
 * {@link fabric.ast.FabricExtFactory} interface, even those that produce
 * <code>Jif</code> extensions rather than <code>FabricExt</code> extensions.
 * The <code>FabricExtFactory</code> interface simply designates a factory that
 * is able to create extensions for all of the Fabric AST nodes.
 * </p>
 * <p>
 * The {@link fabric.ast.FabricJifExtFactory_c} class is responsible for
 * creating all of the <code>Jif</code> extensions in the diagram above. It is a
 * simple extension of the <code>JifExtFactory_c</code> that is able to
 * construct <code>Jif</code> extensions for atomic sections (and any other new
 * AST nodes we may add).
 * </p>
 * <p>
 * The {@link fabric.ast.AbstractFabExtFactory_c} class is a skeleton
 * implementation of the <code>FabricExtFactory</code> interface that takes care
 * of chaining extension objects together (much like the
 * <code>polyglot.ast.AbstractExtFactory_c</code> and the
 * <code>jif.ast.AbstractJifExtFactory_c</code> classes). It delegates the
 * actual creation of the extension objects to <code>extFooImpl()</code>
 * methods, which by default simply call the parent factory methods.
 * </p>
 * <p>
 * Finally, the {@link fabric.ast.FabricFabExtFactory_c} class creates all of
 * the <code>FabricExt</code> classes in the above diagram. It extends the
 * <code>AbstractFabExtFactory_c</code> class and overrides some of the
 * <code>extFooImpl</code> methods to create the actual extension objects used
 * by fabric.
 * </p>
 * <p>
 * Here is a diagram showing how it all fits together: <center> <a
 * href="doc-files/factories.png"><img width="440px"
 * src="doc-files/factories.png"/></a> </center>
 * </p>
 */

package fabric.ast;

