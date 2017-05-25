package com.mercurievv.messaginghomework.web;

import com.mercurievv.messaginghomework.MessagesController;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    void testDos() {
        TestObserver<Integer> testObserver = new TestObserver<>();
        Observable.range(0, 6)
                .observeOn(Schedulers.newThread())
                .map(integer -> given().get("/messages").statusCode())
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();

        assertThat(testObserver.values()).containsOnly(200, 429);
    }
}