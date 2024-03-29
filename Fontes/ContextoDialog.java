/*
	A basic extension of the java.awt.Dialog class
 */

import Yourdon.*;
import java.text.SimpleDateFormat;

import java.awt.*;

import symantec.itools.awt.util.ToolBarPanel;
import symantec.itools.awt.ImageButton;
public class ContextoDialog extends Dialog
{
    protected static final Dimension areaDesenho = new Dimension(1500,1500);
    protected Diagrama dfd;
    protected Projeto projeto;
    public ContextoDialog (Frame parent, Projeto projeto, Diagrama dfd)
    {
        this(parent, "Diagrama de contexto: " + dfd.processo.getDescricao(), false);

        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        this.projeto = projeto;
        this.dfd = dfd;

        // Cria o canvas para desenhar o diagram
        dfdCanvas = new DiagramaCanvas((Frame) this.getParent(), projeto, dfd);
        dfdCanvas.setSize(this.areaDesenho);
        dfdCanvas.setBackground(new Color(255,218,200));

        // Adiciona o canvas ao ScrollPane
        scrollPane1.add(dfdCanvas);

        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
	public ContextoDialog(Frame parent, boolean modal)
	{
		super(parent, modal);

		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		setLayout(new BorderLayout(0,0));
		setVisible(false);
		setSize(530,400);
		setBackground(new Color(12632256));
		toolBarPanel1 = new symantec.itools.awt.util.ToolBarPanel();
		toolBarPanel1.setBounds(0,0,530,46);
		toolBarPanel1.setBackground(new Color(12632256));
		add("North", toolBarPanel1);
		selecionarButton = new symantec.itools.awt.ImageButton();
		try {
			selecionarButton.setImageURL(symantec.itools.net.RelativeURL.getURL("selecionar.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			selecionarButton.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		selecionarButton.setBounds(0,0,44,38);
		toolBarPanel1.add(selecionarButton);
		editarButton = new symantec.itools.awt.ImageButton();
		try {
			editarButton.setImageURL(symantec.itools.net.RelativeURL.getURL("editar.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			editarButton.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		editarButton.setBounds(44,0,44,38);
		toolBarPanel1.add(editarButton);
		excluirButton = new symantec.itools.awt.ImageButton();
		try {
			excluirButton.setImageURL(symantec.itools.net.RelativeURL.getURL("excluir.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			excluirButton.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		excluirButton.setBounds(88,0,44,38);
		toolBarPanel1.add(excluirButton);
		entidadeButton = new symantec.itools.awt.ImageButton();
		try {
			entidadeButton.setImageURL(symantec.itools.net.RelativeURL.getURL("entidade.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			entidadeButton.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		entidadeButton.setBounds(132,0,44,38);
		toolBarPanel1.add(entidadeButton);
		fluxoButton = new symantec.itools.awt.ImageButton();
		try {
			fluxoButton.setImageURL(symantec.itools.net.RelativeURL.getURL("fluxo.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			fluxoButton.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		fluxoButton.setBounds(176,0,44,38);
		toolBarPanel1.add(fluxoButton);
		verificarButton = new symantec.itools.awt.ImageButton();
		try {
			verificarButton.setImageURL(symantec.itools.net.RelativeURL.getURL("verificar.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			verificarButton.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		verificarButton.setBounds(220,0,44,38);
		toolBarPanel1.add(verificarButton);
		ajudaButton = new symantec.itools.awt.ImageButton();
		try {
			ajudaButton.setImageURL(symantec.itools.net.RelativeURL.getURL("ajuda.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			ajudaButton.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		ajudaButton.setBounds(264,0,44,38);
		toolBarPanel1.add(ajudaButton);
		scrollPane1 = new java.awt.ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		scrollPane1.setBounds(0,46,530,354);
		scrollPane1.setBackground(new Color(12632256));
		add("Center", scrollPane1);
		setTitle("ContextoDialog");
		//}}

		//{{REGISTER_LISTENERS
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		selecionarButton.addActionListener(lSymAction);
		excluirButton.addActionListener(lSymAction);
		entidadeButton.addActionListener(lSymAction);
		fluxoButton.addActionListener(lSymAction);
		verificarButton.addActionListener(lSymAction);
		editarButton.addActionListener(lSymAction);
		ajudaButton.addActionListener(lSymAction);
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

    // Used for addNotify check.
	boolean fComponentsAdjusted = false;


	public ContextoDialog(Frame parent, String title, boolean modal)
	{
		this(parent, modal);
		setTitle(title);
	}

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

	//{{DECLARE_CONTROLS
	symantec.itools.awt.util.ToolBarPanel toolBarPanel1;
	symantec.itools.awt.ImageButton selecionarButton;
	symantec.itools.awt.ImageButton editarButton;
	symantec.itools.awt.ImageButton excluirButton;
	symantec.itools.awt.ImageButton entidadeButton;
	symantec.itools.awt.ImageButton fluxoButton;
	symantec.itools.awt.ImageButton verificarButton;
	symantec.itools.awt.ImageButton ajudaButton;
	java.awt.ScrollPane scrollPane1;
	//}}

	// Controle para desenho do diagrama
	DiagramaCanvas dfdCanvas;

	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowDeactivated(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == ContextoDialog.this)
				ContextoDialog_WindowDeactivated(event);
		}

		public void windowActivated(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == ContextoDialog.this)
				ContextoDialog_WindowActivated(event);
		}

		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == ContextoDialog.this)
				Dialog1_WindowClosing(event);
		}
	}

	void Dialog1_WindowClosing(java.awt.event.WindowEvent event)
	{
		setVisible(false);
	}

	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == selecionarButton)
				selecionarButton_actionPerformed(event);
			else if (object == excluirButton)
				excluirButton_actionPerformed(event);
			else if (object == entidadeButton)
				entidadeButton_actionPerformed(event);
			else if (object == fluxoButton)
				fluxoButton_actionPerformed(event);
			else if (object == verificarButton)
				verificarButton_actionPerformed(event);
			else if (object == editarButton)
				editarButton_actionPerformed(event);
			else if (object == ajudaButton)
				ajudaButton_actionPerformed(event);
		}
	}

	void selecionarButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		dfdCanvas.setEstado(dfdCanvas.SELECIONAR);
	}

	void excluirButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		dfdCanvas.excluirSimboloSelecionado();
	}

	void processoButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		dfdCanvas.setEstado(dfdCanvas.CRIARPROCESSO);
	}

	void entidadeButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		dfdCanvas.setEstado(dfdCanvas.CRIARENTIDADE);
	}

	void depositoButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		dfdCanvas.setEstado(dfdCanvas.CRIARDEPOSITO);
	}

	void fluxoButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		dfdCanvas.setEstado(dfdCanvas.CRIARFLUXO);
	}

	void decomporButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		dfdCanvas.decomporProcessoSelecionado();
	}

	void verificarButton_actionPerformed(java.awt.event.ActionEvent event)
	{
        (new VerificarDialog((Frame) this.getParent(), dfd)).setVisible(true);
	}

	void editarButton_actionPerformed(java.awt.event.ActionEvent event)
	{
	    dfdCanvas.editar();
        this.setTitle("Diagrama de Contexto: " + dfd.processo.getDescricao());
	}

	void ContextoDialog_WindowActivated(java.awt.event.WindowEvent event)
	{
        this.setTitle("Diagrama de Contexto: " + dfd.processo.getDescricao());
        dfdCanvas.repaint();
	}

	void ajudaButton_actionPerformed(java.awt.event.ActionEvent event)
	{
        try
        {
            Runtime.getRuntime().exec("winhelp PowerCase.hlp");
        }
        catch (java.io.IOException e) {}
	}

	void ContextoDialog_WindowDeactivated(java.awt.event.WindowEvent event)
	{
        dfdCanvas.setEstado(dfdCanvas.SELECIONAR);
	}
}
