package Yourdon;

import Yourdon.*;
import java.lang.*;
public class ListaEventos extends Yourdon.ListaElementos
{
    public Evento getEvento(Entidade entidade, int indice)
    {
        Evento  evento;
        int     i;

        evento = getPrimeiro(entidade);
        i = 0;
        while (i < indice)
        {
            evento = getProximo(entidade);
            i++;
        }
        return evento;
    }
    public void remover(Evento evento)
    {
        elementos.removeElement(evento);
    }
    public Evento getPrimeiro (Entidade entidade)
    {
        this.indice = 0;
        return this.getProximo(entidade);
    }
    public Evento getProximo (Entidade entidade)
    {
        Evento evento;

        try
        {
            while (indice < elementos.size())
            {
                evento = (Evento) elementos.elementAt(indice++);
                if (evento.getEntidade() == entidade)
                    return evento;
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
        return null;
    }
    public Evento adicionar (Entidade entidade, String descricao, String estimulo, String resposta)
    {
        Evento evento;

        evento = new Evento(entidade, descricao, estimulo, resposta);
        elementos.addElement(evento);
        return evento;
    }
    protected int indice;
    public ListaEventos ()
    {
        super();
    }
}