package com.example.project.controllers.admin;

import com.example.project.dtos.DriverDto;
import com.example.project.dtos.ResponseMessage;
import com.example.project.exceptions.ResourceNotFoundException;
import com.example.project.models.Driver;
import com.example.project.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("admin/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    public final Logger logger;

    @RequestMapping("all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String drivers(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                            @RequestParam(value="sortBy", required = false, defaultValue = "id") String sortBy,
                            Model model) {
        model.addAttribute("items", driverService.getAllPaged(pageNumber, size, sortBy));
        model.addAttribute("mapping", Driver.mapping);
        model.addAttribute("modelName", "Driver");
        model.addAttribute("sortBy", sortBy);
        return "admin/paginatedTable";
    }

    @RequestMapping("new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView newForm() {
        return new ModelAndView("admin/form")
                .addObject("mapping", Driver.mapping)
                .addObject("modelName", "Driver")
                .addObject("operationType", "Add");
    }

    @RequestMapping("edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView editForm(@PathVariable(value = "id") Long id) {
        DriverDto driver = driverService.getOne(id);
        if (driver == null) {
            throw new ResourceNotFoundException("Driver with ID " + id + " not found");
        }
        return new ModelAndView("admin/form")
                .addObject("mapping", Driver.mapping)
                .addObject("model", driver)
                .addObject("modelName", "Driver")
                .addObject("operationType", "Edit");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("save")
    public ModelAndView save(
            @ModelAttribute @Valid DriverDto driverDto,
            BindingResult bindingResult
    ) {
        ModelAndView modelAndView = new ModelAndView("admin/form")
                .addObject("mapping", Driver.mapping)
                .addObject("modelName", "Driver")
                .addObject("operationType", "Edit");
        try {
            if (bindingResult.hasErrors()){
                throw new Exception(bindingResult.getAllErrors().get(0).getDefaultMessage());
            }

            ResponseMessage response = driverService.save(driverDto);
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
                    .addObject("model", driverDto)
                    .addObject("errorMessage", ex.getMessage());
        }
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            ResponseMessage response = driverService.deleteById(id);
            logger.info(response.getMessage());
            attributes.addFlashAttribute("successMessage", "Driver was removed successfully.");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("errorMessage", "Driver cannot be deleted.");
        }
        return "redirect:/admin/driver/all";
    }
}
