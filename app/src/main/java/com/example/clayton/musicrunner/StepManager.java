package com.example.clayton.musicrunner;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by clayf on 9/24/2017.
 */

class StepManager {
    Calendar calendar = Calendar.getInstance();
    Date now = new Date();
    long endTime = calendar.getTimeInMillis();
    long startTime = calendar.getTimeInMillis();

    public StepManager(){
        calendar.setTime(now);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
    }

    public String getStepCount(GoogleApiClient mClient){
        DataReadRequest stepRequest = new DataReadRequest.Builder()
                // The data request can specify multiple data types to return, effectively
                // combining multiple data queries into one call.
                // In this example, it's very unlikely that the request is for several hundred
                // datapoints each consisting of a few steps and a timestamp.  The more likely
                // scenario is wanting to see how many steps were walked per day, for 7 days.
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                // Analogous to a "Group By" in SQL, defines how data should be aggregated.
                // bucketByTime allows for a time span, whereas bucketBySession would allow
                // bucketing by "sessions", which would need to be defined in code.
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.HistoryApi.readData(mClient, stepRequest)
                .setResultCallback(new ResultCallback<DataReadResult>(){

                    @Override
                    public void onResult(DataReadResult result) {
                        for (Bucket bucket : result.getBuckets()) {
                            DataSet data = bucket.getDataSet(DataType.AGGREGATE_STEP_COUNT_DELTA);

                            for (DataPoint dp : data.getDataPoints()) {

                            }
                        }
                    }

                });

        return "test";
    }
}
