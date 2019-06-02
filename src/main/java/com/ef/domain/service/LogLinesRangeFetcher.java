package com.ef.domain.service;


import com.ef.domain.bean.LogLineBean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogLinesRangeFetcher {


    public List<LogLineBean> fetchRange(List<LogLineBean> logLineBeans, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (logLineBeans.isEmpty()) return new ArrayList<>();

        int lowerRangeIndex = -1;
        int higherRangeIndex = -1;

        if (startDateTime.isAfter(logLineBeans.get(logLineBeans.size() - 1).getDateTime()) || endDateTime.isBefore(logLineBeans.get(0).getDateTime())) {
            return new ArrayList<>();
        }

        if (startDateTime.compareTo(logLineBeans.get(0).getDateTime()) <= 0) {
            lowerRangeIndex = 0;
        } else {
            lowerRangeIndex = this.searchNearestDate(logLineBeans, startDateTime);
        }

        if (endDateTime.compareTo(logLineBeans.get(logLineBeans.size() - 1).getDateTime()) >= 0) {
            higherRangeIndex = logLineBeans.size();
        } else {
            higherRangeIndex = this.searchNearestDate(logLineBeans, endDateTime);

        }
        return logLineBeans.subList(lowerRangeIndex, higherRangeIndex);
    }


    private int searchNearestDate(List<LogLineBean> logLineBeans, LocalDateTime dateTime) {
        //logLineBeans must be ordered
        int lowerIndex = 0;
        int higherIndex = logLineBeans.size() - 1;

        while (lowerIndex <= higherIndex) {
            int middleIndex = (higherIndex + lowerIndex) / 2;

            if (dateTime.isBefore(logLineBeans.get(middleIndex).getDateTime())) {
                higherIndex = middleIndex - 1;
            } else if (dateTime.isAfter(logLineBeans.get(middleIndex).getDateTime())) {
                lowerIndex = middleIndex + 1;
            } else {
                return middleIndex;
            }
        }

        return dateTime.isAfter(logLineBeans.get(lowerIndex).getDateTime()) ? higherIndex : lowerIndex;
    }


}
