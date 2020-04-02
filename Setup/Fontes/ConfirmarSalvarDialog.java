import java.awt.*;
import java.awt.event.*;



public class ConfirmarSalvarDialog extends Dialog
{
    public ConfirmarSalvarDialog (Frame parent)
    {
        this(parent, true);
        this.resposta = CANCELAR;
    }
    public int getResposta ()
    {
        return this.resposta;
    }
    protected int resposta;
    public static int CANCELAR = 3;
    public static int NAO = 2;
    public static int SIM = 1;

	public ConfirmarSalvarDialog(Frame parent, boolean modal)
	{

		super(parent, modal);

		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		setLayout(null);
		setVisible(false);
		setSize(337,108);
		setBackground(new Color(12632256));
		yesButton = new java.awt.Button();
		yesButton.setLabel("Sim");
		yesButton.setBounds(46,70,75,20);
		add(yesButton);
		noButton = new java.awt.Button();
		noButton.setLabel("N�o");
		noButton.setBounds(131,70,75,20);
		add(noButton);
		label1 = new java.awt.Label("Deseja salvar o projeto aberto?",Label.CENTER);
		label1.setBounds(8,25,320,25);
		add(label1);
		cancelButton = new java.awt.Button();
		cancelButton.setLabel("Cancelar");
		cancelButton.setBounds(216,70,75,20);
		add(cancelButton);
		setTitle("PowerCase");
		setResizable(false);
		//}}

		//{{REGISTER_LISTENERS
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		noButton.addActionListener(lSymAction);
		yesButton.addActionListener(lSymAction);
		cancelButton.addActionListener(lSymAction);
		//}}
	}

	public void addNotify()
	{
	    // Record the size of the window prior to calling parents addNotify.
	    Dimension d = getSize();
	    
		super.addNotify();

		if (fComponentsAdjusted)
			return;

		// Adjust components according to the insets
		setSize(insets().left + insets().right + d.width, insets().top + insets().bottom + d.height);
		Component components[] = getComponents();
		for (int i = 0; i < components.length; i++)
		{
			Point p = components[i].getLocation();
			p.translate(insets().left, insets().top);
			components[i].setLocation(p);
		}
		fComponentsAdjusted = true;
	}

	public ConfirmarSalvarDialog(Frame parent, String title, boolean modal)
	{
		this(parent, modal);
		setTitle(title);
	}

    /**
     * Shows or hides the component depending on the boolean flag b.
     * @param b  if true, show the component; otherwise, hide the component.
     * @see java.awt.Component#isVisible
     */
    public void setVisible(boolean b)
	{
		if(b)
		{
			Rectangle bounds = getParent().getBounds();
			Rectangle abounds = getBounds();
	
			setLocation(bounds.x + (bounds.width - abounds.width)/ 2,
				 bounds.y + (bounds.height - abounds.height)/2);
		}
		super.setVisible(b);
	}

    // Used for addNotify check.
	boolean fComponentsAdjusted = false;

	//{{DECLARE_CONTROLS
	java.awt.Button yesButton;
	java.awt.Button noButton;
	java.awt.Label label1;
	java.awt.Button cancelButton;
	//}}

	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == ConfirmarSalvarDialog.this)
				QuitDialog_WindowClosing(event);
		}
	}
	void QuitDialog_WindowClosing(java.awt.event.WindowEvent event)
	{
	    this.resposta = CANCELAR;
        dispose();
	}

	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == noButton)
				noButton_Clicked(event);
			else if (object == yesButton)
				yesButton_Clicked(event);
			else if (object == cancelButton)
				cancelButton_ActionPerformed(event);
		}
	}
	void yesButton_Clicked(java.awt.event.ActionEvent event)
	{
		resposta = SIM;
		this.dispose();
	}
	void noButton_Clicked(java.awt.event.ActionEvent event)
	{
	    resposta = NAO;
	    this.dispose();
	}
	void cancelButton_ActionPerformed(java.awt.event.ActionEvent event)
	{
		resposta = CANCELAR;
		this.dispose();
	}
}
