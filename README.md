#Validador de Autômatos Finitos Determinísticos distribuído usando com Java/RMI

AFD :
Σ é um conjunto finito de símbolos, chamado de alfabeto do autômato. 
δ é a função de transição, isto é, δ: Q x Σ → Q. 
q0 é o estado inicial, isto é, o estado do autômato antes de qualquer entrada ser processada, 
onde q0 ∈ Q. 
F é um conjunto de estados de Q (isto é, F ⊆ Q) chamado de estados de aceitação.

Projeto em desenvolvido para disciplina de Sistemas Distribuídos. 
O projeto consiste em um validador de AFD (autômato finito determinístico), opera com um servidor e dois clientes em paralelo.
