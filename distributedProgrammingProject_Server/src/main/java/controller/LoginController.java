package controller;

import entity.Account;
import exception.checkUserEmail;
import exception.checkUserPass;
import service.inteface.AccountService;
import view.frame.iframe.ILoginFrame;
import view.frame.imp.LoginFrameImp;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginController {
    private final ILoginFrame frame;
    private final AccountService accountService;

    public LoginController(ILoginFrame frame, AccountService accountService) {
        this.frame = frame;
        this.accountService = accountService;

        setOnLogin();
    }

    private void setOnLogin(){
        frame.setOnLoginListener((email, password) -> {

            SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
                @Override
                protected Void doInBackground() throws Exception {

                    try {
                        Account account = accountService.login(email, password);
                        for (int i = 0; i <= 100; i += 10) {
                            Thread.sleep(100);
                            publish(i);
                        }
                        System.out.println("logging...");
                        frame.showMainFrame(account.getEmployee(), account.getRole().getValue());
                        frame.close();
                    } catch (checkUserEmail e) {
                        frame.setEmailFieldColor(new Color(0, 102, 102));
                        frame.showMessage(e.getMessage());
                    } catch (checkUserPass e) {
                        frame.setPasswordFieldColor(new Color(0, 102, 102));
                        frame.showMessage(e.getMessage());
                    }
                    return null;
                }

                @Override
                protected void process(List<Integer> chunks) {
                    for(int chunk : chunks){
                        ((LoginFrameImp)frame).getProgressBar().setValue(chunk);
                    }
                }

                @Override
                protected void done() {
                    frame.showLoading(false);
                }
            };

            new Thread(() -> {
                worker.execute();
                frame.showLoading(true);
            }).start();
        });
    }

    public void show(){
        frame.visible();
    }
}
