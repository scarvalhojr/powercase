/*
	This simple extension of the java.awt.Frame class
	contains all the elements necessary to act as the
	main window of an application.
 */

import Yourdon.*;

import java.awt.*;
import java.io.*;
import java.util.*;

import symantec.itools.awt.util.ToolBarPanel;
import symantec.itools.awt.ImageButton;
import symantec.itools.awt.StatusBar;
public class PowerCase extends Frame
{
    protected void editarProjeto()
    {
		(new EditarProjetoDialog(this, this.projeto, this.nomeArquivo)).setVisible(true);
    }
    protected final int CANCELAR = 3;
    protected final int NAO = 2;
    protected final int SIM = 1;
    protected int fecharArquivo ()
    {
        if (this.arquivo == null) return NADA_A_FAZER;

        try
        {
            this.arquivo.flush();
            this.arquivo.close();
            this.arquivo = null;
            return OK;
        }
        catch (IOException e)
        {
            // Mensagem: erro fechando arquivo de projeto
            getToolkit().beep();
            return ERRO;
        }
    }
    protected final int NADA_A_FAZER = 0;
    protected final int ERRO = -1;
    protected final int OK = 1;
    protected FileOutputStream arquivo;
    protected String nomeArquivo;
    protected int abrir ()
    {
        FileInputStream     fin;
        ObjectInputStream   ois;

        if (this.projeto != null)
        {
            // Existe um projeto aberto: tentar fechar
            if (this.fechar() != OK) return ERRO;
        }

        // Abrir diálogo para selecionar nome do arquivo
        int		defMode			= openFileDialog1.getMode();
		String	defTitle		= openFileDialog1.getTitle();
		String  defDirectory	= openFileDialog1.getDirectory();
		openFileDialog1 = new java.awt.FileDialog(this, defTitle, defMode);
		openFileDialog1.setDirectory(defDirectory);
		openFileDialog1.setVisible(true);

        this.nomeArquivo = openFileDialog1.getFile();
        if (this.nomeArquivo == null) return ERRO;

        this.nomeArquivo = openFileDialog1.getDirectory() + this.nomeArquivo;

        try
        {
            fin = new FileInputStream(this.nomeArquivo);
            ois = new ObjectInputStream(fin);
            this.projeto = (Projeto) ois.readObject();
            ois.close();

            this.arquivo = new FileOutputStream(this.nomeArquivo);
            this.salvar();

            this.setEstadoMenu(true);
            this.setTitle("PowerCase - " + this.projeto.getNome());
            return OK;
        }
        catch (IOException e) {}
        catch (ClassNotFoundException e) {}

        // Mensagem: erro lendo arquivo de projeto
        getToolkit().beep();
        return ERRO;
    }
    protected void setEstadoMenu (boolean estado)
    {
		// Configura os menus
        Fechar.setEnabled(estado);
        miSave.setEnabled(estado);
        miSaveAs.setEnabled(estado);
        Contexto.setEnabled(estado);
        Eventos.setEnabled(estado);
        Entidades.setEnabled(estado);
        Propiedades.setEnabled(estado);
		dfd.setEnabled(estado);
		verificarMenu.setEnabled(estado);
		

		// Configura os botões da barra de ferramentas
		imageButton_salvar.setEnabled(estado);
		imageButton_editar.setEnabled(estado);
		imageButton_entidades.setEnabled(estado);
		imageButton_eventos.setEnabled(estado);
		imageButton_contexto.setEnabled(estado);
		imageButton_dfd.setEnabled(estado);
		s.setEnabled(estado);
    }
    protected int salvarComo ()
    {
        if (arquivo != null)
            if (this.fecharArquivo() != 1) return ERRO;

        // Abrir diálogo para selecionar nome do arquivo

   		int		defMode			= saveFileDialog1.getMode();
   		String	defTitle		= saveFileDialog1.getTitle();
   		String  defDirectory	= saveFileDialog1.getDirectory();
   		saveFileDialog1 = new java.awt.FileDialog(this, defTitle, defMode);
   		saveFileDialog1.setDirectory(defDirectory);
   		saveFileDialog1.setVisible(true);

        this.nomeArquivo = saveFileDialog1.getFile();
        if (this.nomeArquivo == null) return NADA_A_FAZER;

        this.nomeArquivo = saveFileDialog1.getDirectory() + this.nomeArquivo;

        try
        {
            this.arquivo = new FileOutputStream(nomeArquivo);
            return this.salvar();
        }
        catch (IOException e)
        {
            // Mensagem: erro abrindo arquivo para gravação
            getToolkit().beep();
            return ERRO;
        }
    }
    protected int salvar ()
    {
        ObjectOutputStream  oos;

        if (this.projeto == null) return ERRO;

        if (this.arquivo == null) return this.salvarComo();

        try
        {
            arquivo.flush();
            arquivo.close();
            arquivo = new FileOutputStream(nomeArquivo);
            oos = new ObjectOutputStream(arquivo);
            oos.writeObject(this.projeto);
            return OK;
        }
        catch (IOException e)
        {
            // Mensagem: erro gravando arquivo de projeto
            getToolkit().beep();
            return ERRO;
        }
    }
    protected int fechar ()
    {
        ConfirmarSalvarDialog dialogo;

        int resposta;

        if (projeto == null) return NADA_A_FAZER;

        // Deseja salvar projeto? (Sim, Não, Cancelar)
        dialogo = new ConfirmarSalvarDialog(this);
        dialogo.setVisible(true);

        resposta = dialogo.getResposta();

        switch (resposta)
        {
            case SIM:
                if (this.salvar() != OK) return ERRO;
                break;

            case NAO:
                break;

            default:
                return ERRO;
        }

        try
        {
            this.projeto = null;
            if (this.arquivo != null)
            {
                this.arquivo.close();
                this.arquivo = null;
            }
            if (this.nomeArquivo != null)
            {
                this.nomeArquivo = null;
            }
            this.setEstadoMenu(false);
            this.setTitle("PowerCase");
            return OK;
        }
        catch (IOException e)
        {
            return ERRO;
        }
    }
    protected int novo ()
    {
        if (this.projeto != null)
        {
            // Existe um projeto aberto: tentar fechar
            if (this.fechar() != OK) return ERRO;
        }

        if (this.arquivo != null) return ERRO;
        if (this.nomeArquivo != null) return ERRO;

        this.projeto = new Projeto();
        this.setEstadoMenu(true);
        this.setTitle("PowerCase - " + this.projeto.getNome());
        return OK;
    }
    protected Projeto projeto;
	public PowerCase()
	{
	    SplashWindow splash = new SplashWindow(this);
	    splash.setVisible(true);
	    for (long i=0; i<100000000; i++) {}
	    
		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.

		//{{INIT_CONTROLS
		setLayout(new BorderLayout(0,0));
		setVisible(false);
		setSize(693,439);
		setBackground(new Color(8421504));
		openFileDialog1 = new java.awt.FileDialog(this);
		openFileDialog1.setMode(FileDialog.LOAD);
		openFileDialog1.setTitle("Abrir Projeto");
		//$$ openFileDialog1.move(324,276);
		saveFileDialog1 = new java.awt.FileDialog(this);
		saveFileDialog1.setMode(FileDialog.SAVE);
		saveFileDialog1.setTitle("Salvar Projeto");
		//$$ saveFileDialog1.move(290,275);
		toolBarPanel1 = new symantec.itools.awt.util.ToolBarPanel();
		try {
			toolBarPanel1.setPaddingRight(3);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			toolBarPanel1.setPaddingBottom(3);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			toolBarPanel1.setPaddingTop(3);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			toolBarPanel1.setPaddingLeft(3);
		}
		catch(java.beans.PropertyVetoException e) { }
		toolBarPanel1.setBounds(0,0,693,52);
		toolBarPanel1.setBackground(new Color(12632256));
		add("North", toolBarPanel1);
		imageButton_novo = new symantec.itools.awt.ImageButton();
		try {
			imageButton_novo.setImageURL(symantec.itools.net.RelativeURL.getURL("novo.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			imageButton_novo.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		imageButton_novo.setBounds(0,0,44,38);
		toolBarPanel1.add(imageButton_novo);
		imageButton_abrir = new symantec.itools.awt.ImageButton();
		try {
			imageButton_abrir.setImageURL(symantec.itools.net.RelativeURL.getURL("abrir.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			imageButton_abrir.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		imageButton_abrir.setBounds(44,0,44,38);
		toolBarPanel1.add(imageButton_abrir);
		imageButton_salvar = new symantec.itools.awt.ImageButton();
		try {
			imageButton_salvar.setImageURL(symantec.itools.net.RelativeURL.getURL("salvar.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			imageButton_salvar.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		imageButton_salvar.setBounds(88,0,44,38);
		toolBarPanel1.add(imageButton_salvar);
		imageButton_editar = new symantec.itools.awt.ImageButton();
		try {
			imageButton_editar.setImageURL(symantec.itools.net.RelativeURL.getURL("editar.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			imageButton_editar.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		imageButton_editar.setBounds(132,0,44,38);
		toolBarPanel1.add(imageButton_editar);
		imageButton_entidades = new symantec.itools.awt.ImageButton();
		try {
			imageButton_entidades.setImageURL(symantec.itools.net.RelativeURL.getURL("entidades.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			imageButton_entidades.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		imageButton_entidades.setBounds(176,0,44,38);
		toolBarPanel1.add(imageButton_entidades);
		imageButton_eventos = new symantec.itools.awt.ImageButton();
		try {
			imageButton_eventos.setImageURL(symantec.itools.net.RelativeURL.getURL("eventos.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			imageButton_eventos.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		imageButton_eventos.setBounds(220,0,44,38);
		toolBarPanel1.add(imageButton_eventos);
		imageButton_contexto = new symantec.itools.awt.ImageButton();
		try {
			imageButton_contexto.setImageURL(symantec.itools.net.RelativeURL.getURL("contexto.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			imageButton_contexto.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		imageButton_contexto.setBounds(264,0,44,38);
		toolBarPanel1.add(imageButton_contexto);
		imageButton_dfd = new symantec.itools.awt.ImageButton();
		try {
			imageButton_dfd.setImageURL(symantec.itools.net.RelativeURL.getURL("dfd.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			imageButton_dfd.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		imageButton_dfd.setBounds(308,0,44,38);
		toolBarPanel1.add(imageButton_dfd);
		s = new symantec.itools.awt.ImageButton();
		try {
			s.setImageURL(symantec.itools.net.RelativeURL.getURL("verificar.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			s.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		s.setBounds(352,0,44,38);
		toolBarPanel1.add(s);
		imageButton_ajuda = new symantec.itools.awt.ImageButton();
		try {
			imageButton_ajuda.setImageURL(symantec.itools.net.RelativeURL.getURL("ajuda.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			imageButton_ajuda.setShowFocus(false);
		}
		catch(java.beans.PropertyVetoException e) { }
		imageButton_ajuda.setBounds(396,0,44,38);
		toolBarPanel1.add(imageButton_ajuda);
		statusBar1 = new symantec.itools.awt.StatusBar();
		try {
			statusBar1.setBevelStyle(symantec.itools.awt.StatusBar.BEVEL_LOWERED);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			statusBar1.setIPadBottom(0);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			statusBar1.setIPadSides(5);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			statusBar1.setStatusText("Pronto");
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			statusBar1.setPaddingRight(3);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			statusBar1.setPaddingBottom(3);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			statusBar1.setPaddingTop(5);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			statusBar1.setIPadTop(11);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			statusBar1.setPaddingLeft(3);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			statusBar1.setAlignStyle(symantec.itools.awt.StatusBar.ALIGN_LEFT);
		}
		catch(java.beans.PropertyVetoException e) { }
		statusBar1.setBounds(0,399,693,40);
		statusBar1.setFont(new Font("Serif", Font.PLAIN, 12));
		statusBar1.setBackground(new Color(12632256));
		add("South", statusBar1);
		panel1 = new java.awt.Panel();
		panel1.setLayout(null);
		panel1.setBounds(0,52,693,347);
		add("Center", panel1);
		setTitle("PowerCase");
		//}}

		//{{INIT_MENUS
		mainMenuBar = new java.awt.MenuBar();
		menu1 = new java.awt.Menu("Arquivo");
		miNew = new java.awt.MenuItem("Novo...");
		miNew.setShortcut(new MenuShortcut(java.awt.event.KeyEvent.VK_N,false));
		menu1.add(miNew);
		miOpen = new java.awt.MenuItem("Abrir...");
		miOpen.setShortcut(new MenuShortcut(java.awt.event.KeyEvent.VK_A,false));
		menu1.add(miOpen);
		Fechar = new java.awt.MenuItem("Fechar");
		menu1.add(Fechar);
		menu1.addSeparator();
		miSave = new java.awt.MenuItem("Salvar");
		miSave.setShortcut(new MenuShortcut(java.awt.event.KeyEvent.VK_S,false));
		menu1.add(miSave);
		miSaveAs = new java.awt.MenuItem("Salvar como...");
		menu1.add(miSaveAs);
		menu1.addSeparator();
		miExit = new java.awt.MenuItem("Sair");
		menu1.add(miExit);
		mainMenuBar.add(menu1);
		Modelo = new java.awt.Menu("Modelo");
		Propiedades = new java.awt.MenuItem("Propiedades...");
		Modelo.add(Propiedades);
		Entidades = new java.awt.MenuItem("Entidades...");
		Modelo.add(Entidades);
		Eventos = new java.awt.MenuItem("Eventos...");
		Modelo.add(Eventos);
		Contexto = new java.awt.MenuItem("Diagrama de Contexto...");
		Modelo.add(Contexto);
		dfd = new java.awt.MenuItem("Diagrama de Fluxo de Dados...");
		Modelo.add(dfd);
		Modelo.addSeparator();
		verificarMenu = new java.awt.MenuItem("Verificar modelo...");
		Modelo.add(verificarMenu);
		mainMenuBar.add(Modelo);
		Ajuda = new java.awt.Menu("Ajuda");
		mainMenuBar.setHelpMenu(Ajuda);
		conteudoButton = new java.awt.MenuItem("Conteudo...");
		Ajuda.add(conteudoButton);
		miAbout = new java.awt.MenuItem("Sobre...");
		Ajuda.add(miAbout);
		mainMenuBar.add(Ajuda);
		setMenuBar(mainMenuBar);
		//$$ mainMenuBar.move(360,276);
		//}}

		//{{REGISTER_LISTENERS
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		miOpen.addActionListener(lSymAction);
		miAbout.addActionListener(lSymAction);
		miExit.addActionListener(lSymAction);
		dfd.addActionListener(lSymAction);
		imageButton_novo.addActionListener(lSymAction);
		miSave.addActionListener(lSymAction);
		miSaveAs.addActionListener(lSymAction);
		miNew.addActionListener(lSymAction);
		Fechar.addActionListener(lSymAction);
		Propiedades.addActionListener(lSymAction);
		imageButton_abrir.addActionListener(lSymAction);
		imageButton_salvar.addActionListener(lSymAction);
		imageButton_editar.addActionListener(lSymAction);
		imageButton_entidades.addActionListener(lSymAction);
		Entidades.addActionListener(lSymAction);
		Eventos.addActionListener(lSymAction);
		imageButton_eventos.addActionListener(lSymAction);
		Contexto.addActionListener(lSymAction);
		imageButton_contexto.addActionListener(lSymAction);
		imageButton_dfd.addActionListener(lSymAction);
		s.addActionListener(lSymAction);
		verificarMenu.addActionListener(lSymAction);
		imageButton_ajuda.addActionListener(lSymAction);
		conteudoButton.addActionListener(lSymAction);
		//}}

        if (splash != null) splash.dispose();
		this.setEstadoMenu(false);
	}
	
	public PowerCase(String title)
	{
		this();
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
			setLocation(50, 50);
		}	
		super.setVisible(b);
	}
	
	static public void main(String args[])
	{
		(new PowerCase()).setVisible(true);
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
	
	//{{DECLARE_CONTROLS
	java.awt.FileDialog openFileDialog1;
	java.awt.FileDialog saveFileDialog1;
	symantec.itools.awt.util.ToolBarPanel toolBarPanel1;
	symantec.itools.awt.ImageButton imageButton_novo;
	symantec.itools.awt.ImageButton imageButton_abrir;
	symantec.itools.awt.ImageButton imageButton_salvar;
	symantec.itools.awt.ImageButton imageButton_editar;
	symantec.itools.awt.ImageButton imageButton_entidades;
	symantec.itools.awt.ImageButton imageButton_eventos;
	symantec.itools.awt.ImageButton imageButton_contexto;
	symantec.itools.awt.ImageButton imageButton_dfd;
	symantec.itools.awt.ImageButton s;
	symantec.itools.awt.ImageButton imageButton_ajuda;
	symantec.itools.awt.StatusBar statusBar1;
	java.awt.Panel panel1;
	//}}
	
	//{{DECLARE_MENUS
	java.awt.MenuBar mainMenuBar;
	java.awt.Menu menu1;
	java.awt.MenuItem miNew;
	java.awt.MenuItem miOpen;
	java.awt.MenuItem Fechar;
	java.awt.MenuItem miSave;
	java.awt.MenuItem miSaveAs;
	java.awt.MenuItem miExit;
	java.awt.Menu Modelo;
	java.awt.MenuItem Propiedades;
	java.awt.MenuItem Entidades;
	java.awt.MenuItem Eventos;
	java.awt.MenuItem Contexto;
	java.awt.MenuItem dfd;
	java.awt.MenuItem verificarMenu;
	java.awt.Menu Ajuda;
	java.awt.MenuItem conteudoButton;
	java.awt.MenuItem miAbout;
	//}}
	
	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowActivated(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == PowerCase.this)
				PowerFrame_WindowActivated(event);
		}

		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == PowerCase.this)
				Frame1_WindowClosing(event);
		}
	}
	void Frame1_WindowClosing(java.awt.event.WindowEvent event)
	{
	    if(this.fechar() == ERRO) return;

		setVisible(false);	// hide the Frame
		dispose();			// free the system resources
		System.exit(0);		// close the application
	}
	
	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == miOpen)
				miOpen_Action(event);
			else if (object == miAbout)
				miAbout_Action(event);
			else if (object == miExit)
				miExit_Action(event);
			else if (object == dfd)
				dfd_ActionPerformed(event);
			else if (object == imageButton_novo)
				imageButton1_actionPerformed(event);
			else if (object == miSave)
				miSave_ActionPerformed(event);
			else if (object == miSaveAs)
				miSaveAs_ActionPerformed(event);
			else if (object == miNew)
				miNew_ActionPerformed(event);
			else if (object == Fechar)
				Fechar_ActionPerformed(event);
			else if (object == Propiedades)
				Propiedades_ActionPerformed(event);
			else if (object == imageButton_abrir)
				imageButtonAbrir_actionPerformed(event);
			else if (object == imageButton_salvar)
				imageButtonSalvar_actionPerformed(event);
			else if (object == imageButton_editar)
				imageButtonEditar_actionPerformed(event);
			else if (object == imageButton_entidades)
				imageButtonEntidades_actionPerformed(event);
			else if (object == Entidades)
				Entidades_ActionPerformed(event);
			else if (object == Eventos)
				Eventos_ActionPerformed(event);
			else if (object == imageButton_eventos)
				imageButtonEventos_actionPerformed(event);
			else if (object == Contexto)
				Contexto_ActionPerformed(event);
			else if (object == imageButton_contexto)
				imageButtonContexto_actionPerformed(event);
			else if (object == imageButton_dfd)
				imageButtonDfd_actionPerformed(event);
			else if (object == s)
				imageButtonVerificar_actionPerformed(event);
			else if (object == verificarMenu)
				verificarMenu_ActionPerformed(event);
			else if (object == imageButton_ajuda)
				imageButtonAjuda_actionPerformed(event);
			else if (object == conteudoButton)
				conteudoButton_ActionPerformed(event);
			
		}
	}
	void miAbout_Action(java.awt.event.ActionEvent event)
	{
	    this.setEnabled(false);
	    (new SplashWindow (this)).setVisible(true);
	    
		//{{CONNECTION
		// Action from About Create and show as modal
		//(new AboutDialog(this, true)).setVisible(true);
		//}}
	}
	void miExit_Action(java.awt.event.ActionEvent event)
	{
		//{{CONNECTION
		// Action from Exit Create and show as modal
		(new QuitDialog(this, true)).setVisible(true);
		//}}
	}
	void miOpen_Action(java.awt.event.ActionEvent event)
	{
	    this.abrir();

	}
	void dfd_ActionPerformed(java.awt.event.ActionEvent event)
	{
		(new DFDDialog(this, this.projeto, this.projeto.dfd0)).setVisible(true);
	}
	void imageButton1_actionPerformed(java.awt.event.ActionEvent event)
	{
	    miNew_ActionPerformed(event);
	}
    void miSave_ActionPerformed(java.awt.event.ActionEvent event)
	{
	    this.salvar();
	}
	void miSaveAs_ActionPerformed(java.awt.event.ActionEvent event)
	{
	    this.salvarComo();
	}
	void miNew_ActionPerformed(java.awt.event.ActionEvent event)
	{
	    if (this.novo() == OK)
	        this.editarProjeto();
	}
	void Fechar_ActionPerformed(java.awt.event.ActionEvent event)
	{
	    this.fechar();
	}
	void Propiedades_ActionPerformed(java.awt.event.ActionEvent event)
	{
        this.editarProjeto();
	}
	void imageButtonAbrir_actionPerformed(java.awt.event.ActionEvent event)
	{
    	miOpen_Action(event);
	}
	void imageButtonSalvar_actionPerformed(java.awt.event.ActionEvent event)
	{
        miSave_ActionPerformed(event);
	}
	void imageButtonEditar_actionPerformed(java.awt.event.ActionEvent event)
	{
        Propiedades_ActionPerformed(event);
	}
	void imageButtonEntidades_actionPerformed(java.awt.event.ActionEvent event)
	{
		Entidades_ActionPerformed(event);
	}
	void Entidades_ActionPerformed(java.awt.event.ActionEvent event)
	{
		(new EntidadesDialog(this, this.projeto)).setVisible(true);
	}

	void PowerFrame_WindowActivated(java.awt.event.WindowEvent event)
	{
	    if (this.projeto != null)
            this.setTitle("PowerCase - " + this.projeto.getNome());
	}

	void Eventos_ActionPerformed(java.awt.event.ActionEvent event)
	{
		(new EventosDialog(this, this.projeto)).setVisible(true);
	}

	void imageButtonEventos_actionPerformed(java.awt.event.ActionEvent event)
	{
        Eventos_ActionPerformed(event);
	}

	void Contexto_ActionPerformed(java.awt.event.ActionEvent event)
	{
		(new ContextoDialog(this, this.projeto, this.projeto.contexto)).setVisible(true);
	}

	void imageButtonContexto_actionPerformed(java.awt.event.ActionEvent event)
	{
        Contexto_ActionPerformed(event);
	}

	void imageButtonDfd_actionPerformed(java.awt.event.ActionEvent event)
	{
        dfd_ActionPerformed(event);
	}

	void imageButtonVerificar_actionPerformed(java.awt.event.ActionEvent event)
	{
        verificarMenu_ActionPerformed(event);
	}

	void verificarMenu_ActionPerformed(java.awt.event.ActionEvent event)
	{
        (new VerificarDialog(this, this.projeto)).setVisible(true);
	}

	void imageButtonAjuda_actionPerformed(java.awt.event.ActionEvent event)
	{
        conteudoButton_ActionPerformed(event);
	}

	void conteudoButton_ActionPerformed(java.awt.event.ActionEvent event)
	{
        try
        {
            Runtime.getRuntime().exec("winhelp PowerCase.hlp");
        }
        catch (java.io.IOException e) {}
	}
}
