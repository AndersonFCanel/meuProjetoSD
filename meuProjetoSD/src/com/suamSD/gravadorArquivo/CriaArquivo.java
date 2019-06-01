package com.suamSD.gravadorArquivo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author ander
 */
public class CriaArquivo
{

	public void criarArq( String text) throws IOException
	{
		final String CONTEUDO = text;

		File file = new File(".\\automato.xml");

		FileWriter     arquivo;
		PrintWriter    Arq;
		BufferedWriter bw = null ;
		
		// Se o arquivo nao existir, ele gera
		if ( !file.exists( ) )
		{
			file.createNewFile( );
			} 

		if ( file.exists( ) )
		{
			// Prepara para escrever no arquivo
		    arquivo = new FileWriter    (".\\automato.txt");
		    Arq     = new PrintWriter   (arquivo          );
		    bw      = new BufferedWriter(Arq              );
			
	        // Escreve e fecha arquivo
		    // gravarArq.printf(conteudo);
		    bw.write(CONTEUDO);
	    	bw.close();
		}

	}

}