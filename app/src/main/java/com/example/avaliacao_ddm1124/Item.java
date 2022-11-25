package com.example.avaliacao_ddm1124;

public class Item {
    private String chave;
    private String mesa;
    private String item;
    private String produto;
    private boolean atendido;
    public Item(String c, String m, String i, String p) {
        this.chave = c;
        this.mesa = m;
        this.item = i;
        this.produto = p;
        this.atendido = false;
    }
    public Item() {}
    public String getChave() { return this.chave; }
    public void setChave(String chave) { this.chave = chave; }
    public String getMesa() { return this.mesa; }
    public void setMesa(String mesa) { this.mesa = mesa; }
    public String getItem() { return this.item; }
    public void setItem(String item) { this.item = item; }
    public String getProduto() { return this.produto; }
    public void setProduto(String produto) { this.produto = produto; }
    public boolean isAtendido() { return this.atendido; }
    public void setAtendido(boolean atendido) { this.atendido = atendido; }
}