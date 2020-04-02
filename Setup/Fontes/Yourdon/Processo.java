package Yourdon;

import java.util.Vector;
import Yourdon.*;
public class Processo extends Yourdon.ElementoEssencial
{
    public void removerNumeroSubProcesso (int num)
    {
        boolean r = this.numerosSubProcessos.removeElement(new Integer(num));
        return;
    }
    public void inserirNumeroSubProcesso (int numero)
    {
        numerosSubProcessos.addElement(new Integer(numero));
    }
    public int trocarNumeroSubProcesso (int velho, int novo)
    {
        int pos;

        // Verificar se novo numero já existe
        if (numerosSubProcessos.contains(new Integer(novo)))
            return -1;

        // Remove a inscrição velha
        numerosSubProcessos.removeElement(new Integer(velho));
        
        // Adiciona uma nova
        numerosSubProcessos.addElement(new Integer(novo));

        return 1;
    }
    public int getProximoNumero ()
    {
        int total, num, maior;

        total = numerosSubProcessos.size();
        maior = 0;

        for (int pos = 0; pos < total; pos++)
        {
            num = ((Integer)numerosSubProcessos.elementAt(pos)).intValue();
            if (num > maior) maior = num;
        }

        return (maior + 1);
    }
    protected Vector numerosSubProcessos;
    public String getNomeCompleto ()
    {
        if (this.pai == null)
        {
            return "0. " + this.descricao;
        }
        else
        {
            return this.getNumeroCompleto() + " " + this.descricao;
        }
    }
    public String getNumeroCompleto ()
    {
        if (this.pai == null)
        {
            return "";
        }
        else
        {
            return pai.getNumeroCompleto() + String.valueOf(this.numero)+  "." ;
        }
    }
    public Processo (String descricao)
    {
        this(null, descricao);
    }
    public Processo(Processo pai, String descricao)
    {
        super(descricao);
        this.pai = pai;

        this.numerosSubProcessos = new Vector();

        if (this.pai == null)
        {
            this.numero = 0;
        }
        else
        {
            this.numero = this.pai.getProximoNumero();
            this.pai.inserirNumeroSubProcesso(this.numero);
        }
    }
    public int setNumero (int novo)
    {
        if (this.pai.trocarNumeroSubProcesso(this.numero, novo) == -1)
            return -1;

        this.numero = novo;
        return 1;
    }
    public int getNumero ()
    {
        return this.numero;
    }
    public Processo pai;
    protected int numero;
}