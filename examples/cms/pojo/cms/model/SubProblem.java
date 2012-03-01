package cms.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import polyglot.types.Type_c;

public class SubProblem {
  private static int nextID = 1;
  
  
  // TODO: subclass for multiple choice
  public static int MULTIPLE_CHOICE = 0;
  public static int FILL_IN = 1;
  public static int SHORT_ANSWER = 2;
  
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private final Assignment assignment;
  private String     name;
  private float      maxScore;
  private boolean    hidden;
  private int        type;
  private int        order;
  private int        answer;
  private final int  id;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setName (final String name)     { this.name = name; }
  public void setMaxScore       (final float maxScore)            { this.maxScore       = maxScore;       }
  public void setHidden         (final boolean hidden)            { this.hidden         = hidden;         }
  public void setType           (final int type)                  { this.type           = type;           }
  public void setOrder          (final int order)                 { this.order          = order;          }
  public void setAnswer         (final int answer)                { this.answer         = answer;         }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Assignment getAssignment()     { return this.assignment;     }
  public String     getName() { return this.name; }
  public float      getMaxScore()       { return this.maxScore;       }
  public boolean    getHidden()         { return this.hidden;         }
  public int        getType()           { return this.type;           }
  public int        getOrder()          { return this.order;          }
  public int        getAnswer()         { return this.answer;         }

  //////////////////////////////////////////////////////////////////////////////
  // managed fields                                                           //
  //////////////////////////////////////////////////////////////////////////////

  final Map/*Group, User*/ assignedTo;  // Managed by GroupAssignedTo
  final Map/*String, Choice*/ choices;  // Managed by Choice

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public SubProblem(Assignment assign, String name, float maxScore, int type, int order, int answer) {
    assignedTo = new HashMap/*Group, User*/();
    choices = new HashMap();
    
    this.assignment = assign;
    setName(name);
    setMaxScore(maxScore);
    setType(type);
    setOrder(order);
    setHidden(false);
    setAnswer(answer);
    
    id = nextID++;
    
    assignment.subProblems.add(this);
    assignment.getCourse().getSemester().database.subProblems.put(toString(), this);
  }

  public SubProblem(Assignment assign) {
    this(assign, "", 0, MULTIPLE_CHOICE, 0, 0);
  }
  
  public boolean isHidden() {
    throw new NotImplementedException();
  }
  
  /**
   * @return the Choice object corresponding to the right answer if this is a
   *         multiple choice problem, or null.
   */
  public Choice getAnswerChoice() {
    if (assignment.getType() == Assignment.ASSIGNMENT)
      return null;
    throw new NotImplementedException("Only implemented for assignments");
  }

  /**
   * Returns a list of all visible and deleted/hidden choices for this
   * subproblem.  Hidden choices come after visible ones.
   */
  public List/*Choice*/ getChoices() {
    List visibleChoices = new ArrayList();
    List hiddenChoices = new ArrayList();
    
    for (Iterator it = choices.values().iterator(); it.hasNext();) {
      Choice choice = (Choice) it.next();
      if (choice.getHidden())
        hiddenChoices.add(choice);
      else visibleChoices.add(choice);
    }
    
    visibleChoices.addAll(hiddenChoices);
    return visibleChoices;
  }
  public Choice getChoice(String text) {
    throw new NotImplementedException();
  }
  
  public String toString() {
    return "" + id;
  }

}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
