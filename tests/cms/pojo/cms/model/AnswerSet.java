package cms.model;

import java.util.Collection;
import java.util.Date;

/**
 * A set of responses to a survey.
 */
public class AnswerSet {
  Assignment assignment;
  Group      group, originalGroup;
  String     netID;

  Date       submitted;
  int        lateSubmission;

  Collection/*Answer*/ answers;
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
