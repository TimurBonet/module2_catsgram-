package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController

public class PostFeedController {


    private final PostService postService;

    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/feed/friends")  //09.01.2023
    List<Post> getFriendsFeed(@RequestBody String params) {
        ObjectMapper objectMapper = new ObjectMapper();
        FriendParam friendParam;
        try {
            String parametersFromString = objectMapper.readValue(params, String.class);
            friendParam = objectMapper.readValue(parametersFromString, FriendParam.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Некорректный формат json", e);
        }

        if (friendParam != null) {
            List<Post> res = new ArrayList<>();
            for (String friend : friendParam.friends) {
                res.addAll(postService.findAllByUserEmail(friend, friendParam.size, friendParam.sort));
            }
            return res;
        } else {
            throw new RuntimeException("неверно заполнены параметры");
        }
    }


    @Data //09.01.2023
    static class FriendParam {
        String sort;
        Integer size;
        List<String> friends;
    }
}
