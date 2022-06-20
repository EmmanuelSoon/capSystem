import React, { Component } from 'react';
import {Button, Card, CardBody, CardTitle, CardText, CardImg, CardHeader} from 'reactstrap';
import { Link } from 'react-router-dom';


import student from '../assets/images/studyingpig.jpg';
import lecturer from '../assets/images/lecturer.png';
import courses from '../assets/images/courses.jpg';


class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {
            token: JSON.parse(sessionStorage.getItem('token')),
        };

    }

    render(){
        return (
            <div className='container' style={{height:"900px"}}>
                <div className='row align-items-start'>
                        <h2 className='mt-3 mb-3'>Welcome {this.state.token.token},</h2>
                        <div className='col-12 col-md m-1'>
                            <Card body outline color="secondary" className='h-75'>
                                <CardImg src={student} height='300'></CardImg>
                                <CardBody>
                                    <CardTitle tag="h5">
                                        Students
                                    </CardTitle>
                                    <CardText>
                                        For information about students currently enrolled in the system click here!
                                    </CardText>
                                    <Button color="primary" tag={Link} to={"/admin/student"}>Go to Student Page</Button>
                                </CardBody>
                            </Card>
                        </div>
                        <div className='col-12 col-md m-1'>
                            <Card body outline color="secondary" className='h-75'>
                                <CardImg src={lecturer} height='300'></CardImg>
                                <CardBody>
                                    <CardTitle tag="h5">
                                        Lecturers
                                    </CardTitle>
                                    <CardText>
                                        For information about Lecturers currently teaching in the system click here!
                                    </CardText>
                                    <Button color="secondary" tag={Link} to={"/admin/lecturer"}>Go to Lecturer Page</Button>
                                </CardBody>
                            </Card>
                        </div>
                        <div className='col-12 col-md m-1'>

                            <Card body outline color="secondary" className='h-75'> 
                            <CardImg src={courses} height='300'></CardImg>

                                <CardBody>
                                    <CardTitle tag="h5">
                                        Courses
                                    </CardTitle>
                                    <CardText>
                                        For information about Courses available in the system click here!
                                    </CardText>
                                    <Button color="info" tag={Link} to={"/admin/courselist"}>Go to Courses Page</Button>
                                </CardBody>
                            </Card>
                        </div>
                </div>
            </div>


        )
    }


}
export default Home;