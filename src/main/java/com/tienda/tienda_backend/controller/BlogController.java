package com.tienda.tienda_backend.controller;

import com.tienda.tienda_backend.dto.BlogPostDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    private final Map<String, BlogPostDto> posts = new ConcurrentHashMap<>();

    public BlogController() {
        BlogPostDto p1 = new BlogPostDto();
        p1.setId("1");
        p1.setTitle("Cómo elegir la torta perfecta");
        p1.setExcerpt("Tres tips para no equivocarte eligiendo tu torta.");
        p1.setContent("Contenido largo del post...");
        p1.setImageUrl("https://via.placeholder.com/800x400");
        p1.setAuthor("Pastelería 1000 Sabores");
        p1.setPublishedAt(LocalDateTime.now().minusDays(3));
        p1.setTags(Arrays.asList("tortas", "tips"));

        BlogPostDto p2 = new BlogPostDto();
        p2.setId("2");
        p2.setTitle("Nuestros 50 años de historia");
        p2.setExcerpt("Una mirada a medio siglo de sabor.");
        p2.setContent("Contenido largo del post de aniversario...");
        p2.setImageUrl("https://via.placeholder.com/800x400");
        p2.setAuthor("Pastelería 1000 Sabores");
        p2.setPublishedAt(LocalDateTime.now().minusDays(10));
        p2.setTags(Arrays.asList("aniversario", "historia"));

        posts.put(p1.getId(), p1);
        posts.put(p2.getId(), p2);
    }

    @GetMapping
    public List<BlogPostDto> getAll() {
        return new ArrayList<>(posts.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostDto> getById(@PathVariable String id) {
        BlogPostDto post = posts.get(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<BlogPostDto> create(@RequestBody BlogPostDto request) {
        String id = UUID.randomUUID().toString();
        request.setId(id);
        if (request.getPublishedAt() == null) {
            request.setPublishedAt(LocalDateTime.now());
        }
        posts.put(id, request);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }
}
