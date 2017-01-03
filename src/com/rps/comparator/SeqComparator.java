package com.rps.comparator;

import org.bson.Document;

import java.util.Comparator;

/**
 * Created by yinhao on 2017/1/2.
 */
public class SeqComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Document doc1 = (Document) o1;
        Document doc2 = (Document) o2;
        return doc1.getInteger("seq") - doc2.getInteger("seq");
    }
}
