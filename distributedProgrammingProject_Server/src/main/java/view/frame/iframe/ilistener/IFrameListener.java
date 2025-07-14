package view.frame.iframe.ilistener;

public interface IFrameListener<T> {
    void onSearch(Object criteria);
    void onView(int row);
    void onAdd();
    void onUpdate(int row);
    void onDelete(int row);
    void onExport(Object... object);
    void onReset();
}
