package com.curso.master.controller;

import com.curso.master.dto.request.SaveRating;
import com.curso.master.dto.response.GetCompleteRating;
import com.curso.master.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<GetCompleteRating>> findAll(
            Pageable pageable)
    {
        return ResponseEntity.ok(ratingService.findAll(pageable));
    }

    @GetMapping("{ratingId}")
    public ResponseEntity<GetCompleteRating> findOneById (
            @PathVariable Long ratingId)
    {
        return ResponseEntity.ok(ratingService.findOneById(ratingId));
    }

    @PostMapping
    public ResponseEntity<GetCompleteRating> saveOne (
            @RequestBody @Valid SaveRating saveDto,
            HttpServletRequest request)
    {
        GetCompleteRating newRating = ratingService.createOne(saveDto);
        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + newRating.ratingId());
        return ResponseEntity
                .created(newLocation)
                .body(newRating);
    }

    @PutMapping("{ratingId}")
    public ResponseEntity<GetCompleteRating> UpdateOneById (
            @RequestBody @Valid SaveRating saveDto,
            @PathVariable Long ratingId)
    {
        return ResponseEntity.ok(ratingService.updateOneById(ratingId, saveDto));
    }

    @DeleteMapping("{ratingId}")
    public ResponseEntity<Void> DeleteOneById (
            @PathVariable Long ratingId)
    {
        ratingService.deleteOnById(ratingId);
        return ResponseEntity.noContent().build();
    }

}
