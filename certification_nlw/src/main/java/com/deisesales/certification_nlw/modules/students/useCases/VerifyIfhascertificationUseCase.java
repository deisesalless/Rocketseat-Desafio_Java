package com.deisesales.certification_nlw.modules.students.useCases;

import com.deisesales.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfhascertificationUseCase {

    public boolean execute(VerifyHasCertificationDTO dto) {

        if (dto.getEmail().equalsIgnoreCase("deisesales@gmail.com") &&
                dto.getTechnology().equalsIgnoreCase("java")) {
            return true;
        }

        return false;
    }
}
