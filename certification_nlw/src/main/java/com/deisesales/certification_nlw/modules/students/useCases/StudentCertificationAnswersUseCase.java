package com.deisesales.certification_nlw.modules.students.useCases;

import com.deisesales.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.deisesales.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.deisesales.certification_nlw.modules.students.entities.AnswersCertificationsEntity;
import com.deisesales.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.deisesales.certification_nlw.modules.students.entities.StudentEntity;
import com.deisesales.certification_nlw.modules.students.questions.entities.QuestionEntity;
import com.deisesales.certification_nlw.modules.students.questions.repositories.QuestionRepository;
import com.deisesales.certification_nlw.modules.students.repositories.CertificationStudentEntityRepository;
import com.deisesales.certification_nlw.modules.students.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CertificationStudentEntityRepository certificationStudentEntityRepository;

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {

        var hasCertification = this.verifyIfHasCertificationUseCase.execute(new VerifyHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

        if(hasCertification) {
            throw new Exception("Sua certificação já foi emitida");
        }

        List<QuestionEntity> questionsEntities = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();
        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionsAnswers().stream().forEach(questionAnswerDTO -> {
            var ask = questionsEntities.stream().filter(question -> question.getId().equals(questionAnswerDTO.getQuestionID())).findFirst().get();
            var findCorrectAlternative = ask.getAlternatives().stream().filter(alternativesEntity -> alternativesEntity.isCorrect()).findFirst().get();

            if (findCorrectAlternative.getId().equals(questionAnswerDTO.getAlternativeID())) {
                questionAnswerDTO.setCorrect(true);
                correctAnswers.incrementAndGet();

            } else {
                questionAnswerDTO.setCorrect(false);
            }

            var answersCertificationsEntity = AnswersCertificationsEntity.builder().answerID(questionAnswerDTO.getAlternativeID()).questionID(questionAnswerDTO.getQuestionID()).isCorrect(questionAnswerDTO.isCorrect()).build();
            answersCertifications.add(answersCertificationsEntity);
        });

        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentID;

        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentID = studentCreated.getId();

        } else {
            studentID = student.get().getId();
        }

        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder().technology(dto.getTechnology()).studentID(studentID).grate(correctAnswers.get()).build();
        var certificationStudentCreated = certificationStudentEntityRepository.save(certificationStudentEntity);

        answersCertifications.stream().forEach(answersCertificationsEntity -> {
            answersCertificationsEntity.setCertificationID(certificationStudentEntity.getId());
            answersCertificationsEntity.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswersCertificationsEntities(answersCertifications);
        certificationStudentEntityRepository.save(certificationStudentEntity);

        return certificationStudentCreated;
    }
}
