package Yourdon;

import Yourdon.*;
import java.lang.*;
import java.awt.*;
public class Fluxo extends Yourdon.ElementoEssencial
{
    public Fluxo (String descricao, int tipo)
    {
        super(descricao);
        this.tipoFluxo = tipo;
    }
    public void setTipoFluxo (int tipo)
    {
        this.tipoFluxo = tipo;
    }
    public int getTipoFluxo ()
    {
        return this.tipoFluxo;
    }
    public static final int BIDIRECIONAL = 2;
    public static final int INVERTIDO = 1;
    public static final int NORMAL = 0;
    protected int tipoFluxo;
}