package com.example.demo.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mangement/api/v1/students")
public class StudentMangementController {
  
    
    private static   List<Student> STUDENTS =new ArrayList<>(List.of(new Student[]{
            new Student(1, "Yakoub1 Ould bouchiba1"),
            new Student(2, "Yakoub2 Ould bouchiba2"),
            new Student(3, "Yakoub3 Ould bouchiba3"),
            new Student(4, "Yakoub4 Ould bouchiba4")})

    );
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN' , 'ADMINTRAINEE')")
    public static List<Student> getSTUDENTS() {
        return STUDENTS;
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student){
         STUDENTS.add(student);
    }
    
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("id") Integer id){
        STUDENTS.remove(STUDENTS.stream().filter(student -> student.getId().equals(id)).findFirst().get());
    }
    
    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("id") Integer id , @RequestBody   Student student){
        STUDENTS.set(STUDENTS.stream().takeWhile(s-> s.getId()!=id).collect(Collectors.toList()).size(), student);
    }
    
    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('student:read')")
    public Student getStudent(@PathVariable("id") Integer id){
      return STUDENTS.stream()
              .filter(student -> student.getId() == id)
              .findFirst()
              .orElseThrow(NoSuchElementException::new);
  }

}
