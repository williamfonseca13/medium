package com.example.fuzzymatch.todo.control;


import com.example.fuzzymatch.todo.dto.ToDoDetailsResponseDto;
import com.example.fuzzymatch.todo.dto.mapper.ToDoMapper;
import com.example.fuzzymatch.todo.persistence.ToDo;
import com.example.fuzzymatch.todo.persistence.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ToDoControl {

    private final ToDoRepository toDoRepository;
    private final ToDoMapper toDoMapper;

    @PostConstruct
    @Transactional
    public void initData() {
        final ToDo cleanHouse = ToDo.builder().extRef(UUID.randomUUID().toString()).title("Clean House").active(true).build();
        final ToDo cleanCar = ToDo.builder().extRef(UUID.randomUUID().toString()).title("Clean Car").active(true).build();
        final ToDo doHomework = ToDo.builder().extRef(UUID.randomUUID().toString()).title("Do Homework").active(true).build();
        final ToDo goShopping = ToDo.builder().extRef(UUID.randomUUID().toString()).title("Go Shopping").active(true).build();
        final ToDo goToSleep = ToDo.builder().extRef(UUID.randomUUID().toString()).title("Go To Sleep").active(true).build();
        final ToDo eat = ToDo.builder().extRef(UUID.randomUUID().toString()).title("Eat").active(true).build();
        this.toDoRepository.saveAll(Arrays.asList(cleanHouse, cleanCar, doHomework, goShopping, goToSleep, eat));
    }


    public List<ToDoDetailsResponseDto> findAll(final String partialTitle) {
        return this.toDoRepository.findAllMatching(partialTitle).stream().map(this.toDoMapper::toDoToToDoDetailsResponseDto)
                .collect(Collectors.toList());
    }

    public List<ToDoDetailsResponseDto> findAllLike(final String partialTitle) {
        return this.toDoRepository.findByTitleLike(partialTitle).stream().map(this.toDoMapper::toDoToToDoDetailsResponseDto)
                .collect(Collectors.toList());
    }

    public List<ToDoDetailsResponseDto> findAllStartingWith(final String partialTitle) {
        return this.toDoRepository.findByTitleStartingWith(partialTitle).stream().map(this.toDoMapper::toDoToToDoDetailsResponseDto)
                .collect(Collectors.toList());
    }

    public List<ToDoDetailsResponseDto> findAll() {
        return this.toDoRepository.findAll().stream().map(this.toDoMapper::toDoToToDoDetailsResponseDto)
                .collect(Collectors.toList());
    }

}
