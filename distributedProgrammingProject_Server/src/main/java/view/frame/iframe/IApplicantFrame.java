package view.frame.iframe;

import entity.Applicant;
import view.frame.iframe.ilistener.IApplicantListener;

import javax.swing.*;
import java.awt.event.KeyEvent;

public interface IApplicantFrame extends IFrame<Applicant> {
    void setApplicantListener(IApplicantListener listener);
}
