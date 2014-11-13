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
    URA97.test();

    System.out.println("All tests passed.");
  }

  private static final Principal bottom = PrincipalUtil.bottom();
  private static final Principal top = PrincipalUtil.top();

  private static class URA97 {
    /**
     * Represents a rule specifying which administrators can assign which roles
     * to which users.
     *
     * With this rule, an administrator acting for {@code ar} may assign users
     * that act for {@code c} to roles r between {@code ar:xiMin} and
     * {@code ar:xiMax} (inclusive). To assign a role r to a user u, the
     * acts-for relationship ar:u ≽ r is established.
     */
    static class CanAssignRule {
      /**
       * The administrative authority required to assign roles with this rule.
       */
      final PrimitivePrincipal ar;

      /**
       * The role-membership criterion: for all roles that can be assigned with
       * this rule, the members of those roles must have at least {@code c}'s
       * authority.
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

      public CanAssignRule(PrimitivePrincipal ar, Principal c, Principal xiMin,
          Principal xiMax) {
        this.ar = ar;
        this.c = c;
        this.xiMin = xiMin;
        this.xiMax = xiMax;
      }

    }

    final Set<CanAssignRule> canAssign;

    URA97(Set<CanAssignRule> canAssign) {
      this.canAssign = Collections.unmodifiableSet(new HashSet<>(canAssign));
    }

    /**
     * Assigns user {@code u} to role {@code r} using administrator {@code a}'s
     * authority.
     *
     * @return true iff successful (even if {@code u} already has role {@code r})
     */
    boolean assignUser(Principal a, Principal u, Principal r) {
      for (CanAssignRule rule : canAssign) {
        final PrimitivePrincipal ar = rule.ar;
        final Principal c = rule.c;
        final Principal arXiMin = rule.xiMin.owner(ar);
        final Principal arXiMax = rule.xiMax.owner(ar);
        final Principal arInteg = ar.integrity();

        // Ensure a ≽ ar with ar←.
        if (!PrincipalUtil.actsFor(ar, a, ar, arInteg, bottom)) continue;

        // Ensure u ≽ c with ar←.
        if (!PrincipalUtil.actsFor(ar, u, c, arInteg, bottom)) continue;

        // Ensure r ≽ ar:xiMin with ar←.
        if (!PrincipalUtil.actsFor(ar, r, arXiMin, arInteg, bottom)) continue;

        // Ensure ar:xiMax ≽ r with ar←.
        if (!PrincipalUtil.actsFor(ar, arXiMax, r, arInteg, bottom)) continue;

        // Establish the delegation ar:u ≽ r with ar←.
        ar.addDelegatesTo(r, ar.project(u), arInteg);
      }

      return false;
    }

    static void test() {
      // TODO
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
