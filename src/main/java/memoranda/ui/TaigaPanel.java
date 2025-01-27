package memoranda.ui;

import javax.swing.*;
import java.awt.*;

public class TaigaPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JButton historyBackB = new JButton();
    JToolBar profileManagementTB = new JToolBar();

    DailyItemsPanel parentPanel = null;

    public TaigaPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void jbInit() throws Exception {
        profileManagementTB.setFloatable(false);

    }
}
