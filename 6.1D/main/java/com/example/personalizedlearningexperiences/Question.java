package com.example.personalizedlearningexperiences;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

public class Question implements Parcelable {
    String correctAnswer;
    List<String> options;
    String question;
    String chosenAnswer;
    String topic;
    Calendar timeStamp;


    public Question(String correctAnswer, List<String> options, String question, String chosenAnswer, String topic, Calendar timeStamp, String packageType) {
        this.correctAnswer = correctAnswer;
        this.options = options;
        this.question = question;
        this.chosenAnswer = chosenAnswer;
        this.topic = topic;
        this.timeStamp = timeStamp;
    }

    protected Question(Parcel in) {
        correctAnswer = in.readString();
        options = in.createStringArrayList();
        question = in.readString();
        chosenAnswer = in.readString();
        topic = in.readString();
        timeStamp = (Calendar) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(correctAnswer);
        dest.writeStringList(options);
        dest.writeString(question);
        dest.writeString(chosenAnswer);
        dest.writeString(topic);
        dest.writeSerializable(timeStamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
