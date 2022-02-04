package com.cursoandroid.organizze.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual(){
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataSting = simpleDateFormat.format(data);
        return dataSting;
    }

    public static String mesAnoDataEscolhida(String data){

        String dataArray[] = data.split("/");
        String mes = dataArray[1];
        String ano = dataArray[2];

        return mes+ano;
    }
}
