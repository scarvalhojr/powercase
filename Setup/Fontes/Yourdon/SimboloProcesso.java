package Yourdon;

import Yourdon.*;
import java.awt.*;
public class SimboloProcesso extends Yourdon.SimboloMovel
{
    public boolean contemPonto (Point ponto)
    {
        int     raio, dx, dy, dist;

        dx = ponto.x - this.getCentro().x;
        dy = ponto.y - this.getCentro().y;
        dist = (int) Math.sqrt(dx*dx + dy*dy);

        raio = this.area.width / 2;

        if (dist > raio)
            return false;
        else
            return true;
    }
    public Point getAresta (Point ref)
    {
        int     raio, cx, cy, distx, disty, dx, dy;
        double  dist;
        
        raio = this.area.width / 2;

        cx = this.getCentro().x;
        cy = this.getCentro().y;

        distx = ref.x - cx;
        disty = ref.y - cy;
        dist = Math.sqrt(distx*distx + disty*disty);

        dx = (int) (raio * distx / dist);
        dy = (int) (raio * disty / dist);

        return new Point(cx + dx, cy + dy);
    }
    public Diagrama detalhe;
    public SimboloProcesso (Processo processo, Point ponto, boolean herdado)
    {
        super(PROCESSO, processo, ponto, herdado);
    }
    public void desenhar(Graphics areaGrafica)
    {
        FontMetrics fm;
        String      texto;

        if (!this.area.intersects(areaGrafica.getClipBounds())) return;

        fm = areaGrafica.getFontMetrics(areaGrafica.getFont());

        // Obtem o texto do elemento
        if (((Processo) this.elemento).pai == null)
            texto = this.elemento.getDescricao();
        else
            if (this.getHerdado())
            {
                texto = ((Processo) this.elemento).getNumeroCompleto();
                texto = "( " + texto + " )";
            }
            else
                texto = ((Processo) this.elemento).getNomeCompleto();

        if (this.detalhe != null) texto = texto + " *";

        texto = texto.trim();

        // Exibe somente os trinta primeiros caracteres
        if (texto.length() > this.MAXTAMTEXTO)
            texto = texto.substring(0,MAXTAMTEXTO) + "...";
        // Mínimo 3 caracteres
        else if (texto.length() < 3)
            texto = " " + texto + " ";

        // Calcula o diâmetroa altura e a largura necessária
        this.area.width = fm.stringWidth(texto) + 10;
        this.area.height = this.area.width;

        areaGrafica.setColor(cor);
        areaGrafica.fillOval(area.x, area.y, area.width, area.height);

        areaGrafica.setColor(borda);
        areaGrafica.drawOval(area.x, area.y, area.width, area.height);

        areaGrafica.setColor(TEXTO);
        areaGrafica.drawString(texto, this.area.x + 5, this.area.y + (this.area.height / 2) + (fm.getHeight() / 2) - 3);
    }
}