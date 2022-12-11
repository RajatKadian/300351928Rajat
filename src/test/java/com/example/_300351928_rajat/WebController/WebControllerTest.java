package com.example._300351928_rajat.WebController;

import com.example._300351928_rajat.Entities.Salesman;
import com.example._300351928_rajat.Repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.swing.text.View;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class WebControllerTest {

    Salesman users;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    StudentRepository stuRepository;

    @Mock
    View mockView;


    @InjectMocks
    private WebController myCOntroller;


    @BeforeEach
    public void setup() throws ParseException
    {
        Date myObj = new Date();
        users = new Salesman();
        users.setId(1L);
        users .setName("Rajat");
        users.setAmount(500.0);
        users.setDot(myObj);
        users.setItem("washing machine");


        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(myCOntroller).build();
    }



    @Test
    public void regist() throws Exception {

        List<Salesman> list = new ArrayList<Salesman>();

        list.add(users);
        list.add(users);

        when(stuRepository.findAll()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listStudents", list))
                .andExpect(view().name("home"))
                .andExpect((model().attribute("listStudents", hasSize(2))));

        verify(stuRepository, Mockito.times(1)).findAll();
        verifyNoMoreInteractions(stuRepository);
    }

    @Test
    public void del() throws Exception{

        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(stuRepository).deleteById(idCapture.capture());
        stuRepository.deleteById(1L);
        assertEquals(1L, idCapture.getValue());
        verify(stuRepository, times(1)).deleteById(1L);
    }


    @Test
    public void edi() throws  Exception {
        Date myObj = new Date();
        Salesman s2= new Salesman();
        s2.setId(1L);
        s2.setName("John");
        s2.setItem("Refrigerator");
        s2.setAmount(500.0);
        s2.setDot(myObj);

        Long iid = 1L;

        when(stuRepository.findById(iid)).thenReturn(Optional.of(s2));

        mockMvc.perform(MockMvcRequestBuilders.get("/editS").param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("student", s2))
                .andExpect(view().name("editS"));

        verify(stuRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(stuRepository);




    }
}