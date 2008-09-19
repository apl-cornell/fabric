package cms.model;

/**
 * Contains information for source files associated with a survey answer.  An
 * answer holds the answer to one Subproblem.
 */
public class Answer {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private AnswerSet  answerSet;
  private SubProblem subProblem;
  private String     text;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setAnswerSet  (final AnswerSet answerSet)   { this.answerSet  = answerSet;  }
  public void setSubProblem (final SubProblem subProblem) { this.subProblem = subProblem; }
  public void setText       (final String text)           { this.text       = text;       }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public AnswerSet  getAnswerSet()  { return this.answerSet;  }
  public SubProblem getSubProblem() { return this.subProblem; }
  public String     getText()       { return this.text;       }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Answer(AnswerSet answerSet, SubProblem sub, String text) {
    this.answerSet  = answerSet;
    this.subProblem = sub;
    this.text       = text;
  }
}
  
/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
