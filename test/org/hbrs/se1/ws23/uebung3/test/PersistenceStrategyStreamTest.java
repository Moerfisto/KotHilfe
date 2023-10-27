package org.hbrs.se1.ws23.uebung3.test;

import org.hbrs.se1.ws23.uebung2.ConcreteMember;
import org.hbrs.se1.ws23.uebung2.Member;
import org.hbrs.se1.ws23.uebung3.PersistenceException;
import org.hbrs.se1.ws23.uebung3.PersistenceStrategyStream;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PersistenceStrategyStreamTest {

    @Test
    public void testTest() throws PersistenceException {
        List<Member> liste = new ArrayList<>();
        liste.add(new ConcreteMember(1));
        liste.add(new ConcreteMember(2));
        PersistenceStrategyStream<Member> pss = new PersistenceStrategyStream<>();

        pss.save(liste);
    }

    @Test
    public void penisTest(){
        PersistenceStrategyStream<Member> pss = new PersistenceStrategyStream<>();
        try {
            List<Member> liste = pss.load();
            System.out.println(liste);
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }

    }
}
