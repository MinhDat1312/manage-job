package view.dialog.idialog;

public interface IDialogListener<T> {
    void onSearch(Object criteria);
    void onView(int row);
    void onReset();
}
