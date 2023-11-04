package com.example.demo.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
  
    
    private static  final List<Student> STUDENTS = Arrays.asList(
            new Student(1 , "Yakoub1 Ould bouchiba1"),
            new Student(2 , "Yakoub2 Ould bouchiba2"),
            new Student(3 , "Yakoub3 Ould bouchiba3"),
            new Student(4 , "Yakoub4 Ould bouchiba4")
            
    );
    @GetMapping(path = "/{id}")
    public Student getStudent(@PathVariable("id") Integer id){
      return STUDENTS.stream()
              .filter(student -> student.getId() == id)
              .findFirst()
              .orElseThrow(NoSuchElementException::new);
  }

}
