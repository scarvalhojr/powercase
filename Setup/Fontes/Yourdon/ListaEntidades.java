package Yourdon;

import Yourdon.*;
import java.lang.*;
public class ListaEntidades extends Yourdon.ListaElementos
{
    public void remover (int indice)
    {
        this.elementos.removeElementAt(indice);
    }
    public Entidade getEntidade(int pos)
    {
        try
        {
            return (Entidade) this.elementos.elementAt(pos);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
    }
    protected int indiceAtual;
    public Entidade getProximo ()
    {
        try
        {
            return (Entidade) this.elementos.elementAt(indiceAtual++);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
    }
    public Entidade getPrimeiro ()
    {
        indiceAtual = 0;
        return this.getProximo();
    }
    public ListaEntidades ()
    {
        super();
    }
    public Entidade adicionar (String nome)
    {
        Entidade novaEntidade;

        novaEntidade = new Entidade (nome);
        elementos.addElement(novaEntidade);
        return novaEntidade;
    }
}