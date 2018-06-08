package pt.ipg.gestortreinos;

import android.content.ContentValues;

public class Treinos {
    /*  Treino

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


    private int idTreino;
    private String exercicio;
    private int repeticoes;
    private int series;
    private int pesoUsado;
    private int total_Reps = 0;
/*
    public Treinos(int idTreino, String exercicio, int reps, int series) {
     // public Treinos(){
        this.idTreino = idTreino;
        this.exercicio = exercicio;
        this.repeticoes = repeticoes;
        this.series = series;
    }*/

    public int getPesoUsado() {
        return pesoUsado;
    }

    public int setPesoUsado(int pesoUsado) {
        return this.pesoUsado = pesoUsado;
    }

    public int getId() {

        return idTreino;
    }

    public int setId(int idTreino) {

        return this.idTreino = idTreino;
    }

    public String getExercicio() {

        return exercicio;
    }

    public String setExercicio(String exercicio) {
       return this.exercicio = exercicio;
    }

    public int getRepeticoes() {

        return repeticoes;
    }

    public int setRepeticoes(int repeticoes) {
        return this.repeticoes = repeticoes;
    }

    public int getSeries()
    {
        return series;
    }

    public int setSeries(int series) {
        return this.series = series;
    }

    public int getTotal_Reps(int repeticoes,int series){
        return total_Reps = setRepeticoes(repeticoes)*setSeries(series); //Formula:int total=int Rep da série 1 + int Rep da série 2+...int Rep da série n
    }


}
