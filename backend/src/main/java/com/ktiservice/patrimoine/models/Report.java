package com.ktiservice.patrimoine.models;

import com.ktiservice.patrimoine.enums.ReportType;
import com.ktiservice.patrimoine.exceptions.ValidationException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Report domain entity.
 * Represents generated reports (visitation, reviews, engagement).
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Report extends BaseEntity {

    private ReportType reportType;
    private String reportTitle;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Map<String, Object> reportData;
    private String generatedBy;

    /**
     * Factory method to create new Report.
     */
    public static Report create(ReportType reportType, String reportTitle,
                                LocalDateTime startDate, LocalDateTime endDate) {
        if (reportType == null) {
            throw new ValidationException("Report type is required");
        }
        if (reportTitle == null || reportTitle.isBlank()) {
            throw new ValidationException("Report title is required");
        }
        if (startDate == null || endDate == null) {
            throw new ValidationException("Start and end dates are required");
        }
        if (endDate.isBefore(startDate)) {
            throw new ValidationException("End date must be after start date");
        }

        Report report = new Report();
        report.reportType = reportType;
        report.reportTitle = reportTitle;
        report.startDate = startDate;
        report.endDate = endDate;
        return report;
    }

    /**
     * Set report data.
     */
    public void setReportData(Map<String, Object> data) {
        this.reportData = data;
    }
}
