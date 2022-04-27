package com.example.project.controllers.admin;

import com.example.project.dtos.DriverDto;
import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TrainDto;
import com.example.project.dtos.TravelRequestParamFilter;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.models.Route;
import com.example.project.models.Travel;
import com.example.project.services.DriverService;
import com.example.project.services.RouteService;
import com.example.project.services.TrainService;
import com.example.project.services.TravelService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class TravelController {
    private final TravelService travelService;
    private final RouteService routeService;
    private final TrainService trainService;
    private final DriverService driverService;
    public final Logger logger;

    @RequestMapping(value={"admin/travel/all", "index"})
    public String travels(
            HttpServletRequest request,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value="sortBy", required = false, defaultValue = "id") String sortBy,
            Model model) {
        model.addAttribute("items", travelService.getAllPaged(pageNumber, size, sortBy));
        model.addAttribute("mapping", Travel.mapping);
        model.addAttribute("modelName", "Travel");
        model.addAttribute("sortBy", sortBy);

        if (request.getRequestURL().toString().contains("all")) {
            model.addAttribute("type", "all");
            return "admin/paginatedTable";
        }
        model.addAttribute("type", "buy");
        return "buy_tickets";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("admin/travel/new")
    public ModelAndView newForm() {
        List<Route> routes = routeService.getAll();
        List<TrainDto> trains = trainService.getAll();
        List<DriverDto> drivers = driverService.getAll();
        return new ModelAndView("admin/form")
                .addObject("mapping", Travel.mapping)
                .addObject("modelName", "Travel")
                .addObject("operationType", "Add")
                .addObject("route", routes)
                .addObject("train", trains)
                .addObject("driver", drivers);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("admin/travel/edit/{id}")
    public ModelAndView editForm(@PathVariable(value = "id") Long id) {
        List<Route> routes = routeService.getAll();
        List<TrainDto> trains = trainService.getAll();
        List<DriverDto> drivers = driverService.getAll();

        Travel travel = travelService.getById(id);
        if (travel == null) {
            throw new ResourceNotFoundException("Travel with ID " + id + " not found");
        }

        return new ModelAndView("admin/form")
                .addObject("mapping", Travel.mapping)
                .addObject("model", travel)
                .addObject("modelName", "Travel")
                .addObject("operationType", "Edit")
                .addObject("route", routes)
                .addObject("train", trains)
                .addObject("driver", drivers);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("admin/travel/save")
    public ModelAndView save(
            @ModelAttribute @Valid Travel travelDto
    ) {
        List<Route> routes = routeService.getAll();
        List<TrainDto> trains = trainService.getAll();
        List<DriverDto> drivers = driverService.getAll();
        ModelAndView modelAndView = new ModelAndView("admin/form")
                .addObject("mapping", Travel.mapping)
                .addObject("modelName", "Travel")
                .addObject("operationType", "Edit")
                .addObject("route", routes)
                .addObject("train", trains)
                .addObject("driver", drivers);
        try {
            ResponseMessage response = travelService.save(travelDto);
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
                    .addObject("model", travelDto)
                    .addObject("errorMessage", ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("admin/travel/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            ResponseMessage response = travelService.deleteById(id);
            logger.info(response.getMessage());
            attributes.addFlashAttribute("successMessage", "Travel was removed successfully.");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("errorMessage", "Travel cannot be deleted.");
        }
        return "redirect:/admin/travel/all";
    }
}
