package com.mercurievv.messaginghomework.api;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/31/2017
 * Time: 5:57 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
class QuestionBlacklistValidatorTest {

    @TestFactory
    Stream<DynamicTest> testDistanceComputations() {
        QuestionBlacklistValidator questionBlacklistValidator = new QuestionBlacklistValidator("bad word should be blocked");

        return Stream.of(
                new Tuple2<>("la gushka", true)
                , new Tuple2<>("ehali medvedi na velosipede", true)
                , new Tuple2<>("Du hast mich gefragt und ich hab nichts gesagt", true)
                , new Tuple2<>("badbad", true)
                , new Tuple2<>("la bad de gushka", false)
                , new Tuple2<>("the    word   banned", false)
        )
                .map(datum -> DynamicTest.dynamicTest(
                        "Testing " + datum,
                        () -> assertThat(questionBlacklistValidator.isValid(datum.t1))
                                .isEqualTo(datum.t2)
                ));
    }

}