package Yourdon;

import java.lang.*;
import java.util.Vector;
import java.util.Enumeration;
import java.awt.*;
public class Diagrama extends java.lang.Object implements java.io.Serializable
{
    public InfoErroDiagrama verificarDiagrama ()
    {
        return verificarDiagrama (true);
    }
    public InfoErroDiagrama verificarDiagrama(boolean subNiveis)
    {
        InfoErroDiagrama    erros = new InfoErroDiagrama();
        Simbolo             simbolo;
        SimboloFluxo        fluxo;
        boolean             achou;

        for (Enumeration e1 = simbolos.elements(); e1.hasMoreElements();)
        {
            simbolo = (Simbolo) e1.nextElement();
            if (simbolo.getTipoSimbolo() != Simbolo.FLUXO)
            {
                
                // Se é para verificar todos os sub-níveis...
                if (subNiveis)
                    // Verifica se é um processo
                    if (simbolo.getTipoSimbolo() == Simbolo.PROCESSO)
                        // Verifica se o processo tem um dfd de detalhe
                        if (((SimboloProcesso) simbolo).detalhe != null)
                            // Acumula os erros
                            erros.adicionar( ((SimboloProcesso) simbolo).detalhe.verificarDiagrama() );
                
                // Verificar se algum fluxo o referencia
                Enumeration e2 = simbolos.elements();
                achou = false;

                do
                {
                    try
                    {
                        fluxo = (SimboloFluxo) e2.nextElement();
                        
                        if ((fluxo.origem == simbolo) || (fluxo.destino == simbolo))
                            achou = true;
                    }
                    catch (ClassCastException e) {}
                } while ((!achou) && (e2.hasMoreElements()));

                if (!achou)
                {
                    switch (simbolo.getTipoSimbolo())
                    {
                        case Simbolo.ENTIDADE:
                            erros.qtdeEntidades++;
                            break;

                        case Simbolo.DEPOSITO:
                            erros.qtdeDepositos++;
                            break;

                        case Simbolo.PROCESSO:
                            erros.qtdeProcessos++;
                            break;
                    }
                }
            }
        }

        return erros;
    }
    public void excluirSimboloHerdadoElemento (ElementoEssencial elemento)
    {
        Simbolo simbolo;

        for (Enumeration e = simbolos.elements(); e.hasMoreElements();)
        {
            simbolo = (Simbolo) e.nextElement();

            if (simbolo.getHerdado())
                if (simbolo.elemento == elemento)
                {
                    this.excluirSimbolo(simbolo);
                    return;
                }
        }
    }
    public void adicionarSimboloHerdadoDetalhe (Diagrama detalhe, Simbolo simbolo)
    {
        Point   ponto = new Point(10,10);

        switch(simbolo.getTipoSimbolo())
        {
            case Simbolo.ENTIDADE:
                detalhe.adicionarSimboloEntidade((Entidade) simbolo.elemento, ponto, true);
                break;

            case Simbolo.DEPOSITO:
                detalhe.adicionarSimboloDeposito((Deposito) simbolo.elemento, ponto, true);
                break;

            case Simbolo.PROCESSO:
                detalhe.adicionarSimboloProcesso((Processo) simbolo.elemento, ponto, true);
                break;
        }
    }
    public Diagrama decomporProcessoSelecionado ()
    {
        Simbolo         simbolo;
        SimboloProcesso simbProcesso;
        SimboloFluxo    simbFluxo;
        Diagrama        detalhe;

        simbolo = this.getSimboloSelecionado();
        if (simbolo == null) return null;

        try
        {
            simbProcesso = (SimboloProcesso) simbolo;
        }
        catch (ClassCastException e)
        {
            // O simbolo selecionado não é um processo
            return null;
        }

        // Não pode decompor um processo herdado
        if (simbProcesso.getHerdado()) return null;

        if (simbProcesso.detalhe != null) return simbProcesso.detalhe;

        // ainda não possui um diagrama de detalhe
        detalhe = new Diagrama ((Processo) simbProcesso.elemento);

        // Copiar elementos...
        for (Enumeration e = simbolos.elements(); e.hasMoreElements();)
        {
            try
            {
                simbFluxo = (SimboloFluxo) e.nextElement();
                if (simbFluxo.origem == simbProcesso)
                    this.adicionarSimboloHerdadoDetalhe(detalhe, (Simbolo) simbFluxo.destino);
                    // simbolo = simbFluxo.destino;
                else if (simbFluxo.destino == simbProcesso)
                    this.adicionarSimboloHerdadoDetalhe(detalhe, (Simbolo) simbFluxo.origem);
                    // simbolo = simbFluxo.origem;
                /*
                else
                    simbolo = null;

                if (simbolo != null)
                    this.adicionarSimboloHerdadoDetalhe(detalhe, simbolo.elemento);
                */
            }
            catch (ClassCastException ex) {}
        }

        simbProcesso.detalhe = detalhe;
        return detalhe;
    }
    public void selecionarSimbolo (Simbolo simbolo)
    {
        // Verifica se já tem algum símbolo selecionado
        // e retira a seleção
        if (simboloSelecionado != null)
        {
            simboloSelecionado.selecionar(false);
            simboloSelecionado = null;
        }
        simbolo.selecionar(true);
        simboloSelecionado = simbolo;
    }
    public void adicionarSimboloDeposito (Deposito deposito, Point origem)
    {
        this.adicionarSimboloDeposito (deposito, origem, false);
    }
    public void adicionarSimboloDeposito (Deposito deposito, Point origem, boolean herdado)
    {
        SimboloDeposito    simbolo;

        // Cria novo símbolo de entidade associado a uma entidade do projeto
        simbolo = new SimboloDeposito(deposito, origem, herdado);

        // Adiciona o símbolo ao vetor de símbolos do diagrama
        simbolos.addElement(simbolo);
    }
    public void excluirReferenciaElemento (ElementoEssencial elemento)
    {
        int     indice = 0;
        Simbolo simbolo;

        while (indice < simbolos.size())
        {
            simbolo = (Simbolo) simbolos.elementAt(indice);

            if (simbolo.getTipoSimbolo() == Simbolo.PROCESSO)
            {
                SimboloProcesso processo = (SimboloProcesso) simbolo;
                if (processo.detalhe != null)
                    processo.detalhe.excluirReferenciaElemento(elemento);
            }

            if (simbolo.elemento == elemento)
            {
                this.excluirSimbolo(simbolo);
                indice = 0;
            }
            else
                indice++;
        }
    }
    public void excluirSimbolo (Simbolo simbolo)
    {
        SimboloFluxo    simbFluxo;
        SimboloProcesso simbProcesso;

        // Se não for um fluxo: verificar se algum fluxo o referencia
        if (simbolo.getTipoSimbolo() != Simbolo.FLUXO)
            this.excluirFluxoReferencia((SimboloMovel)simbolo);

        simbolos.removeElement(simbolo);

        if (simbolo.getTipoSimbolo() == Simbolo.FLUXO)
        {
            simbFluxo = (SimboloFluxo) simbolo;

            if (simbFluxo.origem.getTipoSimbolo() == Simbolo.PROCESSO)
            {
                simbProcesso = (SimboloProcesso) simbFluxo.origem;
                if (simbProcesso.detalhe != null)
                    simbProcesso.detalhe.excluirSimboloHerdadoElemento(simbFluxo.destino.elemento);
            }

            if (simbFluxo.destino.getTipoSimbolo() == Simbolo.PROCESSO)
            {
                simbProcesso = (SimboloProcesso) simbFluxo.destino;
                if (simbProcesso.detalhe != null)
                    simbProcesso.detalhe.excluirSimboloHerdadoElemento(simbFluxo.origem.elemento);
            }

        }
    }
    protected void excluirFluxoReferencia (SimboloMovel movel)
    {
        SimboloFluxo    fluxo;
        int             total;

        total = simbolos.size();
        for (int indice=0; indice < total;)
            try
            {
                fluxo = (SimboloFluxo) simbolos.elementAt(indice);
                if ((fluxo.origem == movel) || (fluxo.destino == movel))
                {
                    this.excluirSimbolo((Simbolo) fluxo);
                    total--;
                }
                else
                    indice++;
            }
            catch (ClassCastException e)
            {
                indice++;
            }
    }

    public void adicionarSimboloFluxo (Fluxo fluxo, SimboloMovel origem, SimboloMovel destino)
    {
        this.adicionarSimboloFluxo (fluxo, origem, destino, false);
    }
    public void adicionarSimboloFluxo (Fluxo fluxo, SimboloMovel origem, SimboloMovel destino, boolean herdado)
    {
        SimboloFluxo simboloFluxo;

        simboloFluxo = new SimboloFluxo(fluxo, origem, destino, herdado);

        this.selecionarSimbolo(simboloFluxo);

        this.simbolos.addElement(simboloFluxo);

        if (origem.getTipoSimbolo() == Simbolo.PROCESSO)
            if (((SimboloProcesso) origem).detalhe != null)
                this.adicionarSimboloHerdadoDetalhe(((SimboloProcesso) origem).detalhe, destino);

        if (destino.getTipoSimbolo() == Simbolo.PROCESSO)
            if (((SimboloProcesso) destino).detalhe != null)
                this.adicionarSimboloHerdadoDetalhe(((SimboloProcesso) destino).detalhe, origem);

    }
    public void excluirSimboloSelecionado ()
    {
        if (simboloSelecionado == null) return;

        if (simboloSelecionado.getHerdado()) return;

        this.excluirSimbolo(simboloSelecionado);

        simboloSelecionado = null;
    }
    public void adicionarSimboloProcesso (Processo processo, Point ponto)
    {
        this.adicionarSimboloProcesso(processo, ponto, false);
    }
    public void adicionarSimboloProcesso (Processo processo, Point ponto, boolean herdado)
    {
        SimboloProcesso simbolo;

        // Cria novo símbolo de processo associado a um processo do projeto
        simbolo = new SimboloProcesso(processo, ponto, herdado);

        // Adiciona o símbolo ao vetor de símbolos do diagrama
        simbolos.addElement(simbolo);
    }
    public Processo processo;
    public void adicionarSimboloEntidade (Entidade entidade, Point origem, boolean herdado)
    {
        SimboloEntidade    simbolo;

        // Cria novo símbolo de entidade associado a uma entidade do projeto
        simbolo = new SimboloEntidade(entidade, origem, herdado);

        // Adiciona o símbolo ao vetor de símbolos do diagrama
        simbolos.addElement(simbolo);
    }
    public void adicionarSimboloEntidade (Entidade entidade, Point origem)
    {
        this.adicionarSimboloEntidade (entidade, origem, false);
    }
    public Diagrama (Processo processo)
    {
        this.simbolos = new Vector();
        this.processo = processo;
    }
    public void moverSimboloSelecionado (int incX, int incY)
    {

        simboloSelecionado.mover(incX, incY);

        /*
        SimboloMovel    movel;
        try {
            // Se o simbolo selecionado não for
            // do tipo SimboloMovel, a exceção
            // será capturada
            movel = (SimboloMovel) simboloSelecionado;
            movel.mover(incX, incY);
        } catch (ClassCastException e) {
            // O elemento selecionado não é móvel
        }
        */
    }
    public Simbolo selecionarSimbolo (Point ponto)
    {
        Simbolo simbolo;
        int     indice;

        // Verifica se já tem algum símbolo selecionado
        // e retira a seleção
        if (simboloSelecionado != null)
        {
            simboloSelecionado.selecionar(false);
            simboloSelecionado = null;
        }

        // Percorre o vetor do final para o começo procurando por
        // símbolos que estejam ocupando o ponto determinado
        indice = simbolos.size() - 1;
        while (indice >= 0)
        {
            simbolo = (Simbolo) simbolos.elementAt(indice);
            if (simbolo.contemPonto(ponto)) {
                // Achou: seleciona o elemento
                simbolo.selecionar(true);
                simboloSelecionado = simbolo;
                return simboloSelecionado;
            }
            indice--;
        }

        // Não existe símbolo nesta posição
        return null;
    }
    public Simbolo getSimboloSelecionado ()
    {
        return simboloSelecionado;
    }
    protected Simbolo simboloSelecionado;
    public void desenhar (Graphics areaGrafica)
    {
        Simbolo simbolo;
        int     total;

        total = simbolos.size();
        for (int indice=0; indice < total; indice++) {
            simbolo = (Simbolo) simbolos.elementAt(indice);
            simbolo.desenhar(areaGrafica);
        }
    }
    protected Vector simbolos;
}