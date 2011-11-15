package jif.ast;

import polyglot.ast.Ext;

public interface JifStmtExtFactory
{
    public Ext extDeclassifyStmt();
    public Ext extEndorseStmt(); 
    public Ext extCheckedEndorseStmt(); 
}
