package Yourdon;

import Yourdon.*;
import java.awt.*;
public class SimboloEntidade extends Yourdon.SimboloMovel
{
    public SimboloEntidade (Entidade entidade, Point origem, boolean herdado)
    {
        super(ENTIDADE, entidade, origem, herdado);
    }
    public void desenhar(Graphics areaGrafica)
    {
        FontMetrics fm;
        String      texto;

        if (!this.area.intersects(areaGrafica.getClipBounds())) return;

        fm = areaGrafica.getFontMetrics(areaGrafica.getFont());

        // Obtem o texto do elemento
        texto= elemento.getDescricao();

        // Exibe somente os trinta primeiros caracteres
        if (texto.length() > this.MAXTAMTEXTO)
            texto = texto.substring(0,MAXTAMTEXTO) + "...";

        // Calcula a altura e a largura necessária
        this.area.width = fm.stringWidth(texto) + 10;
        this.area.height = fm.getHeight() + 30;

        areaGrafica.setColor(cor);
        areaGrafica.fillRect(area.x, area.y, area.width, area.height);

        areaGrafica.setColor(borda);
        areaGrafica.drawRect(area.x, area.y, area.width, area.height);

        areaGrafica.setColor(TEXTO);
        areaGrafica.drawString(texto, this.area.x + 5, this.area.y + this.area.height / 2 + 5);
    }
}