package Yourdon;

import java.lang.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;
public abstract class Simbolo extends java.lang.Object implements java.io.Serializable
{
    protected Color borda;
    protected static final int MAXTAMTEXTO = 40;
    public Simbolo (int tipo, ElementoEssencial elemento, boolean herdado)
    {
        this.tipoSimbolo = tipo;
        this.elemento = elemento;
        this.herdado = herdado;
        if (herdado)
        {
            this.cor = NORMALHERDADO;
            this.borda = BORDAHERDADO;
        }
        else
        {
            this.borda = BORDA;
            this.cor = NORMAL;
        }
    }
    public abstract void desenhar (Graphics areaGrafica);
    public void selecionar (boolean selecionar)
    {
        if (selecionar)
            if (getHerdado())
                this.cor = SELECIONADOHERDADO;
            else
                this.cor = SELECIONADO;
         else
             if (getHerdado())
                this.cor = NORMALHERDADO;
             else
                this.cor = NORMAL;
    }
    protected static final Color BORDA = Color.black;
    protected static final Color BORDAHERDADO = Color.gray;
    protected static final Color TEXTO = Color.blue;
    protected static final Color SELECIONADOHERDADO = new Color(160,160,160);
    protected static final Color NORMALHERDADO = Color.lightGray;
    protected static final Color NORMAL = Color.white;
    protected static final Color SELECIONADO = Color.yellow;
    protected Color cor;
    public boolean getHerdado ()
    {
        return this.herdado;
    }
    protected boolean herdado;
    public int getTipoSimbolo ()
    {
        return this.tipoSimbolo;
    }
    public static final int FLUXO = 4;
    public static final int DEPOSITO = 3;
    public static final int PROCESSO = 2;
    public static final int ENTIDADE = 1;
    protected int tipoSimbolo;
    public ElementoEssencial elemento;
    public boolean contemPonto (Point ponto)
    {
        return this.area.contains(ponto);
    }
    public void mover (int incX, int incY)
    {
        this.area.translate(incX, incY);
    }
    protected Rectangle area;
    public Point getCentro()
    {
        int cx, cy;

        cx = this.area.x + this.area.width / 2;
        cy = this.area.y + this.area.height / 2;

        return new Point(cx, cy);
    }
}