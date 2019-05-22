package com.suam.contador;

public class ServiceContaExecucao implements ContaExecucaoInterface 
{
	
	static Integer contaThreads = 1;
	
	public Integer getContaExecucao( ) 
	{
		return contaThreads;
	}

	public void setContaThreads( Integer contaThread )
	{
		contaThreads = contaThread;
	}
	
}