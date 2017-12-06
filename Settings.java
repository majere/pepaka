package sample;//raist on 03.12.2017


import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Settings {

    @Getter @Setter String token, ip, bdname, login, pswd;

    static Settings chkCfg(){

        Settings cfg = new Settings();
        String pathname = "settings.cfg";
        File file = new File(pathname);
        if(file.exists()){

            System.out.println("Чтение настроек");
            ArrayList<String> arrayList = new ArrayList<>();

            //чтение фала настроек и помещение строк в массив
            try {
                Files.lines(Paths.get(pathname), StandardCharsets.UTF_8).forEach(line -> arrayList.add(line));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Не удалось прочесть файл настроек");
            }

            for(int i = 0; i < arrayList.size(); i++){

                int pos = arrayList.get(i).indexOf('=');
                String temp = arrayList.get(i).substring(0, pos);
                String value = arrayList.get(i).substring(pos + 1);
                System.out.println(temp + " = " + value);
                switch(temp){
                    case "token": cfg.setToken(value);
                        break;
                    case "ip": cfg.setIp(value);
                        break;
                    case "bdname": cfg.setBdname(value);
                        break;
                    case "login": cfg.setLogin(value);
                        break;
                    case "pswd": cfg.setPswd(value);
                }

                switch (arrayList.get(i)){

                }
            }

        }else{

            //опрос пользователя
            System.out.println("создание файла настроек");
            Scanner input = new Scanner(System.in);
            System.out.println("Введите токен телеграм-бота:");
            cfg.setToken(input.nextLine());
            System.out.println("Введите адрес PostgreSQL севера:");
            cfg.setIp(input.nextLine());
            System.out.println("Введите имя базы данных бота:");
            cfg.setBdname(input.nextLine());
            System.out.println("Введите логин для доступа к БД:");
            cfg.setLogin(input.nextLine());
            System.out.println("Введите пароль для доступа к БД:");
            cfg.setPswd(input.nextLine());

            //создание фала настроек
            try {
                PrintWriter pw = new PrintWriter(file);
                pw.println("token=" + cfg.getToken());
                pw.println("ip=" + cfg.getIp());
                pw.println("bdname=" + cfg.getBdname());
                pw.println("login=" + cfg.getLogin());
                pw.println("pswd=" + cfg.getPswd());

                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        return cfg;
    }
}
