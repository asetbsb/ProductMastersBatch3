package org.example.student_attendance_boot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentAttendanceDto {
    private String name;
    private String groupName;
    private boolean attended;
}
