package Yourdon;

import Yourdon.*;
import java.lang.*;
public class ListaFluxos extends Yourdon.ListaElementos
{
    public void remover (Fluxo fluxo)
    {
        this.elementos.removeElement(fluxo);
    }
    public ListaFluxos ()
    {
        super();
    }
    public Fluxo adicionar (String descricao)
    {
        return this.adicionar(descricao, Fluxo.NORMAL);
    }
    public Fluxo adicionar (String descricao, int tipoFluxo)
    {
        Fluxo   fluxo;

        fluxo = new Fluxo(descricao, tipoFluxo);
        this.elementos.addElement(fluxo);
        return fluxo;
    }
}