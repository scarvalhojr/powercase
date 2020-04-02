/*
	A basic extension of the java.awt.Window class
 */

import java.awt.*;

import Yourdon.*;
import symantec.itools.awt.ComboBox;
import symantec.itools.awt.MultiList;
import symantec.itools.awt.RadioButtonGroupPanel;

public class EventosDialog extends java.awt.Dialog
{
    protected Frame parentFrame;

    protected void editar ()
    {
        Entidade    entidade;
        Evento      evento;
        int         indice;

        if (radioButton_temporal.getState())
            entidade = null;
        else
        {
            indice = choice_entidades.getSelectedIndex();
            if (indice < 0) return;
            entidade = entidades.getEntidade(indice);
            if (entidade == null) return;
        }
        
        indice = multiList1.getSelectedRow();

        evento = eventos.getEvento(entidade, indice);
        if (evento == null) return;
        
        (new EditarEventoDialog(this.parentFrame, this.entidades, evento)).setVisible(true);
        
        this.atualizar();

    }// Fim de editar

    protected void excluir ()
    {
        Entidade    entidade;
        Evento      evento;
        int         row;

        row = multiList1.getSelectedRow();
        if (row < 0) return;

        if (radioButton_temporal.getState())
            entidade = null;
        else
        {
            int indice = choice_entidades.getSelectedIndex();
            if (indice < 0) return;
            entidade = entidades.getEntidade(choice_entidades.getSelectedIndex());
            if (entidade == null) return;
        }
        
        evento = eventos.getEvento(entidade, row);
        eventos.remover(evento);
        atualizar();
    }// Fim de excluir

    protected void inserir ()
    {
        int         row;
        Entidade    entidade;
        Evento      evento;

        row = multiList1.getNumberOfRows();

        if (radioButton_temporal.getState())
            entidade = null;
        else
        {
            int indice = choice_entidades.getSelectedIndex();
            if (indice < 0)
            {
                entidade = null;
                radioButton_temporal.setState(true);
            }
            else
            {
                entidade = entidades.getEntidade(choice_entidades.getSelectedIndex());
                if (entidade == null)
                    radioButton_temporal.setState(true);
            }
        }

        this.eventos.adicionar(entidade,"descricao","estimulo","resposta");
        this.atualizar();
        multiList1.selectRow(row);
        this.editar();
        this.atualizar();

    }//Fim de Inserir
    
    protected void fechar ()
    {
       this.dispose();
    }

 	protected ListaEntidades entidades;
	protected ListaEventos eventos;

    protected void atualizar()
    {
        Entidade    entidade;
        Evento      evento;

        multiList1.clear();

        if (radioButton_temporal.getState())
            entidade = null;
        else
        {
            int indice = choice_entidades.getSelectedIndex();
            if (indice < 0) return;
            entidade = this.entidades.getEntidade(indice);
            if (entidade == null) return;
        }

        evento = this.eventos.getPrimeiro(entidade);
        int row=0;
        while (evento != null)
        {
            // adicionar evento ao multilist
            multiList1.addTextCell(row,0,evento.getDescricao());
            multiList1.addTextCell(row,1,evento.getEstimulo());
            multiList1.addTextCell(row++,2,evento.getResposta());
            evento = this.eventos.getProximo(entidade);
        }
        
        multiList1.selectRow(0);

    }  // fim atualizar

	public EventosDialog (Frame parent, Projeto projeto)
	{
	    this(parent, false);
	    Entidade    entidade;

        this.parentFrame = parent;
	    this.entidades = projeto.entidades;
	    this.eventos = projeto.eventos;
	    
	    this.setTitle(this.getTitle() + projeto.getNome());

        try {
            multiList1.setAllowSorting(false);
        } catch (Exception e) {}

        radioButton_entidade.setState(true);

		//{{REGISTER_LISTENERS
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		OKbutton.addActionListener(lSymAction);
		SymItem lSymItem = new SymItem();
		radioButton_entidade.addItemListener(lSymItem);
		radioButton_temporal.addItemListener(lSymItem);
		choice_entidades.addItemListener(lSymItem);
		Inserirbutton.addActionListener(lSymAction);
		Editarbutton.addActionListener(lSymAction);
		Excluirbutton.addActionListener(lSymAction);
		SymMouse aSymMouse = new SymMouse();
		multiList1.addMouseListener(aSymMouse);
		Ajudabutton.addActionListener(lSymAction);
		//}}
	}

	public EventosDialog(Frame parent, boolean modal)
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
		setSize(542,267);
		setBackground(new Color(12632256));
		OKbutton = new java.awt.Button();
		OKbutton.setLabel("OK");
		OKbutton.setBounds(460,5,75,20);
		OKbutton.setBackground(new Color(12632256));
		add(OKbutton);
		Ajudabutton = new java.awt.Button();
		Ajudabutton.setLabel("Ajuda");
		Ajudabutton.setBounds(460,130,75,20);
		Ajudabutton.setBackground(new Color(12632256));
		add(Ajudabutton);
		Inserirbutton = new java.awt.Button();
		Inserirbutton.setLabel("Novo...");
		Inserirbutton.setBounds(460,40,75,20);
		Inserirbutton.setBackground(new Color(12632256));
		add(Inserirbutton);
		Editarbutton = new java.awt.Button();
		Editarbutton.setLabel("Editar...");
		Editarbutton.setBounds(460,65,75,20);
		Editarbutton.setBackground(new Color(12632256));
		add(Editarbutton);
		Excluirbutton = new java.awt.Button();
		Excluirbutton.setLabel("Excluir");
		Excluirbutton.setBounds(460,90,75,20);
		Excluirbutton.setBackground(new Color(12632256));
		add(Excluirbutton);
		multiList1 = new symantec.itools.awt.MultiList();
		try {
			java.lang.String[] tempString = new java.lang.String[3];
			tempString[0] = new java.lang.String("Evento");
			tempString[1] = new java.lang.String("Estímulo");
			tempString[2] = new java.lang.String("Resposta");
			multiList1.setHeadings(tempString);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			multiList1.setCellBg(new java.awt.Color(12632256));
		}
		catch(java.beans.PropertyVetoException e) { }
		multiList1.setBounds(10,70,440,192);
		multiList1.setBackground(new Color(12632256));
		add(multiList1);
		choice_entidades = new java.awt.Choice();
		add(choice_entidades);
		choice_entidades.setBounds(115,35,168,24);
		radioButtonGroupPanel1 = new symantec.itools.awt.RadioButtonGroupPanel();
		try {
			radioButtonGroupPanel1.setBorderColor(new java.awt.Color(12632256));
		}
		catch(java.beans.PropertyVetoException e) { }
		radioButtonGroupPanel1.setLayout(null);
		radioButtonGroupPanel1.setBounds(5,5,108,55);
		add(radioButtonGroupPanel1);
		radioButton_temporal = new java.awt.Checkbox("Temporal");
		radioButton_temporal.setBounds(1,3,87,18);
		radioButtonGroupPanel1.add(radioButton_temporal);
		radioButton_entidade = new java.awt.Checkbox("da Entidade");
		radioButton_entidade.setBounds(1,28,91,21);
		radioButtonGroupPanel1.add(radioButton_entidade);
		setTitle("Eventos: ");
		setResizable(false);
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

	//{{DECLARE_CONTROLS
	java.awt.Button OKbutton;
	java.awt.Button Ajudabutton;
	java.awt.Button Inserirbutton;
	java.awt.Button Editarbutton;
	java.awt.Button Excluirbutton;
	symantec.itools.awt.MultiList multiList1;
	java.awt.Choice choice_entidades;
	symantec.itools.awt.RadioButtonGroupPanel radioButtonGroupPanel1;
	java.awt.Checkbox radioButton_temporal;
	java.awt.Checkbox radioButton_entidade;
	//}}

	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowActivated(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == EventosDialog.this)
				EventoDialog_WindowActivated(event);
		}

		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == EventosDialog.this)
				EventoDialog_WindowClosing(event);
		}
	}

	void EventoDialog_WindowClosing(java.awt.event.WindowEvent event)
	{
        this.dispose();
	}

	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == OKbutton)
				OKbutton_ActionPerformed(event);
			else if (object == Inserirbutton)
				Inserirbutton_ActionPerformed(event);
			else if (object == Editarbutton)
				Editarbutton_ActionPerformed(event);
			else if (object == Excluirbutton)
				Excluirbutton_ActionPerformed(event);
			else if (object == Ajudabutton)
				Ajudabutton_ActionPerformed(event);
		}
	}

	void OKbutton_ActionPerformed(java.awt.event.ActionEvent event)
	{
       this.fechar();
	}

	void EventoDialog_WindowActivated(java.awt.event.WindowEvent event)
	{
	    Entidade entidade;
	    
	    // Carregar a lista com entidades
	    int total = entidades.getQuantidade();
	    choice_entidades.removeAll();
	    for (int indice=0; indice<total; indice++)
	    {
	        entidade = entidades.getEntidade(indice);
	        choice_entidades.add(entidade.getDescricao());
	    }

       this.atualizar();
	}

	class SymItem implements java.awt.event.ItemListener
	{
		public void itemStateChanged(java.awt.event.ItemEvent event)
		{
			Object object = event.getSource();
			if (object == radioButton_entidade)
				radioButtonEntidade_ItemStateChanged(event);
			else if (object == radioButton_temporal)
				radioButtonTemporal_ItemStateChanged(event);
			else if (object == choice_entidades)
				choiceEntidades_ItemStateChanged(event);
		}
	}

	void radioButtonEntidade_ItemStateChanged(java.awt.event.ItemEvent event)
	{
	    if (radioButton_entidade.getState())
	    {
            choice_entidades.enable(true);
            choice_entidades.requestFocus();
        }
        else
            choice_entidades.enable(false);
        this.atualizar();
	}

	void radioButtonTemporal_ItemStateChanged(java.awt.event.ItemEvent event)
	{
	    if (radioButton_temporal.getState())
            choice_entidades.enable(false);
        else
            choice_entidades.enable(true);
        this.atualizar();
	}

	void choiceEntidades_ItemStateChanged(java.awt.event.ItemEvent event)
	{
        this.atualizar();
	}

	void Inserirbutton_ActionPerformed(java.awt.event.ActionEvent event)
	{
        this.inserir();
	}

	void Editarbutton_ActionPerformed(java.awt.event.ActionEvent event)
	{
        this.editar();
	}

	void Excluirbutton_ActionPerformed(java.awt.event.ActionEvent event)
	{
        this.excluir();
	}

	class SymMouse extends java.awt.event.MouseAdapter
	{
		public void mouseClicked(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == multiList1)
				multiList1_mouseClicked(event);
		}
	}

	void multiList1_mouseClicked(java.awt.event.MouseEvent event)
	{
        if (event.getClickCount() > 1) this.editar();
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

	void Ajudabutton_ActionPerformed(java.awt.event.ActionEvent event)
	{
        try
        {
            Runtime.getRuntime().exec("winhelp PowerCase.hlp");
        }
        catch (java.io.IOException e) {}
	}
}
