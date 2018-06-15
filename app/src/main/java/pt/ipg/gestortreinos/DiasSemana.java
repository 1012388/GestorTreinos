package pt.ipg.gestortreinos;

import java.util.Calendar;
import java.util.Date;

public class DiasSemana {

    private int idDia;
    private int current_day = 0;

    private String nome_dia;
    private String nameDay;

    private int sabado = Calendar.SUNDAY;//1
    private int segunda = Calendar.MONDAY;//2
    private int terca = Calendar.TUESDAY;//3
    private int quarta = Calendar.WEDNESDAY;//4
    private int quinta = Calendar.THURSDAY;//5
    private int sexta = Calendar.FRIDAY;//6
    private int domingo = Calendar.SATURDAY;//7
    private Date current_date = null;


    public int getIdDia() {
        idDia = getAndroidSystemDay();
        return idDia;
    }

    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }

    public String getNome_dia() {
        nome_dia = getAndroidSystemNameDay();
        return nome_dia;
    }

    public void setNome_dia(String nome_dia) {
        this.nome_dia = nome_dia;
    }


    public int getAndroidSystemDay() {
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

    public String getAndroidSystemNameDay() {


        switch (current_date.getDay()) {
            case 1:
                nameDay = "Sábado";
                break;
            case 2:
                nameDay = "Segunda";
                break;
            case 3:
                nameDay = "Terça";
                break;
            case 4:
                nameDay = "Quarta";
                break;
            case 5:
                nameDay = "Quinta";
                break;
            case 6:
                nameDay = "Sexta";
                break;
            case 7:
                nameDay = "Domingo";
                break;
        }

        return nameDay;
    }

}
