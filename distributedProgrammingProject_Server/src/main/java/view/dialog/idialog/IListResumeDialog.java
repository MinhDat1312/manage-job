package view.dialog.idialog;

import entity.JobResume;
import entity.Resume;

import java.util.List;

public interface IListResumeDialog extends IDialog<JobResume> {
    void showDataResume(List<Resume> data);
}
