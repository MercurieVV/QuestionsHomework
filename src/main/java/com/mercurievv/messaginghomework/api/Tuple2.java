package com.mercurievv.messaginghomework.api;

import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/31/2017
 * Time: 6:00 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@SuppressWarnings("WeakerAccess")
@ToString
public class Tuple2<T1, T2> {
    public final T1 t1;
    public final T2 t2;

    public Tuple2(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }
}
