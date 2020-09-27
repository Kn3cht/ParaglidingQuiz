package com.example.paraglidingquiz.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.preference.PreferenceManager;

import com.example.paraglidingquiz.R;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class QuestionCatalog implements Serializable, SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(null);

    public static final int NEVER_ANSWERED = 0;
    public static final int ANSWERED_WRONG = 1;
    public static final int RANDOM_QUESTIONS = 2;

    public static final int BOX_QUESTIONS = 3;

    private static Set<String> chapters;

    /**
     * Contains all questions
     */
    private static Question[] questions;

    /**
     * Contains the current question set
     */
    private static Question[] currentQuestionSet;

    /**
     * Index of the current Question in the currentQuestionSet
     */
    private static int index = -1;

    /**
     * Returns all questions
     * @return
     */
    public static Question[] getQuestions() {
        return questions;
    }

    /**
     * Returns the question at index index in the question set which contains all
     * questions
     * @param index
     * @return
     */
    public static Question getQuesetion(short index) {
        if (questions == null)
            throw new RuntimeException("getQuestion: Question catalog not loaded");
        if (index >= questions.length)
            throw new RuntimeException("No such element: index " + index + " > questions.length " + questions.length);

        return questions[index];
    }

    private static Question[] getWrongQuestions() {
        Question[] ret = null;
        List<Question> li = new ArrayList<>();
        for (Question q : questions) {
            if (q.isAnsweredWrong() && QuestionCatalog.chapters == null ||
                    q.getUnanswered() && QuestionCatalog.chapters.contains("" + q.getChapter()))
                li.add(q);
        }

        ret = new Question[li.size()];
        ret = li.toArray(ret);

        if (ret == null)
            throw new RuntimeException("getUnansweredQuestions: Question Catalog not loaded");
        return ret;
    }

    private static Question[] getCorrectQuestions() {
        Question[] ret = null;
        List<Question> li = new ArrayList<>();
        for (Question q : questions )
            if (q.getAnsweredCorrectCounter() > 0 && QuestionCatalog.chapters == null ||
                    q.getUnanswered() && QuestionCatalog.chapters.contains("" + q.getChapter()))
                li.add(q);

        ret = new Question[li.size()];
        ret = li.toArray(ret);

        if (ret == null)
            throw new RuntimeException("getUnansweredQuestions: Question Catalog not loaded");
        return ret;
    }

    /**
     * Returns the statistics of the progress among all questions
     * @return
     */
    public static PieData getStatisticsProgress(Context context) {
        float[] statistics = new float[2];
        int unanswered = 0;
        for (Question q : QuestionCatalog.questions)
            if (q.getAnsweredCorrectCounter() == 0 && q.getAnsweredWrongCounter() == 0)
                unanswered++;

        statistics[0] = (questions.length - unanswered) * 1.f / questions.length * 100;
        statistics[1] = unanswered * 1.f / questions.length * 100;

        ArrayList<PieEntry> values = new ArrayList<>();
        values.add(new PieEntry(statistics[0], "Beantwortet"));
        values.add(new PieEntry(statistics[1], "Unbeantwortet"));

        return createPieData(createPieDataSet("", values, new int[] {context.getResources().getColor(R.color.progress), context.getResources().getColor(R.color.unanswered)}, context));
    }

    /**
     * Returns the statistics of the progress among answered questions
     *
     * @return PieData containing statistical information
     */
    public static PieData getStatisticsAnswered(Context context) {
        float[] statistics = new float[3];
        int correct = 0, wrong = 0, unanswered = 0;
        for (Question q : QuestionCatalog.questions)
            if (q.getAnsweredCorrectCounter() == 0 && q.getAnsweredWrongCounter() == 0)
                unanswered++;
            else
                if (q.getAnsweredWrongCounter() > q.getAnsweredCorrectCounter())
                    wrong++;
                else
                    correct++;

        statistics[0] = correct * 1.f / questions.length * 100;
        statistics[1] = wrong * 1.f / questions.length * 100;
        statistics[2] = unanswered * 1.f / questions.length * 100;

        ArrayList<PieEntry> values = new ArrayList<>();
        values.add(new PieEntry(statistics[0], context.getString(R.string.correct)));
        values.add(new PieEntry(statistics[1], "Falsch"));
        values.add(new PieEntry(statistics[2], "Unbeantwortet"));

        return createPieData(createPieDataSet("", values, new int[] {context.getResources().getColor(R.color.correct), context.getResources().getColor(R.color.wrong), context.getResources().getColor(R.color.unanswered)}, context));
    }

    public static PieData getStatisticsBox(Context context) {
        float[] statistics = new float[4];
        for (int i = 0; i < questions.length; i++) {
            Question q = questions[i];
            statistics[q.getAnsweredCorrectCounter() < statistics.length ?
                    q.getAnsweredCorrectCounter() :
                    statistics.length - 1]++;
        }


        ArrayList<PieEntry> values = new ArrayList<>();
        for (int i = 0; i < statistics.length; i++) {
            statistics[i] *= 1.f / questions.length * 100;
            values.add(new PieEntry(statistics[i], "Box " + (i + 1)));
        }

        return createPieData(createPieDataSet("", values,
                new int[] {
                        context.getResources().getColor(R.color.box_0),
                        context.getResources().getColor(R.color.box_1),
                        context.getResources().getColor(R.color.box_2),
                        context.getResources().getColor(R.color.box_3),}, context));
    }

    private static PieData createPieData(PieDataSet dataSet) {
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        data.setHighlightEnabled(false);

        return data;
    }

    private static PieDataSet createPieDataSet(String label, ArrayList<PieEntry> values, int[] colors, Context context) {
        PieDataSet dataSet = new PieDataSet(values, label);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);
        return dataSet;
    }

    private static Question[] getUnansweredQuestions() {
        Question[] ret = null;
        List<Question> li = new ArrayList<>();
        for (Question q : questions)
            if (q.getUnanswered() && QuestionCatalog.chapters == null ||
                    q.getUnanswered() && QuestionCatalog.chapters.contains("" + q.getChapter()))
                li.add(q);

        ret = new Question[li.size()];
        ret = li.toArray(ret);

        if (ret == null)
            throw new RuntimeException("getUnansweredQuestions: Question Catalog not loaded");
        return ret;
    }

    private static Question[] getBoxQuestions(int box) {
        Question[] ret = null;

        List<Question> li = new ArrayList<>();
        for (Question q : questions)
            if (q.getAnsweredCorrectCounter() <= box + 1 && QuestionCatalog.chapters == null ||
                    q.getUnanswered() && QuestionCatalog.chapters.contains("" + q.getChapter()))
                li.add(q);
        ret = new Question[li.size()];
        ret = li.toArray(ret);

        if (ret == null)
            throw new RuntimeException("getBoxQuestions: Question Catalog not loaded");
        return ret;
    }

    private static Question[] getQuestionsChapters() {
        Question[] ret = null;
        List<Question> li = new ArrayList<>();
        for (Question q : questions)
            if (QuestionCatalog.chapters.contains("" + q.getChapter()))
                li.add(q);
        ret = new Question[li.size()];
        ret = li.toArray(ret);

        if (ret == null)
            throw new RuntimeException("getQuestionsChapters: Question Catalog not loaded");
        return ret;
    }

    public static Question[] getCurrentQuestionSet() {
        return currentQuestionSet;
    }

    public static void setCurrentQuestionSet(int type, int box) {
        index = -1;
        Question[] temp = null;
        switch(type) {
            case NEVER_ANSWERED:
                temp = getUnansweredQuestions();
                break;
            case ANSWERED_WRONG:
                temp = getWrongQuestions();
                break;
            case RANDOM_QUESTIONS:
                temp = getQuestionsChapters();
                break;
            case BOX_QUESTIONS:
                temp = getBoxQuestions(box);
                break;
            default:
                throw new RuntimeException("Wrong input: " + type);
        }
        QuestionCatalog.currentQuestionSet = temp;
    }

    public static void setQuestions(Question[] questions, Set<String> chapters) {
        QuestionCatalog.questions = questions;
        QuestionCatalog.chapters = chapters;
    }

    public static void setQuestions(Question[] questions) {
        QuestionCatalog.questions = questions;
        QuestionCatalog.chapters = null;
    }

    public static Set<String> getChapters() {
        return chapters;
    }

    /**
     * Returns null if there are no questions left
     * Returns the next question in the questionSet
     * @return
     */
    public static Question getNextQuestion() {
        if (++index >= currentQuestionSet.length)
            return null;
        return currentQuestionSet[index];
    }

    public static boolean hasNext() {
        return index < currentQuestionSet.length - 1;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }
}
