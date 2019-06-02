package com.suamSD.service;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.suamSD.AutomatoInterface;
import com.suamSD.Util;

public class AutomatoImplementsService implements AutomatoInterface 
{
	public static  int           contadorFuncTran  = 0;
	public static  Character     identificaUsuario = 'A';
	static         Integer       contaPasso        = 1;
	public  static int           qtdUsers;
	private static StringBuilder sb;
    
	@Override
	public void setQtdUsuario( int i ) throws RemoteException
	{
		 AutomatoImplementsService.qtdUsers = i;
		
	}

	@Override
	public int getQtdUsuario( ) throws RemoteException 
	{
		return AutomatoImplementsService.qtdUsers;
	}
	
	/**
	 * Zera o valor da posição de inserção no conjuno de regras de transição.
	 * Útil no caso de reset da aplicação.
	 */
	public void zeraContadorFuncTran( )
    {
		AutomatoImplementsService.contadorFuncTran = 0;
	}
	
	/**
	 * Retorna o valor da posição de inserção no conjuno de regras de transição.
	 */
	public int getContadorFuncTran( )
    {
		return contadorFuncTran;
	}
    
    /**
     * A cada inserção no conjunto de regras de transição, esse método deve ser invocado.
     * @return
     */
	public static int setContadorFuncTran( )
	{
		return contadorFuncTran ++;
	}
	   
	/**
	 * Retorna para o cliente a identificação de usuários correntes.
	 */
    public Character getIdentificaUsuario( ) 
    {
        return identificaUsuario;
    }

    /**
     * O cliente seta um novo usuário.
     */
    public void setIdentificaUsuario( Character identificador )
    {
        identificaUsuario = identificador;
    }
    
    /**
	 * Retorna para o cliente sobre o passo( Métodos em ordem sequencial ) em que se encontra o programa.
	 */
    public Integer getContaPasso( ) throws RemoteException 
    {
         return contaPasso;
    }

   /**
    * A cada passo dado pelo cliente é incrementado, partindo assim para um próximo passo ( Próximo método em ordem sequencial ).
    * @return
    */
    public void incrementaContaPasso( ) throws RemoteException
    {
        contaPasso++;
        
    }
    
    /**
     * Zera contador para uma nova execução.
     * * Útil no caso de reset da aplicação.
     * @return
     */
     public void zeraContaPasso( ) throws RemoteException
     {
         contaPasso = 1;
         
     }

    //Variáveis para armazenar valores correntes a serem IMPRIMIDOS. 
    private static String alfabetoIMPRIME;
    private static String conjuntoDeEstadosTerminaisIMPRIME;
    private static String estIniIMPRIME;
    private static String conjEstTermIMPRIME;
 
    /*
     * Variáveis para armazenar as entradas inseridas;
     */
    private static Character[ ] conjuntodeSimbolos_Alfabeto;
    private static String       conjuntoDeEstadosTerminaisEnaoTerminais = "";
    private static String       estadoIni                               = "";
    private static String       conjuntoEstadosTerminais                = "";
    private static String   [ ] conjuntoFuncaoDeTransicaoDeEstados;
    
    /*
     * ---------
     */
    
    /*Variáveis para receber valores inseridos no delta
    /*( elemento do conjunto transição de estados ).
     * Exemplo: 
     * 1,a;2
     *  estadoPartidaS  = 1;
     *  caracConsumidoS = a;
     *  estadoDestinoS  = 2;
     */
    private static String [ ] estadoPartidaS  ;
    private static String [ ] caracConsumidoS ;
    private static String [ ] estadoDestinoS  ;
   
    //Variáveis para conversão ( para manipulação )
    //de cada elemento  de cada função( delta ) 
    //de transição de estados
    private static Integer   [ ] estadoPartida ; 
    private static Character [ ] le ;
    private static Integer   [ ] estadoDestino ;
    
    /*
     * ---------
     */
   
    //Variáveis para conversão ( para manipulação ) 
    //de cada elemento do conjunto de estados Finais
    //e estado inicial.
    private static int     estadoi;
    private static int [ ] estadosf;

    
    //No caso de elementos compostos por mais de um carater ( não implementado )
    //private static final Pattern PATTERN_DELTA      = Pattern.compile( "^\\S*,\\S*;\\S$*"       );
    //private static final Pattern PATTERN_ESTADOS    = Pattern.compile( "(^\\S+,\\S+$)|(^\\S+$)" );
    //private static final Pattern PATTERN_ALFBETO    = Pattern.compile( "(^\\S+,\\S+$)|(^\\S+$)" );
    
    private static final Pattern PATTERN_DELTA      = Pattern.compile( "^\\S,\\S;\\S$"                       );
    private static final Pattern PATTERN_ESTADOS    = Pattern.compile( "(^\\S,\\S$)|(^\\S$)|(^\\S,\\S,\\S$)" );
    private static final Pattern PATTERN_ALFBETO    = Pattern.compile( "(^\\S,\\S$)|(^\\S$)|(^\\S,\\S,\\S$)" );
    private static final Pattern PATTERN_ESTADO_INI = Pattern.compile( "(^\\S{1}$)" );
    
    private static HashMap<Integer, String> conjuntoDeEstadosMap   = new HashMap<Integer, String>( );
    private static HashMap<String, String> listaDePalavrasTestadas = new HashMap<String, String>( );
    //private static HashMap<Integer, String> conjuntoDeEstadosFinaisMap = new HashMap<Integer, String>( );
    
    static int quantidadeDeFuncTransPossiveis;
    
    public int getTamanhoCjtFuncTran( )
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
        String  responseValidAlf = Util.OK; 
        
        responseValidAlf = verificaConjuntoCaracteres_Alfabeto( alf );
        
        //Armazenando para imprimir, tratando a formatação
        alfabetoIMPRIME = "{ " + alfabeto + " }";
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

        String [ ] conjuntoDeEstados =  est.split( "," );
        
        responseValidEst = verificaEst( est ); 
        
        conjuntoDeEstadosTerminaisIMPRIME = "{ " + est + " }";
        
        conjuntoDeEstadosTerminaisEnaoTerminais = removeNulos( est );// Removendo {,}
        
        int a = 0;

        //Mapeando posição dos estados
        for ( String estado : conjuntoDeEstados )
        {
            conjuntoDeEstadosMap.put( a, estado );
            a++;
        }
        
         return responseValidEst;
    }
    
   void inicializaVariaveis( ) 
   {
	   quantidadeDeFuncTransPossiveis = conjuntodeSimbolos_Alfabeto.length
                                       * conjuntoDeEstadosTerminaisEnaoTerminais.length( );
	    
	    estadoPartidaS  = new String[ quantidadeDeFuncTransPossiveis ];
	    caracConsumidoS = new String[ quantidadeDeFuncTransPossiveis ];
	    estadoDestinoS  = new String[ quantidadeDeFuncTransPossiveis ];
	
	    estadoPartida = new Integer  [ quantidadeDeFuncTransPossiveis ]; 
	    le            = new Character[ quantidadeDeFuncTransPossiveis ];
	    estadoDestino = new Integer  [ quantidadeDeFuncTransPossiveis ];
	
	    for ( int i = 0; i < quantidadeDeFuncTransPossiveis; i++ ) 
	    {
	    	estadoPartidaS  [ i ] = "*";
	 	    caracConsumidoS [ i ] = "*";
	 	    estadoDestinoS  [ i ] = "*";
	 	                   	  
		}                     
	    
	    conjuntoFuncaoDeTransicaoDeEstados  = new String [ quantidadeDeFuncTransPossiveis ]; 
   }
    
    /**
     * Trecho responsável por receber entrada do conjunto de regras de transição
     * de estados (Regra de Produção), funciona da seguinte forma:
     *  # ESTADO (LADO ESQUERDO), CONSOME (CENTRO); VAI PARA ESTADO (LADO DIREITO)#
     */
    public String setRegra( String func ) 
    {   	
    	if( !PATTERN_DELTA.matcher( func ).matches( ) )
        {
    		if( !"".equals( func ) )
    			return "Formato inválido";
   		}
    	
    	if( contadorFuncTran == 0 )
    	     inicializaVariaveis( );
    	
    	
    	String responseValidaFunc = Util.OK;
    	
    	setContadorFuncTran( );
    
    	conjuntoFuncaoDeTransicaoDeEstados[ contadorFuncTran - 1]  = func;
        
		for ( int i = 0; i < quantidadeDeFuncTransPossiveis; i++ ) 
		{
			if ( conjuntoFuncaoDeTransicaoDeEstados[ i ] == null )
			{
				//break;
				continue;
			}
			
			if ( conjuntoFuncaoDeTransicaoDeEstados[ i ].isEmpty( ) )
			{	
				//break;
				continue;
			}
				
			String[ ] p1 = conjuntoFuncaoDeTransicaoDeEstados[ i ].split( ";" );
			String[ ] p2 = p1[0].split                                  ( "," );

			estadoPartidaS [ i ] = p2[ 0 ];
			caracConsumidoS[ i ] = p2[ 1 ];
			estadoDestinoS [ i ] = p1[ 1 ];
			
			responseValidaFunc = validaDelta( estadoPartidaS [ i ], caracConsumidoS[ i ], estadoDestinoS [ i ] );
			
			if( !Util.OK.equals( responseValidaFunc ) )
			{
				  estadoPartidaS [ i ] = null;
				  caracConsumidoS[ i ] = null;
				  estadoDestinoS [ i ] = null;
			
				  contadorFuncTran =  contadorFuncTran -1;
				  
				  return "Dados inconsistentes: \nChecar delta: " + func + "\nAtenção ao valor:  " + responseValidaFunc;
			}
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
				//le[ p ] = ( Character ) null;
				le[ p ] = '*';
			}	
		}
		if( contadorFuncTran == quantidadeDeFuncTransPossiveis )
			return responseValidaFunc;
    
		return responseValidaFunc ;
    }

    private String validaDelta( String partida, String charac, String destino ) 
    {
    	String response = Util.OK;
    	String testChar = "";
    	
    	if ( !conjuntoDeEstadosTerminaisEnaoTerminais.contains( partida ) )
    		return partida;
    	
    	for ( Character c : conjuntodeSimbolos_Alfabeto ) 
    	{
    		testChar += c.toString( );
		}
    	
    	if ( !testChar.contains( charac ) )
    		return charac;
    	
    	if ( !conjuntoDeEstadosTerminaisEnaoTerminais.contains( destino ) )
    		return destino;
    	
    	return response;
	}

	/**
     * Trecho responsável por receber o estado inicial, identiasficar posição
     * correspondente no conjunto de estados (Terminais e não terminais) e armazena
     * o mesmo sem os caracteres desnecessários em uma variável do tipo int com o
     * valor correspondente a sua posição * no conjunto de estados.
     */
    public String setEstInicial( String ei )
    {
        String responseValidaEstadoI = Util.OK;
 
        responseValidaEstadoI = verificaEstInicial( ei ) ;
 
        if( Util.OK.equals( responseValidaEstadoI ) )
        {
            estadoIni = ei;
            //estadoIni   = removeNulos            ( estadoIni );
            estIniIMPRIME = "{" + estadoIni + "}";
            estadoi = conjuntoDeEstadosTerminaisEnaoTerminais.indexOf( ei.trim( ) );
        }
        
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
        String validaConjuntoEstadosFin = Util.OK;
        
        conjuntoEstadosTerminais = cjtFin;

        if( "".equals( cjtFin ) )
        	return "Entrada Vazia, não permitida!";
        
        if( !PATTERN_ESTADOS.matcher( cjtFin ).matches( ) )
        {
    		if( !"".equals( cjtFin ) )
    			return "ENTRADA INVALIDA\n" + "Formato inválido";
   		}
        
        if( !( seEntradaVaziaOuMaiorquePermitido( cjtFin ).equals( cjtFin ) ) )
        	return seEntradaVaziaOuMaiorquePermitido ( cjtFin );
        
        String[ ] estFin = splitVirgula( conjuntoEstadosTerminais );
        
        for ( String est : estFin ) 
        {
            if ( !( conjuntoDeEstadosTerminaisEnaoTerminais.contains( est ) ) )
            {
            	 return "Estado no inexistente no conjunto de estados." ;
            } 
        }      
        
        conjEstTermIMPRIME       = "{ " + conjuntoEstadosTerminais + " }";
        conjuntoEstadosTerminais = removeNulos( conjuntoEstadosTerminais );
        estadosf                 = new int[ conjuntoEstadosTerminais.length( ) ];
        int  b                   = 0, 
        	 y                   = 0;
                                 
        //***********Sugestão de código Eclipse
       /*
        String [ ] conjuntoDeEstados          = conjuntoDeEstadosTerminaisEnaoTerminais.split( "," );
        String [ ] conjuntoDeEstadosTerminais = conjuntoEstadosTerminais.split               ( "," );
       
        for (int i = 0; i < conjuntoDeEstados.length; i++) {
            for ( String ef : conjuntoDeEstadosTerminais )
            {
                if ( conjuntoDeEstadosMap.get( y ).equals( ef.toString( ) ) )
                {
                    estadosf[ b ] = y;
                    b++;
                    break;
                }
            }
            y++;  
        }
        
        imprimirAutomato( alfabetoIMPRIME, conjuntoDeEstadosTerminaisIMPRIME, estadoPartida, 
                          estadoDestino, le, estIniIMPRIME, conjEstTermIMPRIME );
                          */
        //****************

        /*Meu código*/
        for ( Character ch : conjuntoDeEstadosTerminaisEnaoTerminais.toCharArray( ) ) 
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
        
        return validaConjuntoEstadosFin;
    }

     
    /**
     * Entrada realizada pelo usuário, realiza verificação para checar se a palavra
     * pode ser formada com os caracteres do conjunto de símbolo (alfabeto).
     */
    public String checaAceitacaoPalavra( String palavraInserida ) 
    {
        String palavraS;
        String responseValidaPalavra = Util.OK;
        
        do {
            int teste = 0;
            palavraS = palavraInserida;
            
            // Variável reponsável por receber a validação da palavra informada pelo autmato
            responseValidaPalavra = VerificaPalavra( palavraS, conjuntodeSimbolos_Alfabeto );

            if ( Util.OK.equals( responseValidaPalavra ) ) 
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
                
                for ( Character d : le ) 
                {
                	if( d != null )
                	{
					    c: for ( Character c : palavra ) 
					    {
					    	if( d == c || d == '*' ) 
					    	{
					    		continue c;
					    	}
					    	else
					    	{
					    		listaDePalavrasTestadas.put( palavraS , 
					    				"PALAVRA CONTENDO CARACTERES NÃO INFORMADOS NA REGRA DE PRODUÇÃO.\n"
					    				+ "Caracter: " + c + " não informado  no conjunto de regras." );
					    		
					    		return "PALAVRA CONTENDO CARACTERES NÃO INFORMADOS NA REGRA DE PRODUÇÃO.\n"
					    				+ "Caracter: " + c + " não informado  no conjunto de regras.";
					    	}
					    }
                	}
				}
                
                if ( teste == 1 ) 
                {
                	 listaDePalavrasTestadas.put( palavraS , "PALAVRA ACEITA PELO AUTOMATO" );
                     return  "PALAVRA ACEITA PELO AUTOMATO\n\n";
                    // break;
                }
                else 
                {
                	listaDePalavrasTestadas.put( palavraS , "PALAVRA NÃO ACEITA PELO AUTOMATO" );
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
        String validadorAlf = Util.OK;
        
        if( !PATTERN_ALFBETO.matcher( alfabeto ).matches( ) )
        {
    		if( !"".equals( alfabeto ) )
    			return "ENTRADA INVÁLIDA\n" +"Formato inválido";
   	    }
      
        if( !( seEntradaVaziaOuMaiorquePermitido( alfabeto ).equals( alfabeto ) ) )
        	return seEntradaVaziaOuMaiorquePermitido ( alfabeto );

        // Entrada iniciando pela virgula
        if (alfabeto.charAt(0) == ',')
        {
             return  "ENTRADA INVÁLIDA\n" + "Não começe a inserção pela virgula";
        }

        // Caracteres iguais, exeção de ','
        int w = 1;
        
        for1: for ( int k = 0; k < alfabeto.length( ); k = k + 2 ) 
        {
            if  ( k == alfabeto.length( ) || w == alfabeto.length( ) )
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
        String validador = Util.OK;
        
        if( !PATTERN_ESTADOS.matcher( estados ).matches( ) )
        {
    		if( !"".equals( estados ) )
    			return "ENTRADA INVALIDA\n" + "Formato inválido";
   		}
        
        if( !( seEntradaVaziaOuMaiorquePermitido( estados ).equals( estados ) ) )
        	return seEntradaVaziaOuMaiorquePermitido ( estados );


        // INSERÇÃO DE ESTADOS NÃO PODE COMEÇAR PELA VIRGULA.
        if ( estados.charAt( 0 ) == ',' ) 
        {
            return "ENTRADA INVALIDA\n" + "Não começe a inserção pela virgula";
        }
        
        String[ ] estAux = estados.split( "," );

        for ( int i = 0; i < estAux.length; i++ )
        {
            for ( int j = i + 1; j < estAux.length; j++ ) 
            {
                if ( estAux[ i ].equals(estAux[ j ] ) )
                {
                	//i = estAux.length;
                    return    "ENTRADA INVALIDA\n"
                            + "Existem elementos iguais no conjunto!\n" 
                            +  estAux[ i ] + " = " + estAux[ j ];
                }
            }
        }
        
        return validador;// = false;
    }

    /**
     * VERIFICA ESTADO INICIAL
     * 
     * @param conjuntoDeEstados
     * @return
     */
    private static String verificaEstInicial(  String estIn )
    {        
        String  estadoInicial = Util.OK;//= estIn; 

        if( "".equals( estIn ) )
        	return "Entrada Vazia, não permitida!";
        
        if( !PATTERN_ESTADO_INI.matcher( estIn ).matches( ) )
        {
    			return "Formato inválido";
   		}
                
        if ( !conjuntoDeEstadosTerminaisEnaoTerminais.contains( estIn ) )
        {
            return "Estado no inexistente no conjunto de estados." ;
        } 
        
        if ( estIn.length( ) > 1 )
        {
            return "Existe apenas um estado inicial." ;
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
    	String responseVerificaPal = Util.OK;
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
        	listaDePalavrasTestadas.put( palavra, "A palavra \"" + palavra
                    + "\" contém simbolos não pertencentes ao conjunto de simbolos (alfabeto,Σ= "
                    + alfabetoIMPRIME + ")!");
            return  "A palavra \"" + palavra
                            + "\" contém simbolos não pertencentes ao conjunto de simbolos (alfabeto,Σ= "
                            + alfabetoIMPRIME + ")!";
        }
    }

    public static String seEntradaVaziaOuMaiorquePermitido ( String entrada )
	{
		// Entrada Vazia
		final int TAMANHO_MAX = 3;
		final int TAMANHO_MIN = 1;
		
	   String[] elementos = entrada.split( "," );
		
        if ( " ".equals( entrada ) || elementos.length < TAMANHO_MIN 
        		|| entrada.isEmpty( ) || elementos.length > TAMANHO_MAX 
        		|| "".equals( entrada ) )
        {
            return  "ENTRADA INVÁLIDA\n" + "Tamanho do entrada fora do range permitido!";
        }
		return entrada;
	}

    /*
     ****************************************************************
     * MÉTODOS UTIL'S
     ****************************************************************
     */

    // SPLIT PARA SEPARAR ENTRADAS QUE CONTEM VIRGULA
    private static String[ ] splitVirgula( String valor )
    {
        return valor.split(",");// Divide a String quando ocorrer ","
    }
    
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
    
    
    /*
     ****************************************************************
     * METODOS DE IMPRESSÃO DE INFORMAÇOES
     ****************************************************************
     */
    
    /**
     * Imprime informações correntes do objto autômato 
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
        	if( conjuntoDeEstadosMap.get( key ) !=null ) 
        	{
                estP[ b ] = conjuntoDeEstadosMap.get( key );
                b++;
        	}
        	else
        	{
        		estP[ b ] = "*";
        		b++;
        	}
        }

        String[ ] estD = new String[ estadoDestino.length ];
        
        int c = 0;
        for ( Integer key : estadoDestino )
        {
        	if( conjuntoDeEstadosMap.get( key ) !=null ) 
        	{
                estD[c] = conjuntoDeEstadosMap.get( key );
                c++;
        	}
        	else
        	{
        		estD[ c ] = "*";
        		c++;
        	}
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
        + "\t\tESTADO PARTIDA:     Q\n"
        + "\t\tCARACTER CONSUMIDO: Σ\n" 
        + "\t\tESTADO DESTINO:     Q\n"
        + "\t\t" + Arrays.toString(estP) 
        + "\n"
        + "\t\t" +Arrays.toString(le) 
        + "\n" 
        + "\t\t" +Arrays.toString(estD) 
        + "\n" 
        + "\tq0  = " + estIn + "\n" 
        + "" + "\tF   = "+ conjuntoEstadosFinais + "\n" + ""
        + "**************************************************";
    }

    //METODOS ESPECIFICOS PARA IMPRESSÃO DE DADOS DE 2 USUÁRIOS ALTERNADOS
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
    		return imprimirAutomato( alfabetoIMPRIME, conjuntoDeEstadosTerminaisIMPRIME, estadoPartida, 
    								 estadoDestino, le, estIniIMPRIME, conjEstTermIMPRIME );  
       	}
    	if( c == 'A')
    	{
    		return imprimirAutomatoCliAouB( alfabetoIMPRIME, conjuntoDeEstadosTerminaisIMPRIME, estadoPartida, 
    										estadoDestino, le, estIniIMPRIME, conjEstTermIMPRIME, 'A' );  
       	}
    	if( c == 'B')
    	{
    		return imprimirAutomatoCliAouB( alfabetoIMPRIME, conjuntoDeEstadosTerminaisIMPRIME, estadoPartida, 
    										estadoDestino, le, estIniIMPRIME, conjEstTermIMPRIME, 'B' );  
       	}
    	if( c == 'P')
    	{
    		return imprimirAutomatoCliAouB( alfabetoIMPRIME, conjuntoDeEstadosTerminaisIMPRIME, estadoPartida, 
    										estadoDestino, le, estIniIMPRIME, conjEstTermIMPRIME, 'P' );  
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
        	if( conjuntoDeEstadosMap.get( key ) !=null ) 
        	{
                estP[ b ] = conjuntoDeEstadosMap.get( key );
                b++;
        	}
        	else
        	{
        		estP[ b ] = "*";
        		b++;
        	}
        }

        String[ ] estD = new String[ estadoDestino.length ];
        ;
        int c = 0;
        for ( Integer key : estadoDestino )
        {
        	if( conjuntoDeEstadosMap.get( key ) !=null ) 
        	{
                estD[c] = conjuntoDeEstadosMap.get( key );
                c++;
        	}
        	else
        	{
        		estD[ c ] = "*";
        		c++;
        	}
        }
        
        
        String[] listaPalavras = listaDePalavrasTestadas.toString( ).split(",");
        
    	sb = new StringBuilder( );
    	
    	for (String string : listaPalavras) 
    	{
    		sb.append( string +"\n" );
		}
          
        if( cliente == 'A') 
        {
             return "**************************************************\n" 
                  + "\tIMPRIMINDO DADOS INSERIDOS PELO CLIENTE A:\n"
                  + "\t\t\t ==>NOTAÇÃO UTILIZADA <== \n" 
                  + "\tO conjunto de simbolos - alfabeto: Σ \n" 
                  + "\tAs transicoes: (δ: Q × Σ → Q)\n" 
                  + "\tO conjunto dos estados terminais: F\n"
                  //+ "\tM = (Q, Σ, (δ: Q × Σ → Q), q0, F)\n"
                  + "\n\t\t ==>DADOS INFORMADOS <==\n" 
                  + "\tΣ   = " + alf + "\n" 
                  + "\tδ   = \n" 
                  + "\t\tESTADO PARTIDA:     Q\n"
                  + "\t\tCARACTER CONSUMIDO: Σ\n" 
                  + "\t\tESTADO DESTINO:     Q\n"
                  + "\t\t" + Arrays.toString(estP) 
                  + "\n"
                  + "\t\t" +Arrays.toString(le) 
                  + "\n" 
                  + "\t\t" +Arrays.toString(estD) 
                  + "\n"
                  + "\tF   = "
                  + conjuntoEstadosFinais + "\n" + 
                  "**************************************************"+
                  "\n\nPalavras Testadas:\n\n"
                  +sb;
        }
        
        if( cliente == 'B')
        {
             return "**************************************************\n" 
                   + "\tIMPRIMINDO DADOS INSERIDOS PELO CLIENTE B: \n"
                   + "\t\t\t ==>NOTAÇÃO UTILIZADA <== \n" 
                   + "\tO conjunto dos estados terminais e não terminais: Q = {S1, S2...}\n"
                   + "\tO  estado Inicial: q0\n"
                   + "\tO conjunto dos estados terminais: F\n"
                   // + "\tM = (Q, Σ, (δ: Q × Σ → Q), q0, F)\n"
                   + "\n\t\t ==>DADOS INFORMADOS <==\n" 
                   + "\tQ   = " 
                   + est 
                   + "\n"
                   + "\tq0  = " 
                   + estIn 
                   + "\n"
                   + "**************************************************"
                   +"\n\nPalavras Testadas:\n\n"
                   +sb;
        }
        
        if( cliente == 'P') 
        {
        	return  "**************************************************\n" 
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
        	        + "\t\tESTADO PARTIDA:     Q\n"
        	        + "\t\tCARACTER CONSUMIDO: Σ\n" 
        	        + "\t\tESTADO DESTINO:     Q\n"
        	        + "\t\t" + Arrays.toString(estP) 
        	        + "\n"
        	        + "\t\t" +Arrays.toString(le) 
        	        + "\n" 
        	        + "\t\t" +Arrays.toString(estD) 
                    + "\n"
        	        + "\tq0  = " + estIn + "\n" 
        	        + "" + "\tF   = "+ conjuntoEstadosFinais + "\n" + ""
        	        + "**************************************************"
        	        +"\n\nPalavras Testadas:\n\n"
                    +sb;
       }
		return null;
    }
}