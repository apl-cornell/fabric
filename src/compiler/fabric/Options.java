package fabric;

import polyglot.frontend.ExtensionInfo;

/**
 * This is the same as the JL options, except by default, we always generate
 * fully qualified class names. This is here because the logic for qualifying
 * class names seems a bit wonky.
 */
public class Options extends polyglot.main.Options {
  public Options(ExtensionInfo extension) {
    super(extension);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.main.Options#setDefaultValues()
   */
  @Override
  public void setDefaultValues() {
    super.setDefaultValues();
    this.fully_qualified_names = true;
  }
}
