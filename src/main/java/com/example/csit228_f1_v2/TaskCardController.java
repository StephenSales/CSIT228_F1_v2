package com.example.csit228_f1_v2;

import javafx.scene.control.Label;

public class TaskCardController {
    public Label taskID;
    public Label taskText;
    public Label taskDate;
    public void setTaskCardValues(String tID, String text, String date){
        taskID.setText(tID);
        taskText.setText(text);
        taskDate.setText(date);
    }

//    public void onTaskEditClicked() {
//
//    }
}
