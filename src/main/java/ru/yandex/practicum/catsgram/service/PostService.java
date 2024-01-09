package ru.yandex.practicum.catsgram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;
    private static Integer postId = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    private static Integer getNextId() {
        return postId++;
    }

    public List<Post> findAll(String sort, Integer page, Integer size) {
        log.debug("Текущее количество постов: {}", posts.size());
        /*Integer fromIndex = 0;
        Integer toIndex = 0;

        if(size <= 0){
            throw new UncorrectPageSizeException("Размер страницы должен быть больше нуля");
        } else {
            fromIndex = page*size;
            toIndex = fromIndex+size-1;
        }

        if(sort.equals("asc")){
            posts.stream()
                    .sorted(Comparator.comparing(Post::getCreationDate))
                    .collect(Collectors.toList());
            List<Post> ascList = posts.subList(fromIndex,toIndex);
            return ascList;
        } else if (sort.equals("desc")) {
            posts.stream()
                    .sorted(Comparator.comparing(Post::getCreationDate))
                    .collect(Collectors.toList());
            Collections.reverse(posts);
            List<Post> descList = posts.subList(fromIndex,toIndex);
            return descList;
        } else throw new IllegalArgumentException("Сортировка может быть только: asc - возрастающая, " +
                "либо desc - убывающая ");*/
        return posts.stream()
                .sorted((p0, p1) -> {
                    int comp = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
                    if (sort.equals("desc")) {
                        comp = -1 * comp; //обратный порядок сортировки
                    }
                    return comp;
                })
                .skip(page)
                .limit(size)
                .collect(Collectors.toList());
    }

    public Post findById(@PathVariable int postId) {
        return posts.stream()
                .filter(x -> x.getId() == postId)
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост с Id = %d отсутствует", postId)));
    }

    public Post create(Post post) {
        log.debug("Попытка добавить {}", post);

        User author = userService.findUserByEmail(post.getAuthor());
        if (author == null) {
            throw new UserNotFoundException(String.format("Пользователь %s не найден", post.getAuthor()));
        }

        post.setId(getNextId());
        posts.add(post);
        return post;
    }

    public List<Post> findAllByUserEmail(String email, Integer size, String sort) { //09.01.2023
        return posts.stream()
                .filter(p -> email.equals(p.getAuthor()))
                .sorted((p0, p1) -> {
            int comp = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
            if(sort.equals("desc")){
                comp = -1 * comp; //обратный порядок сортировки
            }
            return comp;
        })
                .limit(size)
                .collect(Collectors.toList());
    }
}
