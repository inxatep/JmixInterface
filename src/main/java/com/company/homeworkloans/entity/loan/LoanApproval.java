package com.company.homeworkloans.entity.loan;

import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("LoanApproval.browse")
@UiDescriptor("loanApproval-browse.xml")
@LookupComponent("loansTable")
public class LoanApproval extends StandardLookup<Loan> {

    @Autowired
    protected InstanceContainer<Loan> loansDc;

    @Autowired
    public DataManager dataManager;

    @Autowired
    public Notifications notifications;

    @Subscribe("Approve")
    protected void ApproveLoan(Button.ClickEvent event) {
        Loan loan = loansDc.getItem();
        loan.setStatus(LoanStatus.APPROVED);
        dataManager.remove(loan);
        notifications.create()
                .withCaption("Approved")
                .show();
    }
    @Subscribe("Rejected")
    protected void RejectedLoan(Button.ClickEvent event) {
        Loan loan = loansDc.getItem();
        loan.setStatus(LoanStatus.REJECTED);
        dataManager.remove(loan);
        notifications.create()
                .withCaption("Rejected")
                .show();
    }
    @Install(to = "loansTable.fullName", subject = "columnGenerator")
    private Table.PlainTextCell tableGeneratedColumnFullNameColumnGenerator(Loan loan) {
        return new Table.PlainTextCell(loan.getClient().getFirstName() + " " + loan.getClient().getLastName());
    }
}