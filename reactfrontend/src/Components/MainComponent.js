import React, {Component} from 'react';
import {Switch, Route, Redirect } from 'react-router-dom';


import Student from './StudentComponent';
import Home from './HomeComponent';
import EditStudent from './EditStudentComponent';
import Lecturer from './LecturerComponent';
import EditLecturer from './EditLecturerComponent';
import StudentCourse from './StudentCourseComponent';
import Header from './HeaderComponent';
import Course from "./CourseComponent";
import LecturerCourse from './LecturerCourseComponent';
import CourseDetail from "./CourseDetailComponent";
import BatchDetail from "./BatchDetailComponent";
import Enrolment from './EnrolmentComponent';
import EditCourse from './EditCourseComponent';

class Main extends Component {

  constructor(props) {
    super(props);

    this.state = {}
    
  }




  render() { //render() is used to define the view 
    
    return ( //any function that does not match home or menu will be redirected to dashboard
      <div>
        <Header />
        <Switch>
            <Route path="/admin" exact = {true} component={Home} />
            <Route path="/admin/student" exact={true} component={Student} />
            <Route path="/admin/student/:id" exact={true} component={EditStudent}/>
            <Route path="/admin/student/new" exact={true} component={EditStudent}/>
            <Route path="/admin/student/course/:id" exact={true} component={StudentCourse} />
            <Route path="/admin/student/course/:id/new" exact={true} component={Enrolment} />
            

            <Route path="/admin/lecturer" exact={true} component={Lecturer} />
            <Route path="/admin/lecturer/:id" exact={true} component={EditLecturer}/>
            <Route path="/admin/lecturer/new" exact={true} component={EditLecturer}/>
            <Route path="/admin/lecturer/course/:id"  component={LecturerCourse} />


            <Route path="/admin/courselist" exact={true} component={Course}/>
            <Route path="/admin/course/:id" exact={true} component={EditCourse}/>
            <Route path="/admin/course/new" exact={true} component={EditCourse}/>

            <Route path="/admin/coursedetails/:id" exact={true} component={CourseDetail} />
            <Route path="/admin/coursedetails/batch/:id" component={BatchDetail} />
            <Redirect to="/admin" />
        </Switch>
      </div>

    )
  }

  
}



export default Main;