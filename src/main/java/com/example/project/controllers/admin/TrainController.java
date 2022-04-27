package com.example.project.controllers.admin;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TrainDto;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.models.Route;
import com.example.project.models.Train;
import com.example.project.services.TrainService;
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
@RequestMapping("admin/train")
@RequiredArgsConstructor
public class TrainController {
    private final TrainService trainService;
    public final Logger logger;

    @RequestMapping("all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String trains(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                            @RequestParam(value="sortBy", required = false, defaultValue = "id") String sortBy,
                            Model model) {
        model.addAttribute("items", trainService.getAllPaged(pageNumber, size, sortBy));
        model.addAttribute("mapping", Train.mapping);
        model.addAttribute("modelName", "Train");
        model.addAttribute("sortBy", sortBy);
        return "admin/paginatedTable";
    }

    @RequestMapping("new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView newForm() {
        return new ModelAndView("admin/form")
                .addObject("mapping", Train.mapping)
                .addObject("modelName", "Train")
                .addObject("operationType", "Add");
    }

    @RequestMapping("edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView editForm(@PathVariable(value = "id") Long id) {
        TrainDto train = trainService.getOne(id);
        if (train == null) {
            throw new ResourceNotFoundException("Train with ID " + id + " not found");
        }

        return new ModelAndView("admin/form")
                .addObject("mapping", Train.mapping)
                .addObject("model", train)
                .addObject("modelName", "Train")
                .addObject("operationType", "Edit");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("save")
    public ModelAndView save(
            @ModelAttribute @Valid TrainDto trainDto
    ) {
        ModelAndView modelAndView = new ModelAndView("admin/form")
                .addObject("mapping", Train.mapping)
                .addObject("modelName", "Train")
                .addObject("operationType", "Edit");
        try {
            ResponseMessage response = trainService.save(trainDto);
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
                    .addObject("model", trainDto)
                    .addObject("errorMessage", ex.getMessage());
        }
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            ResponseMessage response = trainService.deleteById(id);
            logger.info(response.getMessage());
            attributes.addFlashAttribute("successMessage", "Train was removed successfully.");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("errorMessage", "Train cannot be deleted.");
        }
        return "redirect:/admin/train/all";
    }
}
