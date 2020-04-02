/*
	A basic extension of the java.awt.Frame class
 */

import java.awt.*;
import java.util.*;
import java.text.SimpleDateFormat;

import symantec.itools.awt.ImageHTMLLink;
import Yourdon.*;
public class EditarProjetoDialog extends java.awt.Dialog 
{
    
    protected Projeto projeto;

    public EditarProjetoDialog (Frame parent, Projeto projeto, String nomeArquivo)
    {
        this(parent, false);
        
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        this.projeto=projeto;

        this.setTitle(this.getTitle() + projeto.getNome());

        textField_criacao.setText(format.format(this.projeto.getCriacao()));
        textField_revisao.setText(format.format(this.projeto.getRevisao()));
        
        if (nomeArquivo != null) textField_arquivo.setText(nomeArquivo);
        
        // atualiza os campos de texto
        textField_nome.setText(this.projeto.getNome());
	    textArea_descricao.setText(this.projeto.getDescricao());
	    textField_responsavel.setText(this.projeto.getResponsavel());
	    textArea_objetivo.setText(this.projeto.getObjetivo());
    }
	public EditarProjetoDialog(Frame parent, boolean modal)
	{
	    super (parent, modal);

		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		setLayout(null);
		setVisible(false);
		setSize(448,355);
		setBackground(new Color(12632256));
		abreviado = new java.awt.Label("Nome Abreviado do Projeto:");
		abreviado.setBounds(7,50,160,15);
		add(abreviado);
		textField_nome = new java.awt.TextField();
		textField_nome.setBounds(7,65,433,20);
		add(textField_nome);
		textField_arquivo = new java.awt.TextField();
		textField_arquivo.setEditable(false);
		textField_arquivo.setBounds(7,20,433,20);
		add(textField_arquivo);
		textField_arquivo.setEnabled(false);
		file = new java.awt.Label("Arquivo de Projeto:");
		file.setBounds(10,5,105,15);
		add(file);
		textArea_descricao = new java.awt.TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
		textArea_descricao.setBounds(7,110,433,50);
		add(textArea_descricao);
		descritivo = new java.awt.Label("Nome Descritivo do Projeto:");
		descritivo.setBounds(7,95,191,15);
		add(descritivo);
		respons = new java.awt.Label("Respons�vel:");
		respons.setBounds(7,170,84,15);
		add(respons);
		textField_responsavel = new java.awt.TextField();
		textField_responsavel.setBounds(7,185,243,20);
		add(textField_responsavel);
		objetivos = new java.awt.Label("Declara��o de Objetivos:");
		objetivos.setBounds(10,215,171,15);
		add(objetivos);
		textArea_objetivo = new java.awt.TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
		textArea_objetivo.setBounds(7,230,433,80);
		add(textArea_objetivo);
		ok_pro = new java.awt.Button();
		ok_pro.setLabel("OK");
		ok_pro.setBounds(101,325,75,20);
		ok_pro.setBackground(new Color(12632256));
		add(ok_pro);
		cancel_pro = new java.awt.Button();
		cancel_pro.setLabel("Cancelar");
		cancel_pro.setBounds(271,325,75,20);
		cancel_pro.setBackground(new Color(12632256));
		add(cancel_pro);
		button2 = new java.awt.Button();
		button2.setLabel("Ajuda");
		button2.setBounds(186,325,75,20);
		button2.setBackground(new Color(12632256));
		add(button2);
		textField_criacao = new java.awt.TextField();
		textField_criacao.setEditable(false);
		textField_criacao.setBounds(260,185,85,20);
		add(textField_criacao);
		label1 = new java.awt.Label("Cria��o:");
		label1.setBounds(260,170,84,15);
		add(label1);
		textField_revisao = new java.awt.TextField();
		textField_revisao.setEditable(false);
		textField_revisao.setBounds(355,185,85,20);
		add(textField_revisao);
		label2 = new java.awt.Label("Revis�o:");
		label2.setBounds(355,170,84,15);
		add(label2);
		setTitle("Editar projeto: ");
		setResizable(false);
		//}}

		//{{INIT_MENUS
		//}}

		//{{REGISTER_LISTENERS
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		ok_pro.addActionListener(lSymAction);
		cancel_pro.addActionListener(lSymAction);
		button2.addActionListener(lSymAction);
		//}}
	}

	public EditarProjetoDialog(Frame parent, String title, boolean modal)
	{
		this(parent, true);
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
	java.awt.Label abreviado;
	java.awt.TextField textField_nome;
	java.awt.TextField textField_arquivo;
	java.awt.Label file;
	java.awt.TextArea textArea_descricao;
	java.awt.Label descritivo;
	java.awt.Label respons;
	java.awt.TextField textField_responsavel;
	java.awt.Label objetivos;
	java.awt.TextArea textArea_objetivo;
	java.awt.Button ok_pro;
	java.awt.Button cancel_pro;
	java.awt.Button button2;
	java.awt.TextField textField_criacao;
	java.awt.Label label1;
	java.awt.TextField textField_revisao;
	java.awt.Label label2;
	//}}

	//{{DECLARE_MENUS
	//}}

	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == EditarProjetoDialog.this)
				EditarProjetoDialog_WindowClosing(event);
		}
	}
	
	void EditarProjetoDialog_WindowClosing(java.awt.event.WindowEvent event)
	{
		setVisible(false);		 // hide the Frame
	}

	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == ok_pro)
				ok_pro_ActionPerformed(event);
			else if (object == cancel_pro)
				cancel_pro_ActionPerformed(event);
			else if (object == button2)
				button2_ActionPerformed(event);
		}
	}


    void ok_pro_ActionPerformed(java.awt.event.ActionEvent event)
	{
	    this.projeto.setNome(textField_nome.getText());
	    this.projeto.setDescricao(textArea_descricao.getText());	    
	    this.projeto.setResponsavel(textField_responsavel.getText());
	    this.projeto.setObjetivo(textArea_objetivo.getText());	    
	    
		//{{CONNECTION
		// Action from EditarProjetoDialog Create and show as modal
		setVisible(false);
		
		//}}
		dispose();
	}
	
void cancel_pro_ActionPerformed(java.awt.event.ActionEvent event)
	{
		//{{CONNECTION
		// Action from EditarProjetoDialog Create and show as modal
		setVisible(false);
		
		//}}
	    dispose();
	}

	void button2_ActionPerformed(java.awt.event.ActionEvent event)
	{
        try
        {
            Runtime.getRuntime().exec("winhelp PowerCase.hlp");
        }
        catch (java.io.IOException e) {}
	}
}
