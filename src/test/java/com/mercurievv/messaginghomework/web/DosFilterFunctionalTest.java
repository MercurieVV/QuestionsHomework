package com.mercurievv.messaginghomework.web;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/22/2017
 * Time: 10:16 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@ExtendWith(SpringExtension.class)
class DosFilterFunctionalTest extends BaseRestTest {
    @Test
    void testDos() throws InterruptedException {
        TestObserver<Integer> testObserver = new TestObserver<>();
        Observable.range(0, 8)
                .observeOn(Schedulers.newThread())
                .map(integer -> given().get("/messages").statusCode())
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();

        assertThat(testObserver.values()).containsOnly(200, 429);
        Thread.sleep(1000); //to not affect on another functional tests
    }
}