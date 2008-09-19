package cms.model;

public class GroupGrade {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Group      group;
  private SubProblem subProblem;
  private float      score;
  private boolean    isAveraged;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setGroup      (final Group group)           { this.group      = group;      }
  public void setSubProblem (final SubProblem subProblem) { this.subProblem = subProblem; }
  public void setScore      (final float score)           { this.score      = score;      }
  public void setAveraged   (final boolean isAveraged)    { this.isAveraged = isAveraged; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Group      getGroup()      { return this.group;      }
  public SubProblem getSubProblem() { return this.subProblem; }
  public float      getScore()      { return this.score;      }
  public boolean    getAveraged()   { return this.isAveraged; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public GroupGrade(Group group, float score, boolean isAveraged, SubProblem subproblem) {
    setGroup(group);
    setScore(score);
    setAveraged(isAveraged);
    setSubProblem(subproblem);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
