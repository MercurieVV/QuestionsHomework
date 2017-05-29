package com.mercurievv.messaginghomework.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mercurievv.messaginghomework.db.DbHelper;
import com.mercurievv.messaginghomework.web.BaseRestTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.mercurievv.messaginghomework.db.QQuestions.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/29/2017
 * Time: 12:43 AM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@ExtendWith(SpringExtension.class)
class QuestionsControllerFunctionalTest extends BaseRestTest {
    @Autowired
    DbHelper dbHelper;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        dbHelper.getQueryFactory()
                .delete(questions)
                .execute();
    }

    @Test
    void postAndGetQuestion() throws JsonProcessingException {
        post("/questions", objectMapper.writeValueAsString(
                new Question(null, "Vovec", null, "Gbl bbl?")
        ));

        Question[] questions = getObject("/questions", "", Question[].class);
        assertThat(questions).hasSize(1)
                .extracting(input -> input.message).containsOnly("Gbl bbl?");
        assertThat(questions[0].countryCode)
                .isNotEmpty().isNotNull();
    }

    @Test
    void getByCountry() {
        insertQuestion("Ololosh", "ZW", "Pish pish?");
        insertQuestion("Syava", "RU", "Cho? Cho?");

        Question[] questions = getObject("/questions?countryCode=ZW", "", Question[].class);
        assertThat(questions).hasSize(1)
                .extracting(input -> input.message).containsOnly("Pish pish?");
    }

    private void insertQuestion(String name, String countryCode, String question) {
        dbHelper.getQueryFactory()
                .insert(questions)
                .columns(questions.userName, questions.countryCode, questions.text)
                .values(name, countryCode, question)
                .execute();
    }
}