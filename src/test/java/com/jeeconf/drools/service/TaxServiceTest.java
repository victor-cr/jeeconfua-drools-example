package com.jeeconf.drools.service;

import com.jeeconf.drools.bean.Category;
import com.jeeconf.drools.bean.Entrepreneur;
import com.jeeconf.drools.bean.Party;
import com.jeeconf.drools.bean.Person;
import com.jeeconf.drools.bean.TotalRecord;
import com.jeeconf.drools.bean.TransactionRecord;
import com.jeeconf.drools.dao.PartyDao;
import com.jeeconf.drools.dao.TransactionDao;
import org.easymock.classextension.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Service test
 *
 * @author Victor Polischuk
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
@SpringApplicationContext({
        "classpath:test-context.xml",
        "classpath:/com/jeeconf/drools/drools-context.xml"
})
public class TaxServiceTest extends Assert {
    @TestedObject
    @SpringBean("taxService")
    private TaxService taxService;

    @Mock
    @InjectInto(property = "partyDao")
    private PartyDao partyDao;

    @Mock
    @InjectInto(property = "transactionDao")
    private TransactionDao transactionDao;

    @Test
    public void testCategoryI() {
        Party testedSubject = new Entrepreneur("test-E", Category.I);
        Party someone = Person.getInstance();

        EasyMock.expect(partyDao.findAll()).andReturn(Arrays.asList(testedSubject, someone)).once();
        EasyMock.expect(transactionDao.findRecordsByParty(testedSubject)).andReturn(Arrays.asList(
                new TransactionRecord(testedSubject, someone, new BigDecimal("1000")),
                new TransactionRecord(testedSubject, someone, new BigDecimal("2000"))
        )).once();
        EasyMock.replay(partyDao, transactionDao);

        TotalRecord result = taxService.calculate().getTotalRecordList().get(0);

        assertEquals(0, new BigDecimal("3000").compareTo(result.getTransactionAmount()));
        assertEquals(0, new BigDecimal("100").compareTo(result.getTaxAmount()));
    }

    @Test
    public void testCategoryII() {
        Party testedSubject = new Entrepreneur("test-E", Category.II);
        Party someone = Person.getInstance();

        EasyMock.expect(partyDao.findAll()).andReturn(Arrays.asList(testedSubject, someone)).once();
        EasyMock.expect(transactionDao.findRecordsByParty(testedSubject)).andReturn(Arrays.asList(
                new TransactionRecord(testedSubject, someone, new BigDecimal("1000")),
                new TransactionRecord(testedSubject, someone, new BigDecimal("2000"))
        )).once();
        EasyMock.replay(partyDao, transactionDao);

        TotalRecord result = taxService.calculate().getTotalRecordList().get(0);

        assertEquals(0, new BigDecimal("3000").compareTo(result.getTransactionAmount()));
        assertEquals(0, new BigDecimal("200").compareTo(result.getTaxAmount()));
    }
}
