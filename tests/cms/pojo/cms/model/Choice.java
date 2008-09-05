package cms.model;

public class Choice {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private SubProblem subProblem;
  private String     letter;
  private String     text;
  private boolean    hidden;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setSubProblem (final SubProblem subProblem) { this.subProblem = subProblem; }
  public void setLetter     (final String letter)         { this.letter       = letter;       }
  public void setText       (final String text)           { this.text         = text;         }
  public void setHidden     (final boolean hidden)        { this.hidden       = hidden;       }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public SubProblem getSubProblem() { return this.subProblem; }
  public String     getLetter()     { return this.letter;     }
  public String     getText()       { return this.text;       }
  public boolean    getHidden()     { return this.hidden;     }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  Choice(SubProblem subproblem, String letter, String text, boolean hidden) {
    setSubProblem(subproblem);
    setLetter(letter);
    setText(text);
    setHidden(hidden);
  }
  public Choice(SubProblem sp) {
    throw new NotImplementedException();
  }
  public void remove() {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
