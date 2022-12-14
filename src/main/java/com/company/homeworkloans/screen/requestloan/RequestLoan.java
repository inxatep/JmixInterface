package com.company.homeworkloans.screen.requestloan;

import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

@UiController("RequestLoan")
@UiDescriptor("request-loan.xml")
@LookupComponent("lookupBox")
public class RequestLoan extends StandardLookup<Client> {

    @Autowired
    public DataManager dataManager;

    @Autowired
    public DataContext dataContext;

    @Autowired
    public Notifications notifications;

    @Autowired
    protected InstanceContainer<Loan> LoanDc;

    @Autowired
    protected InstanceContainer<Client> clientsDc;
    @Autowired
    protected Metadata metadata;

    @Subscribe("Request")
    protected void onSomeActionActionPerformed(Action.ActionPerformedEvent event) {
        BigDecimal amount = LoanDc.getItem().getAmount();
        if(amount instanceof BigDecimal) {
            Client client = clientsDc.getItem();
            Loan loan = dataManager.create(Loan.class);
            loan.setClient(client);
            loan.setAmount(amount);
            loan.setStatus(LoanStatus.REQUESTED);
            loan.setRequestDate(LocalDate.now());
            dataManager.save(loan);
            notifications.create()
                    .withCaption("Request performed")
                    .show();
            closeWithDefaultAction();
        } else {
            notifications.create()
                    .withCaption("Неверное значение Amount")
                    .show();
        }
    }
    @Subscribe("Cancel")
    public void onCloseWindow(Action.ActionPerformedEvent event) {
        closeWithDefaultAction();
    }
    @Subscribe
    protected void onInit(InitEvent event) {
        Loan loan = metadata.create(Loan.class);
        LoanDc.setItem(loan);
    }
}

