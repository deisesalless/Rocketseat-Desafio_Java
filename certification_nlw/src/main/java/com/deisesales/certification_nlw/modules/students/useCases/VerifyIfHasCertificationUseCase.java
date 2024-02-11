package com.deisesales.certification_nlw.modules.students.useCases;

import com.deisesales.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfHasCertificationUseCase {

    public boolean execute(VerifyHasCertificationDTO dto) {

        Object object = "deisesales18@gmail.com";
        if(dto.getEmail().equals(object) && dto.getTechnology().equalsIgnoreCase("JAVA")) {
            return true;
        }
        return false;
    }
}
