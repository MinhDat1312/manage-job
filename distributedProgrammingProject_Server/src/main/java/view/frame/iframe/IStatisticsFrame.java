package view.frame.iframe;

import entity.Invoice;

import java.util.List;
import java.util.Map;

public interface IStatisticsFrame extends IFrame {
    void addYearsBox(List<Invoice> invoice);
    void createPieChart(Map<?, Long> statistics, String title);
    void createLineChart(Object... objects);
}
