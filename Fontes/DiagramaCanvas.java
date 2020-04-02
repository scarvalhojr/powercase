
import java.awt.*;
import Yourdon.*;
public class DiagramaCanvas extends java.awt.Canvas   
{
    protected void criarDeposito (Point ponto)
    {
        SelecionarDepositoDialog    dialogo;
        Deposito                    deposito;

        // Abrir diálogo para selecionar entidade
        dialogo = new SelecionarDepositoDialog(this.parentFrame, this.projeto);
        dialogo.setVisible(true);

        deposito = dialogo.selecionada;
        if (deposito != null)
        {
            this.diagrama.adicionarSimboloDeposito(deposito, ponto);
        }

        this.requestFocus();
        this.diagrama.selecionarSimbolo(ponto);
        this.setEstado(SELECIONAR);
        this.repaint();
    }
    protected SimboloMovel origemFluxo;
    protected void criarFluxo (Point ponto)
    {
        SimboloMovel    destinoFluxo;
        Simbolo         selecionado;
        Fluxo           fluxo;

        if (origemFluxo == null)
        {
            try
            {
                selecionado = this.diagrama.selecionarSimbolo(ponto);
                this.repaint();
                if (selecionado == null) return;
                origemFluxo = (SimboloMovel) selecionado;
            }
            catch (ClassCastException e)
            {
                // Se o simbolo selecionado não for móvel
                getToolkit().beep();
                origemFluxo = null;
            }
            return;
        }

        // A origem já foi escolhida: escolher destino
        try
        {
            selecionado = this.diagrama.selecionarSimbolo(ponto);
            if (selecionado == null) return;
            destinoFluxo = (SimboloMovel) selecionado;
        }
        catch (ClassCastException e)
        {
            // Se o simbolo selecionado não for móvel: erro
            getToolkit().beep();
            return;
        }

        // Verificar se a origem ou o destino são processos
        if ((origemFluxo.getTipoSimbolo() != origemFluxo.PROCESSO) &&
           (destinoFluxo.getTipoSimbolo() != destinoFluxo.PROCESSO))
        {
            // Nem a origem nem o destino são processos: fluxo inválido
            getToolkit().beep();
            return;
        }

        // Verificar se a origem e o destino são iguais
        if (origemFluxo == destinoFluxo)
        {
            getToolkit().beep();
            return;
        }

        fluxo = this.projeto.fluxos.adicionar("Novo fluxo");
        this.diagrama.adicionarSimboloFluxo(fluxo, origemFluxo, destinoFluxo);
        this.editar();
        this.setEstado(SELECIONAR);
        this.repaint();
    }
    protected void criarProcesso (Point ponto)
    {
        Processo processo;

        processo = this.projeto.processos.adicionar(this.diagrama.processo, "Novo processo");

        this.diagrama.adicionarSimboloProcesso(processo, ponto);

        this.diagrama.selecionarSimbolo(ponto);
        this.editar();

        this.setEstado(SELECIONAR);
        this.repaint();
    }
    protected Frame parentFrame;
    public void editar ()
    {
        Simbolo simbolo;

        simbolo = this.diagrama.getSimboloSelecionado();
        if (simbolo == null)
        {
            // Não há símbolo selecionado: editar processo
            // do diagrama
            (new EditarProcessoDialog(this.parentFrame, (Processo) this.diagrama.processo)).setVisible(true);
        }
        else
        {
            switch(simbolo.getTipoSimbolo())
            {
                case simbolo.ENTIDADE:
                    (new EditarEntidadeDialog(this.parentFrame, (Entidade) simbolo.elemento)).setVisible(true);
                    break;

                case simbolo.PROCESSO:
                    (new EditarProcessoDialog(this.parentFrame, (Processo) simbolo.elemento)).setVisible(true);
                    break;

                case simbolo.FLUXO:
                    (new EditarFluxoDialog(this.parentFrame, (Fluxo) simbolo.elemento)).setVisible(true);
                    break;

                case simbolo.DEPOSITO:
                    (new EditarDepositoDialog(this.parentFrame, (Deposito) simbolo.elemento)).setVisible(true);
                    break;


            }
        }
        this.requestFocus();
        return;
    }

    public void update(Graphics g)
    {
        this.paint(g);
    }
    protected Graphics bufferGrafico;
    protected Image bufferImagem;
    protected Point baseArrasto;
    protected void selecionarSimbolo (Point ponto)
    {
        Simbolo         simbolo;

        simbolo = this.diagrama.selecionarSimbolo(ponto);
        if (simbolo != null)
        {
            // Um símbolo foi selecionado

            // Guarda a posição do mouse para ser a
            // base de um possível arrasto
            baseArrasto = ponto;

            this.arrastar = true;
        }
    }
    protected boolean arrastar;
    protected void criarEntidade (Point ponto)
    {
        SelecionarEntidadeDialog    dialogo;
        Entidade                    entidade;

        // Abrir diálogo para selecionar entidade
        dialogo = new SelecionarEntidadeDialog(this.parentFrame, this.projeto);
        dialogo.setVisible(true);

        entidade = dialogo.selecionada;
        if (entidade != null)
        {
            this.diagrama.adicionarSimboloEntidade(entidade, ponto);
        }

        this.requestFocus();
        this.diagrama.selecionarSimbolo(ponto);
        this.setEstado(SELECIONAR);
        this.repaint();
    }
    public void decomporProcessoSelecionado ()
    {
        Diagrama        detalhe;

        detalhe = this.diagrama.decomporProcessoSelecionado();

        if (detalhe == null)
        {
            getToolkit().beep();
            return;
        }

        // Abre nova janela com o diagrama
        (new DFDDialog(this.parentFrame, this.projeto, detalhe)).setVisible(true);
    }
    public void excluirSimboloSelecionado ()
    {
        Simbolo simbolo;

        simbolo = this.diagrama.getSimboloSelecionado();

        if (simbolo == null) return;

        this.diagrama.excluirSimboloSelecionado();

        switch(simbolo.getTipoSimbolo()) {
            case simbolo.ENTIDADE:
            case simbolo.DEPOSITO:
                // Excluir somente o simbolo gráfico
                break;

            case simbolo.PROCESSO:
                // Excluir o elemento
                this.projeto.processos.remover((Processo) simbolo.elemento);
                break;

            case simbolo.FLUXO:
                // Excluir o fluxo
                this.projeto.fluxos.remover((Fluxo) simbolo.elemento);
                break;
        }
        this.repaint();
    }
    protected static final Cursor CURSOR_CRIAR = new Cursor (Cursor.HAND_CURSOR);
    protected static final Cursor CURSOR_SELECIONAR = new Cursor (Cursor.DEFAULT_CURSOR);
    protected int estado;
    public void setEstado (int novoEstado)
    {
        // Trocar de estado
        this.origemFluxo = null;

        if ((novoEstado >= 1) && (novoEstado <= 4))
        {
            // Criar processo, criar deposito...
            this.setCursor(CURSOR_CRIAR);
            this.estado = novoEstado;
        }
        else
        {
            // Selecionar ou estado invalido
            this.setCursor(CURSOR_SELECIONAR);
            this.estado = SELECIONAR;
        }
    }
    public static final int CRIARFLUXO = 4;
    public static final int CRIARDEPOSITO = 3;
    public static final int CRIARPROCESSO = 2;
    public static final int CRIARENTIDADE = 1;
    public static final int SELECIONAR = 0;
    protected Projeto projeto;
    public Diagrama diagrama;

    public void paint(Graphics g)
    {
        Rectangle   area = g.getClipBounds();

        if (bufferImagem == null)
        {
            bufferImagem = this.createImage(this.getSize().width, this.getSize().height);
            bufferGrafico = bufferImagem.getGraphics();
        }

        bufferGrafico.setClip(area);
        bufferGrafico.clearRect(area.x, area.y, area.width, area.height);
        diagrama.desenhar(bufferGrafico);

        g.drawImage(bufferImagem,0,0,this);
    }
    public DiagramaCanvas (Frame parentFrame, Projeto projeto, Diagrama diagrama)
    {
        this.parentFrame = parentFrame;
        this.projeto = projeto;
        this.diagrama = diagrama;

        // Cria e registra as classes responsáveis pelos eventos de mouse
        // e eventos de componente
        DiagramaCanvasMouseListener mouseListener = new DiagramaCanvasMouseListener();
        this.addMouseListener(mouseListener);
        DiagramaCanvasMouseMotionListener mouseMotionListener = new DiagramaCanvasMouseMotionListener();
        this.addMouseMotionListener(mouseMotionListener);

        // Inicia no estado SELECIONAR
        this.setEstado(SELECIONAR);
	}

	class DiagramaCanvasMouseListener extends java.awt.event.MouseAdapter
	{
		public void mousePressed(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == DiagramaCanvas.this)
				canvas_MousePressed(event);
		}

		public void mouseReleased(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == DiagramaCanvas.this)
				canvas_MouseReleased(event);
		}
	}

	void canvas_MousePressed(java.awt.event.MouseEvent event)
	{
	    switch(estado) {
	        case SELECIONAR:
	            // Seleciona o simbolo
	            this.selecionarSimbolo(event.getPoint());

	            if (event.getClickCount() > 1)
	                if (this.diagrama.getSimboloSelecionado() != null)
    	                this.editar();

                this.repaint();
	            break;

	        case CRIARENTIDADE:
	            this.criarEntidade(event.getPoint());
	            break;

	        case CRIARDEPOSITO:
	            this.criarDeposito(event.getPoint());
	            break;

	        case CRIARPROCESSO:
	            this.criarProcesso(event.getPoint());
	            break;

	        case CRIARFLUXO:
	            this.criarFluxo(event.getPoint());
	            break;
	    }
	}

	void canvas_MouseReleased(java.awt.event.MouseEvent event)
	{
	    this.arrastar = false;
    }

	class DiagramaCanvasMouseMotionListener extends java.awt.event.MouseMotionAdapter
	{
		public void mouseDragged(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == DiagramaCanvas.this)
				canvas_MouseDragged(event);
		}
	}

	void canvas_MouseDragged(java.awt.event.MouseEvent event)
	{
	    if (arrastar) {
	        Point   novoPonto;
	        int     incX, incY;

	        novoPonto = event.getPoint();

	        incX = novoPonto.x - baseArrasto.x;
	        incY = novoPonto.y - baseArrasto.y;
	        baseArrasto = novoPonto;

	        this.diagrama.moverSimboloSelecionado(incX, incY);
	        this.repaint();
	    }
	}
}