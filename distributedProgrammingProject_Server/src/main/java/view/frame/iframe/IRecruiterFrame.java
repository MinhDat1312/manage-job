package view.frame.iframe;

import entity.Recruiter;
import view.frame.iframe.ilistener.IRecuiterListener;

public interface IRecruiterFrame extends IFrame<Recruiter>{
    void setRecuiterListener(IRecuiterListener listener);
}
