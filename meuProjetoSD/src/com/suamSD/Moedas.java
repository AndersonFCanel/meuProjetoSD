package com.suamSD;

public enum Moedas {
	 
    REAL  ( "Real"  ),
    DOLAR ( "Dolar" ),
    EURO  ( "Euro"  ),
	OUTRA ( "Outra" );
	
    private String descricao;
 
    Moedas(String descricao) {
        this.descricao = descricao;
    }
 
    public String getDescricao() {
        return descricao;
    }
}