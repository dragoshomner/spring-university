package com.example.project.controllers.admin;

import com.example.project.dtos.CityDto;
import com.example.project.dtos.DriverDto;
import com.example.project.dtos.ResponseMessage;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.models.City;
import com.example.project.services.CityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("admin/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;
    public final Logger logger;

    @RequestMapping("all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String cities(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                            @RequestParam(value="sortBy", required = false, defaultValue = "id") String sortBy,
                            Model model) {
        model.addAttribute("items", cityService.getAllPaged(pageNumber, size, sortBy));
        model.addAttribute("mapping", City.mapping);
        model.addAttribute("modelName", "City");
        model.addAttribute("sortBy", sortBy);
        return "admin/paginatedTable";
    }

    @RequestMapping("new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView newForm() {
        return new ModelAndView("admin/form")
            .addObject("mapping", City.mapping)
            .addObject("modelName", "City")
            .addObject("operationType", "Add");
    }

    @RequestMapping("edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView editForm(@PathVariable(value = "id") Long id) {
        CityDto cityDto = cityService.getOne(id);
        if (cityDto == null) {
            throw new ResourceNotFoundException("City with ID " + id + " not found");
        }
        return new ModelAndView("admin/form")
            .addObject("mapping", City.mapping)
            .addObject("model", cityDto)
            .addObject("modelName", "City")
            .addObject("operationType", "Edit");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("save")
    public ModelAndView save(
            @ModelAttribute @Valid CityDto cityDto
    ) {
        ModelAndView modelAndView = new ModelAndView("admin/form")
            .addObject("mapping", City.mapping)
            .addObject("modelName", "City")
            .addObject("operationType", "Edit");
        try {
            ResponseMessage response = cityService.save(cityDto);
            if (response.getStatus() == HttpStatus.ACCEPTED) {
                logger.info(response.getMessage());
                return modelAndView
                        .addObject("model", response.getModel())
                        .addObject("successMessage", response.getMessage());
            }
            throw new Exception(response.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return modelAndView
                    .addObject("model", cityDto)
                    .addObject("errorMessage", ex.getMessage());
        }
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            ResponseMessage response = cityService.deleteById(id);
            logger.info(response.getMessage());
            attributes.addFlashAttribute("successMessage", "City was removed successfully.");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("errorMessage", "City cannot be deleted.");
        }
        return "redirect:/admin/city/all";
    }
}
