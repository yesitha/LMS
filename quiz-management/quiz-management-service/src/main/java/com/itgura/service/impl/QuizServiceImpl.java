package com.itgura.service.impl;

import com.itgura.entity.*;
import com.itgura.repository.QuestionRepository;
import com.itgura.repository.QuizRepository;
import com.itgura.repository.AssignmentRepository;
import com.itgura.request.CreateQuizRequest;
import com.itgura.request.CreateAssignmentRequest;
import com.itgura.response.*;
import com.itgura.service.QuizService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;



    @Override
    @Transactional
    public String createQuiz(CreateQuizRequest request) {
        try {
            // Create and set up the Quiz entity
            Quiz quiz = new Quiz();
            quiz.setTitle(request.getTitle());
            quiz.setDescription(request.getDescription());
            quiz.setTotalMarks(request.getTotalMarks());
            quiz.setCreatedBy(request.getCreatedBy());
            quiz.setDeadline(request.getDeadline());
            quiz.setDuration(request.getDuration());
            quiz.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            quiz.setUpdatedBy(null);
            quiz.setUpdatedAt(null);
            quiz.setClassIds(request.getClassIds());

            // Process the questions
            List<Question> questions = request.getQuestions().stream().map(q -> {
                Question question = new Question();
                question.setQuestionText(q.getQuestionText());
                question.setQuestionType(q.getQuestionType());
                question.setMarks(q.getMarks());
                question.setQuiz(quiz);
                question.setCreatedBy(request.getCreatedBy());
                question.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                question.setUpdatedBy(null);
                question.setUpdatedAt(null);

                // Process options if it's an MCQ
                if (q.getQuestionType() == QuestionType.MCQ) {
                    List<MCQOption> options = q.getOptions().stream().map(o -> {
                        MCQOption option = new MCQOption();
                        option.setOptionText(o.getOptionText());
                        option.setIsCorrect(o.isCorrect());
                        // Attach image if exists
                        if (o.getFile() != null) {
                            QuestionFile imageFile = new QuestionFile();
                            imageFile.setImageUrl(o.getFile().getFileUrl()); // Assuming the file URL is a UUID
                            imageFile.setQuestion(question);

                        }

                        option.setQuestion(question);
                        return option;
                    }).collect(Collectors.toList());
                    question.setOptions(options);
                }

                // Attach files for both MCQ and Essay
                if (q.getFiles() != null) {
                    List<QuestionFile> files = q.getFiles().stream().map(f -> {
                        QuestionFile file = new QuestionFile();
                        file.setImageUrl(f.getFileUrl()); // Assuming the file URL is a UUID
                        file.setQuestion(question);

                        return file;
                    }).collect(Collectors.toList());
                    question.setImages(files);
                }

                return question;
            }).collect(Collectors.toList());

            quiz.setQuestions(questions);

            // Save the quiz and return the quiz ID
            Quiz savedQuiz = quizRepository.save(quiz);
            return "Quiz created successfully with ID: " + savedQuiz.getId().toString();
        } catch (Exception e) {
            return "Error creating quiz: " + e.getMessage();
        }
    }
    @Override
    @Transactional
    public QuizResponse getQuizById(UUID id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();

            // Fetch questions for the quiz
            List<Question> questions = questionRepository.findByQuiz_Id(id);

            // Map to DTO
            List<QuestionResponse> questionResponses = questions.stream().map(question -> {
                return new QuestionResponse(
                        question.getId(),
                        question.getQuestionText(),
                        question.getQuestionType().name(), // Convert enum to string
                        question.getMarks(),
                        question.getCreatedBy(),
                        question.getCreatedAt(),
                        question.getUpdatedBy(),
                        question.getUpdatedAt(),
                        question.getOptions().stream().map(option -> new MCQOptionResponse(
                                option.getId(),
                                option.getOptionText(),
                                option.getIsCorrect()
                        )).collect(Collectors.toList()),
                        question.getAnswers().stream().map(answer -> new QuestionAnswerResponse(
                                answer.getId(),
                                answer.getStudentId(),
                                answer.getAnswerText(),
                                answer.getFileUrl(),
                                answer.getSubmittedAt()
                        )).collect(Collectors.toList()),
                        question.getImages().stream().map(file -> new QuestionFileResponse(
                                file.getId(),
                                file.getImageUrl()
                        )).collect(Collectors.toList())
                );
            }).collect(Collectors.toList());

            // Map to QuizResponse DTO
            return new QuizResponse(
                    quiz.getId(),
                    quiz.getTitle(),
                    quiz.getDescription(),
                    quiz.getTotalMarks(),
                    quiz.getClassIds(),
                    questionResponses
            );
        } else {
            throw new RuntimeException("Quiz not found");
        }
    }

    @Override
    @Transactional
    public void deleteQuiz(UUID id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
        } else {
            throw new RuntimeException("Quiz not found with ID: " + id);
        }
    }
    @Override
    @Transactional
    public List<QuizSummaryDTO> getQuizzesByClassIds(List<UUID> classIds) {
        List<Quiz> quizzes = quizRepository.findByClassIdsIn(classIds);
        return quizzes.stream().map(quiz -> new QuizSummaryDTO(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getStartTime(),
                quiz.getDuration(),
                quiz.getDeadline()
        )).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean updatePublishedStatus(UUID id, Boolean isPublished) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            quiz.setIsPublished(isPublished);
            quizRepository.save(quiz);
            return true;
        }
        return false;
    }

}
