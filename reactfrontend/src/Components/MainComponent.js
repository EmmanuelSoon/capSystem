import React, {Component} from 'react';
import {Switch, Route, Redirect } from 'react-router-dom';


import Student from './StudentComponent';
import Home from './HomeComponent';
import EditStudent from './EditStudentComponent';
import Lecturer from './LecturerComponent';
import EditLecturer from './EditLecturerComponent';

class Main extends Component {

  constructor(props) {
    super(props);

    this.state = {}
    
  }




  render() { //render() is used to define the view 
    
    return ( //any function that does not match home or menu will be redirected to dashboard
      <div>
        <Switch>
            <Route path="/admin" exact = {true} component={Home} />
            <Route path="/admin/student" exact={true} component={Student} />
            <Route path="/admin/student/:id" component={EditStudent}/>
            <Route path="/admin/student/new" component={EditStudent}/>
            <Route path="/admin/lecturer" exact={true} component={Lecturer} />
            <Route path="/admin/lecturer/:id" component={EditLecturer}/>
            <Route path="/admin/lecturer/new" component={EditLecturer}/>
            <Redirect to="/admin" /> 
        </Switch>
      </div>

    )
  }

  
}



export default Main;