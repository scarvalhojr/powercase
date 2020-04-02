package Yourdon;

import Yourdon.*;
import java.awt.*;
public abstract class SimboloMovel extends Yourdon.Simbolo
{
    public Point getAresta (Point ref)
    {
        int     cx, cy, ax, ay;

        cx = this.getCentro().x;
        cy = this.getCentro().y;

        if (ref.x <= cx)
            ax = this.area.x;
        else
            ax = this.area.x + this.area.width;

        if (ref.y <= cy)
            ay = this.area.y;
        else
            ay = this.area.y + this.area.height;

        return new Point(ax, ay);
    }
    public SimboloMovel (int tipo, ElementoEssencial elemento, Point origem, boolean herdado)
    {
        super(tipo, elemento, herdado);
        this.area = new Rectangle(origem, new Dimension(5,5));
    }
}