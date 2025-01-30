package memoranda.util;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import memoranda.ui.AppFrame;

public class Context {
    public static LoadableProperties context = new LoadableProperties();

    static {
        CurrentStorage.get().restoreContext();
        AppFrame.addExitListener(e -> CurrentStorage.get().storeContext());
    }

    public static Object get(Object key) {
        return context.get(key);
    }

    public static void put(Object key, Object value) {
        context.put(key, value);
    }

}