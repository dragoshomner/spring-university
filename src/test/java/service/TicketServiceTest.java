package service;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TicketCreate;
import com.example.project.mappers.TicketMapper;
import com.example.project.models.Ticket;
import com.example.project.models.Travel;
import com.example.project.models.User;
import com.example.project.repositories.interfaces.ITicketRepository;
import com.example.project.services.TicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private ITicketRepository ticketRepository;
    
    @Test
    @DisplayName("Running save ticket happy flow")
    void saveNewTicketHappyFlow() {
        Ticket ticket = new Ticket(new User(1L), new Travel());
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.ACCEPTED, "Ticket successfully saved!");

        when(ticketRepository.save(ticket)).thenReturn(ticket);
        ResponseMessage responseMessageSave = ticketService.save(ticket);

        assertNotNull(responseMessageSave);
        assertEquals(responseMessageSave.getStatus(), responseMessage.getStatus());
        assertEquals(responseMessageSave.getMessage(), responseMessage.getMessage());
    }
}
