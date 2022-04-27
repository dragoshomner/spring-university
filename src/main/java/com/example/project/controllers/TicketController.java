package com.example.project.controllers;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TicketCreate;
import com.example.project.dtos.TicketCreateRequest;
import com.example.project.exceptions.ResourceNotFoundException;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;
    private final TravelService travelService;
    public final Logger logger;

    @RequestMapping("admin/ticket/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String tickets(
                @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                @RequestParam(value="sortBy", required = false, defaultValue = "id") String sortBy,
                Model model) {
        model.addAttribute("items", ticketService.getAllPaged(pageNumber, size, sortBy));
        model.addAttribute("mapping", Ticket.mapping);
        model.addAttribute("modelName", "Ticket");
        model.addAttribute("sortBy", sortBy);
        return "admin/paginatedTable";
    }

    @RequestMapping("admin/ticket/new")
    @PreAuthorize("hasAuthority('ADMIN')")
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

    @RequestMapping("admin/ticket/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView editForm(@PathVariable(value = "id") Long id) {
        List<User> users = userService.getAll();
        List<Travel> travels = travelService.getAll();

        Ticket ticket = ticketService.getOne(id);
        if (ticket == null) {
            throw new ResourceNotFoundException("Ticket with ID " + id + " not found");
        }

        return new ModelAndView("admin/form")
                .addObject("mapping", Ticket.mapping)
                .addObject("model", ticket)
                .addObject("modelName", "Ticket")
                .addObject("operationType", "Edit")
                .addObject("user", users)
                .addObject("travel", travels);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("admin/ticket/save")
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
                logger.info(response.getMessage());
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

    @PostMapping("admin/ticket/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            ResponseMessage response = ticketService.deleteById(id);
            logger.info(response.getMessage());
            attributes.addFlashAttribute("successMessage", "Ticket was removed successfully.");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            attributes.addFlashAttribute("errorMessage", "Ticket cannot be deleted.");
        }
        return "redirect:/admin/ticket/all";
    }

    @PostMapping("ticket/buy/{travelId}")
    public String buy(
            @PathVariable Long travelId,
            Authentication authentication,
            RedirectAttributes attributes) {
        ticketService.save(new Ticket((User) authentication.getPrincipal(), travelService.getById(travelId)));
        logger.info("A new ticket was bought");
        attributes.addFlashAttribute("successMessage", "Ticket was successfully bought.");
        return "redirect:/ticket/my";
    }

    @RequestMapping("ticket/my")
    public String my(
            Authentication authentication,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value="sortBy", required = false, defaultValue = "id") String sortBy,
            Model model) {
        model.addAttribute("items", ticketService.getAllByUserId(pageNumber, size, sortBy, (User) authentication.getPrincipal()));
        model.addAttribute("mapping", Ticket.mapping);
        model.addAttribute("modelName", "Ticket");
        model.addAttribute("sortBy", sortBy);
        return "buy_tickets";
    }

}
