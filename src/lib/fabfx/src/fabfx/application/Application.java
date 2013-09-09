package fabfx.application;

import java.util.List;
import java.util.Map;

import fabric.lang.security.Principal;
import fabric.lang.security.Label;
import fabfx.scene.Scene;
import fabfx.stage.Stage;

public abstract class Application extends javafx.application.Application {
    protected final Principal P;
    protected final Label L;

    public static void launch(Principal P, Label L, String... args) {
        javafx.application.Application.launch(args);
    }

    public void Application(Principal P, Label L) {
      this.P = P;
      this.L = L;
    }
    
    public final void init() throws Exception {
        initialize();
    }

    public abstract void initialize() throws Exception;
}
