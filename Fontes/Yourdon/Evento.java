package Yourdon;

import Yourdon.*;
import java.lang.*;
public class Evento extends Yourdon.ElementoEssencial
{
    public Evento (Entidade entidade, String descricao, String estimulo, String resposta)
    {
        super(descricao);
        this.entidade = entidade;
        this.estimulo = estimulo;
        this.resposta = resposta;
    }
    protected String estimulo;
    public void setEstimulo(String estimulo)
    {
        this.estimulo = estimulo;
    }
    public String getEstimulo()
    {
        return this.estimulo;
    }
    protected Entidade entidade;
    public void setEntidade(Entidade entidade)
    {
        this.entidade = entidade;
    }
    public Entidade getEntidade()
    {
        return this.entidade;
    }
    protected String resposta;
    public void setResposta(String resposta)
    {
        this.resposta = resposta;
    }
    public String getResposta()
    {
        return this.resposta;
    }







}