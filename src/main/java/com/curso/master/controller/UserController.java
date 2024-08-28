package com.curso.master.controller;

import com.curso.master.dto.request.SaveUser;
import com.curso.master.dto.request.UserSearchCriteria;
import com.curso.master.dto.response.GetMovie;
import com.curso.master.dto.response.GetUser;
import com.curso.master.service.RatingService;
import com.curso.master.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<GetUser>> findAll(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String name,
            Pageable pageable)
    {
        UserSearchCriteria searchCriteria =
                new UserSearchCriteria(username, name);

        Page<GetUser> users =  userService.findAll(searchCriteria, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{user}")
    public ResponseEntity<GetUser> findOneByUsername(@PathVariable("user") String username)
    {
        return ResponseEntity.ok(userService.findOneByUsername(username));
    }

    @GetMapping("/{username}/ratings")
    @ResponseBody
    public ResponseEntity<Page<GetUser.GetRating>> findAllRatingForUserByUsername(
            @PathVariable String username,
            Pageable pageable)
    {
        return ResponseEntity.ok(ratingService.findAllByUsername(username, pageable));
    }

    @PostMapping
    public ResponseEntity<GetUser> createOne(
            @Valid @RequestBody SaveUser saveDto,
            HttpServletRequest request)
    {

        GetUser newUser = userService.saveOne(saveDto);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + newUser.username());

        return ResponseEntity
                .created(newLocation)
                .body(newUser);
    }

    @PutMapping("/{name}")
    public ResponseEntity<GetUser> updateOne(
            @PathVariable String name,
            @Valid @RequestBody SaveUser saveDto)
    {
        GetUser oldUser = userService.updateOneByUsername(name, saveDto);
        return ResponseEntity.ok(oldUser);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<GetUser> deleteOneById(
            @PathVariable String name)
    {
        userService.deleteOneByUsername(name);
        return ResponseEntity.noContent().build();
    }
}
