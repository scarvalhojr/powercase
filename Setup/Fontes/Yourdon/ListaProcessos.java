package Yourdon;

import Yourdon.*;
import java.lang.*;
public class ListaProcessos extends Yourdon.ListaElementos
{
    public void remover (Processo processo)
    {
        if (processo.pai != null)
            processo.pai.removerNumeroSubProcesso(processo.numero);

        this.elementos.removeElement(processo);
    }
    public Processo adicionar (Processo pai, String descricao)
    {
        Processo novoProcesso;

        novoProcesso = new Processo (pai, descricao);
        elementos.addElement(novoProcesso);
        return novoProcesso;
    }
    public Processo sistema;
    public ListaProcessos ()
    {
        super ();

        // Cria um processo para o sistema
        // com numero zero
        sistema = new Processo ("Novo Sistema");
    }
}