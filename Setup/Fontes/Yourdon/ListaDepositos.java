package Yourdon;

import Yourdon.*;
import java.lang.*;
public class ListaDepositos extends Yourdon.ListaElementos
{
    public Deposito getPrimeiro ()
    {
        indiceAtual = 0;
        return this.getProximo();
    }
    public Deposito getProximo ()
    {
        try
        {
            return (Deposito) this.elementos.elementAt(indiceAtual++);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
    }
    protected int indiceAtual;
    public Deposito getDeposito(int pos)
    {
        try
        {
            return (Deposito) this.elementos.elementAt(pos);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
    }
    public void remover (int indice)
    {
        this.elementos.removeElementAt(indice);
    }
    public ListaDepositos ()
    {
        super();
    }
    public Deposito adicionar (String descricao)
    {
        Deposito    deposito;

        deposito = new Deposito(descricao);
        this.elementos.addElement(deposito);
        return deposito;
    }
}