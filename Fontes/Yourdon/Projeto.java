package Yourdon;

import java.lang.*;
import java.awt.Point;
import java.util.Date;

public class Projeto extends java.lang.Object implements java.io.Serializable
{
    public Diagrama contexto;
    public ListaDepositos depositos;
    public ListaEventos eventos;
    public void setRevisao(Date data)
    {
        this.revisao = data;
    }
    public Date getRevisao()
    {
        return this.revisao;
    }
    protected Date revisao;
    public void setObjetivo (String objetivo)
    {
        this.objetivo = objetivo;
    }
    public void setResponsavel (String responsavel)
    {
        this.responsavel = responsavel;
    }
    public String getResponsavel ()
    {
        return this.responsavel;
    }
    public String getObjetivo ()
    {
        return this.objetivo;
    }
    public Date getCriacao()
    {
        return this.criacao;
    }
    protected Date criacao;
    protected String objetivo;
    protected String responsavel;
    public void setDescricao (String descricao)
    {
        this.descricao = descricao;
    }
    public String getDescricao ()
    {
        return this.descricao;
    }
    protected String descricao;
    public void setNome (String nome)
    {
        this.processos.sistema.setDescricao(nome);
    }
    public String getNome ()
    {
        return this.processos.sistema.getDescricao();
    }
    public ListaFluxos fluxos;
    public ListaProcessos processos;
    public Projeto ()
    {
        entidades = new ListaEntidades();
        processos = new ListaProcessos();
        fluxos = new ListaFluxos();
        depositos = new ListaDepositos();
        eventos = new ListaEventos();

        contexto = new Diagrama(processos.sistema);
        contexto.adicionarSimboloProcesso(processos.sistema, new Point(10,10), true);

        dfd0 = new Diagrama (processos.sistema);
        criacao = new Date();
        revisao = new Date();
    }
    public Diagrama dfd0;
    public ListaEntidades entidades;
}