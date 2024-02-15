package com.deisesales.certification_nlw.modules.students.controllers;

import com.deisesales.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.deisesales.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.deisesales.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.deisesales.certification_nlw.modules.students.useCases.StudentCertificationAnswersUseCase;
import com.deisesales.certification_nlw.modules.students.useCases.VerifyIfHasCertificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfhascertificationUseCase;

    @Autowired
    private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {
        boolean result = this.verifyIfhascertificationUseCase.execute(verifyHasCertificationDTO);
        if (result) {
            return "Usuario já fez a prova";
        } else {
            return "Usuario pode fazer a prova";
        }
    }

    @PostMapping("/certification/answer")
    public CertificationStudentEntity certificationAnswer(@RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO) {
        return this.studentCertificationAnswersUseCase.execute(studentCertificationAnswerDTO);
    }
}
