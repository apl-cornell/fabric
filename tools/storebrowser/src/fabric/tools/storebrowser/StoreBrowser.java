package fabric.tools.storebrowser;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class StoreBrowser extends JFrame implements TreeSelectionListener {

  private static final boolean useSystemLookAndFeel = false;

  private JTree tree;

  private JEditorPane infoPane;

  private DataProvider dataProvider;

  private Set<DefaultMutableTreeNode> childrenEnumerated;

  private Component createNavTree() {
    childrenEnumerated = new HashSet<DefaultMutableTreeNode>();

    // Create the nodes.
    Object root = dataProvider.getRoot();

    DefaultMutableTreeNode top = new DefaultMutableTreeNode(root);
    addChildren(top);

    // Create a tree that allows one selection at a time.
    tree = new JTree(top);
    tree.getSelectionModel().setSelectionMode(
        TreeSelectionModel.SINGLE_TREE_SELECTION);

    // Listen for when the selection changes.
    tree.addTreeSelectionListener(this);

    // Create the scroll pane and add the tree to it.
    return new JScrollPane(tree);

  }

  private Component createInfoPane() {
    // Create the info pane.
    infoPane = new JEditorPane();
    infoPane.setEditable(false);
    return new JScrollPane(infoPane);

  }

  private JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("File");
    menu.setMnemonic(KeyEvent.VK_A);
    menu.getAccessibleContext().setAccessibleDescription("Hello");
    menuBar.add(menu);

    return menuBar;

  }

  public StoreBrowser(DataProvider dataProvider) {
    super("Fabric Browser");

    this.dataProvider = dataProvider;

    Component treeView = createNavTree();
    Component infoView = createInfoPane();

    setLayout(new GridLayout(1, 0));

    // Add the scroll panes to a split pane.
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setTopComponent(treeView);
    splitPane.setBottomComponent(infoView);

    Dimension minimumSize = new Dimension(100, 50);
    infoView.setMinimumSize(minimumSize);
    treeView.setMinimumSize(minimumSize);
    splitPane.setDividerLocation(400);
    splitPane.setPreferredSize(new Dimension(400, 500));

    // Add the split pane to this panel.
    add(splitPane);

    setJMenuBar(createMenuBar());
  }

  public static void main(final String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        createAndShowGUI(args);
      }
    });
  }

  private void addChildren(DefaultMutableTreeNode n) {
    if (!childrenEnumerated.contains(n)) {
      childrenEnumerated.add(n);
      List<Object> children =
          dataProvider.getChildrenForNode(n.getUserObject());
      for (Object obj : children)
        n.add(new DefaultMutableTreeNode(obj));
    }
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be
   * invoked from the event dispatch thread.
   */
  private static void createAndShowGUI(String[] args) {
    if (useSystemLookAndFeel) {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) {
        System.err.println("Couldn't use system look and feel.");
      }
    }

    String workerName = null, storeName = null;
    long onum = -1;
    if (args.length > 0) workerName = args[0];
    if (args.length > 1) storeName = args[1];
    if (args.length > 2) onum = Long.parseLong(args[2]);
    if (workerName == null) {
      workerName = JOptionPane.showInputDialog("Please enter worker name");
    }
    if (storeName == null) {
      storeName = JOptionPane.showInputDialog("Please enter store name");
    }

    // Create and set up the window.
    JFrame frame;
    if (onum > 0) {
      frame =
          new StoreBrowser(new FabricDataProvider(workerName, storeName, onum));
    } else {
      frame = new StoreBrowser(new FabricDataProvider(workerName, storeName));
    }
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void valueChanged(TreeSelectionEvent e) {
    DefaultMutableTreeNode selected =
        (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
    addChildren(selected);
    infoPane.setText(dataProvider.getDescriptionForNode(selected
        .getUserObject()));
  }

}
