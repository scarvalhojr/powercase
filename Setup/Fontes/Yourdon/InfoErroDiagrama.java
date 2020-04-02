package Yourdon;

import java.lang.*;
public class InfoErroDiagrama extends java.lang.Object
{
    public void adicionar (InfoErroDiagrama erro)
    {
        this.qtdeEntidades = this.qtdeEntidades + erro.qtdeEntidades;
        this.qtdeDepositos = this.qtdeDepositos + erro.qtdeDepositos;
        this.qtdeProcessos = this.qtdeProcessos + erro.qtdeProcessos;
    }
    public InfoErroDiagrama ()
    {
        this(0,0,0);
    }
    public InfoErroDiagrama (int entidades, int depositos, int processos)
    {
        this.qtdeEntidades = entidades;
        this.qtdeDepositos = depositos;
        this.qtdeProcessos = processos;
    }
    public int qtdeEntidades;
    public int qtdeDepositos;
    public int qtdeProcessos;
}