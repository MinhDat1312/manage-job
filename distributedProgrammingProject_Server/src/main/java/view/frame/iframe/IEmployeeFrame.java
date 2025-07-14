package view.frame.iframe;

import entity.Employee;
import view.frame.iframe.ilistener.IEmployeeListener;

public interface IEmployeeFrame extends IFrame<Employee>{
    void setEmployeeListener(IEmployeeListener listener);
}
