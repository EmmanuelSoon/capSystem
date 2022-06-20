import React, { Component } from 'react';
import {Button, Card, CardBody, CardTitle, CardText, Row} from 'reactstrap';
import { Link } from 'react-router-dom';

class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {
            token: JSON.parse(sessionStorage.getItem('token')),
        };

    }

    render(){
        return (
            <div className='container'>
                <div className='row'>
                    <div className='col-10 mt-5'>
                        <h2>Welcome {this.state.token.token}</h2>
                            <Card className='col-4 mb-3'>
                                <CardBody>
                                    <CardTitle tag="h5">
                                        Students
                                    </CardTitle>
                                    <CardText>
                                        For information about students currently enrolled in the system click here!
                                    </CardText>
                                    <Button size="sm" color="primary" tag={Link} to={"/admin/student"}>Go to Student Page</Button>
                                </CardBody>
                            </Card>

                        <Card className='col-4 mb-3'>
                                <CardBody>
                                    <CardTitle tag="h5">
                                        Lecturers
                                    </CardTitle>
                                    <CardText>
                                        For information about Lecturers currently teaching in the system click here!
                                    </CardText>
                                    <Button size="sm" color="secondary" tag={Link} to={"/admin/lecturer"}>Go to Lecturer Page</Button>
                                </CardBody>
                            </Card>

                        <Card className='col-4 mb-3 '> 
                                <CardBody>
                                    <CardTitle tag="h5">
                                        Courses
                                    </CardTitle>
                                    <CardText>
                                        For information about Courses available in the system click here!
                                    </CardText>
                                    <Button size="sm" color="info" tag={Link} to={"/admin/courselist"}>Go to Courses Page</Button>
                                </CardBody>
                            </Card>
   

                    </div>
                </div>
            </div>


        )
    }


}
export default Home;