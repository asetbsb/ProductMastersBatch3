package org.example.student_attendance_boot.controller;

import lombok.RequiredArgsConstructor;
import org.example.student_attendance_boot.model.StudentAttendanceDto;
import org.example.student_attendance_boot.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AttendanceController {

    private final StudentService studentService;

    @GetMapping("/attendance")
    public String attendancePage(Model model, Authentication authentication) {
        model.addAttribute("students", studentService.listAll());
        model.addAttribute("newStudent", new StudentAttendanceDto());

        boolean isTeacher = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));
        model.addAttribute("isTeacher", isTeacher);

        return "attendance";
    }

    @PostMapping("/attendance/add")
    public String add(@ModelAttribute("newStudent") StudentAttendanceDto dto) {
        studentService.add(dto);
        return "redirect:/attendance";
    }
}
