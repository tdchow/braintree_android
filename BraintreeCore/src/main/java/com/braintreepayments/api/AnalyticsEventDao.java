package com.braintreepayments.api;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
interface AnalyticsEventDao {

    @Insert
    void insertEvent(AnalyticsEvent2 event);

    @Query("SELECT * FROM analytics_event")
    List<AnalyticsEvent2> getAllEvents();

    @Delete
    void deleteEvents(List<AnalyticsEvent2> events);
}