package com.example._300351928_rajat.WebController;

import com.example._300351928_rajat.Entities.Salesman;
import com.example._300351928_rajat.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {

    @Autowired
    private StudentRepository stuRepository;

    @GetMapping("/")
    private String hello(Model model)
    {
        List<Salesman> usersList =stuRepository.findAll();
        model.addAttribute("listStudents", usersList);
        List<String> nameList =usersList.stream().map(x->x.getItem()).collect(Collectors.toList());
        model.addAttribute("liStudents", nameList);

        return "home";
    }

    @GetMapping("/index")
    private String hello1(Model model)
    {
        List<Salesman> usersList =stuRepository.findAll();
        model.addAttribute("listStudents", usersList);
        List<String> nameList =usersList.stream().map(x->x.getItem()).collect(Collectors.toList());
        model.addAttribute("liStudents", nameList);

        return "home";
    }

    @PostMapping("/register")
    public String regist(@ModelAttribute Salesman users)
    {

//        serviceImp.addUser(users);
        stuRepository.save(users);
        stuRepository.findAll();
        return "redirect:/";

    }

    @GetMapping("/delete")
    public String del(Long id)
    {
        stuRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String Edi(Long id, Model model)
    {
        Salesman student = stuRepository.findById(id).orElse(null);
        if(student==null) throw new RuntimeException("Patient does not exist");
        model.addAttribute("student", student);

        return "editS";

    }






}
