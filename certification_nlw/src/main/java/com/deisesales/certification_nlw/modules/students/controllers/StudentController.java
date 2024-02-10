package com.deisesales.certification_nlw.modules.students.controllers;

import com.deisesales.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {
        System.out.println(verifyHasCertificationDTO);
        return "Usuario pode fazer a prova";
    }
}
