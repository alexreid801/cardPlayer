package com.sunspotgames.cardplayer.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class LayeredPaneDemo extends JFrame
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1278156996998794178L;

public LayeredPaneDemo() 
  {
    super("Custom MDI: Part II");
    setSize(570,400);
    getContentPane().setBackground(new Color(244,232,152));

    getLayeredPane().setOpaque(true);
   
    InnerFrame[] frames = new InnerFrame[5];
    for(int i=0; i<5; i++) 
    {
      frames[i] = new InnerFrame("InnerFrame " + i);
      frames[i].setBounds(50+i*20, 50+i*20, 200, 200);
      getLayeredPane().add(frames[i]);
    }

    WindowListener l = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    };

    Dimension dim = getToolkit().getScreenSize();
    setLocation(dim.width/2-getWidth()/2, 
      dim.height/2-getHeight()/2);

    ImageIcon image = new ImageIcon("spiral.gif");
    setIconImage(image.getImage());
    addWindowListener(l);
    setVisible(true);
  }

  public static void main(String[] args) 
  {
    new LayeredPaneDemo();
  }
}

class InnerFrame 
extends JPanel
{
  private static ImageIcon ICONIZE_BUTTON_ICON = new ImageIcon("iconize.gif");
  private static ImageIcon RESTORE_BUTTON_ICON = new ImageIcon("restore.gif");
  private static ImageIcon CLOSE_BUTTON_ICON = new ImageIcon("close.gif");
  private static ImageIcon PRESS_CLOSE_BUTTON_ICON = new ImageIcon("pressclose.gif");
  private static ImageIcon PRESS_RESTORE_BUTTON_ICON = new ImageIcon("pressrestore.gif");
  private static ImageIcon PRESS_ICONIZE_BUTTON_ICON = new ImageIcon("pressiconize.gif");
  private static ImageIcon DEFAULT_FRAME_ICON = new ImageIcon("default.gif");
  private static int BORDER_THICKNESS = 4;
  private static int WIDTH = 200;
  private static int HEIGHT = 200;
  private static int TITLE_BAR_HEIGHT = 25;
  private static int FRAME_ICON_PADDING = 2;
  private static int ICONIZED_WIDTH = 150;
  private static Color TITLE_BAR_BG_COLOR = new Color(108,190,116);
  private static Color BORDER_COLOR = new Color(8,90,16);

  private int m_titleBarHeight = TITLE_BAR_HEIGHT;
  private int m_width = WIDTH;
  private int m_height = HEIGHT;
  private int m_iconizedWidth = ICONIZED_WIDTH;

  private String m_title;
  private JLabel m_titleLabel;
  private JLabel m_iconLabel;

  private boolean m_iconified;

  private boolean m_iconizeable;
  private boolean m_resizeable;
  private boolean m_closeable;

  // used to wrap title bar and contentPanel
  private JPanel m_frameContentPanel;

  private JPanel m_titlePanel; 
  private JPanel m_contentPanel;
  private JPanel m_buttonPanel; 
  private JPanel m_buttonWrapperPanel;

  private InnerFrameButton m_iconize;
  private InnerFrameButton m_close;

  private ImageIcon m_frameIcon = DEFAULT_FRAME_ICON;

  private NorthResizeEdge m_northResizer;
  private SouthResizeEdge m_southResizer;
  private EastResizeEdge m_eastResizer;
  private WestResizeEdge m_westResizer;

  public InnerFrame() {
    this("");
  }

  public InnerFrame(String title) {
    this(title, null);
  }

  public InnerFrame(String title, ImageIcon frameIcon) {
    this(title, frameIcon, true, true, true);
  }

  public InnerFrame(String title, ImageIcon frameIcon,
   boolean resizeable, boolean iconizeable, boolean closeable) {
    super.setLayout(new BorderLayout());
    attachNorthResizeEdge();
    attachSouthResizeEdge();
    attachEastResizeEdge();
    attachWestResizeEdge();
    populateInnerFrame();

    setTitle(title);
    setResizeable(resizeable);
    setIconizeable(iconizeable);
    setCloseable(closeable);
    if (frameIcon != null)
      setFrameIcon(frameIcon);
  }

  protected void populateInnerFrame() {
    m_frameContentPanel = new JPanel();
    m_frameContentPanel.setLayout(new BorderLayout());
    createTitleBar();
    m_contentPanel = new JPanel();
    m_frameContentPanel.add(m_titlePanel, BorderLayout.NORTH);
    m_frameContentPanel.add(m_contentPanel, BorderLayout.CENTER);
    super.add(m_frameContentPanel, BorderLayout.CENTER);
  }

  public Component add(Component c) {
    return((m_contentPanel == null) ? null : m_contentPanel.add(c));
  }

  public void setLayout(LayoutManager mgr) {
    if (m_contentPanel != null)
      m_contentPanel.setLayout(mgr);
  }

  public void toFront() {
    if (getParent() instanceof JLayeredPane)
      ((JLayeredPane) getParent()).moveToFront(this);
  }

  public void close() {
    if (getParent() instanceof JLayeredPane) {
      JLayeredPane jlp = (JLayeredPane) getParent();
      jlp.remove(InnerFrame.this);
      jlp.repaint();
    }
  }

  public boolean isIconizeable() {
    return m_iconizeable;
  }

  public void setIconizeable(boolean b) {
    m_iconizeable = b;
    m_iconize.setVisible(b);
    m_titlePanel.revalidate();
  }

  public boolean isCloseable() {
    return m_closeable;
  }

  public void setCloseable(boolean b) {
    m_closeable = b;
    m_close.setVisible(b);
    m_titlePanel.revalidate();
  }

  public boolean isIconified() {
    return m_iconified;
  }

  public void setIconified(boolean b) {
    m_iconified = b;
    if (b) {
      m_width = getWidth();     // remember width
      m_height = getHeight();   // remember height
      setBounds(getX(), getY(), ICONIZED_WIDTH, 
        m_titleBarHeight + 2*BORDER_THICKNESS);
      m_iconize.setIcon(RESTORE_BUTTON_ICON);
      m_iconize.setPressedIcon(PRESS_RESTORE_BUTTON_ICON);
      setResizeable(false);
    }
    else {
      setBounds(getX(), getY(), m_width, m_height);
      m_iconize.setIcon(ICONIZE_BUTTON_ICON);
      m_iconize.setPressedIcon(PRESS_ICONIZE_BUTTON_ICON);
      setResizeable(true);
    }
    revalidate();
  }

  ////////////////////////////////////////////
  //////////////// Title Bar /////////////////
  ////////////////////////////////////////////

  public void setFrameIcon(ImageIcon fi) {
    m_frameIcon = fi;

    if (fi != null) {
      if (m_frameIcon.getIconHeight() > TITLE_BAR_HEIGHT)
        setTitleBarHeight(m_frameIcon.getIconHeight() + 2*FRAME_ICON_PADDING);
      m_iconLabel.setIcon(m_frameIcon);
    }
    else setTitleBarHeight(TITLE_BAR_HEIGHT);
    revalidate();
  }
 
  public ImageIcon getFrameIcon() {
    return m_frameIcon;
  }

  public void setTitle(String s) {
    m_title = s;
    m_titleLabel.setText(s);
    m_titlePanel.repaint();
  }

  public String getTitle() {
    return m_title;
  }

  public void setTitleBarHeight(int h) {
    m_titleBarHeight = h;
  }

  public int getTitleBarHeight() {
    return m_titleBarHeight;
  }
 
  // create the title bar: m_titlePanel
  protected void createTitleBar() {
    m_titlePanel = new JPanel() {
      public Dimension getPreferredSize() {
        return new Dimension(InnerFrame.this.getWidth(), 
          m_titleBarHeight);
      }
    };
    m_titlePanel.setLayout(new BorderLayout());
    m_titlePanel.setOpaque(true);
    m_titlePanel.setBackground(TITLE_BAR_BG_COLOR);

    m_titleLabel = new JLabel();
    m_titleLabel.setForeground(Color.black);

    m_close = new InnerFrameButton(CLOSE_BUTTON_ICON);
    m_close.setPressedIcon(PRESS_CLOSE_BUTTON_ICON);
    m_close.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        InnerFrame.this.close();
      }
    });

    m_iconize = new InnerFrameButton(ICONIZE_BUTTON_ICON);
    m_iconize.setPressedIcon(PRESS_ICONIZE_BUTTON_ICON);
    m_iconize.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        InnerFrame.this.setIconified(!InnerFrame.this.isIconified());
      }
    });

    m_buttonWrapperPanel = new JPanel();
    m_buttonWrapperPanel.setOpaque(false);
    m_buttonPanel = new JPanel(new GridLayout(1,2));
    m_buttonPanel.setOpaque(false);
    m_buttonPanel.add(m_iconize);
    m_buttonPanel.add(m_close);
    m_buttonPanel.setAlignmentX(0.5f);
    m_buttonPanel.setAlignmentY(0.5f);
    m_buttonWrapperPanel.add(m_buttonPanel);

    m_iconLabel = new JLabel();
    m_iconLabel.setBorder(new EmptyBorder(
      FRAME_ICON_PADDING, FRAME_ICON_PADDING,
      FRAME_ICON_PADDING, FRAME_ICON_PADDING));
    if (m_frameIcon != null)
      m_iconLabel.setIcon(m_frameIcon);

    m_titlePanel.add(m_titleLabel, BorderLayout.CENTER);
    m_titlePanel.add(m_buttonWrapperPanel, BorderLayout.EAST);
    m_titlePanel.add(m_iconLabel, BorderLayout.WEST);

    InnerFrameTitleBarMouseAdapter iftbma = 
      new InnerFrameTitleBarMouseAdapter(this);
    m_titlePanel.addMouseListener(iftbma);
    m_titlePanel.addMouseMotionListener(iftbma);
  }

  // title bar mouse adapter for frame dragging
  class InnerFrameTitleBarMouseAdapter 
  extends MouseInputAdapter
  {
    InnerFrame m_if;
    int m_XDifference, m_YDifference;
    boolean m_dragging;

    public InnerFrameTitleBarMouseAdapter(InnerFrame inf) {
      m_if = inf;
    }

    public void mouseDragged(MouseEvent e) {
      if (m_dragging)
        m_if.setLocation(e.getX()-m_XDifference + getX(), 
          e.getY()-m_YDifference + getY());
    }

    public void mousePressed(MouseEvent e) {  
      m_if.toFront();
      m_XDifference = e.getX();
      m_YDifference = e.getY();
      m_dragging = true;
    }

    public void mouseReleased(MouseEvent e) {
      m_dragging = false;
    }
  }
  
  // custom button class for title bar
  class InnerFrameButton extends JButton 
  {
    Dimension m_dim;

    public InnerFrameButton(ImageIcon ii) {
      super(ii);
      m_dim = new Dimension(ii.getIconWidth(), ii.getIconHeight());
      setOpaque(false);
      setContentAreaFilled(false);
      setBorder(null);
    }

    public Dimension getPreferredSize() {
      return m_dim;
    }

    public Dimension getMinimumSize() {
      return m_dim;
    }

    public Dimension getMaximumSize() {
      return m_dim;
    }
  }

  ///////////////////////////////////////////////
  //////////////// Resizability /////////////////
  ///////////////////////////////////////////////

  public boolean isResizeable() {
    return m_resizeable;
  }

  public void setResizeable(boolean b) {
    if (!b && m_resizeable == true) {
      m_northResizer.removeMouseListener(m_northResizer);
      m_northResizer.removeMouseMotionListener(m_northResizer);
      m_southResizer.removeMouseListener(m_southResizer);
      m_southResizer.removeMouseMotionListener(m_southResizer);
      m_eastResizer.removeMouseListener(m_eastResizer);
      m_eastResizer.removeMouseMotionListener(m_eastResizer);
      m_westResizer.removeMouseListener(m_westResizer);
      m_westResizer.removeMouseMotionListener(m_westResizer);
    }
    else if (m_resizeable == false) {
      m_northResizer.addMouseListener(m_northResizer);
      m_northResizer.addMouseMotionListener(m_northResizer);
      m_southResizer.addMouseListener(m_southResizer);
      m_southResizer.addMouseMotionListener(m_southResizer);
      m_eastResizer.addMouseListener(m_eastResizer);
      m_eastResizer.addMouseMotionListener(m_eastResizer);
      m_westResizer.addMouseListener(m_westResizer);
      m_westResizer.addMouseMotionListener(m_westResizer);
    }
    m_resizeable = b;
  }

  protected void attachNorthResizeEdge() {
    m_northResizer = new NorthResizeEdge(this);
    super.add(m_northResizer, BorderLayout.NORTH);
  }

  protected void attachSouthResizeEdge() {
    m_southResizer = new SouthResizeEdge(this);
    super.add(m_southResizer, BorderLayout.SOUTH);
  }

  protected void attachEastResizeEdge() {
    m_eastResizer = new EastResizeEdge(this);
    super.add(m_eastResizer, BorderLayout.EAST);
  }

  protected void attachWestResizeEdge() {
    m_westResizer = new WestResizeEdge(this);
    super.add(m_westResizer, BorderLayout.WEST);
  }

  class EastResizeEdge extends JPanel
  implements MouseListener, MouseMotionListener {
    private int WIDTH = BORDER_THICKNESS;
    private int MIN_WIDTH = ICONIZED_WIDTH;
    private boolean m_dragging;
    private JComponent m_resizeComponent;
  
    protected EastResizeEdge(JComponent c) {
      m_resizeComponent = c;
      setOpaque(true);
      setBackground(BORDER_COLOR);
    }

    public Dimension getPreferredSize() {
      return new Dimension(WIDTH, m_resizeComponent.getHeight());
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {
      m_dragging = false;
    }

    public void mouseDragged(MouseEvent e) {
      if (m_resizeComponent.getWidth() + e.getX() >= MIN_WIDTH)
        m_resizeComponent.setBounds(m_resizeComponent.getX(), 
          m_resizeComponent.getY(), 
          m_resizeComponent.getWidth() + e.getX(),
          m_resizeComponent.getHeight());
      else
        m_resizeComponent.setBounds(m_resizeComponent.getX(), 
          m_resizeComponent.getY(), 
          MIN_WIDTH, m_resizeComponent.getHeight());
      m_resizeComponent.validate();
    }

    public void mouseEntered(MouseEvent e) {
      if (!m_dragging)
        setCursor(Cursor.getPredefinedCursor(
          Cursor.E_RESIZE_CURSOR));
    }
    
    public void mouseExited(MouseEvent e) {
      if (!m_dragging)
        setCursor(Cursor.getPredefinedCursor(
          Cursor.DEFAULT_CURSOR));
    }
  
    public void mousePressed(MouseEvent e) {
      toFront();
      m_dragging = true;
    }  
  }

  class WestResizeEdge extends JPanel
  implements MouseListener, MouseMotionListener {
    private int WIDTH = BORDER_THICKNESS;
    private int MIN_WIDTH = ICONIZED_WIDTH;
    private int m_dragX, m_rightX;
    private boolean m_dragging;
    private JComponent m_resizeComponent;
  
    protected WestResizeEdge(JComponent c) {
      m_resizeComponent = c;
      setOpaque(true);
      setBackground(BORDER_COLOR);
    }

    public Dimension getPreferredSize() {
      return new Dimension(WIDTH, m_resizeComponent.getHeight());
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
  
    public void mouseReleased(MouseEvent e) {
      m_dragging = false;
    }

    public void mouseDragged(MouseEvent e) {
      if (m_resizeComponent.getWidth()-
       (e.getX()-m_dragX) >= MIN_WIDTH)
        m_resizeComponent.setBounds(
          m_resizeComponent.getX() + (e.getX()-m_dragX), 
          m_resizeComponent.getY(), 
          m_resizeComponent.getWidth()-(e.getX()-m_dragX),
          m_resizeComponent.getHeight());
      else
        if (m_resizeComponent.getX() + MIN_WIDTH < m_rightX)
          m_resizeComponent.setBounds(m_rightX-MIN_WIDTH, 
            m_resizeComponent.getY(), 
            MIN_WIDTH, m_resizeComponent.getHeight());
        else
          m_resizeComponent.setBounds(m_resizeComponent.getX(), 
            m_resizeComponent.getY(), 
            MIN_WIDTH, m_resizeComponent.getHeight());
      m_resizeComponent.validate();
    }
  
    public void mouseEntered(MouseEvent e) {
      if (!m_dragging)
        setCursor(Cursor.getPredefinedCursor(
          Cursor.W_RESIZE_CURSOR));
    }
    
    public void mouseExited(MouseEvent e) {
      if (!m_dragging)
        setCursor(Cursor.getPredefinedCursor(
          Cursor.DEFAULT_CURSOR));
    }
    
    public void mousePressed(MouseEvent e) {
      toFront();
      m_rightX = m_resizeComponent.getX() + 
        m_resizeComponent.getWidth();
      m_dragging = true;
      m_dragX = e.getX();
    }  
  }
  
  class NorthResizeEdge extends JPanel
  implements MouseListener, MouseMotionListener {
    private static final int NORTH = 0;
    private static final int NORTHEAST = 1;
    private static final int NORTHWEST = 2;
    private int CORNER = 10;
    private int HEIGHT = BORDER_THICKNESS;
    private int MIN_WIDTH = ICONIZED_WIDTH;
    private int MIN_HEIGHT = TITLE_BAR_HEIGHT+(2*HEIGHT);
    private int m_width, m_dragX, m_dragY, m_rightX, m_lowerY;
    private boolean m_dragging;
    private JComponent m_resizeComponent;
    private int m_mode;
    
    protected NorthResizeEdge(JComponent c) {
      m_resizeComponent = c;
      setOpaque(true);
      setBackground(BORDER_COLOR);
    }

    public Dimension getPreferredSize() {
      return new Dimension(m_resizeComponent.getWidth(), HEIGHT);
    }

    public void mouseClicked(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {
      if (!m_dragging) {
        if (e.getX() < CORNER) {
          setCursor(Cursor.getPredefinedCursor(
            Cursor.NW_RESIZE_CURSOR));
        }
        else if(e.getX() > getWidth()-CORNER) {
          setCursor(Cursor.getPredefinedCursor(
            Cursor.NE_RESIZE_CURSOR));
        }
        else {
          setCursor(Cursor.getPredefinedCursor(
            Cursor.N_RESIZE_CURSOR));
        }
      }
    }

    public void mouseReleased(MouseEvent e) {
      m_dragging = false;
    }

    public void mouseDragged(MouseEvent e) {
      int h = m_resizeComponent.getHeight();
      int w = m_resizeComponent.getWidth();
      int x = m_resizeComponent.getX();
      int y = m_resizeComponent.getY();
      int ex = e.getX();
      int ey = e.getY();
      switch (m_mode) {
        case NORTH:
          if (h-(ey-m_dragY) >= MIN_HEIGHT)
            m_resizeComponent.setBounds(x, y + (ey-m_dragY), 
              w, h-(ey-m_dragY));
          else
              m_resizeComponent.setBounds(x, 
                m_lowerY-MIN_HEIGHT, w, MIN_HEIGHT);
          break;
        case NORTHEAST:
          if (h-(ey-m_dragY) >= MIN_HEIGHT
          && w + (ex-(getWidth()-CORNER)) >= MIN_WIDTH)
            m_resizeComponent.setBounds(x, 
              y + (ey-m_dragY), w + (ex-(getWidth()-CORNER)),
                h-(ey-m_dragY));
          else if (h-(ey-m_dragY) >= MIN_HEIGHT
          && !(w + (ex-(getWidth()-CORNER)) >= MIN_WIDTH))
            m_resizeComponent.setBounds(x, 
              y + (ey-m_dragY), MIN_WIDTH, h-(ey-m_dragY));
          else if (!(h-(ey-m_dragY) >= MIN_HEIGHT)
          && w + (ex-(getWidth()-CORNER)) >= MIN_WIDTH)
            m_resizeComponent.setBounds(x, 
              m_lowerY-MIN_HEIGHT, w + (ex-(getWidth()-CORNER)), 
                MIN_HEIGHT);
          else
            m_resizeComponent.setBounds(x, 
              m_lowerY-MIN_HEIGHT, MIN_WIDTH, MIN_HEIGHT);
          break;
        case NORTHWEST:
          if (h-(ey-m_dragY) >= MIN_HEIGHT
          && w-(ex-m_dragX) >= MIN_WIDTH)
            m_resizeComponent.setBounds(x + (ex-m_dragX), 
              y + (ey-m_dragY), w-(ex-m_dragX),
                h-(ey-m_dragY));
          else if (h-(ey-m_dragY) >= MIN_HEIGHT
          && !(w-(ex-m_dragX) >= MIN_WIDTH)) {
            if (x + MIN_WIDTH < m_rightX) 
              m_resizeComponent.setBounds(m_rightX-MIN_WIDTH, 
                y + (ey-m_dragY), MIN_WIDTH, h-(ey-m_dragY));
            else
              m_resizeComponent.setBounds(x, 
                y + (ey-m_dragY), w, h-(ey-m_dragY));
          } 
          else if (!(h-(ey-m_dragY) >= MIN_HEIGHT)
          && w-(ex-m_dragX) >= MIN_WIDTH) 
            m_resizeComponent.setBounds(x + (ex-m_dragX), 
              m_lowerY-MIN_HEIGHT, w-(ex-m_dragX), MIN_HEIGHT);
          else
            m_resizeComponent.setBounds(m_rightX-MIN_WIDTH, 
              m_lowerY-MIN_HEIGHT, MIN_WIDTH, MIN_HEIGHT);
          break;
      }
      m_rightX = x + w;
      m_resizeComponent.validate();
    }
  
    public void mouseEntered(MouseEvent e) {
      mouseMoved(e);
    }
    
    public void mouseExited(MouseEvent e) {
      if (!m_dragging)
        setCursor(Cursor.getPredefinedCursor(
          Cursor.DEFAULT_CURSOR));
    }
    
    public void mousePressed(MouseEvent e) {
      toFront();
      m_dragging = true;
      m_dragX = e.getX();
      m_dragY = e.getY();
      m_lowerY = m_resizeComponent.getY()
        + m_resizeComponent.getHeight();
      if (e.getX() < CORNER) {
        m_mode = NORTHWEST;
      }
      else if(e.getX() > getWidth()-CORNER) {
        m_mode = NORTHEAST;
      }
      else {
        m_mode = NORTH;    
      }
    }  
  }
  
  class SouthResizeEdge extends JPanel
  implements MouseListener, MouseMotionListener {
    private static final int SOUTH = 0;
    private static final int SOUTHEAST = 1;
    private static final int SOUTHWEST = 2;
    private int CORNER = 10;
    private int HEIGHT = BORDER_THICKNESS;
    private int MIN_WIDTH = ICONIZED_WIDTH;
    private int MIN_HEIGHT = TITLE_BAR_HEIGHT+(2*HEIGHT);
    private int m_width, m_dragX, m_dragY, m_rightX;
    private boolean m_dragging;
    private JComponent m_resizeComponent;
    private int m_mode;
    
    protected SouthResizeEdge(JComponent c) {
      m_resizeComponent = c;
      setOpaque(true);
      setBackground(BORDER_COLOR);
    }

    public Dimension getPreferredSize() {
      return new Dimension(m_resizeComponent.getWidth(), HEIGHT);
    }
  
    public void mouseClicked(MouseEvent e) {}
  
    public void mouseMoved(MouseEvent e) {
      if (!m_dragging) {
        if (e.getX() < CORNER) {
          setCursor(Cursor.getPredefinedCursor(
            Cursor.SW_RESIZE_CURSOR));
        }
        else if(e.getX() > getWidth()-CORNER) {
          setCursor(Cursor.getPredefinedCursor(
            Cursor.SE_RESIZE_CURSOR));
        }
        else {
          setCursor(Cursor.getPredefinedCursor(
            Cursor.S_RESIZE_CURSOR));
        }
      }
    }
  
    public void mouseReleased(MouseEvent e) {
      m_dragging = false;
    }
  
    public void mouseDragged(MouseEvent e) {
      int h = m_resizeComponent.getHeight();
      int w = m_resizeComponent.getWidth();
      int x = m_resizeComponent.getX();
      int y = m_resizeComponent.getY();
      int ex = e.getX();
      int ey = e.getY();
      switch (m_mode) {
        case SOUTH:
          if (h+(ey-m_dragY) >= MIN_HEIGHT)
           m_resizeComponent.setBounds(x, y, w, h+(ey-m_dragY));
          else
            m_resizeComponent.setBounds(x, y, w, MIN_HEIGHT);
          break;
        case SOUTHEAST:
          if (h+(ey-m_dragY) >= MIN_HEIGHT
            && w + (ex-(getWidth()-CORNER)) >= MIN_WIDTH)
            m_resizeComponent.setBounds(x, y, 
              w + (ex-(getWidth()-CORNER)), h+(ey-m_dragY));
          else if (h+(ey-m_dragY) >= MIN_HEIGHT
            && !(w + (ex-(getWidth()-CORNER)) >= MIN_WIDTH))
            m_resizeComponent.setBounds(x, y, 
              MIN_WIDTH, h+(ey-m_dragY));
          else if (!(h+(ey-m_dragY) >= MIN_HEIGHT)
            && w + (ex-(getWidth()-CORNER)) >= MIN_WIDTH)
            m_resizeComponent.setBounds(x, y, 
              w + (ex-(getWidth()-CORNER)), MIN_HEIGHT);
          else
            m_resizeComponent.setBounds(x, 
              y, MIN_WIDTH, MIN_HEIGHT);
          break;
        case SOUTHWEST:
          if (h+(ey-m_dragY) >= MIN_HEIGHT 
            && w-(ex-m_dragX) >= MIN_WIDTH)
            m_resizeComponent.setBounds(x + (ex-m_dragX), y, 
              w-(ex-m_dragX), h+(ey-m_dragY));
          else if (h+(ey-m_dragY) >= MIN_HEIGHT
            && !(w-(ex-m_dragX) >= MIN_WIDTH)) {
            if (x + MIN_WIDTH < m_rightX)
              m_resizeComponent.setBounds(m_rightX-MIN_WIDTH, y, 
                MIN_WIDTH, h+(ey-m_dragY));
            else
              m_resizeComponent.setBounds(x, y, w, 
                h+(ey-m_dragY));
          }
          else if (!(h+(ey-m_dragY) >= MIN_HEIGHT)
            && w-(ex-m_dragX) >= MIN_WIDTH)
            m_resizeComponent.setBounds(x + (ex-m_dragX), y, 
              w-(ex-m_dragX), MIN_HEIGHT);
          else
            m_resizeComponent.setBounds(m_rightX-MIN_WIDTH, 
              y, MIN_WIDTH, MIN_HEIGHT);
          break;
      }
      m_rightX = x + w;
      m_resizeComponent.validate();
    }
  
    public void mouseEntered(MouseEvent e) {
      mouseMoved(e);
    }
    
    public void mouseExited(MouseEvent e) {
      if (!m_dragging)
        setCursor(Cursor.getPredefinedCursor(
          Cursor.DEFAULT_CURSOR));
    }
    
    public void mousePressed(MouseEvent e) {
      toFront();
      m_dragging = true;
      m_dragX = e.getX();
      m_dragY = e.getY();
      if (e.getX() < CORNER) {
        m_mode = SOUTHWEST;
      }
      else if(e.getX() > getWidth()-CORNER) {
        m_mode = SOUTHEAST;
      }
      else {
        m_mode = SOUTH;    
      }
    }  
  }
}