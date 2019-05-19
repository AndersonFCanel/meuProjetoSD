package AFD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;

/* @Anderson Ferreira Canel 
 * 
 *  * Este código é capaz de verificar a aceitação de 
 *    uma palavra por um autômato informado dinâmicamente.
 * */

public class AFD
{
    private static HashMap<Integer, String> conjuntoDeEstadosMap       = new HashMap<Integer, String>( );
            static HashMap<Integer, String> conjuntoDeEstadosFinaisMap = new HashMap<Integer, String>( );

    private static String alfabetoImprime;
    private static String conjuntoDeEstadosTerminaisImprime;
    private static String estIniImprime;
    private static String conjEstTermImprime;
    
    private static String       conjuntoDeEstadosTerminaisEnaoTerminais;
    private static Character[ ] conjuntodeSimbolos_Alfabeto;
    private static String       estadoIni;
    public  static String       conjuntoEstadosTerminais; 
    
    private  static int       estadoi;
    private  static int   [ ] estadosf;
    private  static int   [ ] estadoPartida ;
    private  static int   [ ] estadoDestino ;
    private  static char  [ ] le            ;
    private  static String[ ] conjuntoFuncaoDeTransicaoDeEstados;
      
    
    /**
        * Trecho responsável por receber o conjunto de simbolos(Alfabeto) e armazenar o
        * mesmo sem os caracteres desnecessários em um array de char.
        * @return 
        */
        public void setAlfabeto( ) 
        {
        	String alfabeto  = entrarConjuntoCaracteres_Alfabeto  (          );
            boolean validAlf = verificaConjuntoCaracteres_Alfabeto( alfabeto );

            while ( validAlf )
            {
                alfabeto = entrarConjuntoCaracteres_Alfabeto  (          );
                validAlf = verificaConjuntoCaracteres_Alfabeto( alfabeto );

                if ( !validAlf )  
                    entradaValidada( );
            }
            
            alfabetoImprime = alfabeto;
            
            alfabeto = removeNulos    ( alfabeto );// Removendo {,}
            
            //Poderia ser um array de object, caso cada elemento do conjunto fosse um conjunto de simbolos
            conjuntodeSimbolos_Alfabeto = new Character[ alfabeto.length( ) ];
            int z = 0;
         
            for( char ch : alfabeto.toCharArray( ) )   
            {
                conjuntodeSimbolos_Alfabeto[ z ] = ch;
                z++;
            }
        }
        
       /**
        * Trecho responsável por receber o conjunto de estados (Terminais e não
        * Terminais), e armazenar o mesmo sem os caracteres desnecessários em um array
        * de int, convertendo a posição de um estado fornecido no conjunto em um valor
        * numérico, em ordem crescente correspondente a ordem dos estados fornecidos.
        * @return 
        */
       public void setEstados( )
        {
            conjuntoDeEstadosTerminaisEnaoTerminais = entraConjuntoEstado( );
            
            boolean validEst = verificaEst( conjuntoDeEstadosTerminaisEnaoTerminais );

            while ( validEst ) 
            {
                conjuntoDeEstadosTerminaisEnaoTerminais = entraConjuntoEstado(                                         );
                validEst                                = verificaEst        ( conjuntoDeEstadosTerminaisEnaoTerminais );
                
                if( !validEst )
                	 entradaValidada( );
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
            
        }
        
      /**
       * Trecho responsável por receber entrada do conjunto de regras de
       * transição (Regra de Produção), funciona da seguinte forma: 
       * # ESTADO (LADO ESQUERDO), CONSOME (CENTRO); VAI PARA ESTADO (LADO DIREITO)#
       */
       public void setRegra( )
       {
    	   tutorialTransicao( );
           
           
           String[ ] conjuntoFuncaoDeTransicaoDeEstados = new String[ conjuntodeSimbolos_Alfabeto.length  * 
                                                                      conjuntoDeEstadosTerminaisEnaoTerminais.length( ) ];

           conjuntoFuncaoDeTransicaoDeEstados = montarConjuntoFuncTran( conjuntodeSimbolos_Alfabeto.length  * 
                                                                        conjuntoDeEstadosTerminaisEnaoTerminais.length( ) );
           
           String [ ] estadoPartidaS  = new String[ conjuntoFuncaoDeTransicaoDeEstados.length ];
           String [ ] caracConsumidoS = new String[ conjuntoFuncaoDeTransicaoDeEstados.length ];
           String [ ] estadoDestinoS  = new String[ conjuntoFuncaoDeTransicaoDeEstados.length ];

           estadoPartida = new int [ conjuntoFuncaoDeTransicaoDeEstados.length ];
           estadoDestino = new int [ conjuntoFuncaoDeTransicaoDeEstados.length ];
           le            = new char[ conjuntoFuncaoDeTransicaoDeEstados.length ];

           //
           for (int i = 0; i < conjuntoFuncaoDeTransicaoDeEstados.length; i++)
           {
               if (conjuntoFuncaoDeTransicaoDeEstados[i] == null)
               {
                   break;
               }
               String[ ] p1 = conjuntoFuncaoDeTransicaoDeEstados[ i ].split( ";" );
               String[ ] p2 = p1                                [ 0 ].split( "," );
               
               estadoPartidaS [ i ] = p2[ 0 ];
               caracConsumidoS[ i ] = p2[ 1 ];
               estadoDestinoS [ i ] = p1[ 1 ];
           }

           for( int p = 0; p < conjuntoFuncaoDeTransicaoDeEstados.length; p++ )
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
              
       }
        
        /**
         * Trecho responsável por receber o estado inicial, identificar posição
         * correspondente no conjunto de estados (Terminais e não terminais) e armazena o mesmo sem os 
        * caracteres desnecessários em uma variável do tipo int com o valor correspondente a sua posição   * no conjunto de estados.
         */
       public void setEstIni( )
       {
    	   estadoIni = "{" + entraEstIN( conjuntoDeEstadosTerminaisEnaoTerminais ) + "}";
           estadoIni = removeNulos( estadoIni );
           estIniImprime = estadoIni;
           
           estadoi = 0;
          
           estadoi =  conjuntoDeEstadosTerminaisEnaoTerminais.indexOf( estadoIni );        
       }
       
       /**
         * Trecho responsável por receber o conjunto de estados finais (Terminais), e
         * armazenar o mesmo sem os caracteres desnecessários em um array de int,
         * identificando a posição de um estado fornecido no conjunto em um valor
         * numérico, correspondente a posição do mesmo no conjunto de estados (Terminais
         * e não Terminais)e armazenando estes valores em um array de tipo int. 
     * @return 
         */
        
        public void setEstadosFinais( ) {
        	 // ENTRA COM ESTADOS FINAIS
     	  boolean validEstFim = false;
     	  conjuntoEstadosTerminais = JOptionPane.showInputDialog(null, "\nEntre com o conjunto dos estados finais F:"
     				+ "\nCada estado deve ser separado por virgula, sem espaços.\nEX: q0,q1,q2 ... e1,e2,e3 ...");

     		String[]  estFin = splitVirgula( conjuntoEstadosTerminais );
     		ArrayList  lista = new ArrayList(Arrays.asList( conjuntoDeEstadosTerminaisEnaoTerminais ) );

     		// --------VALIDAÇÃO DOS ESTADOS FINAIS
     		for (int i = 0; i < estFin.length; i++) 
     		{
     			if (lista.contains(estFin[i]))
     			{
     				validEstFim = verificaEst(conjuntoEstadosTerminais);
     				validEstFim = verificaConjuntoEstados(conjuntoEstadosTerminais);
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
     					validEstFim = verificaConjuntoEstados(conjuntoEstadosTerminais);
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

        estadosf = new int[conjuntoEstadosTerminais.length()];
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
            
            imprimirAutomato( alfabetoImprime, conjuntoDeEstadosTerminaisImprime,
            		estadoPartida, estadoDestino, le, estIniImprime, conjEstTermImprime );
        }
        }
        
        public void checaPalavra( ) 
        {
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
                         for ( int k = 0; k < conjuntoFuncaoDeTransicaoDeEstados.length; k++ ) 
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
        
        

        /*
         * Entrada realizada pelo usuário, realiza verificação para checar se a
         * palavra pode ser formada com os caracteres do conjunto de símbolo (alfabeto).
         */
       

    
    
    
    
    
    
    
    
    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    //%---------------METODOS UTILIZADOS NO CÓDIGO------------%
    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    
    /*
     ****************************************************************
     *MÉTODOS PARA ENTRADA DE DADOS 
     ****************************************************************
     */
    private static String entrarConjuntoCaracteres_Alfabeto( ) 
    {
        String alfabeto = JOptionPane.showInputDialog( null,
                "Entre com o alfabeto Σ:\nCada caracter deve ser separado por virgula, "
                + "sem espaço.\nEX: a,b,c,d,e ...\n"
                + "Lembre-se, tratando de conjunto não são permitidos elementos duplicados."
                + "\nTamanho permitido:\n"
                + "       Mínimo = 1(um)   elemento. \n"
                + "       Máximo = 3(três) elementos.\n");
        return alfabeto;
    }

    /**
     * JOptionPane para conjunto ESTADOS
     * @return
     */
    private static String entraConjuntoEstado( )
    {
        String estados = JOptionPane.showInputDialog(null,
                "ATENÇÃO AO MODELO DE INSERÇÃO NO CONJUNTO DE ESTADOS\nCada estado deve ser "
                        + "separado por virgula, sem espaço.\n" + "EX: A,B,C ... e1,e2,e3...\n"
        		+ "Lembre-se, tratando de conjunto não são permitidos elementos duplicados."
                + "\nTamanho permitido:\n"
                + "       Mínimo = 1(um)   elemento. \n"
                + "       Máximo = 3(três) elementos.\n");
        return estados;
    }
    
    // ENTRADA DA FUNÇÃO DE TRANSIÇAO
    private static String entraFuncaoTransicao( ) 
    {

        String delta = JOptionPane.showInputDialog(null,
                "\nEntre com as transiçãos de estado (δ: Q × Σ → Q):\n" + "\nPara ver o tutorial  novamente : 'i'"
                        + "\nPara sair : 's'" + "\nVer entradas anteriores 'a'" + "\n ");
        return delta;
    }

    
    /*
     ****************************************************************
     *  METODOS DE IMPRESSÃO DE INFORMAÇOES
     ****************************************************************
     */
    
    // ENTRADA INVALIDA
    private static void entradaInvalida ()
    {
        JOptionPane.showMessageDialog(null, "ENTRADA INVALIDA", "WARNING", JOptionPane.WARNING_MESSAGE);
    }
    
    // ENTRADA VALIDA
    private static void entradaValidada( ) {
        JOptionPane.showMessageDialog(null, "Entrada VERIFICADA\n");
    }
    
    
    // TUTORIAL DE ENTRADA PARA FUNȿO DE TRANSIȃO
    private static void tutorialTransicao( ) 
    {
        JOptionPane.showMessageDialog(null, 
        		"       * Conjunto de regras de transição (Regra de Produção), funciona da seguinte forma: *\n" + 
        		"         {* # ESTADO (LADO ESQUERDO), CONSOME (CENTRO); VAI PARA ESTADO (LADO DIREITO)# *}"
        		+ "\n\nATENÇÃO AOS PASSOS PARA ENTRADA DA FUNÇÃO DE TRANSIÇÃO DE ESTADOS\n"
                + "PASSO 1: Primeiro entre com o estado inicial - EX: q0\n" 
        		+ "PASSO 2: DIGITE UMA VIRGULA \",\"\n"
                + "PASSO 3: Entre com o caracter a ser consumido pelo estado inicial - EX: a\n"
                + "PASSO 4: DIGITE PONTO E VIRGULA. \";\"\n" + "PASSO 5: Entre com o estado de destino - EX: q1\n"
                + "PASSO 6: APERTE ENTER\n" + "A entrada pode ser verificada com a inserção da letra i + enter\n"
                		+ "e a mesma deve estar da forma do exemplo abaixo:\n"
                + "EX: q0,a;q1", "WARNING", JOptionPane.WARNING_MESSAGE);
    }
    
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
    private static void imprimirAutomato( String alf, String est, int[ ] estadoPartida, int[ ] estadoDestino, char[ ] le,
            String estIn, String conjuntoEstadosFinais) 
    {
    
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
    
    
    
    
    /* 
     * ***************************************************************
     * METODOS VALIDADORES
     * ***************************************************************
    */
    
    /**
     * 
     * @param alfabeto
     * @return
     */
    private static boolean verificaConjuntoCaracteres_Alfabeto( String alfabeto ) 
    {
        boolean validador = false;
    
        // Entrada Vazia
        if ( alfabeto.equals(" ") || alfabeto.length( ) < 1 || alfabeto.isEmpty( ) || alfabeto.length( ) > 5 )//5 porque conta as duas virgulas
        {
            JOptionPane.showMessageDialog(null, "ENTRADA INVALIDA\n"+"Tamanho do alfabeto fora do range permitido!", "WARNING",
                    JOptionPane.WARNING_MESSAGE);
            return validador = true;
        }
    
        // Entrada iniciando pela virgula
        if ( alfabeto.charAt(0) == ',' ) 
        {
            JOptionPane.showMessageDialog(null, "ENTRADA INVALIDA\n"+"Não começe a inserção pela virgula", "WARNING",
                                          JOptionPane.WARNING_MESSAGE);
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
                    JOptionPane.showMessageDialog( null, "ENTRADA INVALIDA\n"+"Você entrou com caracteres iguais no alfabeto!\n"
                            + alfabeto.charAt( k ) + " = " + alfabeto.charAt( j ), "WARNING", JOptionPane.WARNING_MESSAGE );
                    return validador = true;
                }
            }
    
        }
        JOptionPane.showMessageDialog( null, "ENTRADA VERIFICADA" );
        
        return validador;
    }
    
     /**
      * VERIFICADOR DE ENTRADA DE DADOS PARA O CONJUNTO DE ESTADOS
      * @param estados
      * @return
      */
    private static boolean verificaEst( String estados ) 
    {
        boolean validador = false;
        
        // ESTADO COM TAMANHO INFERIOR AO PERMITIDO, = 0 ou >5.
        if ( estados.length( ) < 1 || estados.length( ) > 5 || estados.equals( " " ) || estados.isEmpty( ) )//5 porque conta as duas virgulas
        {
            JOptionPane.showMessageDialog( null, "ENTRADA INVALIDA\n"+"Tamanho do conjunto fora do range permitido!" );
            return validador = true;
        }
    
        // INSERÇÃO DE ESTADOS NÃO PODE COMEÇAR PELA VIRGULA.
        if ( estados.charAt( 0 ) == ',' )
        {
            JOptionPane.showMessageDialog(null,"ENTRADA INVALIDA\n"+ "Não começe a inserção pela virgula" );
            return validador = true;
        }
    
        // ESTADOS IGUIAS
        if( !verificaConjuntoEstados( estados ) ) 
        {
            JOptionPane.showMessageDialog( null, "Entrada VERIFICADA" );
        }
        else
        {
        	return validador = true;
        }
        
        return validador;// = false;
    }
    
    // ===>>>#####ESTADOS IGUAIS --- CORRIGIR VALIDADOR DE ESTADOS 
    private static boolean verificaConjuntoEstados(String estados) 
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
                    JOptionPane.showMessageDialog( null,  "ENTRADA INVALIDA\n"+ "Existem elementos iguais no conjunto!", "WARNING",
                                                          JOptionPane.WARNING_MESSAGE );
                    i = estAux.length;
                    
                    return true;
                }
            }
        }
    
        return false;
    }
    
   
    /**
     * ENTRA ESTADO INICIAL
     * @param conjuntoDeEstados
     * @return
     */
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
    
    
    
    /*
     ****************************************************************
     * MÉTODOS UTIL'S
     ****************************************************************
     */ 
    
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
   
    // SPLIT PARA SEPARAR ENTRADAS QUE CONTEM VIRGULA
    private static String[] splitVirgula( String valor )
    {
    	return valor.split(",");// Divide a String quando ocorrer ","
    }
        
    
    
    
    private static String[] montarConjuntoFuncTran( int i ) 
    {
    	String    funcaoDeTransicao                  = null;
    	conjuntoFuncaoDeTransicaoDeEstados = new String[ i ];
    	boolean valFunc = false;
        
        for1: for (int c = 0; c < i; c++ ) 
        {
            funcaoDeTransicao = entraFuncaoTransicao( );
 
            try 
            {
                if ( funcaoDeTransicao.equals( null ) ) 
                {
                }
            }
            catch ( Exception e )
            {
                JOptionPane.showMessageDialog( null, "ENTRADA INVALIDA\n"+"Para sair use a tecla 's'!" );
                c--;
                continue for1;
            }

            if ( "S".equalsIgnoreCase( funcaoDeTransicao ) ) 
            {
                break for1;
            }

            if ( "I".equalsIgnoreCase( funcaoDeTransicao ) ) 
            {
                tutorialTransicao( );
                c--;
                continue for1;
            }

            if ( "A".equalsIgnoreCase( funcaoDeTransicao ) ) 
            {
                JOptionPane.showMessageDialog(null,"Transições informadas até o momento:\n" + 
                                                    Arrays.toString( conjuntoFuncaoDeTransicaoDeEstados ) );
                c--;
                continue for1;
            }

            valFunc = true; //checkFunc(delta, conjuntoDeEstadosTerminaisEnaoTerminais);
            // valFunc2 = checkEqualsFunc(delta, alfArray,
            // estArray,funcDeltaAux); //CORRIGIR VALIDADOR

            if ( valFunc ) 
            {
                conjuntoFuncaoDeTransicaoDeEstados[ c ] = funcaoDeTransicao;
            } 
            else
            {
                entradaInvalida( );
                c--;
            }
        }
		return conjuntoFuncaoDeTransicaoDeEstados;
    }
}
