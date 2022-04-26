package com.example.project.controllers;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TicketCreate;
import com.example.project.dtos.TicketCreateRequest;
import com.example.project.models.Ticket;
import com.example.project.models.Travel;
import com.example.project.models.User;
import com.example.project.services.TicketService;
import com.example.project.services.TravelService;
import com.example.project.services.UserService;
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
@RequestMapping("admin/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;
    private final TravelService travelService;
    public final Logger logger;

    @RequestMapping("all")
    public String tickets(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                         @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                         @RequestParam(value="sortBy", required = false, defaultValue = "id") String sortBy,
                         Model model) {
        model.addAttribute("items", ticketService.getAllPaged(pageNumber, size, sortBy));
        model.addAttribute("mapping", Ticket.mapping);
        model.addAttribute("modelName", "Ticket");
        model.addAttribute("sortBy", sortBy);
        return "admin/paginatedTable";
    }

    @RequestMapping("new")
    public ModelAndView newForm() {
        List<User> users = userService.getAll();
        List<Travel> travels = travelService.getAll();
        return new ModelAndView("admin/form")
                .addObject("mapping", Ticket.mapping)
                .addObject("modelName", "Ticket")
                .addObject("operationType", "Add")
                .addObject("user", users)
                .addObject("travel", travels);
    }

    @RequestMapping("edit/{id}")
    public ModelAndView editForm(@PathVariable(value = "id") Long id) {
        List<User> users = userService.getAll();
        List<Travel> travels = travelService.getAll();
        return new ModelAndView("admin/form")
                .addObject("mapping", Ticket.mapping)
                .addObject("model", ticketService.getOne(id))
                .addObject("modelName", "Ticket")
                .addObject("operationType", "Edit")
                .addObject("user", users)
                .addObject("travel", travels);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("save")
    public ModelAndView save(
            @ModelAttribute @Valid TicketCreateRequest ticketCreateRequest
    ) {
        List<User> users = userService.getAll();
        List<Travel> travels = travelService.getAll();
        Ticket ticketDto;
        if (ticketCreateRequest.getId() == null) {
            ticketDto = new Ticket(userService.getById(ticketCreateRequest.user), ticketCreateRequest.travel);
        } else {
            ticketDto = ticketService.getOne(ticketCreateRequest.id);
        }
        ModelAndView modelAndView = new ModelAndView("admin/form")
                .addObject("mapping", Ticket.mapping)
                .addObject("modelName", "Ticket")
                .addObject("operationType", "Edit")
                .addObject("user", users)
                .addObject("travel", travels);
        try {
            ResponseMessage response = ticketService.save(ticketDto);
            if (response.getStatus() == HttpStatus.ACCEPTED) {
                return modelAndView
                        .addObject("model", response.getModel())
                        .addObject("successMessage", response.getMessage());
            }
            throw new Exception(response.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return modelAndView
                    .addObject("model", ticketDto)
                    .addObject("errorMessage", ex.getMessage());
        }
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            ResponseMessage response = ticketService.deleteById(id);
            attributes.addFlashAttribute("successMessage", "Ticket was removed successfully.");
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Ticket cannot be deleted.");
        }
        return "redirect:/admin/ticket/all";
    }
}
