package cms.model;

public class Choice {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private final SubProblem subProblem;
  private String     letter;
  private String     text;
  private boolean    hidden;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

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

  public Choice(SubProblem subproblem, String letter, String text, boolean hidden) {
    this.subProblem = subproblem;
    setLetter(letter);
    setText(text);
    setHidden(hidden);
    
    subproblem.choices.put(toString(), this);
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
