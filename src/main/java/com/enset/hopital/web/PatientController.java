package com.enset.hopital.web;

import com.enset.hopital.entities.Patient;
import com.enset.hopital.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "4") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String kwd){
        Page<Patient> pagePatients=patientRepository.findByNomContains(kwd,PageRequest.of(page,size));
        model.addAttribute("pages", new int[pagePatients.getTotalPages()] );
        model.addAttribute("ListPatients", pagePatients.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword",kwd);
        return "patients";
    }

    @GetMapping("/delete")
    public String delete(Long id,String keyword, int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword+"&page="+page;
    }


}
