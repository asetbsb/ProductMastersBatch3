package org.example.student_attendance_boot.service;

import lombok.RequiredArgsConstructor;
import org.example.student_attendance_boot.entity.Group;
import org.example.student_attendance_boot.entity.Student;
import org.example.student_attendance_boot.model.StudentAttendanceDto;
import org.example.student_attendance_boot.repository.GroupRepository;
import org.example.student_attendance_boot.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    public List<StudentAttendanceDto> listAll() {
        return studentRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public void add(StudentAttendanceDto dto) {
        Group group = groupRepository.findByName(dto.getGroupName())
                .orElseGet(() -> {
                    Group g = new Group();
                    g.setName(dto.getGroupName());
                    return groupRepository.save(g);
                });

        Student s = new Student();
        s.setName(dto.getName());
        s.setGroup(group);
        s.setAttended(dto.isAttended());

        studentRepository.save(s);
    }

    private StudentAttendanceDto toDto(Student s) {
        StudentAttendanceDto dto = new StudentAttendanceDto();
        dto.setName(s.getName());
        dto.setGroupName(s.getGroup().getName());
        dto.setAttended(s.isAttended());
        return dto;
    }
}
