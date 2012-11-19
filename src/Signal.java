import java.util.ArrayList;

public class Signal {

    protected ArrayList<Hook> hooks;

    public Signal() {
        hooks = new ArrayList<Hook>();
    }

    public void signal() {
        for (Hook hook: hooks) {
            hook.on_signal();
        }
    }

    public void add_hook(Hook hook) {
        hooks.add(hook);
    }

    public interface Hook {
        public void on_signal();
    }
}
