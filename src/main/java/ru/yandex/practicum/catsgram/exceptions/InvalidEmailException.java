package ru.yandex.practicum.catsgram.exceptions;

public class InvalidEmailException extends Exception{

    public InvalidEmailException(){
        System.out.println("Отсутствует или неверно задан email!");
    }
}
