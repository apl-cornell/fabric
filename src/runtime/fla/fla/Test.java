package fla;

import java.util.HashSet;
import java.util.Set;

import fla.ARBAC97.CanAssignRule;
import fla.ARBAC97.CanRevokeRule;
import fla.principals.OwnedPrincipal;
import fla.principals.NodePrincipal;
import fla.principals.PrimitivePrincipal;
import fla.principals.Principal;
import fla.principals.PrincipalUtil;

public class Test {
  public static void main(String[] args) {
    smokeTest();
    DelegationLoophole.test();
    InfoScreen.test();
    DelegationLeak.test();
    arbac97Test();

    System.out.println("All tests passed.");
  }

  private static final Principal bottom = PrincipalUtil.bottom();
  private static final Principal top = PrincipalUtil.top();

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
          (OwnedPrincipal) new NodePrincipal("group").owner(owner);
      this.exclusionGroup =
          (OwnedPrincipal) new NodePrincipal("exclusionGroup")
              .owner(owner);
    }

    /**
     * @param p the principal to add to the group
     * @param s the confidentiality label for the new delegation
     * @param store a principal that acts for {@code s→} ∧ {@code this.group←}
     * @return true iff delegation was successful
     */
    boolean addMember(MemberType memberType, Principal p, Principal s,
        NodePrincipal store) {
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
      NodePrincipal loblaw = new NodePrincipal("loblaw");
      Principal alice = new NodePrincipal("alice");
      Principal bob = new NodePrincipal("bob");

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
      NodePrincipal a = new NodePrincipal("alice");
      PrimitivePrincipal b = new NodePrincipal("bob");
      PrimitivePrincipal c = new NodePrincipal("charlie");
      NodePrincipal e = new NodePrincipal("eve");

      a.addDelegatesTo(b, c, a);
      e.addDelegatesTo(a, e, a);
      e.addDelegatesTo(a, e, e);
      if (PrincipalUtil.actsFor(e, c, b, a, bottom)) {
        throw new Error(e + " learned " + c + " ≽ " + b);
      }
    }

    static void test02() {
      NodePrincipal a = new NodePrincipal("alice");
      PrimitivePrincipal b = new NodePrincipal("bob");
      PrimitivePrincipal c = new NodePrincipal("charlie");
      NodePrincipal e = new NodePrincipal("eve");

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
      NodePrincipal acme = new NodePrincipal("acme");
      PrimitivePrincipal apex = new NodePrincipal("apex");
      NodePrincipal bob = new NodePrincipal("bob");
      PrimitivePrincipal emp = new NodePrincipal("emp");

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

  static void arbac97Test() {
    NodePrincipal acme = new NodePrincipal("Acme");
    Principal acmeHR = new NodePrincipal("HR").owner(acme);
    Principal acmePL = new NodePrincipal("ProgramLead").owner(acme);
    Principal acmeEmp = new NodePrincipal("Emp").owner(acme);
    Principal acmeEng = new NodePrincipal("Eng").owner(acme);
    Principal acmeInteg = acme.integrity();

    // acmeHR acts for acmeEmp, and acmePL acts for acmeEng.
    acme.addDelegatesTo(acmeEmp, acmeHR, acmeInteg);
    acme.addDelegatesTo(acmeEng, acmePL, acmeInteg);

    // Alice is a program lead at Acme.
    Principal acmeAlice = new NodePrincipal("alice").owner(acme);
    acme.addDelegatesTo(acmePL, acmeAlice, acmeInteg);

    // Bob works in HR at Acme.
    Principal acmeBob = new NodePrincipal("bob").owner(acme);
    acme.addDelegatesTo(acmeHR, acmeBob, acmeInteg);

    // Chuck is a freelance programmer.
    Principal chuck = new NodePrincipal("chuck");

    // Set up the trust management rules for Acme.
    ARBAC97 acmeRules;
    {
      Set<ARBAC97.CanAssignRule> canAssign = new HashSet<>();
      Set<ARBAC97.CanRevokeRule> canRevoke = new HashSet<>();
      Set<ARBAC97.CanAssignPRule> canAssignP = new HashSet<>();
      Set<ARBAC97.CanRevokePRule> canRevokeP = new HashSet<>();
      Set<ARBAC97.CanModifyRule> canModify = new HashSet<>();

      // Members of the HR department can hire new employees.
      canAssign.add(new CanAssignRule(acme, acmeHR, bottom, acmeEmp, acmeEmp));

      // Program leads can recruit employees to the engineering team.
      canAssign.add(new CanAssignRule(acme, acmePL, acmeEmp, acmeEng, acmeEng));

      // Members of the HR department can fire employees.
      canRevoke.add(new CanRevokeRule(acme, acmeHR, acmeEmp, acmeEmp));

      acmeRules =
          new ARBAC97(canAssign, canRevoke, canAssignP, canRevokeP, canModify);
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
      throw new Error(acmeAlice + " unable to add " + chuck + " to " + acmeEng);
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

  private static void smokeTest() {
    // ⊤ should act for ⊥.
    if (!PrincipalUtil.staticallyActsFor(top, bottom)) {
      throw new Error(top + " ⋡ " + bottom);
    }

    Principal a = new NodePrincipal("a");
    Principal b = new NodePrincipal("b");
    Principal c = new NodePrincipal("c");
    Principal d = new NodePrincipal("d");

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
