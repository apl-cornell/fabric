package fabric.translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fabil.types.FabILFlags;
import fabric.extension.ClassBodyJifExt_c;
import fabric.types.FabricFieldInstance;
import fabric.visit.FabricToFabilRewriter;
import jif.ast.JifUtil;
import jif.translate.ClassBodyToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import jif.types.label.Label;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Expr;
import polyglot.ast.FieldDecl;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.util.Pair;

public class ClassBodyToFabilExt_c extends ClassBodyToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FabricToFabilRewriter fabrw = (FabricToFabilRewriter) rw;
    ClassBody cb = (ClassBody) node();
    List<ClassMember> members = new ArrayList<>(cb.members().size());

    // Copy cb.members() into members while omitting field decls that will be
    // moved into split fragments. While doing so, build up a map for generating
    // the split-fragment classes. The map will map the names of the classes to
    // a pair containing the class label and its members.
    Map<String, Pair<Label, List<ClassMember>>> splitMap = new HashMap<>();
    for (ClassMember member : cb.members()) {
      if (!(member instanceof FieldDecl)) {
        members.add(member);
        continue;
      }

      // Leave things alone if the field was declared in an interface.
      FieldDecl fd = (FieldDecl) member;
      FabricFieldInstance fi = (FabricFieldInstance) fd.fieldInstance();
      ClassType container = (ClassType) fi.container();
      if (container.flags().isInterface()) {
        members.add(member);
        continue;
      }

      // Leave things alone if the field has no associated split class name.
      String splitName = fi.splitClassName();
      if (splitName == null) {
        members.add(member);
        continue;
      }

      Pair<Label, List<ClassMember>> splitInfo = splitMap.get(splitName);
      List<ClassMember> splitMembers;
      if (splitInfo == null) {
        splitMembers = new ArrayList<>();
        splitInfo = new Pair<>(fi.label(), splitMembers);
        splitMap.put(splitName, splitInfo);
      } else {
        splitMembers = splitInfo.part2();
      }
      splitMembers.add(fd);
    }

    // Add the split fragments.
    members.addAll(makeSplitFragments(fabrw, splitMap));

    // Add the remote-call wrappers.
    ClassBodyJifExt_c cb_ext = (ClassBodyJifExt_c) JifUtil.jifExt(cb);
    List<ClassMember> remote_wrappers = cb_ext.remoteWrappers();
    members.addAll(remote_wrappers);

    return rw.java_nf().ClassBody(cb.position(), members);
  }

  /**
   * Produces the split fragments from the given split map. Each fragment will
   * have a class declaration and a corresponding field declaration.
   */
  protected List<ClassMember> makeSplitFragments(FabricToFabilRewriter rw,
      Map<String, Pair<Label, List<ClassMember>>> splitMap)
      throws SemanticException {
    List<ClassMember> result = new ArrayList<>();
    for (Map.Entry<String, Pair<Label, List<ClassMember>>> entry : splitMap
        .entrySet()) {
      String splitName = entry.getKey();
      Pair<Label, List<ClassMember>> splitInfo = entry.getValue();
      Label splitLabel = splitInfo.part1();
      List<ClassMember> splitMembers = splitInfo.part2();

      // Produce the $initLabels method for the fragment.
      splitMembers.add(makeFragmentInitLabels(rw, splitLabel));

      // Produce the reference to the root object.
      splitMembers.add(rw.qq().parseMember(
          "private final " + rw.currentClass().fullName() + " $root;"));

      // Produce the constructor for the fragment.
      splitMembers
          .add(rw.qq().parseMember("public %s(" + rw.currentClass().fullName()
              + " root) { super(); this.$root = root; }", splitName));

      // Produce the class decl for the fragment.
      ClassDecl splitClassDecl = rw.qq().parseDecl(
          "public static final class %s { %LM }", splitName, splitMembers);

      // Produce the field decl for the fragment and mark it as immutable.
      FieldDecl splitFieldDecl = (FieldDecl) rw.qq()
          .parseMember("public %s %s;", splitName, splitName);
      Flags flags = splitFieldDecl.flags().set(FabILFlags.IMMUTABLE);
      splitFieldDecl = splitFieldDecl.flags(flags);

      // Notify the rewriter that the fragment needs to be initialized.
      rw.addObjectFragment(splitName);

      result.add(splitClassDecl);
      result.add(splitFieldDecl);
    }

    return result;
  }

  /**
   * Produces the $initLabels() method for a class fragment.
   */
  protected ClassMember makeFragmentInitLabels(FabricToFabilRewriter rw,
      Label splitLabel) throws SemanticException {
    // Locate labels at the same store as the object.
    rw = rw.pushLocation(rw.qq().parseExpr("this.$getStore()"));

    // Create the expression that will qualify all accesses to label params and
    // principal params.
    Expr qualifier = rw.qq().parseExpr("this.$root");

    // Simplify the label and translate it to FabIL.
    Expr updateLabelExpr = rw.labelToJava(splitLabel.simplify(), qualifier);

    List<Stmt> body = new ArrayList<>();

    // Initialize the update label.
    body.add(rw.qq().parseStmt("this.$updateLabel = %E;", updateLabelExpr));

    // Initialize the access policy.
    /* XXX The runtime representation of the access policy is currently unused,
     * so just use the confidentiality component of the update label for now.
     */
    body.add(rw.qq()
        .parseStmt("this.$accessPolicy = this.$updateLabel.confPolicy();"));

    // Return this.
    body.add(rw.qq().parseStmt("return this;"));

    ClassMember result =
        rw.qq().parseMember("public fabric.lang.Object %s() { %LS }",
            FabricToFabilRewriter.LABEL_INITIALIZER_METHOD_NAME, body);

    return result;
  }
}
