package pt.ipg.gestortreinos;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DiasSemana {

    private int idDia;
    private String NomeMes;

    private Calendar instanceDate = Calendar.getInstance();
    private String current_Date;



    public int getIdDia() {
        idDia = getAndroidSystemDay();
        return idDia;
    }

    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }

    public String getNomeMes() {
        NomeMes = getAndroidSystemMonth();
        return NomeMes;
    }

    public void setNomeMes(String nomeMes) {
        this.NomeMes = nomeMes;
    }


    public int getAndroidSystemDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");//Dia
        current_Date = sdf.format(instanceDate.getTime());


        int dia = Integer.parseInt(current_Date);
        return dia;
    }

    public String getAndroidSystemMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");// Mês
        current_Date = sdf.format(instanceDate.getTime());


        String mes = current_Date;
        return mes;
    }

    public String getAndroidSystemYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY");// Ano
        current_Date = sdf.format(instanceDate.getTime());

        String ano = current_Date;
        return ano;
    }

}
