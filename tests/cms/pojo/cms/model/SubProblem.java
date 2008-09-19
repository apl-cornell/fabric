package cms.model;

import java.util.Collection;
import java.util.Iterator;

public class SubProblem {
  
  // TODO: subclass for multiple choice
  public static int MULTIPLE_CHOICE = 0;
  public static int FILL_IN = 1;
  public static int SHORT_ANSWER = 2;
  
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Assignment assignment;
  private String     subProblemName;
  private float      maxScore;
  private boolean    hidden;
  private int        type;
  private int        order;
  private int        answer;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setAssignment     (final Assignment assignment)     { this.assignment     = assignment;     }
  public void setSubProblemName (final String subProblemName)     { this.subProblemName = subProblemName; }
  public void setMaxScore       (final float maxScore)            { this.maxScore       = maxScore;       }
  public void setHidden         (final boolean hidden)            { this.hidden         = hidden;         }
  public void setType           (final int type)                  { this.type           = type;           }
  public void setOrder          (final int order)                 { this.order          = order;          }
  public void setAnswer         (final int answer)                { this.answer         = answer;         }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Assignment getAssignment()     { return this.assignment;     }
  public String     getSubProblemName() { return this.subProblemName; }
  public float      getMaxScore()       { return this.maxScore;       }
  public boolean    getHidden()         { return this.hidden;         }
  public int        getType()           { return this.type;           }
  public int        getOrder()          { return this.order;          }
  public int        getAnswer()         { return this.answer;         }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public SubProblem(Assignment assign, String name, float maxScore, int type, int order, int answer) {
    setAssignment(assignment);
    setSubProblemName(name);
    setMaxScore(maxScore);
    setType(type);
    setOrder(order);
    setHidden(false);
    setAnswer(answer);
  }

  public SubProblem(Assignment assign) {
    throw new NotImplementedException();
  }
  public boolean isHidden() {
    throw new NotImplementedException();
  }
  
  /**
   * @return the Choice object corresponding to the right answer if this is a
   *         multiple choice problem, or null.
   */
  public Choice getAnswerChoice() {
    throw new NotImplementedException();
  }
  public Collection/*Choice*/ getChoices() {
    throw new NotImplementedException();
  }
  public Choice getChoice(String text) {
    throw new NotImplementedException();
  }

}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
