package pt.ipg.gestortreinos;

import java.util.Date;

public class Treinos {
  /*  Treino
		-Duração
		-Peso usado
		-Exercico feito
		-Dia do treino
		-Repetições

    Dias de semana:
            -Grupo muscular(Costas,Pernas,Peito,Braços)
			-Tipo de dia(Treino ou Descanso)
			-Dia

  Classe Treinos, vai ser referente à atividade onde o utilizador vai poder escrever o seu treino, num certo dia

Main activity vai ser a primeira atividade que o utilizador vê.
* A primeira coisa que o utilizador vai ver,vai ser os seus treinos,organizados por data de criação(O mais recente é o primeiro)
*
*
* na main activity o user pode criar,alterar nome e eliminar o treino.
* Isto vai ser implementado por botões que estarão no menu
*
* */
    private Date data;//DD(1 a 31)|MM(0 a 11)|YYYY(0 a 8099) year + 1900
    private String exercicio;
    private int repeticoes;
    private int series;
    private int total_Reps = 0;

    public Treinos(Date data, String exercicio, int reps, int series, int total) {
        this.data = data;
        this.exercicio = exercicio;
        this.repeticoes = reps;
        this.series = series;
        this.total_Reps = total;
    }

    public int getRepeticoes(int repeticoes,int series){
        return total_Reps = repeticoes*series; //Formula:int total=int Rep da série 1 + int Rep da série 2+...int Rep da série n
    }


}
