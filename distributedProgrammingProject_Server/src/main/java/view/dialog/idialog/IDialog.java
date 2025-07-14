package view.dialog.idialog;

import view.frame.iframe.ilistener.IFrameListener;

import javax.swing.*;
import java.util.List;

public interface IDialog <T>{
    void visible();
    void close();
    void showMessage(String message);
    void setDialogListener(IDialogListener<T> listener);
    JPanel getPanel();
    void showData(List<T> data, Object... objects);
    JTable getTable();
    Object getSearchCriteria();
    String getSearchText();
    int getSelectedRow();
}
