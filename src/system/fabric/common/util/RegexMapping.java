package fabric.common.util;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Provide a mapping from strings to values based on a regex.
 *
 * Regexes are checked in the order they are added to the map.
 *
 * XXX: THIS IS NOT INTENDED FOR MORE THAN A FEW MAPPINGS.
 */
public class RegexMapping<V> {
  public RegexMapping() {
  }

  private ArrayList<Pair<Pattern, V>> mappings = new ArrayList<>();

  public void put(String regex, V value) {
    mappings.add(new Pair<>(Pattern.compile(regex), value));
  }

  public V get(String key) {
    for (Pair<Pattern, V> entry : mappings) {
      if (entry.first.matcher(key).matches()) {
        return entry.second;
      }
    }
    return null;
  }
}
