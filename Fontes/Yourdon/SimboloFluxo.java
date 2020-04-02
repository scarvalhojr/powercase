package Yourdon;

import Yourdon.*;
import java.awt.*;
public class SimboloFluxo extends Yourdon.Simbolo
{
    protected static final Color NORMAL = new Color(255,218,200);
    public SimboloFluxo (Fluxo fluxo, SimboloMovel origem, SimboloMovel destino, boolean herdado)
    {
        super(FLUXO, fluxo, herdado);

        int cx, cy;

        this.origem = origem;
        this.destino = destino;

        cx = (origem.getCentro().x + destino.getCentro().x) / 2;
        cy = (origem.getCentro().y + destino.getCentro().y) / 2;

        this.area = new Rectangle(cx, cy, 1, 1);
    }
    protected SimboloMovel destino;
    protected SimboloMovel origem;
    public void desenhar(Graphics areaGrafica)
    {
        FontMetrics fm;
        String      texto;
        Point       centro, ponto;
        int         cx, cy, x1, y1, x2, y2;

        cx = this.area.x + this.area.width / 2;
        cy = this.area.y + this.area.height / 2;
        centro = new Point(cx,cy);

        ponto = this.origem.getAresta(centro);
        x1 = ponto.x;
        y1 = ponto.y;

        if (((Fluxo) this.elemento).getTipoFluxo() != Fluxo.NORMAL)
            this.desenharSeta(areaGrafica, centro, ponto);

        ponto = this.destino.getAresta(centro);
        x2 = ponto.x;
        y2 = ponto.y;

        if (((Fluxo) this.elemento).getTipoFluxo() != Fluxo.INVERTIDO)
            this.desenharSeta(areaGrafica, centro, ponto);

        // Verifica se é necessário desenhar o fluxo
        // if (!(new Rectangle(x1,y1,Math.abs(x2-x1),x1)).intersects(areaGrafica.getClipBounds())) return;

        areaGrafica.setColor(BORDA);
        areaGrafica.drawLine(x1, y1, cx, cy);
        areaGrafica.drawLine(cx, cy, x2, y2);

        fm = areaGrafica.getFontMetrics(areaGrafica.getFont());

        // Obtem o texto do elemento
        texto = elemento.getDescricao();

        // Exibe somente os trinta primeiros caracteres
        if (texto.length() > this.MAXTAMTEXTO)
            texto = texto.substring(0,MAXTAMTEXTO) + "...";

        // Posiciona o retângulo da descrição
        this.area.width = fm.stringWidth(texto) + 10;
        this.area.height = fm.getHeight() + 5;
        this.area.x = cx - this.area.width / 2;
        this.area.y = cy - this.area.height / 2;

        // Desenha o retângulo
        areaGrafica.setColor(cor);
        areaGrafica.fillRect(this.area.x, this.area.y, this.area.width, this.area.height);

        // Desenha a descrição
        areaGrafica.setColor(TEXTO);
        areaGrafica.drawString(texto, this.area.x + 5, this.area.y + this.area.height - 5);
    }
    protected void desenharSeta(Graphics g, Point origem, Point destino)
    {
        final int   h = 10;
        int         x[], y[], distx, disty, dx, dy;
        double      dist;

        x = new int[3];
        y = new int[3];

        x[0] = destino.x;
        y[0] = destino.y;

        distx = origem.x - destino.x;
        disty = origem.y - destino.y;
        dist = Math.sqrt(distx*distx + disty*disty);

        dx = (int) (h * distx / dist);
        dy = (int) (h * disty / dist);

        if (Math.abs(dx) < 3)
        {
            // g.setColor(Color.blue);
            if (dx < 1)
                dx = h / 2;
            else
                dx = (dx / Math.abs(dx)) * h / 2;
            dy = (dy / Math.abs(dy)) * h;
            x[1] = x[0] - dx;
            y[1] = y[0] + dy;

            x[2] = x[0] + dx;
            y[2] = y[0] + dy;
        }
        else if (Math.abs(dy) < 3)
        {
            // g.setColor(Color.red);
            if (dy < 1)
                dy = h / 2;
            else
                dy = (dy / Math.abs(dy)) * h / 2;
            dx = (dx / Math.abs(dx)) * h;
            x[1] = x[0] + dx;
            y[1] = y[0] - dy;

            x[2] = x[0] + dx;
            y[2] = y[0] + dy;
        }
        else
        {
            // g.setColor(Color.gray);
            x[1] = x[0] - dx;
            y[1] = y[0] + 3 * dy / 2;

            x[2] = x[0] + 3 * dx;
            y[2] = y[0] + dy / 2;
        }
        g.setColor(BORDA);
        g.fillPolygon(x, y, 3);
    }
    public void selecionar (boolean selecionar)
    {
        if (selecionar)
            this.cor = SELECIONADO;
        else
            this.cor = NORMAL;
    }
}