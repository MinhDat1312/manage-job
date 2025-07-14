package controller;
import dao.AccountDAO;
import dao.InvoiceDAO;
import entity.Account;
import entity.Invoice;
import service.imp.AccountServiceImp;
import service.imp.InvoiceServiceImp;
import service.inteface.AccountService;
import service.inteface.InvoiceService;
import view.frame.iframe.IStatisticsFrame;
import view.frame.iframe.ilistener.IFrameListener;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class StatisticsController {
    private IStatisticsFrame statisticsFrame;
    private InvoiceService invoiceService;
    private AccountService accountService;

    public StatisticsController(IStatisticsFrame statisticsFrame) {
        this.statisticsFrame = statisticsFrame;
        try {
            this.invoiceService = new InvoiceServiceImp(new InvoiceDAO(Invoice.class));
            this.accountService = new AccountServiceImp(new AccountDAO(Account.class));
        } catch (RemoteException e) {
            statisticsFrame.showMessage("Kết nối bị lỗi");
        }

        addYearBox();
        createPieChart();
        setListeners();
    }

    private void setListeners() {
        statisticsFrame.setFrameListener(new IFrameListener() {
            @Override
            public void onSearch(Object criteria) {

            }

            @Override
            public void onView(int row) {

            }

            @Override
            public void onAdd() {

            }

            @Override
            public void onUpdate(int row) {
                int year = row;
                createLineChart(year);
            }

            @Override
            public void onDelete(int row) {

            }

            @Override
            public void onExport(Object... object) {

            }

            @Override
            public void onReset() {

            }
        });
    }

    private void addYearBox(){
        try {
            List<Invoice> invoices = invoiceService.getAll();
            statisticsFrame.addYearsBox(invoices);

            if (!invoices.isEmpty()) {
                int latestYear = invoices.stream()
                        .map(i -> i.getCreatedDate().getYear())
                        .max(Integer::compare)
                        .orElse(2025);
                createLineChart(latestYear);
            }
        } catch (RemoteException e) {
            statisticsFrame.showMessage("Kết nối bị lỗi");
        }
    }

    private void createPieChart(){
        try {
            statisticsFrame.createPieChart(invoiceService.statisticsInvoiceByWorkingType(),
                    "Tỉ lệ hóa đơn theo hình thức làm việc");
            statisticsFrame.createPieChart(invoiceService.statisticsInvoiceByLevel(),
                    "Tỉ lệ hóa đơn theo trình độ");
            statisticsFrame.createPieChart(invoiceService.statisticsInvoiceByProfession(),
                    "Tỉ lệ hóa đơn theo ngành nghề");
        } catch (RemoteException e) {
            statisticsFrame.showMessage("Kết nối bị lỗi");
        }
    }

    private void createLineChart(int year){
        try {
            List<Account> accounts = accountService.getAll();
            List<Invoice> invoices = invoiceService.getAll();
            Map<Integer, List<Double>> data = invoiceService.calculateInvoiceTotalsByMonthAndYear(
                    invoices, year
            );
            statisticsFrame.createLineChart(accounts, data);
        } catch (RemoteException e) {
            statisticsFrame.showMessage("Kết nối bị lỗi");
        }
    }
}
