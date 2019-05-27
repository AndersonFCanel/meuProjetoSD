package com.suamSD;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;

public class AutomatoService implements AutomatoInterface 
{
	public static  int       contadorFuncTran  = 0;
	public static  Character identificaUsuario = 'A';
	static         Integer   contaPasso        = 1;
	
    public int getContadorFuncTran( )
    {
		return contadorFuncTran;
	}

	public static int setContadorFuncTran( )
	{
		return contadorFuncTran ++;
	}
	   
    public Character getIdentificaUsuario( ) 
    {
        return identificaUsuario;
    }

    public void setIdentificaUsuario( Character identificador )
    {
        identificaUsuario = identificador;
    }
    
    
    
    @Override
    public Integer getContaPasso( ) throws RemoteException 
    {
         return contaPasso;
    }

    @Override
    public void incrementaContaPasso( ) throws RemoteException
    {
        contaPasso++;
        
    }
    
    private static HashMap<Integer, String> conjuntoDeEstadosMap = new HashMap<Integer, String>( );
    //private static HashMap<Integer, String> conjuntoDeEstadosFinaisMap = new HashMap<Integer, String>( );

    private static String alfabetoIMPRIME;
    private static String conjuntoDeEstadosTerminaisIMPRIME;
    private static String estIniIMPRIME;
    private static String conjEstTermIMPRIME;
    
    private static String estadoIni = "";
    private static String conjuntoEstadosTerminais = "";
       
    private static Character[ ] conjuntodeSimbolos_Alfabeto;
    private static String       conjuntoDeEstadosTerminaisEnaoTerminais = "";
    private static String   [ ] conjuntoFuncaoDeTransicaoDeEstados;
    
    private static int     estadoi;
    private static int [ ] estadosf;
    
    static int quantidadeDeFuncTransPossiveis ;
    
    private static String[ ] estadoPartidaS  ;
    private static String[ ] caracConsumidoS ;
    private static String[ ] estadoDestinoS  ;
    
    private static Integer  [ ] estadoPartida ; 
    private static Character[ ] le ;
    private static Integer  [ ] estadoDestino ;
    
    public int getTamanhoCjtFuncTran( )
	{
		return  conjuntodeSimbolos_Alfabeto.length
	               * conjuntoDeEstadosTerminaisEnaoTerminais.length( );
	}
    
    public int tamanhoCjtFuncTran( )
    {
    	return  conjuntodeSimbolos_Alfabeto.length
               * conjuntoDeEstadosTerminaisEnaoTerminais.length( );
    }   

    /**
     * Trecho responsável por receber o conjunto de simbolos(Alfabeto) e armazenar o
     * mesmo sem os caracteres desnecessários em um array de char.
     *
     */
    public String setAlfabeto( String alf )
    {
        String  alfabeto = alf; 
        String  responseValidAlf; 

        responseValidAlf = verificaConjuntoCaracteres_Alfabeto( alfabeto );
        
        //Armazenando para imprimir, tratando a formatação
        alfabetoIMPRIME = alfabeto;
        alfabeto        = removeNulos( alfabeto );// Removendo {,}

        // Poderia ser um array de object, caso cada elemento do conjunto fosse um conjunto de simbolos
        conjuntodeSimbolos_Alfabeto = new Character[ alfabeto.length( ) ];
        
        int z = 0;
        for (char ch : alfabeto.toCharArray( )) {
            conjuntodeSimbolos_Alfabeto[z] = ch;
            z++;
        }
        
        //conjuntodeSimbolos_Alfabeto = alfabeto.chars( ).mapToObj( c -> ( char ) c ).toArray( Character[ ]::new ); 
        
        return responseValidAlf;
    }

    /**
     * Trecho responsável por receber o conjunto de estados (Terminais e não
     * Terminais), e armazenar o mesmo sem os caracteres desnecessários em um array
     * de int, convertendo a posição de um estado fornecido no conjunto em um valor
     * numérico, em ordem crescente correspondente a ordem dos estados fornecidos.
     * 
     * @return
     */
    public String setEstados( String est ) 
    {
        String responseValidEst;

        conjuntoDeEstadosTerminaisEnaoTerminais =  est;// entraConjuntoEstado( );
        responseValidEst = verificaEst( conjuntoDeEstadosTerminaisEnaoTerminais );
     
        conjuntoDeEstadosTerminaisIMPRIME       = conjuntoDeEstadosTerminaisEnaoTerminais;
        conjuntoDeEstadosTerminaisEnaoTerminais = removeNulos( conjuntoDeEstadosTerminaisEnaoTerminais );// Removendo {,}

        int   estados           = conjuntoDeEstadosTerminaisEnaoTerminais.length( );
        int[ ] conjuntoDeEstados = new int[ estados ];
        int a = 0;

        //Mapeando posição dos estados
        for (Character ch : conjuntoDeEstadosTerminaisEnaoTerminais.toCharArray( ) )
        {
            conjuntoDeEstadosMap.put( a, ch.toString( ) );
            conjuntoDeEstados [ a ] = a;
            a++;
        }
        
         return responseValidEst;
    }
    
   void inicializaVetores( ) 
   {
	   quantidadeDeFuncTransPossiveis = conjuntodeSimbolos_Alfabeto.length
                                       * conjuntoDeEstadosTerminaisEnaoTerminais.length( );
	    
	    estadoPartidaS  = new String[ quantidadeDeFuncTransPossiveis ];
	    caracConsumidoS = new String[ quantidadeDeFuncTransPossiveis ];
	    estadoDestinoS  = new String[ quantidadeDeFuncTransPossiveis ];
	    
	    estadoPartida = new Integer  [ quantidadeDeFuncTransPossiveis ]; 
	    le            = new Character[ quantidadeDeFuncTransPossiveis ];
	    estadoDestino = new Integer  [ quantidadeDeFuncTransPossiveis ];
	    
	    conjuntoFuncaoDeTransicaoDeEstados  = new String [ quantidadeDeFuncTransPossiveis ]; 
   }
    
    /**
     * Trecho responsável por receber entrada do conjunto de regras de transição
     * de estados (Regra de Produção), funciona da seguinte forma:
     *  # ESTADO (LADO ESQUERDO), CONSOME (CENTRO); VAI PARA ESTADO (LADO DIREITO)#
     */
    public String setRegra( String func ) 
    {   	
    	if( !func.matches( "^\\S*,\\S*;\\S$*" ) )
        {
    		if( !"".equals( func ) )
    			return "Formato inválido";
   		}
    	
    	if(contadorFuncTran == 0)
    	     inicializaVetores( );
    	
    	String responseValidaFunc = "Entrada Verificada";
    	
    	setContadorFuncTran( );
    
    	conjuntoFuncaoDeTransicaoDeEstados[ contadorFuncTran - 1]  = func;
        
		for ( int i = 0; i < quantidadeDeFuncTransPossiveis; i++ ) 
		{
			if ( conjuntoFuncaoDeTransicaoDeEstados[ i ] == null )
				//break;
				continue;
			
			if ( conjuntoFuncaoDeTransicaoDeEstados[ i ].isEmpty( ) )
				//break;
				continue;
			
				
			String[ ] p1 = conjuntoFuncaoDeTransicaoDeEstados[ i ].split( ";" );
			String[ ] p2 = p1[0].split                                  ( "," );

			estadoPartidaS [ i ] = p2[ 0 ];
			caracConsumidoS[ i ] = p2[ 1 ];
			estadoDestinoS [ i ] = p1[ 1 ];
		}

		for ( int p = 0; p < quantidadeDeFuncTransPossiveis; p++ )
		{
			String aux = estadoPartidaS[ p ];
			int h = 0;

			for ( Character ch : conjuntoDeEstadosTerminaisEnaoTerminais.toCharArray( ) ) 
			{
				for ( int j = 0; j < quantidadeDeFuncTransPossiveis; j++ ) 
				{
					if ( ch.toString( ).equals( estadoPartidaS[ j ] ) ) 
						estadoPartida[ j ] = h;
				}

				for ( int j = 0; j < quantidadeDeFuncTransPossiveis; j++ )
				{
					if ( ch.toString( ).equals( estadoDestinoS[ j ] ) ) 
						estadoDestino[ j ] = h;
				}
				h++;
			}
			aux = caracConsumidoS[ p ];
			
			if ( aux != null )
			{
				le[ p ] = aux.charAt( 0 );
			}
			else
			{
				le[ p ] = ( Character ) null;
			}
			
			if( contadorFuncTran == quantidadeDeFuncTransPossiveis )
				return "OK";	
		}
        return responseValidaFunc ;
    }

    /**
     * Trecho responsável por receber o estado inicial, identificar posição
     * correspondente no conjunto de estados (Terminais e não terminais) e armazena
     * o mesmo sem os caracteres desnecessários em uma variável do tipo int com o
     * valor correspondente a sua posição * no conjunto de estados.
     */
    public String setEstInicial( String ei )
    {
        String responseValidaEstadoI = "OK";
        
        estadoIni     = "{" + verificaEstInicial( ei ) + "}";
        estadoIni     = removeNulos            ( estadoIni );
        estIniIMPRIME = estadoIni;
        
        estadoi = conjuntoDeEstadosTerminaisEnaoTerminais.indexOf( estadoIni );
        
        return responseValidaEstadoI;
    }

    /**
     * Trecho responsável por receber o conjunto de estados finais (Terminais), e
     * armazenar o mesmo sem os caracteres desnecessários em um array de int,
     * identificando a posição de um estado fornecido no conjunto em um valor
     * numérico, correspondente a posição do mesmo no conjunto de estados (Terminais
     * e não Terminais)e armazenando estes valores em um array de tipo int.
     * 
     * @return
     */
     public String setConjuntoEstadosFinais( String cjtFin )
     {
        // ENTRA COM ESTADOS FINAIS
        boolean validEstFim = false;
        String[ ] estFin;
        String validaConjuntoEstadosFin = "OK";
        
        do {
            conjuntoEstadosTerminais = cjtFin;

            estFin = splitVirgula( conjuntoEstadosTerminais );
            
            for ( String est : estFin ) 
            {
                if ( conjuntoDeEstadosTerminaisEnaoTerminais.contains( est ) )
                {
                    validEstFim = false;
                } 
                else
                {
                    validEstFim = true;
                    return "Estado no inexistente no conjunto de estados." ;
                }
            }       
        }
        while ( validEstFim );

        conjEstTermIMPRIME       = conjuntoEstadosTerminais;
        conjuntoEstadosTerminais = removeNulos( conjuntoEstadosTerminais );
        estadosf                 = new int[ conjuntoEstadosTerminais.length( ) ];
        int  b                   = 0, 
        	 y                   = 0;
                                 
        //***********Sugestão de código Eclipse
        char[] charArray = conjuntoDeEstadosTerminaisEnaoTerminais.toCharArray( );
        
        for (int i = 0; i < charArray.length; i++) {
            for ( Character ch1 : conjuntoEstadosTerminais.toCharArray( ) )
            {
                if ( conjuntoDeEstadosMap.get( y ).equals( ch1.toString( ) ) )
                {
                    estadosf[ b ] = y;
                    b++;
                    break;
                }
            }
            y++;
            
            imprimirAutomato(alfabetoIMPRIME, conjuntoDeEstadosTerminaisIMPRIME, estadoPartida, 
                             estadoDestino, le, estIniIMPRIME, conjEstTermIMPRIME);
        }
        //****************

        /*Meu código
         * for ( Character ch : conjuntoDeEstadosTerminaisEnaoTerminais.toCharArray( ) ) 
        {
            for ( Character ch1 : conjuntoEstadosTerminais.toCharArray( ) )
            {
                if ( conjuntoDeEstadosMap.get( y ).equals( ch1.toString( ) ) )
                {
                    estadosf[ b ] = y;
                    b++;
                    break;
                }
            }
            y++;
            
            imprimirAutomato(alfabetoIMPRIME, conjuntoDeEstadosTerminaisIMPRIME, estadoPartida, 
                             estadoDestino, le, estIniIMPRIME, conjEstTermIMPRIME);
        }
         * */
        
        return validaConjuntoEstadosFin;
    }

     
    /**
     * Entrada realizada pelo usuário, realiza verificação para checar se a palavra
     * pode ser formada com os caracteres do conjunto de símbolo (alfabeto).
     */
    public String checaPalavra( String palavraInserida ) 
    {
        String palavraS;
        String responseValidaPalavra = "OK";
        
        do {
            int teste = 0;
            palavraS = palavraInserida;
            
            // Variável reponsável por receber a validação da palavra informada pelo autmato
            responseValidaPalavra = VerificaPalavra( palavraS, conjuntodeSimbolos_Alfabeto );

            if ( "OK".equals( responseValidaPalavra ) ) 
            {
                char[ ] palavra = palavraS.toCharArray( );
                int     estadoa = estadoi;

                for ( int p = 0; p < palavra.length; p++ ) 
                {
                    for ( int k = 0; k < conjuntoFuncaoDeTransicaoDeEstados.length; k++ ) 
                    {
                        if( le[ k ] == null )
                            continue;
                            
                        if ( ( palavra[ p ] == le[ k ]) && ( estadoPartida[ k ] == estadoa ) )
                        {
                            estadoa = estadoDestino[ k ];
                            break;
                        } 
                    }

                    for ( int k = 0; k < conjuntoEstadosTerminais.length( ); k++ )
                    {
                        if ( estadoa == estadosf[ k ] )
                        {
                            teste = 1;
                        } else {
                            teste = 0;
                        }
                    }
                }
                
                if ( teste == 1 ) 
                {
                     return  "PALAVRA ACEITA PELO AUTOMATO\n\n";
                    // break;
                } else {
                    return   "PALAVRA NÃO ACEITA PELO AUTOMATO\n\n";
                    // break;
                }
            }
            else
            	return responseValidaPalavra;
        }
        while ( !palavraS.equalsIgnoreCase( "s" ) );
            
    }

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    // %---------------METODOS UTILIZADOS NO CÓDIGO------------%
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    /*
     ****************************************************************
     * METODOS DE IMPRESSÃO DE INFORMAÇOES
     ****************************************************************
     */
    /**
     * 
     * @param alf
     * @param est
     * @param estadoPartida
     * @param estadoDestino
     * @param le
     * @param estIn
     * @param conjuntoEstadosFinais
     */
    private static String imprimirAutomato( String alf, String est, Integer[ ] estadoPartida, Integer[ ] estadoDestino, Character[ ] le,
            String estIn, String conjuntoEstadosFinais ) 
    {
        
        if ( !( estadoPartida != null ) )
            estadoPartida = new Integer[ 0 ];
        
        if ( !( estadoDestino != null ) )
           estadoDestino = new Integer[ 0 ];
        
        if ( !( le != null ) )
               le = new Character[ 0 ];
        
        String[ ] estP = new String[ estadoPartida.length ];
        
        
        
        int b = 0;
        for ( Integer key : estadoPartida ) 
        {
            estP[ b ] = conjuntoDeEstadosMap.get( key );
            b++;
        }

        String[ ] estD = new String[ estadoDestino.length ];
        ;
        int c = 0;
        for ( Integer key : estadoDestino )
        {
            estD[c] = conjuntoDeEstadosMap.get( key );
            c++;
        }
  
        return "**************************************************\n" 
        + "\tIMPRIMINDO DADOS DO AUTOMATO\n"
        + "\t\t\t ==>NOTAÇÃO UTILIZADA <== \n" 
        + "\tO conjunto de simbolos - alfabeto: Σ \n"
        + "\tO conjunto dos estados terminais e não terminais: Q = {S1, S2...}\n"
        + "\tAs transicoes: (δ: Q × Σ → Q)\n" 
        + "\tO  estado Inicial: q0\n"
        + "\tO conjunto dos estados terminais: F\n" 
        + "\tM = (Q, Σ, (δ: Q × Σ → Q), q0, F)\n"
        + "\n\t\t ==>DADOS INFORMADOS <==\n" + "\tΣ   = " + alf + "\n" + "" 
        + "\tQ   = " + est + "\n"
        + "\tδ   = \n"
        + "ESTADO PARTIDA:         Q" + Arrays.toString(estP) + "\n"
        + "CARACTER CONSUMIDO: Σ" + Arrays.toString(le) + "\n" 
        + "ESTADO DESTINO:          Q"+ Arrays.toString(estD) + "\n" + "" 
        + "\tq0  = " + estIn + "\n" 
        + "" + "\tF   = "+ conjuntoEstadosFinais + "\n" + ""
        + "**************************************************";
    }

    /*
     * *********************************************
     * ****************** MÉTODOS VALIDADORES ******
     * *********************************************
     */

    /**
     * 
     * @param alfabeto
     * @return
     */
    private static String verificaConjuntoCaracteres_Alfabeto( String alfabeto )
    {
        String validadorAlf = "OK";

        // Entrada Vazia
        if (alfabeto.equals(" ") || alfabeto.length( ) < 1 || alfabeto.isEmpty( ) || alfabeto.length( ) > 5)
        {
            return  "ENTRADA INVÁLIDA\n" + "Tamanho do alfabeto fora do range permitido!";
        }

        // Entrada iniciando pela virgula
        if (alfabeto.charAt(0) == ',')
        {
             return  "ENTRADA INVÁLIDA\n" + "Não começe a inserção pela virgula";
        }

        // Caracteres iguais, exeção de ','
        int w = 1;
        
        for1: for ( int k = 0; k < alfabeto.length( ); k = k + 2 ) 
        {
            if  (k == alfabeto.length( ) || w == alfabeto.length( ) )
            {
                break for1;
            }

            if ( alfabeto.charAt(w) != ',' )
            {
                return "ENTRADA INVÁLIDA";
            }
            
            w = w + 2;

            for ( int j = k + 2; j < alfabeto.length( ); j = j + 2 )
            {
                if ( alfabeto.charAt( k ) == alfabeto.charAt( j ) ) 
                {
                    return "ENTRADA INVÁLIDA\n" + "Você entrou com caracteres iguais no alfabeto!\n"
                            + alfabeto.charAt( k ) + " = " + alfabeto.charAt( j );
                }
            }
        }
        return validadorAlf;
    }

    /**
     * VERIFICADOR DE ENTRADA DE DADOS PARA O CONJUNTO DE ESTADOS
     * 
     * @param estados
     * @return
     */
    private static String verificaEst( String estados )
    {
        String validador = "OK";

        // ESTADO COM TAMANHO INFERIOR AO PERMITIDO, = 0 ou >5.
        if ( estados.length( ) < 1 || estados.length( ) > 5 || estados.equals(" ") || estados.isEmpty( ) )
        {
            return "ENTRADA INVALIDA\n" + "Tamanho do conjunto fora do range permitido!";
        }

        // INSERÇÃO DE ESTADOS NÃO PODE COMEÇAR PELA VIRGULA.
        if ( estados.charAt( 0 ) == ',' ) 
        {
            //JOptionPane.showMessageDialog( null, "ENTRADA INVALIDA\n" + "Não começe a inserção pela virgula" );
            
            return "ENTRADA INVALIDA\n" + "Não começe a inserção pela virgula";
        }

        // ESTADOS IGUIAS
        if ( !( "OK".equals( verificaConjuntoEstados( estados ) ) ) ) 
        {
            return    "ENTRADA INVALIDA\n"
                    + "Existem elementos iguais no conjunto!";
        }
        
        return validador;// = false;
    }

    // ===>>>#####ESTADOS IGUAIS --- CORRIGIR VALIDADOR DE ESTADOS
    private static String verificaConjuntoEstados( String estados ) 
    {
        try 
        {
            if ( estados.equals( null ) ) 
            {
            	return "ERRO";
            }
        } 
        catch ( Exception e )
        {
            return "";
        }

        String[ ] estAux = estados.split( "," );

        for ( int i = 0; i < estAux.length; i++ )
        {
            for ( int j = i + 1; j < estAux.length; j++ ) 
            {
                if ( estAux[ i ].equals(estAux[ j ] ) )
                {
                	i = estAux.length;
                    return    "ENTRADA INVALIDA\n"
                            + "Existem elementos iguais no conjunto!";
                }
            }
        }
        return "OK";
    }

    /**
     * VERIFICA ESTADO INICIAL
     * 
     * @param conjuntoDeEstados
     * @return
     */
    private static String verificaEstInicial(  String estIn)
    {
        
        String  estadoInicial = estIn; 

        if ( !conjuntoDeEstadosTerminaisEnaoTerminais.contains( estadoInicial ) )
        {
            return "Estado no inexistente no conjunto de estados." ;
        } 
    
        return estadoInicial;
    }

    /**
     * VERIFICA SE PALAVRA PERTENCE AO ALFABETO
     * 
     * @param palavra
     * @param alf
     * @return
     */
    private static String VerificaPalavra( String palavra, Character[ ] alf )
    { 
    	String responseVerificaPal = "OK";
        int cont = 0;
        
        for ( int x = 0; x < palavra.length( ); x++ )
        {
            Character caracPalavra = palavra.charAt( x );
            
            for ( int y = 0; y < alf.length; y++ ) 
            {
                if ( caracPalavra.equals( alf [ y ] ) ) 
                {
                    cont++;
                }
            }
        }

        if ( cont == palavra.length( ) ) 
        {
            return responseVerificaPal;
        }
        else 
        {
            return  "A palavra \"" + palavra
                            + "\" contém simbolos não pertencentes ao conjunto de simbolos (alfabeto,Σ= "
                            + alfabetoIMPRIME + ")!";
           
        }
    }

    /*
     ****************************************************************
     * MÉTODOS UTIL'S
     ****************************************************************
     */

    /**
     * REMOVE CARACTERES DE FORMATAÇÃO DO CONJUNTO, EX: {,}
     * 
     * @param conjunto
     * @return
     */
    public static String removeNulos( String conjunto ) 
    {
        String[ ] nulos = { "{", "}", "," };// identificando carateres de formatação do conjunto
        for ( String n : nulos )
        {
            conjunto = conjunto.replace( n, "" );
        }
        return conjunto;

    }

    // SPLIT PARA SEPARAR ENTRADAS QUE CONTEM VIRGULA
    private static String[ ] splitVirgula( String valor )
    {
        return valor.split(",");// Divide a String quando ocorrer ","
    }

    
    /*public static String valoresAtuais( String info )
    {
        if ( info.equalsIgnoreCase( "?" ) ) 
        {
        	return imprimirAutomato( alfabetoIMPRIME, conjuntoDeEstadosTerminaisIMPRIME, estadoPartida, estadoDestino, le,
                    estadoIni, conjuntoEstadosTerminais );
            
        }   
        return "";
    }*/
    
 
    //METODOS ESPECIFICOS PARA IMPRESSÃO DE DADOS DE 2 USUÁRIOS
    /*
    * Cliente 1 - Insere conjunto de caracteres.
      Cliente 2 - Insere conjunto de estados terminais e não terminais.
      Cliente 1-  Insere o conjunto de regras de produção.
      Cliente 2-  Insere estado inicial.
      Cliente 1-  Insere conjunto de estados finais.
      Cliente 2 - Palavra a ser validada pelo autômato.
      Cliente 1 e 2 - Recebem o retorno de suas respectivas entradas.
     * */
    @Override
    public String imprimirAutomatoCliente( Character c ) throws RemoteException 
    {
    	if( c == '@')
    	{
        return imprimirAutomato(alfabetoIMPRIME, conjuntoDeEstadosTerminaisIMPRIME, estadoPartida, 
                estadoDestino, le, estIniIMPRIME, conjEstTermIMPRIME);  
       	}
    	if( c == 'A')
    	{
        return imprimirAutomatoCliAouB(alfabetoIMPRIME, conjuntoDeEstadosTerminaisIMPRIME, estadoPartida, 
                estadoDestino, le, estIniIMPRIME, conjEstTermIMPRIME, 'A');  
       	}
    	if( c == 'B')
    	{
        return imprimirAutomatoCliAouB(alfabetoIMPRIME, conjuntoDeEstadosTerminaisIMPRIME, estadoPartida, 
                estadoDestino, le, estIniIMPRIME, conjEstTermIMPRIME, 'B');  
       	}
		return null;  	
   	}
    
    private static String imprimirAutomatoCliAouB( String alf, String est, Integer[ ] estadoPartida, Integer[ ] estadoDestino, Character[ ] le,
            String estIn, String conjuntoEstadosFinais, char cliente ) 
    {
        
        if ( !( estadoPartida != null ) )
            estadoPartida = new Integer[ 0 ];
        
        if ( !( estadoDestino != null ) )
           estadoDestino = new Integer[ 0 ];
        
        if ( !( le != null ) )
               le = new Character[ 0 ];
        
        String[ ] estP = new String[ estadoPartida.length ];
        
        
        
        int b = 0;
        for ( Integer key : estadoPartida ) 
        {
            estP[ b ] = conjuntoDeEstadosMap.get( key );
            b++;
        }

        String[ ] estD = new String[ estadoDestino.length ];
        ;
        int c = 0;
        for ( Integer key : estadoDestino )
        {
            estD[c] = conjuntoDeEstadosMap.get( key );
            c++;
        }
          
        if( cliente == 'A') {
             return "**************************************************\n" 
                  + "\tIMPRIMINDO DADOS INSERIDOS PELO CLIENTE A:\n"
                  + "\t\t\t ==>NOTAÇÃO UTILIZADA <== \n" 
                  + "\tO conjunto de simbolos - alfabeto: Σ \n" 
                  + "\tAs transicoes: (δ: Q × Σ → Q)\n" 
                  + "\tO conjunto dos estados terminais: F\n"
                  + "\tM = (Q, Σ, (δ: Q × Σ → Q), q0, F)\n"
                  + "\n\t\t ==>DADOS INFORMADOS <==\n" 
                  + "\tΣ   = " + alf + "\n" 
                  + "\tδ   = \n" 
                  + "ESTADO PARTIDA:         Q"
                  + Arrays.toString(estP) 
                  + "\n"
                  + "CARACTER CONSUMIDO: Σ" 
                  + Arrays.toString(le) 
                  + "\n" 
                  + "ESTADO DESTINO:          Q"
                  + Arrays.toString(estD) 
                  + "\n"
                  + "\tF   = "
                  + conjuntoEstadosFinais + "\n" + "" + "**************************************************";
        }
        
        if( cliente == 'B') {
             return "**************************************************\n" 
                   + "\tIMPRIMINDO DADOS INSERIDOS PELO CLIENTE B: \n"
                   + "\t\t\t ==>NOTAÇÃO UTILIZADA <== \n" 
                   + "\tO conjunto dos estados terminais e não terminais: Q = {S1, S2...}\n"
                   + "\tO  estado Inicial: q0\n"
                   + "\tO conjunto dos estados terminais: F\n"
                   + "\tM = (Q, Σ, (δ: Q × Σ → Q), q0, F)\n"
                   + "\n\t\t ==>DADOS INFORMADOS <==\n" 
                   + "\tQ   = " 
                   + est 
                   + "\n"
                   + "\tq0  = " 
                   + estIn 
                   + "\n"
                   + "**************************************************";
        }
		return null;
    }


    
}