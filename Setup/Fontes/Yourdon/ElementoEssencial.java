package Yourdon;

import java.lang.*;
public abstract class ElementoEssencial extends java.lang.Object implements java.io.Serializable
{
    public void setDescricao (String descricao)
    {
        this.descricao = descricao;
    }
    public String getDescricao ()
    {
        return this.descricao;
    }
    public ElementoEssencial (String descricao)
    {
        this.descricao = descricao;
    }
    protected String descricao;
}