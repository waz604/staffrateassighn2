package com.assign2.staffrate.controller;


import com.assign2.staffrate.model.StaffRating;
import com.assign2.staffrate.model.StaffRoleType;
import com.assign2.staffrate.services.StaffRatingService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ratings")
public class StaffRatingController {

    private final StaffRatingService service;

    public StaffRatingController(StaffRatingService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("ratings", service.findAll());
        return "ratings/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("rating", service.findByIdOrThrow(id));
        return "ratings/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("rating", new StaffRating());
        model.addAttribute("roles", StaffRoleType.values());
        return "ratings/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("rating") StaffRating rating,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", StaffRoleType.values());
            return "ratings/form";
        }

        try {
            service.create(rating);
        } catch (StaffRatingService.DuplicateEmailException e) {
            bindingResult.rejectValue("email", "error.email", "Email already exists");
            model.addAttribute("roles", StaffRoleType.values());
            return "ratings/form";
        }

        return "redirect:/ratings";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("rating", service.findByIdOrThrow(id));
        model.addAttribute("roles", StaffRoleType.values());
        return "ratings/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("rating") StaffRating rating,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", StaffRoleType.values());
            return "ratings/form";
        }

        try {
            service.update(id, rating);
        } catch (StaffRatingService.DuplicateEmailException e) {
            bindingResult.rejectValue("email", "error.email", "Email already exists");
            model.addAttribute("roles", StaffRoleType.values());
            return "ratings/form";
        }

        return "redirect:/ratings";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/ratings";
    }
}
