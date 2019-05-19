package AFD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;

/* @Anderson Ferreira Canel 
 * 
 *  * Este código é capaz de verificar a aceitação de uma palavra por um dado autômato,
 * caso queira verificar a aceitação  de outras palavras por outros autômatos altere a 
 * regra de produção e os conjuntos de estados e símbolos hardcoded.
 * */

public class AFD_Compiladores_Trabalho
{
    static HashMap<Integer, String> conjuntoDeEstadosMap       = new HashMap<Integer, String>( );
    static HashMap<Integer, String> conjuntoDeEstadosFinaisMap = new HashMap<Integer, String>( );

    static String alfabetoImprime;
    static String conjuntoDeEstadosTerminaisImprime;
    static String estIniImprime;
    static String conjEstTermImprime;

    public static void main( String[ ] args ) 
    {
        /**
         * Trecho responsável por receber o conjunto de simbolos(Alfabeto) e armazenar o
         * mesmo sem os caracteres desnecessários em um array de char.
         */
        String alfabeto  = entrarConjuntoCaracteres_Alfabeto  (          );
        boolean validAlf = verificaConjuntoCaracteres_Alfabeto( alfabeto );

        while ( validAlf )
        {
            alfabeto = entrarConjuntoCaracteres_Alfabeto  (          );
            validAlf = verificaConjuntoCaracteres_Alfabeto( alfabeto );

            if ( validAlf ) 
                JOptionPane.showMessageDialog( null, "Dados invalidos!!!\n" + "Tente novamente.", 
                	                            	 "TENTE NOVAMENTE", JOptionPane.WARNING_MESSAGE );
            else 
                entradaValidada( );
        }
        
        alfabetoImprime = alfabeto;
        
        alfabeto = removeNulos    ( alfabeto );// Removendo {,}
        
        Character[ ]conjuntodeSimbolos_Alfabeto = new Character[ alfabeto.length( ) ];
        int z = 0;
     
        for( char ch : alfabeto.toCharArray( ) )   
        {
            conjuntodeSimbolos_Alfabeto[ z ] = ch;
            z++;
        }
        
        /**
         * Trecho responsável por receber o conjunto de estados (Terminais e não
         * Terminais), e armazenar o mesmo sem os caracteres desnecessários em um array
         * de int, convertendo a posição de um estado fornecido no conjunto em um valor
         * numérico, em ordem crescente correspondente a ordem dos estados fornecidos.
         */
        boolean validEst                                = false;
        String  conjuntoDeEstadosTerminaisEnaoTerminais = entraConjuntoEstado( );
        
        validEst = verificaEst   ( conjuntoDeEstadosTerminaisEnaoTerminais );
        validEst = checkEstEquals( conjuntoDeEstadosTerminaisEnaoTerminais );

        while ( validEst ) 
        {
            conjuntoDeEstadosTerminaisEnaoTerminais = entraConjuntoEstado(                                         );
            validEst                                = verificaEst        ( conjuntoDeEstadosTerminaisEnaoTerminais );

            if( validEst )
            {
                JOptionPane.showMessageDialog( null, "Dados invalidos!!!\n" + "Tente novamente.", 
                		                       "TENTE NOVAMENTE", JOptionPane.WARNING_MESSAGE );
            }
        }
        
        conjuntoDeEstadosTerminaisImprime       = conjuntoDeEstadosTerminaisEnaoTerminais;
        
        conjuntoDeEstadosTerminaisEnaoTerminais = removeNulos( conjuntoDeEstadosTerminaisEnaoTerminais );// Removendo {,}

        int estados              = conjuntoDeEstadosTerminaisEnaoTerminais.length( );
        int[ ] conjuntoDeEstados = new int[ estados ];
        int a = 0;
        
        for ( Character ch : conjuntoDeEstadosTerminaisEnaoTerminais.toCharArray( ) )
        {
            conjuntoDeEstadosMap.put( a, ch.toString( ) );
            conjuntoDeEstados[ a ] = a;
            a++;
        }
        
        /**
         * Trecho responsável por receber entrada do conjunto de regras de
         * transição (Regra de Produção), funciona da seguinte forma: 
         * # ESTADO (LADO ESQUERDO), CONSOME (CENTRO); VAI PARA ESTADO (LADO DIREITO)#
         */
        tutorialTransicao( );
       
        String    delta     = null;
        String[ ] funcDelta = new String[ conjuntodeSimbolos_Alfabeto.length  * 
                                          conjuntoDeEstadosTerminaisEnaoTerminais.length( ) ];

        boolean valFunc = false;
        int     c       = 0;
        
        for1: for (c = 0; c < ( conjuntodeSimbolos_Alfabeto.length *
                                conjuntoDeEstadosTerminaisEnaoTerminais.length( ) ); c++ ) 
        {
            delta = entraFuncaoTransicao( );
 
            try 
            {
                if ( delta.equals( null ) ) 
                {
                }
            }
            catch ( Exception e )
            {
                entradaInvalida();
                JOptionPane.showMessageDialog( null, "Para sair use a tecla 's'!" );
                c--;
                continue for1;
            }

            if ( "S".equalsIgnoreCase( delta ) ) 
            {
                break for1;
            }

            if ( "I".equalsIgnoreCase( delta ) ) 
            {
                tutorialTransicao( );
                c--;
                continue for1;
            }

            if ( "A".equalsIgnoreCase( delta ) ) 
            {
                JOptionPane.showMessageDialog(null,"Transições informadas até o momento:\n" + 
                                                    Arrays.toString( funcDelta ) );
                c--;
                continue for1;
            }

            valFunc = true; //checkFunc(delta, conjuntoDeEstadosTerminaisEnaoTerminais);
            // valFunc2 = checkEqualsFunc(delta, alfArray,
            // estArray,funcDeltaAux); //CORRIGIR VALIDADOR

            if ( valFunc ) 
            {
                funcDelta[ c ] = delta;
            } 
            else
            {
                entradaInvalida( );
                c--;
            }
        }
        //String[ ] funcDelta  = {"A,a;A", "A,b;A", "B,a;A", "B,b;A"};
        
        String[ ] estadoPartidaS  = new String[ funcDelta.length ];
        String[ ] caracConsumidoS = new String[ funcDelta.length ];
        String[ ] estadoDestinoS  = new String[ funcDelta.length ];

        int [ ] estadoPartida = new int [ funcDelta.length ];
        int [ ] estadoDestino = new int [ funcDelta.length ];
        char[ ] le            = new char[ funcDelta.length ];

        for (int i = 0; i < funcDelta.length; i++)
        {
            if (funcDelta[i] == null)
            {
                break;
            }
            String[ ] p1 = funcDelta[ i ].split( ";" );
            String[ ] p2 = p1       [ 0 ].split( "," );
            
            estadoPartidaS [ i ] = p2[ 0 ];
            caracConsumidoS[ i ] = p2[ 1 ];
            estadoDestinoS [ i ] = p1[ 1 ];
        }

        for( int p = 0; p < funcDelta.length; p++ )
        {
            String aux = estadoPartidaS[ p ];
            int    h   = 0;
            
            for ( Character ch : conjuntoDeEstadosTerminaisEnaoTerminais.toCharArray( ) ) 
            {
                for (int j = 0; j < ( conjuntodeSimbolos_Alfabeto.length  *
                                      conjuntoDeEstadosTerminaisEnaoTerminais.length( ) ); j++ )
                {

                    if ( estadoPartidaS[ j ].equals( ch.toString( ) ) ) 
                    {
                         estadoPartida[ j ] = h;
                    }
                }
                
                for( int j = 0; j < ( conjuntodeSimbolos_Alfabeto.length *
                                      conjuntoDeEstadosTerminaisEnaoTerminais.length( ) ); j++ )
                {

                    if( estadoDestinoS[ j ].equals( ch.toString( ) ) )
                    {
                        estadoDestino[ j ] = h;
                    }
                }
                h++;
            }

            aux     = caracConsumidoS[ p ];
            le[ p ] = aux.charAt( 0 );
        }
        
        /**
         * Trecho responsável por receber o estado inicial, identificar posição
         * correspondente no conjunto de estados (Terminais e não terminais) e armazena o mesmo sem os 
        * caracteres desnecessários em uma variável do tipo int com o valor correspondente a sua posição   * no conjunto de estados.
         */
        String estadoIni = "{" + entraEstIN( conjuntoDeEstadosTerminaisEnaoTerminais ) + "}";
        estadoIni = removeNulos( estadoIni );
        estIniImprime = estadoIni;
        
        //String estadoIni = "{A}";
        
        int estadoi = 0;
           //       = 0;
      
        estadoi =  conjuntoDeEstadosTerminaisEnaoTerminais.indexOf( estadoIni );
        
        /**
         * Trecho responsável por receber o conjunto de estados finais (Terminais), e
         * armazenar o mesmo sem os caracteres desnecessários em um array de int,
         * identificando a posição de um estado fornecido no conjunto em um valor
         * numérico, correspondente a posição do mesmo no conjunto de estados (Terminais
         * e não Terminais)e armazenando estes valores em um array de tipo int. 
         */
        
        // ENTRA COM ESTADOS FINAIS
     		boolean validEstFim = false;
     		String conjuntoEstadosTerminais = JOptionPane.showInputDialog(null, "\nEntre com o conjunto dos estados finais F:"
     				+ "\nCada estado deve ser separado por virgula, sem espaços.\nEX: q0,q1,q2 ... e1,e2,e3 ...");

     		String[]  estFin = splitVirgula( conjuntoEstadosTerminais );
     		ArrayList  lista = new ArrayList(Arrays.asList( conjuntoDeEstadosTerminaisEnaoTerminais ) );

     		// --------VALIDAÇÃO DOS ESTADOS FINAIS
     		for (int i = 0; i < estFin.length; i++) 
     		{
     			if (lista.contains(estFin[i]))
     			{
     				validEstFim = verificaEst(conjuntoEstadosTerminais);
     				validEstFim = checkEstEquals(conjuntoEstadosTerminais);
     			}
     			else
     			{
     				validEstFim = true;
     				JOptionPane.showMessageDialog(null, "Estado no inexistente no conjunto de estados");
     			}
     		}

     		while (validEstFim) 
     		{
     			conjuntoEstadosTerminais = JOptionPane.showInputDialog(null, "\nEntre com o conjunto dos estados finais F:"
     					+ "\nCada estado deve ser separado por virgula.\nEX: q0,q1,q2");
     			estFin = splitVirgula(conjuntoEstadosTerminais);

     			for (int i = 0; i < estFin.length; i++)
     			{
     				if (lista.contains(estFin[i]))
     				{
     					validEstFim = verificaEst(conjuntoEstadosTerminais);
     					validEstFim = checkEstEquals(conjuntoEstadosTerminais);
     				}
     				else
     				{
     					validEstFim = true;
     					JOptionPane.showMessageDialog(null, "Estado inexistente no conjunto de estados");
     				}
     			}

     			if (validEstFim) 
     			{
     				JOptionPane.showMessageDialog(null, "TENTE NOVAMENTE!", "TENTE NOVAMENTE", JOptionPane.WARNING_MESSAGE);
     			}
     		}

     		//int qestadosf = 0;
     		//qestadosf = estFin.length;
     		/*int[] estadosf = new int[qestadosf];

     		for (int p = 0; p < qestadosf; p++) {
     			String aux = estFin[p];
     			estadosf[p] = Integer.parseInt(String.valueOf(aux.charAt(1)));
     		}*/


        
        //String conjuntoEstadosTerminais = "{B}";
        conjEstTermImprime = conjuntoEstadosTerminais;
        //conjuntoEstadosTerminais = removeNulos(conjuntoEstadosTerminais);

        int[ ] estadosf = new int[conjuntoEstadosTerminais.length()];
        int b = 0, y = 0;

        for ( Character ch : conjuntoDeEstadosTerminaisEnaoTerminais.toCharArray( ) )
        {
            for ( Character ch1 : conjuntoEstadosTerminais.toCharArray( ) )
            {
                if (conjuntoDeEstadosMap.get(y).equals(ch1.toString( ) ) )
                {
                    estadosf[b] = y;
                    b++;
                    break;
                }
            }
            y++;
        }

        
        
        
        imprimirAutomato( alfabetoImprime, conjuntoDeEstadosTerminaisImprime, estadoPartida, estadoDestino, le,
        				 estIniImprime, conjEstTermImprime );

        /*
         * Entrada realizada pelo usuário, realiza verificação para checar se a
         * palavra pode ser formada com os caracteres do conjunto de símbolo (alfabeto).
         */
        String palavraS;
        boolean flagPal;
        do {
            int teste = 0;
            int w = 0;
            palavraS = JOptionPane.showInputDialog(null,
                    "Entre com a palavra a ser verificada: \nPara conferir os valores dos conjuntos e regras de produção digite 'i'\nPara sair digite s");
            if (palavraS.equalsIgnoreCase( "s" ) ) 
            {
                break;
            }
            if (palavraS.equalsIgnoreCase( "I" ) )
            {
                imprimirAutomato( alfabetoImprime, conjuntoDeEstadosTerminaisImprime, estadoPartida, estadoDestino, le,
                        estadoIni, conjuntoEstadosTerminais);
                
                palavraS = JOptionPane.showInputDialog(null,
                        "Entre com a palavra a ser verificada: \nPara conferir os valores dos cinjuntos e regras de produção digite 'i'\nPara sair digite s");
                
                if( palavraS.equalsIgnoreCase( "s" ) )
                {
                    break;
                }
            }

            //Variável reponsável por receber  a validação da palavra informada pelo automato
            flagPal = VerificaPalavra(palavraS, conjuntodeSimbolos_Alfabeto);

            if ( !flagPal )
            {
            } 
            else 
            {
                char[ ] palavra = palavraS.toCharArray();
                int     estadoa = estadoi;

                for ( int p = 0; p < palavra.length; p++ )
                {
                    for ( int k = 0; k < funcDelta.length; k++ ) 
                    {
                        if ( ( palavra[p] == le[k]) && (estadoPartida[k] == estadoa ) )
                        {
                            estadoa = estadoDestino[k];

                            w++;
                            break;
                        } 
                        else
                        {
                        }
                    }

                    for ( int k = 0; k < conjuntoEstadosTerminais.length(); k++) 
                    {
                        if (estadoa == estadosf[k])
                        {
                            teste = 1;
                        } 
                        else
                        {
                            teste = 0;
                        }
                    }
                }
                if (teste == 1)
                {
                    JOptionPane.showMessageDialog(null, "PALAVRA ACEITA PELO AUTOMATO\n\n");
                    // break;
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "PALAVRA NÃO ACEITA PELO AUTOMATO\n\n");
                    // break;
                }
            }
        }
        
        while (!palavraS.equalsIgnoreCase("s"));
        	JOptionPane.showMessageDialog(null, "Voce finalizou a aplição, obrigado!");
    }

    
    
    
    
    
    
    
    
    
    // ---------------METODOS UTILIZADOS NO CÓDIGO------------
    /**
     * IMPRIME CONJUNTOS E REGRAS DE PRODUÇÃO
     * @param alf
     * @param est
     * @param estadoPartida
     * @param estadoDestino
     * @param le
     * @param estIn
     * @param conjuntoEstadosFinais
     */
    private static void imprimirAutomato(String alf, String est, int[ ] estadoPartida, int[ ] estadoDestino, char[ ] le,
            String estIn, String conjuntoEstadosFinais) {

        String[ ] estP = new String[estadoPartida.length];
        ;
        int b = 0;
        for (int key : estadoPartida) {
            estP[b] = conjuntoDeEstadosMap.get(key);
            b++;
        }

        String[ ] estD = new String[estadoDestino.length];
        ;
        int c = 0;
        for (int key : estadoDestino) {
            estD[c] = conjuntoDeEstadosMap.get(key);
            c++;
        }

        JOptionPane.showMessageDialog(null,
                "**************************************************\n" + "\tIMPRIMINDO DADOS DO AUTOMATO\n"
                        + "\t\t\t ==>NOTAÇÃO UTILIZADA <== \n" + "\tO conjunto de simbolos - alfabeto: Σ \n"
                        + "\tO conjunto dos estados terminais e não terminais: Q = {S1, S2...}\n"
                        + "\tAs transicoes: (δ: Q × Σ → Q)\n" + "\tO  estado Inicial: q0\n"
                        + "\tO conjunto dos estados terminais: F\n" + "\tM = (Q, Σ, (δ: Q × Σ → Q), q0, F)\n"
                        + "\n\t\t ==>DADOS INFORMADOS <==\n" + "\tΣ   = " + alf + "\n" + "" + "\tQ   = " + est + "\n"
                        + "\tδ   = \n" + "ESTADO PARTIDA:         Q" + Arrays.toString(estP) + "\n"
                        + "CARACTER CONSUMIDO: Σ" + Arrays.toString(le) + "\n" + "ESTADO DESTINO:          Q"
                        + Arrays.toString(estD) + "\n" + "" + "\tq0  = " + estIn + "\n" + "" + "\tF   = "
                        + conjuntoEstadosFinais + "\n" + "" + "**************************************************");
    }

    /**
     * VERIFICA SE PALAVRA PERTENCE AO ALFABETO
     * @param palavra
     * @param alf
     * @return
     */
    private static boolean VerificaPalavra(String palavra, Character[ ] alf) {
        int cont = 0;
        for (int x = 0; x < palavra.length(); x++) {
            Character caracPalavra = palavra.charAt(x);
            for (int y = 0; y < alf.length; y++) {
                if (caracPalavra.equals(alf[y])) {
                    cont++;
                }
            }
        }

        if (cont == palavra.length()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null,
                    "A palavra \"" + palavra
                            + "\" contém simbolos não pertencentes ao conjunto de simbolos (alfabeto,Σ= "
                            + alfabetoImprime + ")!",
                    "WARNING", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    /**
     * REMOVE CARACTERES DE FORMATAÇÃO DO CONJUNTO, EX: {,}
     * @param conjunto
     * @return
     */
    public static String removeNulos(String conjunto) {
        String[ ] nulos = { "{", "}", "," };// identificando carateres de formatação do conjunto
        for (String n : nulos) {
            conjunto = conjunto.replace(n, "");
        }
        return conjunto;

    }
    
    //--------------------------------------------
    //--------------------------------------------
    //--------------------------------------------
    //--------------------------------------------
    //--------------------------------------------
    /**
     * JOptionPane para conjunto  ALFABETO
     * @return
     */
    private static String entrarConjuntoCaracteres_Alfabeto( ) 
    {
        String alfabeto = JOptionPane.showInputDialog( null,
                "Entre com o alfabeto Σ:\nCada caracter deve ser separado por virgula, sem espaço.\nEX: a,b,c,d,e ...\nQuantidade máxima permitida = 3\n");
        return alfabeto;
    }
    
    private static boolean verificaConjuntoCaracteres_Alfabeto( String alfabeto ) 
    {
        boolean validador = false;

        // Entrada Vazia
        if ( alfabeto.equals(" ") || alfabeto.length() < 1 || !alfabeto.isEmpty( ) || alfabeto.length() > 3 )
        {
            JOptionPane.showMessageDialog(null, "Tamanho do alfabeto inferior ao permitido!", "WARNING",
                    JOptionPane.WARNING_MESSAGE);
            entradaInvalida();
            return validador = true;
        }

        // Entrada iniciando pela virgula
        if ( alfabeto.charAt(0) == ',' ) 
        {
            JOptionPane.showMessageDialog(null, "Não começe a inserção pela virgula", "WARNING",
                                          JOptionPane.WARNING_MESSAGE);
            entradaInvalida();
        }
        
        // Caracteres iguais, exeção de ','
        int w = 1;
        for1: for ( int k = 0; k < alfabeto.length( ); k = k + 2 )
        {
            if ( k == alfabeto.length() || w == alfabeto.length( ) ) 
            {
                break for1;
            }
            
            if ( alfabeto.charAt(w) != ',' )
            {
                entradaInvalida( );
                return validador = true;
            }
            w = w + 2;
            
            for ( int j = k + 2; j < alfabeto.length(); j = j + 2 ) 
            {
                if ( alfabeto.charAt( k ) == alfabeto.charAt( j ) ) 
                {
                    entradaInvalida( );
                    JOptionPane.showMessageDialog( null, "Você entrou com caracteres iguais no alfabeto!\n"
                            + alfabeto.charAt( k ) + " = " + alfabeto.charAt( j ), "WARNING", JOptionPane.WARNING_MESSAGE );
                    return validador = true;
                }
            }

        }
        JOptionPane.showMessageDialog( null, "ENTRADA VERIFICADA" );
        
        return validador;
    }

    /**
     * JOptionPane para conjunto de estado
     * @return
     */
    private static String entraConjuntoEstado( )
    {
        String estados = JOptionPane.showInputDialog(null,
                "ATENÇÃO AO MODELO DE INSERÇÃO NO CONJUNTO DE ESTADOS\nCada estado deve ser "
                        + "separado por virgula, sem espaço.\n" + "EX: A,B,C ... e1,e2,e3...\nQuantidade máxima permitida = 3");
        return estados;
    }
    
    // ENTRADA DA FUNÇÃO DE TRANSIÇAO
    private static String entraFuncaoTransicao() {

        String delta = JOptionPane.showInputDialog(null,
                "\nEntre com as transiçãos de estado (δ: Q × Σ → Q):\n" + "\nPara ver o tutorial  novamente : 'i'"
                        + "\nPara sair : 's'" + "\nVer entradas anteriores 'a'" + "\n ");
        return delta;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
  
        // ---- METODOS DE IMPRESSÃO DE INFORMAÇOES -

        // ENTRADA INVALIDA
        private static void entradaInvalida() {
            JOptionPane.showMessageDialog(null, "ENTRADA INVALIDA", "WARNING", JOptionPane.WARNING_MESSAGE);
        }

        // ENTRADA VALIDA
        private static void entradaValidada() {
            JOptionPane.showMessageDialog(null, "Entrada VERIFICADA\n");
        }


        // TUTORIAL DE ENTRADA PARA FUNȿO DE TRANSIȃO
        private static void tutorialTransicao() {
            JOptionPane.showMessageDialog(null, "ATENÇÃO AO MODELO PARA ENTRADA DA FUNÇÃO DE TRANSIÇÃO DE ESTADOS\n"
                    + "PASSO 1: Primeiro entre com o estado inicial - EX: q0\n" + "PASSO 2: DIGITE UMA VIRGULA \",\"\n"
                    + "PASSO 3: Entre com o caracter a ser consumido pelo estado inicial - EX: a\n"
                    + "PASSO 4: DIGITE PONTO E VIRGULA. \";\"\n" + "PASSO 5: Entre com o estado de destino - EX: q1\n"
                    + "PASSO 6: APERTE ENTER\n" + "A entrada deve estar da forma do exemplo abaixo\n" + "EX: q0,a;q1",
                    "WARNING", JOptionPane.WARNING_MESSAGE);
        }

               
      
       
        
        // ---------METEDOS VALIDADORES

    

        // VERIFICADOR DE ENTRADA DE DADOS PARA O CONJUNTO DE ESTADOS
        private static boolean verificaEst(String estados) {
            boolean validador = false;
            
            // ESTADO COM TAMANHO INFERIOR AO PERMITIDO, 0.
            if ( estados.length( ) < 1 || estados.length( ) > 3 || estados.equals( " " ) || estados.isEmpty( ) )
            {
                JOptionPane.showMessageDialog( null, "Tamanho do conjunto inferior ao permitido!" );
                entradaInvalida( );
                return validador = true;
            }

            // INSERÇÃO DE ESTADOS NÃO PODE COMEÇAR PELA VIRGULA.
            if ( estados.charAt( 0 ) == ',' )
            {
                JOptionPane.showMessageDialog(null, "Não começe a inserção pela virgula" );
                entradaInvalida( );
                return validador = true;
            }

            // ESTADOS IGUIAS
            if( !checkEstEquals( estados ) ) 
            {
                JOptionPane.showMessageDialog( null, "Entrada VERIFICADA" );
            }
            return validador;// = false;
        }

        // ESTADOS IGUAIS --- CORRIGIR VALIDADOR DE ESTADOS **************************************************
        private static boolean checkEstEquals(String estados) 
        {
            try 
            {
                if ( estados.equals( null ) )
                {
                }
            }
            catch (Exception e) 
            {
                return true;
            }
            
            String[ ] estAux = estados.split( "," );

            for ( int i = 0; i < estAux.length; i++ )
            {
                for ( int j = i + 1; j < estAux.length; j++ ) 
                {
                    if ( estAux[ i ].equals( estAux[ j ] ) )
                    {
                        JOptionPane.showMessageDialog( null, "Existem elementos iguais no conjunto!", "WARNING",
                                                              JOptionPane.WARNING_MESSAGE );
                        i = estAux.length;
                        
                        return true;
                    }
                }
            }

            return false;
        }

        // ENTRA ESTADO INICIAL
    	private static String entraEstIN( String conjuntoDeEstados ) 
    	{
    		boolean validador = true;
    		   		
    		String estadoInicial;
    		do {
    			estadoInicial = JOptionPane.showInputDialog( null, "ESTADO INICIAL:\nEntre com o estado inicial q0: " );

    			if (conjuntoDeEstados.contains( estadoInicial ) ) 
    			{
          			validador = false;
    			}
      			else
    			{
    				validador = true;
    				JOptionPane.showMessageDialog( null, "Estado no inexistente no conjunto de estados." );
    			}

    		} while ( validador );

    		return estadoInicial;
    	}
 
    	// SPLIT PARA SEPARAR ENTRADAS QUE CONTEM VIRGULA
    	private static String[] splitVirgula(String valor) {
    		return valor.split(",");// Divide a String quando ocorrer ","
    	}
        
}
