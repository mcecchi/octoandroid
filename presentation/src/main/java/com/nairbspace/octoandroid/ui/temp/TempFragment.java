package com.nairbspace.octoandroid.ui.temp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.nairbspace.octoandroid.R;
import com.nairbspace.octoandroid.app.SetupApplication;
import com.nairbspace.octoandroid.model.WebsocketModel;
import com.nairbspace.octoandroid.ui.templates.BasePagerFragmentListener;
import com.nairbspace.octoandroid.ui.templates.Presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TempFragment extends BasePagerFragmentListener<TempScreen, TempFragment.Listener>
        implements TempScreen {

    @Inject TempPresenter mPresenter;
    @BindView(R.id.temp_line_chart) LineChart mLineChart;
    private final Listener mListener = (Listener) getContext();

    private static final String X_VALS_TIME_KEY = "x_vals_time_key";
    private static final String Y_VALS_ACTUAL_TEMP_BED_KEY = "y_vals_actual_temp_bed_key";
    private static final String Y_VALS_TARGET_TEMP_BED_KEY = "y_vals_target_temp_bed_key";
    private static final String Y_VALS_ACTUAL_TEMP_TOOL0_KEY = "y_vals_actual_temp_tool0_key";
    private static final String Y_VALS_TARGET_TEMP_TOOL0_KEY = "y_vals_target_temp_tool0_key";
    private static final String Y_VALS_ACTUAL_TEMP_TOOL1_KEY = "y_vals_actual_temp_tool1_key";
    private static final String Y_VALS_TARGET_TEMP_TOOL1_KEY = "y_vals_target_temp_tool1_key";

    private final int actualTempBedIndex;
    private final int targetTempBedIndex;
    private final int actualTempTool0Index;
    private final int targetTempTool0Index;
    private final int actualTempTool1Index;
    private final int targetTempTool1Index;

    private ArrayList<String> xValsTime = new ArrayList<>();
    private ArrayList<Entry> yValsActualTempBed = new ArrayList<>();
    private ArrayList<Entry> yValsTargetTempBed = new ArrayList<>();
    private ArrayList<Entry> yValsActualTempTool0 = new ArrayList<>();
    private ArrayList<Entry> yValsTargetTempTool0 = new ArrayList<>();
    private ArrayList<Entry> yValsActualTempTool1 = new ArrayList<>();
    private ArrayList<Entry> yValsTargetTempTool1 = new ArrayList<>();

    private final LineDataSet actualTempBedDataSet = new LineDataSet(yValsActualTempBed, "Bed - Actual(°C)");
    private final LineDataSet targetTempBedDataSet = new LineDataSet(yValsTargetTempBed, "Bed - Target(°C)");
    private final LineDataSet actualTempTool0DataSet = new LineDataSet(yValsActualTempTool0, "Tool0 - Actual(°C)");
    private final LineDataSet targetTempTool0DataSet = new LineDataSet(yValsTargetTempTool0, "Tool0 - Target(°C)");
    private final LineDataSet actualTempTool1DataSet = new LineDataSet(yValsActualTempTool1, "Tool1 - Actual(°C)");
    private final LineDataSet targetTempTool1DataSet = new LineDataSet(yValsTargetTempTool1, "Tool1 - Target(°C)");

    private final ArrayList<ILineDataSet> mDataSets = new ArrayList<>();
    private final LineData mLineData;
    private final static float VISIBLE_X_RANGE_MAX = 10f;

    public TempFragment() {
        actualTempBedDataSet.enableDashedLine(10f, 5f, 0f);
        actualTempBedDataSet.enableDashedLine(10f, 5f, 0f);
        actualTempBedDataSet.setColor(Color.BLACK);
        actualTempBedDataSet.setCircleColor(Color.BLACK);
        actualTempBedDataSet.setLineWidth(1f);
        actualTempBedDataSet.setCircleRadius(3f);
        actualTempBedDataSet.setDrawCircleHole(false);
        actualTempBedDataSet.setValueTextSize(9f);
        actualTempBedDataSet.setDrawFilled(true);

        mDataSets.add(actualTempBedDataSet);
        actualTempBedIndex = mDataSets.size() - 1;

        mDataSets.add(targetTempBedDataSet);
        targetTempBedIndex = mDataSets.size() - 1;

        mDataSets.add(actualTempTool0DataSet);
        actualTempTool0Index = mDataSets.size() - 1;

        mDataSets.add(targetTempTool0DataSet);
        targetTempTool0Index = mDataSets.size() - 1;

        mDataSets.add(actualTempTool1DataSet);
        actualTempTool1Index = mDataSets.size() - 1;

        mDataSets.add(targetTempTool1DataSet);
        targetTempTool1Index = mDataSets.size() - 1;

        mLineData = new LineData(xValsTime, mDataSets);
    }

    public static TempFragment newInstance() {
        return new TempFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetupApplication.get(getContext()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);
        setUnbinder(ButterKnife.bind(this, view));

        mLineChart.setData(mLineData);
        mLineChart.setScaleEnabled(true);

        if (savedInstanceState != null) {
            restoreSavedInstanceState(savedInstanceState);
        }

        return view;
    }

    private void restoreSavedInstanceState(@NonNull Bundle savedInstanceState) {
        xValsTime = savedInstanceState.getStringArrayList(X_VALS_TIME_KEY);
        yValsActualTempBed = savedInstanceState.getParcelableArrayList(Y_VALS_ACTUAL_TEMP_BED_KEY);
        yValsTargetTempBed = savedInstanceState.getParcelableArrayList(Y_VALS_TARGET_TEMP_BED_KEY);
        yValsActualTempTool0 = savedInstanceState.getParcelableArrayList(Y_VALS_ACTUAL_TEMP_TOOL0_KEY);
        yValsTargetTempTool0 = savedInstanceState.getParcelableArrayList(Y_VALS_TARGET_TEMP_TOOL0_KEY);
        yValsActualTempTool1 = savedInstanceState.getParcelableArrayList(Y_VALS_ACTUAL_TEMP_TOOL1_KEY);
        yValsTargetTempTool1 = savedInstanceState.getParcelableArrayList(Y_VALS_TARGET_TEMP_TOOL1_KEY);

        mLineChart.getData().setXVals(xValsTime);
        ((LineDataSet) mLineChart.getData().getDataSetByIndex(actualTempBedIndex)).setYVals(yValsActualTempBed);
        ((LineDataSet) mLineChart.getData().getDataSetByIndex(targetTempBedIndex)).setYVals(yValsTargetTempBed);
        ((LineDataSet) mLineChart.getData().getDataSetByIndex(actualTempTool0Index)).setYVals(yValsActualTempTool0);
        ((LineDataSet) mLineChart.getData().getDataSetByIndex(targetTempTool0Index)).setYVals(yValsTargetTempTool0);
        ((LineDataSet) mLineChart.getData().getDataSetByIndex(actualTempTool1Index)).setYVals(yValsActualTempTool1);
        ((LineDataSet) mLineChart.getData().getDataSetByIndex(targetTempTool1Index)).setYVals(yValsTargetTempTool1);

        mLineChart.getData().notifyDataChanged();
        updateChart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(X_VALS_TIME_KEY, xValsTime);
        outState.putParcelableArrayList(Y_VALS_ACTUAL_TEMP_BED_KEY, yValsActualTempBed);
        outState.putParcelableArrayList(Y_VALS_TARGET_TEMP_BED_KEY, yValsTargetTempBed);
        outState.putParcelableArrayList(Y_VALS_ACTUAL_TEMP_TOOL0_KEY, yValsActualTempTool0);
        outState.putParcelableArrayList(Y_VALS_TARGET_TEMP_TOOL0_KEY, yValsTargetTempTool0);
        outState.putParcelableArrayList(Y_VALS_ACTUAL_TEMP_TOOL1_KEY, yValsActualTempTool1);
        outState.putParcelableArrayList(Y_VALS_TARGET_TEMP_TOOL1_KEY, yValsTargetTempTool1);
    }

    @Override
    public void updateUi(WebsocketModel websocketModel) {
        xValsTime.add(websocketModel.tempTime());
        mLineChart.getData().addXValue(websocketModel.tempTime());
        addEntry(actualTempBedIndex, websocketModel.actualTempBed());
        addEntry(targetTempBedIndex, websocketModel.targetTempBed());
        addEntry(actualTempTool0Index, websocketModel.actualTempTool0());
        addEntry(targetTempTool0Index, websocketModel.targetTempTool0());
        addEntry(actualTempTool1Index, websocketModel.actualTempTool1());
        addEntry(targetTempTool1Index, websocketModel.targetTempTool1());

        updateChart();
    }

    private Entry addEntry(int dataSetIndex, float val) {
        int xIndex = mLineChart.getData().getDataSetByIndex(dataSetIndex).getEntryCount();
        Entry entry = new Entry(val, xIndex);
        mLineChart.getData().addEntry(entry, dataSetIndex);

        // This is for persistence since Entry's are parcelable.
        // Currently don't know how to retrieve from actual chart
        if (dataSetIndex == actualTempBedIndex) {
            yValsActualTempBed.add(entry);
        } else if (dataSetIndex == targetTempBedIndex) {
            yValsTargetTempBed.add(entry);
        } else if (dataSetIndex == actualTempTool0Index) {
            yValsActualTempTool0.add(entry);
        } else if (dataSetIndex == targetTempTool0Index) {
            yValsTargetTempTool0.add(entry);
        } else if (dataSetIndex == actualTempTool1Index) {
            yValsActualTempTool1.add(entry);
        } else if (dataSetIndex == targetTempTool1Index) {
            yValsTargetTempTool1.add(entry);
        }

        return entry; // For convenience
    }

    public void updateChart() {
        mLineChart.notifyDataSetChanged();
        mLineChart.setVisibleXRangeMaximum(VISIBLE_X_RANGE_MAX);
        mLineChart.moveViewToX(mLineChart.getData().getXValCount() - VISIBLE_X_RANGE_MAX - 1);
    }

    @NonNull
    @Override
    protected Presenter setPresenter() {
        return mPresenter;
    }

    @NonNull
    @Override
    protected TempScreen setScreen() {
        return this;
    }

    @NonNull
    @Override
    protected Listener setListener() {
        return mListener;
    }

    public interface Listener {

    }
}