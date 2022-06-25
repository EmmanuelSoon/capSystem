-- Student update profile query
select * from capSystemDB.student s where s.student_id = 1; 

-- Lecturer update profile query
select * from capSystemDB.lecturer l where l.lecturer_id = 2;

-- Student enrollment showcase query
select cd.course_batch_id, c.name, cd.start_date, cd.end_date, s.student_id, s.name 
from capSystemDB.course_detail cd 
join capSystemDB.course c on cd.course_course_id = c.course_id
join capSystemDB.student_course sc on cd.course_batch_id=sc.course_batch_id
join capSystemDB.student s on s.student_id = sc.student_student_id
where cd.course_batch_id = 4;


-- Student showcase query
select cd.course_batch_id, c.course_id, c.name, s.student_id, s.name, sc.gpa 
from capSystemDB.student s 
join capSystemDB.student_course sc on s.student_id = sc.student_student_id 
join capSystemDB.course_detail cd on cd.course_batch_id = sc.course_batch_id 
join capSystemDB.course c on c.course_id = cd.course_course_id 
where s.student_id = 1;




