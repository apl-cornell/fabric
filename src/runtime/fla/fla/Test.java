package fla;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import fla.principals.OwnedPrincipal;
import fla.principals.PrimitivePrincipal;
import fla.principals.Principal;
import fla.principals.PrincipalUtil;

public class Test {
  public static void main(String[] args) {
    smokeTest();
    DelegationLoophole.test();
    InfoScreen.test();
    DelegationLeak.test();
    ARBAC97.test();

    System.out.println("All tests passed.");
  }

  private static final Principal bottom = PrincipalUtil.bottom();
  private static final Principal top = PrincipalUtil.top();

  private static class ARBAC97 {
    /**
     * Represents a rule specifying which administrators can assign which roles
     * to which users.
     *
     * With this rule, an administrator, in the context of an organization
     * {@code o}, acting for {@code ar} may assign a user having role {@code c}
     * to any role between {@code xiMin} and {@code xiMax} (inclusive). To
     * assign a role {@code r} to a user {@code u}, the acts-for relationship
     * {@code o:u ≽ r} is established at {@code o} with label {@code ar←}.
     *
     * The invariant {@code ar} ≽ {@code xiMax} is assumed to hold.
     */
    static class CanAssignRule {
      /**
       * The owner.
       */
      final PrimitivePrincipal o;

      /**
       * The administrative authority required to assign roles with this rule.
       */
      final Principal ar;

      /**
       * The role-membership criterion: for all roles that can be assigned with
       * this rule, the members of those roles must already have role c.
       */
      final Principal c;

      /**
       * The minimum role that can be assigned with this rule.
       */
      final Principal xiMin;

      /**
       * The maximum role that can be assigned with this rule.
       */
      final Principal xiMax;

      public CanAssignRule(PrimitivePrincipal o, Principal ar, Principal c,
          Principal xiMin, Principal xiMax) {
        this.o = o;
        this.ar = ar;
        this.c = c;
        this.xiMin = xiMin;
        this.xiMax = xiMax;
      }

      /**
       * Uses this rule to assign user {@code u} to role {@code r} using
       * administrator {@code a}'s authority.
       *
       * @return true iff successful (even if {@code u} already has role
       *          {@code r})
       */
      boolean assignUser(Principal a, Principal u, Principal r) {
        final Principal ou = o.project(u);

        // Ensure a ≽ ar with ar←.
        if (!PrincipalUtil.actsFor(ar, a, ar, ar.integrity(), bottom))
          return false;

        // Ensure o:u ≽ c with c←.
        if (!PrincipalUtil.actsFor(ar, ou, c, c.integrity(), bottom))
          return false;

        // Ensure r ≽ xiMin with xiMin←.
        if (!PrincipalUtil.actsFor(ar, r, xiMin, xiMin.integrity(), bottom))
          return false;

        // Ensure xiMax ≽ r with r←.
        if (!PrincipalUtil.actsFor(ar, xiMax, r, r.integrity(), bottom))
          return false;

        // Establish the delegation o:u ≽ r with ar←.
        o.addDelegatesTo(r, ou, ar.integrity());
        return true;
      }
    }

    /**
     * Represents a rule specifying which administrators can revoke which roles.
     *
     * With this rule, an administrator, in the context of an organization
     * {@code o}, acting for {@code ar} may revoke from any user any role
     * between {@code xiMin} and {@code xiMax} (inclusive). To revoke a role r
     * from a user u, the acts-for relationship o:u ≽ r is revoked at {@code o}.
     */
    static class CanRevokeRule {
      /**
       * The owner.
       */
      final PrimitivePrincipal o;

      /**
       * The administrative authority required to revoke roles with this rule.
       */
      final Principal ar;

      /**
       * The minimum role that can be revoked with this rule.
       */
      final Principal xiMin;

      /**
       * The maximum role that can be revoked with this rule.
       */
      final Principal xiMax;

      public CanRevokeRule(PrimitivePrincipal o, Principal ar, Principal xiMin,
          Principal xiMax) {
        super();
        this.o = o;
        this.ar = ar;
        this.xiMin = xiMin;
        this.xiMax = xiMax;
      }

      /**
       * Uses this rule to revoke role {@code r} from user {@code u} using
       * administrator {@code a}'s authority.
       *
       * @return true iff successful (even if {@code u} did not already have
       *          role {@code r})
       */
      boolean revokeUser(Principal a, Principal u, Principal r) {
        final Principal ou = o.project(u);

        // Ensure a ≽ ar with ar←.
        if (!PrincipalUtil.actsFor(ar, a, ar, ar.integrity(), bottom))
          return false;

        // Ensure r ≽ xiMin with xiMin←.
        if (!PrincipalUtil.actsFor(ar, r, xiMin, xiMin.integrity(), bottom))
          return false;

        // Ensure xiMax ≽ r with r←.
        if (!PrincipalUtil.actsFor(ar, xiMax, r, r.integrity(), bottom))
          return false;

        // Revoke the delegation o:u ≽ r with ar←.
        o.removeDelegatesTo(r, ou, ar.integrity());
        return true;
      }
    }

    /**
     * Represents a rule specifying which administrators can assign which
     * permissions to which roles.
     *
     * With this rule, an administrator, in the context of an organization
     * {@code o}, acting for {@code ar} may assign a permission {@code p}
     * to any role between {@code xiMin} and {@code xiMax} (inclusive), if
     * {@code p} was previously assigned to {@code c}. To assign a permission
     * {@code p} to a role {@code r}, the acts-for relationship
     * {@code r ≽ p} is established at {@code o} with label {@code ar←}.
     *
     * The invariant {@code ar} ≽ {@code p} is assumed to hold.
     */
    static class CanAssignPRule {
      /**
       * The owner.
       */
      final PrimitivePrincipal o;

      /**
       * The administrative authority required to assign permissions with this
       * rule.
       */
      final Principal ar;

      /**
       * The permission-assignment criterion: permissions that can be assigned
       * with this rule must already be assigned to c.
       */
      final Principal c;

      /**
       * The minimum role to which permissions can be assigned with this rule.
       */
      final Principal xiMin;

      /**
       * The maximum role to which permissions can be assigned with this rule.
       */
      final Principal xiMax;

      public CanAssignPRule(PrimitivePrincipal o, Principal ar, Principal c,
          Principal xiMin, Principal xiMax) {
        this.o = o;
        this.ar = ar;
        this.c = c;
        this.xiMin = xiMin;
        this.xiMax = xiMax;
      }

      /**
       * Uses this rule to assign permission {@code p} to role {@code r} using
       * administrator {@code a}'s authority.
       *
       * @return true iff successful (even if {@code r} already has permission
       *          {@code p})
       */
      boolean assignPermission(Principal a, Principal p, Principal r) {
        // Ensure a ≽ ar with ar←.
        if (!PrincipalUtil.actsFor(ar, a, ar, ar.integrity(), bottom))
          return false;

        // Ensure p ≽ c with c←.
        if (!PrincipalUtil.actsFor(ar, p, c, c.integrity(), bottom))
          return false;

        // Ensure r ≽ xiMin with xiMin←.
        if (!PrincipalUtil.actsFor(ar, r, xiMin, xiMin.integrity(), bottom))
          return false;

        // Ensure xiMax ≽ r with r←.
        if (!PrincipalUtil.actsFor(ar, xiMax, r, r.integrity(), bottom))
          return false;

        // Establish the delegation r ≽ p with ar←.
        o.addDelegatesTo(p, r, ar.integrity());
        return true;
      }
    }

    /**
     * Represents a rule specifying which administrators can revoke which
     * permissions.
     *
     * With this rule, an administrator, in the context of an organization
     * {@code o}, acting for {@code ar} may revoke any permission from any role
     * between {@code xiMin} and {@code xiMax} (inclusive). To revoke a
     * permission {@code p} from a role {@code r}, the acts-for relationship r ≽
     * p is revoked at {@code o}.
     */
    static class CanRevokePRule {
      /**
       * The owner.
       */
      final PrimitivePrincipal o;

      /**
       * The administrative authority required to revoke permissions with this
       * rule.
       */
      final Principal ar;

      /**
       * The minimum role from which permissions can be revoked with this rule.
       */
      final Principal xiMin;

      /**
       * The maximum role from which permissions can be revoked with this rule.
       */
      final Principal xiMax;

      public CanRevokePRule(PrimitivePrincipal o, Principal ar,
          Principal xiMin, Principal xiMax) {
        super();
        this.o = o;
        this.ar = ar;
        this.xiMin = xiMin;
        this.xiMax = xiMax;
      }

      /**
       * Uses this rule to revoke permission {@code p} from role {@code r} using
       * administrator {@code a}'s authority.
       *
       * @return true iff successful (even if {@code r} did not already have
       *          permission {@code p})
       */
      boolean revokePermission(Principal a, Principal p, Principal r) {
        // Ensure a ≽ ar with ar←.
        if (!PrincipalUtil.actsFor(ar, a, ar, ar.integrity(), bottom))
          return false;

        // Ensure r ≽ xiMin with xiMin←.
        if (!PrincipalUtil.actsFor(ar, r, xiMin, xiMin.integrity(), bottom))
          return false;

        // Ensure xiMax ≽ r with r←.
        if (!PrincipalUtil.actsFor(ar, xiMax, r, r.integrity(), bottom))
          return false;

        // Revoke the delegation r ≽ p with ar←.
        o.removeDelegatesTo(p, r, ar.integrity());
        return true;
      }
    }

    final Set<CanAssignRule> canAssign;
    final Set<CanRevokeRule> canRevoke;
    final Set<CanAssignPRule> canAssignP;
    final Set<CanRevokePRule> canRevokeP;

    ARBAC97(Set<CanAssignRule> canAssign, Set<CanRevokeRule> canRevoke,
        Set<CanAssignPRule> canAssignP, Set<CanRevokePRule> canRevokeP) {
      this.canAssign = Collections.unmodifiableSet(new HashSet<>(canAssign));
      this.canRevoke = Collections.unmodifiableSet(new HashSet<>(canRevoke));
      this.canAssignP = Collections.unmodifiableSet(new HashSet<>(canAssignP));
      this.canRevokeP = Collections.unmodifiableSet(new HashSet<>(canRevokeP));
    }

    /**
     * Assigns user {@code u} to role {@code r} using administrator {@code a}'s
     * authority.
     *
     * @return true iff successful (even if {@code u} already has role {@code r})
     */
    boolean assignUser(Principal a, Principal u, Principal r) {
      for (CanAssignRule rule : canAssign) {
        if (rule.assignUser(a, u, r)) return true;
      }

      return false;
    }

    /**
     * Revokes role {@code r} from user {@code u} using administrator {@code
     * a}'s authority.
     *
     * @return true iff successful (even if {@code u} did not already have role
     *          {@code r})
     */
    boolean revokeUser(Principal a, Principal u, Principal r) {
      for (CanRevokeRule rule : canRevoke) {
        if (rule.revokeUser(a, u, r)) return true;
      }

      return false;
    }

    /**
     * Assigns permission {@code p} to role {@code r} using administrator {@code
     * a}'s authority.
     *
     * @return true iff successful (even if {@code r} already has permission
     *          {@code p})
     */
    boolean assignPermission(Principal a, Principal p, Principal r) {
      for (CanAssignPRule rule : canAssignP) {
        if (rule.assignPermission(a, p, r)) return true;
      }

      return false;
    }

    /**
     * Revokes permission {@code p} from role {@code r} using administrator
     * {@code a}'s authority.
     *
     * @return true iff successful (even if {@code r} did not already have
     *          permission {@code p})
     */
    boolean revokePermission(Principal a, Principal p, Principal r) {
      for (CanRevokePRule rule : canRevokeP) {
        if (rule.revokePermission(a, p, r)) return true;
      }

      return false;
    }

    static void test() {
      PrimitivePrincipal acme = new PrimitivePrincipal("Acme");
      Principal acmeHR = new PrimitivePrincipal("HR").owner(acme);
      Principal acmePL = new PrimitivePrincipal("ProgramLead").owner(acme);
      Principal acmeEmp = new PrimitivePrincipal("Emp").owner(acme);
      Principal acmeEng = new PrimitivePrincipal("Eng").owner(acme);
      Principal acmeInteg = acme.integrity();

      // acmeHR acts for acmeEmp, and acmePL acts for acmeEng.
      acme.addDelegatesTo(acmeEmp, acmeHR, acmeInteg);
      acme.addDelegatesTo(acmeEng, acmePL, acmeInteg);

      // Alice is a program lead at Acme.
      Principal acmeAlice = new PrimitivePrincipal("alice").owner(acme);
      acme.addDelegatesTo(acmePL, acmeAlice, acmeInteg);

      // Bob works in HR at Acme.
      Principal acmeBob = new PrimitivePrincipal("bob").owner(acme);
      acme.addDelegatesTo(acmeHR, acmeBob, acmeInteg);

      // Chuck is a freelance programmer.
      Principal chuck = new PrimitivePrincipal("chuck");

      // Set up the trust management rules for Acme.
      ARBAC97 acmeRules;
      {
        Set<CanAssignRule> canAssign = new HashSet<>();
        Set<CanRevokeRule> canRevoke = new HashSet<>();
        Set<CanAssignPRule> canAssignP = new HashSet<>();
        Set<CanRevokePRule> canRevokeP = new HashSet<>();

        // Members of the HR department can hire new employees.
        canAssign
        .add(new CanAssignRule(acme, acmeHR, bottom, acmeEmp, acmeEmp));

        // Program leads can recruit employees to the engineering team.
        canAssign
        .add(new CanAssignRule(acme, acmePL, acmeEmp, acmeEng, acmeEng));

        // Members of the HR department can fire employees.
        canRevoke.add(new CanRevokeRule(acme, acmeHR, acmeEmp, acmeEmp));

        acmeRules = new ARBAC97(canAssign, canRevoke, canAssignP, canRevokeP);
      }

      // Alice should not be able to add Chuck to the engineering team.
      if (acmeRules.assignUser(acmeAlice, chuck, acmeEng)) {
        throw new Error(acmeAlice + " added " + chuck + " to " + acmeEng);
      }

      // Bob should not be able to add Chuck to the engineering team.
      if (acmeRules.assignUser(acmeBob, chuck, acmeEng)) {
        throw new Error(acmeBob + " added " + chuck + " to " + acmeEng);
      }

      // Alice should not be able to hire Chuck.
      if (acmeRules.assignUser(acmeAlice, chuck, acmeEmp)) {
        throw new Error(acmeAlice + " added " + chuck + " to " + acmeEmp);
      }

      // Bob should be able to hire Chuck.
      if (!acmeRules.assignUser(acmeBob, chuck, acmeEmp)) {
        throw new Error(acmeBob + " unable to add " + chuck + " to " + acmeEmp);
      }

      // Bob should still not be able to add Chuck to the engineering team.
      if (acmeRules.assignUser(acmeBob, chuck, acmeEng)) {
        throw new Error(acmeBob + " added " + chuck + " to " + acmeEng);
      }

      // Alice should now be able to add Chuck to the engineering team.
      if (!acmeRules.assignUser(acmeAlice, chuck, acmeEng)) {
        throw new Error(acmeAlice + " unable to add " + chuck + " to "
            + acmeEng);
      }

      // Chuck should be a member of acmeEng.
      Principal acmeChuck = acme.project(chuck);
      if (!PrincipalUtil.actsFor(acme, acmeChuck, acmeEng, acmePL.integrity(),
          bottom)) {
        throw new Error(acmeChuck + " does not act for " + acmeEng);
      }

      // Chuck should be a member of acmeEmp.
      if (!PrincipalUtil.actsFor(acme, acmeChuck, acmeEmp, acmeHR.integrity(),
          bottom)) {
        throw new Error(acmeChuck + " does not act for " + acmeEmp);
      }

      // Alice should not be able to fire Chuck.
      if (acmeRules.revokeUser(acmeAlice, chuck, acmeEmp)) {
        throw new Error(acmeAlice + " removed " + chuck + " from " + acmeEmp);
      }

      // Bob should be able to fire Chuck.
      if (!acmeRules.revokeUser(acmeBob, chuck, acmeEmp)) {
        throw new Error(acmeBob + " unable to remove " + chuck + " from "
            + acmeEmp);
      }

      // Chuck should still be a member of acmeEng.
      if (!PrincipalUtil.actsFor(acme, acmeChuck, acmeEng, acmePL.integrity(),
          bottom)) {
        throw new Error(acmeChuck + " no longer acts for " + acmeEng);
      }

      // Chuck should not be a member of acmeEmp.
      if (PrincipalUtil.actsFor(acme, acmeChuck, acmeEmp, acmeHR.integrity(),
          bottom)) {
        throw new Error(acmeChuck + " still acts for " + acmeEmp);
      }
    }
  }

  private static class InfoScreen {
    enum MemberType {
      EXCLUDE, INCLUDE
    }

    /**
     * The owner.
     */
    final Principal owner;

    /**
     * The (owned) primary group.
     */
    final OwnedPrincipal group;

    /**
     * The (owned) exclusion group.
     */
    final OwnedPrincipal exclusionGroup;

    InfoScreen(Principal owner) {
      this.owner = owner;
      this.group =
          (OwnedPrincipal) new PrimitivePrincipal("group").owner(owner);
      this.exclusionGroup =
          (OwnedPrincipal) new PrimitivePrincipal("exclusionGroup")
      .owner(owner);
    }

    /**
     * @param p the principal to add to the group
     * @param s the confidentiality label for the new delegation
     * @param store a principal that acts for {@code s→} ∧ {@code this.group←}
     * @return true iff delegation was successful
     */
    boolean addMember(MemberType memberType, Principal p, Principal s,
        PrimitivePrincipal store) {
      // Turn p into o:p.
      p = p.owner(owner);

      // The group to which the principal is to be added.
      final OwnedPrincipal targetGroup;

      // The other group, of which the principal should not be a member.
      final OwnedPrincipal otherGroup;

      if (memberType == MemberType.INCLUDE) {
        targetGroup = group;
        otherGroup = exclusionGroup;
      } else {
        targetGroup = exclusionGroup;
        otherGroup = group;
      }

      // Drop the integrity component of s, if any.
      s = s.confidentiality();

      // Assume store ≽ s ∧ this.group←.

      // The label s ∧ this.group←.
      Principal maxLabel = PrincipalUtil.join(s, group.integrity());
      if (PrincipalUtil.actsFor(store, p, otherGroup, maxLabel, s))
        return false;

      store.addDelegatesTo(targetGroup, p, maxLabel);
      return true;
    }

    static void test() {
      PrimitivePrincipal loblaw = new PrimitivePrincipal("loblaw");
      Principal alice = new PrimitivePrincipal("alice");
      Principal bob = new PrimitivePrincipal("bob");

      InfoScreen screen = new InfoScreen(loblaw);

      // Should be able to add alice to the inclusion group.
      if (!screen.addMember(InfoScreen.MemberType.INCLUDE, alice, bottom,
          loblaw)) {
        throw new Error("failed to add alice to inclusion group");
      }

      // Should be able to add bob to the exclusion group.
      if (!screen.addMember(InfoScreen.MemberType.EXCLUDE, bob, bottom, loblaw)) {
        throw new Error("failed to add bob to exclusion group");
      }

      // Shouldn't be able to add alice to the exclusion group.
      if (screen
          .addMember(InfoScreen.MemberType.EXCLUDE, alice, bottom, loblaw)) {
        throw new Error("shouldn't have added alice to exclusion group");
      }

      // Shouldn't be able to add bob to the inclusion group.
      if (screen.addMember(InfoScreen.MemberType.INCLUDE, bob, bottom, loblaw)) {
        throw new Error("shouldn't have added bob to exclusion group");
      }

      loblaw.addDelegatesTo(alice, bob,
          PrincipalUtil.join(alice.integrity(), bob.integrity()));

      // Shouldn't have loblaw:bob acting for the inclusion group with the inclusion group's integrity.
      Principal loblawBob = loblaw.project(bob);
      Principal screenGroupInteg = screen.group.integrity();
      if (PrincipalUtil.actsFor(loblaw, loblawBob, screen.group,
          screenGroupInteg, bottom)) {
        throw new Error(loblawBob + " ≽ " + screen.group + " with "
            + screenGroupInteg);
      }
    }
  }

  private static class DelegationLeak {
    static void test() {
      test01();
      test02();
    }

    static void test01() {
      PrimitivePrincipal a = new PrimitivePrincipal("alice");
      PrimitivePrincipal b = new PrimitivePrincipal("bob");
      PrimitivePrincipal c = new PrimitivePrincipal("charlie");
      PrimitivePrincipal e = new PrimitivePrincipal("eve");

      a.addDelegatesTo(b, c, a);
      e.addDelegatesTo(a, e, a);
      e.addDelegatesTo(a, e, e);
      if (PrincipalUtil.actsFor(e, c, b, a, bottom)) {
        throw new Error(e + " learned " + c + " ≽ " + b);
      }
    }

    static void test02() {
      PrimitivePrincipal a = new PrimitivePrincipal("alice");
      PrimitivePrincipal b = new PrimitivePrincipal("bob");
      PrimitivePrincipal c = new PrimitivePrincipal("charlie");
      PrimitivePrincipal e = new PrimitivePrincipal("eve");

      a.addDelegatesTo(b, c, a);
      e.addDelegatesTo(a, e,
          PrincipalUtil.join(bottom.confidentiality(), top.integrity()));
      if (PrincipalUtil.actsFor(e, c, b, a, bottom)) {
        throw new Error(e + " learned " + c + " ≽ " + b);
      }
    }
  }

  private static class DelegationLoophole {
    static void test() {
      PrimitivePrincipal acme = new PrimitivePrincipal("acme");
      PrimitivePrincipal apex = new PrimitivePrincipal("apex");
      PrimitivePrincipal bob = new PrimitivePrincipal("bob");
      PrimitivePrincipal emp = new PrimitivePrincipal("emp");

      Principal acmeEmp = acme.project(emp);
      Principal acmeBob = acme.project(bob);
      Principal acmeInteg = acme.integrity();
      Principal bobInteg = bob.integrity();

      acme.addDelegatesTo(acmeEmp, acmeBob, acmeInteg);
      bob.addDelegatesTo(bob, apex, bobInteg);

      // Should not have acme:bob→ ⊑ acme:apex→ with acme's integrity.
      Principal acmeBobConf = acmeBob.confidentiality();
      Principal acmeApexConf = acme.project(apex).confidentiality();
      if (PrincipalUtil.flowsTo(acme, acmeBobConf, acmeApexConf, acmeInteg,
          bottom)) {
        throw new Error(acmeBobConf + " ⊑ " + acmeApexConf + " with "
            + acmeInteg);
      }

      // Should not have acme:emp→ ⊑ acme:bob→ with bob's integrity.
      Principal acmeEmpConf = acmeEmp.confidentiality();
      if (PrincipalUtil
          .flowsTo(bob, acmeEmpConf, acmeBobConf, bobInteg, bottom)) {
        throw new Error(acmeEmpConf + " ⊑ " + acmeBobConf + " with " + bobInteg);
      }

      // Should have acme:bob→ ⊑ acme:apex→ with bob's integrity.
      if (!PrincipalUtil.flowsTo(bob, acmeBobConf, acmeApexConf, bobInteg,
          bottom)) {
        throw new Error(acmeBobConf + " ⊑ " + acmeApexConf + " with "
            + bobInteg);
      }

      // Should have acme:emp→ ⊑ acme:apex→ with bob∨apex's integrity.
      Principal bobOrApex = PrincipalUtil.meet(bob, apex);
      Principal bobOrApexInteg = bobOrApex.integrity();
      if (PrincipalUtil.flowsTo(bobOrApex, acmeEmpConf, acmeApexConf,
          bobOrApexInteg, bottom)) {
        throw new Error(acmeEmpConf + " ⊑ " + acmeApexConf + " with "
            + bobOrApexInteg);
      }
    }
  }

  private static void smokeTest() {
    // ⊤ should act for ⊥.
    if (!PrincipalUtil.staticallyActsFor(top, bottom)) {
      throw new Error(top + " ⋡ " + bottom);
    }

    Principal a = new PrimitivePrincipal("a");
    Principal b = new PrimitivePrincipal("b");
    Principal c = new PrimitivePrincipal("c");
    Principal d = new PrimitivePrincipal("d");

    // ⊤ should act for a.
    if (!PrincipalUtil.staticallyActsFor(top, a)) {
      throw new Error(top + " ⋡ " + a);
    }

    // a should act for ⊥.
    if (!PrincipalUtil.staticallyActsFor(a, bottom)) {
      throw new Error(a + " ⋡ " + bottom);
    }

    // Let p1 = (a ∨ b) ∧ (c ∧ d)
    Principal p1 =
        PrincipalUtil.conjunction(PrincipalUtil.disjunction(a, b),
            PrincipalUtil.disjunction(c, d));

    // Let p2 = (a ∧ c) ∨ (a ∧ d) ∨ (b ∧ c) ∨ (b ∧ d)
    Principal p2 =
        PrincipalUtil.disjunction(PrincipalUtil.conjunction(a, c),
            PrincipalUtil.conjunction(a, d), PrincipalUtil.conjunction(b, c),
            PrincipalUtil.conjunction(b, d));

    // p1 and p2 better be equivalent.
    if (!PrincipalUtil.staticallyEquivalent(p1, p2)) {
      throw new Error(p1 + " ≠ " + p2);
    }

    // a ∧ c should act for p1.
    if (!PrincipalUtil.staticallyActsFor(PrincipalUtil.conjunction(a, c), p1)) {
      throw new Error(a + " ∧ " + c + " ⋡ " + p1);
    }

    // a should be the same as a:⊤.
    if (!PrincipalUtil.staticallyEquivalent(a, a.project(top))) {
      throw new Error(a + " ≠ " + a + ":" + top);
    }

    // ⊥ should be the same as a:⊥.
    if (!PrincipalUtil.staticallyEquivalent(bottom, a.project(bottom))) {
      throw new Error(bottom + " ≠ " + a + ":" + bottom);
    }

    // ⊤ should act for ⊤→.
    if (!PrincipalUtil.staticallyActsFor(top, top.confidentiality())) {
      throw new Error(top + " ⋡ " + top + "→");
    }

    // ⊤ should act for ⊤←.
    if (!PrincipalUtil.staticallyActsFor(top, top.integrity())) {
      throw new Error(top + " ⋡ " + top + "←");
    }

    // ⊤→ should not act for ⊤.
    if (PrincipalUtil.staticallyActsFor(top.confidentiality(), top)) {
      throw new Error(top + "→ ≽ " + top);
    }

    // ⊤← should not act for ⊤.
    if (PrincipalUtil.staticallyActsFor(top.integrity(), top)) {
      throw new Error(top + "← ≽ " + top);
    }

    // ⊥ should be the same as ⊥→.
    if (!PrincipalUtil.staticallyEquivalent(bottom, bottom.confidentiality())) {
      throw new Error(bottom + " ≠ " + bottom + "→");
    }

    // ⊥ should be the same as ⊥←.
    if (!PrincipalUtil.staticallyEquivalent(bottom, bottom.integrity())) {
      throw new Error(bottom + " ≠ " + bottom + "←");
    }
  }
}
