package view.frame.iframe;

import view.frame.iframe.ilistener.IFrameListener;

import javax.swing.*;
import java.util.List;

public interface IFrame<T> {
    void visible();
    void close();
    void showMessage(String message);
    void setFrameListener(IFrameListener<T> listener);
    JPanel getPanel();
    void showData(List<T> data, Object... objects);
    JTable getTable();
    Object getSearchCriteria();
    String getSearchText();
    int getSelectedRow();
}

