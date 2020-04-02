package Yourdon;

import java.lang.*;
import java.util.Vector;

public abstract class ListaElementos extends java.lang.Object implements java.io.Serializable
{
    public ListaElementos ()
    {
        elementos = new Vector();
    }
    public int getQuantidade ()
    {
        return elementos.size();
    }
    protected Vector elementos;
}