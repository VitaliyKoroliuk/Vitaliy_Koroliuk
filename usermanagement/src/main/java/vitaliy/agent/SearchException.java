package vitaliy.agent;

import vitaliy.db.DatabaseException;

public class SearchException extends Exception {

  public SearchException(DatabaseException e) {
    super(e);
  }

}
