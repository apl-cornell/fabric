package fla.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fla.Label;

/**
 * Represents a set with labelled elements.
 */
public class PolyInstantiableSet<T> {
  private final Map<Label, Set<T>> entries;

  public PolyInstantiableSet() {
    entries = new HashMap<>();
  }

  public void add(Label label, T e) {
    Set<T> entry = entries.get(label);
    if (entry == null) {
      entry = new HashSet<>();
      entries.put(label, entry);
    }

    entry.add(e);
  }
}
