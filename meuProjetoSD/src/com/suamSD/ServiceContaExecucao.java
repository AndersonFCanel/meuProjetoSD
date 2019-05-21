package com.suamSD;

public class ServiceContaExecucao implements ContaExecucaoInterface 
{
	
	static Integer contaThreads;
	
	public Integer getContaExecucao( ) 
	{
		return contaThreads;
	}

	public void setContaThreads( Integer contaThread )
	{
		contaThreads = contaThread;
	}
	
}