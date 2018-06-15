package pt.ipg.gestortreinos;

import java.util.Calendar;
import java.util.Date;

public class DiasSemana {

    private int idDia;
    private String nome_dia;
    private int current_day = 0;


    public int getIdDia() {
        idDia = getAndroidSystemDay();
        return idDia;
    }

    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }

    public String getNome_dia() {
        return nome_dia;
    }

    public void setNome_dia(String nome_dia) {
        this.nome_dia = nome_dia;
    }


    public int getAndroidSystemDay() {
        Date current_date = null;

        int sabado = Calendar.SUNDAY;//1
        int segunda = Calendar.MONDAY;//2
        int terca = Calendar.TUESDAY;//3
        int quarta = Calendar.WEDNESDAY;//4
        int quinta = Calendar.THURSDAY;//5
        int sexta = Calendar.FRIDAY;//6
        int domingo = Calendar.SATURDAY;//7

        switch (current_date.getDay()) {
            case 1:
                current_day = sabado;
                break;
            case 2:
                current_day = segunda;
                break;
            case 3:
                current_day = terca;
                break;
            case 4:
                current_day = quarta;
                break;
            case 5:
                current_day = quinta;
                break;
            case 6:
                current_day = sexta;
                break;
            case 7:
                current_day = domingo;
                break;
        }

        return current_day;
    }
}
