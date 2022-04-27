package com.example.project.controllers.admin;

import com.example.project.dtos.CityDto;
import com.example.project.dtos.DriverDto;
import com.example.project.dtos.ResponseMessage;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.models.Route;
import com.example.project.services.CityService;
import com.example.project.services.RouteService;
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
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("admin/route")
@RequiredArgsConstructor
public class RouteController {
    
    private final RouteService routeService;
    private final CityService cityService;
    public final Logger logger;

    @RequestMapping("all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String routes(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                            @RequestParam(value="sortBy", required = false, defaultValue = "id") String sortBy,
                         Model model) {
        model.addAttribute("items", routeService.getAllPaged(pageNumber, size, sortBy));
        model.addAttribute("mapping", Route.mapping);
        model.addAttribute("modelName", "Route");
        model.addAttribute("sortBy", sortBy);
        return "admin/paginatedTable";
    }

    @RequestMapping("new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView newForm() {
        List<CityDto> cities = cityService.getAll();
        return new ModelAndView("admin/form")
                .addObject("mapping", Route.mapping)
                .addObject("modelName", "Route")
                .addObject("operationType", "Add")
                .addObject("cityFrom", cities)
                .addObject("cityTo", cities);
    }

    @RequestMapping("edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView editForm(@PathVariable(value = "id") Long id) {
        List<CityDto> cities = cityService.getAll();

        Route route = routeService.getOne(id);
        if (route == null) {
            throw new ResourceNotFoundException("Route with ID " + id + " not found");
        }

        return new ModelAndView("admin/form")
                .addObject("mapping", Route.mapping)
                .addObject("model", route)
                .addObject("modelName", "Route")
                .addObject("operationType", "Edit")
                .addObject("cityFrom", cities)
                .addObject("cityTo", cities);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("save")
    public ModelAndView save(
            @ModelAttribute @Valid Route routeDto
    ) {
        List<CityDto> cities = cityService.getAll();
        ModelAndView modelAndView = new ModelAndView("admin/form")
                .addObject("mapping", Route.mapping)
                .addObject("modelName", "Route")
                .addObject("operationType", "Edit")
                .addObject("cityFrom", cities)
                .addObject("cityTo", cities);
        try {
            ResponseMessage response = routeService.save(routeDto);
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
                    .addObject("model", routeDto)
                    .addObject("errorMessage", ex.getMessage());
        }
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            ResponseMessage response = routeService.deleteById(id);
            logger.info(response.getMessage());
            attributes.addFlashAttribute("successMessage", "Route was removed successfully.");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("errorMessage", "Route cannot be deleted.");
        }
        return "redirect:/admin/route/all";
    }
}
