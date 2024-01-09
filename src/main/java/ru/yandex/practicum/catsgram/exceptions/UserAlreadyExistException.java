package ru.yandex.practicum.catsgram.exceptions;

public class UserAlreadyExistException extends Exception{

    public UserAlreadyExistException(){
        System.out.println("Такой пользователь уже существует");
    }
}
