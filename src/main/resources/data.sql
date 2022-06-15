
INSERT INTO admin 
    Select 'admin', 'password', 'admin1', 'admin@gmail.com' from dual
where not exists (
    select name from admin where name='admin1'
);

INSERT INTO lecturer values (default, 'esther', 'password', 'Esther', 'Esther@gmail.com');
INSERT INTO lecturer values (default, 'tin', 'password', 'Tin', 'Tin@gmail.com');
INSERT INTO lecturer values (default, 'yeunkwan', 'password', 'Yeun Kwan', 'YK@gmail.com');
INSERT INTO lecturer values (default, 'suria', 'password', 'Suria', 'Suria@gmail.com');
INSERT INTO lecturer values (default, 'cherwah', 'password', 'Cher Wah', 'CW@gmail.com');
INSERT INTO lecturer values (default, 'liufan', 'password', 'LiuFan', 'LF@gmail.com');
INSERT INTO lecturer values (default, 'tsukiji', 'password', 'Tsukiji', 'tsutsu@gmail.com');


INSERT INTO student values (default, 'Student1', 'password', 'Student', 'student@gmail.com');
INSERT INTO student values (default, 'alyssa', 'password', 'Alyssa', 'alyssa@gmail.com');
INSERT INTO student values (default, 'emmanuel', 'password', 'Emmanuel', 'emmanuel@gmail.com');
INSERT INTO student values (default, 'gavin', 'password', 'Gavin', 'gavin@gmail.com');
INSERT INTO student values (default, 'youcheng', 'password', 'YouCheng', 'YC@gmail.com');
INSERT INTO student values (default, 'hein', 'password', 'Hein', 'hein@gmail.com');
INSERT INTO student values (default, 'yoonmie', 'password', 'YoonMie', 'YM@gmail.com');
INSERT INTO student values (default, 'anand', 'password', 'Anand', 'anand@gmail.com');

INSERT INTO course values (default, 'Java Programming', 'Basic java and springboot application');
INSERT INTO course values (default, 'ML', 'Machine Learning');
INSERT INTO course values (default, 'FOPCS', 'Basic programming');
INSERT INTO course values (default, 'NiHonGo', 'Minasan no nihongo!');
INSERT INTO course values (default, 'Mobile Applications', 'Basic app creation through android studio');
INSERT INTO course values (default, 'Pokemon 101', 'How to be a pokemon master');
INSERT INTO course values (default, 'Farming 101', 'How to start your own farm');
INSERT INTO course values (default, 'Cooking with Pork', 'From meat to dish, learn all the tricks chefs use!');






